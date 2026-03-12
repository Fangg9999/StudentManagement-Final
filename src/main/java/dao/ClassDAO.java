package dao;

import util.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.ClassRoom;

public class ClassDAO {

    // Lấy ID Sinh viên từ Mã SV (Dùng nội bộ)
    private int getStudentIdByCode(String studentCode, Connection conn) throws SQLException {
        String sql = "SELECT id FROM Students WHERE student_code = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }

    // =======================================================
    // LOGIC 1 & 2: THÊM SINH VIÊN BẰNG MÃ & TỰ ĐỘNG CHÈN ĐIỂM
    // =======================================================
    public String addStudentToClass(int classId, String studentCode) {
        String checkClassSql = "SELECT id FROM Class_Student WHERE class_id = ? AND student_id = ?";
        String insertClassSql = "INSERT INTO Class_Student (class_id, student_id) VALUES (?, ?)";
        String insertGradeSql = "INSERT INTO Grades (student_id, class_id, score) VALUES (?, ?, NULL)";

        try (Connection conn = DBContext.getConnection()) {
            // Bước 1: Tìm ID sinh viên
            int studentId = getStudentIdByCode(studentCode, conn);
            if (studentId == -1) {
                return "not_found"; // Báo lỗi: Mã SV không tồn tại
            }
            // Bước 2: Chống thêm trùng lặp
            try (PreparedStatement psCheck = conn.prepareStatement(checkClassSql)) {
                psCheck.setInt(1, classId);
                psCheck.setInt(2, studentId);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        return "exists"; // Báo lỗi: SV đã có trong lớp này rồi
                    }
                }
            }

            // Bước 3: TRANSACTION - Đảm bảo dữ liệu đồng nhất
            conn.setAutoCommit(false);
            try (PreparedStatement psClass = conn.prepareStatement(insertClassSql); PreparedStatement psGrade = conn.prepareStatement(insertGradeSql)) {

                // Nhét vào lớp
                psClass.setInt(1, classId);
                psClass.setInt(2, studentId);
                psClass.executeUpdate();

                // Dọn sẵn 1 dòng điểm Rỗng (NULL)
                psGrade.setInt(1, studentId);
                psGrade.setInt(2, classId);
                psGrade.executeUpdate();

                conn.commit(); // Chốt giao dịch thành công!
                return "success";
            } catch (Exception e) {
                conn.rollback(); // Lỗi thì quay xe
                e.printStackTrace();
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    // =======================================================
    // LOGIC 3: XÓA SINH VIÊN (KÈM KHÓA AN TOÀN NẾU ĐÃ CÓ ĐIỂM)
    // =======================================================
    public String removeStudentFromClass(int classId, int studentId) {
        String checkGradeSql = "SELECT score FROM Grades WHERE class_id = ? AND student_id = ?";
        String deleteGradeSql = "DELETE FROM Grades WHERE class_id = ? AND student_id = ?";
        String deleteClassSql = "DELETE FROM Class_Student WHERE class_id = ? AND student_id = ?";

        try (Connection conn = DBContext.getConnection()) {
            // Bước 1: Kiểm tra xem Giáo viên đã chấm điểm chưa
            try (PreparedStatement psCheck = conn.prepareStatement(checkGradeSql)) {
                psCheck.setInt(1, classId);
                psCheck.setInt(2, studentId);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        Object score = rs.getObject("score");
                        if (score != null) {
                            return "graded"; // SV ĐÃ CÓ ĐIỂM -> Kích hoạt khóa an toàn, chặn không cho xóa
                        }
                    }
                }
            }

            // Bước 2: An toàn để xóa -> Tiến hành TRANSACTION
            conn.setAutoCommit(false);
            try (PreparedStatement psDelGrade = conn.prepareStatement(deleteGradeSql); PreparedStatement psDelClass = conn.prepareStatement(deleteClassSql)) {

                psDelGrade.setInt(1, classId);
                psDelGrade.setInt(2, studentId);
                psDelGrade.executeUpdate();

                psDelClass.setInt(1, classId);
                psDelClass.setInt(2, studentId);
                psDelClass.executeUpdate();

                conn.commit();
                return "success";
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    // =======================================================
    // LẤY DANH SÁCH SINH VIÊN TRONG LỚP (Hiển thị ra màn hình)
    // =======================================================
    public List<Map<String, Object>> getStudentsInClass(int classId) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT s.id, s.student_code, s.full_name, g.score "
                + "FROM Class_Student cs "
                + "JOIN Students s ON cs.student_id = s.id "
                + "LEFT JOIN Grades g ON s.id = g.student_id AND g.class_id = cs.class_id "
                + "WHERE cs.class_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("student_id", rs.getInt("id"));
                    map.put("student_code", rs.getString("student_code"));
                    map.put("full_name", rs.getString("full_name"));
                    map.put("score", rs.getObject("score"));
                    list.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // =======================================================
    // MODULE: QUẢN LÝ LỚP HỌC (CRUD CLASSES)
    // =======================================================
    // 1. Lấy danh sách Lớp học (Kèm tên Môn, Kỳ, Giáo viên)
    public List<Map<String, Object>> getAllClasses() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT c.id, c.class_code, s.subject_name, sem.semester_name, u.full_name AS teacher_name "
                + "FROM Classes c "
                + "JOIN Subjects s ON c.subject_id = s.id "
                + "JOIN Semesters sem ON c.semester_id = sem.id "
                + "JOIN Users u ON c.teacher_id = u.id "
                + "ORDER BY c.id DESC";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getInt("id"));
                map.put("class_code", rs.getString("class_code"));
                map.put("subject_name", rs.getString("subject_name"));
                map.put("semester_name", rs.getString("semester_name"));
                map.put("teacher_name", rs.getString("teacher_name"));
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Các hàm lấy dữ liệu cho Dropdown ở Form Thêm/Sửa
    public List<Map<String, Object>> getDropdownData(String sql, String idCol, String nameCol) {
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getInt(idCol));
                map.put("name", rs.getString(nameCol));
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ClassRoom getClassById(int id) {
        String sql = "SELECT * FROM Classes WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ClassRoom(rs.getInt("id"), rs.getString("class_code"),
                            rs.getInt("subject_id"), rs.getInt("semester_id"), rs.getInt("teacher_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertClass(ClassRoom c) {
        String sql = "INSERT INTO Classes (class_code, subject_id, semester_id, teacher_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getClassCode());
            ps.setInt(2, c.getSubjectId());
            ps.setInt(3, c.getSemesterId());
            ps.setInt(4, c.getTeacherId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateClass(ClassRoom c) {
        String sql = "UPDATE Classes SET subject_id=?, semester_id=?, teacher_id=? WHERE id=?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getSubjectId());
            ps.setInt(2, c.getSemesterId());
            ps.setInt(3, c.getTeacherId());
            ps.setInt(4, c.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteClass(int id) {
        String delGrades = "DELETE FROM Grades WHERE class_id = ?";
        String delStudents = "DELETE FROM Class_Student WHERE class_id = ?";
        String delClass = "DELETE FROM Classes WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement p1 = conn.prepareStatement(delGrades); PreparedStatement p2 = conn.prepareStatement(delStudents); PreparedStatement p3 = conn.prepareStatement(delClass)) {
                p1.setInt(1, id);
                p1.executeUpdate();
                p2.setInt(1, id);
                p2.executeUpdate();
                p3.setInt(1, id);
                boolean success = p3.executeUpdate() > 0;
                conn.commit();
                return success;
            } catch (Exception e) {
                conn.rollback();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    // Lấy danh sách lớp học do MỘT giáo viên cụ thể phụ trách
    public List<Map<String, Object>> getClassesByTeacherId(int teacherId) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT c.id AS class_id, c.class_code, s.id AS subject_id, s.subject_name, sem.semester_name, " +
                     "(SELECT COUNT(*) FROM Class_Student cs WHERE cs.class_id = c.id) AS student_count " +
                     "FROM Classes c " +
                     "JOIN Subjects s ON c.subject_id = s.id " +
                     "JOIN Semesters sem ON c.semester_id = sem.id " +
                     "WHERE c.teacher_id = ? " +
                     "ORDER BY c.id DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("class_id", rs.getInt("class_id"));
                    map.put("class_code", rs.getString("class_code"));
                    map.put("subject_id", rs.getInt("subject_id"));
                    map.put("subject_name", rs.getString("subject_name"));
                    map.put("semester_name", rs.getString("semester_name"));
                    map.put("student_count", rs.getInt("student_count")); // Đếm sĩ số lớp
                    list.add(map);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}

package dao;

import model.Student;
import util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // 1. Lấy danh sách Sinh viên (Có tìm kiếm + Phân trang)
    public List<Student> getStudents(String keyword, int page, int pageSize) {
        List<Student> list = new ArrayList<>();
        // Công thức tính OFFSET
        int offset = (page - 1) * pageSize;

        // Dùng OR để gom 3 tiêu chí vào 1 ô tìm kiếm. Bắt buộc phải có ORDER BY khi dùng OFFSET.
        String sql = "SELECT id, student_code, full_name, dob, enrollment_date, homeroom_class, major_id "
                + "FROM Students "
                + "WHERE (student_code LIKE ? OR full_name LIKE ? OR homeroom_class LIKE ?) "
                + "ORDER BY id DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setInt(4, offset);
            ps.setInt(5, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setId(rs.getInt("id"));
                    s.setStudentCode(rs.getString("student_code"));
                    s.setFullName(rs.getString("full_name"));
                    s.setDob(rs.getDate("dob"));
                    s.setEnrollmentDate(rs.getDate("enrollment_date"));
                    s.setHomeroomClass(rs.getString("homeroom_class"));
                    s.setMajorId(rs.getInt("major_id"));
                    list.add(s);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi getStudents: " + e.getMessage());
        }
        return list;
    }

    // 2. Đếm tổng số Sinh viên (để tính tổng số trang)
    public int getTotalStudents(String keyword) {
        String sql = "SELECT COUNT(*) FROM Students WHERE (student_code LIKE ? OR full_name LIKE ? OR homeroom_class LIKE ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi getTotalStudents: " + e.getMessage());
        }
        return 0;
    }

    // Kiểm tra trùng Mã SV (Dùng chung cho cả Thêm và Sửa. Nếu là Thêm thì excludeId = 0)
    public boolean isDuplicateCode(String studentCode, int excludeId) {
        String sql = "SELECT id FROM Students WHERE student_code = ? AND id != ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentCode);
            ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Trả về true nếu đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm mới Sinh viên
    public boolean insertStudent(Student s) {
        String sql = "INSERT INTO Students (student_code, full_name, dob, enrollment_date, homeroom_class, major_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getStudentCode());
            ps.setString(2, s.getFullName());
            ps.setDate(3, s.getDob());
            ps.setDate(4, s.getEnrollmentDate());
            ps.setString(5, s.getHomeroomClass());
            ps.setInt(6, s.getMajorId()); // Giả sử đã có sẵn Major, nếu chưa có truyền DB Null
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy 1 Sinh viên theo ID
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM Students WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getInt("id"), rs.getString("student_code"),
                            rs.getString("full_name"), rs.getDate("dob"),
                            rs.getDate("enrollment_date"), rs.getString("homeroom_class"),
                            rs.getInt("major_id")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật Sinh viên (Bỏ qua student_code)
    public boolean updateStudent(Student s) {
        String sql = "UPDATE Students SET full_name=?, dob=?, enrollment_date=?, homeroom_class=?, major_id=? WHERE id=?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getFullName());
            ps.setDate(2, s.getDob());
            ps.setDate(3, s.getEnrollmentDate());
            ps.setString(4, s.getHomeroomClass());
            ps.setInt(5, s.getMajorId());
            ps.setInt(6, s.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteStudent(int id) {
        String deleteGrades = "DELETE FROM Grades WHERE student_id = ?";
        String deleteClass = "DELETE FROM Class_Student WHERE student_id = ?";
        String deleteStudent = "DELETE FROM Students WHERE id = ?";

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false); // Bật Transaction để xóa an toàn
            try (PreparedStatement ps1 = conn.prepareStatement(deleteGrades); PreparedStatement ps2 = conn.prepareStatement(deleteClass); PreparedStatement ps3 = conn.prepareStatement(deleteStudent)) {

                ps1.setInt(1, id);
                ps1.executeUpdate(); // Xóa điểm
                ps2.setInt(1, id);
                ps2.executeUpdate(); // Xóa khỏi lớp
                ps3.setInt(1, id);
                boolean success = ps3.executeUpdate() > 0; // Xóa sinh viên

                conn.commit();
                return success;
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

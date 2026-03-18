package dao;

import util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeDAO {

    // 1. Lấy danh sách Sinh viên cùng với Điểm hiện tại của họ trong một Lớp cụ thể
    // Sử dụng LEFT JOIN để lấy cả những SV chưa có điểm
    public List<Map<String, Object>> getStudentsAndGrades(int classId, int subjectId) {
        List<Map<String, Object>> list = new ArrayList<>();
        // CHÍNH: Sử dụng class_id thay vì subject_id vì Grades table chỉ có class_id
        String sql = "SELECT s.id as student_id, s.student_code, s.full_name, g.score " +
                     "FROM Class_Student cs " +
                     "JOIN Students s ON cs.student_id = s.id " +
                     "LEFT JOIN Grades g ON s.id = g.student_id AND g.class_id = ? " +
                     "WHERE cs.class_id = ? " +
                     "ORDER BY s.student_code";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, classId);  // Set g.class_id = ?
            ps.setInt(2, classId);  // Set WHERE cs.class_id = ?
            
            System.out.println("[DEBUG DAO] Executing SQL with classId=" + classId);
            
            try (ResultSet rs = ps.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    Map<String, Object> map = new HashMap<>();
                    map.put("student_id", rs.getInt("student_id"));
                    map.put("student_code", rs.getString("student_code"));
                    map.put("full_name", rs.getString("full_name"));
                    // Nếu chưa có điểm, getObject sẽ trả về null thay vì 0.0
                    map.put("score", rs.getObject("score")); 
                    list.add(map);
                }
                System.out.println("[DEBUG DAO] Found " + count + " students for classId=" + classId);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getStudentsAndGrades: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 2. Lưu điểm hàng loạt (Bulk Save) dùng MERGE (Upsert) và Batch Update
    // Chú ý: Lưu với class_id thay vì subject_id
    public boolean saveGradesBatch(int classId, String[] studentIds, String[] scores) {
        // Lệnh MERGE: Nếu khớp (student_id, class_id) thì UPDATE, nếu không khớp thì INSERT
        String sql = "MERGE INTO Grades AS target " +
                     "USING (SELECT ? AS student_id, ? AS class_id, ? AS score) AS source " +
                     "ON target.student_id = source.student_id AND target.class_id = source.class_id " +
                     "WHEN MATCHED THEN UPDATE SET score = source.score " +
                     "WHEN NOT MATCHED THEN INSERT (student_id, class_id, score) " +
                     "VALUES (source.student_id, source.class_id, source.score);";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Tắt auto-commit để chạy transaction an toàn
            conn.setAutoCommit(false); 

            for (int i = 0; i < studentIds.length; i++) {
                if (scores[i] != null && !scores[i].trim().isEmpty()) {
                    ps.setInt(1, Integer.parseInt(studentIds[i]));
                    ps.setInt(2, classId);  // Sử dụng classId thay vì subjectId
                    ps.setDouble(3, Double.parseDouble(scores[i]));
                    ps.addBatch(); // Đưa vào mảng chờ
                }
            }
            
            System.out.println("[DEBUG DAO] saveGradesBatch for classId=" + classId + " with " + studentIds.length + " students");
            
            ps.executeBatch(); // Thực thi toàn bộ mảng trong 1 lần
            conn.commit();     // Chốt giao dịch
            System.out.println("[DEBUG DAO] saveGradesBatch completed successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi saveGradesBatch: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
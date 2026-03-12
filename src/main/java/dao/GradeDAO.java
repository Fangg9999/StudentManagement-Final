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
        String sql = "SELECT s.id as student_id, s.student_code, s.full_name, g.score " +
                     "FROM Class_Student cs " +
                     "JOIN Students s ON cs.student_id = s.id " +
                     "LEFT JOIN Grades g ON s.id = g.student_id AND g.subject_id = ? " +
                     "WHERE cs.class_id = ? " +
                     "ORDER BY s.student_code";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, subjectId);
            ps.setInt(2, classId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("student_id", rs.getInt("student_id"));
                    map.put("student_code", rs.getString("student_code"));
                    map.put("full_name", rs.getString("full_name"));
                    // Nếu chưa có điểm, getObject sẽ trả về null thay vì 0.0
                    map.put("score", rs.getObject("score")); 
                    list.add(map);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi getStudentsAndGrades: " + e.getMessage());
        }
        return list;
    }

    // 2. Lưu điểm hàng loạt (Bulk Save) dùng MERGE (Upsert) và Batch Update
    public boolean saveGradesBatch(int subjectId, String[] studentIds, String[] scores) {
        // Lệnh MERGE: Nếu khớp (student_id, subject_id) thì UPDATE, nếu không khớp thì INSERT
        String sql = "MERGE INTO Grades AS target " +
                     "USING (SELECT ? AS student_id, ? AS subject_id, ? AS score) AS source " +
                     "ON target.student_id = source.student_id AND target.subject_id = source.subject_id " +
                     "WHEN MATCHED THEN UPDATE SET score = source.score " +
                     "WHEN NOT MATCHED THEN INSERT (student_id, subject_id, score) " +
                     "VALUES (source.student_id, source.subject_id, source.score);";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Tắt auto-commit để chạy transaction an toàn
            conn.setAutoCommit(false); 

            for (int i = 0; i < studentIds.length; i++) {
                if (scores[i] != null && !scores[i].trim().isEmpty()) {
                    ps.setInt(1, Integer.parseInt(studentIds[i]));
                    ps.setInt(2, subjectId);
                    ps.setDouble(3, Double.parseDouble(scores[i]));
                    ps.addBatch(); // Đưa vào mảng chờ
                }
            }
            
            ps.executeBatch(); // Thực thi toàn bộ mảng trong 1 lần
            conn.commit();     // Chốt giao dịch
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi saveGradesBatch: " + e.getMessage());
            return false;
        }
    }
}
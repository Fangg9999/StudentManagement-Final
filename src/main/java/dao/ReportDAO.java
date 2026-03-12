package dao;

import util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportDAO {

    // Lấy danh sách điểm trung bình của toàn bộ sinh viên đã có ít nhất 1 đầu điểm
    public List<Map<String, Object>> getStudentGPAList() {
        List<Map<String, Object>> list = new ArrayList<>();
        // Hàm AVG() tự động bỏ qua NULL
        String sql = "SELECT s.student_code, s.full_name, s.homeroom_class, AVG(g.score) AS gpa " +
                     "FROM Students s " +
                     "JOIN Grades g ON s.id = g.student_id " +
                     "GROUP BY s.student_code, s.full_name, s.homeroom_class " +
                     "ORDER BY gpa DESC";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("student_code", rs.getString("student_code"));
                map.put("full_name", rs.getString("full_name"));
                map.put("homeroom_class", rs.getString("homeroom_class"));
                // Làm tròn 2 chữ số thập phân ở Java hoặc SQL, ở đây lấy raw
                map.put("gpa", rs.getDouble("gpa")); 
                list.add(map);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getStudentGPAList: " + e.getMessage());
        }
        return list;
    }
}
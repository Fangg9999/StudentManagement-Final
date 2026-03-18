package dao;

import util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class DashboardDAO {

    public Map<String, Integer> getStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        
        // 4 câu lệnh đếm cơ bản
        String sqlStudents = "SELECT COUNT(*) FROM Students";
        String sqlClasses = "SELECT COUNT(*) FROM Classes";
        String sqlSubjects = "SELECT COUNT(*) FROM Subjects";
        String sqlTeachers = "SELECT COUNT(*) FROM Users WHERE roleId=2";

        try (Connection conn = DBContext.getConnection()) {
            stats.put("students", getCount(conn, sqlStudents));
            stats.put("classes", getCount(conn, sqlClasses));
            stats.put("subjects", getCount(conn, sqlSubjects));
            stats.put("teachers", getCount(conn, sqlTeachers));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

    // Hàm dùng chung để thực thi lệnh đếm, tránh lặp code
    private int getCount(Connection conn, String sql) {
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
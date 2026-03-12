package dao;

import model.Subject;
import util.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    
    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM Subjects ORDER BY id DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Subject(rs.getInt("id"), rs.getString("subject_code"), 
                        rs.getString("subject_name"), rs.getInt("credits")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public Subject getSubjectById(int id) {
        String sql = "SELECT * FROM Subjects WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Subject(rs.getInt("id"), rs.getString("subject_code"), 
                        rs.getString("subject_name"), rs.getInt("credits"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(Subject s) {
        String sql = "INSERT INTO Subjects (subject_code, subject_name, credits) VALUES (?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getSubjectCode());
            ps.setString(2, s.getSubjectName());
            ps.setInt(3, s.getCredits());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean update(Subject s) {
        String sql = "UPDATE Subjects SET subject_name = ?, credits = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getSubjectName());
            ps.setInt(2, s.getCredits());
            ps.setInt(3, s.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public String delete(int id) {
        // Kiểm tra xem Môn học đã có Lớp nào chưa
        String checkSql = "SELECT COUNT(*) FROM Classes WHERE subject_id = ?";
        String delSql = "DELETE FROM Subjects WHERE id = ?";
        
        try (Connection conn = DBContext.getConnection()) {
            try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setInt(1, id);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return "foreign_key_error"; // Đang có lớp dạy môn này, không được xóa
                }
            }
            
            try (PreparedStatement psDel = conn.prepareStatement(delSql)) {
                psDel.setInt(1, id);
                return psDel.executeUpdate() > 0 ? "success" : "error";
            }
        } catch (Exception e) { e.printStackTrace(); return "error"; }
    }
}
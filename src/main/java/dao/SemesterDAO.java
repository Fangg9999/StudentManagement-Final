package dao;

import model.Semester;
import util.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SemesterDAO {
    public List<Semester> getAll() {
        List<Semester> list = new ArrayList<>();
        String sql = "SELECT * FROM Semesters ORDER BY id DESC";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(new Semester(rs.getInt("id"), rs.getString("semester_code"), rs.getString("semester_name")));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public Semester getById(int id) {
        String sql = "SELECT * FROM Semesters WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Semester(rs.getInt("id"), rs.getString("semester_code"), rs.getString("semester_name"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(Semester s) {
        String sql = "INSERT INTO Semesters (semester_code, semester_name) VALUES (?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getSemesterCode()); ps.setString(2, s.getSemesterName());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean update(Semester s) {
        String sql = "UPDATE Semesters SET semester_name = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getSemesterName()); ps.setInt(2, s.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public String delete(int id) {
        String checkSql = "SELECT COUNT(*) FROM Classes WHERE semester_id = ?";
        String delSql = "DELETE FROM Semesters WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setInt(1, id); ResultSet rs = psCheck.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) return "foreign_key_error"; 
            }
            try (PreparedStatement psDel = conn.prepareStatement(delSql)) {
                psDel.setInt(1, id); return psDel.executeUpdate() > 0 ? "success" : "error";
            }
        } catch (Exception e) { e.printStackTrace(); return "error"; }
    }
}
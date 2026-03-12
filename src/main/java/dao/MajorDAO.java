package dao;

import model.Major;
import util.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MajorDAO {
    
    public List<Major> getAllMajors() {
        List<Major> list = new ArrayList<>();
        String sql = "SELECT * FROM Majors ORDER BY id DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Major(rs.getInt("id"), rs.getString("major_code"), rs.getString("major_name")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public Major getMajorById(int id) {
        String sql = "SELECT * FROM Majors WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Major(rs.getInt("id"), rs.getString("major_code"), rs.getString("major_name"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(Major m) {
        String sql = "INSERT INTO Majors (major_code, major_name) VALUES (?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getMajorCode());
            ps.setString(2, m.getMajorName());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean update(Major m) {
        String sql = "UPDATE Majors SET major_name = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getMajorName()); // Chỉ cho sửa tên, khóa mã ngành
            ps.setInt(2, m.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Majors WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
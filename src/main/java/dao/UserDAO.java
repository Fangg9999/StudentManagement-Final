package dao;

import model.User;
import util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User checkLogin(String username, String hashedPassword) {
        String sql = "SELECT id, username, full_name, role FROM Users WHERE username = ? AND password = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, hashedPassword);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int roleId = 2; // Mặc định là TEACHER
                    String role = rs.getString("role");
                    if ("ADMIN".equalsIgnoreCase(role)) {
                        roleId = 1;
                    }
                    
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("full_name"),
                            null, // email không có trong database
                            roleId
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi đăng nhập: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Trả về null nếu sai thông tin hoặc có lỗi
    }

    public boolean updatePassword(int userId, String newHashedPassword) {
        String sql = "UPDATE Users SET password = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newHashedPassword);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProfile(int userId, String fullName, String email) {
        String sql = "UPDATE Users SET full_name = ?, email = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setInt(3, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // =======================================================
    // MODULE: QUẢN LÝ TÀI KHOẢN (DÀNH CHO ADMIN)
    // =======================================================
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, username, full_name, role FROM Users ORDER BY id DESC";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int roleId = 2; // Mặc định là TEACHER
                String role = rs.getString("role");
                if ("ADMIN".equalsIgnoreCase(role)) {
                    roleId = 1;
                }
                list.add(new User(rs.getInt("id"), rs.getString("username"),
                        rs.getString("full_name"), null, roleId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public User getUserById(int id) {
        String sql = "SELECT id, username, full_name, role FROM Users WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int roleId = 2; // Mặc định là TEACHER
                    String role = rs.getString("role");
                    if ("ADMIN".equalsIgnoreCase(role)) {
                        roleId = 1;
                    }
                    return new User(rs.getInt("id"), rs.getString("username"),
                            rs.getString("full_name"), null, roleId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isDuplicateUsername(String username, int excludeId) {
        String sql = "SELECT id FROM Users WHERE username = ? AND id != ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Khi tạo mới, Admin cấp luôn mật khẩu mặc định đã được Hash MD5
    public boolean insertUser(User u, String hashedDefaultPassword) {
        String role = (u.getRoleId() == 1) ? "ADMIN" : "TEACHER";
        String sql = "INSERT INTO Users (username, password, full_name, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, hashedDefaultPassword);
            ps.setString(3, u.getFullName());
            ps.setString(4, role);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("🚨 LỖI INSERT DATABASE: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserByAdmin(User u) {
        String role = (u.getRoleId() == 1) ? "ADMIN" : "TEACHER";
        String sql = "UPDATE Users SET full_name = ?, role = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getFullName());
            ps.setString(2, role);
            ps.setInt(3, u.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("🚨 LỖI UPDATE DATABASE: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public String deleteUser(int id) {
        String checkSql = "SELECT COUNT(*) FROM Classes WHERE teacher_id = ?";
        String delSql = "DELETE FROM Users WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            // Kiểm tra xem giáo viên này có đang dạy lớp nào không
            try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setInt(1, id);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return "foreign_key_error";
                }
            }
            try (PreparedStatement psDel = conn.prepareStatement(delSql)) {
                psDel.setInt(1, id);
                return psDel.executeUpdate() > 0 ? "success" : "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}

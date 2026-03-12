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
        String sql = "SELECT id, username, full_name, email, role_id FROM Users WHERE username = ? AND password = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, hashedPassword);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getInt("role_id")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi đăng nhập: " + e.getMessage());
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
        String sql = "SELECT * FROM Users ORDER BY role_id ASC, id DESC";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new User(rs.getInt("id"), rs.getString("username"),
                        rs.getString("full_name"), rs.getString("email"), rs.getInt("role_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("username"),
                            rs.getString("full_name"), rs.getString("email"), rs.getInt("role_id"));
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
        // Chỉ dùng đúng 5 cột cơ bản này
        String sql = "INSERT INTO Users (username, password, full_name, email, role_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, hashedDefaultPassword);
            ps.setString(3, u.getFullName());
            ps.setString(4, u.getEmail());
            ps.setInt(5, u.getRoleId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("🚨 LỖI INSERT DATABASE: " + e.getMessage()); // In lỗi ra NetBeans
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserByAdmin(User u) {
        String sql = "UPDATE Users SET full_name = ?, email = ?, role_id = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getFullName());
            ps.setString(2, u.getEmail());
            ps.setInt(3, u.getRoleId());
            ps.setInt(4, u.getId());
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

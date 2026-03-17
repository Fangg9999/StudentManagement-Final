package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Kiểm tra dữ liệu users trong database
 */
public class CheckUsers {

    public static void main(String[] args) {
        System.out.println("=== KIỂM TRA DỮ LIỆU USERS ===\n");

        try (Connection conn = DBContext.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT id, username, password, full_name, role FROM Users";
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("Các users trong database:");
            System.out.println("-------------------------------------------");
            
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("\nUser #" + count);
                System.out.println("  ID: " + rs.getInt("id"));
                System.out.println("  Username: " + rs.getString("username"));
                System.out.println("  Password (hash): " + rs.getString("password"));
                System.out.println("  Tên đầy đủ: " + rs.getString("full_name"));
                System.out.println("  Role: " + rs.getString("role"));
            }

            System.out.println("\n-------------------------------------------");
            System.out.println("Tổng cộng: " + count + " users");

            // Kiểm tra MD5 của "123456"
            System.out.println("\n=== KIỂM TRA MẬT KHẨU ===");
            String testPassword = "123456";
            String hashedPassword = SecurityUtil.hashMD5(testPassword);
            System.out.println("Password: " + testPassword);
            System.out.println("MD5 Hash: " + hashedPassword);

            rs.close();

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=== KẾT THÚC ===");
    }
}

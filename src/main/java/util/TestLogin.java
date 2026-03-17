package util;

import dao.UserDAO;
import model.User;

/**
 * Test đăng nhập sau khi sửa
 */
public class TestLogin {

    public static void main(String[] args) {
        System.out.println("=== TEST ĐĂNG NHẬP ===\n");

        UserDAO userDAO = new UserDAO();
        String username = "admin";
        String password = "123456";

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println();

        String hashedPassword = SecurityUtil.hashMD5(password);
        System.out.println("Hashed Password: " + hashedPassword);
        System.out.println();

        System.out.println("Attempting login...");
        User user = userDAO.checkLogin(username, hashedPassword);

        System.out.println();
        if (user != null) {
            System.out.println("✓ ĐĂNG NHẬP THÀNH CÔNG!");
            System.out.println("\nThông tin user:");
            System.out.println("  ID: " + user.getId());
            System.out.println("  Username: " + user.getUsername());
            System.out.println("  Full Name: " + user.getFullName());
            System.out.println("  Role ID: " + user.getRoleId());
            System.out.println("  Email: " + user.getEmail());
        } else {
            System.out.println("✗ ĐĂNG NHẬP THẤT BẠI!");
        }

        System.out.println("\n=== KẾT THÚC ===");
    }
}

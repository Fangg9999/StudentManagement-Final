package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DatabaseMetaData;

/**
 * Công cụ kiểm tra kết nối database
 */
public class DBConnectionTest {

    public static void main(String[] args) {
        System.out.println("=== BẮT ĐẦU KIỂM TRA KẾT NỐI DATABASE ===\n");

        // Test 1: Kiểm tra kết nối cơ bản
        testConnection();

        // Test 2: Kiểm tra thông tin database
        testDatabaseInfo();

        // Test 3: Kiểm tra các table
        testTables();

        // Test 4: Kiểm tra dữ liệu
        testData();

        System.out.println("\n=== KẾT THÚC KIỂM TRA ===");
    }

    private static void testConnection() {
        System.out.println("TEST 1: Kiểm tra kết nối");
        System.out.println("------------------------");
        try {
            Connection conn = DBContext.getConnection();
            if (conn != null) {
                System.out.println("✓ KẾT NỐI THÀNH CÔNG!");
                System.out.println("  - URL: " + conn.getMetaData().getURL());
                System.out.println("  - Driver: " + conn.getMetaData().getDriverName());
                System.out.println("  - Database: " + conn.getMetaData().getDatabaseProductName());
                conn.close();
            } else {
                System.out.println("✗ KHÔNG THỂ KẾT NỐI: conn = null");
            }
        } catch (Exception e) {
            System.out.println("✗ LỖI KẾT NỐI: " + e.getClass().getSimpleName());
            System.out.println("  Message: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void testDatabaseInfo() {
        System.out.println("TEST 2: Thông tin database");
        System.out.println("---------------------------");
        try (Connection conn = DBContext.getConnection()) {
            DatabaseMetaData metadata = conn.getMetaData();
            System.out.println("✓ Thông tin database:");
            System.out.println("  - Tên database: " + metadata.getDatabaseProductName());
            System.out.println("  - Phiên bản: " + metadata.getDatabaseProductVersion());
            System.out.println("  - Driver: " + metadata.getDriverName());
            System.out.println("  - Phiên bản driver: " + metadata.getDriverVersion());
        } catch (Exception e) {
            System.out.println("✗ LỖI: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testTables() {
        System.out.println("TEST 3: Kiểm tra các table");
        System.out.println("---------------------------");
        try (Connection conn = DBContext.getConnection()) {
            String[] types = {"TABLE"};
            var tables = conn.getMetaData().getTables(null, "dbo", "%", types);
            
            int count = 0;
            System.out.println("✓ Các table trong database:");
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("  - " + tableName);
                count++;
            }
            if (count == 0) {
                System.out.println("  (Không tìm thấy table nào)");
            } else {
                System.out.println("\nTổng cộng: " + count + " table");
            }
        } catch (Exception e) {
            System.out.println("✗ LỖI: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testData() {
        System.out.println("TEST 4: Kiểm tra dữ liệu");
        System.out.println("------------------------");
        String[] tables = {"Users", "Major", "Semester", "Classes", "Students", "Subjects", "Grades"};

        for (String table : tables) {
            try (Connection conn = DBContext.getConnection();
                 Statement stmt = conn.createStatement()) {
                
                String sql = "SELECT COUNT(*) as count FROM " + table;
                ResultSet rs = stmt.executeQuery(sql);
                
                if (rs.next()) {
                    int count = rs.getInt("count");
                    System.out.println("✓ " + table + ": " + count + " bản ghi");
                }
                rs.close();
            } catch (Exception e) {
                System.out.println("✗ " + table + ": " + e.getMessage());
            }
        }
        System.out.println();
    }
}

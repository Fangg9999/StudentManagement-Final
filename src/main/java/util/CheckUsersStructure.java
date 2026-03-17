package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;

/**
 * Kiểm tra cấu trúc table Users
 */
public class CheckUsersStructure {

    public static void main(String[] args) {
        System.out.println("=== KIỂM TRA CẤU TRÚC TABLE USERS ===\n");

        try (Connection conn = DBContext.getConnection()) {
            DatabaseMetaData metadata = conn.getMetaData();
            
            System.out.println("Các column trong table Users:");
            System.out.println("-------------------------------------------");
            
            ResultSet columns = metadata.getColumns(null, "dbo", "Users", null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String columnType = columns.getString("TYPE_NAME");
                int columnSize = columns.getInt("COLUMN_SIZE");
                String isNullable = columns.getString("IS_NULLABLE");
                
                System.out.println(columnName + " (" + columnType + (columnSize > 0 ? "(" + columnSize + ")" : "") + ") - Nullable: " + isNullable);
            }
            
            columns.close();

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=== KẾT THÚC ===");
    }
}

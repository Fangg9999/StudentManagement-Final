🎓 Hệ Thống Quản Lý Sinh Viên MVC (Jakarta EE)
Dự án Java Web xây dựng theo kiến trúc MVC chuẩn, sử dụng Servlet/JSP và JDBC.

⚠️ Môi trường BẮT BUỘC (Yêu cầu cài đúng phiên bản)
IDE: NetBeans 25 (Hoặc IntelliJ Ultimate/Eclipse Enterprise).
Java: JDK 11 hoặc mới hơn.
Web Server: Tomcat 10.1.x (Cực kỳ quan trọng: Dự án dùng thư viện jakarta.*, không chạy được trên Tomcat 9 trở xuống).
Database: SQL Server.
🚀 Hướng dẫn Cài đặt & Chạy dự án (Dành cho Team)
Bước 1: Thiết lập Cơ sở dữ liệu
Mở SQL Server Management Studio (SSMS).
Mở file database.sql đính kèm trong thư mục gốc của project.
Bấm Execute để tạo DB StudentManagementMVC, các bảng và sinh sẵn 30 dữ liệu mẫu.
Bước 2: Cấu hình Kết nối (DBContext)
Kéo code về máy bằng lệnh: git clone <URL_REPO_CỦA_TEAM>
Mở project bằng NetBeans.
Mở file src/main/java/util/DBContext.java.
Thay đổi thông tin USER (mặc định là sa) và PASS thành mật khẩu SQL Server trên máy của bạn:
private static final String USER = "sa";
private static final String PASS = "mat_khau_cua_ban";

# 🎓 Hệ Thống Quản Lý Sinh Viên MVC (Jakarta EE)

Dự án Java Web xây dựng theo kiến trúc MVC chuẩn, sử dụng Servlet/JSP và JDBC.

## ⚠️ Môi trường BẮT BUỘC (Yêu cầu cài đúng phiên bản)
* **IDE:** NetBeans 25 (Hoặc IntelliJ Ultimate/Eclipse Enterprise).
* **Java:** JDK 11 hoặc mới hơn.
* **Web Server:** Tomcat **10.1.x** (Cực kỳ quan trọng: Dự án dùng thư viện `jakarta.*`, không chạy được trên Tomcat 9 trở xuống).
* **Database:** SQL Server.

## 🚀 Hướng dẫn Cài đặt & Chạy dự án (Dành cho Team)

### Bước 1: Thiết lập Cơ sở dữ liệu
1. Mở SQL Server Management Studio (SSMS).
2. Mở file `database.sql` đính kèm trong thư mục gốc của project.
3. Bấm `Execute` để tạo DB `StudentManagementMVC`, các bảng và sinh sẵn 30 dữ liệu mẫu.

### Bước 2: Cấu hình Kết nối (DBContext)
1. Kéo code về máy bằng lệnh: `git clone <URL_REPO_CỦA_TEAM>`
2. Mở project bằng NetBeans.
3. Mở file `src/main/java/util/DBContext.java`.
4. Thay đổi thông tin `USER` (mặc định là `sa`) và `PASS` thành mật khẩu SQL Server trên máy của bạn:
   ```java
   private static final String USER = "sa";
   private static final String PASS = "mat_khau_cua_ban";

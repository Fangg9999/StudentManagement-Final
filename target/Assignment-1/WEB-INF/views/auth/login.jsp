<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đăng nhập - Quản lý Sinh viên</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f4f6f9;
                display: flex;
                align-items: center;
                height: 100vh;
            }
            .login-card {
                max-width: 400px;
                width: 100%;
                margin: auto;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                background: #fff;
            }
        </style>
    </head>
    <body>
        <div class="login-card">
            <h3 class="text-center mb-4">Hệ Thống Quản Lý</h3>

            <% String error = (String) request.getAttribute("errorMessage");
                if (error != null) {%>
            <div class="alert alert-danger" role="alert"><%= error%></div>
            <% }%>

            <form action="${pageContext.request.contextPath}/auth?action=login" method="POST">
                <div class="mb-3">
                    <label for="username" class="form-label">Tài khoản</label>
                    <input type="text" class="form-control" id="username" name="username" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Mật khẩu</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
            </form>
        </div>
    </body>
</html>
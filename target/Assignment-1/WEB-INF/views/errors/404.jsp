<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>404 - Không tìm thấy trang</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; height: 100vh; display: flex; align-items: center; justify-content: center; }
        .error-card { text-align: center; padding: 40px; background: white; border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
        .error-code { font-size: 80px; font-weight: bold; color: #6c757d; }
    </style>
</head>
<body>
    <div class="error-card">
        <div class="error-code">404</div>
        <h3 class="mb-3">Sorry ! Có vẻ bạn lạc đường rồi.</h3>
        <p class="text-muted mb-4">Trang bạn đang tìm kiếm không tồn tại, đã bị xóa hoặc bạn gõ sai địa chỉ URL.</p>
        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary px-4">🏠 Quay về Trang chủ</a>
    </div>
</body>
</html>
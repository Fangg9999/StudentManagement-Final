<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>500 - Lỗi Hệ Thống</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; height: 100vh; display: flex; align-items: center; justify-content: center; }
        .error-card { text-align: center; padding: 40px; background: white; border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); border-top: 5px solid #dc3545; }
        .error-code { font-size: 80px; font-weight: bold; color: #dc3545; }
    </style>
</head>
<body>
    <div class="error-card">
        <div class="error-code">500</div>
        <h3 class="mb-3">Hệ thống đang gặp sự cố!</h3>
        <p class="text-muted mb-4">Rất xin lỗi, máy chủ của chúng tôi đang quá tải hoặc gặp lỗi cấu hình.<br>Vui lòng thử lại sau ít phút.</p>
        <a href="${pageContext.request.contextPath}/home" class="btn btn-danger px-4">🔄 Thử lại trang chủ</a>
    </div>
</body>
</html>
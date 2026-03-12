<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Đổi Mật Khẩu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../layouts/sidebar.jsp" />
        <div class="p-4 flex-grow-1 bg-light">
            <h3 class="mb-4">🔒 Đổi Mật Khẩu</h3>

            <div class="card" style="max-width: 500px;">
                <div class="card-body">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/profile/change-password" method="POST">
                        <div class="mb-3">
                            <label class="form-label">Mật khẩu cũ</label>
                            <input type="password" name="oldPassword" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mật khẩu mới</label>
                            <input type="password" name="newPassword" class="form-control" required minlength="6">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Xác nhận mật khẩu mới</label>
                            <input type="password" name="confirmPassword" class="form-control" required minlength="6">
                        </div>
                        <button type="submit" class="btn btn-warning w-100">Cập Nhật Mật Khẩu</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/home">Quản Lý Sinh Viên</a>
        <div class="d-flex text-white align-items-center">
            <span class="me-3">Xin chào, <strong>${sessionScope.LOGIN_USER.fullName}</strong></span>
            <a href="${pageContext.request.contextPath}/auth?action=logout" class="btn btn-sm btn-light">Đăng xuất</a>
        </div>
    </div>
</nav>
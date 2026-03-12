<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Hồ Sơ Cá Nhân</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <jsp:include page="../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../layouts/sidebar.jsp" />
        
        <div class="p-4 flex-grow-1">
            <h3 class="mb-4">👤 Thông Tin Cá Nhân</h3>

            <c:if test="${param.msg == 'success'}">
                <div class="alert alert-success">Cập nhật hồ sơ thành công!</div>
            </c:if>
            <c:if test="${param.msg == 'error'}">
                <div class="alert alert-danger">Đã xảy ra lỗi khi cập nhật.</div>
            </c:if>

            <div class="row">
                <div class="col-md-7 mb-4">
                    <div class="card shadow-sm border-0">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0">Cập Nhật Thông Tin</h5>
                        </div>
                        <div class="card-body p-4">
                            <form action="${pageContext.request.contextPath}/profile" method="POST">
                                <div class="mb-3">
                                    <label class="form-label text-muted">Tên Đăng Nhập</label>
                                    <input type="text" class="form-control bg-light" value="${sessionScope.LOGIN_USER.username}" readonly>
                                    <small class="text-danger">Không thể thay đổi tên đăng nhập.</small>
                                </div>
                                
                                <div class="mb-3">
                                    <label class="form-label fw-bold">Họ và Tên</label>
                                    <input type="text" name="fullName" class="form-control" value="${sessionScope.LOGIN_USER.fullName}" required>
                                </div>
                                
                                <div class="mb-4">
                                    <label class="form-label fw-bold">Địa chỉ Email</label>
                                    <input type="email" name="email" class="form-control" value="${sessionScope.LOGIN_USER.email}">
                                </div>
                                
                                <button type="submit" class="btn btn-success fw-bold"><i class="bi bi-save me-1"></i> Lưu Thay Đổi</button>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="col-md-5">
                    <div class="card shadow-sm border-0 mb-4">
                        <div class="card-body">
                            <h5 class="card-title text-primary"><i class="bi bi-shield-lock me-2"></i>Bảo Mật Tài Khoản</h5>
                            <p class="text-muted">Nên thay đổi mật khẩu định kỳ để bảo vệ tài khoản của bạn.</p>
                            <a href="${pageContext.request.contextPath}/profile/change-password" class="btn btn-outline-primary w-100 fw-bold">
                                Đổi Mật Khẩu
                            </a>
                        </div>
                    </div>
                    
                    <div class="card shadow-sm border-0">
                        <div class="card-body text-center py-4">
                            <div class="display-1 text-secondary mb-2"><i class="bi bi-person-circle"></i></div>
                            <h5 class="fw-bold">${sessionScope.LOGIN_USER.fullName}</h5>
                            <span class="badge ${sessionScope.LOGIN_USER.roleId == 1 ? 'bg-danger' : 'bg-info'} fs-6">
                                ${sessionScope.LOGIN_USER.roleId == 1 ? 'ADMINISTRATOR' : 'TEACHER'}
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
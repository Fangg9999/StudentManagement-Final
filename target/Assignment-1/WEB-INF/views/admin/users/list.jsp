<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Quản lý Tài Khoản</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    </head>
    <body class="bg-light">
        <jsp:include page="../../layouts/header.jsp" />
        <div class="d-flex">
            <jsp:include page="../../layouts/sidebar.jsp" />
            <div class="p-4 flex-grow-1">
                <h3 class="mb-4">🔐 Quản lý Tài Khoản Cán bộ & Giảng viên</h3>

                <a href="${pageContext.request.contextPath}/admin/users?action=add" class="btn btn-success mb-3 fw-bold">
                    <i class="bi bi-person-plus-fill me-1"></i> Cấp Tài Khoản Mới
                </a>

                <c:if test="${param.msg == 'success'}"><div class="alert alert-success">Cập nhật tài khoản thành công!</div></c:if>
                <c:if test="${param.msg == 'add_success'}"><div class="alert alert-success">Tạo tài khoản thành công! Mật khẩu mặc định là <b>123456</b>.</div></c:if>
                <c:if test="${param.msg == 'deleted'}"><div class="alert alert-success">Đã xóa tài khoản!</div></c:if>
                <c:if test="${param.error == 'in_use'}">
                    <div class="alert alert-danger fw-bold"><i class="bi bi-shield-lock-fill me-2"></i>Không thể xóa: Giảng viên này đang được phân công giảng dạy!</div>
                </c:if>
                <c:if test="${param.error == 'self_delete'}">
                    <div class="alert alert-warning fw-bold">Hệ thống chặn: Bạn không thể tự xóa tài khoản của chính mình!</div>
                </c:if>
                <c:if test="${param.error == 'db_error'}">
                    <div class="alert alert-danger fw-bold"><i class="bi bi-x-circle-fill me-2"></i>Lỗi Database! Không thể lưu dữ liệu, vui lòng kiểm tra Console.</div>
                </c:if>

                <div class="card shadow-sm border-0">
                    <div class="card-body p-0">
                        <table class="table table-hover mb-0 align-middle">
                            <thead class="table-dark">
                                <tr>
                                    <th class="ps-4">Tên Đăng Nhập</th>
                                    <th>Họ và Tên</th>
                                    <th>Email</th>
                                    <th>Phân Quyền</th>
                                    <th class="text-center">Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="u" items="${users}">
                                    <tr>
                                        <td class="ps-4 fw-bold text-primary">${u.username}</td>
                                        <td>${u.fullName}</td>
                                        <td>${u.email}</td>
                                        <td>
                                            <span class="badge ${u.roleId == 1 ? 'bg-danger' : 'bg-info'} px-3 py-2">
                                                <i class="bi ${u.roleId == 1 ? 'bi-shield-check' : 'bi-person-video3'} me-1"></i>
                                                ${u.roleId == 1 ? 'Quản Trị Viên' : 'Giảng Viên'}
                                            </span>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/users?action=edit&id=${u.id}" class="btn btn-sm btn-warning">Sửa</a>
                                            <a href="${pageContext.request.contextPath}/admin/users?action=delete&id=${u.id}" 
                                               class="btn btn-sm btn-danger" 
                                               onclick="return confirm('Bạn có chắc chắn muốn xóa tài khoản ${u.username}?');">Xóa</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
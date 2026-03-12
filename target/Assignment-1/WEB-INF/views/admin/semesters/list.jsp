<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Quản lý Học Kỳ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <jsp:include page="../../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../../layouts/sidebar.jsp" />
        <div class="p-4 flex-grow-1">
            <h3 class="mb-4">📅 Danh sách Học Kỳ</h3>
            
            <a href="${pageContext.request.contextPath}/admin/semesters?action=add" class="btn btn-success mb-3 fw-bold">
                <i class="bi bi-plus-circle me-1"></i> Thêm Học Kỳ Mới
            </a>

            <c:if test="${param.msg == 'success'}">
                <div class="alert alert-success"><i class="bi bi-check-circle-fill me-2"></i>Lưu thông tin học kỳ thành công!</div>
            </c:if>
            <c:if test="${param.msg == 'deleted'}">
                <div class="alert alert-success"><i class="bi bi-trash-fill me-2"></i>Đã xóa học kỳ khỏi hệ thống!</div>
            </c:if>
            <c:if test="${param.error == 'in_use'}">
                <div class="alert alert-danger fw-bold"><i class="bi bi-shield-lock-fill me-2"></i>KHÔNG THỂ XÓA: Học kỳ này đang có lớp học hoạt động!</div>
            </c:if>

            <div class="card shadow-sm border-0">
                <div class="card-body p-0">
                    <table class="table table-hover mb-0 align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th class="ps-4">Mã Học Kỳ</th>
                                <th>Tên Học Kỳ</th>
                                <th class="text-center">Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${semesters}">
                                <tr>
                                    <td class="ps-4 fw-bold text-primary">${s.semesterCode}</td>
                                    <td><span class="badge bg-secondary fs-6">${s.semesterName}</span></td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/admin/semesters?action=edit&id=${s.id}" class="btn btn-sm btn-warning">
                                            <i class="bi bi-pencil-square"></i> Sửa
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/semesters?action=delete&id=${s.id}" 
                                           class="btn btn-sm btn-danger" 
                                           onclick="return confirm('Bạn có chắc chắn muốn xóa học kỳ ${s.semesterCode} này không?');">
                                            <i class="bi bi-trash"></i> Xóa
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty semesters}">
                                <tr>
                                    <td colspan="3" class="text-center py-4 text-muted">Chưa có học kỳ nào trong hệ thống.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            
        </div>
    </div>
</body>
</html>
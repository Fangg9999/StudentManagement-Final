<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Quản lý Môn Học</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <jsp:include page="../../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../../layouts/sidebar.jsp" />
        <div class="p-4 flex-grow-1">
            <h3 class="mb-4">📖 Danh sách Môn Học</h3>
            <a href="${pageContext.request.contextPath}/admin/subjects?action=add" class="btn btn-success mb-3 fw-bold">
                <i class="bi bi-plus-circle me-1"></i> Thêm Môn Học
            </a>

            <c:if test="${param.msg == 'success'}"><div class="alert alert-success">Lưu môn học thành công!</div></c:if>
            <c:if test="${param.msg == 'deleted'}"><div class="alert alert-success">Đã xóa môn học!</div></c:if>
            <c:if test="${param.error == 'in_use'}">
                <div class="alert alert-danger fw-bold"><i class="bi bi-shield-lock-fill me-2"></i>Không thể xóa: Môn học này đang được mở lớp giảng dạy!</div>
            </c:if>

            <div class="card shadow-sm border-0">
                <div class="card-body p-0">
                    <table class="table table-hover mb-0">
                        <thead class="table-dark">
                            <tr>
                                <th class="ps-4">Mã Môn</th>
                                <th>Tên Môn Học</th>
                                <th>Số Tín Chỉ</th>
                                <th class="text-center">Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${subjects}">
                                <tr>
                                    <td class="ps-4 fw-bold text-primary">${s.subjectCode}</td>
                                    <td>${s.subjectName}</td>
                                    <td><span class="badge bg-info text-dark">${s.credits} tín chỉ</span></td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/admin/subjects?action=edit&id=${s.id}" class="btn btn-sm btn-warning">Sửa</a>
                                        <a href="${pageContext.request.contextPath}/admin/subjects?action=delete&id=${s.id}" 
                                           class="btn btn-sm btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa môn học này?');">Xóa</a>
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
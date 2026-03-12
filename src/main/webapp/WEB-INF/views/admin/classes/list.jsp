<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Quản lý Lớp Học</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <jsp:include page="../../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../../layouts/sidebar.jsp" />
        <div class="p-4 flex-grow-1">
            <h3 class="mb-4">📚 Danh sách Lớp Học</h3>
            <a href="${pageContext.request.contextPath}/admin/classes?action=add" class="btn btn-success mb-3 fw-bold">
                <i class="bi bi-plus-circle me-1"></i> Tạo Lớp Mới
            </a>

            <c:if test="${param.msg == 'success'}"><div class="alert alert-success">Lưu lớp học thành công!</div></c:if>
            <c:if test="${param.msg == 'deleted'}"><div class="alert alert-success">Đã xóa lớp học (bao gồm sinh viên và điểm trong lớp)!</div></c:if>

            <div class="card shadow-sm border-0">
                <div class="card-body p-0">
                    <table class="table table-hover mb-0">
                        <thead class="table-dark">
                            <tr>
                                <th class="ps-4">Mã Lớp</th>
                                <th>Môn Học</th>
                                <th>Học Kỳ</th>
                                <th>Giáo Viên Phụ Trách</th>
                                <th class="text-center">Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="c" items="${classesList}">
                                <tr>
                                    <td class="ps-4 fw-bold text-primary">${c.class_code}</td>
                                    <td>${c.subject_name}</td>
                                    <td><span class="badge bg-secondary">${c.semester_name}</span></td>
                                    <td>${c.teacher_name}</td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/admin/class-students?classId=${c.id}" class="btn btn-sm btn-info text-white fw-bold me-2">
                                            <i class="bi bi-people-fill"></i> Quản lý Sinh Viên
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/classes?action=edit&id=${c.id}" class="btn btn-sm btn-warning">Sửa</a>
                                        <a href="${pageContext.request.contextPath}/admin/classes?action=delete&id=${c.id}" 
                                           class="btn btn-sm btn-danger" onclick="return confirm('CẢNH BÁO: Xóa lớp sẽ xóa TOÀN BỘ sinh viên và điểm số trong lớp này. Tiếp tục?');">Xóa</a>
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
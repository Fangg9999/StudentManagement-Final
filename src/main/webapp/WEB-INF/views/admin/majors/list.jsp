<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Quản lý Ngành Học</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="../../layouts/header.jsp" />
        <div class="d-flex">
            <jsp:include page="../../layouts/sidebar.jsp" />
            <div class="p-4 flex-grow-1 bg-light">
                <h3 class="mb-4">Danh sách Ngành Học</h3>
                <a href="${pageContext.request.contextPath}/admin/majors?action=add" class="btn btn-success mb-3">+ Thêm Ngành</a>

                <table class="table table-bordered table-hover bg-white">
                    <thead class="table-dark">
                        <tr><th>Mã Ngành</th><th>Tên Ngành</th><th>Hành động</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach var="m" items="${majors}">
                            <tr>
                                <td>${m.majorCode}</td>
                                <td>${m.majorName}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/majors?action=delete&id=${m.id}" 
                                       class="btn btn-sm btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa?');">Xóa</a>
                                    <a href="${pageContext.request.contextPath}/admin/majors?action=edit&id=${m.id}" class="btn btn-sm btn-primary">Sửa</a>
                                </td>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Quản lý Sinh viên</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="../../layouts/header.jsp" />
        <div class="d-flex">
            <jsp:include page="../../layouts/sidebar.jsp" />
            <div class="p-4 flex-grow-1 bg-light">
                <h3 class="mb-4">Danh sách Sinh viên</h3>

                <div class="d-flex justify-content-between mb-3">
                    <a href="${pageContext.request.contextPath}/admin/students?action=add" class="btn btn-success">+ Thêm Sinh viên</a>

                    <form action="${pageContext.request.contextPath}/admin/students" method="GET" class="d-flex w-50">
                        <input type="text" name="keyword" value="${keyword}" class="form-control me-2" placeholder="Tìm theo Mã SV, Họ tên, Lớp...">
                        <button type="submit" class="btn btn-primary">Tìm</button>
                        <c:if test="${not empty keyword}">
                            <a href="${pageContext.request.contextPath}/admin/students" class="btn btn-secondary ms-2">Xóa</a>
                        </c:if>
                    </form>
                </div>

                <table class="table table-bordered table-hover bg-white">
                    <thead class="table-dark">
                        <tr>
                            <th>Mã SV</th>
                            <th>Họ tên</th>
                            <th>Ngày sinh</th>
                            <th>Lớp (Khóa)</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${students}">
                            <tr>
                                <td>${s.studentCode}</td>
                                <td>${s.fullName}</td>
                                <td>${s.dob}</td>
                                <td>${s.homeroomClass}</td>
                                <td>
                                    <button class="btn btn-sm btn-danger" onclick="confirmDelete(${s.id}, '${s.fullName}')">Xóa</button>
                                    <a href="${pageContext.request.contextPath}/admin/students?action=edit&id=${s.id}" class="btn btn-sm btn-primary">Sửa</a>
                                </td>
                                    

                            </tr>
                        </c:forEach>
                        <c:if test="${empty students}">
                            <tr><td colspan="5" class="text-center">Không tìm thấy sinh viên nào.</td></tr>
                        </c:if>
                    </tbody>
                </table>

                <c:if test="${totalPages > 1}">
                    <nav>
                        <ul class="pagination justify-content-center">
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/admin/students?page=${i}&keyword=${keyword}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </c:if>
            </div>
        </div>

        <script>
            function confirmDelete(id, name) {
                if (confirm('Bạn có chắc chắn muốn xóa sinh viên: ' + name + '? Dữ liệu điểm liên quan sẽ bị ảnh hưởng!')) {
                    window.location.href = '${pageContext.request.contextPath}/admin/students?action=delete&id=' + id;
                }
            }
        </script>
    </body>
</html>
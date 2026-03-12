<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Thêm/Sửa Ngành Học</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../../layouts/sidebar.jsp" />
        <div class="p-4 flex-grow-1 bg-light">
            <h3 class="mb-4">${actionType == 'edit' ? 'Sửa' : 'Thêm'} Ngành Học</h3>

            <form action="${pageContext.request.contextPath}/admin/majors" method="POST" class="card p-4" style="max-width: 600px;">
                <input type="hidden" name="actionType" value="${actionType}">
                <c:if test="${actionType == 'edit'}">
                    <input type="hidden" name="id" value="${major.id}">
                </c:if>

                <div class="mb-3">
                    <label class="form-label">Mã Ngành *</label>
                    <input type="text" name="majorCode" class="form-control" value="${major.majorCode}" required ${actionType == 'edit' ? 'readonly bg-light' : ''}>
                </div>
                <div class="mb-3">
                    <label class="form-label">Tên Ngành *</label>
                    <input type="text" name="majorName" class="form-control" value="${major.majorName}" required>
                </div>
                <div>
                    <button type="submit" class="btn btn-primary">Lưu dữ liệu</button>
                    <a href="${pageContext.request.contextPath}/admin/majors" class="btn btn-secondary">Hủy</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
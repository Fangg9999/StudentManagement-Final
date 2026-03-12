<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>${actionType == 'edit' ? 'Sửa' : 'Thêm'} Môn Học</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <jsp:include page="../../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../../layouts/sidebar.jsp" />
        <div class="p-4 flex-grow-1">
            <h3 class="mb-4">${actionType == 'edit' ? 'Cập Nhật' : 'Thêm Mới'} Môn Học</h3>

            <div class="card shadow-sm border-0" style="max-width: 500px;">
                <div class="card-body p-4">
                    <form action="${pageContext.request.contextPath}/admin/subjects" method="POST">
                        <input type="hidden" name="actionType" value="${actionType}">
                        <c:if test="${actionType == 'edit'}">
                            <input type="hidden" name="id" value="${subject.id}">
                        </c:if>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Mã Môn Học *</label>
                            <input type="text" name="subjectCode" class="form-control" value="${subject.subjectCode}" 
                                   required placeholder="VD: PRJ301" ${actionType == 'edit' ? 'readonly bg-light' : ''}>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Tên Môn Học *</label>
                            <input type="text" name="subjectName" class="form-control" value="${subject.subjectName}" 
                                   required placeholder="VD: Java Web Development">
                        </div>

                        <div class="mb-4">
                            <label class="form-label fw-bold">Số Tín Chỉ *</label>
                            <input type="number" name="credits" class="form-control" value="${subject.credits != null ? subject.credits : 3}" 
                                   required min="1" max="10">
                        </div>

                        <hr class="my-4">
                        <button type="submit" class="btn btn-primary px-4 fw-bold">Lưu Thông Tin</button>
                        <a href="${pageContext.request.contextPath}/admin/subjects" class="btn btn-secondary">Hủy Bỏ</a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
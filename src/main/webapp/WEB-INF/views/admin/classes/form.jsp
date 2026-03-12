<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>${actionType == 'edit' ? 'Sửa' : 'Thêm'} Lớp Học</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <jsp:include page="../../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../../layouts/sidebar.jsp" />
        <div class="p-4 flex-grow-1">
            <h3 class="mb-4">${actionType == 'edit' ? 'Cập Nhật' : 'Tạo Mới'} Lớp Học</h3>

            <div class="card shadow-sm border-0" style="max-width: 600px;">
                <div class="card-body p-4">
                    <form action="${pageContext.request.contextPath}/admin/classes" method="POST">
                        <input type="hidden" name="actionType" value="${actionType}">
                        <c:if test="${actionType == 'edit'}">
                            <input type="hidden" name="id" value="${classRoom.id}">
                        </c:if>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Mã Lớp Học *</label>
                            <input type="text" name="classCode" class="form-control" value="${classRoom.classCode}" 
                                   required placeholder="VD: PRJ301_SE1801" ${actionType == 'edit' ? 'readonly bg-light' : ''}>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Môn Học *</label>
                            <select name="subjectId" class="form-select" required>
                                <c:forEach var="sub" items="${subjects}">
                                    <option value="${sub.id}" ${classRoom.subjectId == sub.id ? 'selected' : ''}>${sub.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Học Kỳ *</label>
                            <select name="semesterId" class="form-select" required>
                                <c:forEach var="sem" items="${semesters}">
                                    <option value="${sem.id}" ${classRoom.semesterId == sem.id ? 'selected' : ''}>${sem.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Giáo Viên Phụ Trách *</label>
                            <select name="teacherId" class="form-select" required>
                                <c:forEach var="t" items="${teachers}">
                                    <option value="${t.id}" ${classRoom.teacherId == t.id ? 'selected' : ''}>${t.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <hr class="my-4">
                        <button type="submit" class="btn btn-primary px-4 fw-bold">Lưu Thông Tin</button>
                        <a href="${pageContext.request.contextPath}/admin/classes" class="btn btn-secondary">Hủy Bỏ</a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>${actionType == 'edit' ? 'Sửa' : 'Thêm'} Học Kỳ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <jsp:include page="../../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../../layouts/sidebar.jsp" />
        <div class="p-4 flex-grow-1">
            <h3 class="mb-4">${actionType == 'edit' ? 'Cập Nhật' : 'Thêm Mới'} Học Kỳ</h3>

            <div class="card shadow-sm border-0" style="max-width: 500px;">
                <div class="card-body p-4">
                    <form action="${pageContext.request.contextPath}/admin/semesters" method="POST">
                        <input type="hidden" name="actionType" value="${actionType}">
                        <c:if test="${actionType == 'edit'}">
                            <input type="hidden" name="id" value="${semester.id}">
                        </c:if>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Mã Học Kỳ <span class="text-danger">*</span></label>
                            <input type="text" name="semesterCode" class="form-control" 
                                   value="${semester.semesterCode}" 
                                   required placeholder="VD: FA26" 
                                   ${actionType == 'edit' ? 'readonly tabindex="-1" style="background-color: #e9ecef;"' : ''}>
                            <c:if test="${actionType == 'edit'}">
                                <small class="text-danger">Không được phép sửa mã học kỳ.</small>
                            </c:if>
                        </div>

                        <div class="mb-4">
                            <label class="form-label fw-bold">Tên Học Kỳ <span class="text-danger">*</span></label>
                            <input type="text" name="semesterName" class="form-control" 
                                   value="${semester.semesterName}" 
                                   required placeholder="VD: Fall 2026">
                        </div>

                        <hr class="my-4">
                        <div class="d-flex justify-content-end gap-2">
                            <a href="${pageContext.request.contextPath}/admin/semesters" class="btn btn-light border">Hủy Bỏ</a>
                            <button type="submit" class="btn btn-primary px-4 fw-bold">
                                <i class="bi bi-floppy me-1"></i> Lưu Thông Tin
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            
        </div>
    </div>
</body>
</html>
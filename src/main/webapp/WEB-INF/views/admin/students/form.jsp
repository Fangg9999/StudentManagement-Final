<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${actionType == 'edit' ? 'Sửa Thông Tin' : 'Thêm Mới'} Sinh Viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        .form-label { font-weight: 500; color: #495057; }
        .required-asterisk { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <jsp:include page="../../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../../layouts/sidebar.jsp" />
        
        <div class="p-4 flex-grow-1 bg-light">
            <div class="container-fluid">
                <div class="mb-3">
                    <a href="${pageContext.request.contextPath}/admin/students" class="btn btn-outline-secondary btn-sm">
                        <i class="bi bi-arrow-left"></i> Quay lại danh sách
                    </a>
                </div>

                <div class="row justify-content-center">
                    <div class="col-lg-8">
                        <div class="card shadow-sm border-0 rounded-3">
                            <div class="card-header ${actionType == 'edit' ? 'bg-warning text-dark' : 'bg-success text-white'} p-3 rounded-top-3">
                                <h4 class="mb-0">
                                    <i class="bi ${actionType == 'edit' ? 'bi-pencil-square' : 'bi-person-plus'} me-2"></i>
                                    ${actionType == 'edit' ? 'Cập Nhật Thông Tin Sinh Viên' : 'Thêm Sinh Viên Mới'}
                                </h4>
                            </div>
                            
                            <div class="card-body p-4">
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger d-flex align-items-center" role="alert">
                                        <i class="bi bi-exclamation-triangle-fill me-2"></i>
                                        <div>${error}</div>
                                    </div>
                                </c:if>

                                <form action="${pageContext.request.contextPath}/admin/students" method="POST">
                                    <input type="hidden" name="actionType" value="${actionType}">
                                    <c:if test="${actionType == 'edit'}">
                                        <input type="hidden" name="id" value="${student.id}">
                                    </c:if>

                                    <div class="row g-3">
                                        <div class="col-md-6">
                                            <label class="form-label">Mã Sinh Viên <span class="required-asterisk">*</span></label>
                                            <input type="text" name="studentCode" class="form-control ${actionType == 'edit' ? 'bg-light text-muted' : ''}" 
                                                   value="${actionType == 'edit' ? student.studentCode : studentCode}" 
                                                   required pattern="[a-zA-Z0-9]+" 
                                                   title="Mã SV không chứa khoảng trắng hoặc ký tự đặc biệt"
                                                   ${actionType == 'edit' ? 'readonly tabindex="-1"' : 'placeholder="VD: SV001"'}>
                                            <c:if test="${actionType == 'edit'}">
                                                <small class="text-danger">Mã sinh viên không được phép thay đổi.</small>
                                            </c:if>
                                        </div>
                                        
                                        <div class="col-md-6">
                                            <label class="form-label">Họ và Tên <span class="required-asterisk">*</span></label>
                                            <input type="text" name="fullName" class="form-control" 
                                                   value="${actionType == 'edit' ? student.fullName : fullName}" 
                                                   required placeholder="Nhập đầy đủ họ tên">
                                        </div>

                                        <div class="col-md-6">
                                            <label class="form-label">Ngày Sinh <span class="required-asterisk">*</span></label>
                                            <input type="date" name="dob" class="form-control" 
                                                   value="${student.dob}" required>
                                        </div>
                                        
                                        <div class="col-md-6">
                                            <label class="form-label">Ngày Nhập Học <span class="required-asterisk">*</span></label>
                                            <input type="date" name="enrollmentDate" class="form-control" 
                                                   value="${student.enrollmentDate}" required>
                                        </div>

                                        <div class="col-md-6">
                                            <label class="form-label">Lớp / Khóa <span class="required-asterisk">*</span></label>
                                            <input type="text" name="homeroomClass" class="form-control" 
                                                   value="${actionType == 'edit' ? student.homeroomClass : homeroomClass}" 
                                                   required placeholder="VD: IT-K61">
                                        </div>
                                        
                                        <div class="col-md-6">
                                            <label class="form-label">Ngành Học <span class="required-asterisk">*</span></label>
                                            <select name="majorId" class="form-select" required>
                                                <option value="1" ${student.majorId == 1 ? 'selected' : ''}>Công nghệ thông tin</option>
                                                <option value="2" ${student.majorId == 2 ? 'selected' : ''}>Marketing</option>
                                                <option value="3" ${student.majorId == 3 ? 'selected' : ''}>Thiết kế đồ họa</option>
                                            </select>
                                        </div>
                                    </div>

                                    <hr class="my-4">
                                    
                                    <div class="d-flex justify-content-end gap-2">
                                        <a href="${pageContext.request.contextPath}/admin/students" class="btn btn-light border">Hủy Bỏ</a>
                                        <button type="submit" class="btn ${actionType == 'edit' ? 'btn-warning text-dark' : 'btn-success'} px-4 fw-bold">
                                            <i class="bi bi-floppy me-1"></i>
                                            ${actionType == 'edit' ? 'Lưu Thay Đổi' : 'Tạo Sinh Viên'}
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
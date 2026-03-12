<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>${actionType == 'edit' ? 'Sửa' : 'Thêm'} Tài Khoản</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <jsp:include page="../../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../../layouts/sidebar.jsp" />
        <div class="p-4 flex-grow-1">
            <h3 class="mb-4">${actionType == 'edit' ? 'Cập Nhật Thông Tin' : 'Cấp Tài Khoản Mới'}</h3>

            <div class="card shadow-sm border-0" style="max-width: 600px;">
                <div class="card-body p-4">
                    
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger"><i class="bi bi-exclamation-triangle-fill me-2"></i>${error}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/admin/users" method="POST">
                        <input type="hidden" name="actionType" value="${actionType}">
                        <c:if test="${actionType == 'edit'}">
                            <input type="hidden" name="id" value="${userObj.id}">
                        </c:if>

                        <div class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Tên Đăng Nhập <span class="text-danger">*</span></label>
                                <input type="text" name="username" class="form-control" value="${userObj.username}" 
                                       required pattern="[a-zA-Z0-9]+" title="Chỉ chứa chữ và số, không khoảng trắng"
                                       ${actionType == 'edit' ? 'readonly style="background-color: #e9ecef;"' : 'placeholder="VD: teacher3"'}>
                                <c:if test="${actionType == 'add'}">
                                    <small class="text-muted fst-italic">Mật khẩu mặc định sẽ là: <b>123456</b></small>
                                </c:if>
                            </div>
                            
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Phân Quyền <span class="text-danger">*</span></label>
                                <select name="roleId" class="form-select" required>
                                    <option value="2" ${userObj.roleId == 2 ? 'selected' : ''}>Giảng Viên (Teacher)</option>
                                    <option value="1" ${userObj.roleId == 1 ? 'selected' : ''}>Quản Trị Viên (Admin)</option>
                                </select>
                            </div>

                            <div class="col-md-12">
                                <label class="form-label fw-bold">Họ và Tên <span class="text-danger">*</span></label>
                                <input type="text" name="fullName" class="form-control" value="${userObj.fullName}" required placeholder="VD: Nguyễn Văn A">
                            </div>

                            <div class="col-md-12">
                                <label class="form-label fw-bold">Email</label>
                                <input type="email" name="email" class="form-control" value="${userObj.email}" placeholder="VD: annguyen@edu.vn">
                            </div>
                        </div>

                        <hr class="my-4">
                        <div class="d-flex justify-content-end gap-2">
                            <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-light border">Hủy Bỏ</a>
                            <button type="submit" class="btn btn-primary px-4 fw-bold">
                                <i class="bi bi-floppy me-1"></i> Lưu Tài Khoản
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            
        </div>
    </div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Nhập Điểm Sinh Viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        /* CSS làm nổi bật ô nhập điểm khi focus để Giáo viên dễ nhìn */
        .score-input { max-width: 120px; font-weight: bold; text-align: center; }
        .score-input:focus { border-color: #0d6efd; box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25); }
        /* Ẩn mũi tên lên xuống mặc định của input number cho đẹp */
        input[type=number]::-webkit-inner-spin-button, 
        input[type=number]::-webkit-outer-spin-button { -webkit-appearance: none; margin: 0; }
    </style>
</head>
<body class="bg-light">
    <jsp:include page="../../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../../layouts/sidebar.jsp" />
        
        <div class="p-4 flex-grow-1">
            <a href="${pageContext.request.contextPath}/teacher/my-classes" class="btn btn-sm btn-outline-secondary mb-3">
                <i class="bi bi-arrow-left"></i> Quay lại Danh sách Lớp
            </a>

            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3>📝 Bảng Điểm Lớp Học</h3>
                <span class="badge bg-primary fs-6">Sĩ số: ${studentList.size()}</span>
            </div>

            <c:if test="${param.msg == 'success'}">
                <div class="alert alert-success fw-bold">
                    <i class="bi bi-check-circle-fill me-2"></i> Đã lưu bảng điểm thành công!
                </div>
            </c:if>
            <c:if test="${param.msg == 'error'}">
                <div class="alert alert-danger fw-bold">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i> Có lỗi xảy ra khi lưu điểm!
                </div>
            </c:if>

            <div class="card shadow-sm border-0">
                <div class="card-body p-0">
                    <form action="${pageContext.request.contextPath}/teacher/grades/entry" method="POST">
                        <input type="hidden" name="classId" value="${classId}">
                        <input type="hidden" name="subjectId" value="${subjectId}">

                        <table class="table table-hover table-striped mb-0 align-middle">
                            <thead class="table-dark">
                                <tr>
                                    <th class="ps-4" style="width: 5%;">STT</th>
                                    <th style="width: 20%;">Mã Sinh Viên</th>
                                    <th style="width: 40%;">Họ và Tên</th>
                                    <th class="text-center" style="width: 35%;">Điểm Tổng Kết (0 - 10)</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="s" items="${studentList}" varStatus="loop">
                                    <tr>
                                        <td class="ps-4 text-muted">${loop.index + 1}</td>
                                        <td class="fw-bold text-primary">${s.student_code}</td>
                                        <td>${s.full_name}</td>
                                        <td class="text-center d-flex justify-content-center">
                                            
                                            <input type="hidden" name="studentIds" value="${s.student_id}">
                                            
                                            <input type="number" name="scores" value="${s.score}" 
                                                   class="form-control score-input" 
                                                   step="0.1" min="0" max="10" 
                                                   placeholder="Trống">
                                        </td>
                                    </tr>
                                </c:forEach>
                                
                                <c:if test="${empty studentList}">
                                    <tr>
                                        <td colspan="4" class="text-center py-5 text-muted">
                                            <i class="bi bi-folder-x fs-1 d-block mb-2"></i>
                                            Lớp học này chưa có sinh viên nào. Vui lòng liên hệ Admin để thêm sinh viên.
                                        </td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>

                        <c:if test="${not empty studentList}">
                            <div class="p-3 bg-white border-top text-end">
                                <button type="submit" class="btn btn-success px-5 fw-bold btn-lg shadow-sm">
                                    <i class="bi bi-floppy-fill me-2"></i> Lưu Bảng Điểm
                                </button>
                            </div>
                        </c:if>
                    </form>
                </div>
            </div>

        </div>
    </div>
</body>
</html>
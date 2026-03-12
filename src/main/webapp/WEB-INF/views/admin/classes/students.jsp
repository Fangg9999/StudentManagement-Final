<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Sinh viên trong Lớp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <jsp:include page="../../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../../layouts/sidebar.jsp" />
        
        <div class="p-4 flex-grow-1">
            <a href="${pageContext.request.contextPath}/admin/classes" class="btn btn-sm btn-outline-secondary mb-3">
                <i class="bi bi-arrow-left"></i> Quay lại Danh sách Lớp
            </a>

            <h3 class="mb-4">🎓 Quản lý Danh sách Sinh viên (Lớp ID: ${classId})</h3>

            <c:if test="${param.msg == 'success'}">
                <div class="alert alert-success"><i class="bi bi-check-circle-fill me-2"></i>Thêm sinh viên thành công!</div>
            </c:if>
            <c:if test="${param.msg == 'deleted'}">
                <div class="alert alert-success"><i class="bi bi-check-circle-fill me-2"></i>Đã xóa sinh viên khỏi lớp!</div>
            </c:if>
            <c:if test="${param.error == 'not_found'}">
                <div class="alert alert-danger"><i class="bi bi-exclamation-triangle-fill me-2"></i>Lỗi: Mã Sinh Viên không tồn tại trong hệ thống!</div>
            </c:if>
            <c:if test="${param.error == 'exists'}">
                <div class="alert alert-warning"><i class="bi bi-exclamation-triangle-fill me-2"></i>Cảnh báo: Sinh viên này đã có trong lớp rồi!</div>
            </c:if>
            <c:if test="${param.error == 'graded'}">
                <div class="alert alert-danger fw-bold"><i class="bi bi-shield-lock-fill me-2"></i>KHÓA AN TOÀN: Không thể xóa sinh viên đã có điểm thi!</div>
            </c:if>
            <c:if test="${param.error == 'empty'}">
                <div class="alert alert-warning">Vui lòng nhập Mã Sinh Viên!</div>
            </c:if>

            <div class="row">
                <div class="col-md-4 mb-4">
                    <div class="card shadow-sm border-0">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0"><i class="bi bi-person-plus-fill me-2"></i>Thêm Sinh Viên</h5>
                        </div>
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/admin/class-students" method="POST">
                                <input type="hidden" name="classId" value="${classId}">
                                <div class="mb-3">
                                    <label class="form-label text-muted small fw-bold">NHẬP MÃ SINH VIÊN</label>
                                    <input type="text" name="studentCode" class="form-control form-control-lg" 
                                           placeholder="VD: SE15001" required autofocus>
                                </div>
                                <button type="submit" class="btn btn-primary w-100 fw-bold">
                                    <i class="bi bi-plus-circle me-1"></i> Đưa vào Lớp
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="col-md-8">
                    <div class="card shadow-sm border-0">
                        <div class="card-body p-0">
                            <table class="table table-hover mb-0">
                                <thead class="table-dark">
                                    <tr>
                                        <th class="ps-4">Mã SV</th>
                                        <th>Họ và Tên</th>
                                        <th class="text-center">Tình trạng Điểm</th>
                                        <th class="text-center">Hành động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="s" items="${students}">
                                        <tr>
                                            <td class="ps-4 fw-bold">${s.student_code}</td>
                                            <td>${s.full_name}</td>
                                            <td class="text-center">
                                                <c:choose>
                                                    <c:when test="${s.score == null}">
                                                        <span class="badge bg-secondary">Chưa có điểm</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-success fs-6">${s.score}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="text-center">
                                                <a href="${pageContext.request.contextPath}/admin/class-students?action=delete&classId=${classId}&studentId=${s.student_id}" 
                                                   class="btn btn-sm btn-outline-danger"
                                                   onclick="return confirm('Bạn có chắc chắn muốn xóa sinh viên ${s.student_code} khỏi lớp?');">
                                                    <i class="bi bi-trash"></i> Xóa
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty students}">
                                        <tr>
                                            <td colspan="4" class="text-center py-4 text-muted">Lớp học này hiện chưa có sinh viên nào.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div> </div>
    </div>
</body>
</html>
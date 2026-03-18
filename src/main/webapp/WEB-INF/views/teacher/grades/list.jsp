<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Quản lý Điểm số</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        .class-card { transition: transform 0.2s; border-left: 5px solid #17a2b8; }
        .class-card:hover { transform: translateY(-5px); box-shadow: 0 10px 20px rgba(0,0,0,0.1) !important; }
    </style>
</head>
<body class="bg-light">
    <jsp:include page="../../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../../layouts/sidebar.jsp" />
        
        <div class="p-4 flex-grow-1">
            <h3 class="mb-4">📝 Quản lý Điểm số Lớp</h3>

            <div class="row">
                <c:forEach var="c" items="${classList}">
                    <div class="col-md-4 mb-4">
                        <div class="card shadow-sm class-card h-100">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center mb-2">
                                    <h5 class="card-title fw-bold text-info mb-0">${c.class_code}</h5>
                                    <span class="badge bg-secondary">${c.semester_name}</span>
                                </div>
                                <h6 class="card-subtitle mb-3 text-muted">${c.subject_name}</h6>
                                
                                <p class="card-text mb-4">
                                    <i class="bi bi-people-fill text-success me-2"></i>Sĩ số: <strong>${c.student_count}</strong> sinh viên
                                </p>
                                
                                <c:choose>
                                    <c:when test="${c.student_count > 0}">
                                        <a href="${pageContext.request.contextPath}/teacher/grades/entry?classId=${c.class_id}&subjectId=${c.subject_id}" 
                                           class="btn btn-info w-100 fw-bold">
                                            <i class="bi bi-pencil-square me-2"></i> Nhập / Sửa Điểm
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-outline-secondary w-100" disabled>
                                            <i class="bi bi-lock-fill me-2"></i> Lớp chưa có sinh viên
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                
                <c:if test="${empty classList}">
                    <div class="col-12 text-center text-muted mt-5">
                        <i class="bi bi-calendar-x" style="font-size: 3rem;"></i>
                        <h5 class="mt-3">Thầy/Cô chưa được phân công giảng dạy lớp nào trong học kỳ này.</h5>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    <jsp:include page="../../layouts/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

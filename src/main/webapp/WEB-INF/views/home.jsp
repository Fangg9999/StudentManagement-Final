<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ - Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        .stat-card { border: none; border-radius: 12px; transition: transform 0.3s ease, box-shadow 0.3s ease; color: white; overflow: hidden; }
        .stat-card:hover { transform: translateY(-5px); box-shadow: 0 10px 20px rgba(0,0,0,0.15); }
        .icon-bg { position: absolute; right: -20px; bottom: -20px; font-size: 8rem; opacity: 0.15; transform: rotate(-15deg); }
        
        /* Bảng màu Gradient hiện đại */
        .bg-grad-1 { background: linear-gradient(135deg, #4e54c8, #8f94fb); }
        .bg-grad-2 { background: linear-gradient(135deg, #11998e, #38ef7d); }
        .bg-grad-3 { background: linear-gradient(135deg, #f12711, #f5af19); }
        .bg-grad-4 { background: linear-gradient(135deg, #b224ef, #7579ff); }
    </style>
</head>
<body class="bg-light">
    <jsp:include page="layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="layouts/sidebar.jsp" />
        
        <div class="p-4 flex-grow-1">
            
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 class="fw-bold text-dark">👋 Xin chào, ${sessionScope.LOGIN_USER.fullName}!</h2>
                    <p class="text-muted">Chào mừng bạn trở lại hệ thống Quản lý Sinh viên.</p>
                </div>
                <div>
                    <span class="badge ${sessionScope.LOGIN_USER.roleId == 1 ? 'bg-danger' : 'bg-primary'} fs-6 px-3 py-2 rounded-pill">
                        <i class="bi ${sessionScope.LOGIN_USER.roleId == 1 ? 'bi-shield-lock' : 'bi-person-workspace'} me-1"></i>
                        Quyền: ${sessionScope.LOGIN_USER.roleId == 1 ? 'Quản Trị Viên' : 'Giảng Viên'}
                    </span>
                </div>
            </div>

            <c:if test="${sessionScope.LOGIN_USER.roleId == 1}">
                <div class="row g-4 mb-5">
                    <div class="col-md-3">
                        <div class="card stat-card bg-grad-1 h-100 p-3 position-relative">
                            <i class="bi bi-people-fill icon-bg"></i>
                            <div class="card-body">
                                <h6 class="text-uppercase fw-bold mb-3 opacity-75">Tổng Sinh Viên</h6>
                                <h1 class="display-4 fw-bold mb-0">${stats.students}</h1>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="card stat-card bg-grad-2 h-100 p-3 position-relative">
                            <i class="bi bi-bank icon-bg"></i>
                            <div class="card-body">
                                <h6 class="text-uppercase fw-bold mb-3 opacity-75">Lớp Học Đang Mở</h6>
                                <h1 class="display-4 fw-bold mb-0">${stats.classes}</h1>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="card stat-card bg-grad-3 h-100 p-3 position-relative">
                            <i class="bi bi-journal-album icon-bg"></i>
                            <div class="card-body">
                                <h6 class="text-uppercase fw-bold mb-3 opacity-75">Môn Học (Khung)</h6>
                                <h1 class="display-4 fw-bold mb-0">${stats.subjects}</h1>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="card stat-card bg-grad-4 h-100 p-3 position-relative">
                            <i class="bi bi-person-badge-fill icon-bg"></i>
                            <div class="card-body">
                                <h6 class="text-uppercase fw-bold mb-3 opacity-75">Giảng Viên</h6>
                                <h1 class="display-4 fw-bold mb-0">${stats.teachers}</h1>
                            </div>
                        </div>
                    </div>
                </div>
                
                <h5 class="fw-bold mb-3 text-secondary">Phím Tắt Nhanh</h5>
                <div class="d-flex gap-3">
                    <a href="${pageContext.request.contextPath}/admin/students?action=add" class="btn btn-outline-primary fw-bold shadow-sm">
                        <i class="bi bi-person-plus me-1"></i> Thêm Sinh viên
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/classes?action=add" class="btn btn-outline-success fw-bold shadow-sm">
                        <i class="bi bi-plus-square me-1"></i> Mở Lớp mới
                    </a>
                </div>
            </c:if>

            <c:if test="${sessionScope.LOGIN_USER.roleId == 2}">
                <div class="row mt-4">
                    <div class="col-md-8 offset-md-2 text-center">
                        <div style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 12px; padding: 40px; margin-bottom: 20px;">
                            <i class="bi bi-mortarboard-fill" style="font-size: 5rem; color: white; opacity: 0.9;"></i>
                        </div>
                        <h4 class="text-primary mb-3">Sẵn sàng cho một ngày giảng dạy hiệu quả!</h4>
                        <p class="text-muted mb-4">Hệ thống đã cập nhật danh sách lớp và sinh viên mới nhất. Thầy/Cô có thể bắt đầu quản lý điểm số và xem báo cáo ngay.</p>
                        
                        <div class="d-flex justify-content-center gap-3">
                            <a href="${pageContext.request.contextPath}/teacher/my-classes" class="btn btn-primary btn-lg fw-bold px-4 shadow-sm">
                                <i class="bi bi-journal-bookmark me-2"></i> Vào Lớp Của Tôi
                            </a>
                            <a href="${pageContext.request.contextPath}/teacher/reports" class="btn btn-outline-secondary btn-lg fw-bold px-4 shadow-sm">
                                <i class="bi bi-bar-chart-fill me-2"></i> Xem Thống Kê Điểm
                            </a>
                        </div>
                    </div>
                </div>
            </c:if>

        </div>
    </div>
</body>
</html>
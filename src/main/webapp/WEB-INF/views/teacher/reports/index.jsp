<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Báo Cáo Học Tập</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        .stat-card {
            border: none;
            border-radius: 15px;
            transition: all 0.3s ease;
            padding: 25px;
            position: relative;
            overflow: hidden;
        }
        
        .stat-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 15px 40px rgba(0,0,0,0.15) !important;
        }
        
        .stat-card i {
            font-size: 2.5rem;
            opacity: 0.2;
            position: absolute;
            right: -10px;
            top: -10px;
            transform: rotate(-15deg);
        }
        
        .stat-value {
            font-size: 2.5rem;
            font-weight: 700;
        }
        
        .stat-label {
            text-transform: uppercase;
            font-size: 0.9rem;
            font-weight: 600;
            letter-spacing: 1px;
        }
        
        .rank-badge {
            padding: 8px 16px;
            font-weight: 600;
            border-radius: 25px;
        }
        
        .table-student {
            font-size: 0.95rem;
        }
        
        .table-student thead {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        .table-student tbody tr {
            border-bottom: 1px solid #e0e0e0;
            transition: all 0.2s;
        }
        
        .table-student tbody tr:hover {
            background-color: #f8f9ff;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05) inset;
        }
        
        .rank-top {
            font-weight: 700;
            color: #667eea;
            font-size: 1.2rem;
        }
        
        .header-report {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            border-radius: 15px;
            margin-bottom: 30px;
        }
        
        .card-report {
            border: none;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }
    </style>
</head>
<body class="bg-light">
    <jsp:include page="../../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../../layouts/sidebar.jsp" />
        
        <div class="p-4 flex-grow-1">
            <!-- Header Section -->
            <div class="header-report">
                <div class="d-flex align-items-center">
                    <i class="bi bi-graph-up me-3" style="font-size: 2.5rem;"></i>
                    <div>
                        <h2 class="mb-2">📊 Báo Cáo Tổng Hợp Tình Hình Học Tập</h2>
                        <p class="mb-0 opacity-90">Thống kê xếp loại und GPA trung bình tổng quát của tất cả sinh viên</p>
                    </div>
                </div>
            </div>

            <!-- Statistics Cards -->
            <div class="row g-4 mb-5">
                <div class="col-md-6 col-lg-3">
                    <div class="card stat-card text-white bg-success shadow-sm">
                        <i class="bi bi-star-fill"></i>
                        <div>
                            <div class="stat-label">Sinh viên Giỏi</div>
                            <div class="stat-value">${countGioi}</div>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 col-lg-3">
                    <div class="card stat-card text-white" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); ">
                        <i class="bi bi-hand-thumbs-up"></i>
                        <div>
                            <div class="stat-label">Sinh viên Khá</div>
                            <div class="stat-value">${countKha}</div>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 col-lg-3">
                    <div class="card stat-card text-dark bg-warning shadow-sm">
                        <i class="bi bi-exclamation-triangle"></i>
                        <div>
                            <div class="stat-label">Sinh viên Trung Bình</div>
                            <div class="stat-value">${countTB}</div>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 col-lg-3">
                    <div class="card stat-card text-white bg-danger shadow-sm">
                        <i class="bi bi-X-circle"></i>
                        <div>
                            <div class="stat-label">Sinh viên Yếu</div>
                            <div class="stat-value">${countYeu}</div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Main Statistics -->
            <div class="row mb-4">
                <div class="col-md-12">
                    <div class="card card-report shadow-sm">
                        <div class="card-header" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border: none;">
                            <h5 class="mb-0 text-white">
                                <i class="bi bi-list-check me-2"></i>Bảng Xếp Hạng Sinh Viên Theo GPA (Từ Cao Đến Thấp)
                            </h5>
                        </div>
                        <div class="card-body p-0">
                            <div class="table-responsive">
                                <table class="table table-hover table-striped mb-0 table-student">
                                    <thead>
                                        <tr>
                                            <th style="width: 5%;">
                                                <i class="bi bi-trophy-fill"></i> Top
                                            </th>
                                            <th style="width: 12%;">Mã SV</th>
                                            <th style="width: 30%;">Họ và Tên</th>
                                            <th style="width: 18%;">Lớp/Khóa</th>
                                            <th style="width: 15%;" class="text-center">
                                                <i class="bi bi-graph-up"></i> GPA
                                            </th>
                                            <th style="width: 20%;" class="text-center">Xếp Loại</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="s" items="${gpaList}" varStatus="loop">
                                            <tr>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${loop.index < 3}">
                                                            <span class="rank-top">
                                                                <c:choose>
                                                                    <c:when test="${loop.index == 0}">🥇 #1</c:when>
                                                                    <c:when test="${loop.index == 1}">🥈 #2</c:when>
                                                                    <c:when test="${loop.index == 2}">🥉 #3</c:when>
                                                                </c:choose>
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="text-muted">#${loop.index + 1}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="fw-bold text-primary">${s.student_code}</td>
                                                <td>${s.full_name}</td>
                                                <td><span class="badge bg-light text-dark">${s.homeroom_class}</span></td>
                                                <td class="text-center">
                                                    <span class="fw-bold" style="font-size: 1.1rem; color: #667eea;">
                                                        ${s.gpa}
                                                    </span>
                                                </td>
                                                <td class="text-center">
                                                    <span class="rank-badge
                                                        <c:choose>
                                                            <c:when test="${s.rank == 'Giỏi'}">bg-success text-white</c:when>
                                                            <c:when test="${s.rank == 'Khá'}">text-white" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);</c:when>
                                                            <c:when test="${s.rank == 'Trung bình'}">bg-warning text-dark</c:when>
                                                            <c:otherwise>bg-danger text-white</c:otherwise>
                                                        </c:choose>">
                                                        ${s.rank}
                                                    </span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        
                                        <c:if test="${empty gpaList}">
                                            <tr>
                                                <td colspan="6" class="text-center py-5">
                                                    <i class="bi bi-inbox" style="font-size: 3rem; color: #ccc;"></i>
                                                    <p class="text-muted mt-3">Chưa có dữ liệu điểm để thống kê. Giáo viên vui lòng nhập điểm trước!</p>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>

                            <c:if test="${not empty gpaList}">
                                <div class="card-footer bg-light text-muted small p-3">
                                    <i class="bi bi-info-circle me-2"></i>
                                    Tổng cộng <strong>${total}</strong> sinh viên | 
                                    GPA cao nhất: <strong><c:out value="${gpaList[0].gpa}" /></strong> | 
                                    GPA thấp nhất: <strong><c:out value="${gpaList[gpaList.size()-1].gpa}" /></strong>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Export & Actions -->
            <div class="row">
                <div class="col-md-12 text-end">
                    <button class="btn btn-outline-primary" onclick="window.print()">
                        <i class="bi bi-printer me-2"></i>In Báo Cáo
                    </button>
                    <a href="${pageContext.request.contextPath}/teacher/my-classes" class="btn btn-outline-secondary">
                        <i class="bi bi-arrow-left me-2"></i>Quay Lại
                    </a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
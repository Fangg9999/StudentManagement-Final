<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Báo Cáo Học Tập</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../../layouts/header.jsp" />
    <div class="d-flex">
        <jsp:include page="../../layouts/sidebar.jsp" />
        
        <div class="p-4 flex-grow-1 bg-light">
            <h3 class="mb-4">📊 Báo Cáo Tổng Hợp Tình Hình Học Tập</h3>

            <div class="row mb-4 text-center">
                <div class="col-md-3">
                    <div class="card text-white bg-success mb-3 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Giỏi</h5>
                            <h2 class="card-text">${countGioi}</h2>
                            <small>Sinh viên</small>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-white bg-primary mb-3 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Khá</h5>
                            <h2 class="card-text">${countKha}</h2>
                            <small>Sinh viên</small>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-dark bg-warning mb-3 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Trung Bình</h5>
                            <h2 class="card-text">${countTB}</h2>
                            <small>Sinh viên</small>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-white bg-danger mb-3 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Yếu</h5>
                            <h2 class="card-text">${countYeu}</h2>
                            <small>Sinh viên</small>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card shadow-sm">
                <div class="card-header bg-dark text-white">
                    <h5 class="mb-0">Bảng Điểm Xếp Hạng (Đã sắp xếp theo GPA)</h5>
                </div>
                <div class="card-body p-0">
                    <table class="table table-hover table-bordered mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>Top</th>
                                <th>Mã SV</th>
                                <th>Họ Tên</th>
                                <th>Lớp/Khóa</th>
                                <th>GPA</th>
                                <th>Xếp Loại</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${gpaList}" varStatus="loop">
                                <tr>
                                    <td><strong>#${loop.index + 1}</strong></td>
                                    <td>${s.student_code}</td>
                                    <td>${s.full_name}</td>
                                    <td>${s.homeroom_class}</td>
                                    <td class="text-primary fw-bold">${s.gpa}</td>
                                    <td>
                                        <span class="badge 
                                            ${s.rank == 'Giỏi' ? 'bg-success' : 
                                              s.rank == 'Khá' ? 'bg-primary' : 
                                              s.rank == 'Trung bình' ? 'bg-warning text-dark' : 'bg-danger'}">
                                            ${s.rank}
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty gpaList}">
                                <tr><td colspan="6" class="text-center">Chưa có dữ liệu điểm để thống kê.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            
        </div>
    </div>
</body>
</html>
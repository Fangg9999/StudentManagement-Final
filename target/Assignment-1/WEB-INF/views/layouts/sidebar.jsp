<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<div class="bg-dark text-white p-3" style="min-height: 100vh; width: 250px;">
    <h5 class="text-center border-bottom pb-3 mb-3">MENU TÍNH NĂNG</h5>
    <ul class="nav flex-column">
        <li class="nav-item mb-2">
            <a href="${pageContext.request.contextPath}/home" class="nav-link text-white">🏠 Trang chủ</a>
        </li>

        <li class="nav-item mb-2">
            <a href="${pageContext.request.contextPath}/profile" class="nav-link text-white">👤 Hồ sơ cá nhân</a>
        </li>

        <c:if test="${sessionScope.LOGIN_USER.roleId == 1}">
            <li class="nav-item">
                <a class="nav-link text-white" href="${pageContext.request.contextPath}/admin/users">
                    <i class="bi bi-shield-lock-fill me-2"></i> Quản lý Tài Khoản
                </a>
            </li>
            <li class="nav-item mb-2">
                <a href="${pageContext.request.contextPath}/admin/majors" class="nav-link text-info">⚙️ Quản lý Ngành học</a>
            </li>
            <li class="nav-item mb-2">
                <a href="${pageContext.request.contextPath}/admin/subjects" class="nav-link text-info">📚 Quản lý Môn học</a>
            </li>
            <li class="nav-item mb-2">
                <a href="${pageContext.request.contextPath}/admin/students" class="nav-link text-info">🎓 Quản lý Sinh viên</a>
            </li>
            <li class="nav-item mb-2">
                <a href="${pageContext.request.contextPath}/admin/classes" class="nav-link text-info">🏫 Quản lý Lớp học</a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="${pageContext.request.contextPath}/admin/semesters">
                    <i class="bi bi-calendar-event me-2"></i> Quản lý Học Kỳ
                </a>
            </li>
        </c:if>

        <c:if test="${sessionScope.LOGIN_USER.roleId == 2}">
            <li class="nav-item mb-2">
                <a href="${pageContext.request.contextPath}/teacher/grades" class="nav-link text-warning">📝 Quản lý Điểm số</a>
            </li>
            <li class="nav-item mb-2">
                <a href="${pageContext.request.contextPath}/teacher/reports" class="nav-link text-warning">📊 Báo cáo Học tập</a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="${pageContext.request.contextPath}/teacher/my-classes">
                    <i class="bi bi-journal-bookmark me-2"></i> Lớp Của Tôi
                </a>
            </li>
        </c:if>


    </ul>
</div>
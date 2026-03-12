package filter;

import model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String contextPath = req.getContextPath();
        String currentURI = req.getRequestURI();

        // 1. Whitelist: Cho phép truy cập vào /auth
        if (currentURI.equals(contextPath + "/auth") || currentURI.matches(".*(css|jpg|png|gif|js)")) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Kiểm tra đăng nhập: Đá về /auth?action=login
        boolean isLoggedIn = (session != null && session.getAttribute("LOGIN_USER") != null);
        if (!isLoggedIn) {
            res.sendRedirect(contextPath + "/auth?action=login");
            return;
        }

        // 3. Phân quyền (Authorization) - Trạm kiểm soát trung tâm
        User currentUser = (User) session.getAttribute("LOGIN_USER");

        // Nếu URL bắt đầu bằng /admin/ mà user không phải Admin (roleId != 1) => Chặn
        if (currentURI.startsWith(contextPath + "/admin/") && currentUser.getRoleId() != 1) {
            // Đẩy về trang home kèm thông báo lỗi (tùy chọn) hoặc trang 403
            res.sendRedirect(contextPath + "/home?error=access_denied");
            return;
        }

        // Hợp lệ, cho phép đi tiếp tới Controller
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

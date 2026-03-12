package controller;

import dao.UserDAO;
import model.User;
import util.SecurityUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ChangePasswordController", urlPatterns = {"/profile/change-password"})
public class ChangePasswordServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/profile/change-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("LOGIN_USER");

        String oldPass = request.getParameter("oldPassword");
        String newPass = request.getParameter("newPassword");
        String confirmPass = request.getParameter("confirmPassword");

        // 1. Kiểm tra xác nhận mật khẩu mới (Backend Validation)
        if (!newPass.equals(confirmPass)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("/WEB-INF/views/profile/change-password.jsp").forward(request, response);
            return;
        }

        // 2. Xác minh mật khẩu cũ bằng cách gọi lại hàm Login
        String hashedOldPass = SecurityUtil.hashMD5(oldPass);
        User verifyUser = userDAO.checkLogin(currentUser.getUsername(), hashedOldPass);
        
        if (verifyUser == null) {
            request.setAttribute("error", "Mật khẩu cũ không chính xác!");
            request.getRequestDispatcher("/WEB-INF/views/profile/change-password.jsp").forward(request, response);
            return;
        }

        // 3. Đổi mật khẩu
        String hashedNewPass = SecurityUtil.hashMD5(newPass);
        boolean success = userDAO.updatePassword(currentUser.getId(), hashedNewPass);

        if (success) {
            // ÉP ĐĂNG NHẬP LẠI
            session.invalidate(); 
            response.sendRedirect(request.getContextPath() + "/auth?action=login?msg=password_changed");
        } else {
            request.setAttribute("error", "Lỗi hệ thống, không thể đổi mật khẩu.");
            request.getRequestDispatcher("/WEB-INF/views/profile/change-password.jsp").forward(request, response);
        }
    }
}
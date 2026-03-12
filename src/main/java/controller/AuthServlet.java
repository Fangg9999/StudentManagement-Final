package controller;

import dao.UserDAO;
import model.User;
import util.SecurityUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "AuthController", urlPatterns = {"/auth"})
public class AuthServlet extends HttpServlet {
    
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "login";

        if ("logout".equals(action)) {
            // Xử lý Đăng xuất
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate(); // Hủy toàn bộ session an toàn
            }
            response.sendRedirect(request.getContextPath() + "/auth?action=login");
            
        } else {
            // Xử lý hiển thị form Đăng nhập
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("LOGIN_USER") != null) {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        // Chỉ xử lý POST cho form Login
        if ("login".equals(action) || action == null) {
            String user = request.getParameter("username");
            String pass = request.getParameter("password");
            
            String hashedPass = SecurityUtil.hashMD5(pass);
            User loginUser = userDAO.checkLogin(user, hashedPass);
            
            if (loginUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("LOGIN_USER", loginUser);
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                request.setAttribute("errorMessage", "Sai tài khoản hoặc mật khẩu!");
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            }
        }
    }
}
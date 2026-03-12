package controller;

import dao.UserDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ProfileController", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chỉ cần đẩy sang trang JSP, thông tin user đã có sẵn trong sessionScope.LOGIN_USER
        request.getRequestDispatcher("/WEB-INF/views/profile/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("LOGIN_USER");
        
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        
        // Cập nhật DB
        boolean success = userDAO.updateProfile(currentUser.getId(), fullName, email);
        
        if (success) {
            // Cập nhật lại session ngay lập tức để giao diện không bị cũ
            currentUser.setFullName(fullName);
            currentUser.setEmail(email);
            session.setAttribute("LOGIN_USER", currentUser);
            
            response.sendRedirect(request.getContextPath() + "/profile?msg=success");
        } else {
            response.sendRedirect(request.getContextPath() + "/profile?msg=error");
        }
    }
}
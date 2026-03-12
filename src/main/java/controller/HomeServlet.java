package controller;

import dao.DashboardDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {
    
    private DashboardDAO dashboardDAO = new DashboardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("LOGIN_USER") != null) {
            User currentUser = (User) session.getAttribute("LOGIN_USER");
            
            // Chỉ Admin (roleId = 1) mới cần xem số liệu thống kê tổng quan
            if (currentUser.getRoleId() == 1) {
                Map<String, Integer> stats = dashboardDAO.getStatistics();
                request.setAttribute("stats", stats);
            }
        }
        
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}
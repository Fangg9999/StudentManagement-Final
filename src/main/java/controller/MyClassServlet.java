package controller;

import dao.ClassDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "MyClassController", urlPatterns = {"/teacher/my-classes"})
public class MyClassServlet extends HttpServlet {

    private ClassDAO classDAO = new ClassDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Lấy thông tin Giáo viên đang đăng nhập từ Session
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("LOGIN_USER");

        // 2. Bảo mật bổ sung: Chắc chắn đây là Giáo viên (Role = 2)
        if (currentUser.getRoleId() != 2) {
            response.sendRedirect(request.getContextPath() + "/home?error=unauthorized");
            return;
        }

        // 3. Lấy danh sách lớp của riêng Giáo viên này
        List<Map<String, Object>> myClasses = classDAO.getClassesByTeacherId(currentUser.getId());
        
        request.setAttribute("myClasses", myClasses);
        request.getRequestDispatcher("/WEB-INF/views/teacher/classes/my-classes.jsp").forward(request, response);
    }
}
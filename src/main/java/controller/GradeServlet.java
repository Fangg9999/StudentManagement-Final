package controller;

import dao.ClassDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "GradeController", urlPatterns = {"/teacher/grades"})
public class GradeServlet extends HttpServlet {

    private ClassDAO classDAO = new ClassDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lấy thông tin user từ session
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("LOGIN_USER");
        
        // Kiểm tra phân quyền: chỉ giáo viên mới được phép
        if (currentUser == null || currentUser.getRoleId() != 2) {
            response.sendRedirect(request.getContextPath() + "/home?error=access_denied");
            return;
        }
        
        int teacherId = currentUser.getId();

        // Lấy danh sách các lớp mà giáo viên này dạy
        List<Map<String, Object>> classList = classDAO.getClassesByTeacherId(teacherId);
        
        request.setAttribute("classList", classList);
        request.getRequestDispatcher("/WEB-INF/views/teacher/grades/list.jsp").forward(request, response);
    }
}

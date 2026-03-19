package controller;

import dao.ClassDAO;
import dao.GradeDAO;
import model.ClassRoom;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "GradeEntryController", urlPatterns = {"/teacher/grades/entry"})
public class GradeEntryServlet extends HttpServlet {

    private GradeDAO gradeDAO = new GradeDAO();
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
        
        // Giả sử Giáo viên đã click vào 1 Lớp từ danh sách các lớp họ dạy
        int classId = Integer.parseInt(request.getParameter("classId"));
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));

        System.out.println("[DEBUG] GradeEntryServlet - classId=" + classId + ", subjectId=" + subjectId);
        
        // Kiểm tra xem giáo viên này có dạy lớp này không
        ClassRoom classRoom = classDAO.getClassById(classId);
        if (classRoom == null || classRoom.getTeacherId() != currentUser.getId()) {
            response.sendRedirect(request.getContextPath() + "/teacher/grades?error=class_not_found");
            return;
        }
        
        // Sử dụng ClassDAO thay vì GradeDAO vì nó đã hoạt động tốt
        List<Map<String, Object>> studentList = classDAO.getStudentsInClass(classId);
        System.out.println("[DEBUG] GradeEntryServlet - studentList size=" + studentList.size());
        
        request.setAttribute("studentList", studentList);
        request.setAttribute("classId", classId);
        request.setAttribute("subjectId", subjectId);
        
        request.getRequestDispatcher("/WEB-INF/views/teacher/grades/entry.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lấy thông tin user từ session
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("LOGIN_USER");
        
        // Kiểm tra phân quyền: chỉ giáo viên mới được phép
        if (currentUser == null || currentUser.getRoleId() != 2) {
            response.sendRedirect(request.getContextPath() + "/home?error=access_denied");
            return;
        }
        
        int classId = Integer.parseInt(request.getParameter("classId"));
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        
        // Kiểm tra xem giáo viên này có dạy lớp này không
        ClassRoom classRoom = classDAO.getClassById(classId);
        if (classRoom == null || classRoom.getTeacherId() != currentUser.getId()) {
            response.sendRedirect(request.getContextPath() + "/teacher/grades?error=class_not_found");
            return;
        }
        
        // Lấy toàn bộ mảng Input từ giao diện
        String[] studentIds = request.getParameterValues("studentIds");
        String[] scores = request.getParameterValues("scores");

        System.out.println("[DEBUG] GradeEntryServlet POST - classId=" + classId + ", saving grades");
        boolean success = gradeDAO.saveGradesBatch(classId, studentIds, scores);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/teacher/grades/entry?classId=" + classId + "&subjectId=" + subjectId + "&msg=success");
        } else {
            response.sendRedirect(request.getContextPath() + "/teacher/grades/entry?classId=" + classId + "&subjectId=" + subjectId + "&msg=error");
        }
    }
}
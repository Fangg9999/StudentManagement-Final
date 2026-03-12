package controller;

import dao.GradeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "GradeEntryController", urlPatterns = {"/teacher/grades/entry"})
public class GradeEntryServlet extends HttpServlet {

    private GradeDAO gradeDAO = new GradeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Giả sử Giáo viên đã click vào 1 Lớp từ danh sách các lớp họ dạy
        int classId = Integer.parseInt(request.getParameter("classId"));
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));

        List<Map<String, Object>> studentList = gradeDAO.getStudentsAndGrades(classId, subjectId);
        
        request.setAttribute("studentList", studentList);
        request.setAttribute("classId", classId);
        request.setAttribute("subjectId", subjectId);
        
        request.getRequestDispatcher("/WEB-INF/views/teacher/grades/entry.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int classId = Integer.parseInt(request.getParameter("classId"));
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        
        // Lấy toàn bộ mảng Input từ giao diện
        String[] studentIds = request.getParameterValues("studentIds");
        String[] scores = request.getParameterValues("scores");

        boolean success = gradeDAO.saveGradesBatch(subjectId, studentIds, scores);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/teacher/grades/entry?classId=" + classId + "&subjectId=" + subjectId + "&msg=success");
        } else {
            response.sendRedirect(request.getContextPath() + "/teacher/grades/entry?classId=" + classId + "&subjectId=" + subjectId + "&msg=error");
        }
    }
}
package controller;

import dao.ClassDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ClassStudentController", urlPatterns = {"/admin/class-students"})
public class ClassStudentServlet extends HttpServlet {

    private ClassDAO classDAO = new ClassDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        int classId = Integer.parseInt(request.getParameter("classId"));

        // Xử lý XÓA Sinh viên khỏi Lớp
        if ("delete".equals(action)) {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            String result = classDAO.removeStudentFromClass(classId, studentId);

            if ("graded".equals(result)) {
                // Đá về kèm thông báo đỏ: Đã có điểm không cho xóa
                response.sendRedirect(request.getContextPath() + "/admin/class-students?classId=" + classId + "&error=graded");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/class-students?classId=" + classId + "&msg=deleted");
            }
            return;
        }

        // Mặc định: Lấy danh sách SV trong lớp này
        request.setAttribute("classId", classId);
        request.setAttribute("students", classDAO.getStudentsInClass(classId));
        request.getRequestDispatcher("/WEB-INF/views/admin/classes/students.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Xử lý THÊM Sinh viên vào lớp bằng Mã SV
        int classId = Integer.parseInt(request.getParameter("classId"));
        String studentCode = request.getParameter("studentCode");

        // Validate cơ bản
        if (studentCode == null || studentCode.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/class-students?classId=" + classId + "&error=empty");
            return;
        }

        // Gọi DAO xử lý Logic
        String result = classDAO.addStudentToClass(classId, studentCode.trim());

        // Phân luồng kết quả trả về
        switch (result) {
            case "not_found":
                response.sendRedirect(request.getContextPath() + "/admin/class-students?classId=" + classId + "&error=not_found");
                break;
            case "exists":
                response.sendRedirect(request.getContextPath() + "/admin/class-students?classId=" + classId + "&error=exists");
                break;
            case "success":
                response.sendRedirect(request.getContextPath() + "/admin/class-students?classId=" + classId + "&msg=success");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/class-students?classId=" + classId + "&error=db_error");
                break;
        }
    }
}
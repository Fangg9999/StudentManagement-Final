package controller;

import dao.StudentDAO;
import model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "StudentController", urlPatterns = {"/admin/students"})
public class StudentServlet extends HttpServlet {
    
    private StudentDAO studentDAO = new StudentDAO();
    private final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                request.setAttribute("actionType", "add");
                request.getRequestDispatcher("/WEB-INF/views/admin/students/form.jsp").forward(request, response);
                break;
                
            case "edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                Student s = studentDAO.getStudentById(editId);
                if (s == null) {
                    response.sendRedirect(request.getContextPath() + "/admin/students");
                    return;
                }
                request.setAttribute("student", s);
                request.setAttribute("actionType", "edit");
                request.getRequestDispatcher("/WEB-INF/views/admin/students/form.jsp").forward(request, response);
                break;
                
            case "delete":
                int delId = Integer.parseInt(request.getParameter("id"));
                studentDAO.deleteStudent(delId); 
                response.sendRedirect(request.getContextPath() + "/admin/students?msg=deleted");
                break;
                
            default: // Lấy danh sách + Tìm kiếm + Phân trang
                String keyword = request.getParameter("keyword");
                if (keyword == null) keyword = "";

                int page = 1;
                try {
                    if (request.getParameter("page") != null) {
                        page = Integer.parseInt(request.getParameter("page"));
                    }
                } catch (NumberFormatException e) {
                    page = 1;
                }

                List<Student> students = studentDAO.getStudents(keyword, page, PAGE_SIZE);
                int totalRecords = studentDAO.getTotalStudents(keyword);
                int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);

                request.setAttribute("students", students);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("keyword", keyword);

                request.getRequestDispatcher("/WEB-INF/views/admin/students/list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String actionType = request.getParameter("actionType");
        String code = request.getParameter("studentCode");
        String name = request.getParameter("fullName");
        String dobStr = request.getParameter("dob");
        String enrollStr = request.getParameter("enrollmentDate");
        String homeroom = request.getParameter("homeroomClass");
        
        // Validation cơ bản
        if (name == null || name.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/students?action=" + actionType + "&error=empty");
            return;
        }

        Student s = new Student();
        s.setFullName(name);
        s.setDob(Date.valueOf(dobStr));
        s.setEnrollmentDate(Date.valueOf(enrollStr));
        s.setHomeroomClass(homeroom);
        s.setMajorId(1); // Tạm set cứng ID ngành học

        if ("edit".equals(actionType)) {
            int id = Integer.parseInt(request.getParameter("id"));
            s.setId(id);
            // Cập nhật không đụng tới mã SV
            studentDAO.updateStudent(s);
            response.sendRedirect(request.getContextPath() + "/admin/students?msg=edit_success");
        } else {
            s.setStudentCode(code);
            // Kiểm tra trùng lặp
            if (studentDAO.isDuplicateCode(code, 0)) {
                request.setAttribute("error", "Mã sinh viên đã tồn tại!");
                request.setAttribute("actionType", "add");
                request.getRequestDispatcher("/WEB-INF/views/admin/students/form.jsp").forward(request, response);
                return;
            }
            studentDAO.insertStudent(s);
            response.sendRedirect(request.getContextPath() + "/admin/students?msg=add_success");
        }
    }
}
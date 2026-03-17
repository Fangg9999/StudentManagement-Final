package controller;

import dao.ClassDAO;
import model.ClassRoom;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ClassController", urlPatterns = {"/admin/classes"})
public class ClassServlet extends HttpServlet {

    private ClassDAO classDAO = new ClassDAO();

    private void loadDropdownData(HttpServletRequest request) {
        request.setAttribute("subjects", classDAO.getDropdownData("SELECT id, subject_name FROM Subjects", "id", "subject_name"));
        request.setAttribute("semesters", classDAO.getDropdownData("SELECT id, semester_name FROM Semesters", "id", "semester_name"));
        request.setAttribute("teachers", classDAO.getDropdownData("SELECT id, full_name FROM Users WHERE role = 'TEACHER'", "id", "full_name"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                loadDropdownData(request);
                request.setAttribute("actionType", "add");
                request.getRequestDispatcher("/WEB-INF/views/admin/classes/form.jsp").forward(request, response);
                break;
            case "edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                loadDropdownData(request);
                request.setAttribute("classRoom", classDAO.getClassById(editId));
                request.setAttribute("actionType", "edit");
                request.getRequestDispatcher("/WEB-INF/views/admin/classes/form.jsp").forward(request, response);
                break;
            case "delete":
                int delId = Integer.parseInt(request.getParameter("id"));
                classDAO.deleteClass(delId);
                response.sendRedirect(request.getContextPath() + "/admin/classes?msg=deleted");
                break;
            default:
                request.setAttribute("classesList", classDAO.getAllClasses());
                request.getRequestDispatcher("/WEB-INF/views/admin/classes/list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String actionType = request.getParameter("actionType");
        String code = request.getParameter("classCode");
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        int semesterId = Integer.parseInt(request.getParameter("semesterId"));
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));

        ClassRoom c = new ClassRoom(0, code, subjectId, semesterId, teacherId);

        if ("edit".equals(actionType)) {
            c.setId(Integer.parseInt(request.getParameter("id")));
            classDAO.updateClass(c);
        } else {
            classDAO.insertClass(c);
        }
        response.sendRedirect(request.getContextPath() + "/admin/classes?msg=success");
    }
}

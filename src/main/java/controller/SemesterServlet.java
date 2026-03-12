package controller;

import dao.SemesterDAO;
import model.Semester;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "SemesterController", urlPatterns = {"/admin/semesters"})
public class SemesterServlet extends HttpServlet {
    private SemesterDAO dao = new SemesterDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                request.setAttribute("actionType", "add");
                request.getRequestDispatcher("/WEB-INF/views/admin/semesters/form.jsp").forward(request, response);
                break;
            case "edit":
                request.setAttribute("semester", dao.getById(Integer.parseInt(request.getParameter("id"))));
                request.setAttribute("actionType", "edit");
                request.getRequestDispatcher("/WEB-INF/views/admin/semesters/form.jsp").forward(request, response);
                break;
            case "delete":
                String result = dao.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect(request.getContextPath() + "/admin/semesters?" + ("foreign_key_error".equals(result) ? "error=in_use" : "msg=deleted"));
                break;
            default:
                request.setAttribute("semesters", dao.getAll());
                request.getRequestDispatcher("/WEB-INF/views/admin/semesters/list.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String actionType = request.getParameter("actionType");
        Semester s = new Semester(0, request.getParameter("semesterCode"), request.getParameter("semesterName"));
        
        if ("edit".equals(actionType)) {
            s.setId(Integer.parseInt(request.getParameter("id")));
            dao.update(s);
        } else {
            dao.insert(s);
        }
        response.sendRedirect(request.getContextPath() + "/admin/semesters?msg=success");
    }
}
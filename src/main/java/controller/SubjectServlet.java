package controller;

import dao.SubjectDAO;
import model.Subject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SubjectController", urlPatterns = {"/admin/subjects"})
public class SubjectServlet extends HttpServlet {

    private SubjectDAO subjectDAO = new SubjectDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                request.setAttribute("actionType", "add");
                request.getRequestDispatcher("/WEB-INF/views/admin/subjects/form.jsp").forward(request, response);
                break;
            case "edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("subject", subjectDAO.getSubjectById(editId));
                request.setAttribute("actionType", "edit");
                request.getRequestDispatcher("/WEB-INF/views/admin/subjects/form.jsp").forward(request, response);
                break;
            case "delete":
                int delId = Integer.parseInt(request.getParameter("id"));
                String result = subjectDAO.delete(delId);
                if ("foreign_key_error".equals(result)) {
                    response.sendRedirect(request.getContextPath() + "/admin/subjects?error=in_use");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/subjects?msg=deleted");
                }
                break;
            default:
                List<Subject> list = subjectDAO.getAllSubjects();
                request.setAttribute("subjects", list);
                request.getRequestDispatcher("/WEB-INF/views/admin/subjects/list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String actionType = request.getParameter("actionType");
        String code = request.getParameter("subjectCode");
        String name = request.getParameter("subjectName");
        int credits = Integer.parseInt(request.getParameter("credits"));

        Subject s = new Subject(0, code, name, credits);

        if ("edit".equals(actionType)) {
            s.setId(Integer.parseInt(request.getParameter("id")));
            subjectDAO.update(s);
        } else {
            subjectDAO.insert(s);
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/subjects?msg=success");
    }
}
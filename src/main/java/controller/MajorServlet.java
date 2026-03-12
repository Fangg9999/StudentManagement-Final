package controller;

import dao.MajorDAO;
import model.Major;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MajorController", urlPatterns = {"/admin/majors"})
public class MajorServlet extends HttpServlet {

    private MajorDAO majorDAO = new MajorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                request.getRequestDispatcher("/WEB-INF/views/admin/majors/form.jsp").forward(request, response);
                break;
            case "edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("major", majorDAO.getMajorById(editId));
                request.setAttribute("actionType", "edit");
                request.getRequestDispatcher("/WEB-INF/views/admin/majors/form.jsp").forward(request, response);
                break;
            case "delete":
                int delId = Integer.parseInt(request.getParameter("id"));
                majorDAO.delete(delId);
                response.sendRedirect(request.getContextPath() + "/admin/majors?msg=deleted");
                break;
            default: // Lấy danh sách mặc định
                List<Major> list = majorDAO.getAllMajors();
                request.setAttribute("majors", list);
                request.getRequestDispatcher("/WEB-INF/views/admin/majors/list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String actionType = request.getParameter("actionType");
        String code = request.getParameter("majorCode");
        String name = request.getParameter("majorName");

        Major m = new Major();
        m.setMajorName(name);

        if ("edit".equals(actionType)) {
            m.setId(Integer.parseInt(request.getParameter("id")));
            majorDAO.update(m);
        } else {
            m.setMajorCode(code);
            majorDAO.insert(m);
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/majors?msg=success");
    }
}
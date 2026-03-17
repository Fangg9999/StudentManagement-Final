package controller;

import dao.UserDAO;
import model.User;
import util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "UserController", urlPatterns = {"/admin/users"})
public class UserServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                request.setAttribute("actionType", "add");
                request.getRequestDispatcher("/WEB-INF/views/admin/users/form.jsp").forward(request, response);
                break;
            case "edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("userObj", userDAO.getUserById(editId));
                request.setAttribute("actionType", "edit");
                request.getRequestDispatcher("/WEB-INF/views/admin/users/form.jsp").forward(request, response);
                break;
            case "delete":
                int delId = Integer.parseInt(request.getParameter("id"));
                // Chống Admin tự xóa chính mình
                User currentUser = (User) request.getSession().getAttribute("LOGIN_USER");
                if (currentUser.getId() == delId) {
                    response.sendRedirect(request.getContextPath() + "/admin/users?error=self_delete");
                    return;
                }

                String result = userDAO.deleteUser(delId);
                if ("foreign_key_error".equals(result)) {
                    response.sendRedirect(request.getContextPath() + "/admin/users?error=in_use");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/users?msg=deleted");
                }
                break;
            default:
                request.setAttribute("users", userDAO.getAllUsers());
                request.getRequestDispatcher("/WEB-INF/views/admin/users/list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String actionType = request.getParameter("actionType");
        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        User u = new User(0, username, fullName, email, roleId);

        if ("edit".equals(actionType)) {
            int id = Integer.parseInt(request.getParameter("id"));
            u.setId(id);
            // Bắt kết quả trả về
            boolean success = userDAO.updateUserByAdmin(u);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/users?msg=success");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/users?error=db_error");
            }
        } else {
            // Kiểm tra trùng lặp Username
            if (userDAO.isDuplicateUsername(username, 0)) {
                request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
                request.setAttribute("actionType", "add");
                request.setAttribute("userObj", u);
                request.getRequestDispatcher("/WEB-INF/views/admin/users/form.jsp").forward(request, response);
                return;
            }

            // Cấp mật khẩu mặc định là 123456
            String defaultPass = SecurityUtil.hashMD5("123456");
            boolean success = userDAO.insertUser(u, defaultPass);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/users?msg=add_success");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/users?error=db_error");
            }
        }
    }
}

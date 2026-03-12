package controller;

import dao.ReportDAO;
import util.GradingUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ReportController", urlPatterns = {"/teacher/reports"})
public class ReportServlet extends HttpServlet {

    private ReportDAO reportDAO = new ReportDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String, Object>> gpaList = reportDAO.getStudentGPAList();

        // Biến đếm thống kê
        int countGioi = 0, countKha = 0, countTB = 0, countYeu = 0;

        // Xử lý logic Xếp loại và Thống kê
        for (Map<String, Object> student : gpaList) {
            double gpa = (Double) student.get("gpa");

            // Làm tròn 2 chữ số thập phân
            double roundedGpa = Math.round(gpa * 100.0) / 100.0;
            student.put("gpa", roundedGpa);
            String rank = GradingUtil.getRank(roundedGpa);
            student.put("rank", rank);

            // Đếm số lượng
            if (rank.equals("Giỏi")) {
                countGioi++;
            } else if (rank.equals("Khá")) {
                countKha++;
            } else if (rank.equals("Trung bình")) {
                countTB++;
            } else {
                countYeu++;
            }
        }

        request.setAttribute("gpaList", gpaList);
        request.setAttribute("total", gpaList.size());
        request.setAttribute("countGioi", countGioi);
        request.setAttribute("countKha", countKha);
        request.setAttribute("countTB", countTB);
        request.setAttribute("countYeu", countYeu);

        request.getRequestDispatcher("/WEB-INF/views/teacher/reports/index.jsp").forward(request, response);
    }
}

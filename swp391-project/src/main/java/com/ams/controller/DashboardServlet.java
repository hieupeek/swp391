package com.ams.controller;

import com.ams.config.Constants;
import com.ams.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet hiển thị Dashboard dựa trên role của user.
 * Mỗi role có dashboard view riêng.
 */
@WebServlet(name = "DashboardServlet", urlPatterns = { "/dashboard" })
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy user từ session
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(Constants.SESSION_USER) == null) {
            // Chưa đăng nhập - redirect về login
            response.sendRedirect(request.getContextPath() + Constants.PAGE_LOGIN);
            return;
        }

        User user = (User) session.getAttribute(Constants.SESSION_USER);
        String roleName = (String) session.getAttribute(Constants.SESSION_ROLE);

        // Set attributes cho view
        request.setAttribute("user", user);
        request.setAttribute("roleName", roleName);

        // Chọn view dựa trên role
        String viewPath = switch (roleName) {
            case Constants.ROLE_PRINCIPAL -> Constants.VIEW_PREFIX + "dashboard/principal" + Constants.VIEW_SUFFIX;
            case Constants.ROLE_FINANCE_HEAD -> Constants.VIEW_PREFIX + "dashboard/finance" + Constants.VIEW_SUFFIX;
            case Constants.ROLE_ASSET_STAFF -> Constants.VIEW_PREFIX + "dashboard/staff" + Constants.VIEW_SUFFIX;
            case Constants.ROLE_HEAD_OF_DEPT -> Constants.VIEW_PREFIX + "dashboard/hod" + Constants.VIEW_SUFFIX;
            default -> Constants.VIEW_PREFIX + "dashboard/default" + Constants.VIEW_SUFFIX;
        };

        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}

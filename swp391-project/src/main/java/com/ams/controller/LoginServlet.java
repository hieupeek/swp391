package com.ams.controller;

import com.ams.config.Constants;
import com.ams.model.User;
import com.ams.service.UserService;
import com.ams.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Servlet xử lý đăng nhập.
 * GET: Hiển thị form đăng nhập
 * POST: Xử lý xác thực
 */
@WebServlet(name = "LoginServlet", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserServiceImpl();
    }

    /**
     * Hiển thị trang đăng nhập.
     * Nếu user đã đăng nhập, redirect về dashboard.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra nếu đã đăng nhập
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(Constants.SESSION_USER) != null) {
            response.sendRedirect(request.getContextPath() + Constants.PAGE_DASHBOARD);
            return;
        }

        // Hiển thị trang login
        request.getRequestDispatcher(Constants.VIEW_PREFIX + "auth/login" + Constants.VIEW_SUFFIX)
                .forward(request, response);
    }

    /**
     * Xử lý đăng nhập.
     * Xác thực username/password và tạo session.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy thông tin từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate input cơ bản
        if (username == null || username.isBlank() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin đăng nhập.");
            request.getRequestDispatcher(Constants.VIEW_PREFIX + "auth/login" + Constants.VIEW_SUFFIX)
                    .forward(request, response);
            return;
        }

        // Xác thực
        Optional<User> userOpt = userService.authenticate(username.trim(), password);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Tạo session mới để tránh session fixation
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            HttpSession newSession = request.getSession(true);

            // Lưu user vào session
            newSession.setAttribute(Constants.SESSION_USER, user);
            newSession.setAttribute(Constants.SESSION_ROLE, user.getRole().getRoleName());

            // Redirect về dashboard
            response.sendRedirect(request.getContextPath() + Constants.PAGE_DASHBOARD);
        } else {
            // Đăng nhập thất bại
            request.setAttribute("error", Constants.MSG_LOGIN_FAILED);
            request.setAttribute("username", username); // Giữ lại username
            request.getRequestDispatcher(Constants.VIEW_PREFIX + "auth/login" + Constants.VIEW_SUFFIX)
                    .forward(request, response);
        }
    }
}

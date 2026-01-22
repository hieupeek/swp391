package com.ams.controller;

import com.ams.config.Constants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet xử lý đăng xuất.
 * Invalidate session và redirect về trang login.
 */
@WebServlet(name = "LogoutServlet", urlPatterns = { "/logout" })
public class LogoutServlet extends HttpServlet {

    /**
     * Xử lý đăng xuất.
     * Hủy session và redirect về trang login với thông báo.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy session hiện tại (không tạo mới nếu không có)
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Hủy session
            session.invalidate();
        }

        // Redirect về trang login với thông báo đăng xuất thành công
        response.sendRedirect(request.getContextPath() + Constants.PAGE_LOGIN + "?logout=success");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Cho phép cả POST request
        doGet(request, response);
    }
}

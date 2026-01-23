package com.ams.controller;

import com.ams.config.Constants;
import com.ams.model.User;
import com.ams.service.UserService;
import com.ams.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(Constants.SESSION_USER) == null) {
            response.sendRedirect(request.getContextPath() + Constants.PAGE_LOGIN);
            return;
        }

        User user = (User) session.getAttribute(Constants.SESSION_USER);
        String roleName = (String) session.getAttribute(Constants.SESSION_ROLE);

        request.setAttribute("user", user);
        request.setAttribute("roleName", roleName);
        request.setAttribute("pageTitle", "Hồ sơ cá nhân");
        request.setAttribute("currentPage", "profile");

        request.getRequestDispatcher(Constants.VIEW_PREFIX + "user/profile" + Constants.VIEW_SUFFIX)
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(Constants.SESSION_USER) == null) {
            response.sendRedirect(request.getContextPath() + Constants.PAGE_LOGIN);
            return;
        }

        User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
        String roleName = (String) session.getAttribute(Constants.SESSION_ROLE);

        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");

        boolean hasError = false;
        StringBuilder errorMsg = new StringBuilder();

        if (fullName != null && !fullName.isBlank()) {
            currentUser.setFullName(fullName.trim());
        }

        if (email != null && !email.isBlank()) {
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                hasError = true;
                errorMsg.append("Email không hợp lệ. ");
            } else {
                currentUser.setEmail(email.trim());
            }
        }

        if (phone != null && !phone.isBlank()) {
            if (!phone.matches("^[0-9]{10,11}$")) {
                hasError = true;
                errorMsg.append("Số điện thoại không hợp lệ (10-11 số). ");
            } else {
                currentUser.setPhone(phone.trim());
            }
        }

        if (!hasError) {
            boolean updated = userService.updateProfile(currentUser);

            if (updated) {
                session.setAttribute(Constants.SESSION_USER, currentUser);
                request.setAttribute("success", "Cập nhật hồ sơ thành công!");
            } else {
                request.setAttribute("error", "Có lỗi xảy ra khi cập nhật. Vui lòng thử lại.");
            }
        } else {
            request.setAttribute("error", errorMsg.toString());
        }

        request.setAttribute("user", currentUser);
        request.setAttribute("roleName", roleName);
        request.setAttribute("pageTitle", "Hồ sơ cá nhân");
        request.setAttribute("currentPage", "profile");

        request.getRequestDispatcher(Constants.VIEW_PREFIX + "user/profile" + Constants.VIEW_SUFFIX)
                .forward(request, response);
    }
}

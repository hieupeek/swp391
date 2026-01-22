package com.ams.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Filter xác thực bảo vệ các trang cần đăng nhập.
 * Cho phép truy cập các trang public, redirect về login nếu chưa đăng nhập.
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    /**
     * Danh sách các URL pattern không cần đăng nhập.
     */
    private static final List<String> PUBLIC_PATTERNS = Arrays.asList(
            "/login",
            "/logout",
            "/assets/", // Static assets (CSS, JS, images)
            "/index.jsp",
            "/");

    /**
     * Danh sách các extension không cần check authentication.
     */
    private static final List<String> PUBLIC_EXTENSIONS = Arrays.asList(
            ".css", ".js", ".png", ".jpg", ".jpeg", ".gif", ".ico", ".woff", ".woff2", ".ttf");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Không cần initialization đặc biệt
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();

        // Lấy path tương đối (không bao gồm context path)
        String path = requestURI.substring(contextPath.length());

        // Kiểm tra nếu là public resource
        if (isPublicResource(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Kiểm tra session
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute(Constants.SESSION_USER) != null) {
            // Đã đăng nhập - cho phép truy cập
            chain.doFilter(request, response);
        } else {
            // Chưa đăng nhập - redirect về login
            // Lưu URL đang truy cập để redirect sau khi login
            String targetUrl = requestURI;
            if (request.getQueryString() != null) {
                targetUrl += "?" + request.getQueryString();
            }
            session = request.getSession(true);
            session.setAttribute("redirectUrl", targetUrl);

            response.sendRedirect(contextPath + Constants.PAGE_LOGIN);
        }
    }

    @Override
    public void destroy() {
        // Không cần cleanup đặc biệt
    }

    /**
     * Kiểm tra xem path có phải là public resource không.
     * 
     * @param path Request path (không bao gồm context)
     * @return true nếu là public resource
     */
    private boolean isPublicResource(String path) {
        // Kiểm tra empty path hoặc root
        if (path == null || path.isEmpty() || path.equals("/")) {
            return true;
        }

        // Kiểm tra public patterns
        for (String pattern : PUBLIC_PATTERNS) {
            if (path.equals(pattern) || path.startsWith(pattern)) {
                return true;
            }
        }

        // Kiểm tra public extensions
        String lowerPath = path.toLowerCase();
        for (String ext : PUBLIC_EXTENSIONS) {
            if (lowerPath.endsWith(ext)) {
                return true;
            }
        }

        return false;
    }
}

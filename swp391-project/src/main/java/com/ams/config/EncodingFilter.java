package com.ams.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Filter đảm bảo encoding UTF-8 cho tất cả request và response.
 * Áp dụng cho toàn bộ ứng dụng (/*).
 * 
 * Cần thiết để hiển thị đúng tiếng Việt và các ký tự đặc biệt.
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = "/*")
public class EncodingFilter implements Filter {

    private static final String ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Không cần initialization đặc biệt
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Set encoding cho request (form data, parameters)
        request.setCharacterEncoding(ENCODING);

        // Set encoding cho response (HTML output)
        response.setCharacterEncoding(ENCODING);
        response.setContentType(CONTENT_TYPE);

        // Tiếp tục filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Không cần cleanup đặc biệt
    }
}

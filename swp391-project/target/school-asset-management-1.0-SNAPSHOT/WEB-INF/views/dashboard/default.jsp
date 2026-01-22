<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <c:set var="currentPage" value="dashboard" scope="request" />
        <c:set var="pageTitle" value="Dashboard" scope="request" />
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>${pageTitle}</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
        </head>

        <body>
            <div class="dashboard-layout">
                <%@ include file="/WEB-INF/views/layouts/sidebar.jsp" %>

                    <main class="main-content">
                        <%@ include file="/WEB-INF/views/layouts/header.jsp" %>

                            <div class="content">
                                <div class="card">
                                    <div class="card-header">
                                        <h2>👋 Chào mừng đến với Hệ thống Quản lý Tài sản</h2>
                                    </div>
                                    <div class="card-body">
                                        <p>Bạn đã đăng nhập thành công với tài khoản: <strong>${user.username}</strong>
                                        </p>
                                        <p>Vai trò hiện tại: <strong>${roleName}</strong></p>
                                        <br>
                                        <p>Sử dụng menu bên trái để điều hướng đến các chức năng của hệ thống.</p>
                                    </div>
                                </div>
                            </div>
                    </main>
            </div>
        </body>

        </html>
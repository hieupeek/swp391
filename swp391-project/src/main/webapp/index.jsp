<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>School Asset Management System</title>
            <style>
                * {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                }

                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
                    min-height: 100vh;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }

                .welcome-container {
                    text-align: center;
                    color: #fff;
                    padding: 40px;
                }

                .logo {
                    font-size: 80px;
                    margin-bottom: 20px;
                }

                h1 {
                    font-size: 36px;
                    margin-bottom: 10px;
                    font-weight: 300;
                }

                .subtitle {
                    font-size: 18px;
                    opacity: 0.8;
                    margin-bottom: 40px;
                }

                .btn-login {
                    display: inline-block;
                    padding: 15px 50px;
                    background: #fff;
                    color: #1e3c72;
                    text-decoration: none;
                    border-radius: 30px;
                    font-weight: 600;
                    font-size: 16px;
                    transition: transform 0.3s, box-shadow 0.3s;
                }

                .btn-login:hover {
                    transform: translateY(-3px);
                    box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2);
                }

                .features {
                    display: flex;
                    gap: 30px;
                    margin-top: 60px;
                    flex-wrap: wrap;
                    justify-content: center;
                }

                .feature {
                    background: rgba(255, 255, 255, 0.1);
                    padding: 25px;
                    border-radius: 15px;
                    width: 200px;
                    backdrop-filter: blur(10px);
                }

                .feature-icon {
                    font-size: 40px;
                    margin-bottom: 15px;
                }

                .feature-title {
                    font-size: 16px;
                    font-weight: 600;
                    margin-bottom: 8px;
                }

                .feature-desc {
                    font-size: 13px;
                    opacity: 0.7;
                }
            </style>
        </head>

        <body>
            <div class="welcome-container">
                <div class="logo">🏫</div>
                <h1>Hệ thống Quản lý Tài sản</h1>
                <p class="subtitle">School Asset Management System - SWP391</p>

                <a href="${pageContext.request.contextPath}/login" class="btn-login">
                    Đăng nhập hệ thống
                </a>

                <div class="features">
                    <div class="feature">
                        <div class="feature-icon">📦</div>
                        <div class="feature-title">Quản lý Tài sản</div>
                        <div class="feature-desc">Theo dõi toàn bộ vòng đời tài sản</div>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">🔄</div>
                        <div class="feature-title">Điều chuyển</div>
                        <div class="feature-desc">Quy trình điều chuyển minh bạch</div>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">🛠️</div>
                        <div class="feature-title">Bảo trì</div>
                        <div class="feature-desc">Báo cáo và theo dõi sửa chữa</div>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">📊</div>
                        <div class="feature-title">Báo cáo</div>
                        <div class="feature-desc">Dashboard và xuất báo cáo</div>
                    </div>
                </div>
            </div>
        </body>

        </html>
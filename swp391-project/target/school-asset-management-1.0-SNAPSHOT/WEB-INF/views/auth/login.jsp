<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Đăng nhập - School Asset Management</title>
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
                    padding: 20px;
                }

                .login-container {
                    background: #fff;
                    border-radius: 20px;
                    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
                    overflow: hidden;
                    width: 100%;
                    max-width: 420px;
                }

                .login-header {
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    color: #fff;
                    padding: 40px 30px;
                    text-align: center;
                }

                .login-header .logo {
                    font-size: 60px;
                    margin-bottom: 15px;
                }

                .login-header h1 {
                    font-size: 22px;
                    font-weight: 500;
                }

                .login-header p {
                    font-size: 14px;
                    opacity: 0.8;
                    margin-top: 8px;
                }

                .login-form {
                    padding: 40px 30px;
                }

                .form-group {
                    margin-bottom: 25px;
                }

                .form-group label {
                    display: block;
                    font-size: 14px;
                    color: #555;
                    margin-bottom: 8px;
                    font-weight: 500;
                }

                .form-group input {
                    width: 100%;
                    padding: 14px 16px;
                    border: 2px solid #e0e0e0;
                    border-radius: 10px;
                    font-size: 15px;
                    transition: all 0.3s ease;
                    outline: none;
                }

                .form-group input:focus {
                    border-color: #667eea;
                    box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
                }

                .btn-login {
                    width: 100%;
                    padding: 16px;
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    border: none;
                    border-radius: 10px;
                    color: #fff;
                    font-size: 16px;
                    font-weight: 600;
                    cursor: pointer;
                    transition: all 0.3s ease;
                }

                .btn-login:hover {
                    transform: translateY(-2px);
                    box-shadow: 0 10px 30px rgba(102, 126, 234, 0.4);
                }

                .alert {
                    padding: 14px 16px;
                    border-radius: 10px;
                    margin-bottom: 20px;
                    font-size: 14px;
                }

                .alert-error {
                    background: #fff5f5;
                    border: 1px solid #fed7d7;
                    color: #c53030;
                }

                .alert-success {
                    background: #f0fff4;
                    border: 1px solid #c6f6d5;
                    color: #276749;
                }

                .back-link {
                    display: block;
                    text-align: center;
                    margin-top: 20px;
                    color: #667eea;
                    text-decoration: none;
                    font-size: 14px;
                }

                .back-link:hover {
                    text-decoration: underline;
                }

                .test-accounts {
                    margin-top: 30px;
                    padding: 20px;
                    background: #f8f9fa;
                    border-radius: 10px;
                    font-size: 12px;
                }

                .test-accounts h4 {
                    color: #555;
                    margin-bottom: 10px;
                    font-size: 13px;
                }

                .test-accounts table {
                    width: 100%;
                    border-collapse: collapse;
                }

                .test-accounts td {
                    padding: 6px 0;
                    color: #666;
                }

                .test-accounts code {
                    background: #e2e8f0;
                    padding: 2px 6px;
                    border-radius: 4px;
                    font-family: 'Courier New', monospace;
                }
            </style>
        </head>

        <body>
            <div class="login-container">
                <div class="login-header">
                    <div class="logo">🏫</div>
                    <h1>School Asset Management</h1>
                    <p>Hệ thống Quản lý Tài sản Trường học</p>
                </div>

                <div class="login-form">
                    <%-- Hiển thị thông báo lỗi --%>
                        <c:if test="${not empty error}">
                            <div class="alert alert-error">
                                ${error}
                            </div>
                        </c:if>

                        <%-- Hiển thị thông báo đăng xuất --%>
                            <c:if test="${param.logout == 'success'}">
                                <div class="alert alert-success">
                                    Đăng xuất thành công!
                                </div>
                            </c:if>

                            <form action="${pageContext.request.contextPath}/login" method="POST">
                                <div class="form-group">
                                    <label for="username">Tên đăng nhập</label>
                                    <input type="text" id="username" name="username" value="${username}"
                                        placeholder="Nhập tên đăng nhập" autocomplete="username" required>
                                </div>

                                <div class="form-group">
                                    <label for="password">Mật khẩu</label>
                                    <input type="password" id="password" name="password" placeholder="Nhập mật khẩu"
                                        autocomplete="current-password" required>
                                </div>

                                <button type="submit" class="btn-login">
                                    Đăng nhập
                                </button>
                            </form>

                            <a href="${pageContext.request.contextPath}/" class="back-link">
                                ← Về trang chủ
                            </a>

                            <%-- Thông tin tài khoản test (Chỉ hiển thị trong development) --%>
                                <div class="test-accounts">
                                    <h4>📋 Tài khoản thử nghiệm (password: 123456)</h4>
                                    <table>
                                        <tr>
                                            <td><code>principal</code></td>
                                            <td>Hiệu trưởng</td>
                                        </tr>
                                        <tr>
                                            <td><code>finance</code></td>
                                            <td>Trưởng phòng TC-KT</td>
                                        </tr>
                                        <tr>
                                            <td><code>staff</code></td>
                                            <td>Nhân viên thiết bị</td>
                                        </tr>
                                        <tr>
                                            <td><code>hod_it</code></td>
                                            <td>Trưởng bộ môn Tin học</td>
                                        </tr>
                                    </table>
                                </div>
                </div>
            </div>
        </body>

        </html>
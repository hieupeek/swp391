<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page isErrorPage="true" %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>403 - Truy cập bị từ chối</title>
            <style>
                * {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                }

                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
                    min-height: 100vh;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    color: #fff;
                }

                .error-container {
                    text-align: center;
                    padding: 40px;
                    background: rgba(255, 255, 255, 0.1);
                    border-radius: 20px;
                    backdrop-filter: blur(10px);
                    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
                }

                .error-icon {
                    font-size: 80px;
                    margin-bottom: 20px;
                }

                .error-code {
                    font-size: 100px;
                    font-weight: 700;
                    margin-bottom: 10px;
                }

                .error-title {
                    font-size: 24px;
                    margin-bottom: 20px;
                }

                .error-message {
                    font-size: 16px;
                    opacity: 0.8;
                    margin-bottom: 30px;
                    max-width: 400px;
                }

                .btn-home {
                    display: inline-block;
                    padding: 12px 30px;
                    background: #fff;
                    color: #f5576c;
                    text-decoration: none;
                    border-radius: 30px;
                    font-weight: 600;
                    transition: transform 0.3s, box-shadow 0.3s;
                }

                .btn-home:hover {
                    transform: translateY(-3px);
                    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
                }
            </style>
        </head>

        <body>
            <div class="error-container">
                <div class="error-icon">🔒</div>
                <div class="error-code">403</div>
                <div class="error-title">Truy cập bị từ chối</div>
                <div class="error-message">
                    Bạn không có quyền truy cập trang này. Vui lòng liên hệ quản trị viên nếu bạn cho rằng đây là lỗi.
                </div>
                <a href="${pageContext.request.contextPath}/" class="btn-home">
                    ← Về trang chủ
                </a>
            </div>
        </body>

        </html>
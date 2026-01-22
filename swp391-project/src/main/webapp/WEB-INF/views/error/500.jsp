<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page isErrorPage="true" %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>500 - Lỗi hệ thống</title>
            <style>
                * {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                }

                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
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

                .btn-group {
                    display: flex;
                    gap: 15px;
                    justify-content: center;
                }

                .btn {
                    display: inline-block;
                    padding: 12px 30px;
                    background: #fff;
                    color: #4facfe;
                    text-decoration: none;
                    border-radius: 30px;
                    font-weight: 600;
                    transition: transform 0.3s, box-shadow 0.3s;
                    border: none;
                    cursor: pointer;
                    font-size: 14px;
                }

                .btn:hover {
                    transform: translateY(-3px);
                    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
                }

                .btn-outline {
                    background: transparent;
                    border: 2px solid #fff;
                    color: #fff;
                }
            </style>
        </head>

        <body>
            <div class="error-container">
                <div class="error-icon">⚠️</div>
                <div class="error-code">500</div>
                <div class="error-title">Lỗi hệ thống</div>
                <div class="error-message">
                    Đã xảy ra lỗi không mong muốn. Đội ngũ kỹ thuật đã được thông báo. Vui lòng thử lại sau.
                </div>
                <div class="btn-group">
                    <a href="${pageContext.request.contextPath}/" class="btn">
                        ← Về trang chủ
                    </a>
                    <button onclick="location.reload()" class="btn btn-outline">
                        ↻ Thử lại
                    </button>
                </div>
            </div>
        </body>

        </html>
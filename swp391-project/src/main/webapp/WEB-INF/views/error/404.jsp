<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Không tìm thấy trang</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #fff;
        }
        .error-container {
            text-align: center;
            padding: 40px;
            background: rgba(255,255,255,0.1);
            border-radius: 20px;
            backdrop-filter: blur(10px);
            box-shadow: 0 8px 32px rgba(0,0,0,0.1);
        }
        .error-code { font-size: 120px; font-weight: 700; margin-bottom: 10px; }
        .error-title { font-size: 24px; margin-bottom: 20px; }
        .error-message { font-size: 16px; opacity: 0.8; margin-bottom: 30px; }
        .btn-home {
            display: inline-block;
            padding: 12px 30px;
            background: #fff;
            color: #667eea;
            text-decoration: none;
            border-radius: 30px;
            font-weight: 600;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        .btn-home:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.2);
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code">404</div>
        <div class="error-title">Không tìm thấy trang</div>
        <div class="error-message">
            Trang bạn đang tìm kiếm không tồn tại hoặc đã bị di chuyển.
        </div>
        <a href="${pageContext.request.contextPath}/" class="btn-home">
            ← Về trang chủ
        </a>
    </div>
</body>
</html>

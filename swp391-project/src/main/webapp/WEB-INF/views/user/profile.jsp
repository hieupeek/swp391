<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Hồ sơ cá nhân - AMS</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
            <style>
                .profile-container {
                    max-width: 800px;
                    margin: 0 auto;
                }

                .profile-card {
                    background: #fff;
                    border-radius: 16px;
                    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
                    overflow: hidden;
                }

                .profile-header {
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    color: #fff;
                    padding: 40px 30px;
                    text-align: center;
                }

                .profile-avatar {
                    width: 100px;
                    height: 100px;
                    border-radius: 50%;
                    background: rgba(255, 255, 255, 0.2);
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    font-size: 48px;
                    margin: 0 auto 15px;
                    border: 4px solid rgba(255, 255, 255, 0.3);
                }

                .profile-header h2 {
                    font-size: 24px;
                    margin-bottom: 5px;
                }

                .profile-header .role-tag {
                    background: rgba(255, 255, 255, 0.2);
                    padding: 6px 16px;
                    border-radius: 20px;
                    font-size: 14px;
                    display: inline-block;
                }

                .profile-body {
                    padding: 30px;
                }

                .form-section {
                    margin-bottom: 30px;
                }

                .form-section h3 {
                    font-size: 16px;
                    color: #333;
                    margin-bottom: 20px;
                    padding-bottom: 10px;
                    border-bottom: 2px solid #f0f0f0;
                }

                .form-row {
                    display: grid;
                    grid-template-columns: repeat(2, 1fr);
                    gap: 20px;
                    margin-bottom: 20px;
                }

                .form-group {
                    display: flex;
                    flex-direction: column;
                }

                .form-group.full-width {
                    grid-column: 1 / -1;
                }

                .form-group label {
                    font-size: 14px;
                    color: #555;
                    margin-bottom: 8px;
                    font-weight: 500;
                }

                .form-group input {
                    padding: 12px 16px;
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

                .form-group input:disabled {
                    background: #f5f5f5;
                    color: #888;
                    cursor: not-allowed;
                }

                .form-group .help-text {
                    font-size: 12px;
                    color: #888;
                    margin-top: 5px;
                }

                .btn-group {
                    display: flex;
                    gap: 15px;
                    justify-content: flex-end;
                    margin-top: 30px;
                    padding-top: 20px;
                    border-top: 2px solid #f0f0f0;
                }

                .btn {
                    padding: 12px 30px;
                    border: none;
                    border-radius: 10px;
                    font-size: 15px;
                    font-weight: 600;
                    cursor: pointer;
                    transition: all 0.3s ease;
                }

                .btn-primary {
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    color: #fff;
                }

                .btn-primary:hover {
                    transform: translateY(-2px);
                    box-shadow: 0 10px 30px rgba(102, 126, 234, 0.4);
                }

                .btn-secondary {
                    background: #f5f5f5;
                    color: #555;
                }

                .btn-secondary:hover {
                    background: #e0e0e0;
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

                .info-item {
                    display: flex;
                    justify-content: space-between;
                    padding: 12px 0;
                    border-bottom: 1px solid #f0f0f0;
                }

                .info-item:last-child {
                    border-bottom: none;
                }

                .info-label {
                    color: #888;
                    font-size: 14px;
                }

                .info-value {
                    color: #333;
                    font-weight: 500;
                }

                @media (max-width: 768px) {
                    .form-row {
                        grid-template-columns: 1fr;
                    }
                }
            </style>
        </head>

        <body>
            <div class="dashboard-layout">
                <%@ include file="/WEB-INF/views/layouts/sidebar.jsp" %>

                    <main class="main-content">
                        <%@ include file="/WEB-INF/views/layouts/header.jsp" %>

                            <div class="content">
                                <div class="profile-container">
                                    <div class="profile-card">
                                        <div class="profile-header">
                                            <div class="profile-avatar">👤</div>
                                            <h2>${user.fullName}</h2>
                                            <span class="role-tag">
                                                <c:choose>
                                                    <c:when test="${roleName == 'Principal'}">Hiệu trưởng</c:when>
                                                    <c:when test="${roleName == 'Finance_Head'}">Trưởng phòng TC-KT
                                                    </c:when>
                                                    <c:when test="${roleName == 'Asset_Staff'}">Nhân viên Thiết bị
                                                    </c:when>
                                                    <c:when test="${roleName == 'Head_of_Dept'}">Trưởng Bộ môn</c:when>
                                                    <c:otherwise>${roleName}</c:otherwise>
                                                </c:choose>
                                            </span>
                                        </div>

                                        <div class="profile-body">
                                            <c:if test="${not empty error}">
                                                <div class="alert alert-error">${error}</div>
                                            </c:if>

                                            <c:if test="${not empty success}">
                                                <div class="alert alert-success">${success}</div>
                                            </c:if>

                                            <form action="${pageContext.request.contextPath}/profile" method="POST">
                                                <div class="form-section">
                                                    <h3>📋 Thông tin cơ bản</h3>

                                                    <div class="form-row">
                                                        <div class="form-group">
                                                            <label for="username">Tên đăng nhập</label>
                                                            <input type="text" id="username" value="${user.username}"
                                                                disabled>
                                                            <span class="help-text">Không thể thay đổi</span>
                                                        </div>

                                                        <div class="form-group">
                                                            <label for="fullName">Họ và tên</label>
                                                            <input type="text" id="fullName" name="fullName"
                                                                value="${user.fullName}" required>
                                                        </div>
                                                    </div>

                                                    <div class="form-row">
                                                        <div class="form-group">
                                                            <label for="email">Email</label>
                                                            <input type="email" id="email" name="email"
                                                                value="${user.email}" required>
                                                        </div>

                                                        <div class="form-group">
                                                            <label for="phone">Số điện thoại</label>
                                                            <input type="tel" id="phone" name="phone"
                                                                value="${user.phone}" pattern="[0-9]{10,11}"
                                                                placeholder="VD: 0901234567">
                                                            <span class="help-text">10-11 chữ số</span>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-section">
                                                    <h3>🏢 Thông tin tổ chức</h3>

                                                    <div class="info-item">
                                                        <span class="info-label">Vai trò</span>
                                                        <span class="info-value">
                                                            <c:choose>
                                                                <c:when test="${roleName == 'Principal'}">Hiệu trưởng
                                                                </c:when>
                                                                <c:when test="${roleName == 'Finance_Head'}">Trưởng
                                                                    phòng TC-KT</c:when>
                                                                <c:when test="${roleName == 'Asset_Staff'}">Nhân viên
                                                                    Thiết bị</c:when>
                                                                <c:when test="${roleName == 'Head_of_Dept'}">Trưởng Bộ
                                                                    môn</c:when>
                                                                <c:otherwise>${roleName}</c:otherwise>
                                                            </c:choose>
                                                        </span>
                                                    </div>

                                                    <c:if test="${not empty user.department}">
                                                        <div class="info-item">
                                                            <span class="info-label">Bộ môn / Phòng ban</span>
                                                            <span class="info-value">${user.department.deptName}</span>
                                                        </div>
                                                    </c:if>

                                                    <div class="info-item">
                                                        <span class="info-label">Trạng thái</span>
                                                        <span class="info-value">
                                                            <c:choose>
                                                                <c:when test="${user.status == 'Active'}">
                                                                    <span style="color: #27ae60;">● Hoạt động</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span style="color: #e74c3c;">● Ngừng hoạt
                                                                        động</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </span>
                                                    </div>

                                                    <div class="info-item">
                                                        <span class="info-label">Ngày tạo tài khoản</span>
                                                        <span class="info-value">${user.createdAt}</span>
                                                    </div>
                                                </div>

                                                <div class="btn-group">
                                                    <a href="${pageContext.request.contextPath}/dashboard"
                                                        class="btn btn-secondary">
                                                        Quay lại
                                                    </a>
                                                    <button type="submit" class="btn btn-primary">
                                                        💾 Lưu thay đổi
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                    </main>
            </div>
        </body>

        </html>
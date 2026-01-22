<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <c:set var="currentPage" value="dashboard" scope="request" />
        <c:set var="pageTitle" value="Dashboard - Trưởng Bộ môn" scope="request" />
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
                                <%-- KPI Cards --%>
                                    <div class="kpi-grid">
                                        <div class="kpi-card blue">
                                            <div class="kpi-icon">📦</div>
                                            <div class="kpi-info">
                                                <h3>Tài sản phòng</h3>
                                                <p class="kpi-value">45</p>
                                            </div>
                                        </div>
                                        <div class="kpi-card orange">
                                            <div class="kpi-icon">🔄</div>
                                            <div class="kpi-info">
                                                <h3>Chờ xác nhận</h3>
                                                <p class="kpi-value">3</p>
                                            </div>
                                        </div>
                                        <div class="kpi-card red">
                                            <div class="kpi-icon">🔧</div>
                                            <div class="kpi-info">
                                                <h3>Cần sửa chữa</h3>
                                                <p class="kpi-value">2</p>
                                            </div>
                                        </div>
                                        <div class="kpi-card green">
                                            <div class="kpi-icon">✅</div>
                                            <div class="kpi-info">
                                                <h3>Hoạt động tốt</h3>
                                                <p class="kpi-value">40</p>
                                            </div>
                                        </div>
                                    </div>

                                    <%-- Transfer Confirmations --%>
                                        <div class="card">
                                            <div class="card-header">
                                                <h2>🔄 Xác nhận điều chuyển</h2>
                                            </div>
                                            <div class="card-body">
                                                <table class="data-table">
                                                    <thead>
                                                        <tr>
                                                            <th>Loại</th>
                                                            <th>Tài sản</th>
                                                            <th>Từ → Đến</th>
                                                            <th>Ngày</th>
                                                            <th>Hành động</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td><span class="badge receive">Nhận</span></td>
                                                            <td>Laptop Dell Inspiron 15 (x3)</td>
                                                            <td>Kho → Phòng Tin học</td>
                                                            <td>22/01/2026</td>
                                                            <td>
                                                                <button class="btn btn-sm btn-success">Xác nhận
                                                                    nhận</button>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td><span class="badge handover">Bàn giao</span></td>
                                                            <td>Máy chiếu cũ</td>
                                                            <td>Phòng Tin học → Kho</td>
                                                            <td>21/01/2026</td>
                                                            <td>
                                                                <button class="btn btn-sm btn-warning">Xác nhận bàn
                                                                    giao</button>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>

                                        <%-- Quick Actions --%>
                                            <div class="card">
                                                <div class="card-header">
                                                    <h2>⚡ Thao tác nhanh</h2>
                                                </div>
                                                <div class="card-body">
                                                    <div class="quick-actions">
                                                        <a href="${pageContext.request.contextPath}/my-assets"
                                                            class="action-btn">
                                                            <span class="icon">📦</span>
                                                            <span>Xem Tài sản phòng</span>
                                                        </a>
                                                        <a href="${pageContext.request.contextPath}/maintenance-requests/create"
                                                            class="action-btn">
                                                            <span class="icon">🔧</span>
                                                            <span>Yêu cầu Sửa chữa</span>
                                                        </a>
                                                        <a href="${pageContext.request.contextPath}/transfers"
                                                            class="action-btn">
                                                            <span class="icon">🔄</span>
                                                            <span>Lịch sử Điều chuyển</span>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                            </div>
                    </main>
            </div>
        </body>

        </html>
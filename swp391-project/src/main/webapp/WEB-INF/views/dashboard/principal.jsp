<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <c:set var="currentPage" value="dashboard" scope="request" />
        <c:set var="pageTitle" value="Dashboard - Hiệu trưởng" scope="request" />
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
                                                <h3>Tổng Tài sản</h3>
                                                <p class="kpi-value">1,234</p>
                                            </div>
                                        </div>
                                        <div class="kpi-card green">
                                            <div class="kpi-icon">💰</div>
                                            <div class="kpi-info">
                                                <h3>Tổng Giá trị</h3>
                                                <p class="kpi-value">2.5 Tỷ</p>
                                            </div>
                                        </div>
                                        <div class="kpi-card orange">
                                            <div class="kpi-icon">⏳</div>
                                            <div class="kpi-info">
                                                <h3>Chờ Duyệt</h3>
                                                <p class="kpi-value">5</p>
                                            </div>
                                        </div>
                                        <div class="kpi-card red">
                                            <div class="kpi-icon">🔧</div>
                                            <div class="kpi-info">
                                                <h3>Đang Bảo trì</h3>
                                                <p class="kpi-value">12</p>
                                            </div>
                                        </div>
                                    </div>

                                    <%-- Pending Approvals --%>
                                        <div class="card">
                                            <div class="card-header">
                                                <h2>Yêu cầu chờ duyệt</h2>
                                            </div>
                                            <div class="card-body">
                                                <table class="data-table">
                                                    <thead>
                                                        <tr>
                                                            <th>Loại</th>
                                                            <th>Mô tả</th>
                                                            <th>Người tạo</th>
                                                            <th>Ngày</th>
                                                            <th>Hành động</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td><span class="badge procurement">Mua sắm</span></td>
                                                            <td>Kế hoạch mua sắm Q1/2026</td>
                                                            <td>Trần Thị Kế Toán</td>
                                                            <td>22/01/2026</td>
                                                            <td>
                                                                <button class="btn btn-sm btn-success">Duyệt</button>
                                                                <button class="btn btn-sm btn-danger">Từ chối</button>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td><span class="badge liquidation">Thanh lý</span></td>
                                                            <td>Thanh lý 10 máy tính cũ</td>
                                                            <td>Lê Văn Thiết Bị</td>
                                                            <td>21/01/2026</td>
                                                            <td>
                                                                <button class="btn btn-sm btn-success">Duyệt</button>
                                                                <button class="btn btn-sm btn-danger">Từ chối</button>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                            </div>
                    </main>
            </div>
        </body>

        </html>
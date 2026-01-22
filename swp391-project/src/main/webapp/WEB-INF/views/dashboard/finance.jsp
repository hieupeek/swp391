<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <c:set var="currentPage" value="dashboard" scope="request" />
        <c:set var="pageTitle" value="Dashboard - Trưởng phòng TC-KT" scope="request" />
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
                                            <div class="kpi-icon">📁</div>
                                            <div class="kpi-info">
                                                <h3>Danh mục</h3>
                                                <p class="kpi-value">25</p>
                                            </div>
                                        </div>
                                        <div class="kpi-card green">
                                            <div class="kpi-icon">📦</div>
                                            <div class="kpi-info">
                                                <h3>Tài sản</h3>
                                                <p class="kpi-value">1,234</p>
                                            </div>
                                        </div>
                                        <div class="kpi-card orange">
                                            <div class="kpi-icon">🛒</div>
                                            <div class="kpi-info">
                                                <h3>Kế hoạch mua</h3>
                                                <p class="kpi-value">3</p>
                                            </div>
                                        </div>
                                        <div class="kpi-card purple">
                                            <div class="kpi-icon">📊</div>
                                            <div class="kpi-info">
                                                <h3>Báo cáo</h3>
                                                <p class="kpi-value">8</p>
                                            </div>
                                        </div>
                                    </div>

                                    <%-- Quick Actions --%>
                                        <div class="card">
                                            <div class="card-header">
                                                <h2>⚡ Thao tác nhanh</h2>
                                            </div>
                                            <div class="card-body">
                                                <div class="quick-actions">
                                                    <a href="${pageContext.request.contextPath}/categories"
                                                        class="action-btn">
                                                        <span class="icon">📁</span>
                                                        <span>Quản lý Danh mục</span>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/assets/add"
                                                        class="action-btn">
                                                        <span class="icon">➕</span>
                                                        <span>Thêm Tài sản</span>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/procurement/create"
                                                        class="action-btn">
                                                        <span class="icon">🛒</span>
                                                        <span>Tạo Kế hoạch Mua sắm</span>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/reports"
                                                        class="action-btn">
                                                        <span class="icon">📊</span>
                                                        <span>Xem Báo cáo</span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>

                                        <%-- Recent Assets --%>
                                            <div class="card">
                                                <div class="card-header">
                                                    <h2>📦 Tài sản mới thêm gần đây</h2>
                                                </div>
                                                <div class="card-body">
                                                    <table class="data-table">
                                                        <thead>
                                                            <tr>
                                                                <th>Mã tài sản</th>
                                                                <th>Tên</th>
                                                                <th>Danh mục</th>
                                                                <th>Phòng</th>
                                                                <th>Ngày thêm</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr>
                                                                <td><code>LAP-001</code></td>
                                                                <td>Laptop Dell Inspiron 15</td>
                                                                <td>Máy tính xách tay</td>
                                                                <td>Phòng Tin học</td>
                                                                <td>22/01/2026</td>
                                                            </tr>
                                                            <tr>
                                                                <td><code>PRJ-002</code></td>
                                                                <td>Máy chiếu Epson EB-X51</td>
                                                                <td>Thiết bị trình chiếu</td>
                                                                <td>Phòng họp</td>
                                                                <td>21/01/2026</td>
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
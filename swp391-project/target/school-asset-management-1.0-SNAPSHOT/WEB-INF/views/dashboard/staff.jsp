<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <c:set var="currentPage" value="dashboard" scope="request" />
        <c:set var="pageTitle" value="Dashboard - Nhân viên Thiết bị" scope="request" />
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
                                            <div class="kpi-icon">🔄</div>
                                            <div class="kpi-info">
                                                <h3>Điều chuyển chờ</h3>
                                                <p class="kpi-value">8</p>
                                            </div>
                                        </div>
                                        <div class="kpi-card orange">
                                            <div class="kpi-icon">🔧</div>
                                            <div class="kpi-info">
                                                <h3>Bảo trì đang xử lý</h3>
                                                <p class="kpi-value">5</p>
                                            </div>
                                        </div>
                                        <div class="kpi-card green">
                                            <div class="kpi-icon">✅</div>
                                            <div class="kpi-info">
                                                <h3>Hoàn thành hôm nay</h3>
                                                <p class="kpi-value">12</p>
                                            </div>
                                        </div>
                                        <div class="kpi-card purple">
                                            <div class="kpi-icon">📦</div>
                                            <div class="kpi-info">
                                                <h3>Tổng Tài sản</h3>
                                                <p class="kpi-value">1,234</p>
                                            </div>
                                        </div>
                                    </div>

                                    <%-- My Tasks --%>
                                        <div class="card">
                                            <div class="card-header">
                                                <h2>📋 Công việc cần làm</h2>
                                            </div>
                                            <div class="card-body">
                                                <table class="data-table">
                                                    <thead>
                                                        <tr>
                                                            <th>Loại</th>
                                                            <th>Mô tả</th>
                                                            <th>Từ → Đến</th>
                                                            <th>Độ ưu tiên</th>
                                                            <th>Hành động</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td><span class="badge transfer">Điều chuyển</span></td>
                                                            <td>Di chuyển 5 máy tính</td>
                                                            <td>P.101 → P.205</td>
                                                            <td><span class="priority high">Cao</span></td>
                                                            <td>
                                                                <button class="btn btn-sm btn-primary">Xử lý</button>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td><span class="badge maintenance">Bảo trì</span></td>
                                                            <td>Sửa máy chiếu hỏng</td>
                                                            <td>P.301</td>
                                                            <td><span class="priority medium">Trung bình</span></td>
                                                            <td>
                                                                <button class="btn btn-sm btn-primary">Xử lý</button>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td><span class="badge transfer">Điều chuyển</span></td>
                                                            <td>Bàn giao máy in mới</td>
                                                            <td>Kho → VP Tài chính</td>
                                                            <td><span class="priority low">Thấp</span></td>
                                                            <td>
                                                                <button class="btn btn-sm btn-primary">Xử lý</button>
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
                                                        <a href="${pageContext.request.contextPath}/assets"
                                                            class="action-btn">
                                                            <span class="icon">📦</span>
                                                            <span>Tra cứu Tài sản</span>
                                                        </a>
                                                        <a href="${pageContext.request.contextPath}/transfers"
                                                            class="action-btn">
                                                            <span class="icon">🔄</span>
                                                            <span>Xử lý Điều chuyển</span>
                                                        </a>
                                                        <a href="${pageContext.request.contextPath}/maintenance"
                                                            class="action-btn">
                                                            <span class="icon">🔧</span>
                                                            <span>Quản lý Bảo trì</span>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                            </div>
                    </main>
            </div>
        </body>

        </html>
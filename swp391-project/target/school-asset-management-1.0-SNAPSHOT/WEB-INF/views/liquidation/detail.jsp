<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
            <!DOCTYPE html>
            <html lang="vi">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>${pageTitle}</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
                <style>
                    .detail-grid {
                        display: grid;
                        grid-template-columns: 1fr 2fr;
                        gap: 20px;
                    }

                    .info-card {
                        background: #fff;
                        padding: 20px;
                        border-radius: 12px;
                        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
                    }

                    .info-row {
                        display: flex;
                        margin-bottom: 12px;
                        border-bottom: 1px solid #f3f4f6;
                        padding-bottom: 8px;
                    }

                    .info-label {
                        width: 120px;
                        font-weight: 500;
                        color: #6b7280;
                    }

                    .info-value {
                        flex: 1;
                        color: #111827;
                    }

                    .status-pending {
                        background: #fef3c7;
                        color: #92400e;
                        padding: 4px 10px;
                        border-radius: 4px;
                    }

                    .status-approved {
                        background: #d1fae5;
                        color: #065f46;
                        padding: 4px 10px;
                        border-radius: 4px;
                    }

                    .status-rejected {
                        background: #fee2e2;
                        color: #b91c1c;
                        padding: 4px 10px;
                        border-radius: 4px;
                    }

                    .section-title {
                        font-size: 16px;
                        font-weight: 600;
                        margin-bottom: 15px;
                        color: #1f2937;
                    }

                    .action-buttons {
                        display: flex;
                        gap: 10px;
                        margin-top: 20px;
                    }
                </style>
            </head>

            <body>
                <div class="dashboard-layout">
                    <%@ include file="/WEB-INF/views/layouts/sidebar.jsp" %>

                        <main class="main-content">
                            <%@ include file="/WEB-INF/views/layouts/header.jsp" %>

                                <div class="content">
                                    <div class="page-header"
                                        style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                                        <h2>Chi tiết Biên bản Thanh lý #${minute.minuteId}</h2>
                                        <a href="${pageContext.request.contextPath}/liquidation"
                                            class="btn btn-secondary">Quay lại</a>
                                    </div>

                                    <div class="detail-grid">
                                        <!-- Left: Minute Info -->
                                        <div class="info-card">
                                            <h3 class="section-title">Thông tin Biên bản</h3>

                                            <div class="info-row">
                                                <span class="info-label">Mã:</span>
                                                <span class="info-value"><code>#${minute.minuteId}</code></span>
                                            </div>
                                            <div class="info-row">
                                                <span class="info-label">Ngày tạo:</span>
                                                <span class="info-value">
                                                    <fmt:formatDate value="${minute.createdDateAsDate}"
                                                        pattern="dd/MM/yyyy HH:mm" />
                                                </span>
                                            </div>
                                            <div class="info-row">
                                                <span class="info-label">Người tạo:</span>
                                                <span class="info-value">${minute.creator.fullName}</span>
                                            </div>
                                            <div class="info-row">
                                                <span class="info-label">Trạng thái:</span>
                                                <span class="info-value">
                                                    <span
                                                        class="status-${minute.status.toLowerCase()}">${minute.status}</span>
                                                </span>
                                            </div>
                                            <c:if test="${minute.approver != null}">
                                                <div class="info-row">
                                                    <span class="info-label">Người duyệt:</span>
                                                    <span class="info-value">${minute.approver.fullName}</span>
                                                </div>
                                                <div class="info-row">
                                                    <span class="info-label">Ngày duyệt:</span>
                                                    <span class="info-value">
                                                        <fmt:formatDate value="${minute.approvedDateAsDate}"
                                                            pattern="dd/MM/yyyy HH:mm" />
                                                    </span>
                                                </div>
                                            </c:if>
                                            <div class="info-row" style="border-bottom: none;">
                                                <span class="info-label">Ghi chú:</span>
                                                <span class="info-value">${minute.note}</span>
                                            </div>

                                            <c:if test="${isPrincipal && minute.status == 'Pending'}">
                                                <div class="action-buttons">
                                                    <form action="${pageContext.request.contextPath}/liquidation"
                                                        method="post">
                                                        <input type="hidden" name="action" value="approve">
                                                        <input type="hidden" name="id" value="${minute.minuteId}">
                                                        <button type="submit" class="btn btn-success">Duyệt Biên
                                                            bản</button>
                                                    </form>
                                                    <form action="${pageContext.request.contextPath}/liquidation"
                                                        method="post">
                                                        <input type="hidden" name="action" value="reject">
                                                        <input type="hidden" name="id" value="${minute.minuteId}">
                                                        <button type="submit" class="btn btn-danger">Từ chối</button>
                                                    </form>
                                                </div>
                                            </c:if>
                                        </div>

                                        <!-- Right: Asset List -->
                                        <div class="info-card">
                                            <h3 class="section-title">Danh sách Tài sản Thanh lý (${details.size()}
                                                items)</h3>

                                            <table class="data-table">
                                                <thead>
                                                    <tr>
                                                        <th>Mã TS</th>
                                                        <th>Tên Tài sản</th>
                                                        <th>Trạng thái</th>
                                                        <th>Lý do thanh lý</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="detail" items="${details}">
                                                        <tr>
                                                            <td><code>${detail.asset.assetCode}</code></td>
                                                            <td>${detail.asset.assetName}</td>
                                                            <td>
                                                                <span
                                                                    class="status-badge status-${detail.asset.status.toLowerCase()}">
                                                                    ${detail.asset.status}
                                                                </span>
                                                            </td>
                                                            <td>${detail.reason}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                        </main>
                </div>
            </body>

            </html>
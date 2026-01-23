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
                        grid-template-columns: 1fr 1fr;
                        gap: 20px;
                    }

                    .section-title {
                        font-size: 18px;
                        font-weight: 600;
                        color: #1f2937;
                        margin-bottom: 15px;
                        padding-bottom: 10px;
                        border-bottom: 2px solid #e5e7eb;
                    }

                    .info-row {
                        display: flex;
                        margin-bottom: 12px;
                        border-bottom: 1px solid #f3f4f6;
                        padding-bottom: 8px;
                    }

                    .info-label {
                        width: 150px;
                        font-weight: 500;
                        color: #6b7280;
                    }

                    .info-value {
                        flex: 1;
                        color: #111827;
                        font-weight: 500;
                    }

                    /* Timeline style for history */
                    .history-list {
                        list-style: none;
                        padding: 0;
                        margin: 0;
                    }

                    .history-item {
                        position: relative;
                        padding-left: 20px;
                        margin-bottom: 20px;
                        border-left: 2px solid #e5e7eb;
                    }

                    .history-item::before {
                        content: '';
                        position: absolute;
                        left: -6px;
                        top: 0;
                        width: 10px;
                        height: 10px;
                        border-radius: 50%;
                        background: #3b82f6;
                        border: 2px solid #fff;
                    }

                    .history-date {
                        font-size: 12px;
                        color: #6b7280;
                        margin-bottom: 4px;
                    }

                    .history-action {
                        font-weight: 600;
                        color: #1f2937;
                    }

                    .history-desc {
                        font-size: 14px;
                        color: #4b5563;
                    }

                    .history-user {
                        font-size: 12px;
                        color: #6b7280;
                        margin-top: 4px;
                        font-style: italic;
                    }
                </style>
            </head>

            <body>
                <div class="dashboard-layout">
                    <%@ include file="/WEB-INF/views/layouts/sidebar.jsp" %>

                        <main class="main-content">
                            <%@ include file="/WEB-INF/views/layouts/header.jsp" %>

                                <div class="content">
                                    <div class="page-header">
                                        <h2>Chi tiết Tài sản: ${asset.assetName}</h2>
                                        <div>
                                            <a href="${pageContext.request.contextPath}/assets"
                                                class="btn btn-secondary">Quay lại</a>
                                            <a href="${pageContext.request.contextPath}/assets?action=edit&id=${asset.assetId}"
                                                class="btn btn-primary">Chỉnh sửa</a>
                                        </div>
                                    </div>

                                    <div class="detail-grid">
                                        <!-- Left Column: Asset Info -->
                                        <div class="card">
                                            <div class="card-body">
                                                <h3 class="section-title">Thông tin chung</h3>

                                                <div class="info-row">
                                                    <span class="info-label">Mã tài sản:</span>
                                                    <span class="info-value"><code>${asset.assetCode}</code></span>
                                                </div>
                                                <div class="info-row">
                                                    <span class="info-label">Tên tài sản:</span>
                                                    <span class="info-value">${asset.assetName}</span>
                                                </div>
                                                <div class="info-row">
                                                    <span class="info-label">Danh mục:</span>
                                                    <span class="info-value">${asset.category.categoryName}</span>
                                                </div>
                                                <div class="info-row">
                                                    <span class="info-label">Vị trí:</span>
                                                    <span class="info-value">${asset.room != null ? asset.room.roomName
                                                        : 'Chưa phân bổ'}</span>
                                                </div>
                                                <div class="info-row">
                                                    <span class="info-label">Nhà cung cấp:</span>
                                                    <span class="info-value">${asset.supplier != null ?
                                                        asset.supplier.supplierName : 'Không xác định'}</span>
                                                </div>
                                                <div class="info-row">
                                                    <span class="info-label">Trạng thái:</span>
                                                    <span class="info-value">
                                                        <span
                                                            class="status-badge status-${asset.status.toLowerCase()}">${asset.status}</span>
                                                    </span>
                                                </div>
                                                <div class="info-row">
                                                    <span class="info-label">Giá trị:</span>
                                                    <span class="info-value">
                                                        <fmt:formatNumber value="${asset.price}" type="currency"
                                                            currencySymbol="₫" />
                                                    </span>
                                                </div>
                                                <div class="info-row">
                                                    <span class="info-label">Ngày mua:</span>
                                                    <span class="info-value">${asset.purchaseDate}</span>
                                                </div>
                                                <div class="info-row">
                                                    <span class="info-label">Hết hạn bảo hành:</span>
                                                    <span class="info-value">${asset.warrantyExpiryDate}</span>
                                                </div>
                                                <div class="info-row" style="border-bottom: none;">
                                                    <span class="info-label">Mô tả:</span>
                                                    <span class="info-value">${asset.description}</span>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Right Column: History -->
                                        <div class="card">
                                            <div class="card-body">
                                                <h3 class="section-title">Lịch sử hoạt động</h3>

                                                <c:if test="${empty historyList}">
                                                    <p class="text-muted">Chưa có lịch sử.</p>
                                                </c:if>

                                                <div class="history-list">
                                                    <c:forEach var="history" items="${historyList}">
                                                        <div class="history-item">
                                                            <div class="history-date">
                                                                <fmt:formatDate value="${history.actionDateAsDate}"
                                                                    pattern="dd/MM/yyyy HH:mm" />
                                                            </div>
                                                            <div class="history-action">${history.action}</div>
                                                            <div class="history-desc">${history.description}</div>
                                                            <div class="history-user">
                                                                Thực hiện bởi: ${history.performer != null ?
                                                                history.performer.fullName : 'Hệ thống'}
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                        </main>
                </div>
            </body>

            </html>
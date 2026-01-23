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
                    .form-group {
                        margin-bottom: 20px;
                    }

                    .form-group label {
                        display: block;
                        margin-bottom: 8px;
                        font-weight: 500;
                    }

                    .form-group input,
                    .form-group textarea {
                        width: 100%;
                        padding: 10px;
                        border: 1px solid #ddd;
                        border-radius: 6px;
                    }

                    .form-group textarea {
                        min-height: 100px;
                        resize: vertical;
                    }

                    .asset-selection {
                        margin-top: 20px;
                    }

                    .asset-item {
                        display: flex;
                        align-items: center;
                        gap: 15px;
                        padding: 15px;
                        background: #f8fafc;
                        border-radius: 8px;
                        margin-bottom: 10px;
                        border: 1px solid #e5e7eb;
                    }

                    .asset-item input[type="checkbox"] {
                        width: 20px;
                        height: 20px;
                    }

                    .asset-info {
                        flex: 1;
                    }

                    .asset-info .code {
                        font-family: monospace;
                        color: #6b7280;
                    }

                    .asset-info .name {
                        font-weight: 600;
                    }

                    .asset-info .status {
                        display: inline-block;
                        padding: 2px 8px;
                        border-radius: 4px;
                        font-size: 11px;
                        background: #fee2e2;
                        color: #b91c1c;
                    }

                    .reason-input {
                        width: 300px;
                        padding: 8px;
                        border: 1px solid #ddd;
                        border-radius: 4px;
                    }

                    .btn-group {
                        display: flex;
                        gap: 10px;
                        margin-top: 20px;
                    }

                    .empty-state {
                        text-align: center;
                        padding: 40px;
                        color: #6b7280;
                    }
                </style>
            </head>

            <body>
                <div class="dashboard-layout">
                    <%@ include file="/WEB-INF/views/layouts/sidebar.jsp" %>

                        <main class="main-content">
                            <%@ include file="/WEB-INF/views/layouts/header.jsp" %>

                                <div class="content">
                                    <div class="card">
                                        <div class="card-header">
                                            <h2>Tạo Biên bản Thanh lý</h2>
                                        </div>
                                        <div class="card-body">
                                            <c:if test="${not empty error}">
                                                <div class="alert alert-error">${error}</div>
                                            </c:if>

                                            <form action="${pageContext.request.contextPath}/liquidation" method="post">
                                                <input type="hidden" name="action" value="create">

                                                <div class="form-group">
                                                    <label for="note">Ghi chú / Lý do chung:</label>
                                                    <textarea id="note" name="note"
                                                        placeholder="VD: Thanh lý lô máy tính hết niên hạn sử dụng...">${param.note}</textarea>
                                                </div>

                                                <div class="asset-selection">
                                                    <h3>Chọn tài sản cần thanh lý:</h3>
                                                    <p style="color: #6b7280; margin-bottom: 15px;">
                                                        Chỉ hiển thị tài sản có trạng thái Broken, Maintenance hoặc Lost
                                                        và chưa có trong biên bản khác.
                                                    </p>

                                                    <c:choose>
                                                        <c:when test="${empty eligibleAssets}">
                                                            <div class="empty-state">
                                                                <p>Không có tài sản nào đủ điều kiện thanh lý.</p>
                                                                <p>Tài sản cần có trạng thái Broken, Maintenance hoặc
                                                                    Lost.</p>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach var="asset" items="${eligibleAssets}"
                                                                varStatus="i">
                                                                <div class="asset-item">
                                                                    <input type="checkbox" name="assetIds"
                                                                        value="${asset.assetId}"
                                                                        id="asset_${asset.assetId}">
                                                                    <div class="asset-info">
                                                                        <span class="code">${asset.assetCode}</span>
                                                                        <span class="name">${asset.assetName}</span>
                                                                        <span class="status">${asset.status}</span>
                                                                    </div>
                                                                    <input type="text" name="reasons"
                                                                        class="reason-input"
                                                                        placeholder="Lý do thanh lý chi tiết...">
                                                                </div>
                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>

                                                <div class="btn-group">
                                                    <button type="submit" class="btn btn-primary">Tạo Biên bản</button>
                                                    <a href="${pageContext.request.contextPath}/liquidation"
                                                        class="btn btn-secondary">Hủy</a>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                        </main>
                </div>
            </body>

            </html>
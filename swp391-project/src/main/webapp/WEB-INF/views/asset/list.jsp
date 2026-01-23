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
                    .page-header {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        margin-bottom: 25px;
                    }

                    .search-box {
                        display: flex;
                        gap: 10px;
                        background: #fff;
                        padding: 5px;
                        border-radius: 8px;
                        border: 1px solid #e0e0e0;
                        width: 300px;
                    }

                    .search-box input {
                        border: none;
                        outline: none;
                        flex: 1;
                        padding: 8px;
                        font-size: 14px;
                    }

                    /* Status Badges */
                    .status-badge {
                        padding: 4px 8px;
                        border-radius: 4px;
                        font-size: 12px;
                        font-weight: 600;
                    }

                    .status-new {
                        background: #d1fae5;
                        color: #065f46;
                    }

                    .status-in_use {
                        background: #dbeafe;
                        color: #1e40af;
                    }

                    .status-maintenance {
                        background: #fef3c7;
                        color: #92400e;
                    }

                    .status-broken {
                        background: #fee2e2;
                        color: #b91c1c;
                    }

                    .status-liquidated {
                        background: #f3f4f6;
                        color: #374151;
                    }

                    .status-lost {
                        background: #1f2937;
                        color: #f9fafb;
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
                                        <form action="${pageContext.request.contextPath}/assets" method="GET"
                                            class="search-form" style="display: flex; gap: 10px; align-items: center;">
                                            <div class="search-box">
                                                <input type="text" name="search"
                                                    placeholder="Tìm tên hoặc mã tài sản..." value="${searchKeyword}">
                                                <button type="submit">🔍</button>
                                            </div>
                                            <select name="status" onchange="this.form.submit()"
                                                style="padding: 10px; border: 1px solid #e0e0e0; border-radius: 8px; background: #fff;">
                                                <option value="">-- Tất cả trạng thái --</option>
                                                <option value="New" ${filterStatus=='New' ? 'selected' : '' }>New
                                                </option>
                                                <option value="In_Use" ${filterStatus=='In_Use' ? 'selected' : '' }>In
                                                    Use</option>
                                                <option value="Maintenance" ${filterStatus=='Maintenance' ? 'selected'
                                                    : '' }>Maintenance</option>
                                                <option value="Broken" ${filterStatus=='Broken' ? 'selected' : '' }>
                                                    Broken</option>
                                                <option value="Liquidated" ${filterStatus=='Liquidated' ? 'selected'
                                                    : '' }>Liquidated</option>
                                                <option value="Lost" ${filterStatus=='Lost' ? 'selected' : '' }>Lost
                                                </option>
                                            </select>
                                        </form>

                                        <a href="${pageContext.request.contextPath}/assets?action=create"
                                            class="btn btn-primary">
                                            + Thêm Tài sản
                                        </a>
                                    </div>

                                    <div class="card">
                                        <div class="card-body">
                                            <c:if test="${param.success == 'create'}">
                                                <div class="alert alert-success">Thêm tài sản mới thành công!</div>
                                            </c:if>
                                            <c:if test="${param.success == 'update'}">
                                                <div class="alert alert-success">Cập nhật tài sản thành công!</div>
                                            </c:if>
                                            <c:if test="${not empty error}">
                                                <div class="alert alert-error">${error}</div>
                                            </c:if>

                                            <table class="data-table">
                                                <thead>
                                                    <tr>
                                                        <th>Mã TS</th>
                                                        <th>Tên Tài sản</th>
                                                        <th>Loại</th>
                                                        <th>Vị trí</th>
                                                        <th>Trạng thái</th>
                                                        <th>Giá trị (VNĐ)</th>
                                                        <th width="100" style="text-align: right;">Hành động</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:choose>
                                                        <c:when test="${empty assets}">
                                                            <tr>
                                                                <td colspan="7"
                                                                    style="text-align: center; padding: 30px;">
                                                                    Chưa có tài sản nào.
                                                                </td>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach var="asset" items="${assets}">
                                                                <tr>
                                                                    <td><code>${asset.assetCode}</code></td>
                                                                    <td><strong>${asset.assetName}</strong></td>
                                                                    <td>${asset.category.categoryName}</td>
                                                                    <td>${asset.room != null ? asset.room.roomName :
                                                                        '-'}</td>
                                                                    <td>
                                                                        <span
                                                                            class="status-badge status-${asset.status.toLowerCase()}">
                                                                            ${asset.status}
                                                                        </span>
                                                                    </td>
                                                                    <td>
                                                                        <fmt:formatNumber value="${asset.price}"
                                                                            type="currency" currencySymbol="₫" />
                                                                    </td>
                                                                    <td style="text-align: right;">
                                                                        <a href="${pageContext.request.contextPath}/assets?action=detail&id=${asset.assetId}"
                                                                            class="btn btn-sm btn-secondary">
                                                                            Xem
                                                                        </a>
                                                                        <a href="${pageContext.request.contextPath}/assets?action=edit&id=${asset.assetId}"
                                                                            class="btn btn-sm btn-primary">
                                                                            Sửa
                                                                        </a>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                        </main>
                </div>
            </body>

            </html>
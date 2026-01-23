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

                    .status-pending {
                        background: #fef3c7;
                        color: #92400e;
                    }

                    .status-approved {
                        background: #d1fae5;
                        color: #065f46;
                    }

                    .status-rejected {
                        background: #fee2e2;
                        color: #b91c1c;
                    }

                    .status-completed {
                        background: #dbeafe;
                        color: #1e40af;
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
                                        <h2>Quản lý Thanh lý Tài sản</h2>
                                        <c:if
                                            test="${sessionScope.role == 'Finance_Head' || sessionScope.role == 'Asset_Staff'}">
                                            <a href="${pageContext.request.contextPath}/liquidation?action=create"
                                                class="btn btn-primary">
                                                + Tạo Biên bản Thanh lý
                                            </a>
                                        </c:if>
                                    </div>

                                    <div class="card">
                                        <div class="card-body">
                                            <c:if test="${param.success == 'create'}">
                                                <div class="alert alert-success">Tạo biên bản thanh lý thành công! Đang
                                                    chờ duyệt.</div>
                                            </c:if>
                                            <c:if test="${param.success == 'approve'}">
                                                <div class="alert alert-success">Đã duyệt biên bản. Tài sản đã được cập
                                                    nhật trạng thái Thanh lý.</div>
                                            </c:if>
                                            <c:if test="${param.success == 'reject'}">
                                                <div class="alert alert-success">Đã từ chối biên bản thanh lý.</div>
                                            </c:if>
                                            <c:if test="${not empty error}">
                                                <div class="alert alert-error">${error}</div>
                                            </c:if>

                                            <table class="data-table">
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Ngày tạo</th>
                                                        <th>Người tạo</th>
                                                        <th>Ghi chú</th>
                                                        <th>Trạng thái</th>
                                                        <th>Người duyệt</th>
                                                        <th style="text-align: right;">Hành động</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:choose>
                                                        <c:when test="${empty minutes}">
                                                            <tr>
                                                                <td colspan="7"
                                                                    style="text-align: center; padding: 30px;">
                                                                    Chưa có biên bản thanh lý nào.
                                                                </td>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach var="minute" items="${minutes}">
                                                                <tr>
                                                                    <td><code>#${minute.minuteId}</code></td>
                                                                    <td>
                                                                        <fmt:formatDate
                                                                            value="${minute.createdDateAsDate}"
                                                                            pattern="dd/MM/yyyy HH:mm" />
                                                                    </td>
                                                                    <td>${minute.creator.fullName}</td>
                                                                    <td>${minute.note}</td>
                                                                    <td>
                                                                        <span
                                                                            class="status-badge status-${minute.status.toLowerCase()}">
                                                                            ${minute.status}
                                                                        </span>
                                                                    </td>
                                                                    <td>
                                                                        <c:choose>
                                                                            <c:when test="${minute.approver != null}">
                                                                                ${minute.approver.fullName}
                                                                            </c:when>
                                                                            <c:otherwise>-</c:otherwise>
                                                                        </c:choose>
                                                                    </td>
                                                                    <td style="text-align: right;">
                                                                        <a href="${pageContext.request.contextPath}/liquidation?action=view&id=${minute.minuteId}"
                                                                            class="btn btn-sm btn-secondary">Xem</a>

                                                                        <c:if
                                                                            test="${isPrincipal && minute.status == 'Pending'}">
                                                                            <form
                                                                                action="${pageContext.request.contextPath}/liquidation"
                                                                                method="post" style="display: inline;">
                                                                                <input type="hidden" name="action"
                                                                                    value="approve">
                                                                                <input type="hidden" name="id"
                                                                                    value="${minute.minuteId}">
                                                                                <button type="submit"
                                                                                    class="btn btn-sm btn-success">Duyệt</button>
                                                                            </form>
                                                                            <form
                                                                                action="${pageContext.request.contextPath}/liquidation"
                                                                                method="post" style="display: inline;">
                                                                                <input type="hidden" name="action"
                                                                                    value="reject">
                                                                                <input type="hidden" name="id"
                                                                                    value="${minute.minuteId}">
                                                                                <button type="submit"
                                                                                    class="btn btn-sm btn-danger">Từ
                                                                                    chối</button>
                                                                            </form>
                                                                        </c:if>
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
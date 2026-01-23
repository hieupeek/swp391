<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
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

                .search-box button {
                    background: #f8f9fa;
                    border: none;
                    padding: 8px 12px;
                    border-radius: 6px;
                    cursor: pointer;
                    color: #555;
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
                                    <form action="${pageContext.request.contextPath}/suppliers" method="GET"
                                        class="search-form">
                                        <div class="search-box">
                                            <input type="text" name="search" placeholder="Tìm nhà cung cấp..."
                                                value="${searchKeyword}">
                                            <button type="submit">🔍</button>
                                        </div>
                                    </form>

                                    <c:if test="${canEdit}">
                                        <a href="${pageContext.request.contextPath}/suppliers?action=create"
                                            class="btn btn-primary">
                                            + Thêm NCC
                                        </a>
                                    </c:if>
                                </div>

                                <div class="card">
                                    <div class="card-body">
                                        <c:if test="${param.success == 'create'}">
                                            <div class="alert alert-success">Tạo nhà cung cấp thành công!</div>
                                        </c:if>
                                        <c:if test="${param.success == 'update'}">
                                            <div class="alert alert-success">Cập nhật nhà cung cấp thành công!</div>
                                        </c:if>
                                        <c:if test="${not empty error}">
                                            <div class="alert alert-error">${error}</div>
                                        </c:if>

                                        <table class="data-table">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Tên Nhà cung cấp</th>
                                                    <th>Người liên hệ</th>
                                                    <th>Email</th>
                                                    <th>Điện thoại</th>
                                                    <c:if test="${canEdit}">
                                                        <th width="100" style="text-align: right;">Hành động</th>
                                                    </c:if>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:choose>
                                                    <c:when test="${empty suppliers}">
                                                        <tr>
                                                            <td colspan="${canEdit ? 6 : 5}"
                                                                style="text-align: center; padding: 30px;">
                                                                Không tìm thấy dữ liệu.
                                                            </td>
                                                        </tr>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:forEach var="sup" items="${suppliers}">
                                                            <tr>
                                                                <td>${sup.supplierId}</td>
                                                                <td><strong>${sup.supplierName}</strong></td>
                                                                <td>${sup.contactPerson}</td>
                                                                <td>${sup.email}</td>
                                                                <td>${sup.phone}</td>
                                                                <c:if test="${canEdit}">
                                                                    <td style="text-align: right;">
                                                                        <a href="${pageContext.request.contextPath}/suppliers?action=edit&id=${sup.supplierId}"
                                                                            class="btn btn-sm btn-primary">
                                                                            Sửa
                                                                        </a>
                                                                    </td>
                                                                </c:if>
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
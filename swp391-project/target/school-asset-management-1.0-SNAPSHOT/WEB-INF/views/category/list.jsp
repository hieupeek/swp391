<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <c:set var="currentPage" value="categories" scope="request" />
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

                .search-box button:hover {
                    background: #e9ecef;
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
                                    <form action="${pageContext.request.contextPath}/categories" method="GET"
                                        class="search-form">
                                        <div class="search-box">
                                            <input type="text" name="search" placeholder="Tìm kiếm theo tên hoặc mã..."
                                                value="${searchKeyword}">
                                            <button type="submit">🔍</button>
                                        </div>
                                    </form>

                                    <a href="${pageContext.request.contextPath}/categories?action=create"
                                        class="btn btn-primary">
                                        + Thêm Danh mục
                                    </a>
                                </div>

                                <div class="card">
                                    <div class="card-body">
                                        <%-- Alerts --%>
                                            <c:if test="${param.success == 'create'}">
                                                <div class="alert alert-success">Tạo danh mục thành công!</div>
                                            </c:if>
                                            <c:if test="${param.success == 'update'}">
                                                <div class="alert alert-success">Cập nhật danh mục thành công!</div>
                                            </c:if>
                                            <c:if test="${not empty error}">
                                                <div class="alert alert-error">${error}</div>
                                            </c:if>

                                            <table class="data-table">
                                                <thead>
                                                    <tr>
                                                        <th>Mã Prefix</th>
                                                        <th>Tên Danh mục</th>
                                                        <th>Mô tả</th>
                                                        <th width="150" style="text-align: right;">Hành động</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:choose>
                                                        <c:when test="${empty categories}">
                                                            <tr>
                                                                <td colspan="4"
                                                                    style="text-align: center; padding: 30px;">
                                                                    Không tìm thấy danh mục nào.
                                                                </td>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach var="cat" items="${categories}">
                                                                <tr>
                                                                    <td><code>${cat.prefixCode}</code></td>
                                                                    <td>${cat.categoryName}</td>
                                                                    <td>${cat.description}</td>
                                                                    <td style="text-align: right;">
                                                                        <a href="${pageContext.request.contextPath}/categories?action=edit&id=${cat.categoryId}"
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
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
                .form-container {
                    max-width: 600px;
                    margin: 0 auto;
                }

                .form-group {
                    margin-bottom: 20px;
                }

                .form-group label {
                    display: block;
                    margin-bottom: 8px;
                    font-weight: 500;
                    color: #555;
                }

                .form-group input,
                .form-group textarea {
                    width: 100%;
                    padding: 10px;
                    border: 2px solid #e0e0e0;
                    border-radius: 8px;
                    font-size: 15px;
                    outline: none;
                    transition: all 0.3s ease;
                }

                .form-group input:focus,
                .form-group textarea:focus {
                    border-color: #667eea;
                    box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
                }

                .btn-group {
                    display: flex;
                    justify-content: flex-end;
                    gap: 15px;
                    margin-top: 30px;
                    padding-top: 20px;
                    border-top: 1px solid #eee;
                }

                .hint {
                    font-size: 12px;
                    color: #888;
                    margin-top: 5px;
                }
            </style>
        </head>

        <body>
            <div class="dashboard-layout">
                <%@ include file="/WEB-INF/views/layouts/sidebar.jsp" %>

                    <main class="main-content">
                        <%@ include file="/WEB-INF/views/layouts/header.jsp" %>

                            <div class="content">
                                <div class="form-container">
                                    <div class="card">
                                        <div class="card-header">
                                            <h2>
                                                <c:choose>
                                                    <c:when test="${not empty category.categoryId}">
                                                        ✏️ Chỉnh sửa Danh mục
                                                    </c:when>
                                                    <c:otherwise>
                                                        ➕ Thêm Danh mục mới
                                                    </c:otherwise>
                                                </c:choose>
                                            </h2>
                                        </div>
                                        <div class="card-body">
                                            <c:if test="${not empty error}">
                                                <div class="alert alert-error">${error}</div>
                                            </c:if>

                                            <form action="${pageContext.request.contextPath}/categories" method="POST">
                                                <c:choose>
                                                    <c:when test="${not empty category.categoryId}">
                                                        <input type="hidden" name="action" value="update">
                                                        <input type="hidden" name="categoryId"
                                                            value="${category.categoryId}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="hidden" name="action" value="create">
                                                    </c:otherwise>
                                                </c:choose>

                                                <div class="form-group">
                                                    <label for="categoryName">Tên Danh mục <span
                                                            style="color:red">*</span></label>
                                                    <input type="text" id="categoryName" name="categoryName"
                                                        value="${category.categoryName}" required
                                                        placeholder="VD: Máy tính xách tay">
                                                </div>

                                                <div class="form-group">
                                                    <label for="prefixCode">Mã Prefix <span
                                                            style="color:red">*</span></label>
                                                    <input type="text" id="prefixCode" name="prefixCode"
                                                        value="${category.prefixCode}" ${not empty category.categoryId
                                                        ? 'disabled' : 'required' } placeholder="VD: LAP">
                                                    <c:if test="${not empty category.categoryId}">
                                                        <%-- Prefix cannot be changed --%>
                                                            <div class="hint">Mã Prefix không thể thay đổi sau khi tạo.
                                                            </div>
                                                    </c:if>
                                                    <c:if test="${empty category.categoryId}">
                                                        <div class="hint">Mã duy nhất dùng để sinh mã tài sản. Tự động
                                                            chuyển thành chữ hoa.</div>
                                                    </c:if>
                                                </div>

                                                <div class="form-group">
                                                    <label for="description">Mô tả</label>
                                                    <textarea id="description" name="description" rows="4"
                                                        placeholder="Mô tả chi tiết về loại tài sản...">${category.description}</textarea>
                                                </div>

                                                <div class="btn-group">
                                                    <a href="${pageContext.request.contextPath}/categories"
                                                        class="btn btn-secondary">
                                                        Hủy bỏ
                                                    </a>
                                                    <button type="submit" class="btn btn-primary">
                                                        <c:choose>
                                                            <c:when test="${not empty category.categoryId}">
                                                                Lưu thay đổi
                                                            </c:when>
                                                            <c:otherwise>
                                                                Tạo mới
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                    </main>
            </div>
        </body>

        </html>
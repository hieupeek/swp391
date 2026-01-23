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
                                            <h2>${not empty supplier.supplierId ? '✏️ Chỉnh sửa NCC' : '➕ Thêm NCC mới'}
                                            </h2>
                                        </div>
                                        <div class="card-body">
                                            <c:if test="${not empty error}">
                                                <div class="alert alert-error">${error}</div>
                                            </c:if>

                                            <form action="${pageContext.request.contextPath}/suppliers" method="POST">
                                                <c:choose>
                                                    <c:when test="${not empty supplier.supplierId}">
                                                        <input type="hidden" name="action" value="update">
                                                        <input type="hidden" name="supplierId"
                                                            value="${supplier.supplierId}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="hidden" name="action" value="create">
                                                    </c:otherwise>
                                                </c:choose>

                                                <div class="form-group">
                                                    <label for="supplierName">Tên Nhà cung cấp <span
                                                            style="color:red">*</span></label>
                                                    <input type="text" id="supplierName" name="supplierName"
                                                        value="${supplier.supplierName}" required
                                                        placeholder="VD: Công ty TNHH ABC">
                                                </div>

                                                <div class="form-group">
                                                    <label for="contactPerson">Người liên hệ</label>
                                                    <input type="text" id="contactPerson" name="contactPerson"
                                                        value="${supplier.contactPerson}"
                                                        placeholder="VD: Nguyễn Văn A">
                                                </div>

                                                <div class="form-group">
                                                    <label for="email">Email</label>
                                                    <input type="email" id="email" name="email"
                                                        value="${supplier.email}" placeholder="VD: contact@abc.com">
                                                </div>

                                                <div class="form-group">
                                                    <label for="phone">Số điện thoại</label>
                                                    <input type="tel" id="phone" name="phone" value="${supplier.phone}"
                                                        placeholder="VD: 0901234567">
                                                </div>

                                                <div class="form-group">
                                                    <label for="address">Địa chỉ</label>
                                                    <textarea id="address" name="address" rows="3"
                                                        placeholder="Địa chỉ trụ sở...">${supplier.address}</textarea>
                                                </div>

                                                <div class="btn-group">
                                                    <a href="${pageContext.request.contextPath}/suppliers"
                                                        class="btn btn-secondary">
                                                        Hủy bỏ
                                                    </a>
                                                    <button type="submit" class="btn btn-primary">
                                                        ${not empty supplier.supplierId ? 'Lưu thay đổi' : 'Tạo mới'}
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
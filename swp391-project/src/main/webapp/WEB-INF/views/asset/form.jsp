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
                    max-width: 800px;
                    margin: 0 auto;
                }

                .form-row {
                    display: flex;
                    gap: 20px;
                    margin-bottom: 15px;
                }

                .form-col {
                    flex: 1;
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
                .form-group select,
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
                .form-group select:focus,
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
                    margin-top: 4px;
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
                                            <h2>${not empty asset.assetId ? '✏️ Chỉnh sửa Tài sản' : '➕ Thêm Tài sản
                                                mới'}</h2>
                                        </div>
                                        <div class="card-body">
                                            <c:if test="${not empty error}">
                                                <div class="alert alert-error">${error}</div>
                                            </c:if>

                                            <form action="${pageContext.request.contextPath}/assets" method="POST">
                                                <c:choose>
                                                    <c:when test="${not empty asset.assetId}">
                                                        <input type="hidden" name="action" value="update">
                                                        <input type="hidden" name="assetId" value="${asset.assetId}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="hidden" name="action" value="create">
                                                    </c:otherwise>
                                                </c:choose>

                                                <%-- Basic Info --%>
                                                    <div class="form-row">
                                                        <div class="form-col">
                                                            <div class="form-group">
                                                                <label for="assetCode">Mã Tài sản</label>
                                                                <input type="text" id="assetCode" name="assetCode"
                                                                    value="${asset.assetCode}" disabled
                                                                    placeholder="${empty asset.assetId ? '(Tự động sinh khi lưu)' : ''}">
                                                                <c:if test="${empty asset.assetId}">
                                                                    <div class="hint">Mã sẽ được sinh ngẫu nhiên theo
                                                                        Prefix của Danh mục (VD: LAP-2024-0001).</div>
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                        <div class="form-col">
                                                            <div class="form-group">
                                                                <label for="status">Trạng thái</label>
                                                                <select id="status" name="status">
                                                                    <c:forEach var="st" items="${statuses}">
                                                                        <option value="${st}" ${asset.status==st
                                                                            ? 'selected' : '' }>${st}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="assetName">Tên Tài sản <span
                                                                style="color:red">*</span></label>
                                                        <input type="text" id="assetName" name="assetName"
                                                            value="${asset.assetName}" required
                                                            placeholder="VD: Laptop Dell Latitude 7420">
                                                    </div>

                                                    <%-- Relations --%>
                                                        <div class="form-row">
                                                            <div class="form-col">
                                                                <div class="form-group">
                                                                    <label for="categoryId">Loại Tài sản <span
                                                                            style="color:red">*</span></label>
                                                                    <select id="categoryId" name="categoryId" required>
                                                                        <option value="">-- Chọn Loại --</option>
                                                                        <c:forEach var="cat" items="${categories}">
                                                                            <option value="${cat.categoryId}"
                                                                                ${asset.categoryId==cat.categoryId
                                                                                ? 'selected' : '' }>
                                                                                ${cat.categoryName} (${cat.prefixCode})
                                                                            </option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div class="form-col">
                                                                <div class="form-group">
                                                                    <label for="supplierId">Nhà cung cấp</label>
                                                                    <select id="supplierId" name="supplierId">
                                                                        <option value="">-- Chọn NCC --</option>
                                                                        <c:forEach var="sup" items="${suppliers}">
                                                                            <option value="${sup.supplierId}"
                                                                                ${asset.supplierId==sup.supplierId
                                                                                ? 'selected' : '' }>
                                                                                ${sup.supplierName}
                                                                            </option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group">
                                                            <label for="roomId">Vị trí ban đầu (Phòng)</label>
                                                            <select id="roomId" name="roomId">
                                                                <option value="">-- Chọn Vị trí --</option>
                                                                <c:forEach var="room" items="${rooms}">
                                                                    <option value="${room.roomId}"
                                                                        ${asset.roomId==room.roomId ? 'selected' : '' }>
                                                                        ${room.roomName}
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>

                                                        <%-- Financial & Dates --%>
                                                            <div class="form-row">
                                                                <div class="form-col">
                                                                    <div class="form-group">
                                                                        <label for="price">Giá trị (VNĐ)</label>
                                                                        <input type="number" id="price" name="price"
                                                                            value="${asset.price}" min="0" step="1000">
                                                                    </div>
                                                                </div>
                                                                <div class="form-col">
                                                                    <div class="form-group">
                                                                        <label for="purchaseDate">Ngày mua</label>
                                                                        <input type="date" id="purchaseDate"
                                                                            name="purchaseDate"
                                                                            value="${asset.purchaseDate}">
                                                                    </div>
                                                                </div>
                                                                <div class="form-col">
                                                                    <div class="form-group">
                                                                        <label for="warrantyDate">Hạn bảo hành</label>
                                                                        <input type="date" id="warrantyDate"
                                                                            name="warrantyDate"
                                                                            value="${asset.warrantyExpiryDate}">
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="form-group">
                                                                <label for="description">Mô tả / Ghi chú</label>
                                                                <textarea id="description" name="description"
                                                                    rows="3">${asset.description}</textarea>
                                                            </div>

                                                            <div class="btn-group">
                                                                <a href="${pageContext.request.contextPath}/assets"
                                                                    class="btn btn-secondary">
                                                                    Hủy bỏ
                                                                </a>
                                                                <button type="submit" class="btn btn-primary">
                                                                    ${not empty asset.assetId ? 'Lưu thay đổi' : 'Tạo
                                                                    Tài sản'}
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
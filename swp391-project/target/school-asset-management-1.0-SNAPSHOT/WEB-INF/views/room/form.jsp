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
                .form-group select {
                    width: 100%;
                    padding: 10px;
                    border: 2px solid #e0e0e0;
                    border-radius: 8px;
                    font-size: 15px;
                    outline: none;
                    transition: all 0.3s ease;
                }

                .form-group input:focus,
                .form-group select:focus {
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
                                            <h2>${not empty room.roomId ? '✏️ Chỉnh sửa Phòng' : '➕ Thêm Phòng mới'}
                                            </h2>
                                        </div>
                                        <div class="card-body">
                                            <c:if test="${not empty error}">
                                                <div class="alert alert-error">${error}</div>
                                            </c:if>

                                            <form action="${pageContext.request.contextPath}/rooms" method="POST">
                                                <c:choose>
                                                    <c:when test="${not empty room.roomId}">
                                                        <input type="hidden" name="action" value="update">
                                                        <input type="hidden" name="roomId" value="${room.roomId}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="hidden" name="action" value="create">
                                                    </c:otherwise>
                                                </c:choose>

                                                <div class="form-group">
                                                    <label for="roomName">Tên Phòng <span
                                                            style="color:red">*</span></label>
                                                    <input type="text" id="roomName" name="roomName"
                                                        value="${room.roomName}" required
                                                        placeholder="VD: Phòng 101, Phòng Lab C...">
                                                </div>

                                                <div class="form-group">
                                                    <label for="deptId">Thuộc Bộ môn / Khoa</label>
                                                    <select id="deptId" name="deptId">
                                                        <option value="">-- Chọn Đơn vị quản lý --</option>
                                                        <c:forEach var="dept" items="${departments}">
                                                            <option value="${dept.deptId}" ${room.deptId==dept.deptId
                                                                ? 'selected' : '' }>
                                                                ${dept.deptName}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <div class="form-group">
                                                    <label for="capacity">Sức chứa (người)</label>
                                                    <input type="number" id="capacity" name="capacity"
                                                        value="${room.capacity}" min="0" placeholder="VD: 40">
                                                </div>

                                                <div class="btn-group">
                                                    <a href="${pageContext.request.contextPath}/rooms"
                                                        class="btn btn-secondary">
                                                        Hủy bỏ
                                                    </a>
                                                    <button type="submit" class="btn btn-primary">
                                                        ${not empty room.roomId ? 'Lưu thay đổi' : 'Tạo mới'}
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
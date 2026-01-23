<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <%-- Sidebar Navigation Component Renders role-based navigation menu. Required request attributes: - user: User
            object from session - roleName: Current user's role name - currentPage: Current page identifier for active
            state --%>

            <aside class="sidebar">
                <div class="sidebar-header">
                    <div class="logo">🏫</div>
                    <h2>AMS</h2>
                </div>

                <nav class="sidebar-nav">
                    <%-- Dashboard - Available for all roles --%>
                        <a href="${pageContext.request.contextPath}/dashboard"
                            class="nav-item ${currentPage == 'dashboard' ? 'active' : ''}">
                            <span class="icon">📊</span>
                            <span>Dashboard</span>
                        </a>

                        <%-- Principal Menu Items --%>
                            <c:if test="${roleName == 'Principal'}">
                                <a href="${pageContext.request.contextPath}/approvals"
                                    class="nav-item ${currentPage == 'approvals' ? 'active' : ''}">
                                    <span class="icon">✅</span>
                                    <span>Phê duyệt</span>
                                </a>
                                <a href="${pageContext.request.contextPath}/reports"
                                    class="nav-item ${currentPage == 'reports' ? 'active' : ''}">
                                    <span class="icon">📈</span>
                                    <span>Báo cáo</span>
                                </a>
                                <a href="${pageContext.request.contextPath}/liquidation"
                                    class="nav-item ${currentPage == 'liquidation' ? 'active' : ''}">
                                    <span class="icon">📋</span>
                                    <span>Thanh lý TS</span>
                                </a>
                            </c:if>

                            <%-- Finance Head Menu Items --%>
                                <c:if test="${roleName == 'Finance_Head'}">
                                    <a href="${pageContext.request.contextPath}/categories"
                                        class="nav-item ${currentPage == 'categories' ? 'active' : ''}">
                                        <span class="icon">📁</span>
                                        <span>Danh mục</span>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/rooms"
                                        class="nav-item ${currentPage == 'rooms' ? 'active' : ''}">
                                        <span class="icon">🏢</span>
                                        <span>Phòng ban</span>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/suppliers"
                                        class="nav-item ${currentPage == 'suppliers' ? 'active' : ''}">
                                        <span class="icon">🏭</span>
                                        <span>Nhà cung cấp</span>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/assets"
                                        class="nav-item ${currentPage == 'assets' ? 'active' : ''}">
                                        <span class="icon">📦</span>
                                        <span>Tài sản</span>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/procurement"
                                        class="nav-item ${currentPage == 'procurement' ? 'active' : ''}">
                                        <span class="icon">🛒</span>
                                        <span>Mua sắm</span>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/reports"
                                        class="nav-item ${currentPage == 'reports' ? 'active' : ''}">
                                        <span class="icon">📈</span>
                                        <span>Báo cáo</span>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/liquidation"
                                        class="nav-item ${currentPage == 'liquidation' ? 'active' : ''}">
                                        <span class="icon">📋</span>
                                        <span>Thanh lý TS</span>
                                    </a>
                                </c:if>

                                <%-- Asset Staff Menu Items --%>
                                    <c:if test="${roleName == 'Asset_Staff'}">
                                        <a href="${pageContext.request.contextPath}/rooms"
                                            class="nav-item ${currentPage == 'rooms' ? 'active' : ''}">
                                            <span class="icon">🏢</span>
                                            <span>Phòng ban</span>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/suppliers"
                                            class="nav-item ${currentPage == 'suppliers' ? 'active' : ''}">
                                            <span class="icon">🏭</span>
                                            <span>Nhà cung cấp</span>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/assets"
                                            class="nav-item ${currentPage == 'assets' ? 'active' : ''}">
                                            <span class="icon">📦</span>
                                            <span>Quản lý Tài sản</span>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/transfers"
                                            class="nav-item ${currentPage == 'transfers' ? 'active' : ''}">
                                            <span class="icon">🔄</span>
                                            <span>Điều chuyển</span>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/maintenance"
                                            class="nav-item ${currentPage == 'maintenance' ? 'active' : ''}">
                                            <span class="icon">🔧</span>
                                            <span>Bảo trì</span>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/liquidation"
                                            class="nav-item ${currentPage == 'liquidation' ? 'active' : ''}">
                                            <span class="icon">📋</span>
                                            <span>Thanh lý TS</span>
                                        </a>
                                    </c:if>

                                    <%-- Head of Department Menu Items --%>
                                        <c:if test="${roleName == 'Head_of_Dept'}">
                                            <a href="${pageContext.request.contextPath}/my-assets"
                                                class="nav-item ${currentPage == 'my-assets' ? 'active' : ''}">
                                                <span class="icon">📦</span>
                                                <span>Tài sản phòng</span>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/transfers"
                                                class="nav-item ${currentPage == 'transfers' ? 'active' : ''}">
                                                <span class="icon">🔄</span>
                                                <span>Điều chuyển</span>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/maintenance-requests"
                                                class="nav-item ${currentPage == 'maintenance-requests' ? 'active' : ''}">
                                                <span class="icon">🔧</span>
                                                <span>Yêu cầu sửa chữa</span>
                                            </a>
                                        </c:if>
                </nav>

                <div class="sidebar-footer">
                    <a href="${pageContext.request.contextPath}/profile"
                        class="nav-item ${currentPage == 'profile' ? 'active' : ''}">
                        <span class="icon">👤</span>
                        <span>Hồ sơ</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/logout" class="nav-item logout">
                        <span class="icon">🚪</span>
                        <span>Đăng xuất</span>
                    </a>
                </div>
            </aside>
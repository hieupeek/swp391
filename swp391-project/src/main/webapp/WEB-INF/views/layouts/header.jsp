<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <%-- Header Component Displays page title and user info. Required request attributes: - pageTitle: Title for the
            current page - user: User object from session - roleName: Current user's role name --%>

            <header class="top-header">
                <h1>${pageTitle}</h1>
                <div class="user-info">
                    <a href="${pageContext.request.contextPath}/profile" class="user-link">
                        <span>👤 ${user.fullName}</span>
                    </a>
                    <span class="role-badge 
            <c:choose>
                <c:when test=" ${roleName=='Principal' }">principal</c:when>
                        <c:when test="${roleName == 'Finance_Head'}">finance</c:when>
                        <c:when test="${roleName == 'Asset_Staff'}">staff</c:when>
                        <c:when test="${roleName == 'Head_of_Dept'}">hod</c:when>
                        <c:otherwise>default</c:otherwise>
                        </c:choose>">
                        <c:choose>
                            <c:when test="${roleName == 'Principal'}">Hiệu trưởng</c:when>
                            <c:when test="${roleName == 'Finance_Head'}">Trưởng phòng TC-KT</c:when>
                            <c:when test="${roleName == 'Asset_Staff'}">Nhân viên TB</c:when>
                            <c:when test="${roleName == 'Head_of_Dept'}">Trưởng BM</c:when>
                            <c:otherwise>${roleName}</c:otherwise>
                        </c:choose>
                    </span>
                </div>
            </header>
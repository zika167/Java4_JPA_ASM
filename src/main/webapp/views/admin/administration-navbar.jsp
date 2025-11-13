<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/administration-navbar.css">
<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

<!-- Administration Navigation Bar -->
<nav class="admin-navbar">
    <div class="admin-container">
        <!-- Brand/Logo -->
        <div class="admin-brand">
            <i class="bi bi-shield-lock-fill"></i>
            <span class="brand-text"><span class="first-letter">A</span>DMINISTRATION <span class="first-letter">T</span>OOL</span>
        </div>

        <!-- Navigation Links -->
        <div class="admin-menu">
            <a href="${pageContext.request.contextPath}/admin/home" 
               class="admin-link ${param.page == 'home' ? 'active' : ''}">
                HOME
            </a>
            <a href="${pageContext.request.contextPath}/admin/videos" 
               class="admin-link ${param.page == 'videos' ? 'active' : ''}">
                VIDEOS
            </a>
            <a href="${pageContext.request.contextPath}/admin/users" 
               class="admin-link ${param.page == 'users' ? 'active' : ''}">
                USERS
            </a>
            <a href="${pageContext.request.contextPath}/admin/reports" 
               class="admin-link ${param.page == 'reports' ? 'active' : ''}">
                REPORTS
            </a>
        </div>
    </div>
</nav>

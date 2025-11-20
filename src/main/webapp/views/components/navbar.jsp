<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/components/navbar-new.css">
<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

<!-- User Navigation Bar -->
<nav class="user-navbar">
    <div class="user-container">

        <!-- Brand/Logo -->
        <div class="user-brand">
            <a href="${pageContext.request.contextPath}/index" class="brand-link">
                <img src="https://res.cloudinary.com/dlpi2u0ds/image/upload/v1763189405/logojava4-Photoroom_bgg2tz.png"
                     alt="4in1 Logo"
                     class="user-logo-img rounded-circle">
                <span class="brand-text">
                    <span class="first-letter">4</span>IN<span class="first-letter">1</span> ENTERTAINMENT
                </span>
            </a>
        </div>

        <!-- Navigation Links -->
        <div class="user-menu">
            <a href="${pageContext.request.contextPath}/index" 
               class="user-link ${pageContext.request.requestURI.contains('/index') ? 'active' : ''}">
                HOME
            </a>
            
            <c:choose>
                <c:when test="${not empty sessionScope.user && sessionScope.role == 'admin'}">
                    <a href="${pageContext.request.contextPath}/admin/home" class="user-link">
                        ADMIN
                    </a>
                </c:when>
            </c:choose>
            
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a href="${pageContext.request.contextPath}/favorite" class="user-link">
                        FAVORITES
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="#" class="user-link" data-bs-toggle="modal" data-bs-target="#loginModal">
                        FAVORITES
                    </a>
                </c:otherwise>
            </c:choose>
            
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <div class="dropdown">
                        <button type="button" id="userDropdown" class="text-uppercase user-link dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false" data-bs-auto-close="true">
                            <i class="bi bi-person-circle me-1"></i>${sessionScope.user}
                        </button>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <c:if test="${sessionScope.role == 'admin'}">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/home">
                                    <i class="bi bi-shield-lock me-2"></i>Admin Panel</a></li>
                                <li><hr class="dropdown-divider"></li>
                            </c:if>
                            <li><a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#accountSettingsModal">
                                <i class="bi bi-gear me-2"></i>Account Settings</a></li>
                            <li><a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#changePasswordModal">
                                <i class="bi bi-key me-2"></i>Change Password</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                                <i class="bi bi-box-arrow-right me-2"></i>Logout</a></li>
                        </ul>
                    </div>
                </c:when>
                <c:otherwise>
                    <a href="#" class="user-link" data-bs-toggle="modal" data-bs-target="#loginModal">
                        LOGIN
                    </a>
                    <a href="#" class="user-link" data-bs-toggle="modal" data-bs-target="#registerModal">
                        REGISTER
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<script>
// Initialize dropdown - wait for Bootstrap to load
(function() {
    function initDropdown() {
        if (typeof bootstrap !== 'undefined') {
            const dropdownToggle = document.getElementById('userDropdown');
            if (dropdownToggle) {
                try {
                    new bootstrap.Dropdown(dropdownToggle);
                    console.log('User dropdown initialized successfully');
                } catch (e) {
                    console.error('Error initializing dropdown:', e);
                }
            }
        } else {
            // Bootstrap not loaded yet, try again
            setTimeout(initDropdown, 100);
        }
    }
    
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initDropdown);
    } else {
        initDropdown();
    }
})();
</script>
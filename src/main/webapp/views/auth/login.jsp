<%--
  ⚠️ DEPRECATED: This page is deprecated and should not be used.
  
  Login functionality has been moved to a modal popup.
  See: /views/fragments/auth-modals.jsp
  
  This file is kept for backward compatibility only.
  New implementations should use the modal instead.
  
  Redirect users to: index.jsp (navbar will show login modal)
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập - 4in1</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/pages/account-settings.css">
</head>
<body>
    <div class="account-settings-container">
        <div class="settings-header">
            <h1><i class="fas fa-sign-in-alt"></i> Đăng nhập</h1>
        </div>
        
        <div class="settings-content">
            <div class="settings-main">
                <c:if test="${not empty error}">
                    <div class="alert alert-custom alert-error">
                        <i class="fas fa-exclamation-circle"></i> ${error}
                    </div>
                </c:if>
                
                <c:if test="${not empty success}">
                    <div class="alert alert-custom alert-success">
                        <i class="fas fa-check-circle"></i> ${success}
                    </div>
                </c:if>

                <form id="loginForm" action="${pageContext.request.contextPath}/auth/login" method="post">
                    <div class="form-group">
                        <label for="username" class="form-label">Tên đăng nhập hoặc Email</label>
                        <input type="text" id="username" name="username" class="form-control" 
                               value="${param.username}" required autofocus>
                        <span class="error-message">Vui lòng nhập tên đăng nhập hoặc email</span>
                    </div>

                    <div class="form-group">
                        <label for="password" class="form-label">Mật khẩu</label>
                        <div class="password-input">
                            <input type="password" id="password" name="password" class="form-control" required>
                            <button type="button" class="toggle-password" tabindex="-1">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                        <span class="error-message">Vui lòng nhập mật khẩu</span>
                    </div>

                    <div class="form-group" style="display: flex; align-items: center; justify-content: space-between; margin-bottom: 10px;">
                        <label style="display: flex; align-items: center; gap: 6px; font-size: 14px; cursor: pointer;">
                            <input type="checkbox" name="remember" value="true" style="cursor: pointer;">
                            <span>Ghi nhớ đăng nhập</span>
                        </label>
                        <a href="${pageContext.request.contextPath}/views/auth/forgot-password.jsp"
                           style="color: var(--primary-color); text-decoration: none; font-size: 14px;">
                            Quên mật khẩu?
                        </a>
                    </div>

                    <div class="form-actions" style="border-top: none; padding-top: 10px;">
                        <button type="submit" class="btn btn-primary" style="width: 100%;">
                            <i class="fas fa-sign-in-alt"></i> Đăng nhập
                        </button>
                    </div>

                    <div style="text-align: center; margin-top: 20px; padding-top: 20px; border-top: 1px solid #eee;">
                        <span style="color: #666; font-size: 14px;">Chưa có tài khoản?</span>
                        <a href="${pageContext.request.contextPath}/views/auth/register.jsp"
                           style="color: var(--primary-color); text-decoration: none; font-weight: 500; margin-left: 6px;">
                            Đăng ký ngay
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Include JSP Fragments -->
    <jsp:include page="../fragments/toast.jsp"/>
    <jsp:include page="../fragments/loading-spinner.jsp"/>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/common.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.getElementById('loginForm');
            
            // Setup real-time validation using FormValidator from common.js
            FormValidator.setupRealTimeValidation(form);
            
            // Form submission validation
            form.addEventListener('submit', function(e) {
                if (!FormValidator.validateForm(this)) {
                    e.preventDefault();
                    Toast.error('Vui lòng điền đầy đủ thông tin đăng nhập.');
                    const firstError = document.querySelector('.form-group.error');
                    if (firstError) {
                        firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
                    }
                }
            });
            
            // Show toast if there's an error from server
            <c:if test="${not empty error}">
                Toast.error('${error}');
            </c:if>
            
            // Show toast if there's a success message
            <c:if test="${not empty success}">
                Toast.success('${success}');
            </c:if>
        });
    </script>
</body>
</html>

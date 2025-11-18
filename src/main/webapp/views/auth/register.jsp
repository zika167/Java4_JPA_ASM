<%--
  ⚠️ DEPRECATED: This page is deprecated and should not be used.
  
  Register functionality has been moved to a modal popup.
  See: /views/fragments/auth-modals.jsp
  
  This file is kept for backward compatibility only.
  New implementations should use the modal instead.
  
  Redirect users to: index.jsp (navbar will show register modal)
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký tài khoản - 4in1</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/pages/account-settings.css">
</head>
<body>
    <div class="account-settings-container">
        <div class="settings-header">
            <h1><i class="fas fa-user-plus"></i> Đăng ký tài khoản</h1>
        </div>
        
        <div class="settings-content">
            <div class="settings-main">
                <c:if test="${not empty error}">
                    <div class="alert alert-custom alert-error">
                        <i class="fas fa-exclamation-circle"></i> ${error}
                    </div>
                </c:if>

                <form id="registerForm" action="${pageContext.request.contextPath}/auth/register" method="post">
                    <div class="form-group">
                        <label for="username" class="form-label">Tên đăng nhập</label>
                        <input type="text" id="username" name="username" class="form-control" 
                               value="${param.username}" required autofocus 
                               pattern="[a-zA-Z0-9_]{3,20}" 
                               title="Tên đăng nhập từ 3-20 ký tự, chỉ chứa chữ cái, số và dấu gạch dưới">
                        <span class="error-message">Tên đăng nhập từ 3-20 ký tự, chỉ chứa chữ cái, số và dấu gạch dưới</span>
                    </div>

                    <div class="form-group">
                        <label for="fullname" class="form-label">Họ và tên</label>
                        <input type="text" id="fullname" name="fullname" class="form-control" 
                               value="${param.fullname}" required>
                        <span class="error-message">Vui lòng nhập họ và tên</span>
                    </div>

                    <div class="form-group">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" id="email" name="email" class="form-control" 
                               value="${param.email}" required>
                        <span class="error-message">Vui lòng nhập email hợp lệ</span>
                    </div>

                    <div class="form-row">
                        <div class="form-col">
                            <div class="form-group">
                                <label for="password" class="form-label">Mật khẩu</label>
                                <div class="password-input">
                                    <input type="password" id="password" name="password" class="form-control" required>
                                    <button type="button" class="toggle-password" tabindex="-1">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </div>
                                <span class="error-message">Mật khẩu phải có ít nhất 6 ký tự</span>
                            </div>
                        </div>

                        <div class="form-col">
                            <div class="form-group">
                                <label for="confirmPassword" class="form-label">Xác nhận mật khẩu</label>
                                <div class="password-input">
                                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                                    <button type="button" class="toggle-password" tabindex="-1">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </div>
                                <span class="error-message">Mật khẩu xác nhận không khớp</span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group" style="margin-bottom: 10px;">
                        <label style="display: flex; align-items: flex-start; gap: 8px; font-size: 14px; cursor: pointer;">
                            <input type="checkbox" name="agreeTerms" value="true" required style="margin-top: 4px; cursor: pointer;">
                            <span>Tôi đồng ý với 
                                <a href="#" style="color: var(--primary-color); text-decoration: none;">Điều khoản sử dụng</a> 
                                và 
                                <a href="#" style="color: var(--primary-color); text-decoration: none;">Chính sách bảo mật</a>
                            </span>
                        </label>
                    </div>

                    <div class="form-actions" style="border-top: none; padding-top: 10px;">
                        <button type="submit" class="btn btn-primary" style="width: 100%;">
                            <i class="fas fa-user-plus"></i> Đăng ký
                        </button>
                    </div>

                    <div style="text-align: center; margin-top: 20px; padding-top: 20px; border-top: 1px solid #eee;">
                        <span style="color: #666; font-size: 14px;">Đã có tài khoản?</span>
                        <a href="${pageContext.request.contextPath}/views/auth/login.jsp"
                           style="color: var(--primary-color); text-decoration: none; font-weight: 500; margin-left: 6px;">
                            Đăng nhập ngay
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
            const form = document.getElementById('registerForm');
            
            // Setup real-time validation using FormValidator from common.js
            FormValidator.setupRealTimeValidation(form);
            
            // Real-time password confirmation check
            const confirmPasswordField = document.getElementById('confirmPassword');
            if (confirmPasswordField) {
                confirmPasswordField.addEventListener('input', function() {
                    if (this.getAttribute('data-validated') === 'true') {
                        const password = document.getElementById('password').value;
                        const formGroup = this.closest('.form-group');
                        
                        if (this.value === password) {
                            formGroup.classList.remove('error');
                        } else {
                            formGroup.classList.add('error');
                        }
                    }
                });
            }
            
            // Form submission validation
            form.addEventListener('submit', function(e) {
                // Mark all fields as validated
                const formFields = form.querySelectorAll('input[type="text"], input[type="email"], input[type="password"]');
                formFields.forEach(field => {
                    field.setAttribute('data-validated', 'true');
                });
                
                // Validate form
                if (!FormValidator.validateForm(this)) {
                    e.preventDefault();
                    Toast.error('Vui lòng điền đầy đủ thông tin đăng ký.');
                    const firstError = document.querySelector('.form-group.error');
                    if (firstError) {
                        firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
                    }
                    return;
                }
                
                // Check password confirmation
                const password = document.getElementById('password');
                const confirmPassword = document.getElementById('confirmPassword');
                
                if (password.value !== confirmPassword.value) {
                    e.preventDefault();
                    confirmPassword.closest('.form-group').classList.add('error');
                    Toast.error('Mật khẩu xác nhận không khớp.');
                    return;
                }

                // Check terms agreement
                const agreeTerms = document.querySelector('input[name="agreeTerms"]');
                if (!agreeTerms.checked) {
                    e.preventDefault();
                    Toast.error('Vui lòng đồng ý với điều khoản sử dụng và chính sách bảo mật.');
                    return;
                }
            });
            
            // Show toast if there's an error from server
            <c:if test="${not empty error}">
                Toast.error('${error}');
            </c:if>
        });
    </script>
</body>
</html>

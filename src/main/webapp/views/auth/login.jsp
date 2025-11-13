<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/account-settings.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="account-settings-container">
        <div class="settings-header">
            <h1><i class="fas fa-sign-in-alt"></i> Đăng nhập</h1>
        </div>
        
        <div class="settings-content">
            <div class="settings-main">
                <c:if test="${not empty error}">
                    <div class="alert alert-error" style="padding: 10px; margin-bottom: 20px; background-color: #fee; border: 1px solid #fcc; border-radius: 4px; color: #c33;">
                        <i class="fas fa-exclamation-circle"></i> ${error}
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
                        <a href="../auth/forgot-password.jsp"
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
                        <a href="../auth/register.jsp"
                           style="color: var(--primary-color); text-decoration: none; font-weight: 500; margin-left: 6px;">
                            Đăng ký ngay
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        const form = document.getElementById('loginForm');
        const formFields = form.querySelectorAll('input[required]');
        
        // Add blur event listeners for validation
        formFields.forEach(field => {
            field.addEventListener('blur', function() {
                validateField(this);
            });
            
            field.addEventListener('input', function() {
                if (this.getAttribute('data-validated') === 'true') {
                    validateField(this);
                }
            });
        });

        // Toggle password visibility
        document.querySelectorAll('.toggle-password').forEach(button => {
            button.addEventListener('click', function() {
                const input = this.closest('div').querySelector('input');
                const icon = this.querySelector('i');
                if (input.type === 'password') {
                    input.type = 'text';
                    icon.classList.remove('fa-eye');
                    icon.classList.add('fa-eye-slash');
                } else {
                    input.type = 'password';
                    icon.classList.remove('fa-eye-slash');
                    icon.classList.add('fa-eye');
                }
            });
        });

        function validateField(field) {
            const formGroup = field.closest('.form-group');
            if (!formGroup) return true;
            
            field.setAttribute('data-validated', 'true');
            formGroup.classList.remove('error');
            
            let isValid = true;
            const value = field.value.trim();
            
            if (field.required && !value) {
                isValid = false;
            } else if (field.id === 'password' && value && value.length < 6) {
                isValid = false;
                formGroup.querySelector('.error-message').textContent = 'Mật khẩu phải có ít nhất 6 ký tự';
            }
            
            if (!isValid) {
                formGroup.classList.add('error');
                return false;
            }
            
            return true;
        }

        // Form submission validation
        form.addEventListener('submit', function(e) {
            formFields.forEach(field => {
                field.setAttribute('data-validated', 'true');
            });
            
            let isFormValid = true;
            formFields.forEach(field => {
                if (!validateField(field)) {
                    isFormValid = false;
                }
            });
            
            if (!isFormValid) {
                e.preventDefault();
                const firstError = document.querySelector('.form-group.error');
                if (firstError) {
                    firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
                }
            }
        });
    </script>
</body>
</html>

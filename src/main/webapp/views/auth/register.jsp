<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký tài khoản</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/account-settings.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="account-settings-container">
        <div class="settings-header">
            <h1><i class="fas fa-user-plus"></i> Đăng ký tài khoản</h1>
        </div>
        
        <div class="settings-content">
            <div class="settings-main">
                <c:if test="${not empty error}">
                    <div class="alert alert-error" style="padding: 10px; margin-bottom: 20px; background-color: #fee; border: 1px solid #fcc; border-radius: 4px; color: #c33;">
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
                        <a href="../auth/login.jsp"
                           style="color: var(--primary-color); text-decoration: none; font-weight: 500; margin-left: 6px;">
                            Đăng nhập ngay
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        const form = document.getElementById('registerForm');
        const formFields = form.querySelectorAll('input[type="text"], input[type="email"], input[type="password"]');
        
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
            } else if (field.type === 'email' && value) {
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                isValid = emailRegex.test(value);
            } else if (field.id === 'username' && value) {
                const usernameRegex = /^[a-zA-Z0-9_]{3,20}$/;
                isValid = usernameRegex.test(value);
            } else if (field.id === 'password' && value && value.length < 6) {
                isValid = false;
            } else if (field.id === 'confirmPassword' && value) {
                const password = document.getElementById('password').value;
                isValid = value === password;
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

            // Check password confirmation
            const password = document.getElementById('password');
            const confirmPassword = document.getElementById('confirmPassword');
            
            if (password.value !== confirmPassword.value) {
                confirmPassword.closest('.form-group').classList.add('error');
                isFormValid = false;
            }

            // Check terms agreement
            const agreeTerms = document.querySelector('input[name="agreeTerms"]');
            if (!agreeTerms.checked) {
                alert('Vui lòng đồng ý với điều khoản sử dụng và chính sách bảo mật');
                isFormValid = false;
            }
            
            if (!isFormValid) {
                e.preventDefault();
                const firstError = document.querySelector('.form-group.error');
                if (firstError) {
                    firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
                }
            }
        });

        // Real-time password confirmation check
        document.getElementById('confirmPassword').addEventListener('input', function() {
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
    </script>
</body>
</html>

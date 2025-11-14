<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cài đặt tài khoản</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/account-settings.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="account-settings-container">
        <div class="settings-header">
            <h1><i class="fas fa-user-edit"></i> Cập nhật thông tin</h1>
        </div>
        
        <div class="settings-content">
            <!-- Main Content -->
            <div class="settings-main">
                <form id="accountForm">
                    <div class="form-group">
                        <label for="fullname" class="form-label">Họ và tên</label>
                        <input type="text" id="fullname" name="fullname" class="form-control" 
                               value="${user.fullname != null ? user.fullname : ''}" required>
                    </div>

                    <div class="form-group">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" id="email" name="email" class="form-control" 
                               value="${user.email}" required>
                    </div>

                    <div class="form-group">
                        <label for="password" class="form-label">Mật khẩu mới (để trống nếu không đổi)</label>
                        <div class="password-input">
                            <input type="password" id="password" name="password" class="form-control">
                            <button type="button" class="toggle-password">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới</label>
                        <div class="password-input">
                            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control">
                            <button type="button" class="toggle-password">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> Cập nhật
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- JavaScript -->
    <script>
        // Handle avatar change
        document.querySelector('.btn-outline').addEventListener('click', function() {
            document.getElementById('avatarInput').click();
        });

        document.getElementById('avatarInput').addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(event) {
                    document.querySelector('.account-avatar').src = event.target.result;
                    // Here you would typically upload the image to your server
                    // and update the user's avatar URL in the database
                };
                reader.readAsDataURL(file);
            }
        });

        // Track interacted fields
        const form = document.getElementById('accountForm');
        const formFields = form.querySelectorAll('input, select, textarea');
        
        // Add blur event listeners to show errors only after interaction
        formFields.forEach(field => {
            field.addEventListener('blur', function() {
                validateField(this);
            });
            
            // Clear error when user starts typing
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

        // Validate individual field
        function validateField(field) {
            const formGroup = field.closest('.form-group');
            if (!formGroup) return true;
            
            // Mark as validated
            field.setAttribute('data-validated', 'true');
            
            // Reset error state
            formGroup.classList.remove('error');
            
            // Skip validation for non-required fields with empty values
            if (!field.required && !field.value.trim()) {
                return true;
            }
            
            // Field-specific validation
            let isValid = true;
            const value = field.value.trim();
            
            if (field.required && !value) {
                isValid = false;
            } else if (field.type === 'email' && value) {
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                isValid = emailRegex.test(value);
            } else if (field.id === 'password' && value && value.length < 6) {
                isValid = false;
            }
            
            if (!isValid) {
                formGroup.classList.add('error');
                return false;
            }
            
            return true;
        }

        // Form submission
        form.addEventListener('submit', async function(e) {
            e.preventDefault();
            
            // Mark all fields as validated
            formFields.forEach(field => {
                field.setAttribute('data-validated', 'true');
            });
            
            // Validate all fields
            let isFormValid = true;
            formFields.forEach(field => {
                if (!validateField(field)) {
                    isFormValid = false;
                }
            });
            
            // Check password confirmation
            const password = document.getElementById('password');
            const confirmPassword = document.getElementById('confirmPassword');
            const confirmPasswordGroup = confirmPassword.closest('.form-group');
            
            // Reset password confirmation error
            confirmPasswordGroup.classList.remove('error');
            
            // Only validate password confirmation if password is being changed
            if (password.value) {
                if (password.value.length < 6) {
                    password.closest('.form-group').classList.add('error');
                    isFormValid = false;
                }
                
                if (password.value !== confirmPassword.value) {
                    confirmPasswordGroup.classList.add('error');
                    isFormValid = false;
                }
            }
            
            if (!isFormValid) {
                // Scroll to first error
                const firstError = document.querySelector('.form-group.error');
                if (firstError) {
                    firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
                }
                return;
            }
            
            // Prepare form data
            const formData = new FormData(this);
            
            try {
                // Show loading state
                const submitBtn = this.querySelector('button[type="submit"]');
                const originalBtnText = submitBtn.innerHTML;
                submitBtn.disabled = true;
                submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang cập nhật...';
                
                // Send request to server
                const response = await fetch('${pageContext.request.contextPath}/auth/update-profile', {
                    method: 'POST',
                    body: formData
                });
                
                const result = await response.json();
                
                if (result.success) {
                    // Show success message
                    alert('Cập nhật thông tin thành công!');
                    // Optionally reload the page to reflect changes
                    window.location.reload();
                } else {
                    // Show error message
                    alert('Có lỗi xảy ra: ' + (result.message || 'Vui lòng thử lại sau'));
                }
            } catch (error) {
                console.error('Error:', error);
                alert('Có lỗi xảy ra khi kết nối đến máy chủ');
            } finally {
                // Reset button state
                const submitBtn = this.querySelector('button[type="submit"]');
                if (submitBtn) {
                    submitBtn.disabled = false;
                    submitBtn.innerHTML = originalBtnText;
                }
            }
        });
        
        // Tab switching
        document.querySelectorAll('.tab').forEach(tab => {
            tab.addEventListener('click', function() {
                // Remove active class from all tabs
                document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
                // Add active class to clicked tab
                this.classList.add('active');
                // Here you would typically load the appropriate content for the selected tab
            });
        });
    </script>
</body>
</html>

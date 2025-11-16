<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đổi mật khẩu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/change-password.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="account-settings-container">
        <div class="settings-header">
            <h1><i class="fas fa-key"></i> Đổi mật khẩu</h1>
        </div>
        
        <div class="settings-content">
            <div class="settings-main">
                <form id="changePasswordForm">
                    <div class="form-group">
                        <label for="username" class="form-label">Tên đăng nhập</label>
                        <input type="text" id="username" name="username" class="form-control" 
                               value="${user.username}" required>
                    </div>

                    <div class="form-group">
                        <label for="currentPassword" class="form-label">Mật khẩu hiện tại</label>
                        <div class="password-input">
                            <input type="password" id="currentPassword" name="currentPassword" class="form-control" required>
                            <button type="button" class="toggle-password">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="newPassword" class="form-label">Mật khẩu mới</label>
                        <div class="password-input">
                            <input type="password" id="newPassword" name="newPassword" class="form-control" required>
                            <button type="button" class="toggle-password">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="confirmNewPassword" class="form-label">Xác nhận mật khẩu mới</label>
                        <div class="password-input">
                            <input type="password" id="confirmNewPassword" name="confirmNewPassword" class="form-control" required>
                            <button type="button" class="toggle-password">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> Đổi mật khẩu
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
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

        document.getElementById('changePasswordForm').addEventListener('submit', async function(e) {
            e.preventDefault();
            
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmNewPassword').value;
            
            if (newPassword !== confirmPassword) {
                alert('Mật khẩu mới và xác nhận mật khẩu không khớp!');
                return;
            }
            
            if (newPassword.length < 6) {
                alert('Mật khẩu mới phải có ít nhất 6 ký tự!');
                return;
            }
            
            const formData = new FormData(this);
            
            try {
                const submitBtn = this.querySelector('button[type="submit"]');
                const originalBtnText = submitBtn.innerHTML;
                submitBtn.disabled = true;
                submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang xử lý...';
                
                const response = await fetch('${pageContext.request.contextPath}/auth/change-password', {
                    method: 'POST',
                    body: formData
                });
                
                const result = await response.json();
                
                if (result.success) {
                    alert('Đổi mật khẩu thành công!');
                    this.reset();
                } else {
                    alert('Có lỗi xảy ra: ' + (result.message || 'Vui lòng thử lại sau'));
                }
            } catch (error) {
                console.error('Error:', error);
                alert('Có lỗi xảy ra khi kết nối đến máy chủ');
            } finally {
                const submitBtn = this.querySelector('button[type="submit"]');
                if (submitBtn) {
                    submitBtn.disabled = false;
                    submitBtn.innerHTML = '<i class="fas fa-save"></i> Đổi mật khẩu';
                }
            }
        });
    </script>
</body>
</html>
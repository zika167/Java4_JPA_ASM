<%--
  ⚠️ DEPRECATED: This page is deprecated and should not be used.
  
  Change Password functionality has been moved to a modal popup.
  See: /views/fragments/auth-modals.jsp
  
  This file is kept for backward compatibility only.
  New implementations should use the modal instead.
  
  Access from: Navbar dropdown menu > Change Password (opens modal)
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đổi mật khẩu - 4in1</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/auth/change-password.css">
</head>
<body>
    <div class="account-settings-container">
        <div class="settings-header">
            <h1><i class="fas fa-key"></i> Đổi mật khẩu</h1>
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
                
                <form id="changePasswordForm" action="${pageContext.request.contextPath}/auth/change-password" method="post">
                    <div class="form-group">
                        <label for="username" class="form-label">Tên đăng nhập</label>
                        <input type="text" id="username" name="username" class="form-control" 
                               value="${user.username}" required readonly>
                        <span class="error-message">Vui lòng nhập tên đăng nhập</span>
                    </div>

                    <div class="form-group">
                        <label for="currentPassword" class="form-label">Mật khẩu hiện tại</label>
                        <div class="password-input">
                            <input type="password" id="currentPassword" name="currentPassword" class="form-control" required>
                            <button type="button" class="toggle-password" tabindex="-1">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                        <span class="error-message">Vui lòng nhập mật khẩu hiện tại</span>
                    </div>

                    <div class="form-group">
                        <label for="newPassword" class="form-label">Mật khẩu mới</label>
                        <div class="password-input">
                            <input type="password" id="newPassword" name="newPassword" class="form-control" required>
                            <button type="button" class="toggle-password" tabindex="-1">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                        <span class="error-message">Mật khẩu mới phải có ít nhất 6 ký tự</span>
                    </div>

                    <div class="form-group">
                        <label for="confirmNewPassword" class="form-label">Xác nhận mật khẩu mới</label>
                        <div class="password-input">
                            <input type="password" id="confirmNewPassword" name="confirmNewPassword" class="form-control" required>
                            <button type="button" class="toggle-password" tabindex="-1">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                        <span class="error-message">Mật khẩu xác nhận không khớp</span>
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

    <!-- Include JSP Fragments -->
    <jsp:include page="../fragments/toast.jsp"/>
    <jsp:include page="../fragments/loading-spinner.jsp"/>
    <jsp:include page="../fragments/confirmation-modal.jsp"/>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/common.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.getElementById('changePasswordForm');
            
            // Setup real-time validation
            FormValidator.setupRealTimeValidation(form);
            
            // Real-time password confirmation check
            const confirmPasswordField = document.getElementById('confirmNewPassword');
            if (confirmPasswordField) {
                confirmPasswordField.addEventListener('input', function() {
                    if (this.getAttribute('data-validated') === 'true') {
                        const newPassword = document.getElementById('newPassword').value;
                        const formGroup = this.closest('.form-group');
                        
                        if (this.value === newPassword) {
                            formGroup.classList.remove('error');
                        } else {
                            formGroup.classList.add('error');
                        }
                    }
                });
            }
            
            // Form submission with confirmation
            form.addEventListener('submit', async function(e) {
                e.preventDefault();
                
                // Validate form
                if (!FormValidator.validateForm(this)) {
                    Toast.error('Vui lòng điền đầy đủ thông tin!');
                    return;
                }
                
                const newPassword = document.getElementById('newPassword').value;
                const confirmPassword = document.getElementById('confirmNewPassword').value;
                
                // Check password confirmation
                if (newPassword !== confirmPassword) {
                    Toast.error('Mật khẩu xác nhận không khớp!');
                    confirmPasswordField.closest('.form-group').classList.add('error');
                    return;
                }
                
                // Check password length
                if (newPassword.length < 6) {
                    Toast.error('Mật khẩu mới phải có ít nhất 6 ký tự!');
                    return;
                }
                
                // Show confirmation
                Confirmation.show(
                    'Bạn có chắc chắn muốn đổi mật khẩu?',
                    async function() {
                        Loading.show('Đang đổi mật khẩu...');
                        
                        const formData = new FormData(form);
                        
                        try {
                            const response = await fetch(form.action, {
                                method: 'POST',
                                body: formData
                            });
                            
                            const result = await response.json();
                            
                            if (result.success) {
                                Toast.success('Đổi mật khẩu thành công!');
                                form.reset();
                                
                                // Redirect to login after 2s
                                setTimeout(() => {
                                    window.location.href = '${pageContext.request.contextPath}/views/auth/login.jsp';
                                }, 2000);
                            } else {
                                Toast.error(result.message || 'Có lỗi xảy ra. Vui lòng thử lại!');
                            }
                        } catch (error) {
                            console.error('Error:', error);
                            Toast.error('Có lỗi xảy ra khi kết nối đến máy chủ!');
                        } finally {
                            Loading.hide();
                        }
                    }
                );
            });
            
            // Show toast if there's an error/success from server
            <c:if test="${not empty error}">
                Toast.error('${error}');
            </c:if>
            
            <c:if test="${not empty success}">
                Toast.success('${success}');
            </c:if>
        });
    </script>
</body>
</html>
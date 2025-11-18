<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cài đặt tài khoản - 4in1</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/pages/account-settings.css">
</head>
<body>
    <div class="account-settings-container">
        <div class="settings-header">
            <h1><i class="fas fa-user-edit"></i> Cập nhật thông tin</h1>
        </div>
        
        <div class="settings-content">
            <!-- Main Content -->
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
                
                <form id="accountForm" action="${pageContext.request.contextPath}/auth/update-profile" method="post">
                    <div class="form-group">
                        <label for="username" class="form-label">Tên đăng nhập</label>
                        <input type="text" id="username" name="username" class="form-control" 
                               value="${sessionScope.user}" readonly disabled>
                    </div>

                    <div class="form-group">
                        <label for="fullname" class="form-label">Họ và tên</label>
                        <input type="text" id="fullname" name="fullname" class="form-control" 
                               value="${not empty param.fullname ? param.fullname : sessionScope.user}" required>
                        <span class="error-message">Vui lòng nhập họ và tên</span>
                    </div>

                    <div class="form-group">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" id="email" name="email" class="form-control" 
                               value="${not empty param.email ? param.email : ''}" required>
                        <span class="error-message">Vui lòng nhập email hợp lệ</span>
                    </div>

                    <div class="form-group">
                        <label for="password" class="form-label">Mật khẩu mới (để trống nếu không đổi)</label>
                        <div class="password-input">
                            <input type="password" id="password" name="password" class="form-control">
                            <button type="button" class="toggle-password" tabindex="-1">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                        <span class="error-message">Mật khẩu phải có ít nhất 6 ký tự</span>
                    </div>

                    <div class="form-group">
                        <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới</label>
                        <div class="password-input">
                            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control">
                            <button type="button" class="toggle-password" tabindex="-1">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                        <span class="error-message">Mật khẩu xác nhận không khớp</span>
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

    <!-- Include JSP Fragments -->
    <jsp:include page="../fragments/toast.jsp"/>
    <jsp:include page="../fragments/loading-spinner.jsp"/>
    <jsp:include page="../fragments/confirmation-modal.jsp"/>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/common.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.getElementById('accountForm');
            let hasUnsavedChanges = false;
            
            // Setup real-time validation
            FormValidator.setupRealTimeValidation(form);
            
            // Track changes
            form.querySelectorAll('input, textarea, select').forEach(field => {
                field.addEventListener('input', function() {
                    hasUnsavedChanges = true;
                });
            });
            
            // Warn before leaving if there are unsaved changes
            window.addEventListener('beforeunload', function(e) {
                if (hasUnsavedChanges) {
                    e.preventDefault();
                    e.returnValue = 'Bạn có thay đổi chưa lưu. Bạn có chắc muốn rời khỏi trang?';
                }
            });
            
            // Real-time password confirmation check
            const confirmPasswordField = document.getElementById('confirmPassword');
            if (confirmPasswordField) {
                confirmPasswordField.addEventListener('input', function() {
                    const password = document.getElementById('password').value;
                    const formGroup = this.closest('.form-group');
                    
                    if (password && this.value) {
                        if (this.value === password) {
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
                    Toast.error('Vui lòng kiểm tra lại thông tin!');
                    return;
                }
                
                const password = document.getElementById('password');
                const confirmPassword = document.getElementById('confirmPassword');
                
                // Check password if changing
                if (password.value) {
                    if (password.value.length < 6) {
                        Toast.error('Mật khẩu phải có ít nhất 6 ký tự!');
                        password.closest('.form-group').classList.add('error');
                        return;
                    }
                    
                    if (password.value !== confirmPassword.value) {
                        Toast.error('Mật khẩu xác nhận không khớp!');
                        confirmPassword.closest('.form-group').classList.add('error');
                        return;
                    }
                }
                
                // Show confirmation
                Confirmation.show(
                    'Bạn có chắc muốn lưu các thay đổi?',
                    async function() {
                        Loading.show('Đang cập nhật thông tin...');
                        
                        const formData = new FormData(form);
                        
                        try {
                            const response = await fetch(form.action, {
                                method: 'POST',
                                body: formData
                            });
                            
                            const result = await response.json();
                            
                            if (result.success) {
                                Toast.success('Cập nhật thông tin thành công!');
                                hasUnsavedChanges = false;
                                
                                // Reload after 2s
                                setTimeout(() => {
                                    window.location.reload();
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

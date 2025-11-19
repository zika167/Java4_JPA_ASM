<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/pages/account-settings.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- Account Settings Modal -->
<div class="modal fade" id="accountSettingsModal" tabindex="-1" aria-labelledby="accountSettingsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="accountSettingsModalLabel">
                    <i class="fas fa-user-edit"></i> Cập nhật thông tin
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            
            <div class="modal-body">
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
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <i class="fas fa-times"></i> Đóng
                        </button>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> Cập nhật
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('accountForm');
    if (!form) return;
    
    let hasUnsavedChanges = false;
    
    FormValidator.setupRealTimeValidation(form);
    
    form.querySelectorAll('input, textarea, select').forEach(field => {
        field.addEventListener('input', function() {
            hasUnsavedChanges = true;
        });
    });
    
    window.addEventListener('beforeunload', function(e) {
        if (hasUnsavedChanges) {
            e.preventDefault();
            e.returnValue = 'Bạn có thay đổi chưa lưu. Bạn có chắc muốn rời khỏi trang?';
        }
    });
    
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
    
    form.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        if (!FormValidator.validateForm(this)) {
            Toast.error('Vui lòng kiểm tra lại thông tin!');
            return;
        }
        
        const password = document.getElementById('password');
        const confirmPassword = document.getElementById('confirmPassword');
        
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
    
    <c:if test="${not empty error}">
        Toast.error('${error}');
    </c:if>
    
    <c:if test="${not empty success}">
        Toast.success('${success}');
    </c:if>
});

window.openAccountSettingsModal = function() {
    const modal = new bootstrap.Modal(document.getElementById('accountSettingsModal'));
    modal.show();
};
</script>

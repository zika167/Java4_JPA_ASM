<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- Login Modal -->
<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="loginModalLabel">
                    <i class="fas fa-sign-in-alt"></i> Đăng nhập
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div id="loginError" class="alert alert-danger d-none">
                    <i class="fas fa-exclamation-circle"></i> <span class="error-text"></span>
                </div>

                <form id="loginModalForm">
                    <div class="mb-3">
                        <label for="loginUsername" class="form-label">Tên đăng nhập hoặc Email</label>
                        <input type="text" id="loginUsername" name="username" class="form-control" required autofocus>
                    </div>

                    <div class="mb-3">
                        <label for="loginPassword" class="form-label">Mật khẩu</label>
                        <div class="input-group">
                            <input type="password" id="loginPassword" name="password" class="form-control" required>
                            <button class="btn btn-outline-secondary toggle-password-modal" type="button">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>

                    <div class="mb-3 d-flex align-items-center justify-content-between">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="remember" value="true" id="rememberMe">
                            <label class="form-check-label" for="rememberMe">
                                Ghi nhớ đăng nhập
                            </label>
                        </div>
                        <a href="#" class="text-decoration-none" data-bs-toggle="modal" data-bs-target="#forgotPasswordModal" data-bs-dismiss="modal">
                            Quên mật khẩu?
                        </a>
                    </div>

                    <button type="submit" class="btn btn-primary w-100 mb-3">
                        <i class="fas fa-sign-in-alt"></i> Đăng nhập
                    </button>

                    <div class="text-center">
                        <span class="text-muted">Chưa có tài khoản?</span>
                        <a href="#" class="text-decoration-none" data-bs-toggle="modal" data-bs-target="#registerModal" data-bs-dismiss="modal">
                            Đăng ký ngay
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Register Modal -->
<div class="modal fade" id="registerModal" tabindex="-1" aria-labelledby="registerModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="registerModalLabel">
                    <i class="fas fa-user-plus"></i> Đăng ký tài khoản
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div id="registerError" class="alert alert-danger d-none">
                    <i class="fas fa-exclamation-circle"></i> <span class="error-text"></span>
                </div>

                <form id="registerModalForm">
                    <div class="mb-3">
                        <label for="registerUsername" class="form-label">Tên đăng nhập</label>
                        <input type="text" id="registerUsername" name="username" class="form-control" required
                               pattern="[a-zA-Z0-9_]{3,20}" 
                               title="Tên đăng nhập từ 3-20 ký tự, chỉ chứa chữ cái, số và dấu gạch dưới">
                        <small class="form-text text-muted">Từ 3-20 ký tự, chỉ chứa chữ cái, số và dấu gạch dưới</small>
                    </div>

                    <div class="mb-3">
                        <label for="registerFullname" class="form-label">Họ và tên</label>
                        <input type="text" id="registerFullname" name="fullname" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label for="registerEmail" class="form-label">Email</label>
                        <input type="email" id="registerEmail" name="email" class="form-control" required>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="registerPassword" class="form-label">Mật khẩu</label>
                            <div class="input-group">
                                <input type="password" id="registerPassword" name="password" class="form-control" required minlength="6">
                                <button class="btn btn-outline-secondary toggle-password-modal" type="button">
                                    <i class="fas fa-eye"></i>
                                </button>
                            </div>
                            <small class="form-text text-muted">Ít nhất 6 ký tự</small>
                        </div>

                        <div class="col-md-6 mb-3">
                            <label for="registerConfirmPassword" class="form-label">Xác nhận mật khẩu</label>
                            <div class="input-group">
                                <input type="password" id="registerConfirmPassword" name="confirmPassword" class="form-control" required>
                                <button class="btn btn-outline-secondary toggle-password-modal" type="button">
                                    <i class="fas fa-eye"></i>
                                </button>
                            </div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="agreeTerms" value="true" id="agreeTerms" required>
                            <label class="form-check-label" for="agreeTerms">
                                Tôi đồng ý với <a href="#" class="text-decoration-none">Điều khoản sử dụng</a> 
                                và <a href="#" class="text-decoration-none">Chính sách bảo mật</a>
                            </label>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary w-100 mb-3">
                        <i class="fas fa-user-plus"></i> Đăng ký
                    </button>

                    <div class="text-center">
                        <span class="text-muted">Đã có tài khoản?</span>
                        <a href="#" class="text-decoration-none" data-bs-toggle="modal" data-bs-target="#loginModal" data-bs-dismiss="modal">
                            Đăng nhập ngay
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Forgot Password Modal -->
<div class="modal fade" id="forgotPasswordModal" tabindex="-1" aria-labelledby="forgotPasswordModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="forgotPasswordModalLabel">
                    <i class="fas fa-key"></i> Quên mật khẩu
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div id="forgotPasswordError" class="alert alert-danger d-none">
                    <i class="fas fa-exclamation-circle"></i> <span class="error-text"></span>
                </div>
                <div id="forgotPasswordSuccess" class="alert alert-success d-none">
                    <i class="fas fa-check-circle"></i> <span class="success-text"></span>
                </div>

                <p class="text-muted mb-4">
                    Nhập email của bạn để nhận liên kết đặt lại mật khẩu
                </p>

                <form id="forgotPasswordModalForm">
                    <div class="mb-3">
                        <label for="forgotEmail" class="form-label">Email</label>
                        <input type="email" id="forgotEmail" name="email" class="form-control" required>
                    </div>

                    <button type="submit" class="btn btn-primary w-100 mb-3">
                        <i class="fas fa-paper-plane"></i> Gửi liên kết
                    </button>

                    <div class="text-center">
                        <a href="#" class="text-decoration-none" data-bs-toggle="modal" data-bs-target="#loginModal" data-bs-dismiss="modal">
                            <i class="fas fa-arrow-left"></i> Quay lại đăng nhập
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Change Password Modal -->
<div class="modal fade" id="changePasswordModal" tabindex="-1" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="changePasswordModalLabel">
                    <i class="fas fa-lock"></i> Đổi mật khẩu
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div id="changePasswordError" class="alert alert-danger d-none">
                    <i class="fas fa-exclamation-circle"></i> <span class="error-text"></span>
                </div>
                <div id="changePasswordSuccess" class="alert alert-success d-none">
                    <i class="fas fa-check-circle"></i> <span class="success-text"></span>
                </div>

                <form id="changePasswordModalForm">
                    <div class="mb-3">
                        <label for="currentPassword" class="form-label">Mật khẩu hiện tại</label>
                        <div class="input-group">
                            <input type="password" id="currentPassword" name="currentPassword" class="form-control" required>
                            <button class="btn btn-outline-secondary toggle-password-modal" type="button">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="newPassword" class="form-label">Mật khẩu mới</label>
                        <div class="input-group">
                            <input type="password" id="newPassword" name="newPassword" class="form-control" required minlength="6">
                            <button class="btn btn-outline-secondary toggle-password-modal" type="button">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                        <small class="form-text text-muted">Ít nhất 6 ký tự</small>
                    </div>

                    <div class="mb-3">
                        <label for="confirmNewPassword" class="form-label">Xác nhận mật khẩu mới</label>
                        <div class="input-group">
                            <input type="password" id="confirmNewPassword" name="confirmNewPassword" class="form-control" required>
                            <button class="btn btn-outline-secondary toggle-password-modal" type="button">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary w-100">
                        <i class="fas fa-check"></i> Đổi mật khẩu
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<style>
.modal-content {
    border-radius: 15px;
}

.modal-header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    border-radius: 15px 15px 0 0;
}

.modal-header .btn-close {
    filter: brightness(0) invert(1);
}

.modal-body {
    padding: 2rem;
}

.modal .form-label {
    font-weight: 600;
    color: #2d3748;
    margin-bottom: 0.5rem;
}

.modal .form-control {
    border-radius: 8px;
    border: 1.5px solid #e2e8f0;
    padding: 0.75rem 1rem;
    transition: all 0.3s ease;
}

.modal .form-control:focus {
    border-color: #667eea;
    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.modal .btn-primary {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    padding: 0.75rem;
    border-radius: 8px;
    font-weight: 600;
    transition: all 0.3s ease;
}

.modal .btn-primary:hover {
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(102, 126, 234, 0.3);
}

.modal .input-group .btn-outline-secondary {
    border: 1.5px solid #e2e8f0;
    border-left: none;
}

.modal .toggle-password-modal:hover {
    background-color: #f7fafc;
}

.modal .alert {
    border-radius: 8px;
    border: none;
}

.modal .alert-danger {
    background-color: #fee;
    color: #c53030;
}

.modal .alert-success {
    background-color: #e6ffed;
    color: #22543d;
}
</style>

<script>
document.addEventListener('DOMContentLoaded', function() {
    // Toggle password visibility for modals
    document.querySelectorAll('.toggle-password-modal').forEach(button => {
        button.addEventListener('click', function() {
            const input = this.parentElement.querySelector('input');
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

    // Login form submission using XMLHttpRequest for better debugging
    document.getElementById('loginModalForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const formData = new FormData(this);
        const errorDiv = document.getElementById('loginError');
        const loginUrl = '<c:url value="/login"/>';
        
        // Hide error initially
        errorDiv.classList.add('d-none');
        
        // Debug: Log form data
        console.log('Login attempt with username:', formData.get('username'));
        console.log('Password length:', formData.get('password').length);
        console.log('Login URL:', loginUrl);
        
        // Convert FormData to URLSearchParams for proper form encoding
        const params = new URLSearchParams();
        for (const [key, value] of formData.entries()) {
            params.append(key, value);
            console.log('Form param:', key, '=', value);
        }
        
        const xhr = new XMLHttpRequest();
        
        xhr.onload = function() {
            console.log('XHR Status:', xhr.status);
            console.log('XHR Response URL:', xhr.responseURL);
            console.log('XHR Content-Type:', xhr.getResponseHeader('Content-Type'));
            
            // Check if we were redirected
            // If responseURL is different from loginUrl, it means redirect happened
            if (xhr.responseURL && !xhr.responseURL.includes('/login') && !xhr.responseURL.includes('login.jsp')) {
                // Login successful - redirect happened
                console.log('Login successful, redirecting to:', xhr.responseURL);
                window.location.href = xhr.responseURL;
            } else {
                // Login failed - still on login page
                console.log('Login failed');
                errorDiv.classList.remove('d-none');
                errorDiv.querySelector('.error-text').textContent = 'Tên đăng nhập hoặc mật khẩu không đúng';
            }
        };
        
        xhr.onerror = function() {
            console.error('XHR Error');
            errorDiv.classList.remove('d-none');
            errorDiv.querySelector('.error-text').textContent = 'Đã xảy ra lỗi kết nối. Vui lòng thử lại.';
        };
        
        xhr.open('POST', loginUrl, true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send(params.toString());
    });

    // Register form submission
    document.getElementById('registerModalForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const password = document.getElementById('registerPassword').value;
        const confirmPassword = document.getElementById('registerConfirmPassword').value;
        const errorDiv = document.getElementById('registerError');
        
        if (password !== confirmPassword) {
            errorDiv.classList.remove('d-none');
            errorDiv.querySelector('.error-text').textContent = 'Mật khẩu xác nhận không khớp';
            return;
        }
        
        const formData = new FormData(this);
        
        fetch('${pageContext.request.contextPath}/auth/register', {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (response.ok) {
                bootstrap.Modal.getInstance(document.getElementById('registerModal')).hide();
                const loginModal = new bootstrap.Modal(document.getElementById('loginModal'));
                loginModal.show();
                if (typeof Toast !== 'undefined') {
                    Toast.success('Đăng ký thành công! Vui lòng đăng nhập.');
                }
            } else {
                return response.text();
            }
        })
        .then(text => {
            if (text) {
                errorDiv.classList.remove('d-none');
                errorDiv.querySelector('.error-text').textContent = 'Đăng ký thất bại. Vui lòng thử lại.';
            }
        })
        .catch(error => {
            errorDiv.classList.remove('d-none');
            errorDiv.querySelector('.error-text').textContent = 'Đã xảy ra lỗi. Vui lòng thử lại.';
        });
    });

    // Forgot password form submission
    document.getElementById('forgotPasswordModalForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const successDiv = document.getElementById('forgotPasswordSuccess');
        const errorDiv = document.getElementById('forgotPasswordError');
        
        // Hide previous messages
        successDiv.classList.add('d-none');
        errorDiv.classList.add('d-none');
        
        // Simulate sending email (replace with actual API call)
        setTimeout(() => {
            successDiv.classList.remove('d-none');
            successDiv.querySelector('.success-text').textContent = 'Liên kết đặt lại mật khẩu đã được gửi đến email của bạn!';
            this.reset();
        }, 1000);
    });

    // Change password form submission
    document.getElementById('changePasswordModalForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const newPassword = document.getElementById('newPassword').value;
        const confirmNewPassword = document.getElementById('confirmNewPassword').value;
        const successDiv = document.getElementById('changePasswordSuccess');
        const errorDiv = document.getElementById('changePasswordError');
        
        // Hide previous messages
        successDiv.classList.add('d-none');
        errorDiv.classList.add('d-none');
        
        if (newPassword !== confirmNewPassword) {
            errorDiv.classList.remove('d-none');
            errorDiv.querySelector('.error-text').textContent = 'Mật khẩu xác nhận không khớp';
            return;
        }
        
        const formData = new FormData(this);
        
        // Replace with actual API call
        setTimeout(() => {
            successDiv.classList.remove('d-none');
            successDiv.querySelector('.success-text').textContent = 'Đổi mật khẩu thành công!';
            this.reset();
            
            setTimeout(() => {
                bootstrap.Modal.getInstance(document.getElementById('changePasswordModal')).hide();
            }, 1500);
        }, 1000);
    });

    // Clear error messages when modal is hidden
    document.querySelectorAll('.modal').forEach(modal => {
        modal.addEventListener('hidden.bs.modal', function() {
            this.querySelectorAll('.alert').forEach(alert => alert.classList.add('d-none'));
            this.querySelectorAll('form').forEach(form => form.reset());
        });
    });
});
</script>

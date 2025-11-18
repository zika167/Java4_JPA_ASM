<%--
  ⚠️ DEPRECATED: This page is deprecated and should not be used.
  
  Forgot Password functionality has been moved to a modal popup.
  See: /views/fragments/auth-modals.jsp
  
  This file is kept for backward compatibility only.
  New implementations should use the modal instead.
  
  Redirect users to: index.jsp (navbar will show forgot password modal)
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên mật khẩu - 4in1</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/auth/forgot-password.css">
</head>
<body>
    <div class="account-settings-container">
        <div class="settings-header">
            <h1><i class="fas fa-unlock-alt"></i> Quên mật khẩu</h1>
        </div>
        
        <div class="settings-content">
            <div class="settings-main">
                <c:if test="${not empty error}">
                    <div class="alert alert-custom alert-error">
                        <i class="fas fa-exclamation-circle"></i> ${error}
                    </div>
                </c:if>
                
                <div class="alert alert-custom alert-info">
                    <i class="fas fa-info-circle"></i>
                    Nhập tên đăng nhập và email của bạn. Chúng tôi sẽ gửi hướng dẫn reset mật khẩu đến email của bạn.
                </div>
                
                <form id="forgotPasswordForm" action="${pageContext.request.contextPath}/auth/forgot-password" method="post">
                    <div class="form-group">
                        <label for="username" class="form-label">Tên đăng nhập</label>
                        <input type="text" id="username" name="username" class="form-control" required>
                        <span class="error-message">Vui lòng nhập tên đăng nhập</span>
                    </div>

                    <div class="form-group">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" id="email" name="email" class="form-control" required>
                        <span class="error-message">Vui lòng nhập email hợp lệ</span>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-paper-plane"></i> Gửi email reset mật khẩu
                        </button>
                    </div>
                    
                    <div style="text-align: center; margin-top: 20px; padding-top: 20px; border-top: 1px solid #eee;">
                        <span style="color: #666; font-size: 14px;">Đã nhớ mật khẩu?</span>
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
    <jsp:include page="../fragments/confirmation-modal.jsp"/>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/common.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.getElementById('forgotPasswordForm');
            
            // Setup real-time validation
            FormValidator.setupRealTimeValidation(form);
            
            // Form submission with confirmation
            form.addEventListener('submit', async function(e) {
                e.preventDefault();
                
                // Validate form
                if (!FormValidator.validateForm(this)) {
                    Toast.error('Vui lòng điền đầy đủ thông tin!');
                    return;
                }
                
                const username = document.getElementById('username').value;
                const email = document.getElementById('email').value;
                
                // Show confirmation
                Confirmation.show(
                    `Gửi email reset mật khẩu đến ${email}?`,
                    async function() {
                        Loading.show('Đang gửi email...');
                        
                        const formData = new FormData(form);
                        
                        try {
                            const response = await fetch(form.action, {
                                method: 'POST',
                                body: formData
                            });
                            
                            const result = await response.json();
                            
                            if (result.success) {
                                Toast.success('Email đã được gửi! Vui lòng kiểm tra hộp thư của bạn.');
                                form.reset();
                                
                                // Redirect to login after 3s
                                setTimeout(() => {
                                    window.location.href = '${pageContext.request.contextPath}/views/auth/login.jsp';
                                }, 3000);
                            } else {
                                Toast.error(result.message || 'Email hoặc tên đăng nhập không đúng!');
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
            
            // Show toast if there's an error from server
            <c:if test="${not empty error}">
                Toast.error('${error}');
            </c:if>
        });
    </script>
</body>
</html>
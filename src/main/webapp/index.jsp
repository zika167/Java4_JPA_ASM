<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%-- Redirect to servlet if accessed directly without data --%>
<c:if test="${empty videos and empty users}">
    <c:redirect url="${pageContext.request.contextPath}/index"/>
</c:if>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>4in1 - Trang chủ</title>
    
    <!-- Bootstrap 5.3.3 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/static/css/core/variables.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/core/common.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/components/component-video.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/pages/index.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">
    <!-- Include Navbar -->
    <jsp:include page="views/components/navbar.jsp"/>
    
    <!-- Main Content -->
    <main class="flex-grow-1" style="background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%);">
        <div class="container-fluid p-4" style="max-width: 1400px; margin: 0 auto;">
            <!-- Welcome Section -->
            <div class="welcome-section">
                <h1 class="welcome-title">
                    <i class="bi bi-film"></i>
                    CHÀO MỪNG ĐẾN VỚI 4IN1
                </h1>
                <p class="welcome-subtitle">Khám phá bộ sưu tập video đặc sắc của chúng tôi</p>
            </div>

            <!-- Info Cards for User -->
            <c:if test="${not empty sessionScope.user}">
                <div class="row g-4 mb-4">
                    <!-- User Profile Card -->
                    <div class="col-md-4">
                        <div class="info-card info-card-primary">
                            <div class="info-icon">
                                <i class="bi bi-person-circle"></i>
                            </div>
                            <div class="info-details">
                                <div class="info-title">Xin chào!</div>
                                <div class="info-value">${sessionScope.user}</div>
                            </div>
                        </div>
                    </div>

                    <!-- Favorites Card -->
                    <div class="col-md-4">
                        <div class="info-card info-card-danger">
                            <div class="info-icon">
                                <i class="bi bi-heart-fill"></i>
                            </div>
                            <div class="info-details">
                                <div class="info-title">Video yêu thích</div>
                                <div class="info-value">
                                    <a href="${pageContext.request.contextPath}/favorite" class="info-link">
                                        Xem danh sách <i class="bi bi-arrow-right"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Account Settings Card -->
                    <div class="col-md-4">
                        <div class="info-card info-card-success">
                            <div class="info-icon">
                                <i class="bi bi-gear-fill"></i>
                            </div>
                            <div class="info-details">
                                <div class="info-title">Cài đặt tài khoản</div>
                                <div class="info-value">
                                    <a href="javascript:openAccountSettingsModal()" class="info-link">
                                        Quản lý tài khoản <i class="bi bi-arrow-right"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
            
            <!-- Video Grid -->
            <div class="video-section">
                <div class="quick-access-card">
                    <h3 class="card-title">
                        <i class="bi bi-collection-play-fill"></i>
                        VIDEO NỔI BẬT
                    </h3>
                    <div>
                        <jsp:include page="views/components/component-video.jsp"/>
                    </div>
                </div>
            </div>
        </div>
    </main>
    
    <!-- Include Footer -->
    <jsp:include page="views/components/footer.jsp"/>
    
    <!-- Include Modals & Fragments -->
    <jsp:include page="views/fragments/toast.jsp"/>
    <jsp:include page="views/fragments/loading-spinner.jsp"/>
    <jsp:include page="views/fragments/confirmation-modal.jsp"/>
    <jsp:include page="views/fragments/video-preview-modal.jsp"/>
    <jsp:include page="views/fragments/share-modal.jsp"/>
    <jsp:include page="views/fragments/auth-modals.jsp"/>
    <jsp:include page="views/auth/account-settings.jsp"/>
    
    <!-- Bootstrap 5.3.3 JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Common JS Functions -->
    <script src="${pageContext.request.contextPath}/static/js/common.js"></script>
    
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Check if user just logged in
            const urlParams = new URLSearchParams(window.location.search);
            if (urlParams.get('login') === 'success') {
                Toast.success('Đăng nhập thành công! Chào mừng bạn đến với 4in1.');
                // Remove the parameter from URL
                window.history.replaceState({}, document.title, window.location.pathname);
            }
            
            // Check if user just registered
            if (urlParams.get('register') === 'success') {
                Toast.success('Đăng ký thành công! Chào mừng bạn đến với 4in1.');
                window.history.replaceState({}, document.title, window.location.pathname);
            }
        });
    </script>
</body>
</html>

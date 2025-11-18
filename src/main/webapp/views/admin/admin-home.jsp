<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - 4in1</title>
    <!-- Bootstrap 5.3.3 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin/admin-home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/components/component-video.css">

    <!-- Bootstrap 5.3.3 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Custom JS -->
    <script src="${pageContext.request.contextPath}/static/js/common.js"></script>
</head>
<body>
    <!-- Include Admin Navbar -->
    <jsp:include page="/views/admin/administration-navbar.jsp">
        <jsp:param name="page" value="home"/>
    </jsp:include>

    <div class="container-fluid p-4">
        <!-- Welcome Section -->
        <div class="welcome-section">
            <h1 class="dashboard-title">
                <i class="bi bi-speedometer2"></i>
                ADMIN DASHBOARD
            </h1>
            <p class="dashboard-subtitle">Chào mừng đến với trang quản trị hệ thống 4in1</p>
        </div>

        <!-- Statistics Cards -->
        <div class="row g-4 mb-4">
            <!-- Total Users Card -->
            <div class="col-xl-3 col-md-6">
                <div class="stat-card stat-card-primary">
                    <div class="stat-icon">
                        <i class="bi bi-people-fill"></i>
                    </div>
                    <div class="stat-details">
                        <div class="stat-number">${totalUsers != null ? totalUsers : 0}</div>
                        <div class="stat-label">TỔNG NGƯỜI DÙNG</div>
                    </div>
                    <a href="${pageContext.request.contextPath}/admin/users" class="stat-link">
                        Xem chi tiết <i class="bi bi-arrow-right"></i>
                    </a>
                </div>
            </div>

            <!-- Total Videos Card -->
            <div class="col-xl-3 col-md-6">
                <div class="stat-card stat-card-success">
                    <div class="stat-icon">
                        <i class="bi bi-play-circle-fill"></i>
                    </div>
                    <div class="stat-details">
                        <div class="stat-number">${totalVideos != null ? totalVideos : 0}</div>
                        <div class="stat-label">TỔNG VIDEO</div>
                    </div>
                    <a href="${pageContext.request.contextPath}/admin/videos" class="stat-link">
                        Xem chi tiết <i class="bi bi-arrow-right"></i>
                    </a>
                </div>
            </div>

            <!-- Total Reports Card -->
            <div class="col-xl-3 col-md-6">
                <div class="stat-card stat-card-warning">
                    <div class="stat-icon">
                        <i class="bi bi-bar-chart-fill"></i>
                    </div>
                    <div class="stat-details">
                        <div class="stat-number">${totalReports != null ? totalReports : 0}</div>
                        <div class="stat-label">BÁO CÁO</div>
                    </div>
                    <a href="${pageContext.request.contextPath}/admin/reports" class="stat-link">
                        Xem chi tiết <i class="bi bi-arrow-right"></i>
                    </a>
                </div>
            </div>

            <!-- Active Users Card -->
            <div class="col-xl-3 col-md-6">
                <div class="stat-card stat-card-info">
                    <div class="stat-icon">
                        <i class="bi bi-person-check-fill"></i>
                    </div>
                    <div class="stat-details">
                        <div class="stat-number">${activeUsers != null ? activeUsers : 0}</div>
                        <div class="stat-label">NGƯỜI DÙNG HOẠT ĐỘNG</div>
                    </div>
                    <a href="${pageContext.request.contextPath}/admin/users" class="stat-link">
                        Xem chi tiết <i class="bi bi-arrow-right"></i>
                    </a>
                </div>
            </div>
        </div>

        <!-- Quick Access Section -->
        <div class="row g-4">
            <!-- Management Tools -->
            <div class="col-lg-6">
                <div class="quick-access-card">
                    <h3 class="card-title">
                        <i class="bi bi-gear-fill"></i>
                        CÔNG CỤ QUẢN LÝ
                    </h3>
                    <div class="quick-links">
                        <a href="${pageContext.request.contextPath}/admin/users" class="quick-link-item">
                            <div class="quick-link-icon">
                                <i class="bi bi-people"></i>
                            </div>
                            <div class="quick-link-content">
                                <div class="quick-link-title">Quản lý người dùng</div>
                                <div class="quick-link-desc">Thêm, sửa, xóa tài khoản người dùng</div>
                            </div>
                            <i class="bi bi-chevron-right"></i>
                        </a>

                        <a href="${pageContext.request.contextPath}/admin/videos" class="quick-link-item">
                            <div class="quick-link-icon">
                                <i class="bi bi-play-circle"></i>
                            </div>
                            <div class="quick-link-content">
                                <div class="quick-link-title">Quản lý video</div>
                                <div class="quick-link-desc">Thêm, sửa, xóa video</div>
                            </div>
                            <i class="bi bi-chevron-right"></i>
                        </a>

                        <a href="${pageContext.request.contextPath}/admin/reports" class="quick-link-item">
                            <div class="quick-link-icon">
                                <i class="bi bi-bar-chart"></i>
                            </div>
                            <div class="quick-link-content">
                                <div class="quick-link-title">Báo cáo thống kê</div>
                                <div class="quick-link-desc">Xem báo cáo và thống kê hệ thống</div>
                            </div>
                            <i class="bi bi-chevron-right"></i>
                        </a>
                    </div>
                </div>
            </div>

            <!-- System Information -->
            <div class="col-lg-6">
                <div class="quick-access-card">
                    <h3 class="card-title">
<%--                        <i class="bi bi-info-circle-fill"></i>--%>
                        THÔNG TIN HỆ THỐNG
                    </h3>
                    <div class="system-info">
                        <div class="info-item">
                            <div class="info-label">
<%--                                <i class="bi bi-calendar3"></i>--%>
                                Ngày hôm nay
                            </div>
                            <div class="info-value">
                                <jsp:useBean id="now" class="java.util.Date"/>
                                <c:out value="${now}"/>
                            </div>
                        </div>

                        <div class="info-item">
                            <div class="info-label">
<%--                                <i class="bi bi-server"></i>--%>
                                Phiên bản hệ thống
                            </div>
                            <div class="info-value">4in1 v1.0</div>
                        </div>

                        <div class="info-item">
                            <div class="info-label">
<%--                                <i class="bi bi-cpu"></i>--%>
                                Trạng thái
                            </div>
                            <div class="info-value">
                                <span class="badge bg-success">
<%--                                    <i class="bi bi-check-circle"></i> --%>
                                    Hoạt động bình thường
                                </span>
                            </div>
                        </div>

                        <div class="info-item">
                            <div class="info-label">
<%--                                <i class="bi bi-person-badge"></i>--%>
                                Admin đang đăng nhập
                            </div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.user}">
                                        ${sessionScope.user.fullname}
                                    </c:when>
                                    <c:otherwise>
                                        Administrator
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Team Videos Section -->
        <div class="row mt-5">
            <div class="col-12">
                <div class="quick-access-card">
                    <h3 class="card-title">
                        <i class="bi bi-collection-play-fill"></i>
                        CÁC VIDEO CỦA NHÓM
                    </h3>

                    <!-- Include Component Video -->
                    <div class="admin-video-grid">
                        <jsp:include page="../components/component-video.jsp">
                            <jsp:param name="limit" value="6"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
        </div>

        <!-- Recent Videos Section -->
        <div class="row mt-5">
            <div class="col-12">
                <div class="quick-access-card">
                    <h3 class="card-title">
                        <i class="bi bi-play-circle-fill"></i>
                        VIDEO MỚI NHẤT
                    </h3>

                    <!-- Include Component Video -->
                    <div class="admin-video-grid">
                        <jsp:include page="../components/component-video.jsp">
                            <jsp:param name="limit" value="6"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Include Modals & Fragments -->
    <jsp:include page="../fragments/toast.jsp"/>
    <jsp:include page="../fragments/loading-spinner.jsp"/>
    <jsp:include page="../fragments/share-modal.jsp"/>
    
    <!-- Include Footer -->
    <jsp:include page="../components/footer.jsp"/>
</body>
</html>

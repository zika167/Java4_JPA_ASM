<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Video đã thích</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link href="${pageContext.request.contextPath}/static/css/favorites.css" rel="stylesheet">
</head>
<body class="bg-light">
    <!-- Navbar -->
    <jsp:include page="../components/navbar.jsp" />

    <!-- Main Content -->
    <main class="main-content">
            <div class="content-wrapper">
                <!-- Header -->
                <div class="header-section">
                    <h1>Video đã thích</h1>
                    <button class="btn btn-sm btn-outline-secondary">
                        <i class="fas fa-sort me-2"></i>Sắp xếp
                    </button>
                </div>

                <!-- Video Grid -->
                <div class="video-grid">
                    <!-- Video Card 1 -->
                    <div class="video-container">
                        <div class="video-thumbnail">
                            <img src="https://via.placeholder.com/320x180?text=Video+1" alt="Video 1" class="img-fluid">
                            <span class="duration-badge">12:34</span>
                        </div>
                        <div class="video-metadata">
                            <img src="https://via.placeholder.com/40x40?text=Avatar" alt="Channel" class="channel-avatar">
                            <div class="video-info">
                                <h6 class="video-title">Tên video dài có thể tự động wrap sang dòng tiếp theo</h6>
                                <p class="channel-name">Tên Kênh</p>
                                <p class="video-stats">1.2M lượt xem • 2 ngày trước</p>
                            </div>
                        </div>
                        <div class="video-actions">
                            <button class="btn-action"><i class="fas fa-heart"></i></button>
                            <button class="btn-action"><i class="fas fa-share"></i></button>
                            <div class="dropdown">
                                <button class="btn-action dropdown-toggle" data-bs-toggle="dropdown">
                                    <i class="fas fa-ellipsis-v"></i>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-sm">
                                    <li><a class="dropdown-item" href="#"><i class="fas fa-list me-2"></i>Thêm vào danh sách</a></li>
                                    <li><a class="dropdown-item" href="#"><i class="fas fa-flag me-2"></i>Báo cáo</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Video Card 2 -->
                    <div class="video-container">
                        <div class="video-thumbnail">
                            <img src="https://via.placeholder.com/320x180?text=Video+2" alt="Video 2" class="img-fluid">
                            <span class="duration-badge">8:45</span>
                        </div>
                        <div class="video-metadata">
                            <img src="https://via.placeholder.com/40x40?text=Avatar" alt="Channel" class="channel-avatar">
                            <div class="video-info">
                                <h6 class="video-title">Video thứ hai với tiêu đề khác</h6>
                                <p class="channel-name">Channel ABC</p>
                                <p class="video-stats">5.7K lượt xem • 1 tuần trước</p>
                            </div>
                        </div>
                        <div class="video-actions">
                            <button class="btn-action liked"><i class="fas fa-heart"></i></button>
                            <button class="btn-action"><i class="fas fa-share"></i></button>
                            <div class="dropdown">
                                <button class="btn-action dropdown-toggle" data-bs-toggle="dropdown">
                                    <i class="fas fa-ellipsis-v"></i>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-sm">
                                    <li><a class="dropdown-item" href="#"><i class="fas fa-list me-2"></i>Thêm vào danh sách</a></li>
                                    <li><a class="dropdown-item" href="#"><i class="fas fa-flag me-2"></i>Báo cáo</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Video Card 3 -->
                    <div class="video-container">
                        <div class="video-thumbnail">
                            <img src="https://via.placeholder.com/320x180?text=Video+3" alt="Video 3" class="img-fluid">
                            <span class="duration-badge">15:20</span>
                        </div>
                        <div class="video-metadata">
                            <img src="https://via.placeholder.com/40x40?text=Avatar" alt="Channel" class="channel-avatar">
                            <div class="video-info">
                                <h6 class="video-title">Video thứ ba - tiêu đề video thứ ba</h6>
                                <p class="channel-name">Channel XYZ</p>
                                <p class="video-stats">3.1M lượt xem • 3 tuần trước</p>
                            </div>
                        </div>
                        <div class="video-actions">
                            <button class="btn-action"><i class="fas fa-heart"></i></button>
                            <button class="btn-action"><i class="fas fa-share"></i></button>
                            <div class="dropdown">
                                <button class="btn-action dropdown-toggle" data-bs-toggle="dropdown">
                                    <i class="fas fa-ellipsis-v"></i>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-sm">
                                    <li><a class="dropdown-item" href="#"><i class="fas fa-list me-2"></i>Thêm vào danh sách</a></li>
                                    <li><a class="dropdown-item" href="#"><i class="fas fa-flag me-2"></i>Báo cáo</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Video Card 4 -->
                    <div class="video-container">
                        <div class="video-thumbnail">
                            <img src="https://via.placeholder.com/320x180?text=Video+4" alt="Video 4" class="img-fluid">
                            <span class="duration-badge">9:12</span>
                        </div>
                        <div class="video-metadata">
                            <img src="https://via.placeholder.com/40x40?text=Avatar" alt="Channel" class="channel-avatar">
                            <div class="video-info">
                                <h6 class="video-title">Video thứ tư - content mới</h6>
                                <p class="channel-name">Channel 123</p>
                                <p class="video-stats">890K lượt xem • 5 ngày trước</p>
                            </div>
                        </div>
                        <div class="video-actions">
                            <button class="btn-action"><i class="fas fa-heart"></i></button>
                            <button class="btn-action"><i class="fas fa-share"></i></button>
                            <div class="dropdown">
                                <button class="btn-action dropdown-toggle" data-bs-toggle="dropdown">
                                    <i class="fas fa-ellipsis-v"></i>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-sm">
                                    <li><a class="dropdown-item" href="#"><i class="fas fa-list me-2"></i>Thêm vào danh sách</a></li>
                                    <li><a class="dropdown-item" href="#"><i class="fas fa-flag me-2"></i>Báo cáo</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Video Card 5 -->
                    <div class="video-container">
                        <div class="video-thumbnail">
                            <img src="https://via.placeholder.com/320x180?text=Video+5" alt="Video 5" class="img-fluid">
                            <span class="duration-badge">20:15</span>
                        </div>
                        <div class="video-metadata">
                            <img src="https://via.placeholder.com/40x40?text=Avatar" alt="Channel" class="channel-avatar">
                            <div class="video-info">
                                <h6 class="video-title">Video thứ năm video hay lắm</h6>
                                <p class="channel-name">Channel 456</p>
                                <p class="video-stats">2.5M lượt xem • 1 tháng trước</p>
                            </div>
                        </div>
                        <div class="video-actions">
                            <button class="btn-action liked"><i class="fas fa-heart"></i></button>
                            <button class="btn-action"><i class="fas fa-share"></i></button>
                            <div class="dropdown">
                                <button class="btn-action dropdown-toggle" data-bs-toggle="dropdown">
                                    <i class="fas fa-ellipsis-v"></i>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-sm">
                                    <li><a class="dropdown-item" href="#"><i class="fas fa-list me-2"></i>Thêm vào danh sách</a></li>
                                    <li><a class="dropdown-item" href="#"><i class="fas fa-flag me-2"></i>Báo cáo</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Video Card 6 -->
                    <div class="video-container">
                        <div class="video-thumbnail">
                            <img src="https://via.placeholder.com/320x180?text=Video+6" alt="Video 6" class="img-fluid">
                            <span class="duration-badge">11:30</span>
                        </div>
                        <div class="video-metadata">
                            <img src="https://via.placeholder.com/40x40?text=Avatar" alt="Channel" class="channel-avatar">
                            <div class="video-info">
                                <h6 class="video-title">Video cuối cùng trong danh sách yêu thích</h6>
                                <p class="channel-name">Channel 789</p>
                                <p class="video-stats">4.3M lượt xem • 10 ngày trước</p>
                            </div>
                        </div>
                        <div class="video-actions">
                            <button class="btn-action"><i class="fas fa-heart"></i></button>
                            <button class="btn-action"><i class="fas fa-share"></i></button>
                            <div class="dropdown">
                                <button class="btn-action dropdown-toggle" data-bs-toggle="dropdown">
                                    <i class="fas fa-ellipsis-v"></i>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-sm">
                                    <li><a class="dropdown-item" href="#"><i class="fas fa-list me-2"></i>Thêm vào danh sách</a></li>
                                    <li><a class="dropdown-item" href="#"><i class="fas fa-flag me-2"></i>Báo cáo</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    <!-- Footer -->
    <jsp:include page="../components/footer-new.jsp" />

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Like button toggle
        document.querySelectorAll('.btn-action').forEach(btn => {
            if (btn.innerHTML.includes('fa-heart')) {
                btn.addEventListener('click', function() {
                    this.classList.toggle('liked');
                });
            }
        });
    </script>
</body>
</html>

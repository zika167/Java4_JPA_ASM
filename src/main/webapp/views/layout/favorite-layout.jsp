<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Video đã thích</title>
    <!-- Bootstrap 5.3.3 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/static/css/pages/favorites.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/core/variables.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/core/common.css" rel="stylesheet">
    <style>
        /* Fallback styling for images that fail to load */
        .video-thumbnail img {
            background-color: #f0f0f0;
            min-height: 180px;
            object-fit: cover;
        }
        
        .channel-avatar {
            background-color: #e0e0e0;
        }
        
        /* Loading placeholder animation */
        .video-thumbnail::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, #f0f0f0 25%, #e0e0e0 50%, #f0f0f0 75%);
            background-size: 200% 100%;
            animation: loading 1.5s infinite;
            z-index: -1;
        }
        
        @keyframes loading {
            0% { background-position: 200% 0; }
            100% { background-position: -200% 0; }
        }
    </style>
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
                            <img src="https://i.ytimg.com/vi/dQw4w9WgXcQ/mqdefault.jpg" 
                                 alt="Video 1" 
                                 class="img-fluid"
                                 onerror="this.src='https://placehold.co/320x180/e74c3c/ffffff?text=Video+1'">
                            <span class="duration-badge">12:34</span>
                        </div>
                        <div class="video-metadata">
                            <img src="https://yt3.googleusercontent.com/ytc/AIdro_nTv3-_qWX7O5H8FWFPAJGsQvEYA3cZ_-g5NW9YVg=s88-c-k-c0x00ffffff-no-rj" 
                                 alt="Channel" 
                                 class="channel-avatar"
                                 onerror="this.src='https://placehold.co/40x40/3498db/ffffff?text=CH'">
                            <div class="video-info">
                                <h6 class="video-title">Tên video dài có thể tự động wrap sang dòng tiếp theo</h6>
                                <p class="channel-name">Tên Kênh</p>
                                <p class="video-stats">1.2M lượt xem • 2 ngày trước</p>
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

                    <!-- Video Card 2 -->
                    <div class="video-container">
                        <div class="video-thumbnail">
                            <img src="https://i.ytimg.com/vi/jNQXAC9IVRw/mqdefault.jpg" 
                                 alt="Video 2" 
                                 class="img-fluid"
                                 onerror="this.src='https://placehold.co/320x180/3498db/ffffff?text=Video+2'">
                            <span class="duration-badge">8:45</span>
                        </div>
                        <div class="video-metadata">
                            <img src="https://yt3.googleusercontent.com/ytc/AIdro_nTv3-_qWX7O5H8FWFPAJGsQvEYA3cZ_-g5NW9YVg=s88-c-k-c0x00ffffff-no-rj" 
                                 alt="Channel" 
                                 class="channel-avatar"
                                 onerror="this.src='https://placehold.co/40x40/9b59b6/ffffff?text=AB'">
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
                            <img src="https://i.ytimg.com/vi/9bZkp7q19f0/mqdefault.jpg" 
                                 alt="Video 3" 
                                 class="img-fluid"
                                 onerror="this.src='https://placehold.co/320x180/2ecc71/ffffff?text=Video+3'">
                            <span class="duration-badge">15:20</span>
                        </div>
                        <div class="video-metadata">
                            <img src="https://yt3.googleusercontent.com/ytc/AIdro_nTv3-_qWX7O5H8FWFPAJGsQvEYA3cZ_-g5NW9YVg=s88-c-k-c0x00ffffff-no-rj" 
                                 alt="Channel" 
                                 class="channel-avatar"
                                 onerror="this.src='https://placehold.co/40x40/e67e22/ffffff?text=XY'">
                            <div class="video-info">
                                <h6 class="video-title">Video thứ ba - tiêu đề video thứ ba</h6>
                                <p class="channel-name">Channel XYZ</p>
                                <p class="video-stats">3.1M lượt xem • 3 tuần trước</p>
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

                    <!-- Video Card 4 -->
                    <div class="video-container">
                        <div class="video-thumbnail">
                            <img src="https://i.ytimg.com/vi/M7lc1UVf-VE/mqdefault.jpg" 
                                 alt="Video 4" 
                                 class="img-fluid"
                                 onerror="this.src='https://placehold.co/320x180/f39c12/ffffff?text=Video+4'">
                            <span class="duration-badge">9:12</span>
                        </div>
                        <div class="video-metadata">
                            <img src="https://yt3.googleusercontent.com/ytc/AIdro_nTv3-_qWX7O5H8FWFPAJGsQvEYA3cZ_-g5NW9YVg=s88-c-k-c0x00ffffff-no-rj" 
                                 alt="Channel" 
                                 class="channel-avatar"
                                 onerror="this.src='https://placehold.co/40x40/1abc9c/ffffff?text=12'">
                            <div class="video-info">
                                <h6 class="video-title">Video thứ tư - content mới</h6>
                                <p class="channel-name">Channel 123</p>
                                <p class="video-stats">890K lượt xem • 5 ngày trước</p>
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

                    <!-- Video Card 5 -->
                    <div class="video-container">
                        <div class="video-thumbnail">
                            <img src="https://i.ytimg.com/vi/kJQP7kiw5Fk/mqdefault.jpg" 
                                 alt="Video 5" 
                                 class="img-fluid"
                                 onerror="this.src='https://placehold.co/320x180/e74c3c/ffffff?text=Video+5'">
                            <span class="duration-badge">20:15</span>
                        </div>
                        <div class="video-metadata">
                            <img src="https://yt3.googleusercontent.com/ytc/AIdro_nTv3-_qWX7O5H8FWFPAJGsQvEYA3cZ_-g5NW9YVg=s88-c-k-c0x00ffffff-no-rj" 
                                 alt="Channel" 
                                 class="channel-avatar"
                                 onerror="this.src='https://placehold.co/40x40/c0392b/ffffff?text=45'">
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
                            <img src="https://i.ytimg.com/vi/LXb3EKWsInQ/mqdefault.jpg" 
                                 alt="Video 6" 
                                 class="img-fluid"
                                 onerror="this.src='https://placehold.co/320x180/27ae60/ffffff?text=Video+6'">
                            <span class="duration-badge">11:30</span>
                        </div>
                        <div class="video-metadata">
                            <img src="https://yt3.googleusercontent.com/ytc/AIdro_nTv3-_qWX7O5H8FWFPAJGsQvEYA3cZ_-g5NW9YVg=s88-c-k-c0x00ffffff-no-rj" 
                                 alt="Channel" 
                                 class="channel-avatar"
                                 onerror="this.src='https://placehold.co/40x40/16a085/ffffff?text=78'">
                            <div class="video-info">
                                <h6 class="video-title">Video cuối cùng trong danh sách yêu thích</h6>
                                <p class="channel-name">Channel 789</p>
                                <p class="video-stats">4.3M lượt xem • 10 ngày trước</p>
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
                </div>
            </div>
        </main>

    <!-- Footer -->
    <jsp:include page="../components/footer.jsp" />
    
    <!-- Modals -->
    <jsp:include page="../fragments/toast.jsp"/>
    <jsp:include page="../fragments/loading-spinner.jsp"/>
    <jsp:include page="../fragments/share-modal.jsp"/>
    <jsp:include page="../fragments/auth-modals.jsp"/>
    <jsp:include page="../auth/account-settings.jsp"/>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Common JS Functions -->
    <script src="${pageContext.request.contextPath}/static/js/common.js"></script>
    <script>
        // Like button toggle
        document.querySelectorAll('.btn-action').forEach(btn => {
            if (btn.innerHTML.includes('fa-heart')) {
                btn.addEventListener('click', function() {
                    this.classList.toggle('liked');
                    
                    if (this.classList.contains('liked')) {
                        Toast.success('Đã thích video!');
                    } else {
                        Toast.info('Đã bỏ thích video');
                    }
                });
            }
        });
        
        // Share button
        document.querySelectorAll('.video-actions .btn-action:nth-child(2)').forEach(btn => {
            btn.addEventListener('click', function() {
                const videoTitle = this.closest('.video-container').querySelector('.video-title').textContent;
                Video.share('video123', videoTitle);
            });
        });
    </script>
</body>
</html>

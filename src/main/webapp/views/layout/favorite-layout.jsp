<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Video đã thích - YouTube</title>
    
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/static/css/favorites.css" rel="stylesheet">
</head>
<body>
<!-- Sidebar -->
<aside class="sidebar">
    <ul class="list-group">
        <li class="list-group-item active">
            <a href="#" class="nav-link">
                <i class="fas fa-home"></i> Trang chủ
            </a>
        </li>
        <li class="list-group-item">
            <a href="#" class="nav-link">
                <i class="fas fa-compass"></i> Khám phá
            </a>
        </li>
        <li class="list-group-item">
            <a href="#" class="nav-link">
                <i class="fas fa-play-circle"></i> Shorts
            </a>
        </li>
        <li class="list-group-item">
            <a href="#" class="nav-link">
                <i class="fas fa-subscription"></i> Kênh đăng ký
            </a>
        </li>
        <li class="list-group-item">
            <a href="#" class="nav-link active">
                <i class="fas fa-heart"></i> Video đã thích
            </a>
        </li>
    </ul>
</aside>

<!-- Main Content -->
<main class="content">
    <div class="favorite-container">
        <div class="favorite-header">
            <h1 class="favorite-title">Video đã thích</h1>
            <div class="dropdown">
                <button class="btn btn-outline-secondary btn-sm dropdown-toggle" type="button" id="sortDropdown" data-bs-toggle="dropdown">
                    Sắp xếp theo: Mới nhất
                </button>
                <ul class="dropdown-menu" aria-labelledby="sortDropdown">
                    <li><a class="dropdown-item" href="#">Mới nhất</a></li>
                    <li><a class="dropdown-item" href="#">Cũ nhất</a></li>
                    <li><a class="dropdown-item" href="#">Xem nhiều nhất</a></li>
                </ul>
            </div>
        </div>

    <c:choose>
        <c:when test="${empty videos}">
            <div class="empty-state">
                <i class="far fa-folder-open" style="font-size: 64px; margin-bottom: 16px; color: #606060;"></i>
                <h3>Chưa có video nào trong mục yêu thích</h3>
                <p>Nhấn nút THÍCH <i class="fas fa-thumbs-up"></i> để lưu video vào đây</p>
            </div>
        </c:when>
        <c:otherwise>
            <ul class="video-grid">
                <c:forEach items="${videos}" var="video">
                    <li class="video-card">
                        <a href="${pageContext.request.contextPath}/watch?v=${video.id}" class="video-link">
                            <div class="thumbnail-container">
                                <img src="${video.thumbnailUrl}" alt="${video.title}" class="thumbnail" onerror="this.src='https://via.placeholder.com/300x169?text=No+Thumbnail'">
                                <span class="video-duration">${video.duration}</span>
                            </div>
                            <div class="video-info">
                                <div class="channel-avatar">
                                    <img src="${video.channelAvatar}" alt="${video.channelName}" onerror="this.style.display='none'" style="width: 100%; height: 100%; border-radius: 50%; object-fit: cover;">
                                </div>
                                <div class="video-details">
                                    <h3 class="video-title">${video.title}</h3>
                                    <div class="video-meta">
                                        <a href="${pageContext.request.contextPath}/channel/${video.channelId}">${video.channelName}</a>
                                        <div>${video.views} lượt xem • ${video.uploadTime}</div>
                                    </div>
                                </div>
                            </div>
                        </a>
                        <div class="video-actions">
                            <button class="action-btn like-btn ${video.liked ? 'liked' : ''}" data-video-id="${video.id}">
                                <i class="fas fa-thumbs-up"></i>
                                <span>${video.likeCount}</span>
                            </button>
                            <button class="action-btn share-btn" data-video-id="${video.id}">
                                <i class="fas fa-share"></i>
                                <span>Chia sẻ</span>
                            </button>
                            <div class="dropdown">
                                <button class="action-btn more-btn" type="button" data-bs-toggle="dropdown">
                                    <i class="fas fa-ellipsis-h"></i>
                                </button>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="#">Lưu vào danh sách phát</a></li>
                                    <li><a class="dropdown-item" href="#">Báo cáo</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>
        </div>
    </div>
</main>

<!-- Bootstrap JS and dependencies -->
<script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>

<!-- Custom JS -->
<script>
    // Xử lý sự kiện like video
    document.querySelectorAll('.like-btn').forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const videoId = this.dataset.videoId;
            const icon = this.querySelector('i');
            const countSpan = this.querySelector('span');
            const isLiked = this.classList.contains('liked');
            
            // Gọi API để like/unlike video
            fetch(`/api/videos/${videoId}/like`, {
                method: isLiked ? 'DELETE' : 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    this.classList.toggle('liked');
                    if (countSpan) countSpan.textContent = data.likeCount;
                }
            });
        });
    });

    // Xử lý sự kiện chia sẻ video
    document.querySelectorAll('.share-btn').forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const videoId = this.dataset.videoId;
            // Hiển thị modal hoặc thông báo chia sẻ
            alert(`Chia sẻ video ${videoId}`);
        });
    });

    // Thêm class active khi cuộn trang
    window.addEventListener('scroll', function() {
        const header = document.querySelector('.favorite-header');
        if (window.scrollY > 50) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    });
</script>
</body>
</html>
                <li class="list-group-item active">
                    <a href="#" class="nav-link">
                        <i class="fas fa-heart"></i> Video đã thích
                    </a>
                </li>
                <li class="list-group-item">
                    <a href="#" class="nav-link">
                        <i class="fas fa-history"></i> Đã xem gần đây
                    </a>
                </li>
                <li class="list-group-item">
                    <a href="#" class="nav-link">
                        <i class="fas fa-clock"></i> Xem sau
                    </a>
                </li>
            </ul>
        </aside>
        
        <!-- Main Content -->
        <main class="content">
            <div class="favorite-header">
                <h1 class="favorite-title">Video đã thích</h1>
                <div class="dropdown">
                    <button class="btn btn-outline-secondary btn-sm dropdown-toggle" type="button" id="sortDropdown" data-bs-toggle="dropdown">
                        Sắp xếp theo: Mới nhất
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="sortDropdown">
                        <li><a class="dropdown-item" href="#">Mới nhất</a></li>
                        <li><a class="dropdown-item" href="#">Cũ nhất</a></li>
                        <li><a class="dropdown-item" href="#">Thời lượng</a></li>
                    </ul>
                </div>
            </div>
            
            <div class="video-grid">
                <!-- Video Card 1 -->
                <div class="video-card">
                    <div class="thumbnail-container">
                        <img src="https://via.placeholder.com/300x169" alt="Video thumbnail" class="thumbnail">
                        <span class="video-duration">10:30</span>
                    </div>
                    <div class="video-info">
                        <h3 class="video-title">Tên video dài có thể hiển thị trên 2 dòng nếu quá dài</h3>
                        <div class="video-meta">
                            <span>Kênh ABC</span>
                            <span>1.2K lượt xem • 2 ngày trước</span>
                        </div>
                    </div>
                    <div class="video-actions">
                        <button class="action-btn like-btn liked" data-video-id="1">
                            <i class="fas fa-heart"></i>
                            <span>Đã thích</span>
                        </button>
                        <button class="action-btn share-btn" data-video-id="1">
                            <i class="fas fa-share"></i>
                            <span>Chia sẻ</span>
                        </button>
                    </div>
                </div>
                
                <!-- Video Card 2 -->
                <div class="video-card">
                    <div class="thumbnail-container">
                        <img src="https://via.placeholder.com/300x169" alt="Video thumbnail" class="thumbnail">
                        <span class="video-duration">5:45</span>
                    </div>
                    <div class="video-info">
                        <h3 class="video-title">Video ngắn hơn</h3>
                        <div class="video-meta">
                            <span>Kênh XYZ</span>
                            <span>5.7K lượt xem • 1 tuần trước</span>
                        </div>
                    </div>
                    <div class="video-actions">
                        <button class="action-btn like-btn liked" data-video-id="2">
                            <i class="fas fa-heart"></i>
                            <span>Đã thích</span>
                        </button>
                        <button class="action-btn share-btn" data-video-id="2">
                            <i class="fas fa-share"></i>
                            <span>Chia sẻ</span>
                        </button>
                    </div>
                </div>
                
                <!-- Video Card 3 -->
                <div class="video-card">
                    <div class="thumbnail-container">
                        <img src="https://via.placeholder.com/300x169" alt="Video thumbnail" class="thumbnail">
                        <span class="video-duration">15:22</span>
                    </div>
                    <div class="video-info">
                        <h3 class="video-title">Một video khác với tiêu đề dài hơn một chút để kiểm tra giao diện</h3>
                        <div class="video-meta">
                            <span>Kênh 123</span>
                            <span>3.1K lượt xem • 3 tuần trước</span>
                        </div>
                    </div>
                    <div class="video-actions">
                        <button class="action-btn like-btn liked" data-video-id="3">
                            <i class="fas fa-heart"></i>
                            <span>Đã thích</span>
                        </button>
                        <button class="action-btn share-btn" data-video-id="3">
                            <i class="fas fa-share"></i>
                            <span>Chia sẻ</span>
                        </button>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <!-- Bootstrap JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
    
    <!-- Custom JS -->
    <script>
        // Toggle like button
        document.querySelectorAll('.like-btn').forEach(button => {
            button.addEventListener('click', function(e) {
                e.preventDefault();
                const videoId = this.getAttribute('data-video-id');
                const icon = this.querySelector('i');
                const text = this.querySelector('span');
                
                if (this.classList.contains('liked')) {
                    this.classList.remove('liked');
                    icon.className = 'far fa-heart';
                    text.textContent = 'Thích';
                    console.log(`Đã bỏ thích video ${videoId}`);
                } else {
                    this.classList.add('liked');
                    icon.className = 'fas fa-heart';
                    text.textContent = 'Đã thích';
                    console.log(`Đã thích video ${videoId}`);
                }
            });
        });
        
        // Share button functionality
        document.querySelectorAll('.share-btn').forEach(button => {
            button.addEventListener('click', function(e) {
                e.preventDefault();
                const videoId = this.getAttribute('data-video-id');
                console.log(`Chia sẻ video ${videoId}`);
                alert('Chức năng chia sẻ đang được phát triển');
            });
        });
        
        // Search functionality
        const searchForm = document.querySelector('form');
        if (searchForm) {
            searchForm.addEventListener('submit', function(e) {
                e.preventDefault();
                const searchTerm = this.querySelector('input[type="search"]').value.trim();
                if (searchTerm) {
                    console.log('Đang tìm kiếm:', searchTerm);
                    // Thêm logic tìm kiếm ở đây
                }
            });
        }
    </script>
</body>
</html>

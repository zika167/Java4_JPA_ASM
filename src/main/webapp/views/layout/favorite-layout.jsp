<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Video đã thích</title>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/static/css/favorites.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/static/css/favorites.css" rel="stylesheet">
</head>
<body>
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
    </div>

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- Bootstrap JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
    
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
    </script>
</body>
</html>

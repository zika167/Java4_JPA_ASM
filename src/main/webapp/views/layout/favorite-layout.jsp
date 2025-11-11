<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Video đã thích - My App</title>
  <!-- Bootstrap CSS -->
  <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
  <!-- Font Awesome for icons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
  <!-- Custom CSS -->
  <link href="${pageContext.request.contextPath}/static/css/favorites.css" rel="stylesheet">
</head>
<body>
  <!-- ... -->
</body>
</html>

    .sidebar {
      width: 240px;
      padding-right: 20px;
    }

    .content {
      flex: 1;
      padding: 0 20px;
    }

    .favorite-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
      padding-bottom: 10px;
      border-bottom: 1px solid var(--border-color);
    }

    .favorite-title {
      font-size: 24px;
      font-weight: 500;
      margin: 0;
    }

    .video-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
      gap: 20px;
      margin-top: 20px;
    }

    .video-card {
      background: white;
      border-radius: 8px;
      overflow: hidden;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      transition: transform 0.2s, box-shadow 0.2s;
    }

    .video-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    }

    .thumbnail-container {
      position: relative;
      padding-top: 56.25%; /* 16:9 Aspect Ratio */
      background-color: #000;
    }

    .thumbnail {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    .video-duration {
      position: absolute;
      bottom: 8px;
      right: 8px;
      background: rgba(0, 0, 0, 0.8);
      color: white;
      padding: 2px 6px;
      border-radius: 4px;
      font-size: 12px;
    }

    .video-info {
      padding: 12px;
    }

    .video-title {
      font-weight: 500;
      margin: 0 0 8px 0;
      font-size: 14px;
      line-height: 1.4;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }

    .video-meta {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 13px;
      color: #606060;
    }

    .video-actions {
      display: flex;
      justify-content: space-between;
      padding: 8px 12px;
      border-top: 1px solid var(--border-color);
    }

    .action-btn {
      background: none;
      border: none;
      color: #606060;
      cursor: pointer;
      font-size: 14px;
      display: flex;
      align-items: center;
      gap: 4px;
      padding: 4px 8px;
      border-radius: 4px;
    }

    .action-btn:hover {
      background-color: rgba(0, 0, 0, 0.05);
    }

    .action-btn.liked {
      color: var(--primary-color);
    }

    .empty-state {
      text-align: center;
      padding: 40px 20px;
      color: #606060;
    }

    .empty-state i {
      font-size: 48px;
      margin-bottom: 16px;
      color: #ccc;
    }

    .empty-state h3 {
      margin-bottom: 8px;
      font-weight: 500;
    }

    .video-channel, .video-views {
      color: #606060;
      font-size: 0.9rem;
      margin-bottom: 4px;
    }

    .video-actions {
      display: flex;
      justify-content: space-between;
      padding: 0 12px 12px;
      border-top: 1px solid var(--border-color);
      margin-top: 8px;
    }

    .action-btn {
      background: none;
      border: none;
      color: #606060;
      cursor: pointer;
      display: flex;
      align-items: center;
      font-size: 0.9rem;
      padding: 4px 8px;
      border-radius: 4px;
    }

    .action-btn:hover {
      background-color: rgba(0, 0, 0, 0.05);
    }

    .action-btn i {
      margin-right: 6px;
    }

    .action-btn.liked {
      color: var(--primary-color);
    }

    .no-favorites {
      text-align: center;
      padding: 40px 20px;
      color: #606060;
    }

    .favorites-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
      padding: 0 15px;
    }

    .favorites-title {
      font-size: 1.5rem;
      font-weight: 500;
      margin: 0;
    }
  </style>
</head>
<body>
<!-- Navigation Bar -->
<nav class="navbar navbar-expand-lg navbar-light">
  <div class="container">
    <a class="navbar-brand" href="#">
      <h1 style="color: red; font-weight: bold; font-size: 1.8rem; margin: 0;">YOUTUBE</h1>
    </a>
    <div class="d-flex" style="width: 100%; max-width: 600px;">
      <input class="form-control me-2" type="search" placeholder="Tìm kiếm" aria-label="Search">
      <button class="btn btn-outline-danger" type="submit">
        <i class="fas fa-search"></i>
      </button>
    </div>
    <div class="d-flex align-items-center">
      <a href="#" class="text-dark me-3">
        <i class="fas fa-user-circle" style="font-size: 24px;"></i>
      </a>
    </div>
  </div>
</nav>

<div class="container main-container">
  <!-- Sidebar -->
  <aside class="sidebar d-none d-md-block">
    <ul class="list-group">
      <li class="list-group-item">
        <a href="#" class="text-decoration-none text-dark d-flex align-items-center">
          <i class="fas fa-home me-3"></i> Trang chủ
        </a>
      </li>
      <li class="list-group-item active">
        <a href="#" class="text-decoration-none text-dark d-flex align-items-center">
          <i class="fas fa-heart me-3"></i> Video đã thích
        </a>
      </li>
      <li class="list-group-item">
        <a href="#" class="text-decoration-none text-dark d-flex align-items-center">
          <i class="fas fa-history me-3"></i> Đã xem gần đây
        </a>
      </li>
      <li class="list-group-item">
        <a href="#" class="text-decoration-none text-dark d-flex align-items-center">
          <i class="fas fa-clock me-3"></i> Xem sau
        </a>
      </li>
    </ul>
  </aside>

  <!-- Main Content -->
  <main class="content">
    <div class="favorite-header">
      <h1 class="favorite-title">Video đã thích</h1>
      <div class="dropdown">
        <button class="btn btn-outline-secondary btn-sm dropdown-toggle" type="button" id="sortDropdown" data-bs-toggle="dropdown" aria-expanded="false">
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

    <!-- Empty State (hidden by default) -->
    <!-- <div class="empty-state">
        <i class="far fa-heart"></i>
        <h3>Chưa có video yêu thích</h3>
        <p>Nhấn vào biểu tượng trái tim để lưu video vào danh sách yêu thích</p>
    </div> -->
  </main>
</div>
</li>
<li class="nav-item">
  <a class="nav-link active" href="#">Yêu thích</a>
</li>
<li class="nav-item">
  <a class="nav-link" href="#">Tài khoản</a>
</li>
</ul>
</div>
</nav>
</div>
</header>

<!-- Search Bar -->
<div class="search-bar">
  <div class="container">
    <input type="text" class="search-input" placeholder="Tìm kiếm video yêu thích...">
  </div>
</div>

<!-- Main Content -->
<main class="container favorites-container">
  <div class="favorites-header">
    <h2 class="favorites-title">Video đã thích</h2>
    <div class="sort-options">
      <select class="form-control form-control-sm">
        <option>Sắp xếp theo: Mới nhất</option>
        <option>Cũ nhất</option>
        <option>Xem nhiều nhất</option>
      </select>
    </div>
  </div>

  <div class="row">
    <!-- Favorite Item 1 -->
    <div class="col-md-6 col-lg-4 col-xl-3 mb-4">
      <div class="favorite-item">
        <div class="video-thumbnail">
          <img src="https://via.placeholder.com/300x169" alt="Video thumbnail">
        </div>
        <div class="video-info">
          <h3 class="video-title">Tiêu đề video 1 - Đây là mô tả ngắn về video</h3>
          <p class="video-channel">Tên kênh</p>
          <p class="video-views">100K lượt xem • 2 ngày trước</p>
        </div>
        <div class="video-actions">
          <button class="action-btn liked" onclick="toggleLike(this)">
            <i class="fas fa-heart"></i> Đã thích
          </button>
          <button class="action-btn" onclick="shareVideo()">
            <i class="fas fa-share"></i> Chia sẻ
          </button>
        </div>
      </div>
    </div>

    <!-- Favorite Item 2 -->
    <div class="col-md-6 col-lg-4 col-xl-3 mb-4">
      <div class="favorite-item">
        <div class="video-thumbnail">
          <img src="https://via.placeholder.com/300x169" alt="Video thumbnail">
        </div>
        <div class="video-info">
          <h3 class="video-title">Tiêu đề video 2 - Đây là mô tả ngắn về video</h3>
          <p class="video-channel">Tên kênh</p>
          <p class="video-views">50K lượt xem • 1 tuần trước</p>
        </div>
        <div class="video-actions">
          <button class="action-btn liked" onclick="toggleLike(this)">
            <i class="fas fa-heart"></i> Đã thích
          </button>
          <button class="action-btn" onclick="shareVideo()">
            <i class="fas fa-share"></i> Chia sẻ
          </button>
        </div>
      </div>
    </div>

    <!-- More favorite items can be added here -->

    <!-- No Favorites Message (hidden by default) -->
    <div class="col-12 no-favorites d-none">
      <i class="far fa-heart fa-3x mb-3" style="color: #ddd;"></i>
      <h3>Chưa có video yêu thích</h3>
      <p>Nhấn vào biểu tượng trái tim để lưu video yêu thích</p>
    </div>
  </div>
</main>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- Bootstrap JS and dependencies -->
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
        // Unlike the video
        this.classList.remove('liked');
        icon.className = 'far fa-heart';
        text.textContent = 'Thích';
        console.log(`Removed video ${videoId} from favorites`);

        // Optional: Remove the card from the UI when unliked
        // this.closest('.video-card').style.display = 'none';
      } else {
        // Like the video
        this.classList.add('liked');
        icon.className = 'fas fa-heart';
        text.textContent = 'Đã thích';
        console.log(`Added video ${videoId} to favorites`);
      }

      // Show/hide empty state if needed
      updateEmptyState();
    });
  });

  // Share button functionality
  document.querySelectorAll('.share-btn').forEach(button => {
    button.addEventListener('click', function(e) {
      e.preventDefault();
      const videoId = this.getAttribute('data-video-id');
      // Here you would typically open a share dialog or copy link to clipboard
      console.log(`Sharing video ${videoId}`);
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
        console.log('Searching for:', searchTerm);
        // Here you would typically filter videos based on search term
        // For now, just show an alert
        alert('Đang tìm kiếm: ' + searchTerm);
      }
    });
  }

  // Update empty state visibility
  function updateEmptyState() {
    const videoGrid = document.querySelector('.video-grid');
    const emptyState = document.querySelector('.empty-state');
    const videoCards = document.querySelectorAll('.video-card');

    if (videoCards.length === 0 && emptyState) {
      emptyState.style.display = 'block';
    } else if (emptyState) {
      emptyState.style.display = 'none';
    }
  }
  alert('Chức năng chia sẻ đang được phát triển');
  }

  // Update empty state visibility
  function updateEmptyState() {
    const favoriteItems = document.querySelectorAll('.favorite-item');
    const noFavorites = document.querySelector('.no-favorites');

    // In a real app, you would check if there are any favorite items
    // For now, we'll just hide the noFavorites element since we have sample items
    noFavorites.classList.add('d-none');
  }

  // Search functionality
  document.querySelector('.search-input').addEventListener('input', function(e) {
    const searchTerm = e.target.value.toLowerCase();
    const videoItems = document.querySelectorAll('.favorite-item');

    videoItems.forEach(item => {
      const title = item.querySelector('.video-title').textContent.toLowerCase();
      if (title.includes(searchTerm)) {
        item.closest('.col-md-6').style.display = '';
      } else {
        item.closest('.col-md-6').style.display = 'none';
      }
    });
  });

  // Initialize the page
  document.addEventListener('DOMContentLoaded', function() {
    updateEmptyState();
  });
</script>
</body>
</html>

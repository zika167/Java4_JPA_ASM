<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="collection" value="${not empty videos ? videos : users}" />
<c:set var="countRaw" value="${not empty collection ? collection.size() : 0}" />
<c:set var="remaining" value="${6 - countRaw}" />
<!-- Video Grid Container -->
<div class="video-grid-container">
    <div class="video-grid">
        <c:forEach var="item" items="${collection}" varStatus="st">
          <c:if test="${st.index lt 6}">
            <div class="video-container" data-item-id="${item.id}">
              <!-- Video Thumbnail -->
              <div class="video-thumbnail" onclick="window.location.href='${pageContext.request.contextPath}/details?id=${item.id}'" style="cursor: pointer;">
                <img src="https://i.ytimg.com/vi/dQw4w9WgXcQ/mqdefault.jpg" 
                     alt="<c:out value='${not empty item.title ? item.title : item.name}'/>" 
                     class="img-fluid"
                     onerror="this.src='https://placehold.co/320x180/667eea/ffffff?text=Video'">
                <span class="duration-badge">12:34</span>
              </div>
              
              <!-- Video Metadata -->
              <div class="video-metadata">
                <img src="https://yt3.googleusercontent.com/ytc/AIdro_nTv3-_qWX7O5H8FWFPAJGsQvEYA3cZ_-g5NW9YVg=s88-c-k-c0x00ffffff-no-rj" 
                     alt="Channel" 
                     class="channel-avatar"
                     onerror="this.src='https://placehold.co/40x40/667eea/ffffff?text=CH'">
                <div class="video-info">
                  <h6 class="video-title" onclick="window.location.href='${pageContext.request.contextPath}/details?id=${item.id}'" style="cursor: pointer;">
                    <c:out value="${not empty item.title ? item.title : item.name}"/>
                  </h6>
                  <p class="channel-name">4IN1 Channel</p>
                  <p class="video-stats">1.2M lượt xem • 2 ngày trước</p>
                </div>
              </div>
              
              <!-- Video Actions -->
              <div class="video-actions">
                <button class="btn-action" 
                        data-video-id="${item.id}" 
                        data-video-title="${not empty item.title ? item.title : item.name}"
                        data-action="like"
                        title="Thích">
                  <i class="fas fa-heart"></i>
                </button>
                <button class="btn-action" 
                        data-video-id="${item.id}" 
                        data-video-title="${not empty item.title ? item.title : item.name}"
                        data-action="share"
                        title="Chia sẻ">
                  <i class="fas fa-share"></i>
                </button>
                <div class="dropdown">
                  <button class="btn-action dropdown-toggle" data-bs-toggle="dropdown" title="Thêm">
                    <i class="fas fa-ellipsis-v"></i>
                  </button>
                  <ul class="dropdown-menu dropdown-menu-sm">
                    <li><a class="dropdown-item" href="#"><i class="fas fa-list me-2"></i>Thêm vào danh sách</a></li>
                    <li><a class="dropdown-item" href="#"><i class="fas fa-flag me-2"></i>Báo cáo</a></li>
                  </ul>
                </div>
              </div>
            </div>
          </c:if>
        </c:forEach>

        <!-- Render placeholders to ensure 6 cards visible when data < 6 -->
        <c:if test="${remaining gt 0}">
          <c:forEach var="i" begin="1" end="${remaining}">
            <div class="video-container video-placeholder">
              <div class="video-thumbnail placeholder-thumbnail">
                <span class="placeholder-text">Video</span>
                <span class="duration-badge">0:00</span>
              </div>
              <div class="video-metadata">
                <div class="channel-avatar placeholder-avatar">
                  <span>?</span>
                </div>
                <div class="video-info">
                  <h6 class="video-title">Video Title</h6>
                  <p class="channel-name">Channel Name</p>
                  <p class="video-stats">Loading...</p>
                </div>
              </div>
              <div class="video-actions">
                <button class="btn-action" disabled><i class="fas fa-heart"></i></button>
                <button class="btn-action" disabled><i class="fas fa-share"></i></button>
                <button class="btn-action" disabled><i class="fas fa-ellipsis-v"></i></button>
              </div>
            </div>
          </c:forEach>
        </c:if>
    </div>
    
    <!-- Pagination -->
    <nav aria-label="Page navigation" class="pagination-nav">
        <div class="pagination-container">
          <button class="pagination-btn" data-page="first">&#124;&lt;</button>
          <button class="pagination-btn" data-page="prev">&lt;&lt;</button>
          <button class="pagination-btn" data-page="1">1</button>
          <button class="pagination-btn" data-page="2">2</button>
          <button class="pagination-btn" data-page="3">3</button>
          <button class="pagination-btn" data-page="4">4</button>
          <button class="pagination-btn" data-page="5">5</button>
          <button class="pagination-btn" data-page="next">&gt;&gt;</button>
          <button class="pagination-btn" data-page="last">&gt;&#124;</button>
        </div>
    </nav>
</div>
    
    <script>
      document.addEventListener('DOMContentLoaded', function() {
        // Like buttons
        document.querySelectorAll('[data-action="like"]').forEach(btn => {
          btn.addEventListener('click', function() {
            Video.like(this.dataset.videoId);
          });
        });
        
        // Share buttons
        document.querySelectorAll('[data-action="share"]').forEach(btn => {
          btn.addEventListener('click', function() {
            Video.share(this.dataset.videoId, this.dataset.videoTitle);
          });
        });
        
        // Pagination buttons
        document.querySelectorAll('.pagination-btn').forEach(btn => {
          btn.addEventListener('click', function() {
            const page = this.dataset.page;
            goToPage(page);
          });
        });
      });
    </script>
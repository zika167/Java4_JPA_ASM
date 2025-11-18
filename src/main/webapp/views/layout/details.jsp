<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết - 4in1</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/pages/details.css">
    <style hidden>
      /* Styles moved to details.css */
    </style hidden>
    <c:set var="video" value="${not empty video ? video : item}"/>
    <c:set var="title" value="${not empty video.title ? video.title : video.name}"/>
    <c:set var="desc" value="${not empty video.description ? video.description : video.email}"/>
    <!-- prepare sidebar collection and placeholders -->
    <c:set var="sidebarCollection" value="${not empty viewed ? viewed : related}" />
    <c:set var="sidebarCount" value="${not empty sidebarCollection ? sidebarCollection.size() : 0}" />
    <c:set var="sidebarRemaining" value="${5 - sidebarCount}" />
  </head>
  <body>
    <div class="container py-4">
      <img src="${pageContext.request.contextPath}/static/img/logo.png" alt="logo" class="logo-img" onerror="this.style.display='none'">
      <div class="row">
        <div class="col-md-8 mb-4">
          <div class="card h-100 detail-card">
            <div class="video-player-wrapper">
              <iframe class="video-player" 
                      src="https://www.youtube.com/embed/dQw4w9WgXcQ" 
                      title="Video player" 
                      frameborder="0" 
                      allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" 
                      allowfullscreen>
              </iframe>
            </div>
            <div class="card-body p-4">
              <div class="title-bar"><c:out value="${title}"/></div>
              <div class="mt-2 detail-description-label">DESCRIPTION</div>
              <div class="desc-box"><c:out value="${desc}"/></div>
            </div>
            <div class="card-footer detail-card-footer">
              <div class="d-flex justify-content-end gap-2">
                <button class="btn btn-custom-blue" data-video-id="${video.id}" data-action="like">
                  <i class="fas fa-thumbs-up me-1"></i>Like
                </button>
                <button class="btn btn-custom-share" data-video-id="${video.id}" data-action="share">
                  <i class="fas fa-share me-1"></i>Share</button>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-4">
          <div class="card side-card">
            <div style="padding:12px">
              <c:forEach var="v" items="${sidebarCollection}">
                <div class="side-item">
                  <a href="${pageContext.request.contextPath}/details?id=${v.id}">
                    <img src="https://i.ytimg.com/vi/dQw4w9WgXcQ/default.jpg" 
                         alt="thumbnail" 
                         class="side-poster"
                         onerror="this.outerHTML='<div class=\'side-poster\'>POSTER</div>'">
                    <span><c:out value="${not empty v.title ? v.title : v.name}"/></span>
                  </a>
                </div>
              </c:forEach>

              <!-- Render placeholders up to 5 items -->
              <c:if test="${sidebarRemaining gt 0}">
                <c:forEach var="i" begin="1" end="${sidebarRemaining}">
                  <div class="side-item">
                    <div class="d-flex gap-2 align-items-center w-100">
                      <div class="side-poster">POSTER</div>
                      <span class="text-muted-custom">Video Title</span>
                    </div>
                  </div>
                </c:forEach>
              </c:if>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
  
  <!-- Include JSP Fragments -->
  <jsp:include page="../fragments/toast.jsp"/>
  <jsp:include page="../fragments/loading-spinner.jsp"/>
  <jsp:include page="../fragments/share-modal.jsp"/>
  
  <!-- Scripts -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}/static/js/common.js"></script>
  <script>
    // Attach event listeners using common.js functions
    document.addEventListener('DOMContentLoaded', function() {
      // Like button
      const likeBtn = document.querySelector('[data-action="like"]');
      if (likeBtn) {
        likeBtn.addEventListener('click', function() {
          const videoId = this.getAttribute('data-video-id');
          Video.like(videoId);
        });
      }
      
      // Share button
      const shareBtn = document.querySelector('[data-action="share"]');
      if (shareBtn) {
        shareBtn.addEventListener('click', function() {
          const videoId = this.getAttribute('data-video-id');
          Video.share(videoId, '${title}');
        });
      }
    });
  </script>
 </html>
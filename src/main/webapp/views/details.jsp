<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <style>
      body { background: #fafafa; }
      .logo-img { height:28px; margin-bottom:12px; }
      /* video placeholder: solid continuous yellow border, no bottom border */
      .video-placeholder {
        height: 300px;
        background: #fff;
        display: flex;
        align-items: center;
        justify-content: center;
        color: #333;
        font-weight: 700;
        font-size: 16px;
        border-top-left-radius: 6px;
        border-top-right-radius: 6px;
      }
      .detail-card {
        border: 2px solid #ffb300 !important;
        border-radius: 8px;
        box-shadow: 0 2px 6px rgba(0,0,0,.04);
        overflow: hidden;
      }
      .title-bar {
        background: #e9f5e7;
        border-left: 6px solid #99c27c;
        padding: 12px 10px;
        border-radius: 4px;
        color: #2e5a2a;
        font-weight: 700;
        margin: 0 0 12px 0;
      }
      .desc-box {
        background: #f5f5f5;
        padding: 12px;
        border-radius: 6px;
        color: #444;
        min-height: 80px;
        line-height: 1.5;
      }
      .side-card {
        border: 2px solid #ffb300 !important;
        border-radius: 8px;
        box-shadow: 0 2px 6px rgba(0,0,0,.04);
      }
      .side-item {
        display: flex;
        gap: 10px;
        align-items: center;
        padding: 12px;
        border-bottom: 1px solid #e9f5e7;
      }
      .side-item:last-child {
        border-bottom: none;
      }
      .side-item a {
        text-decoration: none;
        color: #2e5a2a;
        font-weight: 600;
        display: flex;
        gap: 10px;
        align-items: center;
        width: 100%;
      }
      .side-item a:hover {
        color: #99c27c;
      }
      .side-poster {
        width: 64px;
        height: 44px;
        border: 2px solid #ffb300;
        border-radius: 4px;
        display: flex;
        align-items: center;
        justify-content: center;
        color: #8aa07c;
        background: #fff;
        font-size: 12px;
        flex-shrink: 0;
      }
      /* style each sidebar entry with light green box like mockup */
      .side-card .side-item { border: 1px solid #cfead6; margin: 8px; border-radius:6px; }
      .side-card .side-item a { text-decoration: underline; text-underline-offset: 3px; }
      .btn-custom-like { 
        background: #2563eb; 
        border: none; 
        color: #fff;
        padding: 8px 16px;
        font-weight: 600;
      }
      .btn-custom-like:hover { 
        background: #1d4ed8; 
        color: #fff;
      }
      .btn-custom-share { 
        background: #f69353; 
        border: none; 
        color: #fff;
        padding: 8px 16px;
        font-weight: 600;
      }
      .btn-custom-share:hover { 
        background: #e67a38; 
        color: #fff;
      }
    </style>
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
            <div class="video-placeholder">VIDEO</div>
            <div class="card-body p-4">
              <div class="title-bar"><c:out value="${title}"/></div>
              <div class="mt-2" style="font-weight:700;color:#222;">DESCRIPTION</div>
              <div class="desc-box"><c:out value="${desc}"/></div>
            </div>
            <div class="card-footer bg-white border-0 p-3">
              <div class="d-flex justify-content-end gap-2">
                <button class="btn btn-custom-like" onclick="console.log('like', '${video.id}')">Like</button>
                <button class="btn btn-custom-share" onclick="console.log('share', '${video.id}')">Share</button>
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
                    <div class="side-poster">POSTER</div>
                    <span><c:out value="${not empty v.title ? v.title : v.name}"/></span>
                  </a>
                </div>
              </c:forEach>

              <!-- Render placeholders up to 5 items -->
              <c:if test="${sidebarRemaining gt 0}">
                <c:forEach var="i" begin="1" end="${sidebarRemaining}">
                  <div class="side-item">
                    <div style="display:flex; gap:10px; align-items:center; width:100%">
                      <div class="side-poster">POSTER</div>
                      <span style="color:#6b6b6b">Video Title</span>
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
  <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}/static/js/actions.js"></script>
 </html>
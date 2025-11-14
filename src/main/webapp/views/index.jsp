<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang chủ</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <style>
      body { background: #fafafa; }
      .logo-img { height:28px; margin-bottom:12px; }
      .poster-placeholder { height:140px; border: 2px dashed #e6b389; background:#fff; display:flex;align-items:center;justify-content:center; }
      .poster-placeholder .inner-frame{ width:84%; height:66%; border:2px solid #f3d5c4; display:flex;align-items:center;justify-content:center; color:#333; font-weight:700; font-size:13px; border-radius:4px }
      .custom-card {
        border: 2px solid #f0c9a5 !important;
        border-radius: 8px;
        box-shadow: 0 2px 6px rgba(0,0,0,.04);
      }
      .custom-card-title {
        background: #e9f5e7;
        border-left: 4px solid #99c27c;
        padding: 10px;
        border-radius: 4px;
        color: #2e5a2a;
        font-weight: 600;
        margin: 0;
        min-height: 22px;
      }
      .btn-custom-like { background: #74c365; border: none; color: #fff; }
      .btn-custom-like:hover { background: #5fa04d; color: #fff; }
      .btn-custom-share { background: #f69353; border: none; color: #fff; }
      .btn-custom-share:hover { background: #e67a38; color: #fff; }
      .pagination-btn { 
        width: 36px; 
        height: 28px; 
        border: none; 
        border-radius: 6px; 
        background: #ddd; 
        color: #333; 
        cursor: pointer;
        margin: 0 3px;
      }
      .pagination-btn.active { background: #9ab0d1; color: #fff; }
    </style>
    <script>
      function handlePosterClick(id){ window.location='${pageContext.request.contextPath}/details?id='+id; }
      function handleActionClick(type,id){ if(type==='like'){console.log('like',id);} if(type==='share'){console.log('share',id);} }
      function goToPage(p){ console.log('goToPage',p); }
    </script>
    <c:set var="collection" value="${not empty videos ? videos : users}" />
    <c:set var="countRaw" value="${not empty collection ? collection.size() : 0}" />
    <c:set var="remaining" value="${6 - countRaw}" />
  </head>
  <body>
    <div class="container py-4">
      <img src="${pageContext.request.contextPath}/static/img/logo.png" alt="logo" class="logo-img" onerror="this.style.display='none'">
      <h1 class="h4 mb-3">Các tiêu phẩm nổi bật</h1>

      <div class="row">
        <c:forEach var="item" items="${collection}" varStatus="st">
          <c:if test="${st.index lt 6}">
            <div class="col-lg-4 col-md-6 mb-4" data-item-id="${item.id}">
              <div class="card h-100 custom-card">
                <a href="${pageContext.request.contextPath}/details?id=${item.id}" class="text-decoration-none text-body">
                  <div class="d-flex align-items-center justify-content-center bg-light poster-placeholder"><div class="inner-frame">POSTER</div></div>
                  <div class="card-body p-3">
                    <h5 class="custom-card-title"><c:out value="${not empty item.title ? item.title : item.name}"/></h5>
                  </div>
                </a>
                <div class="card-footer bg-white border-0 d-flex gap-2 p-3">
                  <button class="btn btn-custom-like flex-fill" onclick="handleActionClick('like', '${item.id}')">Like</button>
                  <button class="btn btn-custom-share flex-fill" onclick="handleActionClick('share', '${item.id}')">Share</button>
                </div>
              </div>
            </div>
          </c:if>
        </c:forEach>

        <!-- Render placeholders to ensure 6 cards visible when data < 6 -->
        <c:if test="${remaining gt 0}">
          <c:forEach var="i" begin="1" end="${remaining}">
            <div class="col-lg-4 col-md-6 mb-4">
              <div class="card h-100 custom-card">
                <div class="d-flex align-items-center justify-content-center bg-light poster-placeholder"><div class="inner-frame">POSTER</div></div>
                <div class="card-body p-3">
                  <h5 class="custom-card-title">Video Title</h5>
                </div>
                <div class="card-footer bg-white border-0 d-flex gap-2 p-3">
                  <button class="btn btn-custom-like flex-fill" disabled>Like</button>
                  <button class="btn btn-custom-share flex-fill" disabled>Share</button>
                </div>
              </div>
            </div>
          </c:forEach>
        </c:if>
      </div>

      <nav aria-label="Page navigation" class="mt-4">
        <div class="d-flex gap-2 justify-content-center align-items-center">
          <button class="pagination-btn" onclick="goToPage(1)">&#124;&lt;</button>
          <button class="pagination-btn" onclick="goToPage('prev')">&lt;&lt;</button>
          <button class="pagination-btn active" onclick="goToPage('current')">&lt;&gt;</button>
          <button class="pagination-btn" onclick="goToPage('next')">&gt;&gt;</button>
          <button class="pagination-btn" onclick="goToPage('last')">&gt;&#124;</button>
        </div>
      </nav>
    </div>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/actions.js"></script>
  </body>
 </html>
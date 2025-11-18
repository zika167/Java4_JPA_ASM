<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Video Management - Admin</title>
    <!-- Bootstrap 5.3.3 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin/management-common.css">
    <!-- Bootstrap 5.3.3 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Common JS -->
    <script src="${pageContext.request.contextPath}/static/js/common.js"></script>
    <!-- Custom JS -->
    <script src="${pageContext.request.contextPath}/static/js/video-management.js"></script>
</head>
<body>
    <!-- Include Admin Navbar -->
    <jsp:include page="/views/admin/administration-navbar.jsp">
        <jsp:param name="page" value="videos"/>
    </jsp:include>

    <div class="container-fluid py-4">
        <!-- Tabs -->
        <ul class="nav nav-tabs video-tabs" id="videoTabs" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="edition-tab" data-bs-toggle="tab" data-bs-target="#edition"
                        type="button" role="tab" aria-controls="edition" aria-selected="false">
                    VIDEO EDITION
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="list-tab" data-bs-toggle="tab" data-bs-target="#list"
                        type="button" role="tab" aria-controls="list" aria-selected="true">
                    VIDEO LIST
                </button>
            </li>
        </ul>

     <!-- Tab Content -->
     <div class="tab-content" id="videoTabContent">
            <!-- Video Edition Tab -->
            <div class="tab-pane fade" id="edition" role="tabpanel" aria-labelledby="edition-tab">
                <div class="tab-content-box">
                    <form id="videoForm" method="post" action="${pageContext.request.contextPath}/admin/videos/save">
                        <div class="row">
                            <!-- Left Column - Poster -->
                            <div class="col-md-4">
                                <div class="poster-preview" id="posterPreview">
                                    <img id="posterImage" src="" alt="Poster" style="display: none;">
                                    <div id="posterPlaceholder" class="poster-placeholder">
                                        <span>POSTER</span>
                                    </div>
                                </div>
                                <input type="file" class="d-none" id="posterFile" accept="image/*">
                                <input type="hidden" name="poster" id="posterInput">
                            </div>

                            <!-- Right Column - Form Fields -->
                            <div class="col-md-8">
                                <div class="mb-3">
                                    <label class="form-label">YOUTUBE ID?</label>
                                    <input type="text" class="form-control" name="id">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">VIDEO TITLE?</label>
                                    <input type="text" class="form-control" name="title">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">VIEW COUNT?</label>
                                    <input type="number" class="form-control" name="views" value="0">
                                </div>

                                <div class="mb-3">
                                    <div class="status-group">
                                        <label class="form-check-label">
                                            <input class="form-check-input" type="radio" name="active" value="true" checked>
                                            <span class="status-icon">☐</span> ACTIVE
                                        </label>
                                        <label class="form-check-label ms-4">
                                            <input class="form-check-input" type="radio" name="active" value="false">
                                            <span class="status-icon">☐</span> INACTIVE
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Description - Full Width -->
                        <div class="row mt-3">
                            <div class="col-12">
                                <label class="form-label">DESCRIPTION?</label>
                                <textarea class="form-control" name="description" rows="5"></textarea>
                            </div>
                        </div>

                        <!-- Action Buttons -->
                        <div class="action-buttons">
                            <button type="submit" name="action" value="create" class="btn-action">Create</button>
                            <button type="submit" name="action" value="update" class="btn-action">Update</button>
                            <button type="submit" name="action" value="delete" class="btn-action btn-delete">Delete</button>
                            <button type="reset" class="btn-action">Reset</button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Video List Tab -->
            <div class="tab-pane fade show active" id="list" role="tabpanel" aria-labelledby="list-tab">
                <div class="tab-content-box">
                    <!-- Video Table -->
                    <table class="video-table">
                        <thead>
                            <tr>
                                <th>Youtube Id</th>
                                <th>Video Title</th>
                                <th>View Count</th>
                                <th>Status</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="video" items="${videos}">
                                <tr>
                                    <td>${video.id}</td>
                                    <td>${video.title}</td>
                                    <td>${video.views}</td>
                                    <td>${video.active ? 'Active' : 'Inactive'}</td>
                                    <td>
                                        <a href="#" class="link-edit" onclick="editVideo('${video.id}'); return false;">Edit</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Pagination -->
                    <div class="pagination-container">
                        <div class="video-count">
                            <c:choose>
                                <c:when test="${not empty totalVideos}">
                                    ${totalVideos} videos
                                </c:when>
                                <c:otherwise>
                                    0 videos
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="pagination-buttons">
                            <button class="btn-page" ${currentPage == 1 ? 'disabled' : ''}>|&lt;</button>
                            <button class="btn-page" ${currentPage == 1 ? 'disabled' : ''}>&lt;&lt;</button>
                            <button class="btn-page" ${currentPage == 1 ? 'disabled' : ''}>&gt;&gt;</button>
                            <button class="btn-page" ${currentPage == 1 ? 'disabled' : ''}>&gt;|</button>
                        </div>
                    </div>
                </div>
            </div>
     </div>
 </div>
</body>
</html>

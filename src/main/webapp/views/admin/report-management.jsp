<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="auto">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Statistical Report</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/core/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin/management-common.css">
</head>
<body>
    <!-- Include Admin Navbar -->
    <jsp:include page="/views/admin/administration-navbar.jsp">
        <jsp:param name="page" value="reports"/>
    </jsp:include>

    <div class="container-fluid py-4">
        <ul class="nav nav-tabs report-tabs" id="reportTabs" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="favorites-tab" data-bs-toggle="tab" data-bs-target="#favorites" type="button" role="tab" aria-controls="favorites" aria-selected="true">FAVORITES</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="favorite-users-tab" data-bs-toggle="tab" data-bs-target="#favorite-users" type="button" role="tab" aria-controls="favorite-users" aria-selected="false">FAVORITE USERS</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="shared-friends-tab" data-bs-toggle="tab" data-bs-target="#shared-friends" type="button" role="tab" aria-controls="shared-friends" aria-selected="false">SHARED FRIENDS</button>
            </li>
        </ul>

        <div class="tab-content" id="reportTabsContent">
            <div class="tab-pane fade show active" id="favorites" role="tabpanel" aria-labelledby="favorites-tab">
                <div class="tab-content-box">
                    <jsp:include page="../components/favorites-report.jsp" />
                </div>
            </div>
            <div class="tab-pane fade" id="favorite-users" role="tabpanel" aria-labelledby="favorite-users-tab">
                <div class="tab-content-box">
                    <jsp:include page="../components/favorite-users-report.jsp" />
                </div>
            </div>
            <div class="tab-pane fade" id="shared-friends" role="tabpanel" aria-labelledby="shared-friends-tab">
                <div class="tab-content-box">
                    <jsp:include page="../components/shared-friends-report.jsp" />
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/common.js"></script>
</body>
</html>

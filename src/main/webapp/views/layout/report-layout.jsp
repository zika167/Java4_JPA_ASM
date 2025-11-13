<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="auto">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Statistical Report</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-body-tertiary">
    <div class="container-fluid py-5">
        <div class="container bg-body p-4 rounded shadow-lg">
            <h3 class="mb-4 text-center text-primary fw-bold">STATISTICAL REPORT</h3>

            <ul class="nav nav-pills nav-fill mb-4 bg-secondary rounded shadow-sm p-2" id="reportTabs" role="tablist">
                <li class="nav-item mx-1" role="presentation">
                    <button class="nav-link active text-white bg-primary shadow-sm" id="favorites-tab" data-bs-toggle="tab" data-bs-target="#favorites" type="button" role="tab" aria-controls="favorites" aria-selected="true">FAVORITES</button>
                </li>
                <li class="nav-item mx-1" role="presentation">
                    <button class="nav-link text-white bg-dark shadow-sm" id="favorite-users-tab" data-bs-toggle="tab" data-bs-target="#favorite-users" type="button" role="tab" aria-controls="favorite-users" aria-selected="false">FAVORITE USERS</button>
                </li>
                <li class="nav-item mx-1" role="presentation">
                    <button class="nav-link text-white bg-dark shadow-sm" id="shared-friends-tab" data-bs-toggle="tab" data-bs-target="#shared-friends" type="button" role="tab" aria-controls="shared-friends" aria-selected="false">SHARED FRIENDS</button>
                </li>
            </ul>

            <div class="tab-content bg-body p-4 rounded shadow" id="reportTabsContent">
                <div class="tab-pane fade show active" id="favorites" role="tabpanel" aria-labelledby="favorites-tab">
                    <jsp:include page="../report/favorites-report.jsp" />
                </div>
                <div class="tab-pane fade" id="favorite-users" role="tabpanel" aria-labelledby="favorite-users-tab">
                    <jsp:include page="../report/favorite-users-report.jsp" />
                </div>
                <div class="tab-pane fade" id="shared-friends" role="tabpanel" aria-labelledby="shared-friends-tab">
                    <jsp:include page="../report/shared-friends-report.jsp" />
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

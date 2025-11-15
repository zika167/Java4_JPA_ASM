<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="auto">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Statistical Report</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Report Layout Styles - Giống với video-management và user-management */
        body {
            background-color: #f5f5f5;
        }

        /* Tabs */
        .report-tabs {
            border-bottom: none;
            gap: 0;
        }

        .report-tabs .nav-item {
            margin: 0;
        }

        .report-tabs .nav-link {
            color: #666;
            font-weight: 600;
            font-size: 1rem;
            padding: 0.8rem 2.5rem;
            border: 2px solid #e67e22;
            border-bottom: none;
            background-color: #fff;
            border-radius: 8px 8px 0 0;
            margin-right: 2px;
        }

        .report-tabs .nav-link.active {
            color: #dc3545;
            background-color: #fff;
            border-color: #e67e22;
            position: relative;
            z-index: 1;
        }

        /* Tab Content Box */
        .tab-content-box {
            background-color: #fff;
            border: 2px solid #e67e22;
            padding: 2rem;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .report-tabs .nav-link {
                font-size: 0.85rem;
                padding: 0.6rem 1.5rem;
            }

            .tab-content-box {
                padding: 1rem;
            }
        }
    </style>
</head>
<body>
    <div class="container-fluid p-4">
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
                    <jsp:include page="../report/favorites-report.jsp" />
                </div>
            </div>
            <div class="tab-pane fade" id="favorite-users" role="tabpanel" aria-labelledby="favorite-users-tab">
                <div class="tab-content-box">
                    <jsp:include page="../report/favorite-users-report.jsp" />
                </div>
            </div>
            <div class="tab-pane fade" id="shared-friends" role="tabpanel" aria-labelledby="shared-friends-tab">
                <div class="tab-content-box">
                    <jsp:include page="../report/shared-friends-report.jsp" />
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

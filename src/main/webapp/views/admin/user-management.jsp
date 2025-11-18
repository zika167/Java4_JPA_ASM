<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý người dùng</title>
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
    <script src="${pageContext.request.contextPath}/static/js/user-management.js"></script>
</head>
<body>
    <!-- Include Admin Navbar -->
    <jsp:include page="/views/admin/administration-navbar.jsp">
        <jsp:param name="page" value="users"/>
    </jsp:include>

    <div class="container-fluid py-4">
        <!-- Tabs -->
        <ul class="nav nav-tabs user-tabs" id="userTabs" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="edition-tab" data-bs-toggle="tab" data-bs-target="#edition"
                        type="button" role="tab" aria-controls="edition" aria-selected="false">
                    USER EDITION
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="list-tab" data-bs-toggle="tab" data-bs-target="#list"
                        type="button" role="tab" aria-controls="list" aria-selected="true">
                    USER LIST
                </button>
            </li>
        </ul>

        <!-- Tab Content -->
        <div class="tab-content" id="userTabContent">
            <!-- User Edition Tab -->
            <div class="tab-pane fade" id="edition" role="tabpanel" aria-labelledby="edition-tab">
                <div class="tab-content-box">
                    <form id="userForm" method="post" action="${pageContext.request.contextPath}/admin/users/save">
                        <div class="row">
                            <!-- Left Column -->
                            <div class="col-md-6">
                                <div class="mb-4">
                                    <label class="form-label">USERNAME?</label>
                                    <input type="text" class="form-control" name="username">
                                </div>

                                <div class="mb-4">
                                    <label class="form-label">FULLNAME?</label>
                                    <input type="text" class="form-control" name="fullname">
                                </div>
                            </div>

                            <!-- Right Column -->
                            <div class="col-md-6">
                                <div class="mb-4">
                                    <label class="form-label">PASSWORD?</label>
                                    <input type="password" class="form-control" name="password">
                                </div>

                                <div class="mb-4">
                                    <label class="form-label">EMAIL ADDRESS?</label>
                                    <input type="email" class="form-control" name="email">
                                </div>
                            </div>
                        </div>

                        <!-- Action Buttons -->
                        <div class="action-buttons">
                            <button type="submit" name="action" value="update" class="btn-action">Update</button>
                            <button type="submit" name="action" value="delete" class="btn-action btn-delete">Delete</button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- User List Tab -->
            <div class="tab-pane fade show active" id="list" role="tabpanel" aria-labelledby="list-tab">
                <div class="tab-content-box">
                    <!-- User Table -->
                    <table class="user-table">
                        <thead>
                            <tr>
                                <th>Username</th>
                                <th>Password</th>
                                <th>Fullname</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <td>${user.id}</td>
                                    <td>${user.password}</td>
                                    <td>${user.fullname}</td>
                                    <td>${user.email}</td>
                                    <td>${user.admin ? 'Admin' : 'User'}</td>
                                    <td>
                                        <a href="#" class="link-edit" onclick="editUser('${user.id}'); return false;">Edit</a>
                                    </td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>

                    <!-- Pagination -->
                    <div class="pagination-container">
                        <div class="user-count">
                            <c:choose>
                                <c:when test="${not empty totalUsers}">
                                    ${totalUsers} users
                                </c:when>
                                <c:otherwise>
                                    10 users
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

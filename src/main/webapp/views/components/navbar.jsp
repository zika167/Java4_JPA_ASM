<%-- 
  File: navbar.jsp
  Mô tả: Thanh điều hướng chính của ứng dụng
  Chứa các liên kết đến các trang chức năng chính
  Sử dụng Bootstrap 5.3.3 cho giao diện
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Navbar</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/navbar.css" rel="stylesheet">
    <style>
        .navbar .nav-link,
        .navbar .navbar-brand,
        .navbar .dropdown-toggle {
            color: #343a40 !important;
        }
    </style>
</head>
<body>
<%--
  Thanh điều hướng chính
  - Sử dụng màu nền vàng (bg-warning)
  - Responsive với navbar-expand-lg (sẽ thu gọn trên màn hình nhỏ hơn lg)
  - Sticky navbar (dính ở đầu trang khi scroll)
--%>
<nav class="navbar navbar-expand-lg bg-warning sticky-top">
    <div class="container">

        <%-- Logo/Thương hiệu của ứng dụng --%>
        <a class="navbar-brand fw-bold" href="index.jsp">
            <img src="https://res.cloudinary.com/dlpi2u0ds/image/upload/v1763189405/logojava4-Photoroom_bgg2tz.png"
                 alt="Logo"
                 width="60"
                 height="60"
                 class="d-inline-block align-text-top">
        </a>

        <%-- Nút toggle cho menu trên mobile --%>
        <button class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#mainNavbar"
                aria-controls="mainNavbar"
                aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <%-- Khu vực chứa các mục menu chính --%>
        <div class="collapse navbar-collapse" id="mainNavbar">
            <ul class="navbar-nav me-auto">
                <%-- Mục menu Favorites --%>
                <li class="nav-item">
                    <a class="nav-link fw-bold" href="../layout/favorite-layout.jsp">My Favorites</a>
                </li>
            </ul>

            <%-- Ô tìm kiếm --%>
            <form class="search-form mx-3" role="search">
                <div class="search-wrapper">
                    <svg xmlns="http://www.w3.org/2000/svg"
                         width="20"
                         height="20"
                         fill="currentColor"
                         class="search-icon bi bi-search"
                         viewBox="0 0 16 16">
                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                    </svg>
                    <input class="form-control search-input"
                           type="search"
                           placeholder="Tìm kiếm..."
                           aria-label="Search">
                </div>
            </form>

            <%-- Phần bên phải navbar --%>
            <ul class="navbar-nav ms-auto">
                <%
                    Object userSession = session.getAttribute("user");
                    if (userSession != null) {
                        String userId = userSession.toString();
                %>
                <%-- Dropdown khi đã đăng nhập --%>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle fw-bold"
                       href="#"
                       role="button"
                       data-bs-toggle="dropdown"
                       aria-expanded="false">
                        <%= userId %>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end py-0">
                        <li><a class="dropdown-item border-bottom border-danger" href="editProfile.jsp">Edit Profile</a></li>
                        <li><a class="dropdown-item border-bottom border-danger" href="changePassword.jsp">Change Password</a></li>
                        <li><a class="dropdown-item" href="/logoff">Logout</a></li>
                    </ul>
                </li>
                <%
                } else {
                %>
                <%-- Nút Login/Register khi chưa đăng nhập --%>
                <li class="nav-item">
                    <a class="nav-link fw-bold" href="/views/auth/login.jsp">Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link fw-bold" href="/views/auth/register.jsp">Register</a>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>

<%-- Thư viện JavaScript của Bootstrap --%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
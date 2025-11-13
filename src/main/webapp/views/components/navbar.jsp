<%-- 
  File: navbar.jsp
  Mô tả: Thanh điều hướng chính của ứng dụng
  Chứa các liên kết đến các trang chức năng chính
  Sử dụng Bootstrap 5.3.3 cho giao diện
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Navbar</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
</head>
<body>
    <div class="container w-75">
        <%--
          Thanh điều hướng chính
          - Sử dụng màu nền vàng (bg-warning)
          - Responsive với navbar-expand-lg (sẽ thu gọn trên màn hình nhỏ hơn lg)
        --%>
        <nav class="navbar navbar-expand-lg bg-warning">
            <div class="container-fluid">

                <%-- Logo/Thương hiệu của ứng dụng --%>
                <a class="navbar-brand fw-bold text-danger" href="index.jsp">3in1</a>

                <%-- Nút toggle cho menu trên mobile --%>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar" aria-controls="mainNavbar" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <%-- Khu vực chứa các mục menu chính --%>
                <div class="collapse navbar-collapse" id="mainNavbar">
                    <ul class="navbar-nav">

                        <%-- Mục menu Favorites --%>
                        <li class="nav-item">
                            <a class="nav-link fw-bold text-primary" href="../layout/favorite-layout.jsp">My Favorites</a>
                        </li>

                        <%-- Dropdown menu Tài khoản --%>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle fw-bold text-primary" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                My Account
                            </a>

                            <%-- Các tùy chọn trong dropdown menu --%>
                            <ul class="dropdown-menu py-0">
                                <li><a class="dropdown-item border-bottom border-danger" href="login.jsp">Login</a></li>
                                <li><a class="dropdown-item border-bottom border-danger" href="forgotPassword.jsp">Forgot Password</a></li>
                                <li><a class="dropdown-item border-bottom border-danger" href="registration.jsp">Registration</a></li>
                                <li><a class="dropdown-item border-bottom border-danger" href="/logoff">Logoff</a></li>
                                <li><a class="dropdown-item border-bottom border-danger" href="changePassword.jsp">Change Password</a></li>
                                <li><a class="dropdown-item" href="editProfile.jsp">Edit Profile</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>

    <%-- Thư viện JavaScript của Bootstrap --%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
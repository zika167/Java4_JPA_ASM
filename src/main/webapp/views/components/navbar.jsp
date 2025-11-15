<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg bg-warning">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold text-danger" href="index.jsp">3in1</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar" aria-controls="mainNavbar" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="mainNavbar">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link fw-bold text-primary" href="../layout/favorite-layout.jsp">My Favorites</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle fw-bold text-primary" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        My Account
                    </a>
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
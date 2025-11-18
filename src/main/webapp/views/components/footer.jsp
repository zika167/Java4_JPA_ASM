<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="java.time.Year" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/components/footer-new.css">
<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

<!-- Footer -->
<footer class="app-footer">
    <div class="footer-container">
        <div class="footer-content">
            <!-- Brand Section -->
            <div class="footer-section">
                <div class="footer-brand">
                    <img src="https://res.cloudinary.com/dlpi2u0ds/image/upload/v1763189405/logojava4-Photoroom_bgg2tz.png"
                         alt="4in1 Logo"
                         class="footer-logo">
                    <span class="footer-brand-text">
                        <span class="first-letter">4</span>IN<span class="first-letter">1</span> ENTERTAINMENT
                    </span>
                </div>
                <p class="footer-description">Nền tảng chia sẻ video hàng đầu Việt Nam</p>
            </div>

            <!-- Contact Section -->
            <div class="footer-section">
                <h5 class="footer-title">THÔNG TIN LIÊN HỆ</h5>
                <ul class="footer-list">
                    <li><i class="bi bi-geo-alt-fill"></i> 123 Đường ABC, TP.HCM</li>
                    <li><i class="bi bi-telephone-fill"></i> 0123 456 789</li>
                    <li><i class="bi bi-envelope-fill"></i> info@4in1.com</li>
                </ul>
            </div>

            <!-- Links Section -->
            <div class="footer-section">
                <h5 class="footer-title">LIÊN KẾT</h5>
                <ul class="footer-list">
                    <li><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a></li>
                    <li><a href="${pageContext.request.contextPath}/favorite">Video yêu thích</a></li>
                    <li><a href="${pageContext.request.contextPath}/account-settings">Cài đặt tài khoản</a></li>
                    <c:if test="${sessionScope.role == 'admin'}">
                        <li><a href="${pageContext.request.contextPath}/admin/home">Quản trị</a></li>
                    </c:if>
                </ul>
            </div>

            <!-- Social Section -->
            <div class="footer-section">
                <h5 class="footer-title">THEO DÕI CHÚNG TÔI</h5>
                <div class="footer-social">
                    <a href="#" class="social-link"><i class="bi bi-facebook"></i></a>
                    <a href="#" class="social-link"><i class="bi bi-twitter"></i></a>
                    <a href="#" class="social-link"><i class="bi bi-instagram"></i></a>
                    <a href="#" class="social-link"><i class="bi bi-youtube"></i></a>
                </div>
            </div>
        </div>

        <!-- Copyright -->
        <div class="footer-bottom">
            <p>© <%= Year.now().getValue() %> 4IN1 - Assignment Java 4. All rights reserved.</p>
        </div>
    </div>
</footer>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="java.time.Year" %>
<%-- 
  File: footer.jsp
  Mô tả: Chân trang của ứng dụng
  Chứa thông tin bản quyền và các liên kết hữu ích
--%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Navbar</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="/static/css/footer.css" rel="stylesheet">

</head>
<body>
<footer class="mt-auto pb-0">
    <div class="container p-4 bg-dark text-warning text-center text-lg-start pb-0" style="background-color: #343a40 !important;">
        <div class="row">
            <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                <h5 class="text-uppercase fs-4">3in1</h5>
                <p class="fs-5">Assignment Java 4</p>
            </div>

            <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                <h5 class="text-uppercase fs-4">Thông tin liên hệ</h5>
                <p class="mb-2 fs-5"><i class="fas fa-map-marker-alt me-2"></i>123 Đường ABC, TP.HCM</p>
                <p class="mb-2 fs-5"><i class="fas fa-phone me-2"></i>0123 456 789</p>
                <p class="mb-3"><i class="fas fa-envelope me-2"></i>info@3in1.com</p>
            </div>

            <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                <h5 class="text-uppercase fs-4">Liên kết</h5>
                <ul class="list-unstyled mb-0">
                    <li class="mb-2"><a href="/gioi-thieu" class="text-warning text-decoration-none fs-5">Giới thiệu</a></li>
                    <li class="mb-2"><a href="/dich-vu" class="text-warning text-decoration-none fs-5">Dịch vụ</a></li>
                    <li class="mb-2"><a href="/chinh-sach" class="text-warning text-decoration-none fs-5">Chính sách bảo mật</a></li>
                    <li><a href="/lien-he" class="text-warning text-decoration-none fs-5">Liên hệ</a></li>
                </ul>
            </div>

            <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                <h5 class="text-uppercase mb-3 fs-4">Theo dõi chúng tôi</h5>
                <div class="d-flex gap-3">
                    <a href="#" class="text-warning"><i class="fab fa-facebook-f fs-2"></i></a>
                    <a href="#" class="text-warning"><i class="fab fa-twitter fs-2"></i></a>
                    <a href="#" class="text-warning"><i class="fab fa-instagram fs-2"></i></a>
                    <a href="#" class="text-warning"><i class="fab fa-youtube fs-2"></i></a>
                </div>
            </div>
        </div>
    </div>

    <div class="text-center p-3 fs-5" style="background-color: rgba(0, 0, 0, 0.2);">
        © <%= Year.now().getValue() %> 3in1 - Assignment Java4
    </div>
</footer>
</body>
</html>

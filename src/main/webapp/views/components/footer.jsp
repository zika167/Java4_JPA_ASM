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

</head>
<body>
<footer class="container w-75 bg-dark text-white text-center text-lg-start mt-auto">

        <div class="row p-4">
            <div class="col-lg-6 col-md-12 mb-4 mb-md-0">
                <h5 class="text-uppercase">3in1</h5>
                <p>Assignment Java4</p>
            </div>

            <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                <h5 class="text-uppercase">Liên kết</h5>
                <ul class="list-unstyled mb-0">
                    <li><a href="/gioi-thieu" class="text-white">Giới thiệu</a></li>
                    <li><a href="/dich-vu" class="text-white">Dịch vụ</a></li>
                    <li><a href="/chinh-sach" class="text-white">Chính sách bảo mật</a></li>
                    <li><a href="/lien-he" class="text-white">Liên hệ</a></li>
                </ul>
            </div>

            <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                <h5 class="text-uppercase">Theo dõi chúng tôi</h5>
                <div class="mt-3">
                    <a href="#" class="text-white me-2"><i class="fab fa-facebook-f"></i></a>
                    <a href="#" class="text-white me-2"><i class="fab fa-twitter"></i></a>
                    <a href="#" class="text-white me-2"><i class="fab fa-instagram"></i></a>
                    <a href="#" class="text-white"><i class="fab fa-youtube"></i></a>
                </div>
            </div>
        </div>

    <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
        © <%= Year.now().getValue() %> 3in1 - Assignment Java4
    </div>
</footer>
</body>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="java.time.Year" %>
<footer class="bg-dark text-white text-center text-lg-start w-100">
    <div class="container-fluid p-4">
        <div class="row">
            <div class="col-lg-6 col-md-12 mb-4 mb-md-0">
                <h5 class="text-uppercase">3in1</h5>
                <p>Assignment Java 4</p>
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
    </div>

    <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
        © <%= Year.now().getValue() %> 3in1 - Assignment Java4
    </div>
</footer>

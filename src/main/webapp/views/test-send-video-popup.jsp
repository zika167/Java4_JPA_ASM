<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Test Send Video Popup</title>
    
    <!-- Bootstrap 5.3.3 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <!-- Send Video Modal CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/send-video-modal.css">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <h1>Test Send Video Popup</h1>
                <p>Click button bên dưới để test popup:</p>
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#sendVideoModal">
                    <i class="fas fa-share-alt"></i> Open Send Video Modal
                </button>
            </div>
        </div>
    </div>

    <!-- Include Send Video Modal -->
    <jsp:include page="send-video.jsp"/>

    <!-- Bootstrap 5.3.3 JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

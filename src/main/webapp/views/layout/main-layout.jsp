<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>4in1 - Java4 Assignment</title>
    
    <!-- Bootstrap 5.3.3 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/static/css/core/variables.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/core/common.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/components/send-video-modal.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">
    <!-- Include Navbar -->
    <jsp:include page="../components/navbar.jsp"/>
    
    <!-- Main Content -->
    <main class="content-wrapper flex-grow-1">
        <div class="container">
            <jsp:include page="${param.view}"/>
        </div>
    </main>
    
    <!-- Include Footer -->
    <jsp:include page="../components/footer.jsp"/>
    
    <!-- Include Modals & Fragments -->
    <jsp:include page="../fragments/toast.jsp"/>
    <jsp:include page="../fragments/loading-spinner.jsp"/>
    <jsp:include page="../fragments/confirmation-modal.jsp"/>
    <jsp:include page="../fragments/video-preview-modal.jsp"/>
    <jsp:include page="../fragments/share-modal.jsp"/>
    <jsp:include page="../fragments/auth-modals.jsp"/>
    <jsp:include page="../components/send-video.jsp"/>
    
    <!-- Bootstrap 5.3.3 JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Common JS Functions -->
    <script src="${pageContext.request.contextPath}/static/js/common.js"></script>
    <!-- Page specific JS (if exists) -->
    <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Send Video to Your Friend</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card border-warning shadow-sm">
                    <div class="card-header bg-success bg-opacity-25 border-warning">
                        <h2 class="card-title text-dark fw-bold text-uppercase mb-0" style="letter-spacing: 1px; font-size: 18px;">
                            Send video to your friend
                        </h2>
                    </div>

                    <form method="POST" action="${pageContext.request.contextPath}/send-video">
                        <div class="card-body">
                            <div class="mb-3">
                                <label for="friendEmail" class="form-label fw-bold">Your friend's email?</label>
                                <input type="email" class="form-control border-warning" id="friendEmail" name="friendEmail" placeholder="Enter your friend's email" required>
                            </div>
                        </div>

                        <div class="card-footer bg-light border-top">
                            <div class="d-flex justify-content-end">
                                <button type="submit" class="btn btn-warning fw-bold">Send</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

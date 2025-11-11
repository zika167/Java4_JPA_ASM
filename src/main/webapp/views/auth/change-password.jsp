<%-- 
    change-password.jsp
    Purpose: Form for users to change their password
    Author: [Your Name]
    Last Updated: [Date]
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <%-- Meta tags for character encoding and responsive viewport --%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <%-- External CSS dependencies --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <%-- Custom CSS for change password page --%>
    <link rel="stylesheet" href="change-password.css">
</head>

<body>

    <div class="container my-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card change-password-card shadow-sm">

                    <div class="card-header bg-success-subtle header-section">
                        <h4 class="card-title mb-0">CHANGE PASSWORD</h4>
                    </div>

                    <div class="card-body form-body">
                        <%-- 
                            Form for changing password
                            Submits to: changePasswordServlet
                            Method: POST
                        --%>
                        <form action="changePasswordServlet" method="post" id="changePasswordForm">
                            <div class="row mb-3">
                                <div class="col-md-6 mb-3 mb-md-0">
                                    <label for="username" class="form-label field-label">USERNAME?</label>
                                    <input type="text" class="form-control orange-border" id="username"
                                        name="username" required>
                                </div>

                                <div class="col-md-6">
                                    <label for="currentPassword" class="form-label field-label">CURRENT
                                        PASSWORD?</label>
                                    <input type="password" class="form-control orange-border" id="currentPassword"
                                        name="currentPassword" required>
                                </div>
                            </div>

                            <div class="row mb-4">
                                <div class="col-md-6 mb-3 mb-md-0">
                                    <label for="newPassword" class="form-label field-label">NEW PASSWORD?</label>
                                    <input type="password" class="form-control orange-border" id="newPassword"
                                        name="newPassword" required>
                                </div>

                                <div class="col-md-6">
                                    <label for="confirmNewPassword" class="form-label field-label">CONFIRM NEW
                                        PASSWORD?</label>
                                    <input type="password" class="form-control orange-border"
                                        id="confirmNewPassword" name="confirmNewPassword" required>
                                </div>
                            </div>

                            <div class="form-footer d-flex justify-content-end align-items-center">
                                <button type="submit" class="btn btn-warning change-button">Change</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%-- JavaScript Dependencies --%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
        
    <%-- Custom JavaScript for form validation can be added here --%>
    <script>
        // Add client-side validation if needed
        document.getElementById('changePasswordForm').addEventListener('submit', function(e) {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmNewPassword').value;
            
            if (newPassword !== confirmPassword) {
                e.preventDefault();
                alert('New password and confirm password do not match!');
            }
            
            // Add more validations as needed
        });
    </script>

</body>

</html>
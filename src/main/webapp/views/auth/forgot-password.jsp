<%-- 
    forgot-password.jsp
    Purpose: Form for users to retrieve/reset forgotten password
    Author: [Your Name]
    Last Updated: [Date]
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%-- Meta tags for character encoding and responsive viewport --%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password</title>
    <%-- External CSS dependencies --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <%-- Custom CSS for forgot password page --%>
    <link rel="stylesheet" href="forgot-password.css"> 
</head>
<body>

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-md-5 col-lg-4"> 
            <div class="card change-password-card shadow-sm">
                
                <div class="card-header bg-success-subtle header-section">
                    <h4 class="card-title mb-0">FORGOT PASSWORD</h4>
                </div>
                
                <div class="card-body form-body">
                    <%-- 
                        Form for password recovery
                        Submits to: forgotPasswordServlet
                        Method: POST
                        Parameters: username, email
                    --%>
                    <form action="forgotPasswordServlet" method="post" id="forgotPasswordForm">
                        
                        <div class="mb-4"> 
                            <label for="username" class="form-label field-label">USERNAME?</label>
                            <input type="text" class="form-control orange-border" id="username" name="username" required>
                        </div>
                        
                        <div class="mb-4">
                            <label for="email" class="form-label field-label">EMAIL?</label>
                            <input type="email" class="form-control orange-border" id="email" name="email" required>
                        </div>
                        
                        <div class="form-footer d-flex justify-content-end align-items-center">
                            <button type="submit" class="btn btn-warning retrieve-button">Retrieve</button>
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
    document.getElementById('forgotPasswordForm').addEventListener('submit', function(e) {
        const email = document.getElementById('email').value;
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        
        if (!emailRegex.test(email)) {
            e.preventDefault();
            alert('Please enter a valid email address!');
        }
        
        // Add more validations as needed
    });
</script>

</body>
</html>
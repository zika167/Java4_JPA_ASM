package com.fpt.java4_asm;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * LoginServlet handles user authentication
 * Routes users based on their role (admin/user)
 */
@WebServlet(name = "loginServlet", urlPatterns = {"/auth/login", "/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Forward to login page
        request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        
        // Validate input
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and password are required");
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
            return;
        }
        
        // TODO: Replace with actual database authentication
        // This is a placeholder for demonstration
        boolean isAuthenticated = authenticateUser(username, password);
        
        if (isAuthenticated) {
            HttpSession session = request.getSession();
            
            // Determine user role
            String role = getUserRole(username);
            
            // Set session attributes
            session.setAttribute("user", username);
            session.setAttribute("role", role);
            
            // Set session timeout (30 minutes)
            session.setMaxInactiveInterval(30 * 60);
            
            // Handle "Remember Me" functionality
            if ("true".equals(remember)) {
                // TODO: Implement remember me with cookies
                // Cookie userCookie = new Cookie("rememberedUser", username);
                // userCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                // response.addCookie(userCookie);
            }
            
            // Redirect based on role
            if ("admin".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/admin/home");
            } else {
                response.sendRedirect(request.getContextPath() + "/index?login=success");
            }
            
        } else {
            request.setAttribute("error", "Invalid username or password");
            request.setAttribute("username", username); // Preserve username
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
        }
    }
    
    /**
     * Authenticate user credentials
     * TODO: Replace with actual database query
     */
    private boolean authenticateUser(String username, String password) {
        // Placeholder authentication logic
        // In production, check against database with hashed passwords
        
        // Demo users
        if ("admin".equals(username) && "admin123".equals(password)) {
            return true;
        }
        if ("user".equals(username) && "user123".equals(password)) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Get user role
     * TODO: Replace with actual database query
     */
    private String getUserRole(String username) {
        // Placeholder role logic
        // In production, retrieve from database
        
        if ("admin".equals(username)) {
            return "admin";
        }
        
        return "user";
    }
}

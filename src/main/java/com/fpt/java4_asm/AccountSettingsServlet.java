package com.fpt.java4_asm;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * AccountSettingsServlet handles user account settings page
 */
@WebServlet(name = "accountSettingsServlet", urlPatterns = {"/account-settings", "/auth/account-settings"})
public class AccountSettingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        // TODO: Fetch user data from database
        // String username = (String) session.getAttribute("user");
        // User user = userService.findByUsername(username);
        // request.setAttribute("user", user);
        
        // Forward to account settings page
        request.getRequestDispatcher("/views/auth/account-settings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        // TODO: Handle account settings update
        // String username = (String) session.getAttribute("user");
        // String fullname = request.getParameter("fullname");
        // String email = request.getParameter("email");
        // ... update user data in database
        
        request.setAttribute("success", "Account settings updated successfully!");
        request.getRequestDispatcher("/views/auth/account-settings.jsp").forward(request, response);
    }
}

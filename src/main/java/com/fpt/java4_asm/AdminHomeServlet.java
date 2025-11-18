package com.fpt.java4_asm;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "adminHomeServlet", value = "/admin/home")
public class AdminHomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        // TODO: Add logic to fetch dashboard statistics from database
        // For now, setting sample data
        request.setAttribute("totalUsers", 150);
        request.setAttribute("totalVideos", 320);
        request.setAttribute("totalReports", 45);
        request.setAttribute("activeUsers", 89);
        
        request.getRequestDispatcher("/views/admin/admin-home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}

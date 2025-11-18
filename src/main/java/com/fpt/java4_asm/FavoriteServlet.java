package com.fpt.java4_asm;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * FavoriteServlet handles favorite videos page
 */
@WebServlet(name = "favoriteServlet", value = "/favorite")
public class FavoriteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        // Add sample favorite videos - FAVORITE PAGE: Videos 201-212
        java.util.List<VideoItem> videos = new java.util.ArrayList<>();
        for (int i = 201; i <= 212; i++) {
            VideoItem video = new VideoItem();
            video.setId(String.valueOf(i));
            video.setTitle("[Yêu thích] Video số " + (i - 200));
            videos.add(video);
        }
        request.setAttribute("videos", videos);
        
        // Forward to favorite layout page
        request.getRequestDispatcher("/views/layout/favorite-layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * Inner class to represent video item
     */
    public static class VideoItem {
        private String id;
        private String title;
        
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
    }
}

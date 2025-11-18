package com.fpt.java4_asm;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * IndexServlet handles home page requests
 * Displays featured videos on the homepage
 */
@WebServlet(name = "indexServlet", urlPatterns = {"", "/index", "/home"})
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // TODO: Replace with actual database query to fetch videos
        List<VideoItem> videos = getFeaturedVideos();
        
        // Set videos as request attribute
        request.setAttribute("videos", videos);
        
        // Forward to index page
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * Get featured videos
     * TODO: Replace with actual database query
     */
    private List<VideoItem> getFeaturedVideos() {
        List<VideoItem> videos = new ArrayList<>();
        
        // Mock data - HOME PAGE: Videos 1-12
        String[] categories = {"Học Tập", "Giải Trí", "Âm Nhạc", "Thể Thao", "Công Nghệ", "Du Lịch"};
        
        for (int i = 1; i <= 12; i++) {
            VideoItem video = new VideoItem();
            video.setId(String.valueOf(i));
            video.setTitle("[Trang Chủ] " + categories[i % categories.length] + " - Video " + i);
            video.setDescription("Video nổi bật trên trang chủ số " + i);
            videos.add(video);
        }
        
        return videos;
    }
    
    /**
     * Inner class to represent video item
     * TODO: Replace with actual entity class from your project
     */
    public static class VideoItem {
        private String id;
        private String title;
        private String description;
        
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}

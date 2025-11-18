package com.fpt.java4_asm;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * DetailsServlet handles video detail page requests
 * Displays detailed information about a specific video
 */
@WebServlet(name = "detailsServlet", urlPatterns = {"/details", "/video/details"})
public class DetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get video ID from request parameter
        String videoId = request.getParameter("id");
        
        if (videoId == null || videoId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Video ID is required");
            return;
        }
        
        // TODO: Replace with actual database query to fetch video details
        // For now, create a mock video object
        VideoDetail video = getVideoById(videoId);
        
        if (video == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Video not found");
            return;
        }
        
        // Set video as request attribute
        request.setAttribute("video", video);
        
        // Fetch related videos
        java.util.List<VideoDetail> relatedVideos = getRelatedVideos(videoId);
        request.setAttribute("related", relatedVideos);
        
        // Forward to details page
        request.getRequestDispatcher("/views/layout/details.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * Get video details by ID
     * TODO: Replace with actual database query
     */
    private VideoDetail getVideoById(String id) {
        // Mock implementation
        VideoDetail video = new VideoDetail();
        video.setId(id);
        video.setTitle("Video chi tiết số " + id);
        video.setDescription("Đây là mô tả chi tiết cho video " + id + 
                           ". Nội dung video rất hấp dẫn và bổ ích. " +
                           "Trong thực tế, dữ liệu này sẽ được lấy từ database.");
        video.setViews(1234);
        video.setLikes(89);
        return video;
    }
    
    /**
     * Get related videos
     * TODO: Replace with actual database query
     */
    private java.util.List<VideoDetail> getRelatedVideos(String currentVideoId) {
        java.util.List<VideoDetail> related = new java.util.ArrayList<>();
        
        // Generate 5 related videos (different from current)
        for (int i = 1; i <= 5; i++) {
            int relatedId = (Integer.parseInt(currentVideoId) + i) % 20 + 1;
            VideoDetail video = new VideoDetail();
            video.setId(String.valueOf(relatedId));
            video.setTitle("Video liên quan " + relatedId);
            related.add(video);
        }
        
        return related;
    }
    
    /**
     * Inner class to represent video details
     * TODO: Replace with actual entity class from your project
     */
    public static class VideoDetail {
        private String id;
        private String title;
        private String name;
        private String description;
        private String email;
        private int views;
        private int likes;
        
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public int getViews() { return views; }
        public void setViews(int views) { this.views = views; }
        
        public int getLikes() { return likes; }
        public void setLikes(int likes) { this.likes = likes; }
    }
}

package com.fpt.java4_asm.servlets;

import com.fpt.java4_asm.dto.ShareVideoRequest;
import com.fpt.java4_asm.models.entities.Share;
import com.fpt.java4_asm.services.ShareService;
import com.fpt.java4_asm.services.impl.ShareServiceImpl;
import com.fpt.java4_asm.servlets.base.BaseApiServlet;
import com.fpt.java4_asm.utils.helpers.ValidationHelper;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/share")
public class ShareVideoServlet extends BaseApiServlet {
    private final ShareService shareService = new ShareServiceImpl();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        try {
            // Read request body
            ShareVideoRequest request = gson.fromJson(
                req.getReader(), 
                ShareVideoRequest.class
            );

            // Validate request
            ValidationHelper.validateShareRequest(request);

            // Process sharing
            Share shared = shareService.shareVideo(request);

            // Prepare success response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Chia sẻ video thành công");
            response.put("data", shared);

            // Send response
            sendAsJson(resp, response, HttpServletResponse.SC_OK);
            
        } catch (IllegalArgumentException e) {
            // Handle validation errors
            sendErrorResponse(resp, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            // Handle other errors
            sendErrorResponse(
                resp, 
                "Có lỗi xảy ra: " + e.getMessage(), 
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );
        }
    }

    private void sendErrorResponse(
        HttpServletResponse resp, 
        String message, 
        int status
    ) throws IOException {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        sendAsJson(resp, errorResponse, status);
    }
}

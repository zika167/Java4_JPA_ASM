package com.fpt.java4_asm.controllers.api;

import com.fpt.java4_asm.dto.ShareVideoRequest;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.Share;
import com.fpt.java4_asm.services.ShareService;
import com.fpt.java4_asm.services.impl.ShareServiceImpl;
import com.fpt.java4_asm.utils.constants.ApiConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet(ApiConstants.API_SHARES + "/*")
public class ShareAPI extends BaseApiServlet {

	private final ShareService shareService = new ShareServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String pathInfo = req.getPathInfo();
			if (pathInfo == null || pathInfo.equals("/")) {
				// No filter -> return empty list (or implement getAll in service)
				sendSuccessResponse(resp, Collections.emptyList(), ApiConstants.MSG_SUCCESS);
				return;
			}

			String[] parts = pathInfo.split("/");
			// Expecting /video/{videoId} or /user/{userId}
			if (parts.length == 3) {
				if ("video".equals(parts[1])) {
					String videoId = parts[2];
					List<Share> shares = shareService.getSharesByVideoId(videoId);
					sendSuccessResponse(resp, shares, ApiConstants.MSG_SUCCESS);
					return;
				}
				if ("user".equals(parts[1])) {
					String userId = parts[2];
					List<Share> shares = shareService.getSharesByUserId(userId);
					sendSuccessResponse(resp, shares, ApiConstants.MSG_SUCCESS);
					return;
				}
			}

			throw new AppException(Error.NOT_FOUND);
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(Error.INTERNAL_SERVER_ERROR, e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			ShareVideoRequest request = parseRequestBody(req, ShareVideoRequest.class);
			Share saved = shareService.shareVideo(request);
			resp.setStatus(HttpServletResponse.SC_CREATED);
			sendSuccessResponse(resp, saved, ApiConstants.MSG_CREATED);
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi khi chia sẻ video");
		}
	}
}

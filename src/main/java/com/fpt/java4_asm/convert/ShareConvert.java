package com.fpt.java4_asm.convert;

import com.fpt.java4_asm.dto.request.ShareRequest;
import com.fpt.java4_asm.dto.response.ShareResponse;
import com.fpt.java4_asm.dto.response.PaginatedResponse;
import com.fpt.java4_asm.models.entities.Share;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Lớp chuyển đổi dữ liệu giữa các đối tượng Share entity và ShareResponse/ShareRequest DTO
 * 
 * Mô tả:
 * - Thực hiện ánh xạ dữ liệu giữa các lớp entity và DTO
 * - Cung cấp các phương thức chuyển đổi cho từng đối tượng và danh sách đối tượng
 */
public class ShareConvert {

    /**
     * Chuyển đổi từ đối tượng Share entity sang đối tượng ShareResponse DTO
     * 
     * @param share Đối tượng Share cần chuyển đổi
     * @return Đối tượng ShareResponse đã được chuyển đổi, trả về null nếu đầu vào là null
     */
    public ShareResponse toResponse(Share share) {
        if (share == null) {
            return null;
        }
        
        ShareResponse response = new ShareResponse();
        response.setId(share.getId());
        response.setUserId(share.getUser() != null ? share.getUser().getId() : null);
        response.setVideoId(share.getVideo() != null ? share.getVideo().getId() : null);
        response.setEmails(share.getEmails());
        response.setShareDate(share.getShareDate());
        
        return response;
    }

    /**
     * Chuyển đổi từ đối tượng ShareRequest DTO sang đối tượng Share entity
     * 
     * @param request Đối tượng ShareRequest cần chuyển đổi
     * @return Đối tượng Share đã được tạo mới, trả về null nếu đầu vào là null
     */
    public Share toEntity(ShareRequest request) {
        if (request == null) {
            return null;
        }
        
        Share share = new Share();
        share.setUser(request.getUser());
        share.setVideo(request.getVideo());
        share.setEmails(request.getEmails());
        share.setShareDate(request.getShareDate() != null ? request.getShareDate() : new java.util.Date());
        
        return share;
    }

    /**
     * Cập nhật thông tin của một đối tượng Share đã tồn tại từ dữ liệu trong ShareRequest
     * 
     * @param share Đối tượng Share cần cập nhật
     * @param request Đối tượng ShareRequest chứa dữ liệu mới
     * @return Đối tượng Share đã được cập nhật, trả về nguyên bản nếu một trong hai tham số là null
     */
    public Share toEntity(Share share, ShareRequest request) {
        if (share == null || request == null) {
            return share;
        }
        
        share.setUser(request.getUser() != null ? request.getUser() : share.getUser());
        share.setVideo(request.getVideo() != null ? request.getVideo() : share.getVideo());
        share.setEmails(request.getEmails() != null ? request.getEmails() : share.getEmails());
        share.setShareDate(new java.util.Date());
        
        return share;
    }

    /**
     * Chuyển đổi một danh sách các đối tượng Share thành danh sách ShareResponse
     * 
     * @param shares Danh sách các đối tượng Share cần chuyển đổi
     * @return Danh sách các đối tượng ShareResponse đã được chuyển đổi, trả về null nếu đầu vào là null
     */
    public List<ShareResponse> toResponseList(List<Share> shares) {
        if (shares == null) {
            return null;
        }
        
        return shares.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Chuyển đổi một danh sách Share thành PaginatedResponse
     *
     * @param shares Danh sách các đối tượng Share cần chuyển đổi
     * @return Đối tượng PaginatedResponse chứa danh sách ShareResponse đã được chuyển đổi
     */
    public PaginatedResponse<ShareResponse> toPaginatedResponse(List<Share> shares) {
        if (shares == null || shares.isEmpty()) {
            return PaginatedResponse.of(
                    List.of(),
                    0,
                    0,
                    0
            );
        }

        List<ShareResponse> responseList = toResponseList(shares);
        
        // Thiết lập giá trị mặc định cho phân trang
        int pageNumber = 1;
        int pageSize = shares.size();
        long totalElements = shares.size();
        
        return PaginatedResponse.of(
                responseList,
                pageNumber,
                pageSize,
                totalElements
        );
    }
}

package com.fpt.java4_asm.convert;

import com.fpt.java4_asm.dto.request.FavoriteRequest;
import com.fpt.java4_asm.dto.response.FavoriteResponse;
import com.fpt.java4_asm.dto.response.PaginatedResponse;
import com.fpt.java4_asm.models.entities.Favorite;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Lớp chuyển đổi dữ liệu giữa các đối tượng Favorite entity và FavoriteResponse/FavoriteRequest DTO
 * 
 * Mô tả:
 * - Thực hiện ánh xạ dữ liệu giữa các lớp entity và DTO
 * - Cung cấp các phương thức chuyển đổi cho từng đối tượng và danh sách đối tượng
 */
public class FavoriteConvert {

    /**
     * Chuyển đổi từ đối tượng Favorite entity sang đối tượng FavoriteResponse DTO
     * 
     * @param favorite Đối tượng Favorite cần chuyển đổi
     * @return Đối tượng FavoriteResponse đã được chuyển đổi, trả về null nếu đầu vào là null
     * 
     * Mô tả:
     * - Phương thức này thực hiện ánh xạ dữ liệu từ entity sang DTO để trả về cho client
     * - Xử lý trường hợp đầu vào là null để tránh lỗi NullPointerException
     */
    public FavoriteResponse toResponse(Favorite favorite) {
        if (favorite == null) {
            return null;
        }
        
        FavoriteResponse response = new FavoriteResponse();
        response.setId(favorite.getId());
        response.setUser(favorite.getUser());
        response.setVideo(favorite.getVideo());
        response.setLikeDate(favorite.getLikeDate());
        
        return response;
    }

    /**
     * Chuyển đổi từ đối tượng FavoriteRequest DTO sang đối tượng Favorite entity
     * 
     * @param request Đối tượng FavoriteRequest cần chuyển đổi
     * @return Đối tượng Favorite đã được tạo mới, trả về null nếu đầu vào là null
     * 
     * Mô tả:
     * - Tạo mới đối tượng Favorite từ dữ liệu request
     * - Nếu likeDate không được cung cấp, sẽ sử dụng ngày hiện tại làm giá trị mặc định
     * - Kiểm tra null để đảm bảo tính ổn định của chương trình
     */
    public Favorite toEntity(FavoriteRequest request) {
        if (request == null) {
            return null;
        }
        
        Favorite favorite = new Favorite();
        favorite.setUser(request.getUser());
        favorite.setVideo(request.getVideo());
        favorite.setLikeDate(request.getLikeDate() != null ? request.getLikeDate() : new java.util.Date());
        
        return favorite;
    }

    /**
     * Cập nhật thông tin của một đối tượng Favorite đã tồn tại từ dữ liệu trong FavoriteRequest
     * 
     * @param favorite Đối tượng Favorite cần cập nhật
     * @param request Đối tượng FavoriteRequest chứa dữ liệu mới
     * @return Đối tượng Favorite đã được cập nhật, trả về nguyên bản nếu một trong hai tham số là null
     * 
     * Mô tả:
     * - Cập nhật từng trường dữ liệu nếu giá trị mới khác null
     * - Giữ nguyên giá trị cũ nếu giá trị mới là null
     * - Tự động cập nhật likeDate nếu không được cung cấp
     * - Đảm bảo tính toàn vẹn dữ liệu bằng cách kiểm tra null
     */
    public Favorite toEntity(Favorite favorite, FavoriteRequest request) {
        if (favorite == null || request == null) {
            return favorite;
        }
        
        favorite.setUser(request.getUser() != null ? request.getUser() : favorite.getUser());
        favorite.setVideo(request.getVideo() != null ? request.getVideo() : favorite.getVideo());
        favorite.setLikeDate(request.getLikeDate() != null ? request.getLikeDate() : new java.util.Date());
        
        return favorite;
    }

    /**
     * Chuyển đổi một danh sách các đối tượng Favorite thành danh sách FavoriteResponse
     * 
     * @param favorites Danh sách các đối tượng Favorite cần chuyển đổi
     * @return Danh sách các đối tượng FavoriteResponse đã được chuyển đổi, trả về null nếu đầu vào là null
     * 
     * Mô tả:
     * - Sử dụng Java Stream API để xử lý song song nếu danh sách lớn
     * - Áp dụng phương thức toResponse() cho từng phần tử trong danh sách
     * - Thu thập kết quả vào một danh sách mới
     * - Xử lý trường hợp danh sách đầu vào là null
     */
    public List<FavoriteResponse> toResponseList(List<Favorite> favorites) {
        if (favorites == null) {
            return null;
        }
        
        return favorites.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Chuyển đổi một đối tượng PaginatedResponse chứa danh sách Favorite
     * thành PaginatedResponse chứa danh sách FavoriteResponse
     *
     * @param paginatedFavorites Đối tượng PaginatedResponse chứa danh sách Favorite cần chuyển đổi
     * @return Đối tượng PaginatedResponse chứa danh sách FavoriteResponse đã được chuyển đổi
     *
     * Mô tả:
     * - Chuyển đổi danh sách các đối tượng Favorite thành FavoriteResponse
     * - Giữ nguyên thông tin phân trang từ đối tượng gốc
     * - Xử lý trường hợp đầu vào là null
     */
    public PaginatedResponse<FavoriteResponse> toPaginatedResponse(List<Favorite> favorites) {
        if (favorites == null || favorites.isEmpty()) {
            return PaginatedResponse.of(
                    List.of(),
                    0,
                    0,
                    0
            );
        }

        List<FavoriteResponse> responseList = toResponseList(favorites);
        
        // Since we only have a list without pagination info, we'll set default values
        int pageNumber = 1;
        int pageSize = favorites.size();
        long totalElements = favorites.size();
        
        return PaginatedResponse.of(
                responseList,
                pageNumber,
                pageSize,
                totalElements
        );
    }
}

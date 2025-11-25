package com.fpt.java4_asm.utils.helpers;

import com.fpt.java4_asm.dto.request.FavoriteRequest;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.Favorite;

/**
 * Lớp tiện ích để kiểm tra tính hợp lệ của các thao tác liên quan đến Yêu thích.
 * 
 * Cung cấp các phương thức tĩnh để kiểm tra nhiều khía cạnh khác nhau của các thực thể Yêu thích
 * và các DTO liên quan, đảm bảo tính toàn vẹn dữ liệu và các quy tắc nghiệp vụ
 * trước khi xử lý các thao tác liên quan đến Yêu thích.
 * 
 * Tất cả các phương thức trong lớp này đều là tĩnh và sẽ ném ra ngoại lệ {@link AppException}
 * với mã lỗi phù hợp khi việc kiểm tra không thành công.
 * 
 * @author Tên của bạn
 * @version 1.0
 * @since 26-11-2025
 */
public class FavoriteValidation {
    
    /**
     * Constructor riêng tư để ngăn chặn việc tạo thể hiện của lớp tiện ích này.
     * Tất cả các phương thức đều là tĩnh và nên được gọi thông qua tên lớp.
     */
    private FavoriteValidation() {
        throw new UnsupportedOperationException("Không thể tạo thể hiện của lớp tiện ích");
    }
    
    /**
     * Kiểm tra tính hợp lệ của đối tượng FavoriteRequest.
     * 
     * @param request Đối tượng FavoriteRequest cần kiểm tra
     * @throws AppException với mã lỗi INVALID_DATA nếu:
     *         - Request là null
     *         - Thiếu thông tin người dùng hoặc không hợp lệ
     *         - Thiếu thông tin video hoặc không hợp lệ
     * @see com.fpt.java4_asm.dto.request.FavoriteRequest
     */
    public static void validateFavoriteRequest(FavoriteRequest request) {
        if (request == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin yêu thích không được để trống");
        }

        validateRequiredField(request.getUser(), "Người dùng");
        validateRequiredField(request.getVideo(), "Video");

        if (request.getUser() == null || request.getUser().getId() == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin người dùng không hợp lệ");
        }

        if (request.getVideo() == null || request.getVideo().getId() == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin video không hợp lệ");
        }
    }

    /**
     * Kiểm tra tính hợp lệ của một đối tượng Favorite.
     * 
     * @param favorite Đối tượng Favorite cần kiểm tra
     * @throws AppException với mã lỗi INVALID_DATA nếu:
     *         - Đối tượng favorite là null
     *         - Thiếu thông tin người dùng hoặc không hợp lệ
     *         - Thiếu thông tin video hoặc không hợp lệ
     * @see com.fpt.java4_asm.models.entities.Favorite
     */
    public static void validateFavorite(Favorite favorite) {
        if (favorite == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin yêu thích không được để trống");
        }

        validateRequiredField(favorite.getUser(), "Người dùng");
        validateRequiredField(favorite.getVideo(), "Video");

        if (favorite.getUser().getId() == null) {
            throw new AppException(Error.INVALID_DATA, "ID người dùng không hợp lệ");
        }

        if (favorite.getVideo().getId() == null) {
            throw new AppException(Error.INVALID_DATA, "ID video không hợp lệ");
        }

        if (favorite.getLikeDate() == null) {
            favorite.setLikeDate(new java.util.Date());
        }
    }

    /**
     * Kiểm tra tính hợp lệ của ID Yêu thích.
     * 
     * @param id ID cần kiểm tra
     * @throws AppException với mã lỗi INVALID_DATA nếu ID là null hoặc nhỏ hơn 1
     */
    public static void validateFavoriteId(Integer id) {
        if (id == null || id <= 0) {
            throw new AppException(Error.INVALID_DATA, "ID yêu thích không hợp lệ");
        }
    }

    /**
     * Kiểm tra xem một trường bắt buộc có giá trị null hay không.
     * 
     * @param <T> Kiểu dữ liệu của trường cần kiểm tra
     * @param field Trường cần kiểm tra
     * @param fieldName Tên trường (dùng trong thông báo lỗi)
     * @throws AppException với mã lỗi INVALID_DATA nếu trường là null
     */
    
    /**
     * Kiểm tra tính hợp lệ của các tham số phân trang.
     * 
     * @param page Số trang (bắt đầu từ 1)
     * @param size Số lượng phần tử trên mỗi trang
     * @throws AppException với mã lỗi INVALID_DATA nếu:
     *         - Số trang nhỏ hơn 1
     *         - Kích thước trang nhỏ hơn 1
     *         - Kích thước trang vượt quá giới hạn tối đa (100)
     */
    public static void validatePagination(int page, int size) {
        if (page < 1) {
            throw new AppException(Error.INVALID_DATA, "Số trang phải lớn hơn 0");
        }
        if (size < 1) {
            throw new AppException(Error.INVALID_DATA, "Kích thước trang phải lớn hơn 0");
        }
        // Giới hạn kích thước trang tối đa để tránh tải quá nhiều dữ liệu
        int maxPageSize = 100;
        if (size > maxPageSize) {
            throw new AppException(Error.INVALID_DATA, 
                String.format("Kích thước trang tối đa là %d", maxPageSize));
        }
    }

    /**
     * Validates that a required field is not null
     * @param field The field to validate
     * @param fieldName The name of the field for error messages
     * @param <T> The type of the field
     * @throws AppException if the field is null
     */
    private static <T> void validateRequiredField(T field, String fieldName) {
        if (field == null) {
            throw new AppException(Error.INVALID_DATA, fieldName + " không được để trống");
        }
    }
}

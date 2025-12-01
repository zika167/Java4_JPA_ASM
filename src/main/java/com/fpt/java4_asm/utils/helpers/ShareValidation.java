package com.fpt.java4_asm.utils.helpers;

import com.fpt.java4_asm.dto.request.ShareRequest;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.Share;

/**
 * Lớp tiện ích để kiểm tra tính hợp lệ của các thao tác liên quan đến Share.
 * 
 * Cung cấp các phương thức tĩnh để kiểm tra nhiều khía cạnh khác nhau của các thực thể Share
 * và các DTO liên quan, đảm bảo tính toàn vẹn dữ liệu và các quy tắc nghiệp vụ
 * trước khi xử lý các thao tác liên quan đến Share.
 */
public class ShareValidation {
    
    /**
     * Constructor riêng tư để ngăn chặn việc tạo thể hiện của lớp tiện ích này.
     * Tất cả các phương thức đều là tĩnh và nên được gọi thông qua tên lớp.
     */
    private ShareValidation() {
        throw new UnsupportedOperationException("Không thể tạo thể hiện của lớp tiện ích");
    }
    
    /**
     * Kiểm tra tính hợp lệ của đối tượng ShareRequest.
     * 
     * @param request Đối tượng ShareRequest cần kiểm tra
     * @throws AppException với mã lỗi INVALID_DATA nếu:
     *         - Request là null
     *         - Thiếu thông tin người dùng hoặc không hợp lệ
     *         - Thiếu thông tin video hoặc không hợp lệ
     *         - Thiếu danh sách email hoặc không hợp lệ
     */
    public static void validateShareRequest(ShareRequest request) {
        if (request == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin chia sẻ không được để trống");
        }

        validateRequiredField(request.getUser(), "Người dùng");
        validateRequiredField(request.getVideo(), "Video");
        validateRequiredField(request.getEmails(), "Danh sách email");

        if (request.getUser() == null || request.getUser().getId() == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin người dùng không hợp lệ");
        }

        if (request.getVideo() == null || request.getVideo().getId() == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin video không hợp lệ");
        }

        if (request.getEmails() == null || request.getEmails().trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, "Danh sách email không được để trống");
        }
    }

    /**
     * Kiểm tra tính hợp lệ của một đối tượng Share.
     * 
     * @param share Đối tượng Share cần kiểm tra
     * @throws AppException với mã lỗi INVALID_DATA nếu:
     *         - Đối tượng share là null
     *         - Thiếu thông tin người dùng hoặc không hợp lệ
     *         - Thiếu thông tin video hoặc không hợp lệ
     *         - Thiếu danh sách email hoặc không hợp lệ
     */
    public static void validateShare(Share share) {
        if (share == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin chia sẻ không được để trống");
        }

        validateRequiredField(share.getUser(), "Người dùng");
        validateRequiredField(share.getVideo(), "Video");
        validateRequiredField(share.getEmails(), "Danh sách email");

        if (share.getUser().getId() == null) {
            throw new AppException(Error.INVALID_DATA, "ID người dùng không hợp lệ");
        }

        if (share.getVideo().getId() == null) {
            throw new AppException(Error.INVALID_DATA, "ID video không hợp lệ");
        }

        if (share.getEmails() == null || share.getEmails().trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, "Danh sách email không được để trống");
        }

        if (share.getShareDate() == null) {
            share.setShareDate(new java.util.Date());
        }
    }

    /**
     * Kiểm tra tính hợp lệ của ID Share.
     * 
     * @param id ID cần kiểm tra
     * @throws AppException với mã lỗi INVALID_DATA nếu ID là null hoặc nhỏ hơn 1
     */
    public static void validateShareId(Integer id) {
        if (id == null || id <= 0) {
            throw new AppException(Error.INVALID_DATA, "ID chia sẻ không hợp lệ");
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
    private static <T> void validateRequiredField(T field, String fieldName) {
        if (field == null) {
            throw new AppException(Error.INVALID_DATA, fieldName + " không được để trống");
        }
    }

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
        int maxPageSize = 100;
        if (size > maxPageSize) {
            throw new AppException(Error.INVALID_DATA, 
                String.format("Kích thước trang tối đa là %d", maxPageSize));
        }
    }
}

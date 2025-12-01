package com.fpt.java4_asm.services;

import com.fpt.java4_asm.dto.response.PaginatedResponse;
import java.util.List;
import java.util.Optional;

/**
 * Base Service interface định nghĩa các phương thức CRUD chung
 * @param <REQ> Request DTO type (dữ liệu đầu vào)
 * @param <RES> Response DTO type (dữ liệu trả về)
 * @param <ID> Primary key type
 */
public interface BaseService<REQ, RES, ID> { // REQ = Request / RES = Response / ID = Primary key
    
    /**
     * Tạo mới entity
     */
    RES create(REQ request);

    /**
     * Cập nhật entity
     */
    Optional<RES> update(ID id, REQ request);

    /**
     * Lấy entity theo ID
     */
    Optional<RES> getById(ID id);

    /**
     * Lấy tất cả entity
     */
    List<RES> getAll();

    /**
     * Xóa entity theo ID
     */
    boolean delete(ID id);

    /**
     * Kiểm tra entity có tồn tại không
     */
    boolean exists(ID id);

    /**
     * Đếm số lượng entity
     */
    long count();

    /**
     * Lấy danh sách phân trang
     */
    PaginatedResponse<RES> paginate(int page, int size);
}


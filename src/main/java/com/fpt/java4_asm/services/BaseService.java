package com.fpt.java4_asm.services;

import java.util.List;
import java.util.Optional;

/**
 * Base Service interface định nghĩa các phương thức xử lý logic chung
 * @param <T> Entity type
 * @param <ID> Primary key type
 */
public interface BaseService<T, ID> {
    /**
     * Tạo mới entity
     */
    T create(T entity);

    /**
     * Cập nhật entity
     */
    T update(ID id, T entity);

    /**
     * Lấy entity theo ID
     */
    Optional<T> getById(ID id);

    /**
     * Lấy tất cả entity
     */
    List<T> getAll();

    /**
     * Xóa entity theo ID
     */
    boolean delete(ID id);

    /**
     * Kiểm tra entity có tồn tại hay không
     */
    boolean exists(ID id);

    /**
     * Đếm số lượng entity
     */
    long count();
}

package com.fpt.java4_asm.repositories;

//import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * Base Repository interface định nghĩa các phương thức CRUD chung
 * @param <T> Entity type
 * @param <ID> Primary key type
 */
public interface BaseRepository<T, ID> {
    /**
     * Lưu entity
     */
    T save(T entity);

    /**
     * Cập nhật entity
     */
    Optional<T> update(T entity);

    /**
     * Lấy entity theo ID
     */
    Optional<T> findById(ID id);

    /**
     * Lấy tất cả entity
     */
    List<T> findAll();

    /**
     * Xóa entity theo ID
     */
    boolean deleteById(ID id);

    /**
     * Kiểm tra entity có tồn tại hay không
     */
    boolean existsById(ID id);

    /**
     * Đếm số lượng entity
     */
    long count();
}

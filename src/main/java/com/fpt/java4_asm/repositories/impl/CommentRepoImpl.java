package com.fpt.java4_asm.repositories.impl;

import com.fpt.java4_asm.config.HibernateUtil;
import com.fpt.java4_asm.models.entities.Comment;
import com.fpt.java4_asm.repositories.CommentRepo;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

/**
 * Triển khai cụ thể của CommentRepo
 * 
 * Lớp này chịu trách nhiệm tương tác với cơ sở dữ liệu cho các thao tác liên quan đến Comment
 * Sử dụng JPA/Hibernate để thực hiện các truy vấn
 */
public class CommentRepoImpl implements CommentRepo {
    EntityManager em = HibernateUtil.getEntityManager();

    /**
     * Lưu một đối tượng Comment vào cơ sở dữ liệu
     *
     * @param entity Đối tượng Comment cần lưu
     * @return Đối tượng đã được lưu với ID được tạo tự động
     * @throws IllegalArgumentException Nếu đối tượng đầu vào là null
     * @throws RuntimeException Nếu có lỗi xảy ra trong quá trình lưu
     */
    @Override
    public Comment save(Comment entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Đối tượng Comment không được để trống");
        }

        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Lỗi khi lưu Comment: " + e.getMessage(), e);
        }
    }

    /**
     * Cập nhật thông tin của một đối tượng Comment đã tồn tại
     *
     * @param entity Đối tượng Comment chứa thông tin cập nhật
     * @return Optional chứa đối tượng đã được cập nhật nếu tìm thấy, rỗng nếu không
     * @throws RuntimeException Nếu có lỗi xảy ra trong quá trình cập nhật
     */
    @Override
    public Optional<Comment> update(Comment entity) {
        if (entity == null || entity.getId() == null) {
            return Optional.empty();
        }

        try {
            em.getTransaction().begin();
            Comment updatedEntity = em.merge(entity);
            em.getTransaction().commit();
            return Optional.of(updatedEntity);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Lỗi khi cập nhật Comment: " + e.getMessage(), e);
        }
    }

    /**
     * Tìm kiếm một đối tượng Comment theo ID
     *
     * @param id ID của đối tượng Comment cần tìm
     * @return Optional chứa đối tượng tìm thấy, hoặc rỗng nếu không tìm thấy
     * @throws RuntimeException Nếu có lỗi xảy ra trong quá trình tìm kiếm
     */
    @Override
    public Optional<Comment> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        try {
            Comment entity = em.find(Comment.class, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm Comment với ID: " + id, e);
        }
    }

    /**
     * Lấy tất cả các đối tượng Comment từ cơ sở dữ liệu
     *
     * @return Danh sách tất cả các đối tượng Comment
     * @throws RuntimeException Nếu có lỗi xảy ra trong quá trình lấy dữ liệu
     */
    @Override
    public List<Comment> findAll() {
        try {
            String jpql = "SELECT c FROM Comment c ORDER BY c.createdDate DESC";
            TypedQuery<Comment> query = em.createQuery(jpql, Comment.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách Comment", e);
        }
    }

    /**
     * Xóa một đối tượng Comment dựa trên ID
     *
     * @param id ID của đối tượng Comment cần xóa
     * @return true nếu xóa thành công, false nếu không tìm thấy đối tượng
     * @throws RuntimeException Nếu có lỗi xảy ra trong quá trình xóa
     */
    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            return false;
        }

        try {
            em.getTransaction().begin();
            Comment entity = em.find(Comment.class, id);
            if (entity != null) {
                em.remove(entity);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Lỗi khi xóa Comment với ID: " + id, e);
        }
    }

    /**
     * Kiểm tra sự tồn tại của một đối tượng Comment dựa trên ID
     *
     * @param id ID cần kiểm tra
     * @return true nếu tồn tại, false nếu ngược lại
     * @throws RuntimeException Nếu có lỗi xảy ra trong quá trình kiểm tra
     */
    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }

        try {
            String jpql = "SELECT COUNT(c) > 0 FROM Comment c WHERE c.id = :id";
            return em.createQuery(jpql, Boolean.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi kiểm tra tồn tại Comment với ID: " + id, e);
        }
    }

    /**
     * Đếm tổng số lượng đối tượng Comment trong cơ sở dữ liệu
     *
     * @return Tổng số lượng đối tượng Comment
     * @throws RuntimeException Nếu có lỗi xảy ra trong quá trình đếm
     */
    @Override
    public long count() {
        try {
            String jpql = "SELECT COUNT(c) FROM Comment c";
            return em.createQuery(jpql, Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm số lượng Comment", e);
        }
    }

    /**
     * Tìm tất cả các comment của một người dùng dựa trên ID
     * 
     * @param userId ID của người dùng cần tìm kiếm
     * @return Danh sách các đối tượng Comment của người dùng
     * @throws RuntimeException nếu có lỗi xảy ra khi thực hiện truy vấn
     */
    @Override
    public List<Comment> findByUserId(String userId) {
        try {
            String jpql = "SELECT c FROM Comment c WHERE c.user.id = :userId ORDER BY c.createdDate DESC";
            TypedQuery<Comment> query = em.createQuery(jpql, Comment.class)
                    .setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm Comment với userId: " + userId, e);
        }
    }

    /**
     * Tìm tất cả các comment của một video dựa trên ID
     * 
     * @param videoId ID của video cần tìm kiếm
     * @return Danh sách các đối tượng Comment của video
     * @throws RuntimeException nếu có lỗi xảy ra khi thực hiện truy vấn
     */
    @Override
    public List<Comment> findByVideoId(String videoId) {
        try {
            String jpql = "SELECT c FROM Comment c WHERE c.video.id = :videoId ORDER BY c.createdDate DESC";
            TypedQuery<Comment> query = em.createQuery(jpql, Comment.class)
                    .setParameter("videoId", videoId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm Comment với videoId: " + videoId, e);
        }
    }

    /**
     * Đếm số lượng comment của một video
     * 
     * @param videoId ID của video
     * @return Số lượng comment
     * @throws RuntimeException nếu có lỗi xảy ra khi thực hiện truy vấn
     */
    @Override
    public long countByVideoId(String videoId) {
        try {
            String jpql = "SELECT COUNT(c) FROM Comment c WHERE c.video.id = :videoId";
            return em.createQuery(jpql, Long.class)
                    .setParameter("videoId", videoId)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm số lượng Comment với videoId: " + videoId, e);
        }
    }

    /**
     * Lấy danh sách phân trang các comment
     * 
     * @param page Số trang cần lấy (bắt đầu từ 0)
     * @param size Số lượng bản ghi trên mỗi trang
     * @return Danh sách các đối tượng Comment thuộc trang được yêu cầu
     * @throws RuntimeException Nếu có lỗi xảy ra khi thực hiện truy vấn
     */
    @Override
    public List<Comment> pages(int page, int size) {
        // Kiểm tra tham số đầu vào
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }

        try {
            // Tạo câu truy vấn JPQL với sắp xếp theo ngày tạo giảm dần (mới nhất trước)
            String jpql = "SELECT c FROM Comment c ORDER BY c.createdDate DESC";
            
            // Thực hiện phân trang
            return em.createQuery(jpql, Comment.class)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception e) {
            String errorMsg = String.format("Lỗi khi lấy dữ liệu phân trang (trang: %d, kích thước: %d)", page, size);
            System.err.println(errorMsg + ": " + e.getMessage());
            throw new RuntimeException(errorMsg, e);
        }
    }
}

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
    
    private EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }

    /**
     * Lưu một đối tượng Comment vào cơ sở dữ liệu
     */
    @Override
    public Comment save(Comment entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Đối tượng Comment không được để trống");
        }
        EntityManager em = getEntityManager();
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
        } finally {
            em.close();
        }
    }

    /**
     * Cập nhật thông tin của một đối tượng Comment đã tồn tại
     */
    @Override
    public Optional<Comment> update(Comment entity) {
        if (entity == null || entity.getId() == null) {
            return Optional.empty();
        }
        EntityManager em = getEntityManager();
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
        } finally {
            em.close();
        }
    }

    /**
     * Tìm kiếm một đối tượng Comment theo ID
     */
    @Override
    public Optional<Comment> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        EntityManager em = getEntityManager();
        try {
            Comment entity = em.find(Comment.class, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm Comment với ID: " + id, e);
        } finally {
            em.close();
        }
    }

    /**
     * Lấy tất cả các đối tượng Comment từ cơ sở dữ liệu
     */
    @Override
    public List<Comment> findAll() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT c FROM Comment c ORDER BY c.createdDate DESC";
            TypedQuery<Comment> query = em.createQuery(jpql, Comment.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách Comment", e);
        } finally {
            em.close();
        }
    }

    /**
     * Xóa một đối tượng Comment dựa trên ID
     */
    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            return false;
        }
        EntityManager em = getEntityManager();
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
        } finally {
            em.close();
        }
    }

    /**
     * Kiểm tra sự tồn tại của một đối tượng Comment dựa trên ID
     */
    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(c) > 0 FROM Comment c WHERE c.id = :id";
            return em.createQuery(jpql, Boolean.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi kiểm tra tồn tại Comment với ID: " + id, e);
        } finally {
            em.close();
        }
    }

    /**
     * Đếm tổng số lượng đối tượng Comment trong cơ sở dữ liệu
     */
    @Override
    public long count() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(c) FROM Comment c";
            return em.createQuery(jpql, Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm số lượng Comment", e);
        } finally {
            em.close();
        }
    }

    /**
     * Tìm tất cả các comment của một người dùng dựa trên ID
     */
    @Override
    public List<Comment> findByUserId(String userId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT c FROM Comment c WHERE c.user.id = :userId ORDER BY c.createdDate DESC";
            TypedQuery<Comment> query = em.createQuery(jpql, Comment.class)
                    .setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm Comment với userId: " + userId, e);
        } finally {
            em.close();
        }
    }

    /**
     * Tìm tất cả các comment của một video dựa trên ID
     */
    @Override
    public List<Comment> findByVideoId(String videoId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT c FROM Comment c WHERE c.video.id = :videoId ORDER BY c.createdDate DESC";
            TypedQuery<Comment> query = em.createQuery(jpql, Comment.class)
                    .setParameter("videoId", videoId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm Comment với videoId: " + videoId, e);
        } finally {
            em.close();
        }
    }

    /**
     * Đếm số lượng comment của một video
     */
    @Override
    public long countByVideoId(String videoId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(c) FROM Comment c WHERE c.video.id = :videoId";
            return em.createQuery(jpql, Long.class)
                    .setParameter("videoId", videoId)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm số lượng Comment với videoId: " + videoId, e);
        } finally {
            em.close();
        }
    }

    /**
     * Lấy danh sách phân trang các comment
     */
    @Override
    public List<Comment> pages(int page, int size) {
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT c FROM Comment c ORDER BY c.createdDate DESC";
            return em.createQuery(jpql, Comment.class)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy dữ liệu phân trang", e);
        } finally {
            em.close();
        }
    }
}

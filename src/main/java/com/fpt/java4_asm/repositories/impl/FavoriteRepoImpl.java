package com.fpt.java4_asm.repositories.impl;

import com.fpt.java4_asm.config.HibernateUtil;
import com.fpt.java4_asm.models.entities.Favorite;
import com.fpt.java4_asm.repositories.FavoriteRepo;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;


/**
 * Triển khai cụ thể của FavoriteRepo
 * 
 * Lớp này chịu trách nhiệm tương tác với cơ sở dữ liệu cho các thao tác liên quan đến Favorite
 * Sử dụng JPA/Hibernate để thực hiện các truy vấn
 */
public class FavoriteRepoImpl implements FavoriteRepo {
    
    private EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }

    /**
     * Lưu một đối tượng Favorite vào cơ sở dữ liệu
     */
    @Override
    public Favorite save(Favorite entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Đối tượng Favorite không được để trống");
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
            throw new RuntimeException("Lỗi khi lưu Favorite: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Cập nhật thông tin của một đối tượng Favorite đã tồn tại
     */
    @Override
    public Optional<Favorite> update(Favorite entity) {
        if (entity == null || entity.getId() == null) {
            return Optional.empty();
        }
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Favorite updatedEntity = em.merge(entity);
            em.getTransaction().commit();
            return Optional.of(updatedEntity);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Lỗi khi cập nhật Favorite: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Tìm kiếm một đối tượng Favorite theo ID
     */
    @Override
    public Optional<Favorite> findById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        EntityManager em = getEntityManager();
        try {
            Favorite entity = em.find(Favorite.class, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm Favorite với ID: " + id, e);
        } finally {
            em.close();
        }
    }

    /**
     * Lấy tất cả các đối tượng Favorite từ cơ sở dữ liệu
     */
    @Override
    public List<Favorite> findAll() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT f FROM Favorite f";
            TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách Favorite", e);
        } finally {
            em.close();
        }
    }

    /**
     * Xóa một đối tượng Favorite dựa trên ID
     */
    @Override
    public boolean deleteById(Integer id) {
        if (id == null) {
            return false;
        }
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Favorite entity = em.find(Favorite.class, id);
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
            throw new RuntimeException("Lỗi khi xóa Favorite với ID: " + id, e);
        } finally {
            em.close();
        }
    }

    /**
     * Kiểm tra sự tồn tại của một đối tượng Favorite dựa trên ID
     */
    @Override
    public boolean existsById(Integer id) {
        if (id == null) {
            return false;
        }
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(f) > 0 FROM Favorite f WHERE f.id = :id";
            return em.createQuery(jpql, Boolean.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi kiểm tra tồn tại Favorite với ID: " + id, e);
        } finally {
            em.close();
        }
    }

    /**
     * Đếm tổng số lượng đối tượng Favorite trong cơ sở dữ liệu
     */
    @Override
    public long count() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(f) FROM Favorite f";
            return em.createQuery(jpql, Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm số lượng Favorite", e);
        } finally {
            em.close();
        }
    }

    /**
     * Kiểm tra xem một người dùng đã thích một video cụ thể chưa
     */
    @Override
    public boolean existsByUserAndVideo(String userId, String videoId) {
        if (userId == null || videoId == null) {
            return false;
        }
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(f) > 0 FROM Favorite f WHERE f.user.id = :userId AND f.video.id = :videoId";
            return em.createQuery(jpql, Boolean.class)
                    .setParameter("userId", userId)
                    .setParameter("videoId", videoId)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi kiểm tra tồn tại Favorite với userId: " + userId + " và videoId: " + videoId, e);
        } finally {
            em.close();
        }
    }

    /**
     * Tìm tất cả các mục yêu thích của một người dùng dựa trên ID
     */
    @Override
    public List<Favorite> findByUserId(String userId){
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT f FROM Favorite f WHERE f.user.id = :userId";
            TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class)
                    .setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm Favorite với userId: " + userId, e);
        } finally {
            em.close();
        }
    };

    /**
     * Lấy danh sách phân trang các mục yêu thích
     */
    @Override
    public List<Favorite> pages(int page, int size) {
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT f FROM Favorite f ORDER BY f.likeDate DESC";
            return em.createQuery(jpql, Favorite.class)
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
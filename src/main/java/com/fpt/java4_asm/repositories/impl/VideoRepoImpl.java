package com.fpt.java4_asm.repositories.impl;

import com.fpt.java4_asm.config.HibernateUtil;
import com.fpt.java4_asm.models.entities.Video;
import com.fpt.java4_asm.repositories.VideoRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * Implementation của VideoRepo
 */
public class VideoRepoImpl implements VideoRepo {
    
    private EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }

    @Override
    public Video save(Video entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi lưu Video: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Video> update(Video entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Video updated = em.merge(entity);
            em.getTransaction().commit();
            return Optional.of(updated);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi cập nhật Video: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Video> findById(String id) {
        EntityManager em = getEntityManager();
        try {
            return Optional.ofNullable(em.find(Video.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT v FROM Video v", Video.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteById(String id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Video entity = em.find(Video.class, id);
            if (entity != null) {
                em.remove(entity);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi xóa Video: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsById(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(v) > 0 FROM Video v WHERE v.id = :id", Boolean.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(v) FROM Video v", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> findByUserId(String userId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT v FROM Video v WHERE v.user.id = :userId", Video.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> searchByTitle(String keyword) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT v FROM Video v WHERE LOWER(v.title) LIKE LOWER(:keyword)", Video.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT v FROM Video v WHERE v.active = true", Video.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> pages(int page, int size) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT v FROM Video v ORDER BY v.createdDate DESC", Video.class)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void incrementViews(String videoId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("UPDATE Video v SET v.views = v.views + 1 WHERE v.id = :id")
                    .setParameter("id", videoId)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi tăng lượt xem: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}

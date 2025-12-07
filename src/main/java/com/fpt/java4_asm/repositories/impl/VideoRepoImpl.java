package com.fpt.java4_asm.repositories.impl;

import com.fpt.java4_asm.config.HibernateUtil;
import com.fpt.java4_asm.models.entities.Video;
import com.fpt.java4_asm.repositories.VideoRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * Triển khai cụ thể của VideoRepo
 * Sử dụng JPA/Hibernate để thao tác với database
 * 
 * Lưu ý: Mỗi method tạo EntityManager mới và đóng sau khi sử dụng
 * để tránh memory leak và đảm bảo dữ liệu luôn mới nhất
 * 
 * @author Java4 ASM
 */
public class VideoRepoImpl implements VideoRepo {
    
    /**
     * Tạo EntityManager mới từ HibernateUtil
     * @return EntityManager instance
     */
    private EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }

    /**
     * Lưu video mới vào database
     * 
     * @param entity Video entity cần lưu
     * @return Video đã được lưu
     * @throws RuntimeException nếu có lỗi khi lưu
     */
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

    /**
     * Cập nhật video trong database
     * 
     * @param entity Video entity cần cập nhật
     * @return Optional chứa Video đã cập nhật
     */
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

    /**
     * Tìm video theo ID
     * 
     * @param id ID của video
     * @return Optional chứa Video nếu tìm thấy
     */
    @Override
    public Optional<Video> findById(String id) {
        EntityManager em = getEntityManager();
        try {
            return Optional.ofNullable(em.find(Video.class, id));
        } finally {
            em.close();
        }
    }

    /**
     * Lấy tất cả video
     * 
     * @return Danh sách tất cả video
     */
    @Override
    public List<Video> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT v FROM Video v", Video.class).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Xóa video theo ID
     * 
     * @param id ID của video cần xóa
     * @return true nếu xóa thành công, false nếu không tìm thấy
     */
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

    /**
     * Kiểm tra video có tồn tại theo ID
     * 
     * @param id ID của video
     * @return true nếu tồn tại, false nếu không
     */
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

    /**
     * Đếm tổng số video
     * 
     * @return Tổng số video trong database
     */
    @Override
    public long count() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(v) FROM Video v", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Tìm video theo ID người dùng
     * 
     * @param userId ID của người dùng
     * @return Danh sách video của người dùng
     */
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

    /**
     * Tìm kiếm video theo tiêu đề (không phân biệt hoa thường)
     * 
     * @param keyword Từ khóa tìm kiếm
     * @return Danh sách video có tiêu đề chứa từ khóa
     */
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

    /**
     * Lấy tất cả video đang hoạt động (active = true)
     * 
     * @return Danh sách video đang hoạt động
     */
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

    /**
     * Lấy danh sách video theo phân trang
     * Sắp xếp theo ngày tạo giảm dần (mới nhất trước)
     * 
     * @param page Số trang (0-based)
     * @param size Số lượng bản ghi mỗi trang
     * @return Danh sách video trong trang
     */
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

    /**
     * Tăng lượt xem cho video
     * 
     * @param videoId ID của video cần tăng lượt xem
     */
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

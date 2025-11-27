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
    EntityManager em = HibernateUtil.getEntityManager();

    /**
     * Lưu một đối tượng Favorite vào cơ sở dữ liệu
     *
     * @param entity Đối tượng Favorite cần lưu
     * @return Đối tượng đã được lưu với ID được tạo tự động
     * @throws IllegalArgumentException Nếu đối tượng đầu vào là null
     * @throws RuntimeException Nếu có lỗi xảy ra trong quá trình lưu
     * 
     * Mô tả:
     * - Thực hiện lưu mới một đối tượng Favorite vào CSDL
     * - Sử dụng transaction để đảm bảo tính toàn vẹn dữ liệu
     * - Nếu có lỗi, thực hiện rollback transaction
     */
    @Override
    public Favorite save(Favorite entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Đối tượng Favorite không được để trống");
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
            throw new RuntimeException("Lỗi khi lưu Favorite: " + e.getMessage(), e);
        }
    }

    /**
     * Cập nhật thông tin của một đối tượng Favorite đã tồn tại
     *
     * @param entity Đối tượng Favorite chứa thông tin cập nhật
     * @return Optional chứa đối tượng đã được cập nhật nếu tìm thấy, rỗng nếu không
     * @throws RuntimeException Nếu có lỗi xảy ra trong quá trình cập nhật
     * 
     * Mô tả:
     * - Cập nhật thông tin của đối tượng Favorite dựa trên ID
     * - Sử dụng transaction để đảm bảo tính toàn vẹn dữ liệu
     * - Trả về Optional rỗng nếu đối tượng hoặc ID là null
     */
    @Override
    public Optional<Favorite> update(Favorite entity) {
        if (entity == null || entity.getId() == null) {
            return Optional.empty();
        }

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
        }
    }

    /**
     * Tìm kiếm một đối tượng Favorite theo ID
     *
     * @param id ID của đối tượng Favorite cần tìm
     * @return Optional chứa đối tượng tìm thấy, hoặc rỗng nếu không tìm thấy
     * @throws RuntimeException Nếu có lỗi xảy ra trong quá trình tìm kiếm
     * 
     * Mô tả:
     * - Tìm kiếm đối tượng Favorite trong CSDL dựa trên ID
     * - Trả về Optional rỗng nếu ID là null hoặc không tìm thấy
     * - Sử dụng JPA EntityManager để thực hiện tìm kiếm
     */
    @Override
    public Optional<Favorite> findById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }

        try {
            Favorite entity = em.find(Favorite.class, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm Favorite với ID: " + id, e);
        }
    }

    /**
     * Lấy tất cả các đối tượng Favorite từ cơ sở dữ liệu
     *
     * @return Danh sách tất cả các đối tượng Favorite
     * @throws RuntimeException Nếu có lỗi xảy ra trong quá trình lấy dữ liệu
     * 
     * Mô tả:
     * - Thực hiện truy vấn JPQL để lấy toàn bộ dữ liệu từ bảng Favorite
     * - Trả về danh sách rỗng nếu không có dữ liệu
     * - Có thể ảnh hưởng đến hiệu năng nếu dữ liệu lớn
     */
    @Override
    public List<Favorite> findAll() {
        try {
            String jpql = "SELECT f FROM Favorite f";
            TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách Favorite", e);
        }
    }

    /**
     * Xóa một đối tượng Favorite dựa trên ID
     *
     * @param id ID của đối tượng Favorite cần xóa
     * @return true nếu xóa thành công, false nếu không tìm thấy đối tượng
     * @throws RuntimeException Nếu có lỗi xảy ra trong quá trình xóa
     * 
     * Mô tả:
     * - Tìm và xóa đối tượng Favorite dựa trên ID
     * - Sử dụng transaction để đảm bảo tính toàn vẹn dữ liệu
     * - Trả về false nếu ID là null hoặc không tìm thấy đối tượng
     */
    @Override
    public boolean deleteById(Integer id) {
        if (id == null) {
            return false;
        }

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
        }
    }

    /**
     * Kiểm tra sự tồn tại của một đối tượng Favorite dựa trên ID
     *
     * @param id ID cần kiểm tra
     * @return true nếu tồn tại, false nếu ngược lại
     * @throws RuntimeException Nếu có lỗi xảy ra trong quá trình kiểm tra
     * 
     * Mô tả:
     * - Kiểm tra xem có bản ghi nào trong bảng Favorite có ID trùng khớp không
     * - Sử dụng JPQL với COUNT để tối ưu hiệu năng
     * - Trả về false nếu ID là null
     */
    @Override
    public boolean existsById(Integer id) {
        if (id == null) {
            return false;
        }

        try {
            String jpql = "SELECT COUNT(f) > 0 FROM Favorite f WHERE f.id = :id";
            return em.createQuery(jpql, Boolean.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi kiểm tra tồn tại Favorite với ID: " + id, e);
        }
    }

    /**
     * Đếm tổng số lượng đối tượng Favorite trong cơ sở dữ liệu
     *
     * @return Tổng số lượng đối tượng Favorite
     * @throws RuntimeException Nếu có lỗi xảy ra trong quá trình đếm
     * 
     * Mô tả:
     * - Thực hiện đếm tổng số bản ghi trong bảng Favorite
     * - Sử dụng JPQL với hàm COUNT để tối ưu hiệu năng
     * - Trả về 0 nếu không có bản ghi nào
     */
    @Override
    public long count() {
        try {
            String jpql = "SELECT COUNT(f) FROM Favorite f";
            return em.createQuery(jpql, Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm số lượng Favorite", e);
        }
    }

    /**
     * Kiểm tra xem một người dùng đã thích một video cụ thể chưa
     * 
     * @param userId ID của người dùng cần kiểm tra
     * @param videoId ID của video cần kiểm tra
     * @return true nếu đã tồn tại bản ghi yêu thích, ngược lại trả về false
     * @throws RuntimeException nếu có lỗi xảy ra khi thực hiện truy vấn
     * 
     * Mô tả:
     * - Phương thức này kiểm tra xem có bản ghi nào trong bảng Favorite
     *   thỏa mãn cả userId và videoId được cung cấp hay không
     * - Sử dụng JPQL để thực hiện truy vấn
     * - Xử lý trường hợp tham số đầu vào là null
     */
    @Override
    public boolean existsByUserAndVideo(String userId, String videoId) {
        if (userId == null || videoId == null) {
            return false;
        }

        try {
            String jpql = "SELECT COUNT(f) > 0 FROM Favorite f WHERE f.user.id = :userId AND f.video.id = :videoId";
            return em.createQuery(jpql, Boolean.class)
                    .setParameter("userId", userId)
                    .setParameter("videoId", videoId)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi kiểm tra tồn tại Favorite với userId: " + userId + " và videoId: " + videoId, e);
        }
    }

    /**
     * Tìm tất cả các mục yêu thích của một người dùng dựa trên ID
     * 
     * @param userId ID của người dùng cần tìm kiếm
     * @return Danh sách các đối tượng Favorite của người dùng
     * @throws RuntimeException nếu có lỗi xảy ra khi thực hiện truy vấn
     * 
     * Mô tả:
     * - Lấy tất cả các bản ghi yêu thích của một người dùng cụ thể
     * - Sử dụng JPQL để thực hiện truy vấn với tham số
     * - Trả về danh sách rỗng nếu không tìm thấy kết quả
     * - Ném ngoại lệ nếu có lỗi khi thực hiện truy vấn
     */
    @Override
    public List<Favorite> findByUserId(String userId){
        try {
            String jpql = "SELECT f FROM Favorite f WHERE f.user.id = :userId";
            TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class)
                    .setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm Favorite với userId: " + userId, e);
        }
    };

    /**
     * Lấy danh sách phân trang các mục yêu thích
     * 
     * @param page Số trang cần lấy (bắt đầu từ 0)
     * @param size Số lượng bản ghi trên mỗi trang
     * @return Danh sách các đối tượng Favorite thuộc trang được yêu cầu
     * @throws IllegalArgumentException Nếu page < 0 hoặc size <= 0
     * @throws RuntimeException Nếu có lỗi xảy ra khi thực hiện truy vấn
     * 
     * Mô tả:
     * - Thực hiện phân trang dữ liệu từ bảng Favorite
     * - Sử dụng JPQL với setFirstResult và setMaxResults để phân trang
     * - Tự động điều chỉnh nếu page hoặc size không hợp lệ
     * - Trả về danh sách rỗng nếu không có dữ liệu
     * 
     * Ví dụ:
     * - pages(0, 10) trả về 10 bản ghi đầu tiên
     * - pages(1, 10) trả về 10 bản ghi tiếp theo (từ bản ghi thứ 11-20)
     */
    @Override
    public List<Favorite> pages(int page, int size) {
        // Kiểm tra tham số đầu vào
        if (page < 0) {
            page = 0; // Mặc định về trang đầu tiên nếu số trang âm
        }
        if (size <= 0) {
            size = 10; // Mặc định kích thước trang là 10 nếu không hợp lệ
        }

        try {
            // Tạo câu truy vấn JPQL
            String jpql = "SELECT f FROM Favorite f ORDER BY f.likeDate DESC";
            
            // Thực hiện phân trang
            return em.createQuery(jpql, Favorite.class)
                    .setFirstResult(page * size) // Vị trí bắt đầu lấy dữ liệu
                    .setMaxResults(size)         // Số lượng bản ghi tối đa
                    .getResultList();
        } catch (Exception e) {
            // Ghi log lỗi và ném ngoại lệ
            String errorMsg = String.format("Lỗi khi lấy dữ liệu phân trang (trang: %d, kích thước: %d)", page, size);
            System.err.println(errorMsg + ": " + e.getMessage());
            throw new RuntimeException(errorMsg, e);
        }
    }
}
package com.fpt.java4_asm.repositories.impl;

import com.fpt.java4_asm.config.HibernateUtil;
import com.fpt.java4_asm.models.entities.Favorite;
import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.repositories.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
// Class implement UserRepo interface, cung cấp các implementation cho các method
// Sử dụng EntityManager từ Hibernate để thao tác với database
public class UserRepoImpl implements UserRepo {
    private final EntityManager em = HibernateUtil.getEntityManager();
    // EntityManager: dùng để quản lý entity, thực hiện các thao tác CRUD với DB
    // final: không thể thay đổi reference sau khi khởi tạo (bảo vệ object)

    // Lưu (tạo) user mới vào database
    @Override
    public User save(User entity) {
        if(entity == null) throw new IllegalArgumentException("User không được để trống");
        try{
            // begin: bắt đầu transaction (giao dịch)
            em.getTransaction().begin();
            // persist: thêm entity vào context và đánh dấu để insert vào DB
            em.persist(entity);
            // commit: xác nhận transaction, thực thi thay đổi
            em.getTransaction().commit();
            return entity;
        }catch(Exception e){
            // Nếu có lỗi thì rollback (hoàn tác) các thay đổi
            if(em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi lưu User: " + e.getMessage(), e);
        }
    }

    // Cập nhật user đã tồn tại trong database
    @Override
    public Optional<User> update(User entity) {
        if(entity == null || entity.getId() == null) return Optional.empty();
        try{
            em.getTransaction().begin();
            // merge: cập nhật entity (nếu entity đã tồn tại)
            User updatedEntity = em.merge(entity);
            em.getTransaction().commit();
            return Optional.of(updatedEntity);
        }catch(Exception e){
            if(em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi cập nhật User: " + e.getMessage(),e);
        }
    }

    // Tìm user theo ID
    @Override
    public Optional<User> findById(String id) {
        if(id == null || id.trim().isEmpty()) return Optional.empty();
        try{
            // find: tìm entity theo class và primary key
            User entity = em.find(User.class, id);
            // ofNullable: nếu entity null thì trả về Optional.empty(), ngược lại trả về Optional chứa entity
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm User với ID: " + id, e);
        }
    }

    // Lấy tất cả user từ database
    @Override
    public List<User> findAll() {
        try {
            String jpql = "SELECT u FROM User u";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            return query.getResultList(); // Trả về danh sách (có thể rỗng nếu ko có user)
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách User", e);
        }
    }

    // Xóa user theo ID
    @Override
    public boolean deleteById(String id) {
        if(id == null || id.trim().isEmpty()) return false;
        try{
            em.getTransaction().begin();
            // Tìm user theo ID
            User entity = em.find(User.class, id);
            if(entity != null){
                // remove: xóa entity khỏi DB
                em.remove(entity);
                em.getTransaction().commit();
                return true; // Xóa thành công
            }
            em.getTransaction().rollback();
            return false; // User không tồn tại
        }catch(Exception e){
            if(em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi xóa User với ID: " + id, e);
        }
    }

    // Kiểm tra user có tồn tại theo ID
    @Override
    public boolean existsById(String id) {
       if(id == null || id.trim().isEmpty()) return false;
       try{
            // JPQL: SELECT COUNT(u) > 0 sẽ trả về true/false
            // COUNT(u) > 0: nếu số lượng > 0 thì true, ngược lại false
            String jpql = "SELECT COUNT(u) > 0 FROM User u WHERE u.id = :id";
            return em.createQuery(jpql, Boolean.class)
                    .setParameter("id", id)
                    .getSingleResult(); // getSingleResult(): lấy 1 kết quả duy nhất
       }catch(Exception e){
           throw new RuntimeException("Lỗi khi kiểm tra tồn tại User với ID: " + id, e);
       }
    }

    // Đếm tổng số user trong database
    @Override
    public long count() {
        try {
            // SELECT COUNT(u): đếm số lượng user
            String jpql = "SELECT COUNT(u) FROM User u";
            return em.createQuery(jpql, Long.class)
                    .getSingleResult(); // Trả về 1 giá trị long là tổng số user
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm số lượng User", e);
        }
    }

    // Lấy thông tin user theo email
    @Override
    public Optional<User> findByEmail(String email){
        if ((email == null) || email.trim().isEmpty()) return Optional.empty();
        try{
            // Tìm user theo email bằng JPQL query
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            User entity = em.createQuery(jpql, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.ofNullable(entity);
        } catch (jakarta.persistence.NoResultException e) {
            return Optional.empty(); // Không tìm thấy user với email này
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm User với Email: " + email, e);
        }
    }

    @Override
    public List<User> pages (int page, int size) {
        // Kiểm tra tham số đầu vào
        if (page < 0) {
            page = 0; // Mặc định về trang đầu tiên nếu số trang âm
        }
        if (size <= 0) {
            size = 10; // Mặc định kích thước trang là 10 nếu không hợp lệ
        }

        try {
            // Tạo câu truy vấn JPQL
            String jpql = "SELECT u FROM User u ORDER BY u.createdDate DESC";

            // Thực hiện phân trang
            return em.createQuery(jpql, User.class)
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

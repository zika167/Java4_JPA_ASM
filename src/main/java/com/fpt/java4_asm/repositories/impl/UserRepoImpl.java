package com.fpt.java4_asm.repositories.impl;

import com.fpt.java4_asm.config.HibernateUtil;
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
    // Tìm user theo email và password (dùng cho login)
    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        // Kiểm tra email và password không được null hoặc rỗng
        if(email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return Optional.empty(); // Trả về Optional rỗng (chứa nothing)
        }
        try{
            // JPQL: Java Persistence Query Language (ngôn ngữ query dùng với JPA)
            // :email và :password là placeholder (sẽ được thay thế sau)
            String jpql = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            // Thiết lập giá trị cho các tham số
            query.setParameter("email", email);
            query.setParameter("password", password);
            // Thực thi query và lấy danh sách kết quả
            List<User> results = query.getResultList();
            // Nếu danh sách trống thì trả về Optional.empty(), ngược lại trả về user đầu tiên
            return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
        }catch(Exception e){
            throw new RuntimeException("Lỗi khi login: " + e.getMessage(), e);
        }
    }

    // Tìm user theo email
    @Override
    public Optional<User> findByEmail(String email) {
        // Kiểm tra email không được null hoặc rỗng
        if(email == null || email.trim().isEmpty()) return Optional.empty();
        try{
            String jpql = "SELECT e FROM User e WHERE e.email = :email";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("email", email);
            List<User> emails_User = query.getResultList();
            return emails_User.isEmpty() ? Optional.empty() : Optional.of(emails_User.getFirst());
        }catch(Exception e){
            throw new RuntimeException("Lỗi khi tìm user theo email: " + email,e);
        }
    }

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
}

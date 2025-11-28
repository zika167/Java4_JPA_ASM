package com.fpt.java4_asm.repositories.impl;

import com.fpt.java4_asm.config.HibernateUtil;
import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.repositories.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class UserRepoImpl implements UserRepo {
    private final EntityManager em = HibernateUtil.getEntityManager();
    // Tạo 1 EntityManager để quản lí, để final để ko thể bị lôi ra dùng nữa
    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        if(email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return Optional.empty(); // Check email và pass xem có null hay rỗng ko, có thì trả về kiểu Optinal, 1 libary tiện ích của Java để cho dữ liệu an toàn
        }
        try{
            String jpql = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password";
            // Jpql , viết query truy vấn lấy email và password từ User nè, Cái này nếu viết xàm là nó chậm đó ,chứ ko phải web lab đâu
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            // Tạo 1 query dựa trên câu truy vấn đó
            query.setParameter("email", email);
            query.setParameter("password", password);
            List<User> results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
            // Check điều kiện , kết quả là Rỗng thì quăng kiểu dữ liệu an toàn, ngược lại là trả về cái đầu tiên (get First)
        }catch(Exception e){
            throw new RuntimeException("Lỗi khi login: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if(email == null || email.trim().isEmpty()) return Optional.empty(); // Check dữ liệu
        // tương tự như cái ở trên ha
        try{
            String jpql = "SELECT e From User e WHERE e.email = :email";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("email", email);
            List<User> emails_User = query.getResultList();
            return emails_User.isEmpty() ? Optional.empty() : Optional.of(emails_User.getFirst());
        }catch(Exception e){
            throw new RuntimeException("Lỗi khi tìm Email theo email: " + email,e);
        }
    }

    @Override
    public User save(User entity) {
        if(entity == null) throw new IllegalArgumentException("User không được để trống"); // Cái entity thì ko trả về kiểu an toàn được nên quăng lỗi ra luôn
        try{
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        }catch(Exception e){
            if(em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi lưu User: " + e.getMessage(), e);
        }
        // Cái này là code trong lab 2 3 nha, ai ko hiểu thì xem lại lab 2 3
    }

    @Override
    public Optional<User> update(User entity) {
        if(entity == null || entity.getId() == null) return Optional.empty(); // check dữ liệu và trả về kiểu an toàn
        try{
            // code trong lab
            em.getTransaction().begin();
            User updatedEntity = em.merge(entity);
            em.getTransaction().commit();
            return Optional.of(updatedEntity);
        }catch(Exception e){
            if(em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi cập nhật User" + e.getMessage(),e);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        if(id == null || id.trim().isEmpty()) return Optional.empty();
        try{
            User entity = em.find(User.class, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm User với ID: " + id, e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            String jpql = "SELECT u FROM User u";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách User", e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        if(id == null || id.trim().isEmpty()) return false;
        try{
            // code lab được cải thiện
            em.getTransaction().begin();
            User entity = em.find(User.class, id);
            if(entity != null){
                em.remove(entity);
                em.getTransaction().commit();
                return true; // do hàm boolean nên chỉ true false nha
            }
            em.getTransaction().rollback();
            return false;
        }catch(Exception e){
            if(em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi xóa User với Id: " + id, e);
            // bắt lỗi roll back lại
        }
    }

    @Override
    public boolean existsById(String id) {
        // Hàm kiểm tra Id có tồn tại ko
       if(id == null || id.trim().isEmpty()) return false;
       try{
            String jpql = "SELECT COUNT(u) > 0 FROM User u WHERE u.id = :id";
            return em.createQuery(jpql, Boolean.class)
                    .setParameter("id", id)
                    .getSingleResult();
            // Cái  .  . là viết theo kiểu java 8 trở lên á, còn ko mn có thể viết em.setParameter vẫn được nha, tham khao trong lab
       }catch(Exception e){
           throw new RuntimeException("Lỗi khi kiểm tra tồn tại User với ID: " + id, e);
       }
    }

    @Override
    public long count() {
        // hàm đếm số lượng, trả về kiểu long, thật ra thấy float cho nhẹ do tụi mình cũng chẳng đến mấy tỉ dữ liệu, nhưng thôi kệ, để double cho đỡ sai số
        try {
            String jpql = "SELECT COUNT(u) FROM User u";
            return em.createQuery(jpql, Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm số lượng User", e);
        }
    }


        
}

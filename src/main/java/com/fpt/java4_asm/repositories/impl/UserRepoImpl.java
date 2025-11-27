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

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        if(email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return Optional.empty();
        }
        try{
            String jpql = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            List<User> results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
        }catch(Exception e){
            throw new RuntimeException("Lỗi khi login: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if(email == null || email.trim().isEmpty()) return Optional.empty();
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
        if(entity == null) throw new IllegalArgumentException("User không được để trống");
        try{
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        }catch(Exception e){
            if(em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi lưu User: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> update(User entity) {
        if(entity == null || entity.getId() == null) return Optional.empty();
        try{
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
            em.getTransaction().begin();
            User entity = em.find(User.class, id);
            if(entity != null){
                em.remove(entity);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        }catch(Exception e){
            if(em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi xóa User với Id: " + id, e);
        }
    }

    @Override
    public boolean existsById(String id) {
       if(id == null || id.trim().isEmpty()) return false;
       try{
            String jpql = "SELECT COUNT(u) > 0 FROM User u WHERE u.id = :id";
            return em.createQuery(jpql, Boolean.class)
                    .setParameter("id", id)
                    .getSingleResult();
       }catch(Exception e){
           throw new RuntimeException("Lỗi khi kiểm tra tồn tại User với ID: " + id, e);
       }
    }

    @Override
    public long count() {
        try {
            String jpql = "SELECT COUNT(u) FROM User u";
            return em.createQuery(jpql, Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm số lượng User", e);
        }
    }


        
}

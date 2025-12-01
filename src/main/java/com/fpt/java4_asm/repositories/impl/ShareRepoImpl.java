package com.fpt.java4_asm.repositories.impl;

import com.fpt.java4_asm.config.HibernateUtil;
import com.fpt.java4_asm.models.entities.Share;
import com.fpt.java4_asm.repositories.ShareRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ShareRepoImpl implements ShareRepo {
	private final EntityManager em = HibernateUtil.getEntityManager();

	@Override
	public Share save(Share entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Share entity is null");
		}
		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			throw new RuntimeException("Failed to save Share: " + e.getMessage(), e);
		}
	}

	@Override
	public Optional<Share> update(Share entity) {
		if (entity == null || entity.getId() == null) return Optional.empty();
		try {
			em.getTransaction().begin();
			Share updated = em.merge(entity);
			em.getTransaction().commit();
			return Optional.of(updated);
		} catch (Exception e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			throw new RuntimeException("Failed to update Share: " + e.getMessage(), e);
		}
	}

	@Override
	public Optional<Share> findById(String id) {
		if (id == null) return Optional.empty();
		try {
			Share s = em.find(Share.class, id);
			return Optional.ofNullable(s);
		} catch (Exception e) {
			throw new RuntimeException("Failed to find Share by id: " + id, e);
		}
	}

	@Override
	public List<Share> findAll() {
		try {
			TypedQuery<Share> q = em.createQuery("SELECT s FROM Share s", Share.class);
			return q.getResultList();
		} catch (Exception e) {
			throw new RuntimeException("Failed to get all Shares", e);
		}
	}

	@Override
	public boolean deleteById(String id) {
		if (id == null) return false;
		try {
			em.getTransaction().begin();
			Share s = em.find(Share.class, id);
			if (s != null) {
				em.remove(s);
				em.getTransaction().commit();
				return true;
			}
			em.getTransaction().rollback();
			return false;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			throw new RuntimeException("Failed to delete Share: " + e.getMessage(), e);
		}
	}

	@Override
	public boolean existsById(String id) {
		if (id == null) return false;
		try {
			String jpql = "SELECT COUNT(s) > 0 FROM Share s WHERE s.id = :id";
			return em.createQuery(jpql, Boolean.class).setParameter("id", id).getSingleResult();
		} catch (Exception e) {
			throw new RuntimeException("Failed to check exists Share: " + e.getMessage(), e);
		}
	}

	@Override
	public long count() {
		try {
			String jpql = "SELECT COUNT(s) FROM Share s";
			return em.createQuery(jpql, Long.class).getSingleResult();
		} catch (Exception e) {
			throw new RuntimeException("Failed to count Shares", e);
		}
	}

	@Override
	public List<Share> findByVideoId(String videoId) {
		try {
			TypedQuery<Share> q = em.createQuery("SELECT s FROM Share s WHERE s.video.id = :videoId ORDER BY s.shareDate DESC", Share.class)
					.setParameter("videoId", videoId);
			return q.getResultList();
		} catch (Exception e) {
			throw new RuntimeException("Failed to find Shares by videoId: " + videoId, e);
		}
	}

	@Override
	public List<Share> findByUserId(String userId) {
		try {
			TypedQuery<Share> q = em.createQuery("SELECT s FROM Share s WHERE s.user.id = :userId ORDER BY s.shareDate DESC", Share.class)
					.setParameter("userId", userId);
			return q.getResultList();
		} catch (Exception e) {
			throw new RuntimeException("Failed to find Shares by userId: " + userId, e);
		}
	}
}

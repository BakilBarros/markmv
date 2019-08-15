package br.com.markmv.model.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.markmv.model.entidades.Sala;

public class SalaRepository {

	private EntityManager em;

	public SalaRepository(EntityManager em) {
		this.em = em;
	}

	public void salvar(Sala sala) {
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		em.persist(sala);

		tx.commit();
	}

	public void alterar(Sala sala) {
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		em.merge(sala);

		tx.commit();
	}

	public void remover(Sala sala) {

		EntityTransaction tx = em.getTransaction();

		tx.begin();

		em.remove(sala);

		tx.commit();
	}

	public Sala buscar(Integer id) {
		return em.find(Sala.class, id);
	}

	public List<Sala> todos() {
		return em.createQuery("From Sala").getResultList();
	}
}

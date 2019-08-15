package br.com.markmv.model.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.markmv.model.entidades.Usuario;

public class UsuarioRepository {

	private EntityManager em;

	public UsuarioRepository(EntityManager em) {
		this.em = em;
	}

	public void salvar(Usuario usuario) {
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		em.persist(usuario);

		tx.commit();

	}

	public void alterar(Usuario usuario) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		em.merge(usuario);

		tx.commit();
	}

	public void remover(Usuario usuario) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		em.remove(usuario);

		tx.commit();
	}

	public Usuario buscar(String matricula) {
		return em.find(Usuario.class, matricula);
	}
	
	public Usuario buscar(String matricula, String email){
		Session session = em.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Usuario.class);
		Criterion matriculaIgual = Restrictions.eq("matricula", matricula);
		Criterion emailIgual = Restrictions.eq("email", email);
		
		criteria.add(Restrictions.or(matriculaIgual, emailIgual));
		
		return (Usuario) criteria.uniqueResult();
	}

	public Usuario buscar(Usuario usuario) {
		Session session = em.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("email", usuario.getEmail()).ignoreCase());
		criteria.add(Restrictions.eq("senha", usuario.getSenha()));
		return (Usuario) criteria.uniqueResult();
	}
}

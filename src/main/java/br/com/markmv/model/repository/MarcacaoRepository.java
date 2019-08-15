package br.com.markmv.model.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.markmv.model.entidades.Marcacao;

public class MarcacaoRepository {

	private EntityManager em;

	public MarcacaoRepository(EntityManager em) {
		this.em = em;
	}

	public void salvar(Marcacao agendamento) {
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		em.persist(agendamento);

		tx.commit();
	}

	public void alterar(Marcacao agendamento) {
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		em.merge(agendamento);

		tx.commit();
	}

	public void remover(Marcacao marcacao) {

		EntityTransaction tx = em.getTransaction();

		tx.begin();

		Query query = em.createQuery("DELETE Marcacao m WHERE m.id = " + marcacao.getId());
		query.executeUpdate();

		tx.commit();
	}

	public Marcacao buscar(Integer id) {
		return em.find(Marcacao.class, id);
	}

	public List<Marcacao> todos() {
		return em.createQuery("From Marcacao").getResultList();
	}

	public List<Marcacao> todos(Marcacao marcacao) {
		Query query = em.createQuery("SELECT m FROM Marcacao m " + "WHERE m.data = :paramData "

		// VALIDANDO SE A HORA INICIAL OU FINAL SE CHOCA COM
		// ALGUM HORÁRIO MARCADO.

				+ "AND " + "NOT (m.horaFinal = :paramInicio " + "OR m.horaInicial = :paramFim) "

				// ************* //

				// VALIDANDO SE O HORÁRIO PASSADO SE CHOCA
				// COM ALGUM HORÁRIO MARCADO

				+ "AND " + "(m.horaInicial BETWEEN :paramInicio AND :paramFim "
				+ "OR m.horaFinal BETWEEN :paramInicio AND :paramFim )" + validarSala(marcacao));

		// ********** //

		// ATRIBUINDO OS PARAMETROS.
		query.setParameter("paramData", marcacao.getData());
		query.setParameter("paramInicio", marcacao.getHoraInicial());
		query.setParameter("paramFim", marcacao.getHoraFinal());

		return query.getResultList();
	}

	// ACRESCENTA A SALA NA CONSULTA, CASO A MARCAÇÃO PASSADA POR PARÂMETRO
	// CONTENHA UMA SALA PREENCHIDA.
	private String validarSala(Marcacao marcacao) {
		if (marcacao.getSala() != null && marcacao.getSala().isExistente()) {
			return " AND m.sala.id = " + marcacao.getSala().getId();
		}
		return "";
	}

	public List<Marcacao> agendamentosData(Date data) {
		// Session session = em.unwrap(Session.class);
		//
		// Criteria criteria = session.createCriteria(Marcacao.class);
		// criteria.add(Restrictions.eq("dataAgendada", dataAgendamento));
		//
		// return criteria.list();

		return em.createQuery("SELECT a FROM Marcacao a WHERE a.data = :data").setParameter("data", data)
				.getResultList();
	}

	public List<Marcacao> todos(Integer id) {

		Query query = em.createQuery("From Marcacao m where m.user.id = " + id);

		return query.getResultList();
		// return em.createQuery("Select m From Marcacao m where m.user = " +
		// id).getResultList();

	}
}

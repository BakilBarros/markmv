package br.com.markmv.model.negocio;

import java.util.List;

import br.com.markmv.model.entidades.Marcacao;
import br.com.markmv.model.entidades.Sala;
import br.com.markmv.model.negocio.exception.HorarioInvalidoException;
import br.com.markmv.model.negocio.exception.NenhumaSalaDisponivelException;
import br.com.markmv.model.negocio.exception.SalaNaoEncontradaException;
import br.com.markmv.model.repository.MarcacaoRepository;
import br.com.markmv.model.repository.SalaRepository;
import br.com.markmv.util.jpa.JpaUtil;

public class SalaNegocio {

	private MarcacaoRepository marcacaoRepository = new MarcacaoRepository(JpaUtil.getEntityManager());
	private SalaRepository salaRepository = new SalaRepository(JpaUtil.getEntityManager());

	public List<Sala> salasDisponíveis(Marcacao marcacao) throws NenhumaSalaDisponivelException, HorarioInvalidoException {
		MarcacaoNegocio.validarMarcacao(marcacao);
		
		List<Sala> salas = salaRepository.todos();

		List<Marcacao> marcacoes = marcacaoRepository.todos(marcacao);

		for (Marcacao marc : marcacoes) {
			salas.remove(marc.getSala());
		}
		
		if(salas.isEmpty()) {
			throw new NenhumaSalaDisponivelException("Nenhuma sala disponível neste dia no horário desejado.");
		}

		return salas;
	}

	public Sala getSalaPorId(Integer id) throws SalaNaoEncontradaException {
		if (salaRepository.buscar(id) == null) {
			throw new SalaNaoEncontradaException("Não foi possivel encontrar sala.");
		}
		
		return salaRepository.buscar(id);
	}
}

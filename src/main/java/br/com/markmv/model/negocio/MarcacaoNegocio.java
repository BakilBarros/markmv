package br.com.markmv.model.negocio;

import java.util.List;

import br.com.markmv.model.entidades.Marcacao;
import br.com.markmv.model.negocio.exception.AgendamentoNaoEncontradoException;
import br.com.markmv.model.negocio.exception.HorarioInvalidoException;
import br.com.markmv.model.negocio.exception.NaoEPossivelMarcarException;
import br.com.markmv.model.negocio.exception.UsuarioNaoPossuiMarcacoesException;
import br.com.markmv.model.repository.MarcacaoRepository;
import br.com.markmv.util.jpa.JpaUtil;

public class MarcacaoNegocio {

	private MarcacaoRepository marcacaoRepository = new MarcacaoRepository(JpaUtil.getEntityManager());

	// MÉTODO RESPONSÁVEL POR VALIDAR E SALVAR UMA MARCAÇÃO
	public void salvar(Marcacao marcacao) throws NaoEPossivelMarcarException, HorarioInvalidoException {
		List<Marcacao> marcacoes = marcacaoRepository.todos(marcacao);

		if (!marcacoes.isEmpty()) {
			throw new NaoEPossivelMarcarException("Horário ocupado");
		}

		validarMarcacao(marcacao);

		marcacaoRepository.salvar(marcacao);

	}

	// RETORNA UMA MARCAÇÃO PASSANDO O ID DELA
	public Marcacao getMarcacaoPorId(Integer id) throws AgendamentoNaoEncontradoException {

		if (marcacaoRepository.buscar(id) == null) {
			throw new AgendamentoNaoEncontradoException("Não foi possivel encontrar agendamento.");
		}

		Marcacao marcacao = marcacaoRepository.buscar(id);
		marcacao.getUser().setSenha(null);
		
		return marcacao;
	}
	
	
	public static void validarMarcacao(Marcacao marcacao) throws HorarioInvalidoException {
		if (!marcacao.isPossivelMarcar()) {
			throw new HorarioInvalidoException("O horário final não pode ser antes do horário inicial.");
		}
	}

	public void desmarcar(Integer id) {
		Marcacao marcacao = new Marcacao();
		marcacao.setId(id);

		marcacaoRepository.remover(marcacao);
	}

	public List<Marcacao> listar(Integer id) throws UsuarioNaoPossuiMarcacoesException {
		List<Marcacao> marcacoes = marcacaoRepository.todos(id);

		if(marcacoes.isEmpty()){
			throw new UsuarioNaoPossuiMarcacoesException("Você não tem nenhum agendamento marcado.");
		}
		
		for (Marcacao marcacao : marcacoes) {
			marcacao.setUser(null);
		}
		
		return marcacoes;
	}

}

package br.com.markmv.servico;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.markmv.model.entidades.Marcacao;
import br.com.markmv.model.negocio.MarcacaoNegocio;
import br.com.markmv.model.negocio.exception.AgendamentoNaoEncontradoException;
import br.com.markmv.model.negocio.exception.HorarioInvalidoException;
import br.com.markmv.model.negocio.exception.NaoEPossivelMarcarException;
import br.com.markmv.model.negocio.exception.UsuarioNaoPossuiMarcacoesException;
import br.com.markmv.util.rest.RetornoRest;

@Path("/marcacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MarcacaoServico {

	private MarcacaoNegocio marcacaoNegocio = new MarcacaoNegocio();
	private RetornoRest retornoRest;

	// MÉTODOS ABAIXO AINDA NÃO ESTÃO COMPLETAMENTE IMPLEMENTADOS, AINDA ESTÁ
	// FALTANDO ADICIONAR ALGUMAS REGRAS DE NEGÓCIO.

	// SALVA UM AGENDAMENTO
	@POST
	public RetornoRest salvar(Marcacao marcacao) {
		retornoRest = new RetornoRest();

		try {
			marcacaoNegocio.salvar(marcacao);
			retornoRest.setId(1);
			retornoRest.setMensagem("Salvo com sucesso");
		} catch (NaoEPossivelMarcarException e) {
			retornoRest.setId(0);
			retornoRest.setMensagem(e.getMessage());
		} catch (HorarioInvalidoException e) {
			retornoRest.setId(0);
			retornoRest.setMensagem(e.getMessage());
		}

		return retornoRest;
	}

	// RETORNA UMA DETERMINADA MARCAÇÃO PASSANDO O ID.
	@Path("/{id}")
	@GET
	public RetornoRest marcacao(@PathParam("id") Integer id) {
		retornoRest = new RetornoRest();
		try {
			retornoRest.setMarcacao((marcacaoNegocio.getMarcacaoPorId(id)));
			retornoRest.setId(1);
			retornoRest.setMensagem("Agendamento encontrado.");
		} catch (AgendamentoNaoEncontradoException e) {
			retornoRest.setId(0);
			retornoRest.setMensagem(e.getMessage());
		}

		return retornoRest;
	}

	// DELETA UM AGENDAMENTO PASSANDO O ID
	@Path("/{id}")
	@DELETE
	public RetornoRest desmarcar(@PathParam("id") Integer id) {
		retornoRest = new RetornoRest();

		try{
			marcacaoNegocio.desmarcar(id);
			
			retornoRest.setId(1);
			retornoRest.setMensagem("Seu agendamento foi desmarcado com sucesso.");
		} catch(Exception e) {
			retornoRest.setId(0);
			retornoRest.setMensagem("Aconteceu algum erro ao tentar desmarcar.");
		}
		
		return retornoRest;
	}
	
	// RETORNA TODAS MARCAÇÕES PASSANDO A MATRÍCULA DO FUNCIONÁRIO
	@Path("/funcionario/{id}")
	@GET
	public RetornoRest lista(@PathParam("id") Integer id) {
		retornoRest = new RetornoRest();

		try {
			retornoRest.setMarcacoes(marcacaoNegocio.listar(id));
			
			retornoRest.setId(1);
			retornoRest.setMensagem("Suas marcações foram carregadas com sucesso.");
		} catch (UsuarioNaoPossuiMarcacoesException e) {
			retornoRest.setId(0);
			retornoRest.setMensagem(e.getMessage());
		}
		
		return retornoRest;
	}
	
}

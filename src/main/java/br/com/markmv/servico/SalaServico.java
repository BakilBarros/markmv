package br.com.markmv.servico;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.markmv.model.entidades.Marcacao;
import br.com.markmv.model.entidades.Sala;
import br.com.markmv.model.negocio.SalaNegocio;
import br.com.markmv.model.negocio.exception.HorarioInvalidoException;
import br.com.markmv.model.negocio.exception.NenhumaSalaDisponivelException;
import br.com.markmv.model.negocio.exception.SalaNaoEncontradaException;
import br.com.markmv.util.rest.RetornoRest;

@Path("/salas")
@Produces(MediaType.APPLICATION_JSON)
public class SalaServico {

	private SalaNegocio salaNegocio = new SalaNegocio();
	private RetornoRest retornoRest;

	// USANDO O PATHPARAM
	// RETORNA TODAS AS SALAS QUE ESTÃO DISPONÍVEIS DE ACORDO COM A DATA,
	// HORARIO INICIAL E HORARIO FINAL PASSADO.
	@Path("/{data}/{horaInicial}/{horaFinal}")
	@GET
	public List<Sala> salas(@PathParam("data") String data, @PathParam("horaInicial") String horaInicial,
			@PathParam("horaFinal") String horaFinal) {

		List<Sala> salasDisponiveis = null;
		SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat tF = new SimpleDateFormat("HH:mm:ss");
		Marcacao marcacao = null;

		try {
			marcacao = new Marcacao(dF.parse(data), tF.parse(horaInicial), tF.parse(horaFinal));
		} catch (ParseException e1) {
			System.out.println("Erro na conversão de datas");
			e1.printStackTrace();
		}

		try {
			salasDisponiveis = salaNegocio.salasDisponíveis(marcacao);
		} catch (NenhumaSalaDisponivelException e) {
			return null;
		} catch (HorarioInvalidoException e) {
			e.printStackTrace();
		}

		return salasDisponiveis;
	}

	// RETORNA UMA DETERMINADA SALA PASSANDO O ID.
	@Path("/{id}")
	@GET
	public RetornoRest sala(@PathParam("id") Integer id) {
		retornoRest = new RetornoRest();
		try {
			retornoRest.setSala(salaNegocio.getSalaPorId(id));
			retornoRest.setId(1);
			retornoRest.setMensagem("Sala encontrada.");

		} catch (SalaNaoEncontradaException e) {
			retornoRest.setId(0);
			retornoRest.setMensagem(e.getMessage());
		}

		return retornoRest;
	}
}

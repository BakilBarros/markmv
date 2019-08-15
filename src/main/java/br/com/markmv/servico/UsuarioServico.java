package br.com.markmv.servico;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.markmv.model.entidades.Usuario;
import br.com.markmv.model.negocio.UsuarioNegocio;
import br.com.markmv.model.negocio.exception.UsuarioJaExisteException;
import br.com.markmv.model.negocio.exception.UsuarioNaoEncontradoException;
import br.com.markmv.util.rest.RetornoRest;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioServico {

	private UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
	private RetornoRest retornoRest;

	// SALVA UM USUÁRIO NO BANCO DE DADOS
	@POST
	public RetornoRest salvar(Usuario usuario) {
		retornoRest = new RetornoRest();

		try {
			usuarioNegocio.salvar(usuario);
			retornoRest.setId(1);
			retornoRest.setMensagem("Colaborador salvo com sucesso.");
		} catch (UsuarioJaExisteException e) {
			retornoRest.setId(0);
			retornoRest.setMensagem(e.getMessage());
		}
		
		return retornoRest;
	}
	
	// MÉTODO QUE RECEBE O EMAIL E SENHA E VERIFICA SE ESSE EMAIL E SENHA CORRESPONDEM A UM FUNCIONÁRIO NO BANCO DE DADOS.
	@Path("/login")
	@POST
	public RetornoRest login(Usuario usuario){
		retornoRest = new RetornoRest();
		
		try {
			Usuario usuarioBuscado = usuarioNegocio.buscar(usuario);
			
			retornoRest.setId(1);
			retornoRest.setMensagem("Sucesso");
			retornoRest.setUsuario(usuarioBuscado);
		} catch (UsuarioNaoEncontradoException e) {
			retornoRest.setId(0);
			retornoRest.setMensagem(e.getMessage());
		}
		return retornoRest;
	}
	
	// RETORNA UM DETERMINADO USUÁRIO PASSANDO A MATRÍCULA
	@Path("/{matricula}")
	@GET
	public RetornoRest usuario(@PathParam("matricula") String matricula) {
		retornoRest = new RetornoRest();

		try {
			Usuario usuario = usuarioNegocio.buscar(matricula);
			retornoRest.setUsuario(usuario);

			retornoRest.setId(1);
			retornoRest.setMensagem("Colaborador encontrado.");
		} catch (UsuarioNaoEncontradoException e) {
			retornoRest.setId(0);
			retornoRest.setMensagem(e.getMessage());
		}

		return retornoRest;
	}
}

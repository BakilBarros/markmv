package br.com.markmv.model.negocio;

import br.com.markmv.model.entidades.Usuario;
import br.com.markmv.model.negocio.exception.UsuarioJaExisteException;
import br.com.markmv.model.negocio.exception.UsuarioNaoEncontradoException;
import br.com.markmv.model.repository.UsuarioRepository;
import br.com.markmv.util.jpa.JpaUtil;

public class UsuarioNegocio {

	private UsuarioRepository usuarioRepository = new UsuarioRepository(JpaUtil.getEntityManager());

	public void salvar(Usuario usuario) throws UsuarioJaExisteException {
		if (usuarioRepository.buscar(usuario.getMatricula(), usuario.getEmail()) != null) {
			throw new UsuarioJaExisteException("Email ou matrícula já cadastrado.");
		}
		
		usuario.criptografarSenha();
		usuario.nomeMaiusculo();
		usuarioRepository.salvar(usuario);
	}

	public Usuario buscar(String matricula) throws UsuarioNaoEncontradoException {
		if (usuarioRepository.buscar(matricula) == null) {
			throw new UsuarioNaoEncontradoException("Colaborador não encontrado");
		}
		
		Usuario usuario = usuarioRepository.buscar(matricula);
		usuario.removerSenhaUsuario();
		
		return usuario;
	}

	public Usuario buscar(Usuario usuario) throws UsuarioNaoEncontradoException {
		
		usuario.criptografarSenha();
		Usuario usuarioBuscado = usuarioRepository.buscar(usuario);

		if (usuarioBuscado == null) {
			throw new UsuarioNaoEncontradoException("Senha ou email não encontrado.");
		}
		usuarioBuscado.removerSenhaUsuario();
		return usuarioBuscado;
	}

}

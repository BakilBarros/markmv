package br.com.markmv.model.negocio.exception;

public class UsuarioJaExisteException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UsuarioJaExisteException(String msg) {
		super(msg);
	}
}

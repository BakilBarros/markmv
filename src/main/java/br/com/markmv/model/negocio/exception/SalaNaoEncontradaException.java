package br.com.markmv.model.negocio.exception;

public class SalaNaoEncontradaException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public SalaNaoEncontradaException(String msg) {
		super(msg);
	}
}

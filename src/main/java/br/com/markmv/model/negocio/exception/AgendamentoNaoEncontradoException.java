package br.com.markmv.model.negocio.exception;

public class AgendamentoNaoEncontradoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public AgendamentoNaoEncontradoException(String msg){
		super(msg);
	}

}

package com.mwcc.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CidadeNaoEncontradaException(Long cozinhaId) {
		this(String.format("Não existe cidade cadastrada com o código %d", cozinhaId));
	}

}

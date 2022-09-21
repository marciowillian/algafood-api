package com.mwcc.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	ERRO_DE_SISTEMA("/erro-sistema", "Erro de Sistema"),
	PARAMETRO_INVALIDO("/parametro-invalido","Parâmetro informado inválido"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Erro de negócio");
	
	private String title;
	private String uri;

	private ProblemType(String path, String title) {
		this.uri = "https://algafood.com" + path;
		this.title = title;
	}

}

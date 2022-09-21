package com.mwcc.algafood.domain.repository;

import java.util.List;

import com.mwcc.algafood.domain.model.Permissao;

public interface PermissaoRepository {

	List<Permissao> todas();
	Permissao porId(Long id);
	Permissao adicionar(Permissao Permissao);
	void remover(Permissao Permissao);
	void atualizar(Permissao Permissao);
	
}

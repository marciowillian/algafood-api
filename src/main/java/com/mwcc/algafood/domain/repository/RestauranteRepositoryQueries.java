package com.mwcc.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.mwcc.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	List<Restaurante> read(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	List<Restaurante> readComFreteGratis(String nome);

}
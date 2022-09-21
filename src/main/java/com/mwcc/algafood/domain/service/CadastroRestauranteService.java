package com.mwcc.algafood.domain.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mwcc.algafood.domain.exception.EntidadeEmUsoException;
import com.mwcc.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.mwcc.algafood.domain.model.Cozinha;
import com.mwcc.algafood.domain.model.Restaurante;
import com.mwcc.algafood.domain.repository.RestauranteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CadastroRestauranteService {

	private static final String MSG_NENHUM_RESTAURANTE_ENCONTRADO = "Nenhum restaurante encontrado.";
	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de dócido %d não pode ser removido, pois está em uso.";

	private RestauranteRepository restauranteRepository;
	private CadastroCozinhaService cadastroCozinhaService;

	public Restaurante salvar(Restaurante restaurante) {
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(restaurante.getCozinha().getId());
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}

	public Restaurante buscarRestaurantePorId(Long id) {
		return restauranteRepository.findById(id).orElseThrow(
				() -> new RestauranteNaoEncontradoException(id));
	}

	public List<Restaurante> buscarRestaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal TaxaFinal)
			throws Exception {
		try {
			return restauranteRepository.findByTaxaFreteBetween(taxaInicial, TaxaFinal);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public List<Restaurante> buscarPorNomeECozinha(String nome, Long id) throws Exception {
		try {
			return restauranteRepository.findByNomeContainingAndCozinhaId(nome, id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public List<Restaurante> buscarPorNomeECozinhaV2(String nome, Long id) throws Exception {
		try {
			return restauranteRepository.buscarPorNomeECozinha(nome, id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public List<Restaurante> restaurantePorNomeEFrete(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal) throws Exception {
		try {
			return restauranteRepository.read(nome, taxaFreteInicial, taxaFreteFinal);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public Restaurante buscarPrimeiro() {
		return restauranteRepository.buscarPrimeiro()
				.orElseThrow(() -> new RestauranteNaoEncontradoException(MSG_NENHUM_RESTAURANTE_ENCONTRADO));
	}

	public void deletar(Long id) {
		try {
			restauranteRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, id));
		}
	}
}

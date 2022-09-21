package com.mwcc.algafood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mwcc.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.mwcc.algafood.domain.exception.NegocioException;
import com.mwcc.algafood.domain.model.FormaPagamento;
import com.mwcc.algafood.domain.model.Restaurante;
import com.mwcc.algafood.domain.repository.RestauranteRepository;
import com.mwcc.algafood.domain.service.CadastroRestauranteService;

import lombok.AllArgsConstructor;

@RestController @AllArgsConstructor
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	private RestauranteRepository restauranteRepository;

	private CadastroRestauranteService cadastroRestauranteService;

	@GetMapping
	public ResponseEntity<List<Restaurante>> todos() {
		return ResponseEntity.ok(restauranteRepository.findAll());
	}

	@GetMapping(value = "{id}")
	public Restaurante buscarPorId(@PathVariable("id") Long id) {
		return cadastroRestauranteService.buscarRestaurantePorId(id);
	}

	@GetMapping("/taxa-frete")
	public List<Restaurante> buscarPotTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) throws Exception {
		return cadastroRestauranteService.buscarRestaurantesPorTaxaFrete(taxaInicial, taxaFinal);
	}

	@GetMapping("/restaurante-cozinha")
	public List<Restaurante> buscaPorNomeEPorCozinha(String nome, Long idCozinha) throws Exception{
		return cadastroRestauranteService.buscarPorNomeECozinha(nome, idCozinha);
	}

	@GetMapping("/restaurante-cozinha-v2")
	public List<Restaurante> buscaPorNomeEPorCozinhaV2(String nome, Long idCozinha)throws Exception {
		return cadastroRestauranteService.buscarPorNomeECozinhaV2(nome, idCozinha);
	}

	@GetMapping("/com-frete-gratis")
	public List<Restaurante> restauranteFreteGratis(String nome) {
		return restauranteRepository.readComFreteGratis(nome);
	}

	@GetMapping("/por-nome-e-frete")
	public List<Restaurante> restaurantePorNomeEFrete(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal) throws Exception{
		return cadastroRestauranteService.restaurantePorNomeEFrete(nome, taxaFreteInicial, taxaFreteFinal);
	}

	@GetMapping("/buscar-primeiro")
	public Restaurante buscarPrimeiro() {
		return cadastroRestauranteService.buscarPrimeiro();
	}

	@GetMapping("/formas-pagamento")
	public List<FormaPagamento> getRestauranteFormasPagamento(@RequestParam Long id) {
		Restaurante restaurante = cadastroRestauranteService.buscarRestaurantePorId(id);
		return restaurante.getFormasPagamento();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(
			@RequestBody @Valid Restaurante restaurante) {
		try {
			return cadastroRestauranteService.salvar(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public Restaurante atualizar(@PathVariable("id") Long id, @RequestBody @Valid Restaurante restaurante) {
		Restaurante restauranteAtual = cadastroRestauranteService.buscarRestaurantePorId(id);
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "produtos");
		
		try {
			return cadastroRestauranteService.salvar(restauranteAtual);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PatchMapping("/{id}")
	public Restaurante atualizarParcial(@PathVariable("id") Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
		Restaurante restauranteAtual = cadastroRestauranteService.buscarRestaurantePorId(id);
		merge(campos, restauranteAtual, request);
		
		try {
			return cadastroRestauranteService.salvar(restauranteAtual);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable("id") Long id) {
		cadastroRestauranteService.deletar(id);
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino, HttpServletRequest request) {

		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);		
			
			Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
			
			camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (Exception e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
		
	}

}

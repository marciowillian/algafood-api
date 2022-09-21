package com.mwcc.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mwcc.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.mwcc.algafood.domain.model.Cozinha;
import com.mwcc.algafood.domain.repository.CozinhaRepository;
import com.mwcc.algafood.domain.service.CadastroCozinhaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	private CozinhaRepository cozinhaRepository;

	private CadastroCozinhaService cadastroCozinhaService;

	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}

//	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
//	public CozinhasXmlWrapper listarXml() {
//		return new CozinhasXmlWrapper(cozinhaRepository.findAll());
//	}

	@GetMapping("/por-nome")
	public List<Cozinha> porNome(String nome) {
		return cozinhaRepository.findByNomeContaining(nome);
	}

	@GetMapping(value = "{id}")
	public Cozinha buscarPorId(@PathVariable("id") Long id) {

		return cadastroCozinhaService.buscarOuFalhar(id);
	}

	@GetMapping("/buscar-primeira")
	public ResponseEntity<Cozinha> buscarPrimeiro() {
		Optional<Cozinha> retorno = cozinhaRepository.buscarPrimeiro();
		try {
			if (retorno.isPresent()) {
				return ResponseEntity.ok(retorno.get());
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			throw new CozinhaNaoEncontradaException(e.getMessage());
		}

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		return cadastroCozinhaService.salvar(cozinha);
	}

	@PutMapping("/{id}")
	public Cozinha atualizar(@PathVariable("id") Long id, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(id);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		return cadastroCozinhaService.salvar(cozinhaAtual);

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("id") Long id) {
		cadastroCozinhaService.excluir(id);
	}
}

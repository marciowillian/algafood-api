package com.mwcc.algafood.api.controller;

import java.util.List;

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

import com.mwcc.algafood.domain.exception.EstadoNaoEncontradoException;
import com.mwcc.algafood.domain.exception.NegocioException;
import com.mwcc.algafood.domain.model.Cidade;
import com.mwcc.algafood.domain.repository.CidadeRepository;
import com.mwcc.algafood.domain.service.CadastroCidadeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	private CidadeRepository cidadeRepository;

	private CadastroCidadeService cadastroCidadeService;

	@GetMapping
	public ResponseEntity<List<Cidade>> todas() {
		return ResponseEntity.ok(cidadeRepository.findAll());
	}

	@GetMapping(value = "{id}")
	public Cidade getCidadeById(@PathVariable("id") Long id) {
		return cadastroCidadeService.buscarCidadeOuFalhar(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody @Valid Cidade cidade) {
		try {
			return cadastroCidadeService.salvar(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public Cidade atualizar(@PathVariable("id") Long id, @RequestBody Cidade cidade) {
		try {
			Cidade cidadeAtual = cadastroCidadeService.buscarCidadeOuFalhar(id);
			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			return cadastroCidadeService.salvar(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable("id") Long id) {
		cadastroCidadeService.deletar(id);
	}

	
}

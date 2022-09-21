package com.mwcc.algafood.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mwcc.algafood.api.domain.service.FormaPagamentoService;
import com.mwcc.algafood.domain.model.FormaPagamento;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("formapagamento")
public class FormaPagamentoController {
	
	private FormaPagamentoService formaPagamentoService;
	
	@GetMapping
	public ResponseEntity<List<FormaPagamento>> formasPagamento() {
		return ResponseEntity.ok(formaPagamentoService.getformasPagamentos());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamento adicionar(@RequestBody FormaPagamento formaPagamento){
		return formaPagamentoService.salvar(formaPagamento);
	}

}

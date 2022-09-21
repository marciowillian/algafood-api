package com.mwcc.algafood.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mwcc.algafood.domain.exception.CidadeNaoEncontradaException;
import com.mwcc.algafood.domain.exception.EntidadeEmUsoException;
import com.mwcc.algafood.domain.model.Cidade;
import com.mwcc.algafood.domain.model.Estado;
import com.mwcc.algafood.domain.repository.CidadeRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CadastroCidadeService {

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso.";
	
	private CidadeRepository cidadeRepository;

	private CadastroEstadoService cadastroEstadoService;
	
	public Cidade salvar(Cidade cidade) {
		Estado estado = cadastroEstadoService.buscarOuFalhar(cidade.getEstado().getId());
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}

	public Cidade atualizar(Long id) {
		Cidade cid = buscarCidadeOuFalhar(id);
		return this.salvar(cid);
	}

	public void deletar(Long id) {
		try {
			cidadeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
		}
	}

	public Cidade buscarCidadeOuFalhar(Long id) {
		return cidadeRepository.findById(id).orElseThrow(
				() -> new CidadeNaoEncontradaException(id));
	}
}

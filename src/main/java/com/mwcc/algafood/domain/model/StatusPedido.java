package com.mwcc.algafood.domain.model;

public enum StatusPedido {
	CRIADO("criado"), CONFIRMADO("confirmado"), ENTREGUE("entregue"), CANCELADO("cancelado");

	public String status;

	StatusPedido(String status) {
		this.status = status;
	}
}

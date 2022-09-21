package com.mwcc.algafood.api.exceptionhandler;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {
	
	private Integer status;
	private String type;
	private String title;
	private String detail;
	private String userMessage;
	@Builder.Default
	private LocalDateTime timeStamp = LocalDateTime.now();
	private List<Field> fields;
	
	@Getter @Builder
	public static class Field {
		private String nome;
		private String userMessage;
	}
	
}

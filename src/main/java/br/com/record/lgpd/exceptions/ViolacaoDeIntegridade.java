package br.com.record.lgpd.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.validation.constraints.NotNull;

public class ViolacaoDeIntegridade extends SQLIntegrityConstraintViolationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String causa = new String("Violação de integridade!");

	public ViolacaoDeIntegridade(@NotNull String mensagem) {
		super(mensagem + causa);
	}

	public ViolacaoDeIntegridade(@NotNull String mensagem, Class<?> classe) {
		this(mensagem + "[ Classe.: " + classe.getSimpleName() + " ] ");
	}

}

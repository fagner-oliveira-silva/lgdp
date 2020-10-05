package br.com.record.lgpd.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.validation.constraints.NotNull;

public class LGPDExceptionJaCadastrado extends SQLIntegrityConstraintViolationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LGPDExceptionJaCadastrado(@NotNull String mensagem) {
		super(mensagem);
	}
}

package br.com.record.lgpd.exceptions;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

public class ViolacaoDeArgumentosDeInicializacaoDoConstrutor extends ExceptionInInitializerError {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String causa = new String("Violação de regras de argumentos do método construtor da classe!");

	public ViolacaoDeArgumentosDeInicializacaoDoConstrutor(@NotNull String mensagem) {
		super(mensagem + causa);
	}

	public ViolacaoDeArgumentosDeInicializacaoDoConstrutor(@NotNull String argumento, Class<?> classe) {
		this("Defina um valor válido para o argumento [" + argumento + "] da [ Classe.: " + classe.getSimpleName() + " ] ");
	}

	public ViolacaoDeArgumentosDeInicializacaoDoConstrutor(@NotNull ArrayList<String> argumentos, Class<?> classe) {
		//this("Defina um valor válido para o argumento [" + argumentos.toString() + "] da [ Classe.: " + classe.getSimpleName() + " ] ");
		this("Defina um valor válido para o argumento [" + montaMensagemComArgumentos(argumentos) + "] da [ Classe.: " + classe.getSimpleName() + " ] ");
	}
	
	public static String montaMensagemComArgumentos(@NotNull ArrayList<String> argumentos) {
		StringBuilder mensagem = new StringBuilder();
		for (String argumento : argumentos) {
			mensagem.append(argumento + ", ");
		}
		String retorno = mensagem.substring(0, mensagem.length()-2);
		return retorno;
	}

}

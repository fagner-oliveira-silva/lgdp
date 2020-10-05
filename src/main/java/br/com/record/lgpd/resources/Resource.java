package br.com.record.lgpd.resources;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.record.lgpd.exceptions.ViolacaoDeIntegridade;
import br.com.record.lgpd.model.JsonDeResposta;

public class Resource<T> {

	protected static final String STRING_SUCESSO = "Processamento efetuado com sucesso!";
	protected static final String STRING_NAO_ACHEI = "Não encontrado!";
	protected static final String STRING_ERRO = "Ocorreu um erro durante processamentom da requisicao!";

	protected HttpStatus statusHttp = null;

	// private Repositorio

	protected boolean temRegistro(Long id) {
		return false;
	}

	protected boolean temId(Long id) {
		boolean retorno = false;
		if (id != null) {
			retorno = true;
		} else {
			statusHttp = HttpStatus.BAD_REQUEST;
		}
		return retorno;
	}

	protected ResponseEntity<JsonDeResposta> montaJsonResposta(Object o, String mensagem, HttpStatus statusHttp) {
		HttpHeaders headers = new HttpHeaders();
		JsonDeResposta oJson = new JsonDeResposta();
		ResponseEntity<JsonDeResposta> response;
		oJson.setResposta(o);
		oJson.setMensagem(mensagem);
		response = new ResponseEntity<JsonDeResposta>(oJson, headers, statusHttp);
		return response;
	}

	protected void trataErro( T objeto, Exception e) throws ViolacaoDeIntegridade {
	}

}
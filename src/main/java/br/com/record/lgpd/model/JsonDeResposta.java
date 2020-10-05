package br.com.record.lgpd.model;

public class JsonDeResposta {
	
	private String mensagem;
	private Object resposta;
	
	public JsonDeResposta() {
		
	}
	
	public JsonDeResposta(String mensagem, Object paciente) {
		
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Object getResposta() {
		return resposta;
	}

	public void setResposta(Object resposta) {
		this.resposta = resposta;
	}

}

package br.com.record.lgpd.resources;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.record.lgpd.exceptions.ViolacaoDeIntegridade;
import br.com.record.lgpd.model.JsonDeResposta;
import br.com.record.lgpd.repository.IServidor;
import br.com.record.lgpd.service.Servidores;

@RestController
@RequestMapping(value = "/api")
public class Servidor extends Resource<br.com.record.lgpd.model.Servidor> {

	@Autowired
	private IServidor repositorio;

	private br.com.record.lgpd.model.Servidor servidor = null;

	@PostMapping("/lgpd/servidor/add")
	public ResponseEntity<JsonDeResposta> cria(@Valid @RequestBody Servidores json) throws ViolacaoDeIntegridade {
		br.com.record.lgpd.model.Servidor servidor = new br.com.record.lgpd.model.Servidor(json.getNome(), json.getEnderecoIp());
		StringBuilder mensagem = new StringBuilder(STRING_SUCESSO);
		try {
			servidor = repositorio.encontrePeloNomeOuIp(servidor.getEnderecoIP().getPrimeiroOcteto(), servidor.getEnderecoIP().getSegundoOcteto(), servidor.getEnderecoIP().getTerceiroOcteto(), servidor.getEnderecoIP().getQuartoOcteto(), servidor.getNome());
			if (servidor == null) {
				servidor = repositorio.save(servidor);
				statusHttp = HttpStatus.OK;
			} else {
				mensagem.delete(0, mensagem.length());
				mensagem.append("Registro criado anteriormente!");
				statusHttp = HttpStatus.EXPECTATION_FAILED;
			}
		} catch (Exception e) {
			statusHttp = HttpStatus.EXPECTATION_FAILED;
			trataErro(servidor, e);
		}
		return montaJsonResposta(servidor, mensagem.toString(), statusHttp);
	}

	@PutMapping("/lgpd/servidor/upd/{id}")
	public ResponseEntity<JsonDeResposta> atualiza(@Valid @PathVariable Long id, @Valid @RequestBody br.com.record.lgpd.model.Servidor json)
			throws ViolacaoDeIntegridade {
		String mensagem = STRING_NAO_ACHEI;
		statusHttp = HttpStatus.INTERNAL_SERVER_ERROR;
		if (temRegistro(id)) {
			try {
				json.setId(servidor.getId());
				json.setDataDeAtualizacao(Calendar.getInstance());
				json = repositorio.save(json);
				mensagem = STRING_SUCESSO;
				statusHttp = HttpStatus.OK;
			} catch (Exception e) {
				trataErro(json, e);
				mensagem = STRING_ERRO + " " + e.getMessage();
			}
		}
		return montaJsonResposta(json, mensagem, statusHttp);
	}

	@DeleteMapping("/lgpd/servidor/del/{id}")
	public ResponseEntity<JsonDeResposta> deleta(@Valid @PathVariable Long id) throws ViolacaoDeIntegridade {
		String mensagem = STRING_NAO_ACHEI;
		statusHttp = HttpStatus.INTERNAL_SERVER_ERROR;
		if (temRegistro(id)) {
			try {
				repositorio.delete(servidor);
				mensagem = STRING_SUCESSO;
				statusHttp = HttpStatus.OK;
			} catch (Exception e) {
				trataErro(servidor, e);
				mensagem = STRING_ERRO + " " + e.getMessage();
			}
		}
		return montaJsonResposta(null, mensagem, statusHttp);
	}

	@GetMapping("/lgpd/servidor/{id}")
	public ResponseEntity<JsonDeResposta> busca(@Valid @PathVariable Long id) {
		String mensagem = STRING_NAO_ACHEI;
		if(temRegistro(id)) {
			mensagem = STRING_SUCESSO;
		}
		return montaJsonResposta(servidor, mensagem, statusHttp);
	}

	@GetMapping("/lgpd/servidor/all")
	public ResponseEntity<JsonDeResposta> listaTudo() {
		Collection<br.com.record.lgpd.model.Servidor> lista = new ArrayList<br.com.record.lgpd.model.Servidor>();
		HttpStatus statusHttp = HttpStatus.OK;
		String mensagem = STRING_SUCESSO;
		lista.addAll(repositorio.findAll());
		return montaJsonResposta(lista, mensagem, statusHttp);
	}
	
	@Override
	protected boolean temRegistro(Long id) {
		boolean retorno = false;
		if (temId(id)) {
			Optional<br.com.record.lgpd.model.Servidor> objetoDeBusca = repositorio.findById(id);
			if (objetoDeBusca.isPresent()) {
				servidor = objetoDeBusca.get();
				statusHttp = HttpStatus.OK;
				retorno = true;
			} else {
				statusHttp = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		return retorno;
	}
	
	@Override
	protected ResponseEntity<JsonDeResposta> montaJsonResposta(Object o, String mensagem, HttpStatus statusHttp) {
		HttpHeaders headers = new HttpHeaders();
		JsonDeResposta oJson = new JsonDeResposta();
		ResponseEntity<JsonDeResposta> response;
		oJson.setResposta(o);
		oJson.setMensagem(mensagem);
		response = new ResponseEntity<JsonDeResposta>(oJson, headers, statusHttp);
		return response;
	}

	@Override
	protected void trataErro(br.com.record.lgpd.model.Servidor servidor, Exception e) throws ViolacaoDeIntegridade {
		if (e instanceof SQLIntegrityConstraintViolationException) {
			StringBuilder mensagem = new StringBuilder("");
			mensagem.append(STRING_NAO_ACHEI);
			mensagem.append("[ ");
			mensagem.append("Nome: " + servidor.getNome());
			mensagem.append(" / ");
			mensagem.append("enderecoIp: " + servidor.getEnderecoIP());
			mensagem.append(" ]");
			throw new ViolacaoDeIntegridade(mensagem.toString(), Servidor.class);
		} else {
			e.printStackTrace();
		}
	}
}
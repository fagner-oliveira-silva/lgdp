package br.com.record.lgpd.resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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

import br.com.record.lgpd.model.Aplicacao;
import br.com.record.lgpd.model.JsonDeResposta;
import br.com.record.lgpd.repository.IAplicacao;

@RestController
@RequestMapping(value = "/api")
public class AplicacaoResource {

	@Autowired
	private IAplicacao repositorio;

	@GetMapping("/lgpd/aplicacao/{id}")
	public ResponseEntity<Optional<Aplicacao>> busca(@Valid @PathVariable Long id) {
		Optional<Aplicacao> resultado = repositorio.findById(id);
		if (resultado == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(resultado);
	}

	@PostMapping("/lgpd/aplicacao/add")
	public Aplicacao cria(@Valid @RequestBody Aplicacao aplicacao) {
		try {
			aplicacao = repositorio.save(aplicacao);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return aplicacao;
	}

	@PutMapping("/lgpd/aplicacao/upd/{id}")
	public ResponseEntity<JsonDeResposta> atualiza(@Valid @RequestBody Aplicacao aplicacao) {
		Long id = aplicacao.getId();
		String mensagem = "Objeto json inválido!";
		Optional<Aplicacao> objetoDeBusca;
		HttpStatus statusHttp = HttpStatus.BAD_REQUEST;
		if (id != null) {
			objetoDeBusca = repositorio.findById(id);
			try {
				if (objetoDeBusca.isPresent()) {
					objetoDeBusca.get().merge(aplicacao);
					aplicacao = objetoDeBusca.get();
					aplicacao = repositorio.save(aplicacao);
					mensagem = "Dados da aplicacao atualizado com sucesso!";
					statusHttp = HttpStatus.OK;
				}
			} catch (Exception erro) {
				mensagem = "Ocorreu no processamentom da requisicao!";
				statusHttp = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		HttpHeaders headers = new HttpHeaders();
		JsonDeResposta json = new JsonDeResposta();
		ResponseEntity<JsonDeResposta> response;
		json.setResposta(aplicacao);
		json.setMensagem(mensagem);
		response = new ResponseEntity<JsonDeResposta>(json, headers, statusHttp);
		return response;
	}

	@DeleteMapping("/lgpd/aplicacao/del/{id}")
	public ResponseEntity<Aplicacao> deleta(@Valid @PathVariable Long id) {
		Optional<Aplicacao> resultado = repositorio.findById(id);
		if (resultado == null) {
			return ResponseEntity.notFound().build();
		} else {
			repositorio.delete(resultado.get());
		}
		return ResponseEntity.ok(resultado.get());
	}

	@GetMapping("/lgpd/aplicacao/all")
	public ResponseEntity<List<Aplicacao>> listaTudo() {
		HttpHeaders headers = new HttpHeaders();
		List<Aplicacao> lista = new ArrayList<Aplicacao>();
		lista.addAll(repositorio.findAll());
		if (lista.size() == 0) {
			headers.add("Content-Length", "0");
			headers.add("Date", Calendar.getInstance().getTime().toString());
			headers.add("Mensagem", "Lista vazia!");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).headers(headers).build();
		}
		return ResponseEntity.ok(lista);
	}
}
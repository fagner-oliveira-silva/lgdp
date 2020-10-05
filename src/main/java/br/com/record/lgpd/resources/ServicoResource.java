package br.com.record.lgpd.resources;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.record.lgpd.exceptions.ViolacaoDeIntegridade;
import br.com.record.lgpd.model.BancoDeDados;
import br.com.record.lgpd.model.JsonDeResposta;
import br.com.record.lgpd.model.ServicoDeBD;
import br.com.record.lgpd.model.Servidor;
import br.com.record.lgpd.repository.IServicoDeBD;
import br.com.record.lgpd.repository.IServidor;

@RestController
@RequestMapping(value = "/api")
public class ServicoResource extends Resource<ServicoDeBD> {

	@Autowired
	private IServicoDeBD servicos;

	@Autowired
	private IServidor servidores;

	private ServicoDeBD servico;

	@PostMapping("/lgpd/servico/add")
	public ResponseEntity<JsonDeResposta> cria(@Valid @RequestBody br.com.record.lgpd.service.ServicoDeBD json) throws ViolacaoDeIntegridade {
		Servidor servidor = null;
		statusHttp = HttpStatus.OK;
		StringBuilder mensagem = new StringBuilder(STRING_SUCESSO);
		String nome = (json.getServidor().getNome() == null ? "" : json.getServidor().getNome());
		String ip = (json.getServidor().getEnderecoIP() == null ? "" : json.getServidor().getEnderecoIP());
		try {
			if(nome.isEmpty()) {
				if(ip.isEmpty()) {
					mensagem.trimToSize();
					mensagem.append("Informe o nome e/ou o ip do servidor!");
					statusHttp = HttpStatus.EXPECTATION_FAILED;
				} else {
					servidor = servidores.encontrePeloIp(ip);
				}
			} else {
				servidor = servidores.encontrePeloNome(nome);
			}
			if (servidor != null) {
				servico = new ServicoDeBD(json.getNome(), json.getPorta(), servidor, json.getSgbdEnum());
				servico.adicionaCatalogo(new BancoDeDados("P12"));
				for (String nome_bd : json.getCatalogo()) {
					BancoDeDados bancoDeDados = new BancoDeDados(nome_bd);
					servico.adicionaCatalogo(bancoDeDados);
				}
				servico = servicos.save(servico);
			} else {
				mensagem.trimToSize();
				mensagem.append("Servidor não localizado! Informe");
			}
		} catch (Exception e) {
			trataErro(servico, e);
		}
		return montaJsonResposta(servico, mensagem.toString(), statusHttp);
	}

	@PutMapping("/lgpd/servico/upd/{id}")
	public ResponseEntity<JsonDeResposta> atualiza(@Valid @PathVariable Long id, @Valid @RequestBody ServicoDeBD json)
			throws ViolacaoDeIntegridade {
		String mensagem = STRING_NAO_ACHEI;
		statusHttp = HttpStatus.INTERNAL_SERVER_ERROR;
		if (temRegistro(id)) {
			try {
				json.setId(servico.getId());
				json.setDataDeAtualizacao(Calendar.getInstance());
				json = servicos.save(json);
				mensagem = STRING_SUCESSO;
				statusHttp = HttpStatus.OK;
			} catch (Exception e) {
				trataErro(json, e);
				mensagem = STRING_ERRO + " " + e.getMessage();
			}
		}
		return montaJsonResposta(json, mensagem, statusHttp);
	}

	@DeleteMapping("/lgpd/servico/del/{id}")
	public ResponseEntity<JsonDeResposta> deleta(@Valid @PathVariable Long id) throws ViolacaoDeIntegridade {
		String mensagem = STRING_NAO_ACHEI;
		statusHttp = HttpStatus.INTERNAL_SERVER_ERROR;
		if (temRegistro(id)) {
			try {
				servicos.delete(servico);
				mensagem = STRING_SUCESSO;
				statusHttp = HttpStatus.OK;
			} catch (Exception e) {
				trataErro(servico, e);
				mensagem = STRING_ERRO + " " + e.getMessage();
			}
		}
		return montaJsonResposta(null, mensagem, statusHttp);
	}

	@GetMapping("/lgpd/servico/{id}")
	public ResponseEntity<JsonDeResposta> busca(@Valid @PathVariable Long id) {
		String mensagem = STRING_NAO_ACHEI;
		if (temRegistro(id)) {
			mensagem = STRING_SUCESSO;
		}
		return montaJsonResposta(servico, mensagem, statusHttp);
	}

	@GetMapping("/lgpd/servico/all")
	public ResponseEntity<JsonDeResposta> listaTudo() {
		Collection<ServicoDeBD> lista = new ArrayList<ServicoDeBD>();
		HttpStatus statusHttp = HttpStatus.OK;
		String mensagem = STRING_SUCESSO;
		lista.addAll(servicos.findAll());
		return montaJsonResposta(lista, mensagem, statusHttp);
	}

	@Override
	protected boolean temRegistro(Long id) {
		boolean retorno = false;
		if (temId(id)) {
			try {
				Optional<ServicoDeBD> objetoDeBusca = servicos.findById(id);
				if (objetoDeBusca.isPresent()) {
					servico = objetoDeBusca.get();
					statusHttp = HttpStatus.OK;
					retorno = true;
				} else {
					statusHttp = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			} catch (Exception e) {
				statusHttp = HttpStatus.INTERNAL_SERVER_ERROR;
				if (e instanceof JpaSystemException) {
				} 
			}
		}
		return retorno;
	}

	@Override
	protected void trataErro(ServicoDeBD servico, Exception e) throws ViolacaoDeIntegridade {
		if (e instanceof SQLIntegrityConstraintViolationException ||
			e instanceof DataIntegrityViolationException ) {
			StringBuilder mensagem = new StringBuilder("");
			mensagem.append(STRING_NAO_ACHEI);
			mensagem.append("[ ");
			mensagem.append("Nome: " + servico.getNome());
			mensagem.append(" / ");
			mensagem.append("Porta: " + servico.getPorta());
			mensagem.append(" / ");
			mensagem.append("ServicoDeBD: " + servico.getServidor().toString());
			mensagem.append(" ]");
			throw new ViolacaoDeIntegridade(mensagem.toString(), ServicoDeBD.class);
		}
	}

}

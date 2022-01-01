package br.com.record.lgpd;

import java.util.ArrayList;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.record.lgpd.exceptions.ViolacaoDeArgumentosDeInicializacaoDoConstrutor;
import br.com.record.lgpd.model.EnumSGBD;
import br.com.record.lgpd.model.Instancia;
import br.com.record.lgpd.model.Servidor;
import br.com.record.lgpd.repository.IServidor;

/**
 *
 * <p>
 * Classe de testes para o repositorio do model Servidor. 
 * Implemente aqui todos os casos de testes de persistencia que deverão ser submetidos a verificação. 
 *
 * <p>
 * <strong>NOTE:</strong> This class requires JUnit 4.12 or higher.
 *
 * @author Fagner Oliveira da Silva
 * @since 1.0
 * @see Servidor
 * @see SpringJUnit4ClassRunner
 * @see org.springframework.test.context.junit4.rules.SpringClassRule
 * @see org.springframework.test.context.junit4.rules.SpringMethodRule
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ServidorRepositorioTest {

	@Autowired
	private IServidor repositorio;

	@Rule
	public ExpectedException excessao = ExpectedException.none();

	@Test
	public void ipNuloException() {
		String hostname = "SERVERDB";
		String ip = null;
		try {
			Assertions.assertThat(new Servidor(hostname, ip)).isNull();
		} catch (ViolacaoDeArgumentosDeInicializacaoDoConstrutor e) {
			Assertions.assertThat(e).isInstanceOf(ViolacaoDeArgumentosDeInicializacaoDoConstrutor.class);
		}
	}

	@Test
	public void ipVazioException() {
		String hostname = "SERVERDB";
		String ip = "";
		try {
			Assertions.assertThat(new Servidor(hostname, ip)).isNull();
		} catch (ViolacaoDeArgumentosDeInicializacaoDoConstrutor e) {
			Assertions.assertThat(e).isInstanceOf(ViolacaoDeArgumentosDeInicializacaoDoConstrutor.class);
		}
	}

	@Test
	public void hostnameNuloException() {
		String hostname = null;
		String ip = "192.168.0.37";
		try {
			Assertions.assertThat(new Servidor(hostname, ip)).isNull();
		} catch (ViolacaoDeArgumentosDeInicializacaoDoConstrutor e) {
			Assertions.assertThat(e).isInstanceOf(ViolacaoDeArgumentosDeInicializacaoDoConstrutor.class);
		}
	}

	@Test
	public void hostnameVazioException() {
		String hostname = "";
		String ip = "192.168.0.37";
		try {
			Assertions.assertThat(new Servidor(hostname, ip)).isNull();
		} catch (ViolacaoDeArgumentosDeInicializacaoDoConstrutor e) {
			Assertions.assertThat(e).isInstanceOf(ViolacaoDeArgumentosDeInicializacaoDoConstrutor.class);
		}
	}

	@Test
	public void criaServidorDeveriaPersistirDados() {
		String hostname = "SERVERDB";
		String ip = "192.168.0.37";
		Servidor serverdb = new Servidor(hostname, ip);
		this.repositorio.save(serverdb);
		Assertions.assertThat(serverdb.getId()).isNotNull();
		Assertions.assertThat(serverdb.getNome()).isEqualTo(hostname);
		Assertions.assertThat(serverdb.getEnderecoIP()).isEqualTo(ip);
		Assertions.assertThat(serverdb.getDataDeAtualizacao()).isNull();
		Assertions.assertThat(serverdb.getDataDeCriacao()).isNotNull();
	}

	@Test
	public void buscaServidorDeveriaBuscarDadosPersistidos() {
		String hostname = "SERVERDB";
		String ip = "192.168.0.37";
		Servidor serverdb = new Servidor(hostname, ip);
		Servidor busca = null;
		this.repositorio.save(serverdb);
		Long id = serverdb.getId();
		Optional<Servidor> resultado = repositorio.findById(id);
		Assertions.assertThat(resultado).isNotNull();
		busca = resultado.get();
		Assertions.assertThat(serverdb).isEqualTo(busca);
		Assertions.assertThat(serverdb.getId()).isEqualTo(busca.getId());
		Assertions.assertThat(serverdb.getNome()).isEqualTo(busca.getNome());
		Assertions.assertThat(serverdb.getEnderecoIP()).isEqualTo(busca.getEnderecoIP());
		Assertions.assertThat(serverdb.getId()).isNotNull();
		resultado = null;
		id = 100L;
		resultado = repositorio.findById(id);
		Assertions.assertThat(resultado.isPresent()).isFalse();
	}


	@Test
	public void listaTodosServidoresDeveriaBuscarDadosPersistidos() {
		Servidor servidor = new Servidor("SERVERDB", "192.168.0.37");
		this.repositorio.save(servidor);
		servidor = new Servidor("SERVER_DESENV", "192.168.0.150");
		this.repositorio.save(servidor);
		servidor = new Servidor("SERVER_HML", "192.168.0.149");
		this.repositorio.save(servidor);
		servidor = new Servidor("SERVERAPL", "192.168.0.81");
		this.repositorio.save(servidor);

		ArrayList<Servidor> lista = new ArrayList<Servidor>();
		lista.addAll(this.repositorio.findAll());
		
		for (Servidor server : lista) {
			Assertions.assertThat(server).isNotNull();
			Assertions.assertThat(server.getId()).isNotNull();
			Assertions.assertThat(server.getNome()).isNotNull();
			Assertions.assertThat(server.getEnderecoIP()).isNotNull();
		}
		
		Assertions.assertThat(lista.size()).isEqualTo(4);
		
	}

	@Test
	public void atualizaServidorDeveriaPersistirDados() {
		String hostname = "SERVERDB";
		String ip = "192.168.0.37";
		String ipNovo = "192.168.0.81";
		Servidor serverdb = new Servidor(hostname, ip);
		this.repositorio.save(serverdb);
		Assertions.assertThat(serverdb.getId()).isNotNull();
		Assertions.assertThat(serverdb.getNome()).isEqualTo(hostname);
		Assertions.assertThat(serverdb.getEnderecoIP()).isEqualTo(ip);
		serverdb.setEnderecoIPComParse(ipNovo);
		this.repositorio.save(serverdb);
		Assertions.assertThat(serverdb.getId()).isNotNull();
		Assertions.assertThat(serverdb.getNome()).isEqualTo(hostname);
		Assertions.assertThat(serverdb.getEnderecoIP()).isEqualTo(ipNovo);
		Assertions.assertThat(serverdb.getDataDeAtualizacao()).isNotNull();
		Assertions.assertThat(serverdb.getDataDeAtualizacao()).isNotEqualTo(serverdb.getDataDeCriacao());
	}
	
	@Test
	public void adicionaServicoDeveriaPersistirDados() {
		String hostname = "SERVERDB";
		String porta = "51086";
		String ip = "192.168.0.37";
		String nomeDoServico = "RECORD";

		Servidor serverdb = new Servidor(hostname, ip);

		Instancia sqlServer = new Instancia(nomeDoServico, porta, EnumSGBD.SQLSERVER );

		serverdb.adicionaServico(sqlServer);
		
		this.repositorio.save(serverdb);
		
		Assertions.assertThat(serverdb.getId()).isNotNull();
		Assertions.assertThat(serverdb.getNome()).isEqualTo(hostname);
		Assertions.assertThat(serverdb.getEnderecoIP()).isEqualTo(ip);
		Assertions.assertThat(serverdb.getDataDeAtualizacao()).isNull();
		Assertions.assertThat(serverdb.getDataDeAtualizacao()).isNotEqualTo(serverdb.getDataDeCriacao());

		Assertions.assertThat(serverdb.getId()).isNotNull();
		
	}
}
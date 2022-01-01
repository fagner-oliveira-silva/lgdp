package br.com.record.lgpd;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.record.lgpd.exceptions.ViolacaoDeArgumentosDeInicializacaoDoConstrutor;
import br.com.record.lgpd.model.BancoDeDados;
import br.com.record.lgpd.model.EnumSGBD;
import br.com.record.lgpd.model.Instancia;
import br.com.record.lgpd.model.Servidor;
import br.com.record.lgpd.repository.IInstancia;

/**
 *
 * <p>
 * Classe de testes para o repositorio do model Servico. Implemente aqui todos
 * os casos de testes de persistencia que deverão ser submetidos a verificação.
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
public class ServicoRepositorioTest {

	@Autowired
	private IInstancia repositorio;

	@Rule
	public ExpectedException excessao = ExpectedException.none();

	@Test
	public void nomeDoServicoNuloException() {
		String nomeDoServidor = "DBSERVER";
		String nomeDoServico = null;
		String ip = "192.168.0.37";
		String porta = "51086";

		Servidor serverdb = new Servidor(nomeDoServidor, ip);

		try {
			Assertions.assertThat(new Instancia(nomeDoServico, porta, serverdb, EnumSGBD.SQLSERVER)).isNull();
		} catch (ViolacaoDeArgumentosDeInicializacaoDoConstrutor e) {
			Assertions.assertThat(e).isInstanceOf(ViolacaoDeArgumentosDeInicializacaoDoConstrutor.class);
		}
	}

	@Test
	public void nomeDoServicoVazioException() {
		String nomeDoServidor = "DBSERVER";
		String nomeDoServico = "";
		String ip = "192.168.0.37";
		String porta = "51086";

		Servidor serverdb = new Servidor(nomeDoServidor, ip);

		try {
			Assertions.assertThat(new Instancia(nomeDoServico, porta, serverdb, EnumSGBD.SQLSERVER)).isNull();
		} catch (ViolacaoDeArgumentosDeInicializacaoDoConstrutor e) {
			Assertions.assertThat(e).isInstanceOf(ViolacaoDeArgumentosDeInicializacaoDoConstrutor.class);
		}
	}

	@Test
	public void portaNuloException() {
		String nomeDoServidor = "DBSERVER";
		String nomeDoServico = "SERVERDB";
		String ip = "192.168.0.37";
		String porta = null;

		Servidor serverdb = new Servidor(nomeDoServidor, ip);

		try {
			Assertions.assertThat(new Instancia(nomeDoServico, porta, serverdb, EnumSGBD.SQLSERVER)).isNull();
		} catch (ViolacaoDeArgumentosDeInicializacaoDoConstrutor e) {
			Assertions.assertThat(e).isInstanceOf(ViolacaoDeArgumentosDeInicializacaoDoConstrutor.class);
		}
	}

	@Test
	public void portaVazioException() {
		String nomeDoServidor = "DBSERVER";
		String nomeDoServico = "SERVERDB";
		String ip = "192.168.0.37";
		String porta = "";

		Servidor serverdb = new Servidor(nomeDoServidor, ip);

		try {
			Assertions.assertThat(new Instancia(nomeDoServico, porta, serverdb, EnumSGBD.SQLSERVER)).isNull();
		} catch (ViolacaoDeArgumentosDeInicializacaoDoConstrutor e) {
			Assertions.assertThat(e).isInstanceOf(ViolacaoDeArgumentosDeInicializacaoDoConstrutor.class);
		}
	}

	@Test
	public void servidorNuloException() {
		String nomeDoServico = "SERVERDB";
		String porta = "51086";

		Servidor serverdb = null;

		try {
			Assertions.assertThat(new Instancia(nomeDoServico, porta, serverdb, EnumSGBD.SQLSERVER)).isNull();
		} catch (ViolacaoDeArgumentosDeInicializacaoDoConstrutor e) {
			Assertions.assertThat(e).isInstanceOf(ViolacaoDeArgumentosDeInicializacaoDoConstrutor.class);
		}
	}

	@Test
	public void criaServidorDeveriaPersistirDados() {
		String nomeDoServidor = "DBSERVER";
		String nomeDoServico = "RECORD";
		String ip = "192.168.0.37";
		String porta = "51086";

		BancoDeDados bdP12 = new BancoDeDados("P12");
		BancoDeDados bdIntegracao = new BancoDeDados("INTEGRACAO");
		BancoDeDados bdGer = new BancoDeDados("GER");

		Servidor serverdb = new Servidor(nomeDoServidor, ip);

		Instancia sqlServer = new Instancia(nomeDoServico, porta, serverdb, EnumSGBD.SQLSERVER);

		serverdb.adicionaServico(sqlServer);

		sqlServer.adicionaCatalogo(bdP12);
		sqlServer.adicionaCatalogo(bdIntegracao);
		sqlServer.adicionaCatalogo(bdGer);

		this.repositorio.save(sqlServer);

		Assertions.assertThat(sqlServer.getId()).isNotNull();
		Assertions.assertThat(sqlServer.getNome()).isEqualTo(nomeDoServico);
		Assertions.assertThat(serverdb.getEnderecoIP()).isEqualTo(ip);
		Assertions.assertThat(serverdb.getDataDeAtualizacao()).isNull();
		Assertions.assertThat(serverdb.getDataDeCriacao()).isNotNull();
	}

	@Test
	public void buscaServicoDeveriaBuscarDadosPersistidos() {
		String nomeDoServidor = "DBSERVER";
		String ip = "192.168.0.37";
		String nomeDoServico1 = "RECORD";
		String nomeDoServico2 = "SITE";
		String porta1 = "51086";
		String porta2 = "51001";

		BancoDeDados bdP12 = new BancoDeDados("P12");
		BancoDeDados bdIntegracao = new BancoDeDados("INTEGRACAO");
		BancoDeDados bdGer = new BancoDeDados("GER");
		BancoDeDados bdSite = new BancoDeDados("SITE");

		Servidor serverdb = new Servidor(nomeDoServidor, ip);

		Instancia sqlServer = new Instancia(nomeDoServico1, porta1, serverdb, EnumSGBD.SQLSERVER);
		Instancia mySql = new Instancia(nomeDoServico2, porta2, serverdb, EnumSGBD.MYSQL);

		sqlServer.adicionaCatalogo(bdP12);
		sqlServer.adicionaCatalogo(bdIntegracao);
		sqlServer.adicionaCatalogo(bdGer);

		mySql.adicionaCatalogo(bdSite);

		serverdb.adicionaServico(sqlServer);
		serverdb.adicionaServico(mySql);

		this.repositorio.save(sqlServer);
		this.repositorio.save(mySql);
		
		Long idSqlServer = sqlServer.getId();
		Long idMySql = mySql.getId();

		Optional<Instancia> resultado = repositorio.findById(idSqlServer);
		Assertions.assertThat(resultado.isPresent()).isTrue();
		
		Instancia busca = resultado.get();
		
		Assertions.assertThat(sqlServer.getId()).isNotNull();
		Assertions.assertThat(sqlServer.getNome()).isEqualTo(nomeDoServico1);
		Assertions.assertThat(sqlServer.getPorta()).isEqualTo(porta1);
		Assertions.assertThat(sqlServer).isEqualTo(busca);
		Assertions.assertThat(sqlServer.getId()).isEqualTo(busca.getId());
		Assertions.assertThat(sqlServer.getNome()).isEqualTo(busca.getNome());
		Assertions.assertThat(sqlServer.getServidor().getEnderecoIP()).isEqualTo(busca.getServidor().getEnderecoIP());

		Assertions.assertThat(serverdb.getEnderecoIP()).isEqualTo(mySql.getServidor().getEnderecoIP());
		Assertions.assertThat(serverdb.getDataDeAtualizacao()).isNull();
		Assertions.assertThat(serverdb.getDataDeCriacao()).isNotNull();

		resultado = repositorio.findById(idMySql);
		Assertions.assertThat(resultado.isPresent()).isTrue();
		busca = resultado.get();

		
		Assertions.assertThat(mySql.getId()).isNotNull();
		Assertions.assertThat(mySql.getNome()).isEqualTo(nomeDoServico2);
		Assertions.assertThat(mySql.getPorta()).isEqualTo(porta2);
		Assertions.assertThat(mySql).isEqualTo(busca);
		Assertions.assertThat(mySql.getId()).isEqualTo(busca.getId());
		Assertions.assertThat(mySql.getNome()).isEqualTo(busca.getNome());
		Assertions.assertThat(mySql.getServidor().getEnderecoIP()).isEqualTo(busca.getServidor().getEnderecoIP());

		Assertions.assertThat(serverdb.getEnderecoIP()).isEqualTo(mySql.getServidor().getEnderecoIP());
		Assertions.assertThat(serverdb.getDataDeAtualizacao()).isNull();
		Assertions.assertThat(serverdb.getDataDeCriacao()).isNotNull();

	}

	@Test
	public void listaTodosServidoresDeveriaBuscarDadosPersistidos() {
		Assert.assertTrue("Não implementado!", false);
	}

	@Test
	public void atualizaServidorDeveriaPersistirDados() {
		Assert.assertTrue("Não implementado!", false);
	}
}
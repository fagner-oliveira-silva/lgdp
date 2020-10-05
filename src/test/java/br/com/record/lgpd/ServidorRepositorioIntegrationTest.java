package br.com.record.lgpd;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import br.com.record.lgpd.model.Servidor;
import br.com.record.lgpd.repository.IServidor;

@Service
public class ServidorRepositorioIntegrationTest {

	@Autowired
	private TestEntityManager em;
	@Autowired
	private IServidor repositorio;
	
	@Test
	public void whenencontrePeloNome_RetorneServidor() {
		//given
		Servidor serverdb = new Servidor("SERVERDB", "192.168.0.37");
		try {
		    this.em.persist(serverdb);
		    em.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	   //when
	    Servidor resultado_da_busca = repositorio.encontrePeloNome(serverdb.getNome());

	   //then
	    assertSame(serverdb.getNome(), resultado_da_busca.getNome());
	}

	@Test
	public void whenencontrePeloIp_RetorneServidor() {
		//given
		Servidor serverdb = new Servidor("SERVERDB", "192.168.0.37");
	    em.persist(serverdb);
	    em.flush();

	   //when
	    Servidor resultado_da_busca = repositorio.encontrePeloIp(serverdb.getEnderecoIP());

	   //then
	    assertSame(serverdb.getNome(), resultado_da_busca.getNome());

	}

}
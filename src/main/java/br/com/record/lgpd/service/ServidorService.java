package br.com.record.lgpd.service;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.record.lgpd.model.EnderecoIp;
import br.com.record.lgpd.model.Servidor;
import br.com.record.lgpd.repository.IServidor;

@Service
public class ServidorService {
	
	@Autowired
    private IServidor daoServidor;

    public Servidor encontrePeloNome(String nome) {
        return daoServidor.encontrePeloNome(nome);
    }
    
	public Servidor encontrePeloIp(String ip) {
		EnderecoIp oIp = new EnderecoIp(ip);
		return daoServidor.encontrePeloIp(oIp.getPrimeiroOcteto(), oIp.getSegundoOcteto(), oIp.getTerceiroOcteto(), oIp.getQuartoOcteto());
	}
}
package br.com.record.lgpd.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.record.lgpd.model.ServicoDeBD;

public interface IServicoDeBD extends JpaRepository<ServicoDeBD, Long> {
	public Collection<ServicoDeBD> encontrePeloNome(String nome);
	//public Collection<ServicoDeBD> encontreServidorPeloIp(String ip);
}

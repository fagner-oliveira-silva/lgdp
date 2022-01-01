package br.com.record.lgpd.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.record.lgpd.model.Instancia;

public interface IInstancia extends JpaRepository<Instancia, Long> {
	public Collection<Instancia> encontrePeloNome(String nome);
	public Instancia encontrePeloNomeOuPortaOuIp(String nome, String porta, int oct1, int oct2, int oct3, int oct4);
	//public Collection<ServicoDeBD> encontreServidorPeloIp(String ip);
}

package br.com.record.lgpd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.record.lgpd.model.Servidor;

public interface IServidor extends JpaRepository<Servidor, Long> {
	public Servidor encontrePeloNome(String nome);
	public Servidor encontrePeloIp(String nome);
}

package br.com.record.lgpd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.record.lgpd.model.Aplicacao;

public interface IAplicacao extends JpaRepository<Aplicacao, Long> {
	public Aplicacao encontrePeloNome(String nome);
}

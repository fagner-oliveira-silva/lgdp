package br.com.record.lgpd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.record.lgpd.model.EnderecoIp;
import br.com.record.lgpd.model.Servidor;

public interface IEnderecoIp extends JpaRepository<EnderecoIp, Long> {
	public Servidor encontrePeloIp(int oct1, int oct2, int oct3, int oct4);
}

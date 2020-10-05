package br.com.record.lgpd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.record.lgpd.model.EnumSGBD;

public interface IEnumSGBD extends JpaRepository<EnumSGBD, Long> {
	public EnumSGBD encontrePeloNome(String nome);
}

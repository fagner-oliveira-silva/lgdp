package br.com.record.lgpd.model;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 *
 * Havilah Consultoria LTDA
 *
 * Classe de objeto de banco de dados (tabela) que possuem dados sensiveis a LGPD.
 * Utilizada na solução de modelo de dados da aplicação de LGPD.
 *
 * @author Fagner Oliveida da Silva
 * @since 16/09/2020
 * @version 1.0
 */
@Entity
@Table(name = "\"LGPD_FUNCIONALIDADE\"")
public class Funcionalidade extends CalendarManageAble implements Comparable<Funcionalidade>, Comparator<Funcionalidade> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "DESCRICAO", length = 50, nullable = false, insertable = true, updatable = true, unique = true)
	private String descricao;

    /**
     * @param servico - Instancia da classe Servico contendo as informações do serviço do banco de dados.
     * @param nome - String contendo o nome do banco de dados.
     * @author Fagner Oliveida da Silva
     */
	public Funcionalidade(final String descricao) {
		super();
		this.descricao = descricao;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
		atualiza();
	}

	@Override
	public int compare(Funcionalidade o1, Funcionalidade o2) {
		return o1.hashCode() - o2.hashCode();
	}

	@Override
	public int compareTo(Funcionalidade o) {
		int retorno = 0;
		if(o == null) {
			retorno = -1;
		} else {
			retorno = this.getId().compareTo(o.getId());
		}
		return retorno;
	}
}
package br.com.record.lgpd.model;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


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
@Table(name = "\"LGPD_BD_APLICACAO\"", 
uniqueConstraints = @UniqueConstraint(columnNames = {
		"ID_APLICACAO", "ID_BD" }))
public class BasesAcessadas extends CalendarManageAble implements Comparable<BasesAcessadas>, Comparator<BasesAcessadas> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ID_APLICACAO", referencedColumnName = "ID", nullable = false) })
	private Aplicacao aplicacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ID_BD", referencedColumnName = "ID") })
	private BancoDeDados bd;


    /**
     * @param servico - Instancia da classe Servico contendo as informações do serviço do banco de dados.
     * @param nome - String contendo o nome do banco de dados.
     * @author Fagner Oliveida da Silva
     */
	public BasesAcessadas(final Aplicacao aplicacao, final BancoDeDados bd) {
		super();
		this.aplicacao = aplicacao;
		this.bd = bd;
	}

	/**
	 * @return the aplicacao
	 */
	public Aplicacao getAplicacao() {
		return aplicacao;
	}

	/**
	 * @return the bd
	 */
	public BancoDeDados getBd() {
		return bd;
	}

	/**
	 * @param aplicacao the aplicacao to set
	 */
	public void muda(Aplicacao aplicacao, BancoDeDados bd) {
		this.aplicacao = aplicacao;
		this.bd = bd;
		atualiza();
	}

	@Override
	public int compare(BasesAcessadas o1, BasesAcessadas o2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compareTo(BasesAcessadas o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
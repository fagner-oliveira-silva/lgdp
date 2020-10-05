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
@Table(name = "\"LGPD_FUNCIONALIDADE_APLICACAO\"", 
uniqueConstraints = @UniqueConstraint(columnNames = {
		"ID_APLICACAO", "ID_FUNCIONALIDADE" }))
public class FuncionalidadesDaAplicacao extends CalendarManageAble implements Comparable<FuncionalidadesDaAplicacao>, Comparator<FuncionalidadesDaAplicacao> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ID_APLICACAO", referencedColumnName = "ID", nullable = false) })
	private Aplicacao aplicacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ID_FUNCIONALIDADE", referencedColumnName = "ID", nullable = false) })
	private Funcionalidade funcionalidade;

    /**
     * @param aplicacao - Instancia da classe Servico contendo as informações do serviço do banco de dados.
     * @param funcionalidade - String contendo o nome do banco de dados.
     * @author Fagner Oliveida da Silva
     */
	public FuncionalidadesDaAplicacao(final Aplicacao aplicacao, final Funcionalidade funcionalidade) {
		super();
		this.aplicacao = aplicacao;
		this.funcionalidade = funcionalidade;
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
	public Funcionalidade getFuncionalidade() {
		return funcionalidade;
	}

	/**
	 * @param aplicacao the aplicacao to set
	 */
	public void muda(Aplicacao aplicacao, Funcionalidade funcionalidade) {
		this.aplicacao = aplicacao;
		this.funcionalidade = funcionalidade;
		atualiza();
	}

	@Override
	public int compare(FuncionalidadesDaAplicacao o1, FuncionalidadesDaAplicacao o2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compareTo(FuncionalidadesDaAplicacao o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
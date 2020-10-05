package br.com.record.lgpd.model;

import java.util.Comparator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


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
@Table(name = "\"LGPD_TABELA\"")
public class Tabela extends CalendarManageAble implements Comparable<Tabela>, Comparator<Tabela> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "\"SCHEMA\"", length = 50, nullable = true)
	private String schema;

	@Column(name = "NOME", length = 50, nullable = false)
	private String nome;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ID_DO_BANCO_DE_DADOS", nullable = false, foreignKey = @ForeignKey( name = "FK_ID_DO_BANCO_DE_DADOS"))
	private BancoDeDados bd;

	/**
	 * @param nome - nome da tabela.
	 * @param bd - nome do banco de dados onde está armazenada.
	 * @author Fagner Oliveida da Silva
	 */
	public Tabela(@NotNull final String nome, @NotNull BancoDeDados bd) {
		this.nome = nome;
	}

	/**
	 * @param nome - nome da tabela.
	 * @param bd - nome do banco de dados onde está armazenada.
	 * @param schema - nome do schema do banco de dados onde está armazenada.
	 * @author Fagner Oliveida da Silva
	 */
	public Tabela(@NotNull final String nome, @NotNull BancoDeDados bd, final String schema) {
		this(nome, bd);
		this.schema = schema;
	}

	/**
	 * @return o nome do objeto.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return the nome
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @return the nome
	 */
	public String getNomeDoBD() {
		return bd.getNome();
	}

	@Override
	public int compareTo(@NotNull Tabela o) {
		return this.hashCode() - o.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#compare(java.lang.Object)
	 */
	@Override
	public int compare(@NotNull Tabela arg0, @NotNull Tabela arg1) {
		return ( arg0.equals(arg1) ? 0: 1 );
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bd == null) ? 0 : bd.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((schema == null) ? 0 : schema.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Tabela))
			return false;
		Tabela other = (Tabela) obj;
		if (bd == null) {
			if (other.bd != null)
				return false;
		} else if (!bd.equals(other.bd))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (schema == null) {
			if (other.schema != null)
				return false;
		} else if (!schema.equals(other.schema))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tabela [schema=" + schema + ", nome=" + nome + ", bd=" + bd + "]";
	}
	

}


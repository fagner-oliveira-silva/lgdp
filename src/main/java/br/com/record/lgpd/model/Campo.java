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
 * Record - Classe com as características do servico a ser disponibilizado.
 * Utilizada na solução de modelo de dados da aplicação de LGPD.
 *
 * @author Fagner Oliveida da Silva
 * @since 16/09/2020
 * @version 1.0
 *
 */
@Entity
@Table(name = "\"LGPD_CAMPO\"")
public class Campo extends CalendarManageAble implements Comparable<Campo>, Comparator<Campo> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

	@Column(name = "NOME", length = 50, nullable = false)
    private String nome;
	
	@Column(name = "TIPO", length = 50, nullable = false)
    private String tipo;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ID_DA_TABELA", nullable = false, foreignKey = @ForeignKey( name = "FK_ID_DA_TABELA"))
    private Tabela tabela;

    /**
     * @param nome
     * @param tipo
     * @param tabela 
     * @author Fagner Oliveida da Silva
     */
    public Campo(@NotNull final String nome, @NotNull final String tipo, @NotNull final Tabela tasbela, Tabela tabela) {
        this.nome = nome;
        this.tipo = tipo;
        this.tabela = tabela;
    }

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
		atualiza();
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
		atualiza();
	}

	@Override
	public int compare(Campo o1, Campo o2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compareTo(Campo o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Campo))
			return false;
		Campo other = (Campo) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Campo [nome=" + nome + ", tipo=" + tipo + "]";
	}

	
}

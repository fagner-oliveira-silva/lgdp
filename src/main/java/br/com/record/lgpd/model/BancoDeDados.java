package br.com.record.lgpd.model;

import java.util.Comparator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * Havilah Consultoria LTDA
 *
 * Classe de modelo de dados da entidade Banco de Dados que possuem tabelas com dados sensiveis a
 * LGPD. Utilizada na solução de modelo de dados da aplicação de LGPD.
 *
 * @author Fagner Oliveida da Silva
 * @since 16/09/2020
 * @version 1.0
 */
@Entity
@Table(name = "\"LGPD_BD\"")
public class BancoDeDados extends CalendarManageAble implements Comparable<BancoDeDados>, Comparator<BancoDeDados> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NOME", length = 50, nullable = false)
	private String nome;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "SERVICO", nullable = false, referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_ID_SERVICO"))
	private Instancia servico;

	/**
	 * @param nome - String contendo o nome do banco de dados.
	 * @author Fagner Oliveida da Silva
	 */
	public BancoDeDados(String nome) {
		super();
		this.nome = nome;
	}

	/**
	 * @param servico - Instancia da classe Servico contendo as informações do
	 *                serviço do banco de dados.
	 * @param nome    - String contendo o nome do banco de dados.
	 * @author Fagner Oliveida da Silva
	 */

	public BancoDeDados(Instancia servico, String nome) {
		this(nome);
		this.servico = servico;
		this.servico.adicionaCatalogo(this);
	}

	public String getInstancia() {
		return servico.getNome();
	}

	public String getNome() {
		return nome;
	}

	/**
	 * @return the servico
	 */
	public Instancia getServico() {
		return servico;
	}

	/**
	 * @param servico the servico to set
	 */
	public void setServico(Instancia servico) {
		atualiza();
		this.servico = servico;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		atualiza();
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((servico == null) ? 0 : servico.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof BancoDeDados))
			return false;
		BancoDeDados other = (BancoDeDados) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (servico == null) {
			if (other.servico != null)
				return false;
		} else if (!servico.equals(other.servico))
			return false;
		return true;
	}

	@Override
	public int compare(BancoDeDados o1, BancoDeDados o2) {
		return o1.hashCode() - o2.hashCode();
	}

	@Override
	public int compareTo(BancoDeDados servidor) {
		int retorno = 0;
		if (servidor == null) {
			retorno = -1;
		} else {
			if (this.getId() != null && servidor.getId() != null) {
				retorno = this.getId().compareTo(servidor.getId());
			} else {
				if (this.getNome() != null && servidor.getNome() != null) {
					if (this.getInstancia() != null && servidor.getInstancia() != null) {
						if (this.getServico() != null && servidor.getServico() != null) {
							retorno = (this.getNome() + this.getInstancia() + this.getServico())
									.compareTo((servidor.getNome() + servidor.getInstancia() + servidor.getServico()));
						} else {
							retorno = (this.getNome() + this.getInstancia())
									.compareTo((servidor.getNome() + servidor.getInstancia()));
						}
					} else {
						retorno = this.getNome().compareTo(servidor.getNome());
					}
				} else {
					if (this.getInstancia() != null && servidor.getInstancia() != null) {
						retorno = (this.getNome() + this.getInstancia())
								.compareTo((servidor.getNome() + servidor.getInstancia()));
					} else {
						retorno = this.getNome().compareTo(servico.getNome());
					}
				}
			}
		}
		return retorno;
	}

	@Override
	public String toString() {
		return "BancoDeDados [nome=" + nome + ", servico=" + servico + "]";
	}

}
package br.com.record.lgpd.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQuery;

import br.com.record.lgpd.exceptions.ViolacaoDeArgumentosDeInicializacaoDoConstrutor;

/**
 *
 * Classe com as características do servidor. Utilizada na solução de modelo de
 * dados da aplicação de LGPD.
 *
 * @author Fagner Oliveida da Silva
 * @since 16/09/2020
 * @version 1.0
 */
@Entity
@Table(name = "\"LGPD_SERVIDOR\"", indexes = { @Index(name = "PK_SERVIDOR", columnList = "ID", unique = true),
		@Index(name = "UNQ_NOME", columnList = "NOME", unique = true),
		@Index(name = "UNQ_IP", columnList = "ENDERECO_IP", unique = true) } /*
																				 * , uniqueConstraints =
																				 * {@UniqueConstraint(columnNames =
																				 * {"ENDERECO_IP"}, name =
																				 * "\"UNQ_ENDERECO_IP\""),
																				 * 
																				 * @UniqueConstraint(columnNames =
																				 * {"NOME"}, name =
																				 * "\"UNQ_SERVIDOR_ENDERECO_NOME\"") }
																				 */
)
@NamedQuery(name = "Servidor.encontrePeloNome", query = "SELECT p FROM Servidor p WHERE p.nome = ?1")
@NamedQuery(name = "Servidor.encontrePeloIp", query = "SELECT p FROM Servidor p WHERE p.enderecoIp = ?1")
public final class Servidor extends CalendarManageAble implements Comparable<Servidor>, Comparator<Servidor> {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NOME", nullable = false, insertable = true, updatable = true)
	private String nome;

	@Column(name = "ENDERECO_IP", nullable = false, insertable = true, updatable = true)
	private String enderecoIp;

	@OneToMany(mappedBy = "servidor", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<ServicoDeBD> servicos;

	/**
	 * @author Fagner Oliveida da Silva
	 */
	protected Servidor() {
		super();
		servicos = new TreeSet<ServicoDeBD>();
	}

	/**
	 * @param nome       - nome do servidor.
	 * @param enderecoIp - endereco IP do servidor.
	 * @author Fagner Oliveida da Silva
	 */
	public Servidor(@NotNull String nome, @NotNull String enderecoIp)
			throws ViolacaoDeArgumentosDeInicializacaoDoConstrutor {
		this();
		ArrayList<String> argumentos = new ArrayList<String>();
		if (nome == null || nome.isEmpty()) {
			argumentos.add("nome");
		}
		if (enderecoIp == null || enderecoIp.isEmpty()) {
			argumentos.add("enderecoIp");
		}
		if (argumentos.isEmpty()) {
			this.nome = nome;
			this.enderecoIp = enderecoIp;
			this.servicos = new TreeSet<ServicoDeBD>();
		} else {
			throw new ViolacaoDeArgumentosDeInicializacaoDoConstrutor(argumentos, Servidor.class);
		}
	}

	/**
	 * @return o enderecoIP do servidor.
	 */
	public String getEnderecoIP() {
		return enderecoIp;
	}

	/**
	 * @param enderecoIp the enderecoIP to set
	 */
	public void setEnderecoIP(@NotNull String enderecoIp) {
		this.enderecoIp = enderecoIp;
		atualiza();
	}

	/**
	 * @return nome atribuído ao servidor.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome - string contendo o nome a ser atribuído ao objeto.
	 */
	public void setNome(@NotNull final String nome) {
		this.nome = nome;
		atualiza();
	}

	/**
	 * @return the servicos
	 */
	public Collection<ServicoDeBD> getServicos() {
		return servicos;
	}

	/**
	 * @param servicos the servicos to set
	 */
	public void setServicos(Collection<ServicoDeBD> servicos) {
		this.servicos = servicos;
		atualiza();
	}

	public void adicionaServico(@NotNull ServicoDeBD servico) {
		if (null != servico) {
			servico.setServidor(this);
			servicos.add(servico);
		}
	}

	public void removeServico(@NotNull ServicoDeBD servico) {
		if (null != servico) {
			servicos.remove(servico);
		}
	}

	public ServicoDeBD getServico(@NotNull String nome) {
		ServicoDeBD servico = null;
		if(!servicos.isEmpty()) {
			for (ServicoDeBD servicoDeBD : servicos) {
				if (null != servicoDeBD) {
					if(servicoDeBD.getNome().equals(nome)) {
						servico = servicoDeBD;
					}
				}
			}
		}
		return servico;
	}

	@Override
	public String toString() {
		return "[" + getNome() + " / " + getEnderecoIP() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((enderecoIp == null) ? 0 : enderecoIp.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Servidor))
			return false;
		Servidor other = (Servidor) obj;
		if (enderecoIp == null) {
			if (other.enderecoIp != null)
				return false;
		} else if (!enderecoIp.equals(other.enderecoIp))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public int compare(Servidor o1, Servidor o2) {
		return o1.hashCode() - o2.hashCode();
	}

	@Override
	public int compareTo(Servidor servidor) {
		int retorno = 0;
		if (servidor == null) {
			retorno = -1;
		} else {
			if(this.getId() != null && servidor.getId() != null ) {
				retorno = this.getId().compareTo(servidor.getId());
			} else {
				if(this.getNome() != null && servidor.getNome() != null ) {
					if(this.getEnderecoIP() != null && servidor.getEnderecoIP() != null ) {
						retorno = ( this.getNome() + this.getEnderecoIP() ).compareTo( ( servidor.getNome() + servidor.getEnderecoIP() ));
					} else {
						retorno = this.getNome().compareTo(servidor.getNome());
					}
				} else {
					if(this.getEnderecoIP() != null && servidor.getEnderecoIP() != null ) {
						retorno = this.getEnderecoIP().compareTo( servidor.getEnderecoIP() );
					}
				}
			}
		}
		return retorno;
	}
}
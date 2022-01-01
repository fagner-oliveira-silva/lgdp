package br.com.record.lgpd.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
		@Index(name = "UNQ_NOME", columnList = "NOME", unique = true) }
)
@NamedQuery(name = "Servidor.encontrePeloNome", query = "SELECT p FROM Servidor p WHERE p.nome = ?1")
@NamedQuery(name = "Servidor.encontrePeloIp", query = "SELECT p FROM Servidor p INNER JOIN EnderecoIp b ON p.enderecoIp = b.id WHERE b.primeiroOcteto = ?1 and b.segundoOcteto = ?2 and b.terceiroOcteto = ?3 and b.quartoOcteto = ?4")
@NamedQuery(name = "Servidor.encontrePeloNomeOuIp", query = "SELECT p FROM Servidor p INNER JOIN EnderecoIp b ON p.enderecoIp = b.id WHERE p.nome =?5 or (b.primeiroOcteto = ?1 and b.segundoOcteto = ?2 and b.terceiroOcteto = ?3 and b.quartoOcteto = ?4)")
public final class Servidor extends CalendarManageAble implements Comparable<Servidor>, Comparator<Servidor> {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NOME", nullable = false, insertable = true, updatable = true)
	private String nome;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_IP", nullable = false, referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_ID_IP"))
	private EnderecoIp enderecoIp;

	@OneToMany(mappedBy = "servidor", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Instancia> servicos;

	/**
	 * @author Fagner Oliveida da Silva
	 */
	protected Servidor() {
		super();
		servicos = new TreeSet<Instancia>();
	}

	/**
	 * @param nome       - nome do servidor.
	 * @param enderecoIp - endereco IP do servidor.
	 * @author Fagner Oliveida da Silva
	 */
	public Servidor(@NotNull String nome, @NotNull EnderecoIp enderecoIp)
			throws ViolacaoDeArgumentosDeInicializacaoDoConstrutor {
		this();
		ArrayList<String> argumentos = new ArrayList<String>();
		if (nome == null || nome.isEmpty()) {
			argumentos.add("nome");
		}
		if (enderecoIp == null ) {
			argumentos.add("enderecoIp");
		}
		if (argumentos.isEmpty()) {
			this.nome = nome;
			this.enderecoIp = enderecoIp;
			this.servicos = new TreeSet<Instancia>();
		} else {
			throw new ViolacaoDeArgumentosDeInicializacaoDoConstrutor(argumentos, Servidor.class);
		}
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
		if (enderecoIp == null ) {
			argumentos.add("enderecoIp");
		}
		if (argumentos.isEmpty()) {
			this.nome = nome;
			this.enderecoIp = new EnderecoIp(enderecoIp);
			this.servicos = new TreeSet<Instancia>();
		} else {
			throw new ViolacaoDeArgumentosDeInicializacaoDoConstrutor(argumentos, Servidor.class);
		}
	}

	/**
	 * @return o enderecoIP do servidor.
	 */
	public EnderecoIp getEnderecoIP() {
		return enderecoIp;
	}

	/**
	 * @return o enderecoIP do servidor.
	 */
	public String getIpToString() {
		return enderecoIp.getIpToString();
	}

	/**
	 * @param enderecoIp the enderecoIP to set
	 */
	public void setEnderecoIP(@NotNull EnderecoIp enderecoIp) {
		this.enderecoIp = enderecoIp;
		atualiza();
	}

	/**
	 * @param enderecoIp the enderecoIP to set
	 */
	public void setEnderecoIPComParse(@NotNull String enderecoIp) {
		this.enderecoIp = new EnderecoIp(enderecoIp);
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
	public Collection<Instancia> getServicos() {
		return servicos;
	}

	/**
	 * @param servicos the servicos to set
	 */
	public void setServicos(Collection<Instancia> servicos) {
		this.servicos = servicos;
		atualiza();
	}

	public void adicionaServico(@NotNull Instancia servico) {
		if (null != servico) {
			servico.setServidor(this);
			servicos.add(servico);
		}
	}

	public void removeServico(@NotNull Instancia servico) {
		if (null != servico) {
			servicos.remove(servico);
		}
	}

	public Instancia getServico(@NotNull String nome) {
		Instancia servico = null;
		if(!servicos.isEmpty()) {
			for (Instancia servicoDeBD : servicos) {
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
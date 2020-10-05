package br.com.record.lgpd.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NamedQuery;

import br.com.record.lgpd.exceptions.ViolacaoDeArgumentosDeInicializacaoDoConstrutor;

@Entity
@Table(name = "\"LGPD_INSTANCIA_SGBD\"", uniqueConstraints = @UniqueConstraint(columnNames = { "SERVIDOR",
		"PORTA" }, name = "\"UNQ_SERVIDOR_PORTA\""))
@NamedQuery(name = "ServicoDeBD.encontrePeloNome", query = "SELECT p FROM ServicoDeBD p WHERE p.nome = ?1")
public class ServicoDeBD extends CalendarManageAble implements Comparable<ServicoDeBD>, Comparator<ServicoDeBD> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NOME", nullable = false, insertable = true, updatable = true, unique = false)
	private String nome;

	@Column(name = "PORTA", nullable = false, insertable = true, updatable = true, unique = false)
	private String porta;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JoinColumns({ @JoinColumn(name = "SERVIDOR", referencedColumnName = "ID") })
	@JoinColumn(name = "SERVIDOR", nullable = false, referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_ID_SERVIDOR"))
	private Servidor servidor;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ID_ENUM_SGBD", nullable = false, referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_ID_TIPO_SGBD"))
	@Enumerated(EnumType.ORDINAL)
	private EnumSGBD sgbdEnum;

	@OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Collection<BancoDeDados> catalogo;

	public ServicoDeBD() {
		super();
		catalogo = new TreeSet<BancoDeDados>();
	}

	/**
	 * @param nome  <b> - nome do servico.
	 * @param porta <b> - número da porta do serviço.
	 * @param sgbd  <b> - Tipo de banco de dados usado.
	 */
	public ServicoDeBD(String nome, String porta, EnumSGBD sgbd)
			throws ViolacaoDeArgumentosDeInicializacaoDoConstrutor {
		this();
		ArrayList<String> argumentos = new ArrayList<String>();
		if (nome == null || nome.isEmpty()) {
			argumentos.add("nome");
		}
		if (porta == null || porta.isEmpty()) {
			argumentos.add("porta");
		}
		if (sgbd == null) {
			argumentos.add("sgbd");
		}
		if (argumentos.isEmpty()) {
			this.nome = nome;
			this.porta = porta;
			this.sgbdEnum = sgbd;
		} else {
			throw new ViolacaoDeArgumentosDeInicializacaoDoConstrutor(argumentos, ServicoDeBD.class);
		}
	}

	/**
	 * @param nome     <b> - nome do servico.
	 * @param porta    <b> - número da porta do serviço.
	 * @param servidor <b> - Instancia da classe do ServidorResource.
	 * @param sgbd     <b> - Tipo de banco de dados usado.
	 */
	public ServicoDeBD(String nome, String porta, Servidor servidor, EnumSGBD sgbd)
			throws ViolacaoDeArgumentosDeInicializacaoDoConstrutor {
		this(nome, porta, sgbd);
		ArrayList<String> argumentos = new ArrayList<String>();
		if (servidor == null) {
			argumentos.add("servidor");
		}
		if (argumentos.isEmpty()) {
			this.servidor = servidor;
			servidor.adicionaServico(this);
		} else {
			throw new ViolacaoDeArgumentosDeInicializacaoDoConstrutor(argumentos, ServicoDeBD.class);
		}
	}

	/**
	 * @return the servidor
	 */
	public Servidor getServidor() {
		return servidor;
	}

	/**
	 * @param servidor the servidor to set
	 */
	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
		atualiza();
	}

	/**
	 * @return the porta
	 */
	public String getPorta() {
		return porta;
	}

	/**
	 * @param porta the porta to set
	 */
	public void setporta(final String porta) {
		this.porta = porta;
		atualiza();
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
	public void setNome(final String nome) {
		this.nome = nome;
		atualiza();
	}

	/**
	 * @return the sgbd
	 */
	public EnumSGBD getSgbd() {
		return sgbdEnum;
	}

	/**
	 * @param sgbd the sgbd to set
	 */
	public void setSgbd(EnumSGBD sgbdEnum) {
		atualiza();
		this.sgbdEnum = sgbdEnum;
	}

	public void adicionaCatalogo(BancoDeDados bancoDeDados) {
		atualiza();
		bancoDeDados.setServico(this);
		catalogo.add(bancoDeDados);
	}

	public void removeCatalogo(BancoDeDados bancoDeDados) {
		atualiza();
		catalogo.remove(bancoDeDados);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((catalogo == null) ? 0 : catalogo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((porta == null) ? 0 : porta.hashCode());
		result = prime * result + ((servidor == null) ? 0 : servidor.hashCode());
		result = prime * result + ((sgbdEnum == null) ? 0 : sgbdEnum.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ServicoDeBD))
			return false;
		ServicoDeBD other = (ServicoDeBD) obj;
		if (catalogo == null) {
			if (other.catalogo != null)
				return false;
		} else if (!catalogo.equals(other.catalogo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (porta == null) {
			if (other.porta != null)
				return false;
		} else if (!porta.equals(other.porta))
			return false;
		if (servidor == null) {
			if (other.servidor != null)
				return false;
		} else if (!servidor.equals(other.servidor))
			return false;
		if (sgbdEnum != other.sgbdEnum)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder retorno = new StringBuilder("ServicoDeBD [sgbd=" + this.sgbdEnum.getNome() + ", catalogo=");
		StringBuilder str = new StringBuilder("[");
		
		for (BancoDeDados bancoDeDados : catalogo) {
			str.append(bancoDeDados.getNome() + ", ");
		}
		if(! catalogo.isEmpty()) {
			retorno.append(str.substring(0, str.length()-2));
		} else {
			retorno.append(str);
		}
		retorno.append("] ]");
		return retorno.toString();
	}

	@Override
	public int compare(ServicoDeBD o1, ServicoDeBD o2) {
		return o1.hashCode() - o2.hashCode();
	}

	@Override
	public int compareTo(ServicoDeBD servico) {
		int retorno = 0;
		if (servico == null) {
			retorno = -1;
		} else {
			if(this.getId() != null && servico.getId() != null ) {
				retorno = this.getId().compareTo(servico.getId());
			} else {
				if(this.getServidor() != null && servico.getServidor() != null) {
					if(this.getNome() != null && servico.getNome() != null ) {
						if(this.getPorta() != null && servico.getPorta() != null ) {
							retorno = ( this.getServidor().toString() + this.getNome() + this.getPorta() ).compareTo( ( servico.getServidor().toString() + servico.getNome() + servico.getPorta() ));
						} else {
							retorno = ( this.getServidor().toString() + this.getNome() ).compareTo( ( servico.getServidor().toString() + servico.getNome() ));
						}
					} else {
						if(this.getPorta() != null && servico.getPorta() != null ) {
							retorno = ( this.getServidor().toString() + this.getPorta() ).compareTo( ( servico.getServidor().toString() + servico.getPorta() ));
						} else {
							retorno = ( this.getServidor().toString() ).compareTo( ( servico.getServidor().toString() ));
						}
					}
				}
			}
		}
		return retorno;
	}
}
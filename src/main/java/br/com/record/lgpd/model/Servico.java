package br.com.record.lgpd.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *
 * Record - Classe com as características do servico a ser disponibilizado.
 * Utilizada na solução de modelo de dados da aplicação de LGPD.
 *
 * @author Fagner Oliveida da Silva
 * @since 16/09/2020
 * @version 1.0
 */
@MappedSuperclass
public abstract class Servico extends CalendarManageAble /*implements Comparable<Servico>, Comparator<Servico>*/ {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

	@Column(name = "NOME", nullable = false, insertable = true, updatable = true, unique = false)
    private String nome;

	@Column(name = "PORTA", nullable = false, insertable = true, updatable = true, unique = false)
    private String porta;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "SERVIDOR", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_ID_SERVIDOR"))
	private Servidor servidor;

    /**
     * @param nome
     * @param porta
     * @param servidor
     * @author Fagner Oliveida da Silva
     */
    public Servico() {
    }

    /**
     * @param nome
     * @param porta
     * @param servidor
     * @author Fagner Oliveida da Silva
     */
    public Servico(final String nome, final String porta, final Servidor servidor) {
        this.nome = nome;
        this.porta = porta;
        this.servidor = servidor;
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
	
    @Override
    public String toString() {
        return this.nome + " -> " + servidor.toString() + ((porta.isEmpty() || porta == null) ? "" : ":" + porta) ;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((porta == null) ? 0 : porta.hashCode());
		result = prime * result + ((servidor == null) ? 0 : servidor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Servico))
			return false;
		Servico other = (Servico) obj;
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
		return true;
	}

}

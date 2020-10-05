package br.com.record.lgpd.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import br.com.record.lgpd.model.EnumSGBD;

public class ServicoDeBD implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private br.com.record.lgpd.model.Servidor servidor;
	private EnumSGBD sgbdEnum;
    private Collection<String> catalogo = new HashSet<>();
    private String nome;
    private String porta;
	
    public ServicoDeBD() {
	}

	/**
	 * @return the servidor
	 */
	public br.com.record.lgpd.model.Servidor getServidor() {
		return servidor;
	}

	/**
	 * @param servidor the servidor to set
	 */
	public void setServidor(br.com.record.lgpd.model.Servidor servidor) {
		this.servidor = servidor;
	}

	/**
	 * @return the sgbdEnum
	 */
	public EnumSGBD getSgbdEnum() {
		return sgbdEnum;
	}

	/**
	 * @param sgbdEnum the sgbdEnum to set
	 */
	public void setSgbdEnum(EnumSGBD sgbdEnum) {
		this.sgbdEnum = sgbdEnum;
	}

	/**
	 * @return the catalogo
	 */
	public Collection<String> getCatalogo() {
		return catalogo;
	}

	/**
	 * @param catalogo the catalogo to set
	 */
	public void setCatalogo(Collection<String> catalogo) {
		this.catalogo = catalogo;
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
	public void setPorta(String porta) {
		this.porta = porta;
	}

}

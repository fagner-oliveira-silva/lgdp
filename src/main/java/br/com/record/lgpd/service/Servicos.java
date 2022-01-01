package br.com.record.lgpd.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import br.com.record.lgpd.model.EnumSGBD;

public class Servicos implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private String nomeDoServidor;
    private String enderecoIp;
	private EnumSGBD sgbdEnum;
    private Collection<String> catalogo = new HashSet<>();
    private String nomeDoServico;
    private String porta;
	
    public Servicos() {
	}

	/**
	 * @return the servidor
	 */
	public String getEnderecoIp() {
		return enderecoIp;
	}

	/**
	 * @param ip to set
	 */
	public void setEnderecoIp(String ip) {
		this.enderecoIp = ip;
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
	 * @return Retorna o atributo nomeDoServico.
	 */
	public String getNomeDoServico() {
		return nomeDoServico;
	}

	/**
	 * @param nomeDoServico the nome to set
	 */
	public void setNomeDoServico(String nomeDoServico) {
		this.nomeDoServico = nomeDoServico;
	}

	/**
	 * @return the nomeDoServidor
	 */
	public String getNomeDoServidor() {
		return nomeDoServidor;
	}

	/**
	 * @param String para preencher o atributo nomeDoServidor.
	 */
	public void setNomeDoServidor(String nomeDoServidor) {
		this.nomeDoServidor = nomeDoServidor;
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

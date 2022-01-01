package br.com.record.lgpd.model;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NamedQuery;

import br.com.record.lgpd.exceptions.ViolacaoDeArgumentosDeInicializacaoDoConstrutor;

/**
*
* Classe de modelo de dados que representa um Endereço Ip. Utilizada na solução de modelo de
* dados da aplicação de LGPD.
*
* @author Fagner Oliveida da Silva
* @since 14/11/2021
* @version 1.0
*/
@Entity
@Table(	name = "\"LGPD_IP\"", 
		indexes = { 
				@Index(name = "\"PK_IP\"", columnList = "ID", unique = true)
				  }, 
		uniqueConstraints =
				@UniqueConstraint(columnNames = {"OCT_4", "OCT_3", "OCT_2", "OCT_1"}, name = "\"UNQ_IP\"")
)
@NamedQuery(name = "EnderecoIp.encontrePeloIp", query = "SELECT p FROM EnderecoIp p WHERE p.primeiroOcteto = ?1 and p.segundoOcteto = ?2 and p.terceiroOcteto = ?3 and p.quartoOcteto = ?4")
public class EnderecoIp extends CalendarManageAble implements Comparable<EnderecoIp>, Comparator<EnderecoIp> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "OCT_1", length = 3, nullable = false)
	private int primeiroOcteto;
	
	@Column(name = "OCT_2", length = 3, nullable = false)
	private int segundoOcteto;
	
	@Column(name = "OCT_3", length = 3, nullable = false)
	private int terceiroOcteto;
	
	@Column(name = "OCT_4", length = 3, nullable = false)
	private int quartoOcteto;

	public EnderecoIp() {
		super();
	}
	
	public EnderecoIp( String ip ) throws ViolacaoDeArgumentosDeInicializacaoDoConstrutor {
		this();
		parse(ip);
	}
	
	public int getPrimeiroOcteto() {
		return primeiroOcteto;
	}
	
	public void setPrimeiroOcteto(int primeiroOcteto) throws ViolacaoDeArgumentosDeInicializacaoDoConstrutor {
		if ( primeiroOcteto >= 0 && primeiroOcteto <=255) {
			this.primeiroOcteto = primeiroOcteto;
		}
	}
	
	public int getSegundoOcteto() {
		return segundoOcteto;
	}
	
	public void setSegundoOcteto(int segundoOcteto) throws ViolacaoDeArgumentosDeInicializacaoDoConstrutor {
		if ( segundoOcteto >= 0 && segundoOcteto <=255) {
			this.segundoOcteto = segundoOcteto;
		}
	}
	public int getTerceiroOcteto() {
		return terceiroOcteto;
	}
	public void setTerceiroOcteto(int terceiroOcteto) throws ViolacaoDeArgumentosDeInicializacaoDoConstrutor {
		if ( terceiroOcteto >= 0 && terceiroOcteto <=255) {
			this.terceiroOcteto = terceiroOcteto;
		}
	}
	public int getQuartoOcteto() {
		return quartoOcteto;
	}
	public void setQuartoOcteto(int quartoOcteto) throws ViolacaoDeArgumentosDeInicializacaoDoConstrutor {
		if ( quartoOcteto >= 0 && quartoOcteto <=255) {
			this.quartoOcteto = quartoOcteto;
		}
	}
	
	private void parse(String ip) {
		String[] octetos = ip.split("[.]");
		Integer valor;

		for (int i = 0; i < 4; i++) {
			valor = Integer.parseInt(octetos[i]);
			switch (i) {
			case 0:
				this.setPrimeiroOcteto(valor);
				break;
			case 1:
				this.setSegundoOcteto(valor);
				break;
			case 2:
				this.setTerceiroOcteto(valor);
				break;
			case 3:
				this.setQuartoOcteto(valor);
				break;
			default:
				break;
			}
		}
	}

	public String getIpToString() {
		String ToString = Integer.toString(primeiroOcteto) + "." + Integer.toString(segundoOcteto) + "." + Integer.toString(terceiroOcteto) + "." + Integer.toString(quartoOcteto);
		return ToString;
	}
	
	@Override
	public int compare(EnderecoIp arg0, EnderecoIp arg1) {
		return arg0.hashCode() - arg1.hashCode();
	}
	@Override
	public int compareTo(EnderecoIp ip) {
		int retorno = 0;
		int operacao = 0;
		
		if (ip == null) {
			retorno = -1;
		} else {
			if(this.getId() != null && ip.getId() != null ) {
				retorno = this.getId().compareTo(ip.getId());
			} else {
				operacao = this.getPrimeiroOcteto() - ip.getPrimeiroOcteto();
				if (operacao > 0) {
					retorno = 1;
				} else if(operacao < 0) {
					retorno = -1;
				} else {
					operacao = this.getSegundoOcteto() - ip.getSegundoOcteto();
					if (operacao > 0) {
						retorno = 1;
					} else if(operacao < 0) {
						retorno = -1;
					} else {
						operacao = this.getTerceiroOcteto() - ip.getTerceiroOcteto();
						if (operacao > 0) {
							retorno = 1;
						} else if(operacao < 0) {
							retorno = -1;
						} else {
							operacao = this.getQuartoOcteto() - ip.getQuartoOcteto();
							if (operacao > 0) {
								retorno = 1;
							} else if(operacao < 0) {
								retorno = -1;
							} else {
								retorno = 0;
							}
						}
					}
				}
			}
		}
		return retorno;
	}
}

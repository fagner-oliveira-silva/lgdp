package br.com.record.lgpd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

/**
 *
 * Record - Enum com os Banco de Dados utilizados. Utilizada na solução de
 * modelo de dados da aplicação de LGPD.
 *
 * @author Fagner Oliveida da Silva
 * @since 16/09/2020
 * @version 1.0
 */
@Entity
@Table(name = "\"LGPD_ENUM_SGBD\"",
indexes = { @Index(name = "UNQ_NOME",  columnList="NOME", unique = true)}
)
@NamedQuery(name = "EnumSGBD.encontrePeloNome", query = "SELECT p FROM EnumSGBD p WHERE p.nome = ?1")
public enum EnumSGBD {
	
	SQLSERVER(1, "SqlServer"), ORACLE(2, "Oracle"), DBASE(3, "dBase"), ACCESS(4, "Access"), MYSQL(5, "MySql"), INDEFINIDO(99,"Indefinido");

	private long id;
	private String nome;

	private EnumSGBD() {
	}

	private EnumSGBD(long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	@Id
	@Column(name = "ID", nullable = false, length = 2, insertable = true, updatable = true)
	public Long getId() {return this.id;}
	public void setId(long id) {this.id = id;}

	@Column(name = "NOME", nullable = false, length = 20, insertable = true, updatable = true)
	public String getNome() {return this.nome;}
	public void setNome(String nome) {this.nome = nome;}

	@Override
	public String toString() {
		return nome;
	}
	
	public EnumSGBD getEnumSGBDFromId( int id) {
		EnumSGBD retorno = null;
		switch (id) {
		case 1:
			retorno = SQLSERVER;
			break;
		case 2:
			retorno = ORACLE;
			break;
		case 3:
			retorno = DBASE;
			break;
		case 4:
			retorno = ACCESS;
			break;
		case 5:
			retorno = MYSQL;
			break;
		case 99:
			retorno = INDEFINIDO;
			break;
		default:
			break;
		}
		return retorno;
	}
}
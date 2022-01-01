package br.com.record.lgpd.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class CalendarManageAble implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_DE_CRIACAO", nullable = false)
	private Calendar dataDeCriacao;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_DE_ATUALIZACAO", nullable = true)
	private Calendar dataDeAtualizacao;
	
	protected CalendarManageAble() {
		this.dataDeCriacao = Calendar.getInstance();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the dataDeCriacao
	 */
	public Calendar getDataDeCriacao() {
		return dataDeCriacao;
	}

	/**
	 * @param dataDeCriacao the dataDeCriacao to set
	 */
	public void setDataDeCriacao(Calendar dataDeCriacao) {
		this.dataDeCriacao = dataDeCriacao;
	}

	/**
	 * @return the dataDeAtualizacao
	 */
	public Calendar getDataDeAtualizacao() {
		return dataDeAtualizacao;
	}

	/**
	 * @param dataDeAtualizacao the dataDeAtualizacao to set
	 */
	public void setDataDeAtualizacao(Calendar dataDeAtualizacao) {
		this.dataDeAtualizacao = dataDeAtualizacao;
	}
/*
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CalendarManageAble))
			return false;
		CalendarManageAble other = (CalendarManageAble) obj;
		if (id != other.id)
			return false;
		return true;
	}

	protected void atualiza() {
		this.dataDeAtualizacao = Calendar.getInstance();
	}

}
package br.com.record.lgpd.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import br.com.record.lgpd.model.Servidor;

public class Servidores {
	
	private Servidor servidor;
	//private EntityManagerProducer emf;

	private Long id;
	private String nome;
	private String enderecoIp;

	public Servidores() {
	}

	public Servidores(String nome, String ip) {
		this.setNome(nome);
		this.setEnderecoIp(ip);
		this.servidor = new Servidor(nome, ip);
	}
	
	public Servidor getServidor() {
		return this.servidor;
	}

	public Servidor salvar() {
		if (tudoOk()) {
			//servidor = repositorio.save(servidor);
		}
		return servidor;
	}

	public Servidor deletar() {
		return servidor;
	}

	public Servidor criar() {
		return servidor;
	}

	public Servidor atualizar() {
		return servidor;
	}

	public Optional<Servidor> buscarPorId(Long id) {
		//Optional<Servidor> servidor = (Optional<Servidor>) repositorio.findById(id);
		Optional<Servidor> servidor = null;
		return servidor;
	}

	public Servidor buscarPorNome() {
		return servidor;
	}

	public Collection<Servidor> listaTudo() {
		Collection<Servidor> lista = new ArrayList<Servidor>();
		return (Collection<Servidor>) Collections.unmodifiableCollection(lista);
	}
	
	private boolean tudoOk() {
		return preenchido();
	}

	private boolean preenchido() {
		boolean retorno = false;
		if( naoNulo()) {
			if( ! (servidor.getIpToString().isEmpty() || servidor.getNome().isEmpty()) ) {
				retorno = true;
			}
		}
		return retorno;
	}

	private boolean naoNulo() {
		boolean retorno = false;
		if(servidor != null) {
			if(servidor.getEnderecoIP() != null && servidor.getNome() != null) {
				retorno = true;
			}
		}
		return retorno;
	}

	/**
	 * @return the enderecoIp
	 */
	public String getEnderecoIp() {
		return enderecoIp;
	}

	/**
	 * @param enderecoIp the enderecoIp to set
	 */
	public void setEnderecoIp(String enderecoIp) {
		this.enderecoIp = enderecoIp;
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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param servidor the servidor to set
	 */
	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}
	
}

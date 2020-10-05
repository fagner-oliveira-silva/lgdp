package br.com.record.lgpd.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "\"LGPD_APLICACAO\"")
@NamedQuery(name = "Aplicacao.encontrePeloNome", query = "SELECT p FROM Aplicacao p WHERE p.nome = ?1")
public class Aplicacao extends CalendarManageAble {
	/**
	 * serial gerado automaticamente.
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NOME", length = 50, nullable = false, insertable = true, updatable = true, unique = false)
	private String nome;

	@Column(name = "FINALIDADE", length = 100, nullable = false, insertable = true, updatable = true, unique = false)
	private String finalidade;

	@OneToMany(mappedBy = "aplicacao", cascade = CascadeType.ALL, orphanRemoval = true)
	//private Map<Long, BasesAcessadas> basesAcessadas = new TreeMap<Long, BasesAcessadas>();
	private Collection<BasesAcessadas> basesAcessadas = new ArrayList<BasesAcessadas>();

	@OneToMany(mappedBy = "aplicacao", cascade = CascadeType.ALL, orphanRemoval = true)
	//private Map<Long, FuncionalidadesDaAplicacao> funcionalidades = new TreeMap<Long, FuncionalidadesDaAplicacao>();
	private Collection<FuncionalidadesDaAplicacao> funcionalidades = new ArrayList<FuncionalidadesDaAplicacao>();

	public Aplicacao(@NotNull final String nomeDaAplicacao, @NotNull final String finalidade) {
		super();
		this.nome = nomeDaAplicacao;
		this.finalidade = finalidade;
	}
	
	public Aplicacao(@NotNull final String nomeDaAplicacao, @NotNull final String finalidade,
			@NotNull final ArrayList<BasesAcessadas> basesAcessadas, @NotNull final ArrayList<FuncionalidadesDaAplicacao> funcionalidades) {
		this(nomeDaAplicacao, finalidade);
		this.basesAcessadas = basesAcessadas;
		this.funcionalidades = funcionalidades;
	}

	public String getNomeDaAplicacao() {
		return nome;
	}

	public String getFinalidade() {
		return finalidade;
	}
	
	public Map<Long, String> getBasesAcessadas() {
		final Map<Long, String> lista = new TreeMap<>();
		for (final BasesAcessadas elemento : basesAcessadas) {
			lista.put(elemento.getBd().getId(), elemento.getBd().getNome());
		}
		return Collections.unmodifiableMap(lista);
	}	

	public Map<Long, String> getFuncionalidades() {
		final Map<Long, String> lista = new TreeMap<>();
		for (final FuncionalidadesDaAplicacao elemento : funcionalidades) {
			lista.put(elemento.getId(), elemento.getFuncionalidade().getDescricao());
		}
		return Collections.unmodifiableMap(lista);
	}
	
	public synchronized void adicionaFuncionalidade(Funcionalidade value) {
		FuncionalidadesDaAplicacao funcionalidade = new FuncionalidadesDaAplicacao(this, value);
		funcionalidades.add(funcionalidade);
		atualiza();
	}

	public synchronized void adicionaBDAcessado(BancoDeDados value) {
		BasesAcessadas ba = new BasesAcessadas(this, value);
		basesAcessadas.add(ba);
		atualiza();
	}

	public synchronized void merge(Aplicacao value) {
		this.nome = value.getNomeDaAplicacao();
		this.finalidade = value.getFinalidade();
		this.funcionalidades = value.funcionalidades;
		this.basesAcessadas = value.basesAcessadas;
		atualiza();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((basesAcessadas == null) ? 0 : basesAcessadas.hashCode());
		result = prime * result + ((finalidade == null) ? 0 : finalidade.hashCode());
		result = prime * result + ((funcionalidades == null) ? 0 : funcionalidades.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Aplicacao))
			return false;
		Aplicacao other = (Aplicacao) obj;
		if (basesAcessadas == null) {
			if (other.basesAcessadas != null)
				return false;
		} else if (!basesAcessadas.equals(other.basesAcessadas))
			return false;
		if (finalidade == null) {
			if (other.finalidade != null)
				return false;
		} else if (!finalidade.equals(other.finalidade))
			return false;
		if (funcionalidades == null) {
			if (other.funcionalidades != null)
				return false;
		} else if (!funcionalidades.equals(other.funcionalidades))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Aplicacao [nome=" + nome + ", finalidade=" + finalidade + ", basesAcessadas=" + basesAcessadas
				+ ", funcionalidades=" + funcionalidades + "]";
	}

}
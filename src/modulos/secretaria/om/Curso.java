package modulos.secretaria.om;

import java.io.Serializable;

public class Curso implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	private Integer pkCurso;
	private String codigo;
	private String descricao;
	private Integer ordemExibicao;
	private Integer status;
	private UnidadeEscolar unidadeEscolar;
	
	public Integer getPkCurso() {
		return pkCurso;
	}
	public void setPkCurso(Integer pkCurso) {
		this.pkCurso = pkCurso;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getOrdemExibicao() {
		return ordemExibicao;
	}
	public void setOrdemExibicao(Integer ordemExibicao) {
		this.ordemExibicao = ordemExibicao;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public UnidadeEscolar getUnidadeEscolar() {
		return unidadeEscolar;
	}
	public void setUnidadeEscolar(UnidadeEscolar unidadeEscolar) {
		this.unidadeEscolar = unidadeEscolar;
	}
}

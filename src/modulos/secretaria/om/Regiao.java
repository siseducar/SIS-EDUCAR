package modulos.secretaria.om;

import java.io.Serializable;

public class Regiao implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	private Integer pkRegiao;
	private String codigo;
	private String descricao;
	private Integer ordemExibicao;
	private Integer status;
	
	public Integer getPkRegiao() {
		return pkRegiao;
	}
	public void setPkRegiao(Integer pkRegiao) {
		this.pkRegiao = pkRegiao;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getOrdemExibicao() {
		return ordemExibicao;
	}
	public void setOrdemExibicao(Integer ordemExibicao) {
		this.ordemExibicao = ordemExibicao;
	}
}

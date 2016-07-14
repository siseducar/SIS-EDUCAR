package modulos.secretaria.om;

import java.io.Serializable;

public class Religiao implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	private Integer pkReligiao;
	private String codigo;
	private String descricao;
	private Integer ordemExibicao;
	private Integer status;
	
	public Integer getPkReligiao() {
		return pkReligiao;
	}
	public void setPkReligiao(Integer pkReligiao) {
		this.pkReligiao = pkReligiao;
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

package modulos.secretaria.om;

import java.io.Serializable;

public class TipoOcupacao implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	private Integer pkTipoOcupacao;
	private String codigo;
	private String descricao;
	private Integer ordemExibicao;
	private Integer status;
	
	public Integer getPkTipoOcupacao() {
		return pkTipoOcupacao;
	}
	public void setPkTipoOcupacao(Integer pkTipoOcupacao) {
		this.pkTipoOcupacao = pkTipoOcupacao;
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
}

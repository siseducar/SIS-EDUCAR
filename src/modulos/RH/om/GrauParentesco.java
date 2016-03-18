package modulos.RH.om;

public class GrauParentesco {
	
	private Integer pkGrauParentesco;
	private String codigo;
	private String descricao;
	private Integer ordemExibicao;
	private Integer status;
	
	/* GETTERS AND SETTERS */
	public Integer getPkGrauParentesco() {
		return pkGrauParentesco;
	}
	public void setPkGrauParentesco(Integer pkGrauParentesco) {
		this.pkGrauParentesco = pkGrauParentesco;
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

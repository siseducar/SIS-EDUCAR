package modulos.RH.om;

public class SituacaoEconomica 
{
	private Integer pkSituacaoEconomica;
	private String codigo;
	private String descricao;
	private Integer status;
	
	public Integer getPkSituacaoEconomica() {
		return pkSituacaoEconomica;
	}
	public void setPkSituacaoEconomica(Integer pkSituacaoEconomica) {
		this.pkSituacaoEconomica = pkSituacaoEconomica;
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
}

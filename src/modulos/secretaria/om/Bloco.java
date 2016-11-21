package modulos.secretaria.om;

public class Bloco 
{
	private Integer pkBloco;
	private Integer ordemExibicao;
	private String codigo;
	private String descricao;
	private Integer status;
	
	public Integer getPkBloco() {
		return pkBloco;
	}
	public void setPkBloco(Integer pkBloco) {
		this.pkBloco = pkBloco;
	}
	public Integer getOrdemExibicao() {
		return ordemExibicao;
	}
	public void setOrdemExibicao(Integer ordemExibicao) {
		this.ordemExibicao = ordemExibicao;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}

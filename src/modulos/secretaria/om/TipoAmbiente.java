package modulos.secretaria.om;

public class TipoAmbiente 
{
	private Integer pkTipoAmbiente;
	private Integer ordemExibicao;
	private String codigo;
	private String descricao;
	private Integer status;
	
	public Integer getPkTipoAmbiente() {
		return pkTipoAmbiente;
	}
	public void setPkTipoAmbiente(Integer pkTipoAmbiente) {
		this.pkTipoAmbiente = pkTipoAmbiente;
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
	public Integer getOrdemExibicao() {
		return ordemExibicao;
	}
	public void setOrdemExibicao(Integer ordemExibicao) {
		this.ordemExibicao = ordemExibicao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}

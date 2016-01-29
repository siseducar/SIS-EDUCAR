package modulos.RH.om;

public class EstadoCivil
{
	private Integer pkEstadoCivil;
	private String codigo;
	private String descricao;
	private Integer status;
	
	public Integer getPkEstadoCivil() {
		return pkEstadoCivil;
	}
	public void setPkEstadoCivil(Integer pkEstadoCivil) {
		this.pkEstadoCivil = pkEstadoCivil;
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

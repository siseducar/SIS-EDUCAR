package modulos.RH.om;

public class Turno
{
	private Integer pkTurno;
	private String codigo;
	private String descricao;
	private Integer status;
	
	public Integer getPkRaca() {
		return pkTurno;
	}
	public void setPkRaca(Integer pkTurno) {
		this.pkTurno = pkTurno;
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

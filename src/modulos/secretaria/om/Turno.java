package modulos.secretaria.om;

public class Turno
{
	private Integer pkTurno;
	private String codigo;
	private String descricao;
	private Integer ordemExibicao;
	private Integer status;
	

	public Integer getPkTurno() {
		return pkTurno;
	}
	public void setPkTurno(Integer pkTurno) {
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
	public Integer getOrdemExibicao() {
		return ordemExibicao;
	}
	public void setOrdemExibicao(Integer ordemExibicao) {
		this.ordemExibicao = ordemExibicao;
	}
}

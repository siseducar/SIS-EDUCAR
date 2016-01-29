package modulos.RH.om;

public class GrauInstrucao
{
	private Integer pkGrauInstrucao;
	private String codigo;
	private String descricao;
	private Integer status;
	
	public Integer getPkGrauInstrucao() {
		return pkGrauInstrucao;
	}
	public void setPkGrauInstrucao(Integer pkGrauInstrucao) {
		this.pkGrauInstrucao = pkGrauInstrucao;
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

package modulos.secretaria.om;

public class JustificativaFalta 
{
	private Integer pkJustificativaFalta;
	private String codigo;
	private String descricao;
	private Boolean abonavel;
	private Integer status;
	
	public Integer getPkJustificativaFalta() {
		return pkJustificativaFalta;
	}
	public void setPkJustificativaFalta(Integer pkJustificativaFalta) {
		this.pkJustificativaFalta = pkJustificativaFalta;
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
	public Boolean getAbonavel() {
		return abonavel;
	}
	public void setAbonavel(Boolean abonavel) {
		this.abonavel = abonavel;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}

package modulos.RH.om;

public class Etapa
{
	private Integer pkEtapa;
	private String codigo;
	private String descricao;
	private Integer ordemExibicao;
	private Integer status;
	private Curso curso; 
	
	public Integer getPkEtapa() {
		return pkEtapa;
	}
	public void setPkEtapa(Integer pkEtapa) {
		this.pkEtapa = pkEtapa;
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
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
}

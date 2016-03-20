package modulos.secretaria.om;

public class Cidade 
{
	private Integer pkCidade;
	private String nome;
	private Integer codigoibge;
	private Integer codigoContabilMunicipio;
	private String sigla;
	private Integer status;
	private Estado estado;
	
	public Integer getPkCidade() {
		return pkCidade;
	}
	public void setPkCidade(Integer pkEstado) {
		this.pkCidade = pkEstado;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getCodigoibge() {
		return codigoibge;
	}
	public void setCodigoibge(Integer codigoibge) {
		this.codigoibge = codigoibge;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Integer getCodigoContabilMunicipio() {
		return codigoContabilMunicipio;
	}
	public void setCodigoContabilMunicipio(Integer codigoContabilMunicipio) {
		this.codigoContabilMunicipio = codigoContabilMunicipio;
	}
}

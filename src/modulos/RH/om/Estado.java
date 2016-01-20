package modulos.RH.om;

public class Estado 
{
	private Integer pkEstado;
	private String nome;
	private Integer codigoibge;
	private String sigla;
	private Integer status;
	private Pais pais;

	public Integer getPkEstado() {
		return pkEstado;
	}
	public void setPkEstado(Integer pkEstado) {
		this.pkEstado = pkEstado;
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
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
}

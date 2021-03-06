package modulos.secretaria.om;

import java.io.Serializable;

public class Pais implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	private Integer pkPais;
	private String nome;
	private Integer codigobacem;
	private String sigla;
	private Integer status;
	
	public Integer getPkPais() {
		return pkPais;
	}
	public void setPkPais(Integer pkPais) {
		this.pkPais = pkPais;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getCodigobacem() {
		return codigobacem;
	}
	public void setCodigobacem(Integer codigobacem) {
		this.codigobacem = codigobacem;
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
}

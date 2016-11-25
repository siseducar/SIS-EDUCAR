package modulos.sisEducar.om;

import java.sql.Date;

import modulos.secretaria.om.Cidade;

public class Versao 
{
	private Integer pkVersao;
	private String versao;
	private String descricao;
	private Date data;
	private String dataAux;
	private Integer status;
	private Boolean visualizado;
	private Cidade fkMunicipioCliente;
	
	public Integer getPkVersao() {
		return pkVersao;
	}
	public void setPkVersao(Integer pkVersao) {
		this.pkVersao = pkVersao;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDataAux() {
		return dataAux;
	}
	public void setDataAux(String dataAux) {
		this.dataAux = dataAux;
	}
	public Boolean getVisualizado() {
		return visualizado;
	}
	public void setVisualizado(Boolean visualizado) {
		this.visualizado = visualizado;
	}
	public Cidade getFkMunicipioCliente() {
		return fkMunicipioCliente;
	}
	public void setFkMunicipioCliente(Cidade fkMunicipioCliente) {
		this.fkMunicipioCliente = fkMunicipioCliente;
	}
}

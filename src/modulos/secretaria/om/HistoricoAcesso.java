package modulos.secretaria.om;

import java.sql.Timestamp;

public class HistoricoAcesso 
{
	private Integer pkHistoricoAcesso;
	private Timestamp dataLogin;
	private String dataLoginAux;
	private Integer status;
	private Usuario usuario;
	private Cidade fkMunicipioCliente;
	
	public Integer getPkHistoricoAcesso() {
		return pkHistoricoAcesso;
	}
	public void setPkHistoricoAcesso(Integer pkHistoricoAcesso) {
		this.pkHistoricoAcesso = pkHistoricoAcesso;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getDataLoginAux() {
		return dataLoginAux;
	}
	public void setDataLoginAux(String dataLoginAux) {
		this.dataLoginAux = dataLoginAux;
	}
	public Timestamp getDataLogin() {
		return dataLogin;
	}
	public void setDataLogin(Timestamp dataLogin) {
		this.dataLogin = dataLogin;
	}
	public Cidade getFkMunicipioCliente() {
		return fkMunicipioCliente;
	}
	public void setFkMunicipioCliente(Cidade fkMunicipioCliente) {
		this.fkMunicipioCliente = fkMunicipioCliente;
	}
}

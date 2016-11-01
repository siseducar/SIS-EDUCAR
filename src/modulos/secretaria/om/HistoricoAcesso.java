package modulos.secretaria.om;

import java.sql.Date;

public class HistoricoAcesso 
{
	private Integer pkHistoricoAcesso;
	private Date dataLogin;
	private Integer status;
	private Usuario usuario;
	
	public Integer getPkHistoricoAcesso() {
		return pkHistoricoAcesso;
	}
	public void setPkHistoricoAcesso(Integer pkHistoricoAcesso) {
		this.pkHistoricoAcesso = pkHistoricoAcesso;
	}
	public Date getDataLogin() {
		return dataLogin;
	}
	public void setDataLogin(Date dataLogin) {
		this.dataLogin = dataLogin;
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
}

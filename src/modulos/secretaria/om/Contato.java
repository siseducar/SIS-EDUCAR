package modulos.secretaria.om;

public class Contato 
{
	private int pkContato;
	private String telComercial;
	private String telResidencial;
	private String telCelular;
	private int status;
	private Boolean enviarNotificacao;
	
	public int getPkContato() {
		return pkContato;
	}
	public void setPkContato(int pkContato) {
		this.pkContato = pkContato;
	}
	public String getTelComercial() {
		return telComercial;
	}
	public void setTelComercial(String telComercial) {
		this.telComercial = telComercial;
	}
	public String getTelResidencial() {
		return telResidencial;
	}
	public void setTelResidencial(String telResidencial) {
		this.telResidencial = telResidencial;
	}
	public String getTelCelular() {
		return telCelular;
	}
	public void setTelCelular(String telCelular) {
		this.telCelular = telCelular;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Boolean getEnviarNotificacao() {
		return enviarNotificacao;
	}
	public void setEnviarNotificacao(Boolean enviarNotificacao) {
		this.enviarNotificacao = enviarNotificacao;
	}
}

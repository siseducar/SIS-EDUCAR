package sisEdcuar.om;

import java.io.Serializable;

import modulos.secretaria.om.Cidade;

public class ChaveAcesso implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
	private Integer pkChaveAcesso;
	private String chave;
	private Integer status;
	private Cidade municipioCliente;
	
	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Cidade getMunicipioCliente() {
		return municipioCliente;
	}
	public void setMunicipioCliente(Cidade municipioCliente) {
		this.municipioCliente = municipioCliente;
	}
	public Integer getPkChaveAcesso() {
		return pkChaveAcesso;
	}
	public void setPkChaveAcesso(Integer pkChaveAcesso) {
		this.pkChaveAcesso = pkChaveAcesso;
	}
}

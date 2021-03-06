package modulos.secretaria.om;

import java.io.Serializable;

public class PermissaoUsuario implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	private Integer status;
	private Usuario usuario;
	private Permissao permissao;
	private Cidade fkMunicipioCliente;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	public Cidade getFkMunicipioCliente() {
		return fkMunicipioCliente;
	}

	public void setFkMunicipioCliente(Cidade fkMunicipioCliente) {
		this.fkMunicipioCliente = fkMunicipioCliente;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

package br.com.sisEducar.modulos.RH.servlet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.sisEducar.modulos.RH.om.Usuario;
import br.com.sisEducar.modulos.sisEducar.sisEducarServlet.SisEducarServlet;

@ManagedBean(name="usuarioServlet")
@SessionScoped
public class UsuarioServlet extends SisEducarServlet
{
	//Variaveis
	Usuario usuario;
	
	/**
	 * Construtor
	 */
	public UsuarioServlet()
	{
		usuario = new Usuario();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}

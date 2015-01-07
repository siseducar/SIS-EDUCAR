package br.com.sisEducar.modulos.RH.servlet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.sisEducar.modulos.RH.om.Usuario;
import br.com.sisEducar.modulos.sisEducar.sisEducarServlet.SisEducarServlet;
import br.com.sisEducar.modulos.sisEducar.utils.ConstantesSisEducar;
import br.com.sisEducar.modulos.sisEducar.utils.Logs;

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
	
	/**
	 * Método generico para redirecionamento de tela
	 * @param idTela (1 = TelaCadastroUsuario, 2 = TelaCadastrUsuarioSimples, 3 = TelaRecuperarSenha)
	 * @return String
	 */
	public void redirecionarTela(int idTela)
	{
		try 
		{
			if(idTela == 1)
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesSisEducar.PATH_PROJETO + "modulos/RH/cadastros/telaCadastroUsuario.jsf");  
			}
			else if (idTela == 2)
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesSisEducar.PATH_PROJETO + "modulos/RH/cadastros/telaCadastroUsuarioSimples.jsf");  
			}
			else if (idTela == 3)
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesSisEducar.PATH_PROJETO + "modulos/RH/cadastros/telaRecuperarSenha.jsf");  
			}
		}
		catch (Exception e) 
		{
			Logs.addError("Redirecionar tela", "Erro ao redirecionar para a tela, contate o administrador.");
		}
	}
}

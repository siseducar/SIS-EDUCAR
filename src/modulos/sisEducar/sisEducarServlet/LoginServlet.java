package modulos.sisEducar.sisEducarServlet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import modulos.RH.dao.UsuarioDAO;
import modulos.RH.om.Usuario;
import modulos.sisEducar.utils.ConstantesSisEducar;
import modulos.sisEducar.utils.Logs;
import sun.misc.BASE64Encoder;

@SessionScoped
@ManagedBean(name= "loginServlet")
public class LoginServlet extends SisEducarServlet
{
	//Objetos e variaveis
	private String nomeUsuarioLogado;
	
	Usuario usuario;  
	
	/**
	 * Construtor
	 */
	public LoginServlet ()
	{
		usuario = new Usuario();
	}
	
	
	/**
	 * O m�todo � usado para validar se existe um usuario no banco com as informa��es passadas pelo usuario (nome, senha)
	 * @return String
	 */
	public void validarLogin()
	{
		try 
		{
			Boolean resultado = false;
			SisEducarServlet sisEducarServlet = new SisEducarServlet();
			
			if(usuario.getNome()!=null && usuario.getNome().length() == 0 && usuario.getSenha()!=null && usuario.getSenha().length() == 0)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Os campos usu�rio/senha s�o obrigat�rios", null));  
			}
			else if(usuario.getNome()!=null && usuario.getNome().length() == 0)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo usu�rio � obrigat�rio", null));  
			}
			else if(usuario.getSenha()!=null && usuario.getSenha().length() == 0)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo senha � obrigat�rio", null));  
			}
			
			if(usuario!=null && usuario.getNome()!=null && usuario.getSenha()!=null)
			{
				if(usuario.getNome().length() > 0 && usuario.getSenha().length() >0)
				{
					usuario.setSenha(criptografarSenha(usuario.getSenha()));
					
					resultado = new UsuarioDAO().validarUsuario(usuario);
					if(resultado)
					{
						sisEducarServlet.putSessionObject(ConstantesSisEducar.USUARIO_LOGADO, usuario);
						FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesSisEducar.PATH_PROJETO_NOME + "/resources/templates/sisEducar/principal.xhtml");
					}
					else
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usu�rio/Senha inv�lidos", null));  
					}
				}
			}
			
		} 
		catch (Exception e) 
		{
			Logs.addError("Validar login", "Erro ao validar o login, contate o administrador.");
		}
	}
	
	/**
	 * M�todo usado para deslogar um usu�rio, perdendo a sess�o do mesmo
	 */
	public void encerrarSessao()
	{
		try
		{
			//FacesContext fc = FacesContext.getCurrentInstance();  
		    //HttpSession session = (HttpSession)fc.getExternalContext().getSession(false);  
		    //session.invalidate(); 

			FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesSisEducar.PATH_PROJETO_NOME + "modulos/sisEducar/mainSisEducar.jsf");
		}
		catch (Exception e)
		{
			Logs.addError("Encerrar sess�o", "Erro ao encerrar a sess�o, contate o administrador.");
		}
	}
	
	/**
	 * O m�todo � usado para criptografar senhas
	 * @param senha sem modifica��es
	 * @return senha modificada
	 */
	public static String criptografarSenha (String senha) 
	{     
		try 
		{
			MessageDigest digest = MessageDigest.getInstance("SHA1");
			digest.update(senha.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(digest.digest());
		} 
		catch (NoSuchAlgorithmException ns) 
		{
			return null;
		}      
	}
	
	/**
	 * O m�todo � usado para verificar o horario atual e transmitir uma mensagem de boas vindas ao usu�rio de acordo com o horario
	 * @param date
	 * @return
	 */
	public void validaPeriodoHorario(Date date)
	{
		try 
		{
			@SuppressWarnings("deprecation")
			Integer hora = date.getHours();
			
			if(hora >=0 && hora <=5)
			{
				nomeUsuarioLogado = "Boa noite: " + usuario.getNome();
			}
			else if(hora >=6 && hora <=11)
			{
				nomeUsuarioLogado = "Bom dia: " + usuario.getNome();
			}
			else if(hora >=12 && hora <=17)
			{
				nomeUsuarioLogado = "Boa tarde: " + usuario.getNome();
			}
			else if(hora >=18)
			{
				nomeUsuarioLogado = "Boa noite: " + usuario.getNome();
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("Validar per�odo", "Erro ao validar per�odo do dia, contate o administrador.");
		}
	}
	
	/* Getters and Setters */
	public Usuario getUsuario() 			{ return usuario; }
	public void setUsuario(Usuario usuario) { this.usuario = usuario; }

	public String getNomeUsuarioLogado()	{ return nomeUsuarioLogado; }
	public void setNomeUsuarioLogado(String nomeUsuarioLogado) { this.nomeUsuarioLogado = nomeUsuarioLogado; } 
}

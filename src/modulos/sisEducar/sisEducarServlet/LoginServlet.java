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
	 * O método é usado para validar se existe um usuario no banco com as informações passadas pelo usuario (nome, senha)
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
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Os campos usuário/senha são obrigatórios", null));  
			}
			else if(usuario.getNome()!=null && usuario.getNome().length() == 0)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo usuário é obrigatório", null));  
			}
			else if(usuario.getSenha()!=null && usuario.getSenha().length() == 0)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo senha é obrigatório", null));  
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
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário/Senha inválidos", null));  
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
	 * Método usado para deslogar um usuário, perdendo a sessão do mesmo
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
			Logs.addError("Encerrar sessão", "Erro ao encerrar a sessão, contate o administrador.");
		}
	}
	
	/**
	 * O método é usado para criptografar senhas
	 * @param senha sem modificações
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
	 * O método é usado para verificar o horario atual e transmitir uma mensagem de boas vindas ao usuário de acordo com o horario
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
			Logs.addError("Validar período", "Erro ao validar período do dia, contate o administrador.");
		}
	}
	
	/* Getters and Setters */
	public Usuario getUsuario() 			{ return usuario; }
	public void setUsuario(Usuario usuario) { this.usuario = usuario; }

	public String getNomeUsuarioLogado()	{ return nomeUsuarioLogado; }
	public void setNomeUsuarioLogado(String nomeUsuarioLogado) { this.nomeUsuarioLogado = nomeUsuarioLogado; } 
}

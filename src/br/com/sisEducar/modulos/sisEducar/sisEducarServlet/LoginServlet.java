package br.com.sisEducar.modulos.sisEducar.sisEducarServlet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import sun.misc.BASE64Encoder;
import br.com.sisEducar.modulos.RH.dao.UsuarioDAO;
import br.com.sisEducar.modulos.RH.om.Usuario;
import br.com.sisEducar.modulos.RH.utils.ConstantesRH;
import br.com.sisEducar.modulos.sisEducar.utils.ConstantesSisEducar;
import br.com.sisEducar.modulos.sisEducar.utils.Logs;

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
	 * M�todo usado para cadastrar um usuaario no banco de dados
	 */
	public String cadastrarUsuario()
	{
		try
		{
			boolean cadastradoSucesso = false;
			
			//Campos obrigat�rios
			if(usuario.getNome().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Nome � obrigat�rio", null));
				return "Usuario";
			}
			if(usuario.getSenha().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Senha � obrigat�rio", null));
				return "Senha";
			}
			if(usuario.getConfirmarSenha().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Confirmar senha � obrigat�rio", null));
				return "Confirmar Senha";
			}
			if(usuario.getEmail().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Email � obrigat�rio", null));
				return "Email";
			}
			if(usuario.getConfirmarEmail().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Confirmar email � obrigat�rio", null));
				return "Confirmar Email";
			}
			
			//Validar Email
			if(!usuario.getEmail().contains("@"))
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "O email n�o � v�lido", null));
				return "Email";
			}
			
			if(usuario.getEmail().length() <8)
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "O email � muito curto", null));
				return "Emails muito curto";
			}
			
			if(!usuario.getEmail().equals(usuario.getConfirmarEmail()))
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Os emails est�o diferentes", null));
				return "Emails Diferentes";
			}
			
			//Validar Senha
			if(usuario.getSenha().length() <8)
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha deve conter 8 d�gitos", null));
				return "Senha muito curta";
			}
			
			if(!usuario.getSenha().equals(usuario.getConfirmarSenha()))
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas est�o diferentes", null));
				return "Senhas Diferentes";
			}
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			cadastradoSucesso = new UsuarioDAO().inserirUsuario(usuario);
			if(cadastradoSucesso)
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_INFO, "Usu�rio cadastrado com sucesso", null));
				limparComponentes(true);
				return "Sucesso";
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar usu�rio", null));
				return "Erro";
			}
		}
		catch (Exception e) 
		{
			Logs.addError("Cadastrar usu�rio", "Erro ao cadastrar usu�rio, contate o administrador.");
			return "";
		}
	}
	
	/**
	 * O m�todo � usado para validar se existe um usuario no banco com as informa��es passadas pelo usuario (nome, senha)
	 * @return String
	 */
	public String validarLogin()
	{
		try 
		{
			Date date = new Date();
			
			if(usuario.getNome().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Usu�rio � obrigat�rio", null));
				return "Usuario";
			}
			else if(usuario.getSenha().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Senha � obrigat�rio", null));
				return "Senha";
			}
			
			//Criptografa a senha e faz a consulta no banco para ver se tem o usu�rio
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			Boolean result = new UsuarioDAO().validarUsuario(usuario); 
			
			//Seta o usuario logado na variavel
			
			validaPeriodoHorario(date);
			if(result)
			{
				 FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesSisEducar.PATH_PROJETO + "modulos/modulos.jsf");  
				 return "Redirecionar";
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usu�rio/Senha incorreto", null));  
				return "Senha";
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("Validar login", "Erro ao validar o login, contate o administrador.");
			return null;
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

			FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesSisEducar.PATH_PROJETO + "modulos/sisEducar/mainSisEducar.jsf");
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
	 * O m�todo � usado para limpar os componentes da tela de usuario
	 * @param limpar
	 */
	public void limparComponentes(Boolean limpar)
	{
		try 
		{
			if(limpar)
			{
				usuario.setNome(null);
				usuario.setSenha(null);
				usuario.setConfirmarSenha(null);
				usuario.setEmail(null);
				usuario.setConfirmarEmail(null);
				usuario.setTipo(new ConstantesRH().getTIPO_USUARIO_ADMIN());
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("Limpar componentes da tela", "Erro ao limpar os componentes da tela, contate o administrador.");
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

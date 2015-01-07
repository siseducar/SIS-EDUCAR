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
	 * Método usado para cadastrar um usuaario no banco de dados
	 */
	public String cadastrarUsuario()
	{
		try
		{
			boolean cadastradoSucesso = false;
			
			//Campos obrigatórios
			if(usuario.getNome().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Nome é obrigatório", null));
				return "Usuario";
			}
			if(usuario.getSenha().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Senha é obrigatório", null));
				return "Senha";
			}
			if(usuario.getConfirmarSenha().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Confirmar senha é obrigatório", null));
				return "Confirmar Senha";
			}
			if(usuario.getEmail().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Email é obrigatório", null));
				return "Email";
			}
			if(usuario.getConfirmarEmail().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Confirmar email é obrigatório", null));
				return "Confirmar Email";
			}
			
			//Validar Email
			if(!usuario.getEmail().contains("@"))
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "O email não é válido", null));
				return "Email";
			}
			
			if(usuario.getEmail().length() <8)
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "O email é muito curto", null));
				return "Emails muito curto";
			}
			
			if(!usuario.getEmail().equals(usuario.getConfirmarEmail()))
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Os emails estão diferentes", null));
				return "Emails Diferentes";
			}
			
			//Validar Senha
			if(usuario.getSenha().length() <8)
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha deve conter 8 dígitos", null));
				return "Senha muito curta";
			}
			
			if(!usuario.getSenha().equals(usuario.getConfirmarSenha()))
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas estão diferentes", null));
				return "Senhas Diferentes";
			}
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			cadastradoSucesso = new UsuarioDAO().inserirUsuario(usuario);
			if(cadastradoSucesso)
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário cadastrado com sucesso", null));
				limparComponentes(true);
				return "Sucesso";
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar usuário", null));
				return "Erro";
			}
		}
		catch (Exception e) 
		{
			Logs.addError("Cadastrar usuário", "Erro ao cadastrar usuário, contate o administrador.");
			return "";
		}
	}
	
	/**
	 * O método é usado para validar se existe um usuario no banco com as informações passadas pelo usuario (nome, senha)
	 * @return String
	 */
	public String validarLogin()
	{
		try 
		{
			Date date = new Date();
			
			if(usuario.getNome().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário é obrigatório", null));
				return "Usuario";
			}
			else if(usuario.getSenha().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_WARN, "Senha é obrigatório", null));
				return "Senha";
			}
			
			//Criptografa a senha e faz a consulta no banco para ver se tem o usuário
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
				FacesContext.getCurrentInstance().addMessage("validaSenhasLogin", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário/Senha incorreto", null));  
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
	 * Método usado para deslogar um usuário, perdendo a sessão do mesmo
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
	 * O método é usado para limpar os componentes da tela de usuario
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

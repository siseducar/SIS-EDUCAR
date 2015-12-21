package modulos.sisEducar.sisEducarServlet;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import modulos.RH.dao.AlunoDAO;
import modulos.RH.dao.UsuarioDAO;
import modulos.RH.om.Usuario;
import modulos.sisEducar.om.Email;
import modulos.sisEducar.utils.ConstantesSisEducar;
import modulos.sisEducar.utils.EmailUtils;
import modulos.sisEducar.utils.Logs;
import sun.misc.BASE64Encoder;

@SessionScoped
@ManagedBean(name= "loginServlet")
public class LoginServlet extends SisEducarServlet implements Serializable
{
	private static final long serialVersionUID = 1L;

	//Objetos e variaveis
	private String nomeUsuarioLogado;
	private String emailRedefinicaoSenha = null;
	
	Usuario usuario = new Usuario();  
	Usuario usuarioTemporario = new Usuario();  
	
	/**
	 * O m�todo � usado para validar se existe um usuario no banco com as informa��es passadas pelo usuario (nome, senha)
	 * @author Jo�o Paulo
	 * @return String
	 */
	public void validarLogin()
	{
		try 
		{ 
			Boolean resultado = false;
			SisEducarServlet sisEducarServlet = new SisEducarServlet();
			
			if(usuario.getNome()!=null && usuario.getNome().length() == 0)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usu�rio � obrigat�rio", null));  
			}
			else if(usuario.getSenha()!=null && usuario.getSenha().length() == 0)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Senha � obrigat�rio", null));  
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
						resetarUsuario();
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
			Logs.addFatal("Validar login", "Erro ao validar o login, contate o administrador.");
		}
	}
	
	/**
	 * M�todo usado para cadastrar um usu�rio simples na tela de login
	 * @author Jo�o Paulo
	 */
	public String cadastrarUsuarioSimples()
	{
		try 
		{
			Map<String, String> destinatarios = new HashMap<String, String>();
			Boolean resultado = false;
			Boolean resultadoExistenciaUsuario = false;
			Boolean resultadoExistenciaAluno = false;
			Boolean resultadoEnvioEmail = false;
			Email email = null;
			String urlBotaoLink = "http://localHost:8080/SIS-EDUCAR/validacaoUsuario.xhtml?validacao=";
			
			if(usuario.getRaAluno().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O R.A. � obrigat�rio", null));
				return null;
			}
			
			resultadoExistenciaAluno = new AlunoDAO().verificaExistenciaAluno(usuario.getRaAluno());
			//Se vier false � porque o ra do aluno n�o existe
			if(!resultadoExistenciaAluno)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O R.A. n�o � v�lido", null));
				return null;
			}
			else
			{
				/*Se vier TRUE � porque o usu�rio pode ser adicionado, se vier false � porque o usu�rio n�o tem permiss�o para ser cadastrado no sistema
				caso vier false � porque o responsavel do aluno deixou esta pessoa como 1 dos responsaveis pelo aluno*/
				
				if(usuario.getCpfcnpj().isEmpty())
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "CPF/CNPJ � obrigat�rio", null));
					return null;
				}
				else
				{
					resultadoExistenciaUsuario = new UsuarioDAO().verificaExistenciaResponsavel(usuario.getCpfcnpj());
				}
				
				if(!resultadoExistenciaUsuario)
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "CPF/CNPJ inv�lido ou o usu�rio n�o tem permiss�o para se registrar, por favor entre em contato com a administra��o", null));
					return null;
				}
			}
			
			if(usuario.getEmail().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O email � obrigat�rio", null));
				return null;
			}
			
			if(!usuario.getEmail().contains("@") || !usuario.getEmail().contains("."))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O email � inv�lido", null));
				return null;
			}
			
			if(usuario.getNome().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O usu�rio � obrigat�rio", null));
				return null;
			}
			
			/*
			 * Valida��o de senha
			 * <Senha> -----------------------------
			 */
			if(usuario.getSenha().length() !=8 && usuario.getConfirmarSenha().length() !=8)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha deve ter no m�nimo 8 d�gitos", null));
				return null;
			}
			
			if(usuario.getSenha().equals("12345678"))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha n�o pode ser sequencial", null));
				return null;
			}
			
			if(!usuario.getSenha().equals(usuario.getConfirmarSenha()))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas est�o diferentes", null));
				return null;
			}
			/**
			 * </Senha> -----------------------------
			 */
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			resultado = new UsuarioDAO().inserirUsuario(usuario);
			
			if(resultado)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviamos um email de confirma��o para o email informado", null));  
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usu�rio n�o registrado", null));  
			}
			
			urlBotaoLink += SisEducarServlet.criptografarURL(true, usuario.getEmail());
			
			email = EmailUtils.inicializarPropriedades();
			email.setSubjectMail("Confirma��o de cadastro de usu�rio");
			email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Ol� " + usuario.getNome() + ",</p> " + 
					" <p style=\"text-align:left; font-size:17px; \">A sua solicita��o de cadastro foi realizada com sucesso.</p> " + 
					" <p style=\"font-style:italic; font-size:17px; text-align:left;\"><b>Para que o cadastro seja efetivado clique no bot�o abaixo. Aten��o o link ir� expirar em 48 horas.</b></p>", "<p style=\"font-size:17px; text-align:left;\">Caso o bot�o acima n�o funcione clique no link abaixo:</p>", urlBotaoLink, urlBotaoLink, true, "Ativar Usu�rio"));
			
			destinatarios.put(usuario.getEmail(), usuario.getNome());
			email.setToMailsUsers(destinatarios);
			
			resultadoEnvioEmail = new EmailUtils().enviarEmail(email);
			
			resetarUsuario();
			return null;
		}
		catch (Exception e) 
		{
			Logs.addFatal("Erro ao cadastrar!", "cadastrarUsuarioSimples");
			return null;
		}
	}
	
	/**
	 * O m�todo � usado para enviar um email para o usuario que quiser realizar a recupera��o de senha dele
	 * @author Jo�o Paulo
	 */
	public void enviarEmailRedefinicaoSenha()
	{
		try 
		{
			Usuario usuario = null;
			Map<String, String> destinatarios = new HashMap<String, String>();
			Boolean resultadoEnvioEmail = false;
			String urlBotaoLink = "http://localHost:8080/SIS-EDUCAR/redefinirSenha.xhtml?redefinir=";
			Email email = new Email();
			String parametro = "&validacao=";
			
			if(emailRedefinicaoSenha!=null && emailRedefinicaoSenha.length() >0)
			{
				usuario = new UsuarioDAO().obtemUsuario(emailRedefinicaoSenha, ConstantesSisEducar.STATUS_ATIVO);
				
				if(usuario!=null)
				{
					urlBotaoLink += criptografarURL(true, emailRedefinicaoSenha);
					email = EmailUtils.inicializarPropriedades();
					email.setSubjectMail("SIS-EDUCAR - Redefini��o de Senha");
					email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Ol� " + usuario.getNome() + ",</p> " + 
							" <p style=\"text-align:left; font-size:17px; \">Recebemos uma solicita��o de refini��o de senha para eu usu�rio, se n�o foi voc� que efetuou esta solicita��o por favor desconsidere o email.</p> " + 
							" <p style=\"font-style:italic; font-size:17px; text-align:left;\"><b>Para continuar o processo de redefini��o de senha clique no bot�o abaixo.</b></p>", "<p style=\"font-size:17px; text-align:left;\">Caso o bot�o acima n�o funcione clique no link abaixo:</p>", urlBotaoLink, urlBotaoLink, true, "Redefinir Senha"));
					
					destinatarios.put(emailRedefinicaoSenha, emailRedefinicaoSenha);
					email.setToMailsUsers(destinatarios);
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ser� enviado uma notifica��o para o email informado", null));
					resultadoEnvioEmail = new EmailUtils().enviarEmail(email);
					
					if(!resultadoEnvioEmail)
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "N�o foi poss�vel enviar o email", null));
					}
				}
				else
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "N�o existe usu�rio cadastrado com este email", null));
				}
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O email � obrigat�rio", null));
			}
		}
		catch (Exception e) 
		{
			Logs.addError("Enviar email redefinicao senha", "");
		}
	}
	
	/**
	 * Realiza um update no banco para trocar a senha do usuario
	 */
	public String redefinirSenha()
	{
		try 
		{
			String novaSenha = "";
			Boolean respostaUpdate = false;
			
			usuarioTemporario = (Usuario) getSessionObject(ConstantesSisEducar.USUARIO_LOGADO);
			putSessionObject(ConstantesSisEducar.USUARIO_LOGADO, null);
			
			if(usuario!=null && (!usuario.getSenha().isEmpty() || !usuario.getConfirmarSenha().isEmpty()))
			{
				if(usuario.getSenha().length() <8 || usuario.getConfirmarSenha().length() <8)
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha deve ter 8 caracteres", null));
					return null;
				}
				
				if(!usuario.getSenha().equals(usuario.getConfirmarSenha()))
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas s�o diferentes", null));
					return null;
				}
				else
				{
					novaSenha = criptografarSenha(usuario.getSenha());
					respostaUpdate = new UsuarioDAO().redefinirSenha(usuarioTemporario.getPkUsuario(), novaSenha);
					
					if(respostaUpdate)
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha foi atualizada", null));
					}
					else
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "N�o foi poss�vel redefinir a senha", null));
					}
				}
				
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas s�o obrigat�rias", null));
				return null;
			}
			
			return null;
		}
		catch (Exception e) 
		{
			Logs.addError("redefinirSenha", "");
			return null;
		}
	}
	
	/**
	 * M�todo usado para resetar o usuario, criando uma nova instancia do usuario
	 */
	public void resetarUsuario()
	{
		try
		{
			usuario = new Usuario();
		}
		catch (Exception e) 
		{
			Logs.addFatal("Resetar", "Falha ao resetar o usu�rio");
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
	
	/* Getters and Setters */
	public Usuario getUsuario() 			{ return usuario; }
	public void setUsuario(Usuario usuario) { this.usuario = usuario; }

	public String getNomeUsuarioLogado()	{ return nomeUsuarioLogado; }
	public void setNomeUsuarioLogado(String nomeUsuarioLogado) { this.nomeUsuarioLogado = nomeUsuarioLogado; }

	public String getEmailRedefinicaoSenha() {
		return emailRedefinicaoSenha;
	}

	public void setEmailRedefinicaoSenha(String emailRedefinicaoSenha) {
		this.emailRedefinicaoSenha = emailRedefinicaoSenha;
	} 
}

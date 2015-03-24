package modulos.sisEducar.sisEducarServlet;

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
public class LoginServlet extends SisEducarServlet
{
	//Objetos e variaveis
	private String nomeUsuarioLogado;
	
	Usuario usuario = new Usuario();  
	
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
			String urlBotaoLink = ConstantesSisEducar.PATH_PROJETO_NOME + "/validacao=";
			
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
			
			if(usuario.getSenha().length() !=8 && usuario.getConfirmarSenha().length() !=8)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha deve ter 8 d�gitos", null));
				return null;
			}
			
			if(!usuario.getSenha().equals(usuario.getConfirmarSenha()))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas s�o diferentes", null));
				return null;
			}
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			resultado = new UsuarioDAO().inserirUsuario(usuario);
			
			if(resultado)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviamos um email de valida��o para sua caixa de email", null));  
				resetarUsuario();
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usu�rio n�o registrado", null));  
			}
			
			urlBotaoLink += SisEducarServlet.criptografarURL(true, usuario.getEmail());
			
			email = EmailUtils.inicializarPropriedades();
			email.setSubjectMail("Valida��o de registro SIS-EDUCAR");
			email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Ol� " + usuario.getNome() + ",</p> " + 
					" <p style=\"text-align:left; font-size:17px; \">A sua solicita��o de cadastro foi realizada com sucesso.</p> " + 
					" <p style=\"font-style:italic; font-size:17px; text-align:left;\"><b>Para que o cadastro seja efetivado clique no bot�o abaixo.</b></p>", "<p style=\"font-size:17px; text-align:left;\">Caso o bot�o acima n�o funcione clique no link abaixo:</p>", urlBotaoLink, urlBotaoLink, true, "Ativar Usu�rio"));
			
			destinatarios.put(usuario.getEmail(), usuario.getNome());
			email.setToMailsUsers(destinatarios);
			
			resultadoEnvioEmail = new EmailUtils().enviarEmail(email);
			return null;
		}
		catch (Exception e) 
		{
			Logs.addFatal("Erro ao cadastrar!", "cadastrarUsuarioSimples");
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
}

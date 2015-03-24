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
	 * O método é usado para validar se existe um usuario no banco com as informações passadas pelo usuario (nome, senha)
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
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário é obrigatório", null));  
			}
			else if(usuario.getSenha()!=null && usuario.getSenha().length() == 0)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Senha é obrigatório", null));  
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
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário/Senha inválidos", null));  
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
	 * Método usado para cadastrar um usuário simples na tela de login
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
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O R.A. é obrigatório", null));
				return null;
			}
			
			resultadoExistenciaAluno = new AlunoDAO().verificaExistenciaAluno(usuario.getRaAluno());
			//Se vier false é porque o ra do aluno não existe
			if(!resultadoExistenciaAluno)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O R.A. não é válido", null));
				return null;
			}
			else
			{
				/*Se vier TRUE é porque o usuário pode ser adicionado, se vier false é porque o usuário não tem permissão para ser cadastrado no sistema
				caso vier false é porque o responsavel do aluno deixou esta pessoa como 1 dos responsaveis pelo aluno*/
				
				if(usuario.getCpfcnpj().isEmpty())
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "CPF/CNPJ é obrigatório", null));
					return null;
				}
				else
				{
					resultadoExistenciaUsuario = new UsuarioDAO().verificaExistenciaResponsavel(usuario.getCpfcnpj());
				}
				
				if(!resultadoExistenciaUsuario)
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "CPF/CNPJ inválido ou o usuário não tem permissão para se registrar, por favor entre em contato com a administração", null));
					return null;
				}
			}
			
			if(usuario.getEmail().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O email é obrigatório", null));
				return null;
			}
			
			if(!usuario.getEmail().contains("@") || !usuario.getEmail().contains("."))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O email é inválido", null));
				return null;
			}
			
			if(usuario.getNome().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O usuário é obrigatório", null));
				return null;
			}
			
			if(usuario.getSenha().length() !=8 && usuario.getConfirmarSenha().length() !=8)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha deve ter 8 dígitos", null));
				return null;
			}
			
			if(!usuario.getSenha().equals(usuario.getConfirmarSenha()))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas são diferentes", null));
				return null;
			}
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			resultado = new UsuarioDAO().inserirUsuario(usuario);
			
			if(resultado)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviamos um email de validação para sua caixa de email", null));  
				resetarUsuario();
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não registrado", null));  
			}
			
			urlBotaoLink += SisEducarServlet.criptografarURL(true, usuario.getEmail());
			
			email = EmailUtils.inicializarPropriedades();
			email.setSubjectMail("Validação de registro SIS-EDUCAR");
			email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Olá " + usuario.getNome() + ",</p> " + 
					" <p style=\"text-align:left; font-size:17px; \">A sua solicitação de cadastro foi realizada com sucesso.</p> " + 
					" <p style=\"font-style:italic; font-size:17px; text-align:left;\"><b>Para que o cadastro seja efetivado clique no botão abaixo.</b></p>", "<p style=\"font-size:17px; text-align:left;\">Caso o botão acima não funcione clique no link abaixo:</p>", urlBotaoLink, urlBotaoLink, true, "Ativar Usuário"));
			
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
	 * Método usado para resetar o usuario, criando uma nova instancia do usuario
	 */
	public void resetarUsuario()
	{
		try
		{
			usuario = new Usuario();
		}
		catch (Exception e) 
		{
			Logs.addFatal("Resetar", "Falha ao resetar o usuário");
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
	
	/* Getters and Setters */
	public Usuario getUsuario() 			{ return usuario; }
	public void setUsuario(Usuario usuario) { this.usuario = usuario; }

	public String getNomeUsuarioLogado()	{ return nomeUsuarioLogado; }
	public void setNomeUsuarioLogado(String nomeUsuarioLogado) { this.nomeUsuarioLogado = nomeUsuarioLogado; } 
}

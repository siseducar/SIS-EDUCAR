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

import modulos.secretaria.dao.AlunoDAO;
import modulos.secretaria.dao.UsuarioDAO;
import modulos.secretaria.om.Usuario;
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
	private String nomeUsuarioLogado = "";
	private String emailRedefinicaoSenha = null;
	
	Usuario usuario = new Usuario();  
	private Usuario usuarioLogado = new Usuario();  
	Usuario usuarioTemporario = new Usuario();  
	
	public String generoMasculino = "none";
	public String generoFeminino = "none";
	
	private String senhaAtual = "";
	
	public LoginServlet()
	{
		//Aqui eu pego o nome do usuário logado e seto a variável global
		if(new SisEducarServlet().getSessionObject(ConstantesSisEducar.USUARIO_LOGADO)!=null)
		{
			SisEducarServlet.usuarioLogado = (Usuario)new SisEducarServlet().getSessionObject(ConstantesSisEducar.USUARIO_LOGADO);
			
			setUsuarioLogado(SisEducarServlet.usuarioLogado);
			
			//Essa variável contem o nome do usuário logado para que seja exebida na tela principal
			nomeUsuarioLogado = SisEducarServlet.usuarioLogado.getNome();
			if(SisEducarServlet.usuarioLogado.getGenero().equals("masculino"))
			{
				generoMasculino = "initial";
			}
			else
			{
				generoFeminino = "initial";
			}
		}
	}
	/**
	 * O método é usado para validar se existe um usuario no banco com as informações passadas pelo usuario (nome, senha)
	 * @author João Paulo
	 * @return String
	 */
	public void validarLogin()
	{
		try 
		{ 
			Usuario resultadoUsuario = null;
			SisEducarServlet sisEducarServlet = new SisEducarServlet();
			//Remove espaços da string
			usuario.setNome(usuario.getNome().trim());
			
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
					
					resultadoUsuario = new UsuarioDAO().validarUsuario(usuario);
					if(resultadoUsuario!=null)
					{
						sisEducarServlet.putSessionObject(ConstantesSisEducar.USUARIO_LOGADO, resultadoUsuario);
						FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesSisEducar.PATH_PROJETO_NOME + "/resources/templates/sisEducar/menuPrincipal.xhtml");
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
	 * @author João Paulo
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
			String generoSelecionado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("inputGeneroAux");
			String urlBotaoLink = "http://localHost:8080/SIS-EDUCAR/validacaoUsuario.xhtml?validacao=";
			
			if(usuario.getRaAluno().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O R.A. é obrigatório", null));
				return null;
			}
			
			//Se o genero selecionado tiver null é porque o usuário deixou a opção masculino marcada, se for <> null é porque ele clicou em algum rádio do tipo gênero
			if(generoSelecionado!=null && generoSelecionado.length()>0) { usuario.setGenero(generoSelecionado); }
			else 														{ usuario.setGenero("masculino"); }
			
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
			
			/*
			 * Validação de senha
			 * <Senha> -----------------------------
			 */
			if(usuario.getSenha().length() !=8 && usuario.getConfirmarSenha().length() !=8)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha deve ter 8 dígitos", null));
				return null;
			}
			
			if(usuario.getSenha().equals("12345678"))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha não pode ser sequencial", null));
				return null;
			}
			
			if(!usuario.getSenha().equals(usuario.getConfirmarSenha()))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas estão diferentes", null));
				return null;
			}
			/**
			 * </Senha> -----------------------------
			 */
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			resultado = new UsuarioDAO().inserirUsuario(usuario);
			
			if(resultado)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviamos um email de confirmação para o email informado", null));  
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não registrado", null));  
			}
			
			urlBotaoLink += SisEducarServlet.criptografarURL(true, usuario.getEmail());
			
			email = EmailUtils.inicializarPropriedades();
			email.setSubjectMail("Confirmação de cadastro de usuário");
			email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Ol� " + usuario.getNome() + ",</p> " + 
					" <p style=\"text-align:left; font-size:17px; \">A sua solicitação de cadastro foi realizada com sucesso.</p> " + 
					" <p style=\"font-style:italic; font-size:17px; text-align:left;\"><b>Para que o cadastro seja efetivado clique no botão abaixo. Atenção o link irá expirar em 48 horas.</b></p>", "<p style=\"font-size:17px; text-align:left;\">Caso o botão acima não funcione clique no link abaixo:</p>", urlBotaoLink, urlBotaoLink, true, "Ativar Usuário"));
			
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
	 * O método é usado para enviar um email para o usuario que quiser realizar a recuperação de senha dele
	 * @author João Paulo
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
					email.setSubjectMail("SIS-EDUCAR - Redefinição de Senha");
					email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Ol� " + usuario.getNome() + ",</p> " + 
							" <p style=\"text-align:left; font-size:17px; \">Recebemos uma solicitação de refinição de senha para este usuário, se não foi você que efetuou esta solicitação por favor desconsidere o email.</p> " + 
							" <p style=\"font-style:italic; font-size:17px; text-align:left;\"><b>Para continuar o processo de redefinição de senha clique no botão abaixo.</b></p>", "<p style=\"font-size:17px; text-align:left;\">Caso o botão acima não funcione clique no link abaixo:</p>", urlBotaoLink, urlBotaoLink, true, "Redefinir Senha"));
					
					destinatarios.put(emailRedefinicaoSenha, emailRedefinicaoSenha);
					email.setToMailsUsers(destinatarios);
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Será enviado uma notificação para o email informado", null));
					resultadoEnvioEmail = new EmailUtils().enviarEmail(email);
					
					if(!resultadoEnvioEmail)
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível enviar o email", null));
					}
				}
				else
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Não existe usuário cadastrado com este email", null));
				}
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O email é obrigatório", null));
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
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas são diferentes", null));
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
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Não foi possível redefinir a senha", null));
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

	public String getEmailRedefinicaoSenha() {
		return emailRedefinicaoSenha;
	}

	public void setEmailRedefinicaoSenha(String emailRedefinicaoSenha) {
		this.emailRedefinicaoSenha = emailRedefinicaoSenha;
	} 
	public String getGeneroMasculino() {
		return generoMasculino;
	}
	public void setGeneroMasculino(String generoMasculino) {
		this.generoMasculino = generoMasculino;
	}
	public String getGeneroFeminino() {
		return generoFeminino;
	}
	public void setGeneroFeminino(String generoFeminino) {
		this.generoFeminino = generoFeminino;
	}
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	public String getSenhaAtual() {
		return senhaAtual;
	}
	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
}

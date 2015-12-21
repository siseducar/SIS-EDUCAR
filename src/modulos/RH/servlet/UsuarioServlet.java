package modulos.RH.servlet;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import modulos.RH.dao.UsuarioDAO;
import modulos.RH.om.Usuario;
import modulos.sisEducar.om.Email;
import modulos.sisEducar.sisEducarServlet.SisEducarServlet;
import modulos.sisEducar.utils.EmailUtils;
import modulos.sisEducar.utils.Logs;

@ManagedBean(name="usuarioServlet")
@SessionScoped
public class UsuarioServlet extends SisEducarServlet
{
	private static final long serialVersionUID = 1L;

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
	 * M�todo usado para cadastrar um novo usu�rio no banco de dados, este usu�rio ser� cadastrado da tela de cadastro de usu�rio
	 * @author Jo�o Paulo
	 * @return NULL - Apenas para retornar a fun��o
	 */
	public String cadastrarUsuario()
	{
		try 
		{
			Map<String, String> destinatarios = new HashMap<String, String>();
			Boolean resultado = false;
			Boolean resultadoExistenciaUsuario = false;
			Boolean resultadoEnvioEmail = false;
			Email email = null;
			String urlBotaoLink = "http://localHost:8080/SIS-EDUCAR/validacaoUsuario.xhtml?validacao=";
			
			/*Se vier TRUE � porque o usu�rio pode ser adicionado, se vier false � porque o usu�rio n�o tem permiss�o para ser cadastrado no sistema
			caso vier false � porque o responsavel do aluno deixou esta pessoa como 1 dos responsaveis pelo aluno*/
			
			if(usuario.getCpfcnpj().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "CPF � obrigat�rio", null));
				return null;
			}
			else
			{
				resultadoExistenciaUsuario = new UsuarioDAO().verificaExistenciaUsuario(usuario.getCpfcnpj());
			}
			
			//Se voltar TRUE � porque o usu�rio j� existe
			if(resultadoExistenciaUsuario)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "J� existe um usu�rio cadastrado para o CPF informado", null));
				return null;
			}
			
			/*
			 * Valida��o de email
			 * <Email> -----------------------------
			 */
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
			
			if(!usuario.getEmail().equals(usuario.getConfirmarEmail()))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Os emails est�o diferentes", null));
				return null;
			}
			/**
			 * </Email> -----------------------------
			 */
			
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
	 * M�todo usado para inicializar novamente o Usuario
	 * @author Jo�o Paulo
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
	 * Este m�todo � respons�vel por enviar email para quantos destinarios quizer
	 * @param assunto
	 * @param corpo (O corpo do texto deve ser em HTML)
	 * @param destinatarios
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	public void enviarEmail(String assunto, String corpo, List<String> destinatarios) throws UnsupportedEncodingException, MessagingException
	{
		Email email = EmailUtils.inicializarPropriedades();
        email.setSubjectMail(assunto);
        email.setBodyMail(corpo);
 
        Map<String, String> mapDestinatarios = new HashMap<String, String>();
        for (String destinatario : destinatarios) 
        {
        	mapDestinatarios.put(destinatario, "");
		}
 
        email.setToMailsUsers(mapDestinatarios);
        
        new EmailUtils().enviarEmail(email);
	}
	
	/*Getters and setters*/
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}

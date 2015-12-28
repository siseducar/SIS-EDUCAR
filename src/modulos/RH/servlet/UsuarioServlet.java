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

import sun.org.mozilla.javascript.internal.ast.ForInLoop;
import modulos.RH.dao.UsuarioDAO;
import modulos.RH.om.Usuario;
import modulos.sisEducar.om.Email;
import modulos.sisEducar.sisEducarServlet.SisEducarServlet;
import modulos.sisEducar.utils.ConstantesSisEducar;
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
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Boolean resultado = false;
			Boolean resultadoExistenciaUsuario = false;
			Boolean resultadoEnvioEmail = false;
			Boolean resultadoRemocaoUsuario = false;
			Email email = null;
			String generoSelecionado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("inputGeneroAux");
			String urlBotaoLink = "http://localHost:8080/SIS-EDUCAR/validacaoUsuario.xhtml?validacao=";
			
			if(usuario.getCpfcnpj().isEmpty())
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "CPF � obrigat�rio", null));
				return null;
			}
			else
			{
				resultadoExistenciaUsuario = usuarioDAO.verificaExistenciaUsuario(usuario.getCpfcnpj());
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
			
			//Se o genero selecionado tiver null � porque o usu�rio deixou a op��o masculino marcada, se for <> null � porque ele clicou em algum r�dio do tipo g�nero
			if(generoSelecionado!=null && generoSelecionado.length()>0) { usuario.setGenero(generoSelecionado); }
			else 														{ usuario.setGenero("masculino"); }
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			
			//Aqui eu busco novamente o usu�rio, mas este usu�rio estar� completo
			resultado = usuarioDAO.inserirUsuario(usuario);
			
			if(resultado)
			{
				//Como o usu�rio estar� em confirma��o ele estar� com o status imcompleto, ent�o eu setoo status imcompleto nele
				usuario.setStatus(ConstantesSisEducar.STATUS_INCOMPLETO);
				usuario = usuarioDAO.buscarUsuario(usuario);
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usu�rio n�o registrado", null));  
			}
			
			/* Envio de email de valida��o */
			urlBotaoLink += SisEducarServlet.criptografarURL(true, usuario.getEmail());
			email = EmailUtils.inicializarPropriedades();
			email.setSubjectMail("Confirma��o de cadastro de usu�rio");
			email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Ol� " + usuario.getNome() + ",</p> " + 
					" <p style=\"text-align:left; font-size:17px; \">A sua solicita��o de cadastro foi realizada com sucesso.</p> " + 
					" <p style=\"font-style:italic; font-size:17px; text-align:left;\"><b>Para que o cadastro seja efetivado clique no bot�o abaixo. Aten��o o link ir� expirar em 48 horas.</b></p>", "<p style=\"font-size:17px; text-align:left;\">Caso o bot�o acima n�o funcione clique no link abaixo:</p>", urlBotaoLink, urlBotaoLink, true, "Ativar Usu�rio"));
			
			destinatarios.put(usuario.getEmail(), usuario.getNome());
			email.setToMailsUsers(destinatarios);
			
			resultadoEnvioEmail = new EmailUtils().enviarEmail(email);
			
			//Se o envio n�o der certo eu removo o usu�rio que foi cadastrado no sistema
			if(!resultadoEnvioEmail)
			{
				resultadoRemocaoUsuario = usuarioDAO.removerUsuario(usuario);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usu�rio n�o registrado 2", null));
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviamos um email de confirma��o para o email informado", null));	
			}
			
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

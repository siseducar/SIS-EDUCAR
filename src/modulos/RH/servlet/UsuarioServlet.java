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
	 * Método usado para cadastrar um novo usuário no banco de dados, este usuário será cadastrado da tela de cadastro de usuário
	 * @author João Paulo
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
			
			if(!usuario.getCpfcnpj().isEmpty())
			{
				resultadoExistenciaUsuario = usuarioDAO.verificaExistenciaUsuario(usuario.getCpfcnpj());
			}
			
			//Se voltar TRUE � porque o usuário já existe
			if(resultadoExistenciaUsuario)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Já existe um usuário cadastrado para o CPF informado", null));
				return null;
			}
			
			/*
			 * Validação de email
			 * <Email> -----------------------------
			 */
			if(!usuario.getEmail().contains("@") || !usuario.getEmail().contains("."))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O email é inválido", null));
				return null;
			}
			
			if(!usuario.getEmail().equals(usuario.getConfirmarEmail()))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Os emails estão diferentes", null));
				return null;
			}
			
			/*
			 * Validação de senha
			 * <Senha> -----------------------------
			 */
			if(usuario.getSenha().length() != 8 && usuario.getConfirmarSenha().length() != 8)
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
			
			//Se o genero selecionado tiver null é porque o usuário deixou a opção masculino marcada, se for <> null é porque ele clicou em algum rádio do tipo gênero
			if(generoSelecionado!=null && generoSelecionado.length()>0) { usuario.setGenero(generoSelecionado); }
			else 														{ usuario.setGenero("masculino"); }
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			
			//Aqui eu busco novamente o usuário, mas este usuário estará completo
			resultado = usuarioDAO.inserirUsuario(usuario);
			
			if(resultado)
			{
				//Como o usuário estará em confirmação ele estará com o status imcompleto, então eu seto o status imcompleto nele
				usuario.setStatus(ConstantesSisEducar.STATUS_INCOMPLETO);
				usuario = usuarioDAO.buscarUsuario(usuario);
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não registrado", null));  
			}
			
			/* Envio de email de validação */
			urlBotaoLink += SisEducarServlet.criptografarURL(true, usuario.getEmail());
			email = EmailUtils.inicializarPropriedades();
			email.setSubjectMail("Confirmação de cadastro de usuário");
			email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Ol� " + usuario.getNome() + ",</p> " + 
					" <p style=\"text-align:left; font-size:17px; \">A sua solicitação de cadastro foi realizada com sucesso.</p> " + 
					" <p style=\"font-style:italic; font-size:17px; text-align:left;\"><b>Para que o cadastro seja efetivado clique no botão abaixo. Atenção o link irá expirar em 48 horas.</b></p>", "<p style=\"font-size:17px; text-align:left;\">Caso o botão acima não funcione clique no link abaixo:</p>", urlBotaoLink, urlBotaoLink, true, "Ativar Usuário"));
			
			destinatarios.put(usuario.getEmail(), usuario.getNome());
			email.setToMailsUsers(destinatarios);
			
			resultadoEnvioEmail = new EmailUtils().enviarEmail(email);
			
			//Se o envio não der certo eu removo o usuário que foi cadastrado no sistema
			if(!resultadoEnvioEmail)
			{
				resultadoRemocaoUsuario = usuarioDAO.removerUsuario(usuario);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não registrado 2", null));
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviamos um email de confirmação para o email informado", null));	
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
	 * Método usado para atualizar as informações do usuário quando ele entrar pela tela de consulta perfil
	 * @author João Paulo
	 * @return String
	 */
	public String atualizarUsuario()
	{
		try
		{
			Map<String, String> destinatarios = new HashMap<String, String>();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Boolean resultado = false;
			Boolean resultadoEnvioEmail = false;
			Boolean resultadoRemocaoUsuario = false;
			Email email = null;
			
			/*
			 * Validação de email
			 * <Email> -----------------------------
			 */
			if(!usuarioLogado.getEmail().contains("@") || !usuarioLogado.getEmail().contains("."))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O email é inválido", null));
				return null;
			}
			
			if(!usuarioLogado.getEmail().equals(usuarioLogado.getConfirmarEmail()))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Os emails estão diferentes", null));
				return null;
			}
			
			/*
			 * Validação de senha
			 * <Senha> -----------------------------
			 */
			if(usuarioLogado.getSenha().length() != 8)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha deve ter 8 dígitos", null));
				return null;
			}
			
			if(usuarioLogado.getSenha().equals("12345678"))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha não pode ser sequencial", null));
				return null;
			}
			
			usuarioLogado.setSenha(criptografarSenha(usuarioLogado.getSenha()));
			
			//Aqui eu busco novamente o usuário, mas este usuário estará completo
			resultado = usuarioDAO.atualizarUsuario(usuarioLogado);
			
			if(resultado)
			{
				email = EmailUtils.inicializarPropriedades();
				email.setSubjectMail("Confirmação das modificações no cadastro de seu usuário");
				email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Olá " + usuarioLogado.getNome() + ",</p> " + 
						" <p style=\"text-align:left; font-size:17px; \">Suas modificações foram efetuadas com sucesso.</p> " + 
						" <p style=\"font-style:italic; font-size:17px; text-align:left;\"><b>Suas novas informações estarão disponíveis somente no próximo acesso ao sistema.</b></p>", null, null, null, false, null));
				
				destinatarios.put(usuarioLogado.getEmail(), usuarioLogado.getNome());
				email.setToMailsUsers(destinatarios);
				
				resultadoEnvioEmail = new EmailUtils().enviarEmail(email);
				
				//Se o envio não der certo eu removo o usuário que foi cadastrado no sistema
				if(!resultadoEnvioEmail)
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não atualizado", "2"));
				}
				else
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suas modificações estarão disponíveis no próximo acesso ao sistema", null));	
				}
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não atualizado", null));  
			}
			
			return null;
		} 
		catch (Exception e) 
		{
			Logs.addFatal("Erro ao atualizar!", "atualizarUsuario");
			return null;
		}
	}
	
	/**
	 * Método usado para inicializar novamente o Usuario
	 * @author João Paulo
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
	 * Este método é responsável por enviar email para quantos destinarios quizer
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

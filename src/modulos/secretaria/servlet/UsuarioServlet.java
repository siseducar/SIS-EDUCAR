package modulos.secretaria.servlet;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.dao.UsuarioDAO;
import modulos.secretaria.om.Permissao;
import modulos.secretaria.om.PermissaoUsuario;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.Usuario;
import modulos.secretaria.utils.ConstantesRH;
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
	Usuario usuarioLogado;
	private String nomePessoaVinculada;
	
	private List<Permissao> permissoes;
    private List<Permissao> permissoesSelecionadas;
    
    //Esta variável é o conteúdo que o class dos módulos tem, por padrão eles já irão vir hidden, o módulo só será liberado se o usuário tiver permissão deste módulo
    private String classAtributeHidden   	= "hidden";
    private String classAtributeDropDown 	= "dropdown-toggle";
    
    //Variaveis ligadas aos módulos
    private String classModuloSecretaria 	= classAtributeDropDown + " " + classAtributeHidden;
    private String classModuloEscola     	= classAtributeDropDown + " " + classAtributeHidden;
	private String classModuloMerenda    	= classAtributeDropDown + " " + classAtributeHidden;
    private String classModuloDocentes   	= classAtributeDropDown + " " + classAtributeHidden;
    private String classModuloPortal     	= classAtributeDropDown + " " + classAtributeHidden;
    private String classModuloPatrimonio 	= classAtributeDropDown + " " + classAtributeHidden;
    private String classModuloAlmoxarifado  = classAtributeDropDown + " " + classAtributeHidden;
    private String classModuloBiblioteca 	= classAtributeDropDown + " " + classAtributeHidden;
    private String classModuloTransporte 	= classAtributeDropDown + " " + classAtributeHidden;
    private String classModuloSocial     	= classAtributeDropDown + " " + classAtributeHidden;
    private String classModuloProtocolo  	= classAtributeDropDown + " " + classAtributeHidden;
    private String classModuloOuvidoria 	= classAtributeDropDown + " " + classAtributeHidden;
	/**
	 * Construtor
	 */
	public UsuarioServlet()
	{
		usuario = new Usuario();
		usuarioLogado = (Usuario) getSessionObject(ConstantesSisEducar.USUARIO_LOGADO);
		
		validarPermissoes();

		nomePessoaVinculada = "";
		
		permissoes = buscarPermissoes();
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
			Pessoa pessoa = null;
			String generoSelecionado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("inputGeneroAux");
			String urlBotaoLink = "http://localHost:8080/SIS-EDUCAR/validacaoUsuario.xhtml?validacao=";
			PermissaoUsuario permissaoUsuario = null;
			
			if(usuarioLogado!=null && usuarioLogado.getFkMunicipioCliente()!=null)
			{
				usuario.setFkMunicipioCliente(usuarioLogado.getFkMunicipioCliente());
			}
			
			/* Pessoa VInculada */
			if(usuario.getCpfcnpj()!=null)
			{
				pessoa = new PessoaDAO().obtemUnicoPessoaSimples(usuario.getCpfcnpj()); 
				if(pessoa!=null && pessoa.getPkPessoa()!=null) 	{ usuario.setPessoa(pessoa); }
			}
			
			if(!usuario.getCpfcnpj().isEmpty())
			{
				resultadoExistenciaUsuario = usuarioDAO.verificaExistenciaUsuario(usuario);
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
				
				/* Adiciona as permissões para o usuário*/
				if(permissoesSelecionadas!=null && permissoesSelecionadas.size() >0)
				{
					
					for (Permissao permissao : permissoes) 
					{
						permissaoUsuario = new PermissaoUsuario();
						permissaoUsuario.setUsuario(usuario);
						permissaoUsuario.setPermissao(permissao);
						permissaoUsuario.setFkMunicipioCliente(usuario.getFkMunicipioCliente());
						usuarioDAO.inserirPermissaoUsuario(permissaoUsuario);
					}
				}
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não registrado", null));  
			}
			
			/* Envio de email de validação */
			urlBotaoLink += SisEducarServlet.criptografarURL(true, usuario.getEmail());
			email = EmailUtils.inicializarPropriedades();
			email.setSubjectMail("Confirmação de cadastro de usuário");
			email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Olá " + usuario.getNome() + ",</p> " + 
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
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviamos um email de confirmação para o email cadastrado", null));	
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
	
	public List<Permissao> buscarPermissoes()
	{
		try 
		{
			List<Permissao> permissoes = new UsuarioDAO().buscarPermissoes();
			return permissoes;
		} 
		catch (Exception e) 
		{
			Logs.addError("buscarPermissoes", "");
			return null;
		}
	}
	
	/**
	 * Usado para buscar as informações da pessoa que será vinculada no usuário
	 * @author João Paulo
	 * @return
	 */
	public String buscarInformacoesPessoaVinculada()
	{
		try 
		{
			Pessoa pessoa = new PessoaDAO().obtemUnicoPessoaSimples(usuario.getCpfcnpj()); 
			if(pessoa!=null && pessoa.getPkPessoa()!=null) 	{ nomePessoaVinculada = pessoa.getNome(); }
			return null;
		} 
		catch (Exception e) 
		{
			Logs.addError("buscarInformacoesDiretor", "");
			return null;
		}
	}
	
	/**
	 * Verifica se o usuário tem as permissões para visualizar os módulos
	 * @author João Paulo
	 */
	public void validarPermissoes()
	{
		try 
		{
			if(usuarioLogado!=null)
			{
				for (Permissao permissao : usuarioLogado.getPermissoes()) 
				{
					if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_ADMINISTRADOR_SISTEMA))
					{
						classModuloSecretaria = classAtributeDropDown;
						classModuloEscola     = classAtributeDropDown;
						classModuloMerenda 	  = classAtributeDropDown;
						classModuloDocentes   = classAtributeDropDown;
						classModuloPortal     = classAtributeDropDown;
						classModuloPatrimonio = classAtributeDropDown;
						classModuloAlmoxarifado = classAtributeDropDown;
						classModuloBiblioteca = classAtributeDropDown;
						classModuloTransporte = classAtributeDropDown;
						classModuloSocial     = classAtributeDropDown;
						classModuloProtocolo  = classAtributeDropDown;
						classModuloOuvidoria  = classAtributeDropDown;
						
						break;
					}
					else if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_SECRETARIA_EDUCACAO))
					{ 
						classModuloSecretaria = classAtributeDropDown; 
					}
					else if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_COORDENADOR_ESCOLAR))
					{ 
						classModuloEscola = classAtributeDropDown; 
					}
					else if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_NUTRICIONISTA))
					{ 
						classModuloMerenda = classAtributeDropDown; 
					}
					else if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_PROFESSOR))
					{ 
						classModuloDocentes = classAtributeDropDown; 
					}
					else if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_ALUNO))
					{ 
						classModuloPortal = classAtributeDropDown; 
					}
					else if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_BIBLIOTECARIO))
					{ 
						classModuloBiblioteca = classAtributeDropDown; 
					}
					else if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_TRANSPORTE))
					{ 
						classModuloTransporte = classAtributeDropDown; 
					}
					else if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_PROTOCOLO))
					{ 
						classModuloProtocolo = classAtributeDropDown; 
					}
					else if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_OUVIDORIA))
					{ 
						classModuloOuvidoria = classAtributeDropDown; 
					}
					//else if(permissao.getPkPermissao() == ConstantesRH.PERMISSAO_TIPO_)       
					//{ 
					//}
					//else if(permissao.getPkPermissao() == ConstantesRH.PERMISSAO_TIPO_)       
					//{ 
					//}
				}
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("", "");
		}
	}
	
	/*Getters and setters*/
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNomePessoaVinculada() {
		return nomePessoaVinculada;
	}

	public void setNomePessoaVinculada(String nomePessoaVinculada) {
		this.nomePessoaVinculada = nomePessoaVinculada;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public List<Permissao> getPermissoesSelecionadas() {
		return permissoesSelecionadas;
	}

	public void setPermissoesSelecionadas(List<Permissao> permissoesSelecionadas) {
		this.permissoesSelecionadas = permissoesSelecionadas;
	}

	public String getClassModuloSecretaria() {
		return classModuloSecretaria;
	}

	public void setClassModuloSecretaria(String classModuloSecretaria) {
		this.classModuloSecretaria = classModuloSecretaria;
	}
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getClassAtributeHidden() {
		return classAtributeHidden;
	}

	public void setClassAtributeHidden(String classAtributeHidden) {
		this.classAtributeHidden = classAtributeHidden;
	}

	public String getClassAtributeDropDown() {
		return classAtributeDropDown;
	}

	public void setClassAtributeDropDown(String classAtributeDropDown) {
		this.classAtributeDropDown = classAtributeDropDown;
	}

	public String getClassModuloEscola() {
		return classModuloEscola;
	}

	public void setClassModuloEscola(String classModuloEscola) {
		this.classModuloEscola = classModuloEscola;
	}

	public String getClassModuloMerenda() {
		return classModuloMerenda;
	}

	public void setClassModuloMerenda(String classModuloMerenda) {
		this.classModuloMerenda = classModuloMerenda;
	}

	public String getClassModuloDocentes() {
		return classModuloDocentes;
	}

	public void setClassModuloDocentes(String classModuloDocentes) {
		this.classModuloDocentes = classModuloDocentes;
	}

	public String getClassModuloPortal() {
		return classModuloPortal;
	}

	public void setClassModuloPortal(String classModuloPortal) {
		this.classModuloPortal = classModuloPortal;
	}

	public String getClassModuloPatrimonio() {
		return classModuloPatrimonio;
	}

	public void setClassModuloPatrimonio(String classModuloPatrimonio) {
		this.classModuloPatrimonio = classModuloPatrimonio;
	}

	public String getClassModuloAlmoxarifado() {
		return classModuloAlmoxarifado;
	}

	public void setClassModuloAlmoxarifado(String classModuloAlmoxarifado) {
		this.classModuloAlmoxarifado = classModuloAlmoxarifado;
	}

	public String getClassModuloBiblioteca() {
		return classModuloBiblioteca;
	}

	public void setClassModuloBiblioteca(String classModuloBiblioteca) {
		this.classModuloBiblioteca = classModuloBiblioteca;
	}

	public String getClassModuloTransporte() {
		return classModuloTransporte;
	}

	public void setClassModuloTransporte(String classModuloTransporte) {
		this.classModuloTransporte = classModuloTransporte;
	}

	public String getClassModuloSocial() {
		return classModuloSocial;
	}

	public void setClassModuloSocial(String classModuloSocial) {
		this.classModuloSocial = classModuloSocial;
	}

	public String getClassModuloProtocolo() {
		return classModuloProtocolo;
	}

	public void setClassModuloProtocolo(String classModuloProtocolo) {
		this.classModuloProtocolo = classModuloProtocolo;
	}

	public String getClassModuloOuvidoria() {
		return classModuloOuvidoria;
	}

	public void setClassModuloOuvidoria(String classModuloOuvidoria) {
		this.classModuloOuvidoria = classModuloOuvidoria;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

package modulos.sisEducar.servlet;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import modulos.educacao.dao.AlunoDAO;
import modulos.secretaria.dao.HistoricoAcessoDAO;
import modulos.secretaria.dao.UsuarioDAO;
import modulos.secretaria.om.HistoricoAcesso;
import modulos.secretaria.om.Permissao;
import modulos.secretaria.om.Usuario;
import modulos.secretaria.utils.ConstantesSecretaria;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.om.ChaveAcesso;
import modulos.sisEducar.om.Email;
import modulos.sisEducar.utils.ConstantesSisEducar;
import modulos.sisEducar.utils.EmailUtils;
import modulos.sisEducar.utils.Logs;
import sun.misc.BASE64Encoder;

@SessionScoped
@ManagedBean(name= "loginServlet")
public class LoginServlet implements Serializable {
	/***/
	private static final long serialVersionUID = 1L;

	//Objetos e variaveis
	private String emailRedefinicaoSenha = null;
	
	Usuario usuario;
	private Usuario usuarioLogado;  
	Usuario usuarioTemporario = new Usuario();  
	SisEducarServlet sisEducar = new SisEducarServlet();
	
	public String generoMasculino = "none";
	public String generoFeminino = "none";
	
	private String senhaAtual = "";
	
	 //Esta variável é o conteúdo que o class dos módulos tem, por padrão eles já irão vir hidden, o módulo só será liberado se o usuário tiver permissão deste módulo
    private String classAtributeHidden   	= "hidden";
    private String classAtributeDropDown 	= "dropdown-toggle";
    
    //VARIAVEIS LIGADAS AOS MÓDULOS
    private String classModuloSecretaria 	= classAtributeDropDown + " " + classAtributeHidden;
    
    /* ATENÇÃO - TODOS ESSES MÓDULOS ABAIXO IRÃO APARECER NO MEU PRINCIPAL APENAS PARA VISUALIZAÇÃO DO CLIENTE*/
    private String classModuloEscola     	= classAtributeDropDown;
	private String classModuloMerenda    	= classAtributeDropDown;
    private String classModuloDocentes   	= classAtributeDropDown;
    private String classModuloPortal     	= classAtributeDropDown;
    private String classModuloPatrimonio 	= classAtributeDropDown;
    private String classModuloAlmoxarifado  = classAtributeDropDown;
    private String classModuloBiblioteca 	= classAtributeDropDown;
    private String classModuloTransporte 	= classAtributeDropDown;
    private String classModuloSocial     	= classAtributeDropDown;
    private String classModuloProtocolo  	= classAtributeDropDown;
    private String classModuloOuvidoria 	= classAtributeDropDown;

    //VARIAVEIS LIGADAS AOS SUB MÓDULOS
    private String classSecretariaCadastroSubMenu 	= classAtributeDropDown + " " + classAtributeHidden;
    private String classSecretariaLancamentoSubMenu = classAtributeDropDown + " " + classAtributeHidden;
    private String classSecretariaConsultaSubMenu 	= classAtributeDropDown + " " + classAtributeHidden;
    private String classSecretariaRelatorioSubMenu 	= classAtributeDropDown + " " + classAtributeHidden;
    
    private String classEscolaCadastroSubMenu 	= classAtributeDropDown + " " + classAtributeHidden;
    private String classEscolaLancamentoSubMenu = classAtributeDropDown + " " + classAtributeHidden;
    private String classEscolaConsultaSubMenu 	= classAtributeDropDown + " " + classAtributeHidden;
    private String classEscolaRelatorioSubMenu 	= classAtributeDropDown + " " + classAtributeHidden;
    
    //VARIAVEIS LIGADAS AOS SUB MÓDULOS
    private String classSecretariaCadastroPessoa 			= classAtributeHidden;
    private String classSecretariaCadastroFornecedor		= classAtributeHidden;
    private String classSecretariaCadastroUsuario   		= classAtributeHidden;
    private String classSecretariaCadastroUnidadeEscolar   	= classAtributeHidden;
    private String classSecretariaCadastroAmbiente   		= classAtributeHidden;
    private String classSecretariaCadastroMatriculaAluno   	= classAtributeHidden;
    private String classSecretariaConsultaHistoricoAcesso	= classAtributeHidden;
	private String classSecretariaConsultaGraficos   		= classAtributeHidden;
    private String classSecretariaConsultaRelatorios   		= classAtributeHidden;
    
    private String classEscolaCadastroMatriculaAluno 		= classAtributeHidden;
    private String classEscolaCadastroHorario 				= classAtributeHidden;
	
	public LoginServlet() {
		usuario = new Usuario();
	}
	
	/**
	 * O método é usado para validar se existe um usuario no banco com as informações passadas pelo usuario (nome, senha)
	 * e também valida se a chave de acesso do cliente é valida para esse usuário
	 * @author João Paulo
	 * @return String
	 */
	public void validarLogin() {
		try {			
			HistoricoAcesso historicoAcesso = new HistoricoAcesso();
			if(usuario.getNome()!= null && usuario.getNome().length() == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário é obrigatório", null));  
			} else {
				if( usuario.getSenha()!=null && usuario.getSenha().length() == 0) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Senha é obrigatório", null));  
				}
			}
			
			if(usuario!=null && usuario.getNome()!=null && usuario.getSenha()!=null) {
				usuario.setSenha(criptografarSenha(usuario.getSenha()));
				
				usuarioLogado = new UsuarioDAO().validarUsuario(usuario);
				
				if( usuarioLogado != null ) {
					if(usuarioLogado.getGenero().equals("masculino")) {
						generoMasculino = "initial";
					} else {
						generoFeminino = "initial";
					}
					
					
					HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
					session.setAttribute("usuario", usuarioLogado);
					
					historicoAcesso.setUsuario(usuarioLogado);
					new HistoricoAcessoDAO().inserirHistoricoAcesso(historicoAcesso);
					
					validarPermissoes();
					
					FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesSisEducar.PATH_PROJETO_NOME + "/menuPrincipal");
				}else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário/Senha inválidos", null));
				}
			}
			
		} 
		catch (Exception e) {
			Logs.addFatal("Validar login", "Erro ao validar o login, contate o administrador.");
		}
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login/login.xhtml?faces-redirect=true";
	}
	
	/**
	 * Verifica se o usuário tem uma chave de acesso valida para acessar o sistema
	 * @author João Paulo
	 * @param usuarioVerificar
	 * @return TRUE || FALSE || NULL
	 */
	public Boolean verificarChaveAcesso(Usuario usuarioVerificar)
	{
		try 
		{
			if(usuarioVerificar!=null)
			{
				//Busca a chave de acesso cadastrada no banco de dados
				ChaveAcesso chaveAcesso = new SisEducarDAO().obtemChaveAcesso(usuarioVerificar);
				
				if(chaveAcesso!=null && chaveAcesso.getPkChaveAcesso()!=null && chaveAcesso.getPkChaveAcesso() >0)
				{
					new SisEducarServlet();
					//Aqui ele defini qual será a chave de acesso do usuário logado
					ConstantesSisEducar.USUARIO_LOGADO = SisEducarServlet.gerarChaveAcessoCriptografada(chaveAcesso.getChave());
					
					return true;
				}
				else
				{
					return false;
				}
			}
			
			return false;
		} 
		catch (Exception e) 
		{
			Logs.addError("", "");
			return null;
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
			String urlBotaoLink = "http://localHost:8080/coruja/validacaoUsuario.xhtml?validacao=";
			
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
			email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Olá " + usuario.getNome() + ",</p> " + 
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
			String urlBotaoLink = "http://localHost:8080/coruja/redefinirSenha.xhtml?redefinir=";
			Email email = new Email();
			String parametro = "&validacao=";
			
			if(emailRedefinicaoSenha!=null && emailRedefinicaoSenha.length() >0)
			{
				usuario = new UsuarioDAO().obtemUsuario(emailRedefinicaoSenha, ConstantesSisEducar.STATUS_ATIVO, null);
				
				if(usuario!=null)
				{
					urlBotaoLink += sisEducar.criptografarURL(true, emailRedefinicaoSenha);
					email = EmailUtils.inicializarPropriedades();
					email.setSubjectMail("CORUJA - Redefinição de Senha");
					email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Olá " + usuario.getNome() + ",</p> " + 
							" <p style=\"text-align:left; font-size:17px; \">Recebemos uma solicitação de refinição de senha para este usuário, se não foi você que efetuou esta solicitação por favor desconsidere o email.</p> " + 
							" <p style=\"font-style:italic; font-size:17px; text-align:left;\"><b>Para continuar o processo de redefinição de senha clique no botão abaixo.</b></p>", "<p style=\"font-size:17px; text-align:left;\">Caso o botão acima não funcione clique no link abaixo:</p>", urlBotaoLink, urlBotaoLink, true, "Redefinir Senha"));
					
					destinatarios.put(emailRedefinicaoSenha, emailRedefinicaoSenha);
					email.setToMailsUsers(destinatarios);
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Será enviado uma notificação para o email informado", null));
					emailRedefinicaoSenha = "";
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
			
			usuarioTemporario = (Usuario) sisEducar.getSessionObject(ConstantesSisEducar.USUARIO_LOGADO);
			sisEducar.putSessionObject(ConstantesSisEducar.USUARIO_LOGADO, null);
			
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
						FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesSisEducar.PATH_PROJETO_NOME + "/login/login.xhtml");
					}
					else
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Não foi possível redefinir a senha", null));
					}
				}
				
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas são obrigatórias", null));
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
	
	public void validarPermissoes()
	{
		try 
		{
			Boolean moduloSecretariaLiberado = false;
			Boolean moduloEscolaLiberado = false;
			Boolean moduloMerendaLiberado = false;
			Boolean moduloDocentesLiberado = false;
			Boolean moduloPortalLiberado = false;
			Boolean moduloPatrimonioLiberado = false;
			Boolean moduloAlmoxarifadoLiberado = false;
			Boolean moduloTransporteLiberado = false;
			Boolean moduloBibliotecaLiberado = false;
			Boolean moduloSocialLiberado = false;
			Boolean moduloProtocoloLiberado = false;
			Boolean moduloOuvidoriaLiberado = false;
			
			Boolean subMenuCadastroSecretariaLiberado = false;
			Boolean subMenuConsultaSecretariaLiberado = false;
			Boolean subMenuLancamentoSecretariaLiberado = false;
			Boolean subMenuRelatorioSecretariaLiberado = false;
			
			Boolean subMenuCadastroEscolaLiberado = false;
			Boolean subMenuLancamentoEscolaLiberado = false;
			Boolean subMenuConsultaEscolaLiberado = false;
			Boolean subMenuRelatorioEscolaLiberado = false;
			
			if(usuarioLogado!=null)
			{
				for (Permissao permissao : usuarioLogado.getPermissoes()) 
				{
					/* MÓDULO SECRETÁRIA */
					if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA)) 
					{
						//LIBERADO O ACESSO AO MÓDULO
						if(!moduloSecretariaLiberado)
						{
							classModuloSecretaria = classAtributeDropDown; 
							moduloSecretariaLiberado = true;
						}
						
						//LIBERA ACESSO AOS SUB MENUS
						if(permissao.getTipoSubMenuResponsavel().equals(ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO))
						{
							if(!subMenuCadastroSecretariaLiberado)
							{
								classSecretariaCadastroSubMenu = classAtributeDropDown; 
								subMenuCadastroSecretariaLiberado = true;
							}
						}
						else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesSecretaria.TIPO_SUB_MENU_LANCAMENTO))
						{
							if(!subMenuLancamentoSecretariaLiberado)
							{
								setClassSecretariaLancamentoSubMenu(classAtributeDropDown); 
								subMenuLancamentoSecretariaLiberado = true;
							}
						}
						else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesSecretaria.TIPO_SUB_MENU_CONSULTA))
						{
							if(!subMenuConsultaSecretariaLiberado)
							{
								classSecretariaConsultaSubMenu = classAtributeDropDown; 
								subMenuConsultaSecretariaLiberado = true;
							}
						}
						else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesSecretaria.TIPO_SUB_MENU_RELATORIO))
						{
							if(!subMenuRelatorioSecretariaLiberado)
							{
								classSecretariaRelatorioSubMenu = classAtributeDropDown; 
								subMenuRelatorioSecretariaLiberado = true;
							}
						}
						
						//LIBERA ACESSO AS TELAS DE CADASTRO
						if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_PESSOA)) { classSecretariaCadastroPessoa = ""; }
						else if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_USUARIO)) { classSecretariaCadastroUsuario = ""; }
						else if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_FORNECEDOR)) { classSecretariaCadastroFornecedor = ""; }
						else if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_UNIDADE_ESCOLAR)) { classSecretariaCadastroUnidadeEscolar = ""; }
						else if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_AMBIENTE)) { classSecretariaCadastroAmbiente = ""; }

						//LIBERA ACESSO AS TELAS DE CONSULTA
						else if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_HISTORICO_ACESSO)) { classSecretariaConsultaHistoricoAcesso = ""; }
						else if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_GRAFICOS)) { classSecretariaConsultaGraficos = ""; }
						else if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_RELATORIOS)) { classSecretariaConsultaRelatorios = ""; }
					}
					
					/* MÓDULO ESCOLA */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_ESCOLA)) 	
					{ 
						if(!moduloEscolaLiberado)
						{
							classModuloEscola = classAtributeDropDown;
							moduloEscolaLiberado = true;
						}
						
						//LIBERA ACESSO AOS SUB MENUS
						if(permissao.getTipoSubMenuResponsavel().equals(ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO))
						{
							if(!subMenuCadastroEscolaLiberado)
							{
								classEscolaCadastroSubMenu = classAtributeDropDown; 
								subMenuCadastroEscolaLiberado = true;
							}
						}
						else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesSecretaria.TIPO_SUB_MENU_LANCAMENTO))
						{
							if(!subMenuLancamentoEscolaLiberado)
							{
								classEscolaLancamentoSubMenu = classAtributeDropDown; 
								subMenuLancamentoEscolaLiberado = true;
							}
						}
						else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesSecretaria.TIPO_SUB_MENU_CONSULTA))
						{
							if(!subMenuConsultaEscolaLiberado)
							{
								classEscolaConsultaSubMenu = classAtributeDropDown; 
								subMenuConsultaEscolaLiberado = true;
							}
						}
						else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesSecretaria.TIPO_SUB_MENU_RELATORIO))
						{
							if(!subMenuRelatorioEscolaLiberado)
							{
								classEscolaRelatorioSubMenu = classAtributeDropDown; 
								subMenuRelatorioEscolaLiberado = true;
							}
						}
						
						//LIBERA ACESSO AS TELAS DE CADASTRO
						if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_ESCOLA_CADASTROS_MATRICULA_ALUNO)) { classEscolaCadastroMatriculaAluno = ""; }
						if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_ESCOLA_CADASTROS_HORARIO)) 		{ classEscolaCadastroHorario = ""; }
					}
					
					/* MÓDULO MERENDA */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_MERENDA)) 	
					{ 
						if(!moduloMerendaLiberado)
						{
							classModuloMerenda = classAtributeDropDown;
							moduloMerendaLiberado = true;
						}
					}
					
					/* MÓDULO DOCENTES */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_DOCENTES)) 	
					{ 
						if(!moduloDocentesLiberado)
						{
							classModuloDocentes = classAtributeDropDown; 
							moduloDocentesLiberado = true;
						}
					}
					
					/* MÓDULO PORTAL */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_PORTAL)) 
					{ 
						if(!moduloPortalLiberado)
						{
							classModuloPortal = classAtributeDropDown; 
							moduloPortalLiberado = true;
						}
					}
					
					/* MÓDULO PATRIMONIO */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_PATROMONIO)) 
					{ 
						if(!moduloPatrimonioLiberado)
						{
							classModuloPatrimonio = classAtributeDropDown; 
							moduloPatrimonioLiberado = true;
						}
					}
					
					/* MÓDULO ALMOXARIFADO */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_ALMOXARIFADO)) 
					{ 
						if(!moduloAlmoxarifadoLiberado)
						{
							classModuloAlmoxarifado = classAtributeDropDown; 
							moduloAlmoxarifadoLiberado = true;
						}
					}
					
					/* MÓDULO BIBLIOTECA */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_BIBLIOTECA)) 
					{ 
						if(!moduloBibliotecaLiberado)
						{
							classModuloBiblioteca = classAtributeDropDown; 
							moduloBibliotecaLiberado = true;
						}
					}
					
					/* MÓDULO TRANSPORTE */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_TRANSPORTE)) 
					{ 
						if(!moduloTransporteLiberado)
						{
							classModuloTransporte = classAtributeDropDown; 
							moduloTransporteLiberado = true;
						}
					}
					
					/* MÓDULO SOCIAL */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SOCIAL)) 
					{ 
						if(!moduloSocialLiberado)
						{
							classModuloSocial = classAtributeDropDown; 
							moduloSocialLiberado = true;
						}
					}
					
					/* MÓDULO PROTOCOLO */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_PROTOCOLO)) 
					{ 
						if(!moduloProtocoloLiberado)
						{
							classModuloProtocolo = classAtributeDropDown; 
							moduloProtocoloLiberado = true;
						}
					}
					
					/* MÓDULO OUVIDORIA */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_OUVIDORIA)) 
					{ 
						if(!moduloOuvidoriaLiberado)
						{
							classModuloOuvidoria = classAtributeDropDown; 
							moduloOuvidoriaLiberado = true;
						}
					}
				}
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("", "");
		}
	}
	
		
	/* Getters and Setters */
	public Usuario getUsuario() 			{ return usuario; }
	public void setUsuario(Usuario usuario) { this.usuario = usuario; }

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

	public String getClassModuloSecretaria() {
		return classModuloSecretaria;
	}

	public void setClassModuloSecretaria(String classModuloSecretaria) {
		this.classModuloSecretaria = classModuloSecretaria;
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

	public String getClassSecretariaCadastroSubMenu() {
		return classSecretariaCadastroSubMenu;
	}

	public void setClassSecretariaCadastroSubMenu(String classSecretariaCadastroSubMenu) {
		this.classSecretariaCadastroSubMenu = classSecretariaCadastroSubMenu;
	}

	public String getClassSecretariaLancamentoSubMenu() {
		return classSecretariaLancamentoSubMenu;
	}

	public void setClassSecretariaLancamentoSubMenu(String classSecretariaLancamentoSubMenu) {
		this.classSecretariaLancamentoSubMenu = classSecretariaLancamentoSubMenu;
	}

	public String getClassSecretariaConsultaSubMenu() {
		return classSecretariaConsultaSubMenu;
	}

	public void setClassSecretariaConsultaSubMenu(String classSecretariaConsultaSubMenu) {
		this.classSecretariaConsultaSubMenu = classSecretariaConsultaSubMenu;
	}

	public String getClassSecretariaRelatorioSubMenu() {
		return classSecretariaRelatorioSubMenu;
	}

	public void setClassSecretariaRelatorioSubMenu(String classSecretariaRelatorioSubMenu) {
		this.classSecretariaRelatorioSubMenu = classSecretariaRelatorioSubMenu;
	}

	public String getClassEscolaCadastroSubMenu() {
		return classEscolaCadastroSubMenu;
	}

	public void setClassEscolaCadastroSubMenu(String classEscolaCadastroSubMenu) {
		this.classEscolaCadastroSubMenu = classEscolaCadastroSubMenu;
	}

	public String getClassEscolaLancamentoSubMenu() {
		return classEscolaLancamentoSubMenu;
	}

	public void setClassEscolaLancamentoSubMenu(String classEscolaLancamentoSubMenu) {
		this.classEscolaLancamentoSubMenu = classEscolaLancamentoSubMenu;
	}

	public String getClassEscolaConsultaSubMenu() {
		return classEscolaConsultaSubMenu;
	}

	public void setClassEscolaConsultaSubMenu(String classEscolaConsultaSubMenu) {
		this.classEscolaConsultaSubMenu = classEscolaConsultaSubMenu;
	}

	public String getClassEscolaRelatorioSubMenu() {
		return classEscolaRelatorioSubMenu;
	}

	public void setClassEscolaRelatorioSubMenu(String classEscolaRelatorioSubMenu) {
		this.classEscolaRelatorioSubMenu = classEscolaRelatorioSubMenu;
	}

	public String getClassSecretariaCadastroPessoa() {
		return classSecretariaCadastroPessoa;
	}

	public void setClassSecretariaCadastroPessoa(String classSecretariaCadastroPessoa) {
		this.classSecretariaCadastroPessoa = classSecretariaCadastroPessoa;
	}

	public String getClassSecretariaCadastroFornecedor() {
		return classSecretariaCadastroFornecedor;
	}

	public void setClassSecretariaCadastroFornecedor(String classSecretariaCadastroFornecedor) {
		this.classSecretariaCadastroFornecedor = classSecretariaCadastroFornecedor;
	}

	public String getClassSecretariaCadastroUsuario() {
		return classSecretariaCadastroUsuario;
	}

	public void setClassSecretariaCadastroUsuario(String classSecretariaCadastroUsuario) {
		this.classSecretariaCadastroUsuario = classSecretariaCadastroUsuario;
	}

	public String getClassSecretariaCadastroUnidadeEscolar() {
		return classSecretariaCadastroUnidadeEscolar;
	}

	public void setClassSecretariaCadastroUnidadeEscolar(String classSecretariaCadastroUnidadeEscolar) {
		this.classSecretariaCadastroUnidadeEscolar = classSecretariaCadastroUnidadeEscolar;
	}

	public String getClassSecretariaCadastroAmbiente() {
		return classSecretariaCadastroAmbiente;
	}

	public void setClassSecretariaCadastroAmbiente(String classSecretariaCadastroAmbiente) {
		this.classSecretariaCadastroAmbiente = classSecretariaCadastroAmbiente;
	}

	public String getClassSecretariaCadastroMatriculaAluno() {
		return classSecretariaCadastroMatriculaAluno;
	}

	public void setClassSecretariaCadastroMatriculaAluno(String classSecretariaCadastroMatriculaAluno) {
		this.classSecretariaCadastroMatriculaAluno = classSecretariaCadastroMatriculaAluno;
	}

	public String getClassSecretariaConsultaHistoricoAcesso() {
		return classSecretariaConsultaHistoricoAcesso;
	}

	public void setClassSecretariaConsultaHistoricoAcesso(String classSecretariaConsultaHistoricoAcesso) {
		this.classSecretariaConsultaHistoricoAcesso = classSecretariaConsultaHistoricoAcesso;
	}

	public String getClassSecretariaConsultaGraficos() {
		return classSecretariaConsultaGraficos;
	}

	public void setClassSecretariaConsultaGraficos(String classSecretariaConsultaGraficos) {
		this.classSecretariaConsultaGraficos = classSecretariaConsultaGraficos;
	}

	public String getClassSecretariaConsultaRelatorios() {
		return classSecretariaConsultaRelatorios;
	}

	public void setClassSecretariaConsultaRelatorios(String classSecretariaConsultaRelatorios) {
		this.classSecretariaConsultaRelatorios = classSecretariaConsultaRelatorios;
	}

	public String getClassEscolaCadastroMatriculaAluno() {
		return classEscolaCadastroMatriculaAluno;
	}

	public void setClassEscolaCadastroMatriculaAluno(String classEscolaCadastroMatriculaAluno) {
		this.classEscolaCadastroMatriculaAluno = classEscolaCadastroMatriculaAluno;
	}

	public String getClassEscolaCadastroHorario() {
		return classEscolaCadastroHorario;
	}

	public void setClassEscolaCadastroHorario(String classEscolaCadastroHorario) {
		this.classEscolaCadastroHorario = classEscolaCadastroHorario;
	}
	
	
}

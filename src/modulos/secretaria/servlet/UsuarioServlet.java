package modulos.secretaria.servlet;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.mail.MessagingException;

import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.dao.UsuarioDAO;
import modulos.secretaria.om.Permissao;
import modulos.secretaria.om.PermissaoUsuario;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.Usuario;
import modulos.secretaria.utils.ConstantesSecretaria;
import modulos.sisEducar.om.Email;
import modulos.sisEducar.om.Modulo;
import modulos.sisEducar.om.Tela;
import modulos.sisEducar.om.TipoTela;
import modulos.sisEducar.servlet.SisEducarServlet;
import modulos.sisEducar.utils.ConstantesSisEducar;
import modulos.sisEducar.utils.EmailUtils;
import modulos.sisEducar.utils.Logs;

@ManagedBean(name="usuarioServlet")
@ViewScoped
public class UsuarioServlet implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	//Variaveis
	SisEducarServlet sisEducarServlet = null;
	Usuario usuario;
	Usuario usuarioLogado;
	private String nomePessoaVinculada;
	
	private Modulo moduloSelecionado;
	private List<SelectItem> comboModulo;
	
	private TipoTela tipoTelaSelecionado;
	private List<SelectItem> comboTipoTela;
	
	private Tela telaSelecionada;
	private List<SelectItem> comboTela;
	
	private List<Permissao> permissoes;
    private List<Permissao> permissoesSelecionadas;
    
    private Permissao permissaoSelecionada;
    private List<SelectItem> comboPermissao;
    
    /*Variáveis para pesquisar os usuários cadastrados no banco de dados*/
    private List<Usuario> usuariosCadastrados;
    private Usuario usuarioCadastradoSelecionado;
    private String cpfPesquisar;
	private String usuarioPesquisar;
    private String emailPesquisar;
    
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
    private String classSecretariaCadastroMatriculaAluno   	= classAtributeHidden;
	private String classSecretariaConsultaGraficos   		= classAtributeHidden;
    private String classSecretariaConsultaRelatorios   		= classAtributeHidden;
    
    private String classEscolaCadastroMatriculaAluno 		= classAtributeHidden;
    
	/**
	 * Construtor
	 */
	public UsuarioServlet()
	{
		sisEducarServlet = new SisEducarServlet();
		comboModulo = new ArrayList<SelectItem>();
		comboTipoTela = new ArrayList<SelectItem>();
		comboTela = new ArrayList<SelectItem>();
		comboPermissao = new ArrayList<SelectItem>();
		
		if(moduloSelecionado==null)   		{ moduloSelecionado = new Modulo(); }
		if(tipoTelaSelecionado==null) 		{ tipoTelaSelecionado = new TipoTela(); }
		if(telaSelecionada==null)     		{ telaSelecionada = new Tela(); }
		if(permissaoSelecionada==null)     	{ permissaoSelecionada = new Permissao(); }
		
		cpfPesquisar = "";
		usuarioPesquisar = "";
		emailPesquisar = "";
		
		usuarioCadastradoSelecionado = new Usuario();
		
		nomePessoaVinculada = "";
		usuario = new Usuario();
		usuarioLogado = (Usuario) sisEducarServlet.getSessionObject(ConstantesSisEducar.USUARIO_LOGADO);
		
		//LIBERA AS TELAS DO SISTEMA DE ACORDO COM AS PERMISSÕES DO USUÁRIO
		validarPermissoes();
		
		//BUSCA AS PERMISSÕES CADASTRADAS NO BANCO DE DADOS E POPULA A TABELA DE PERMISSÕES NO CADASTRO DE USUÁRIO
//		permissoes = buscarPermissoes();
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
				else 
				{ 
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Não tem cadastrado no sistema nenhuma pessoa com o CPF informado", null));
					return null;
				}
			}
			
			if(!usuario.getCpfcnpj().isEmpty() && usuario.getPkUsuario()==null)
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
			usuario.setSenha(SisEducarServlet.criptografarSenha(usuario.getSenha()));
			
			//Se o usuário já está cadastrado no banco de dados eu apenas atualizo as informações do mesmo, caso contrário eu salvo um novo usuário
			if(usuario.getPkUsuario()!=null)
			{
				resultado = usuarioDAO.alterarUsuario(usuario);
			}
			else
			{
				resultado = usuarioDAO.inserirUsuario(usuario);
			}
			
			if(resultado)
			{
				//Como o usuário estará em confirmação ele estará com o status imcompleto, então eu seto o status imcompleto nele
				usuario.setStatus(ConstantesSisEducar.STATUS_INCOMPLETO);
				usuario = usuarioDAO.buscarUsuario(usuario);
				
				/* Adiciona as permissões para o usuário*/
				if(permissoesSelecionadas!=null && permissoesSelecionadas.size() >0)
				{
					for (Permissao permissao : permissoesSelecionadas) 
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
			
			usuarioLogado.setSenha(SisEducarServlet.criptografarSenha(usuarioLogado.getSenha()));
			
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
			permissoesSelecionadas = new ArrayList<Permissao>();
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
	
	/**
	 * Busca as permissões do banco de dados
	 * @author João Paulo
	 * @return
	 */
	public List<SelectItem> buscarPermissoes()
	{
		try 
		{
			List<Permissao> permissoes = null;
			SelectItem selectItem = null;
			List<SelectItem> itens = new ArrayList<SelectItem>();
			if((moduloSelecionado!=null && moduloSelecionado.getTipo()!=null) || (tipoTelaSelecionado!=null && tipoTelaSelecionado.getTipo()!=null) 
					|| (telaSelecionada!=null && telaSelecionada.getTipoTela()!=null))
			{
				permissoes = new UsuarioDAO().buscarPermissoes(moduloSelecionado, tipoTelaSelecionado, telaSelecionada);
				for (Permissao permissao : permissoes) 
				{
					selectItem = new SelectItem();
					
					selectItem.setLabel(permissao.getNome());
					selectItem.setValue(permissao.getPkPermissao());
					
					itens.add(selectItem);
				}
			}
			
			return itens;
		} 
		catch (Exception e) 
		{
			Logs.addError("buscarPermissoes", "");
			return new ArrayList<SelectItem>();
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
			if(pessoa!=null && pessoa.getPkPessoa()!=null) { 
				nomePessoaVinculada = pessoa.getNome(); 
			} else {
				usuario.setCpfcnpj(null);
				nomePessoaVinculada = ""; 
			}
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
						if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_PESSOA)) { classSecretariaCadastroPessoa = ""; }
						else if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_USUARIO)) { classSecretariaCadastroUsuario = ""; }
						else if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_FORNECEDOR)) { classSecretariaCadastroFornecedor = ""; }
						else if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_UNIDADE_ESCOLAR)) { classSecretariaCadastroUnidadeEscolar = ""; }

						//LIBERA ACESSO AS TELAS DE CADASTRO
						else if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_GRAFICOS)) { classSecretariaConsultaGraficos = ""; }
						else if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_RELATORIOS)) { classSecretariaConsultaRelatorios = ""; }
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
						if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_TIPO_ESCOLA_CADASTROS_MATRICULA_ALUNO)) { classEscolaCadastroMatriculaAluno = ""; }
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
	
	/**
	 * Usado para buscar todos os usuários cadastrados pelos filtros digitados na tela pelo usuário
	 * @author João Paulo
	 */
	public void pesquisar()
	{
		try 
		{
			usuariosCadastrados = new UsuarioDAO().buscar(cpfPesquisar, usuarioPesquisar, emailPesquisar);
		} 
		catch (Exception e) 
		{
			Logs.addError("pesquisar", "");
		}
	}
	
	
	private HtmlDataTable dataTable;
	   
    public HtmlDataTable getDataTable() {
          return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
          this.dataTable = dataTable;
    }            

    
    public void pageFirst() {
        dataTable.setFirst(0);
    }

    public void pagePrevious() {
        dataTable.setFirst(dataTable.getFirst() - dataTable.getRows());
    }

    public void pageNext() {
        dataTable.setFirst(dataTable.getFirst() + dataTable.getRows());
    }

    public void pageLast() {
        int count = dataTable.getRowCount();
        int rows = dataTable.getRows();
        dataTable.setFirst(count - ((count % rows != 0) ? count % rows : rows));
    }
	
    public int getCurrentPage() {
        int rows = dataTable.getRows();
        int first = dataTable.getFirst();
        int count = dataTable.getRowCount();
        return (count / rows) - ((count - first) / rows) + 1;
    }

    public int getTotalPages() {
        int rows = dataTable.getRows();
        int count = dataTable.getRowCount();
        return (count / rows) + ((count % rows != 0) ? 1 : 0);
    }
	
	
	/**
	 * Método usado para a edição do registro que o usuário escolheu
	 * @author João Paulo
	 */
	public void editar()
	{
		try 
		{
			
			Usuario usuarioSelecionada = (Usuario) dataTable.getRowData();
//			System.out.println("Nome Usuario Selecionado  = " + usuarioSelecionada.getNome());
//			System.out.println("PK Usuario Selecionado  = " + usuarioSelecionada.getPkUsuario());
			
			usuario = new Usuario();
			permissoesSelecionadas = new ArrayList<Permissao>();
			nomePessoaVinculada = "";
			
			if(usuarioSelecionada != null && usuarioSelecionada.getPkUsuario() != null)
			{
				usuario = usuarioSelecionada;
				usuario.setConfirmarEmail(usuario.getEmail());
				
				if(usuario.getPessoa()!=null && usuario.getPessoa().getNome()!=null)
				{
					nomePessoaVinculada = usuario.getPessoa().getNome();
				}
				
				//Seta as permissões que o usuário tem na tabela de permissões
				if(usuario.getPermissoes()!=null && usuario.getPermissoes().size() >0)
				{
					permissoesSelecionadas = usuario.getPermissoes();
				}
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("editar", "");
		}
	}
	
	/**
	 * Retorna uma lista com os módulos do sistema
	 * @author João Paulo
	 * @return
	 */
	public List<SelectItem> consultaModulos()
	{
		Integer qtdModulos = 12;
		List<SelectItem> itens = new ArrayList<SelectItem>();
		SelectItem selectItem = null;
		String nomeModulo = "";
		Integer tipoModulo = 0;
		
		for (int i = 0; i < qtdModulos; i++) 
		{
			selectItem = new SelectItem();
			if(i==0)
			{
				nomeModulo = "Secretaria";
				tipoModulo = ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA;
			}
			else if(i==1)
			{
				nomeModulo = "Escola";
				tipoModulo = ConstantesSecretaria.PERMISSAO_TIPO_ESCOLA;
			}
			else if(i==2)
			{
				nomeModulo = "Merenda";
				tipoModulo = ConstantesSecretaria.PERMISSAO_TIPO_MERENDA;
			}
			else if(i==3)
			{
				nomeModulo = "Docentes";
				tipoModulo = ConstantesSecretaria.PERMISSAO_TIPO_DOCENTES;
			}
			else if(i==4)
			{
				nomeModulo = "Portal";
				tipoModulo = ConstantesSecretaria.PERMISSAO_TIPO_PORTAL;
			}
			else if(i==5)
			{
				nomeModulo = "Patrimônio";
				tipoModulo = ConstantesSecretaria.PERMISSAO_TIPO_PATROMONIO;
			}
			else if(i==6)
			{
				nomeModulo = "Almoxarifado";
				tipoModulo = ConstantesSecretaria.PERMISSAO_TIPO_ALMOXARIFADO;
			}
			else if(i==7)
			{
				nomeModulo = "Biblioteca";
				tipoModulo = ConstantesSecretaria.PERMISSAO_TIPO_BIBLIOTECA;
			}
			else if(i==8)
			{
				nomeModulo = "Transporte";
				tipoModulo = ConstantesSecretaria.PERMISSAO_TIPO_TRANSPORTE;
			}
			else if(i==9)
			{
				nomeModulo = "Social";
				tipoModulo = ConstantesSecretaria.PERMISSAO_TIPO_SOCIAL;
			}
			else if(i==10)
			{
				nomeModulo = "Protocolo";
				tipoModulo = ConstantesSecretaria.PERMISSAO_TIPO_PROTOCOLO;
			}
			else if(i==11)
			{
				nomeModulo = "Ouvidoria";
				tipoModulo = ConstantesSecretaria.PERMISSAO_TIPO_OUVIDORIA;
			}
			
			selectItem.setLabel(nomeModulo);
			selectItem.setValue(tipoModulo);
			
			itens.add(selectItem);
		}
		
		return itens;
	}
	
	/**
	 * Retorna uma lista com todos os tipos de sub menus
	 * @author João Paulo
	 * @return
	 */
	public List<SelectItem> consultaTipoTela()
	{
		Integer qtdTipos = 4;
		List<SelectItem> itens = new ArrayList<SelectItem>();
		SelectItem selectItem = null;
		String nomeModulo = "";
		Integer tipoModulo = 0;
		
		for (int i = 0; i < qtdTipos; i++) 
		{
			selectItem = new SelectItem();
			if(i==0)
			{
				nomeModulo = "Cadastro";
				tipoModulo = ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO;
			}
			else if(i==1)
			{
				nomeModulo = "Lançamento";
				tipoModulo = ConstantesSecretaria.TIPO_SUB_MENU_LANCAMENTO;
			}
			else if(i==2)
			{
				nomeModulo = "Consulta";
				tipoModulo = ConstantesSecretaria.TIPO_SUB_MENU_CONSULTA;
			}
			else if(i==3)
			{
				nomeModulo = "Relatório";
				tipoModulo = ConstantesSecretaria.TIPO_SUB_MENU_RELATORIO;
			}
			
			selectItem.setLabel(nomeModulo);
			selectItem.setValue(tipoModulo);
			
			itens.add(selectItem);
		}
		
		return itens;
	}

	
	/**
	 * Retorna uma lista com todos os tipos de sub menus
	 * @author João Paulo
	 * @return
	 */
	public List<SelectItem> consultaTela()
	{
		List<SelectItem> itens = new ArrayList<SelectItem>();
		List<Tela> todasTelas = popularTelas();
		SelectItem selectItem = null;
		
		for (Tela tela : todasTelas) 
		{
			selectItem = new SelectItem();
			if((moduloSelecionado!=null && moduloSelecionado.getTipo()!=null) || (tipoTelaSelecionado!=null && tipoTelaSelecionado.getTipo()!=null))
			{
				if(moduloSelecionado!=null && moduloSelecionado.getTipo()!=null && tipoTelaSelecionado!=null && tipoTelaSelecionado.getTipo()!=null)
				{
					if(tela.getModulo().equals(moduloSelecionado.getTipo()) && tela.getTipoSubMenu().equals(tipoTelaSelecionado.getTipo())) 
					{ 
						selectItem.setLabel(tela.getNome());
						selectItem.setValue(tela.getTipoTela());
						itens.add(selectItem);
					}
				}
				else if(moduloSelecionado!=null && moduloSelecionado.getTipo()!=null && tipoTelaSelecionado!=null && tipoTelaSelecionado.getTipo()==null)
				{
					if(tela.getModulo().equals(moduloSelecionado.getTipo())) 
					{ 
						selectItem.setLabel(tela.getNome());
						selectItem.setValue(tela.getTipoTela());
						itens.add(selectItem);
					}
				}
				else if(moduloSelecionado!=null && moduloSelecionado.getTipo()==null && tipoTelaSelecionado!=null && tipoTelaSelecionado.getTipo()!=null)
				{
					if(tela.getTipoSubMenu().equals(tipoTelaSelecionado.getTipo())) 
					{ 
						selectItem.setLabel(tela.getNome());
						selectItem.setValue(tela.getTipoTela());
						itens.add(selectItem);
					}
				}
			}
			else
			{
				selectItem.setLabel(tela.getNome());
				selectItem.setValue(tela.getTipoTela());
				itens.add(selectItem);
			}
		}
		
		return itens;
	}
	
	/**
	 * Preenche uma lista com todas as telas do sistema
	 * @author João Paulo
	 * @return
	 */
	public List<Tela> popularTelas()
	{
		Integer qtdTelas = 5;
		List<Tela> telas = new ArrayList<Tela>();
		Tela tela = null;
		for (int i = 0; i < qtdTelas; i++) 
		{
			tela= new Tela();
			
			//SECRETARIA
			if(i==0)
			{ 
				tela.setNome("Pessoa");
				tela.setTipoSubMenu(ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO);
				tela.setTipoTela(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_PESSOA);
				tela.setModulo(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA);
			}
			else if(i==1)
			{
				tela.setNome("Fornecedor");
				tela.setTipoSubMenu(ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO);
				tela.setTipoTela(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_FORNECEDOR);
				tela.setModulo(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA);
			}
			else if(i==2)
			{
				tela.setNome("Usuário");
				tela.setTipoSubMenu(ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO);
				tela.setTipoTela(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_USUARIO);
				tela.setModulo(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA);
			}
			else if(i==3)
			{
				tela.setNome("Unidade Escolar");
				tela.setTipoSubMenu(ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO);
				tela.setTipoTela(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_UNIDADE_ESCOLAR);
				tela.setModulo(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA);
			}
			//ESCOLA
			else if(i==4)
			{
				tela.setNome("Matrícula Aluno");
				tela.setTipoSubMenu(ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO);
				tela.setTipoTela(ConstantesSecretaria.PERMISSAO_TIPO_ESCOLA_CADASTROS_MATRICULA_ALUNO);
				tela.setModulo(ConstantesSecretaria.PERMISSAO_TIPO_ESCOLA);
			}
			
			telas.add(tela);
		}
		
		return telas;
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
	public String getClassSecretariaCadastroSubMenu() {
		return classSecretariaCadastroSubMenu;
	}

	public void setClassSecretariaCadastroSubMenu(String classSecretariaCadastroSubMenu) {
		this.classSecretariaCadastroSubMenu = classSecretariaCadastroSubMenu;
	}

	public String getClassSecretariaConsultaSubMenu() {
		return classSecretariaConsultaSubMenu;
	}

	public void setClassSecretariaConsultaSubMenu(String classSecretariaConsultaSubMenu) {
		this.classSecretariaConsultaSubMenu = classSecretariaConsultaSubMenu;
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

	public String getClassSecretariaLancamentoSubMenu() {
		return classSecretariaLancamentoSubMenu;
	}

	public void setClassSecretariaLancamentoSubMenu(String classSecretariaLancamentoSubMenu) {
		this.classSecretariaLancamentoSubMenu = classSecretariaLancamentoSubMenu;
	}

	public String getClassSecretariaRelatorioSubMenu() {
		return classSecretariaRelatorioSubMenu;
	}

	public void setClassSecretariaRelatorioSubMenu(String classSecretariaRelatorioSubMenu) {
		this.classSecretariaRelatorioSubMenu = classSecretariaRelatorioSubMenu;
	}

	public List<Usuario> getUsuariosCadastrados() {
		return usuariosCadastrados;
	}

	public void setUsuariosCadastrados(List<Usuario> usuariosCadastrados) {
		this.usuariosCadastrados = usuariosCadastrados;
	}

	public Usuario getUsuarioCadastradoSelecionado() {
		return usuarioCadastradoSelecionado;
	}

	public void setUsuarioCadastradoSelecionado(Usuario usuarioCadastradoSelecionado) {
		this.usuarioCadastradoSelecionado = usuarioCadastradoSelecionado;
	}
	public String getCpfPesquisar() {
		return cpfPesquisar;
	}

	public void setCpfPesquisar(String cpfPesquisar) {
		this.cpfPesquisar = cpfPesquisar;
	}

	public String getUsuarioPesquisar() {
		return usuarioPesquisar;
	}

	public void setUsuarioPesquisar(String usuarioPesquisar) {
		this.usuarioPesquisar = usuarioPesquisar;
	}

	public String getEmailPesquisar() {
		return emailPesquisar;
	}

	public void setEmailPesquisar(String emailPesquisar) {
		this.emailPesquisar = emailPesquisar;
	}

	public String getClassSecretariaCadastroMatriculaAluno() {
		return classSecretariaCadastroMatriculaAluno;
	}

	public void setClassSecretariaCadastroMatriculaAluno(String classSecretariaCadastroMatriculaAluno) {
		this.classSecretariaCadastroMatriculaAluno = classSecretariaCadastroMatriculaAluno;
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

	public String getClassEscolaCadastroMatriculaAluno() {
		return classEscolaCadastroMatriculaAluno;
	}

	public void setClassEscolaCadastroMatriculaAluno(String classEscolaCadastroMatriculaAluno) {
		this.classEscolaCadastroMatriculaAluno = classEscolaCadastroMatriculaAluno;
	}

	public Modulo getModuloSelecionado() {
		return moduloSelecionado;
	}

	public void setModuloSelecionado(Modulo moduloSelecionado) {
		this.moduloSelecionado = moduloSelecionado;
	}

	public List<SelectItem> getComboModulo() {
		comboModulo.clear();
		comboModulo.addAll(consultaModulos());
		return comboModulo;
	}

	public void setComboModulo(List<SelectItem> comboModulo) {
		this.comboModulo = comboModulo;
	}

	public TipoTela getTipoTelaSelecionado() {
		return tipoTelaSelecionado;
	}

	public void setTipoTelaSelecionado(TipoTela tipoTelaSelecionado) {
		this.tipoTelaSelecionado = tipoTelaSelecionado;
	}

	public List<SelectItem> getComboTipoTela() {
		comboTipoTela.clear();
		comboTipoTela.addAll(consultaTipoTela());
		return comboTipoTela;
	}

	public void setComboTipoTela(List<SelectItem> comboTipoTela) {
		this.comboTipoTela = comboTipoTela;
	}

	public Tela getTelaSelecionada() {
		return telaSelecionada;
	}

	public void setTelaSelecionada(Tela telaSelecionada) {
		this.telaSelecionada = telaSelecionada;
	}

	public List<SelectItem> getComboTela() {
		comboTela.clear();
		comboTela.addAll(consultaTela());
		return comboTela;
	}

	public void setComboTela(List<SelectItem> comboTela) {
		this.comboTela = comboTela;
	}

	public Permissao getPermissaoSelecionada() {
		return permissaoSelecionada;
	}

	public void setPermissaoSelecionada(Permissao permissaoSelecionada) {
		this.permissaoSelecionada = permissaoSelecionada;
	}

	public List<SelectItem> getComboPermissao() {
		comboPermissao.clear();
		comboPermissao.addAll(buscarPermissoes());
		return comboPermissao;
	}

	public void setComboPermissao(List<SelectItem> comboPermissao) {
		this.comboPermissao = comboPermissao;
	}
}

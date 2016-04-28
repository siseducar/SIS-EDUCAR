package modulos.secretaria.servlet;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
public class UsuarioServlet implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	//Variaveis
	Usuario usuario;
	Usuario usuarioLogado;
	private String nomePessoaVinculada;
	
	private List<Permissao> permissoes;
    private List<Permissao> permissoesSelecionadas;
    
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
    
    //VARIAVEIS LIGADAS AOS SUB MÓDULOS
    private String classSecretariaCadastroPessoa 			= classAtributeHidden;
    private String classSecretariaCadastroFornecedor		= classAtributeHidden;
    private String classSecretariaCadastroUsuario   		= classAtributeHidden;
    private String classSecretariaCadastroUnidadeEscolar   	= classAtributeHidden;
	private String classSecretariaConsultaGraficos   		= classAtributeHidden;
    private String classSecretariaConsultaRelatorios   		= classAtributeHidden;
    
	/**
	 * Construtor
	 */
	public UsuarioServlet()
	{
		cpfPesquisar = "";
		usuarioPesquisar = "";
		emailPesquisar = "";
		
		if(usuarioCadastradoSelecionado==null)
		{
			usuarioCadastradoSelecionado = new Usuario();
		}
		
		nomePessoaVinculada = "";
		usuario = new Usuario();
		usuarioLogado = (Usuario) new SisEducarServlet().getSessionObject(ConstantesSisEducar.USUARIO_LOGADO);
		
		//LIBERA AS TELAS DO SISTEMA DE ACORDO COM AS PERMISSÕES DO USUÁRIO
		validarPermissoes();
		
		//BUSCA AS PERMISSÕES CADASTRADAS NO BANCO DE DADOS E POPULA A TABELA DE PERMISSÕES NO CADASTRO DE USUÁRIO
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
			usuario.setSenha(SisEducarServlet.criptografarSenha(usuario.getSenha()));
			
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
	public List<Permissao> buscarPermissoes()
	{
		try 
		{
			List<Permissao> permissoes = new UsuarioDAO().buscarPermissoes();
			for (Permissao permissao : permissoes) 
			{
				/* ADICIONA O NOME DOS MÓDULOS NAS PERMISSÕES */
				if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_SECRETARIA)) { permissao.setNomeModulo("Secretaria"); }
				else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_ESCOLA)) { permissao.setNomeModulo("Escola"); }
				else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_MERENDA)) { permissao.setNomeModulo("Merenda"); }
				else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_DOCENTES)) { permissao.setNomeModulo("Docentes"); }
				else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_PORTAL)) { permissao.setNomeModulo("Portal"); }
				else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_PATROMONIO)) { permissao.setNomeModulo("Patrimônio"); }
				else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_ALMOXARIFADO)) { permissao.setNomeModulo("Almoxarifado"); }
				else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_BIBLIOTECA)) { permissao.setNomeModulo("Biblioteca"); }
				else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_TRANSPORTE)) { permissao.setNomeModulo("Transporte"); }
				else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_SOCIAL)) { permissao.setNomeModulo("Social"); }
				else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_PROTOCOLO)) { permissao.setNomeModulo("Protocolo"); }
				else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_OUVIDORIA)) { permissao.setNomeModulo("Ouvidoria"); }

				/* ADICIONA O NOME DOS SUB MENUS NAS PERMISSÕES */
				if(permissao.getTipoSubMenuResponsavel().equals(ConstantesRH.TIPO_SUB_MENU_CADASTRO)) { permissao.setNomeSubMenu("Cadastro"); }
				else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesRH.TIPO_SUB_MENU_LANCAMENTO)) { permissao.setNomeSubMenu("Lançamento"); }
				else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesRH.TIPO_SUB_MENU_CONSULTA)) { permissao.setNomeSubMenu("Consulta"); }
				else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesRH.TIPO_SUB_MENU_RELATORIO)) { permissao.setNomeSubMenu("Relatório"); }
			}
			
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
			
			if(usuarioLogado!=null)
			{
				for (Permissao permissao : usuarioLogado.getPermissoes()) 
				{
					/* MÓDULO SECRETÁRIA */
					if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_SECRETARIA)) 
					{
						//LIBERADO O ACESSO AO MÓDULO
						if(!moduloSecretariaLiberado)
						{
							classModuloSecretaria = classAtributeDropDown; 
							moduloSecretariaLiberado = true;
						}
						
						//LIBERA ACESSO AOS SUB MENUS
						if(permissao.getTipoSubMenuResponsavel().equals(ConstantesRH.TIPO_SUB_MENU_CADASTRO))
						{
							if(!subMenuCadastroSecretariaLiberado)
							{
								classSecretariaCadastroSubMenu = classAtributeDropDown; 
								subMenuCadastroSecretariaLiberado = true;
							}
						}
						else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesRH.TIPO_SUB_MENU_LANCAMENTO))
						{
							if(!subMenuLancamentoSecretariaLiberado)
							{
								setClassSecretariaLancamentoSubMenu(classAtributeDropDown); 
								subMenuLancamentoSecretariaLiberado = true;
							}
						}
						else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesRH.TIPO_SUB_MENU_CONSULTA))
						{
							if(!subMenuConsultaSecretariaLiberado)
							{
								classSecretariaConsultaSubMenu = classAtributeDropDown; 
								subMenuConsultaSecretariaLiberado = true;
							}
						}
						else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesRH.TIPO_SUB_MENU_RELATORIO))
						{
							if(!subMenuRelatorioSecretariaLiberado)
							{
								classSecretariaRelatorioSubMenu = classAtributeDropDown; 
								subMenuRelatorioSecretariaLiberado = true;
							}
						}
						
						//LIBERA ACESSO AS TELAS DE CADASTRO
						if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_SECRETARIA_CADASTROS_PESSOA)) { classSecretariaCadastroPessoa = ""; }
						else if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_SECRETARIA_CADASTROS_USUARIO)) { classSecretariaCadastroUsuario = ""; }
						else if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_SECRETARIA_CADASTROS_FORNECEDOR)) { classSecretariaCadastroFornecedor = ""; }
						else if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_SECRETARIA_CADASTROS_UNIDADE_ESCOLAR)) { classSecretariaCadastroUnidadeEscolar = ""; }

						//LIBERA ACESSO AS TELAS DE CADASTRO
						else if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_GRAFICOS)) { classSecretariaConsultaGraficos = ""; }
						else if(permissao.getTipo().equals(ConstantesRH.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_RELATORIOS)) { classSecretariaConsultaRelatorios = ""; }
					}
					/* MÓDULO ESCOLA */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_ESCOLA)) 	
					{ 
						if(!moduloEscolaLiberado)
						{
							classModuloEscola = classAtributeDropDown;
							moduloEscolaLiberado = true;
						}
					}
					/* MÓDULO MERENDA */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_MERENDA)) 	
					{ 
						if(!moduloMerendaLiberado)
						{
							classModuloMerenda = classAtributeDropDown;
							moduloMerendaLiberado = true;
						}
					}
					/* MÓDULO DOCENTES */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_DOCENTES)) 	
					{ 
						if(!moduloDocentesLiberado)
						{
							classModuloDocentes = classAtributeDropDown; 
							moduloDocentesLiberado = true;
						}
					}
					/* MÓDULO PORTAL */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_PORTAL)) 
					{ 
						if(!moduloPortalLiberado)
						{
							classModuloPortal = classAtributeDropDown; 
							moduloPortalLiberado = true;
						}
					}
					/* MÓDULO PATRIMONIO */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_PATROMONIO)) 
					{ 
						if(!moduloPatrimonioLiberado)
						{
							classModuloPatrimonio = classAtributeDropDown; 
							moduloPatrimonioLiberado = true;
						}
					}
					/* MÓDULO ALMOXARIFADO */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_ALMOXARIFADO)) 
					{ 
						if(!moduloAlmoxarifadoLiberado)
						{
							classModuloAlmoxarifado = classAtributeDropDown; 
							moduloAlmoxarifadoLiberado = true;
						}
					}
					/* MÓDULO BIBLIOTECA */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_BIBLIOTECA)) 
					{ 
						if(!moduloBibliotecaLiberado)
						{
							classModuloBiblioteca = classAtributeDropDown; 
							moduloBibliotecaLiberado = true;
						}
					}
					/* MÓDULO TRANSPORTE */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_TRANSPORTE)) 
					{ 
						if(!moduloTransporteLiberado)
						{
							classModuloTransporte = classAtributeDropDown; 
							moduloTransporteLiberado = true;
						}
					}
					/* MÓDULO SOCIAL */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_SOCIAL)) 
					{ 
						if(!moduloSocialLiberado)
						{
							classModuloSocial = classAtributeDropDown; 
							moduloSocialLiberado = true;
						}
					}
					/* MÓDULO PROTOCOLO */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_PROTOCOLO)) 
					{ 
						if(!moduloProtocoloLiberado)
						{
							classModuloProtocolo = classAtributeDropDown; 
							moduloProtocoloLiberado = true;
						}
					}
					/* MÓDULO OUVIDORIA */
					else if(permissao.getTipoModuloResponsavel().equals(ConstantesRH.PERMISSAO_TIPO_OUVIDORIA)) 
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
	
	/**
	 * Método usado para a edição do registro que o usuário escolheu
	 * @author João Paulo
	 */
	public void editar()
	{
		try 
		{
			usuario = new Usuario();
			permissoesSelecionadas = new ArrayList<Permissao>();
			
			if(usuarioCadastradoSelecionado!=null && usuarioCadastradoSelecionado.getPkUsuario()!=null)
			{
				usuario = usuarioCadastradoSelecionado;
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
}

package modulos.secretaria.servlet;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
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
	private Boolean btRemoverEnabled;
	private Boolean btCadastrarEnabled;
	private Boolean btConsultarEnabled;
	private Boolean temPermissaoCadastrar = false;
	private Boolean temPermissaoExcluir = false;
	private Boolean temPermissaoConsultar = false;
	private String nomePessoaVinculada;
	
	private Modulo moduloSelecionado;
	private List<SelectItem> comboModulo;
	
	private TipoTela tipoTelaSelecionado;
	private List<SelectItem> comboTipoTela;
	
	private Tela telaSelecionada;
	private List<SelectItem> comboTela;
	
	private List<Permissao> todasPermissoes;
	private List<Permissao> permissoes;
    
    private Permissao permissaoSelecionada;
    private List<SelectItem> comboPermissao;
    
    /*Variáveis para pesquisar os usuários cadastrados no banco de dados*/
    private List<Usuario> usuariosCadastrados;
    private Usuario usuarioCadastradoSelecionado;
    private String cpfPesquisar;
	private String usuarioPesquisar;
    private String emailPesquisar;
        
	/**
	 * Construtor
	 * @throws SQLException 
	 */
	public UsuarioServlet() throws SQLException
	{
		sisEducarServlet = new SisEducarServlet();
		comboModulo = new ArrayList<SelectItem>();
		comboTipoTela = new ArrayList<SelectItem>();
		comboTela = new ArrayList<SelectItem>();
		comboPermissao = new ArrayList<SelectItem>();
		permissoes = new ArrayList<Permissao>();
		
		btRemoverEnabled = false;
		btCadastrarEnabled = false;
		btConsultarEnabled = false;
		
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
		usuarioLogado = (Usuario)  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		
		validarPermissoesTela();
		if(temPermissaoCadastrar) { setBtCadastrarEnabled(true); }
		if(temPermissaoConsultar) { setBtConsultarEnabled(true); }
		
		todasPermissoes = new UsuarioDAO().buscarPermissoes(null, null, null);
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
			Boolean atualizacaoUsuario = false;
			String generoSelecionado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("inputGeneroAux");
			String urlBotaoLink = "http://localHost:8080/coruja/validacaoUsuario.xhtml?validacao=";
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
			/* Valida Senha*/
			if(usuario.getSenha()==null || (usuario.getSenha()!=null && usuario.getSenha().length()==0))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha é obrigatória", null));
				return null;
			}
			if(usuario.getConfirmarSenha()==null || (usuario.getConfirmarSenha()!=null && usuario.getConfirmarSenha().length()==0))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A confirmação de senha é obrigatória", null));
				return null;
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
				atualizacaoUsuario = true;
				resultado = usuarioDAO.alterarUsuario(usuario);
			}
			else
			{
				resultado = usuarioDAO.inserirUsuario(usuario);
			}
			
			if(resultado)
			{
				if(!atualizacaoUsuario)
				{
					//Como o usuário estará em confirmação ele estará com o status imcompleto, então eu seto o status imcompleto nele
					usuario.setStatus(ConstantesSisEducar.STATUS_ATIVO);
				}
				usuario = usuarioDAO.buscarUsuario(usuario);
				
				/* Adiciona as permissões para o usuário*/
				if(permissoes!=null && permissoes.size() >0)
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
				
				if(!atualizacaoUsuario)
				{
//					/* Envio de email de validação */
//					urlBotaoLink += SisEducarServlet.criptografarURL(true, usuario.getEmail());
//					email = EmailUtils.inicializarPropriedades();
//					email.setSubjectMail("Confirmação de cadastro de usuário");
//					email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Olá " + usuario.getNome() + ",</p> " + 
//							" <p style=\"text-align:left; font-size:17px; \">A sua solicitação de cadastro foi realizada com sucesso.</p> " + 
//							" <p style=\"font-style:italic; font-size:17px; text-align:left;\"><b>Para que o cadastro seja efetivado clique no botão abaixo. Atenção o link irá expirar em 48 horas.</b></p>", "<p style=\"font-size:17px; text-align:left;\">Caso o botão acima não funcione clique no link abaixo:</p>", urlBotaoLink, urlBotaoLink, true, "Ativar Usuário"));
//					
//					destinatarios.put(usuario.getEmail(), usuario.getNome());
//					email.setToMailsUsers(destinatarios);
//					
//					resultadoEnvioEmail = new EmailUtils().enviarEmail(email);
//					//Se o envio não der certo eu removo o usuário que foi cadastrado no sistema
//					if(!resultadoEnvioEmail)
//					{
//						resultadoRemocaoUsuario = usuarioDAO.removerUsuario(usuario);
//						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário registrado, mas o email não foi enviado", null));
//					}
//					else
//					{
//						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviamos um email de confirmação para o email cadastrado", null));	
//					}
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário cadastrado com sucesso", null));  
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não registrado", null));  
			}
			
			if(atualizacaoUsuario)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário editado com sucesso", null));  
			}
			
			resetarUsuario();
			return null;
		}
		catch (Exception e) 
		{
			Logs.addFatal("Erro ao cadastrar!", "cadastrarUsuario");
			return null;
		}
	}
	
	/**
	 * USado para remover um usuario e suas dependencias
	 * @author João Paulo
	 * @return
	 */
	public String removerUsuario()
	{
		try 
		{
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Boolean resultadoRemocaoUsuario = false;
			Boolean resultadoRemocaoPermissoesUsuario = false;
			
			//Se o usuário já está cadastrado no banco de dados eu apenas atualizo as informações do mesmo, caso contrário eu salvo um novo usuário
			if(usuario.getPkUsuario()!=null)
			{
				resultadoRemocaoUsuario = usuarioDAO.removerUsuario(usuario);
				resultadoRemocaoPermissoesUsuario = usuarioDAO.removerPermissoesUsuario(usuario);
				
				if(resultadoRemocaoUsuario && resultadoRemocaoPermissoesUsuario)
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário removido", null));  
				}
				else
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não removido", null));  
				}
				
				resetarUsuario();
			}
			
			return null;
		}
		catch (Exception e) 
		{
			Logs.addFatal("Erro ao remover!", "cadastrarUsuario");
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
			nomePessoaVinculada = "";
			permissoes = new ArrayList<Permissao>();
			usuariosCadastrados = new ArrayList<Usuario>();
			usuarioCadastradoSelecionado = null;
		    cpfPesquisar = "";
			usuarioPesquisar = "";
		    emailPesquisar = "";
		    moduloSelecionado = null;
		    tipoTelaSelecionado = null;
		    telaSelecionada = null;
		    permissaoSelecionada = null;
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
	 * Adiciona a permissão selecionada na tabela de permissões do usuário
	 * @author João Paulo
	 * @return
	 */
	public void adicionarPermissao()
	{
		try
		{
			Boolean permissaoExiste = false;
			Permissao permissaoAux = null;
			if(permissaoSelecionada!=null && permissaoSelecionada.getPkPermissao()!=null)
			{
				//OBTEM O OBJETO PERMISSÃO COM TODAS AS SUAS INFORMAÇÕES
				for (Permissao permissao : todasPermissoes) 
				{
					if(permissao.getPkPermissao().equals(permissaoSelecionada.getPkPermissao()))
					{
						permissaoAux = permissao;
						break;
					}
				}
				
				//SETA A VARIÁVEL DE PERMISSÃO DUPLICADA
				for (Permissao permissao : permissoes) 
				{
					if(permissaoSelecionada.getPkPermissao().equals(permissao.getPkPermissao())) { permissaoExiste = true; }
				}

				//VERIFICA SE A PERMISSÃO JÁ FOI ADICIONADA NA TABELA, CASO ESTEJA ENTÃO NÃO ADICIONA NOVAMENTE
				if(!permissaoExiste)
				{
					permissoes.add(preencherInformacoesFaltantesPermissao(permissaoAux));
				}
				
				permissaoSelecionada = new Permissao();
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("adicionarPermissao", "adicionarPermissao");
		}
	}
	
	/**
	 * Preenche as informações restantes da permissão apenas para exibir para o usuário
	 * @author João Paulo
	 * @param permissao
	 * @return
	 */
	public Permissao preencherInformacoesFaltantesPermissao(Permissao permissao)
	{
		
		/* ADICIONA O NOME DOS MÓDULOS NAS PERMISSÕES */
		if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA)) { permissao.setNomeModulo("Secretaria"); }
		else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_ESCOLA)) { permissao.setNomeModulo("Escola"); }
		else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_MERENDA)) { permissao.setNomeModulo("Merenda"); }
		else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_DOCENTES)) { permissao.setNomeModulo("Docentes"); }
		else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_PORTAL)) { permissao.setNomeModulo("Portal"); }
		else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_PATROMONIO)) { permissao.setNomeModulo("Patrimônio"); }
		else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_ALMOXARIFADO)) { permissao.setNomeModulo("Almoxarifado"); }
		else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_BIBLIOTECA)) { permissao.setNomeModulo("Biblioteca"); }
		else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_TRANSPORTE)) { permissao.setNomeModulo("Transporte"); }
		else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SOCIAL)) { permissao.setNomeModulo("Social"); }
		else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_PROTOCOLO)) { permissao.setNomeModulo("Protocolo"); }
		else if(permissao.getTipoModuloResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_OUVIDORIA)) { permissao.setNomeModulo("Ouvidoria"); }

		/* ADICIONA O NOME DOS SUB MENUS NAS PERMISSÕES */
		if(permissao.getTipoSubMenuResponsavel().equals(ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO)) { permissao.setNomeSubMenu("Cadastro"); }
		else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesSecretaria.TIPO_SUB_MENU_LANCAMENTO)) { permissao.setNomeSubMenu("Lançamento"); }
		else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesSecretaria.TIPO_SUB_MENU_CONSULTA)) { permissao.setNomeSubMenu("Consulta"); }
		else if(permissao.getTipoSubMenuResponsavel().equals(ConstantesSecretaria.TIPO_SUB_MENU_RELATORIO)) { permissao.setNomeSubMenu("Relatório"); }
		
		/* ADICIONA O NOME DA TELA SELECIONADA */
		/* SECRETARIA*/
		if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_PESSOA)) { permissao.setNomeTela("Pessoa"); }
		else if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_USUARIO)) { permissao.setNomeTela("Usuário"); }
		else if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_FORNECEDOR)) { permissao.setNomeTela("Fornecedor"); }
		else if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_UNIDADE_ESCOLAR)) { permissao.setNomeTela("Unidade Escolar"); }
		else if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_AMBIENTE)) { permissao.setNomeTela("Ambiente"); }
		else if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_HISTORICO_ACESSO)) { permissao.setNomeTela("Histórico Acesso"); }
	
		/* ESCOLA */
		else if(permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_ESCOLA_CADASTROS_HORARIO)) { permissao.setNomeTela("Horário"); }
		
		return permissao;
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
	
	public void validarPermissoesTela()
	{
		try 
		{
			if(usuarioLogado!=null)
			{
				for (Permissao permissao : usuarioLogado.getPermissoes()) 
				{
					if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_EXCLUIR) 
							&& permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_USUARIO))
					{
						temPermissaoExcluir = true;
					}
					else if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_CADASTRAR) 
							&& permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_USUARIO))
					{
						temPermissaoCadastrar = true;
					}
					else if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_CONSULTAR) 
							&& permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_USUARIO))
					{
						temPermissaoConsultar = true;
					}
				}
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("validarPermissoesTela", "validarPermissoesTela");
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
	 * Remove apenas a permissão selecionada da tabela
	 */
	public void removerPermissao()
	{
		List<Permissao> listPermissoesAux = new ArrayList<Permissao>();
		Permissao permissaoSelecionada = null;
		
		if(dataTablePermissao!=null && dataTablePermissao.getRowData()!=null) { permissaoSelecionada = (Permissao) dataTablePermissao.getRowData(); }
		
		if(permissaoSelecionada != null && permissaoSelecionada.getPkPermissao() != null)
		{
			for (Permissao permissao : permissoes) 
			{
				if(!permissao.getPkPermissao().equals(permissaoSelecionada.getPkPermissao()))
				{
					listPermissoesAux.add(permissao);
				}
			}
			
			permissoes = listPermissoesAux;
		}
	}
	
	/**
	 * Remove todas as permissões do usuário da tabela
	 */
	public void removerTodasPermissoes()
	{
		try 
		{
			permissoes = new ArrayList<Permissao>();
		} 
		catch (Exception e) 
		{
			Logs.addError("removerTodasPermissoes", "removerTodasPermissoes");
		}
	}
	
	
	private HtmlDataTable dataTable;
	private HtmlDataTable dataTablePermissao;
	   
    public HtmlDataTable getDataTable() {
          return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
          this.dataTable = dataTable;
    }            

    public HtmlDataTable getDataTablePermissao() {
		return dataTablePermissao;
	}

	public void setDataTablePermissao(HtmlDataTable dataTablePermissao) {
		this.dataTablePermissao = dataTablePermissao;
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
    
    public void pagePermissaoFirst() {
    	dataTablePermissao.setFirst(0);
    }
    
    public void pagePermissaoPrevious() {
    	dataTablePermissao.setFirst(dataTablePermissao.getFirst() - dataTablePermissao.getRows());
    }
    
    public void pagePermissaoNext() {
    	dataTablePermissao.setFirst(dataTablePermissao.getFirst() + dataTablePermissao.getRows());
    }
    
    public void pagePermissaoLast() {
    	int count = dataTablePermissao.getRowCount();
    	int rows = dataTablePermissao.getRows();
    	dataTablePermissao.setFirst(count - ((count % rows != 0) ? count % rows : rows));
    }
    
    public int getCurrentPermissaoPage() {
    	int rows = dataTablePermissao.getRows();
    	int first = dataTablePermissao.getFirst();
    	int count = dataTablePermissao.getRowCount();
    	return (count / rows) - ((count - first) / rows) + 1;
    }
    
    public int getTotalPermissaoPages() {
    	int rows = dataTablePermissao.getRows();
    	int count = dataTablePermissao.getRowCount();
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
			permissoes = new ArrayList<Permissao>();
			nomePessoaVinculada = "";
			
			if(usuarioSelecionada != null && usuarioSelecionada.getPkUsuario() != null)
			{
				usuario = usuarioSelecionada;
				usuario.setConfirmarEmail(usuario.getEmail());
				
				//PREENCHE O OBJETO PERMISSÃO COM AS INFORMAÇÕES QUE A TABELA DE PERMISSÃO PRECISA PARA EXIBIR AS INFORMAÇÕES DA PERMISSÃO
				for (Permissao permissao : usuario.getPermissoes()) 
				{
					permissao = preencherInformacoesFaltantesPermissao(permissao);
				}
				
				if(usuario.getPessoa()!=null && usuario.getPessoa().getNome()!=null)
				{
					nomePessoaVinculada = usuario.getPessoa().getNome();
				}
				
				//Seta as permissões que o usuário tem na tabela de permissões
				if(usuario.getPermissoes()!=null && usuario.getPermissoes().size() >0)
				{
					permissoes = usuario.getPermissoes();
				}
				
				if(temPermissaoExcluir) { btRemoverEnabled = true; }
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
		Integer qtdTelas = 6;
		List<Tela> telas = new ArrayList<Tela>();
		Tela tela = null;
		for (int i = 0; i <= qtdTelas; i++) 
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
			else if(i==4)
			{
				tela.setNome("Ambiente");
				tela.setTipoSubMenu(ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO);
				tela.setTipoTela(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_AMBIENTE);
				tela.setModulo(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA);
			}
			else if(i==5)
			{
				tela.setNome("Histórico Acesso");
				tela.setTipoSubMenu(ConstantesSecretaria.TIPO_SUB_MENU_CONSULTA);
				tela.setTipoTela(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_HISTORICO_ACESSO);
				tela.setModulo(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA);
			}
			//ESCOLA
			else if(i==6)
			{
				tela.setNome("Horário");
				tela.setTipoSubMenu(ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO);
				tela.setTipoTela(ConstantesSecretaria.PERMISSAO_TIPO_ESCOLA_CADASTROS_HORARIO);
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
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
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

	public List<Permissao> getTodasPermissoes() {
		return todasPermissoes;
	}

	public void setTodasPermissoes(List<Permissao> todasPermissoes) {
		this.todasPermissoes = todasPermissoes;
	}

	public Boolean getBtRemoverEnabled() {
		return btRemoverEnabled;
	}

	public void setBtRemoverEnabled(Boolean btRemoverEnabled) {
		this.btRemoverEnabled = btRemoverEnabled;
	}

	public Boolean getBtCadastrarEnabled() {
		return btCadastrarEnabled;
	}

	public void setBtCadastrarEnabled(Boolean btCadastrarEnabled) {
		this.btCadastrarEnabled = btCadastrarEnabled;
	}

	public Boolean getBtConsultarEnabled() {
		return btConsultarEnabled;
	}

	public void setBtConsultarEnabled(Boolean btConsultarEnabled) {
		this.btConsultarEnabled = btConsultarEnabled;
	}

	public Boolean getTemPermissaoCadastrar() {
		return temPermissaoCadastrar;
	}

	public void setTemPermissaoCadastrar(Boolean temPermissaoCadastrar) {
		this.temPermissaoCadastrar = temPermissaoCadastrar;
	}

	public Boolean getTemPermissaoExcluir() {
		return temPermissaoExcluir;
	}

	public void setTemPermissaoExcluir(Boolean temPermissaoExcluir) {
		this.temPermissaoExcluir = temPermissaoExcluir;
	}

	public Boolean getTemPermissaoConsultar() {
		return temPermissaoConsultar;
	}

	public void setTemPermissaoConsultar(Boolean temPermissaoConsultar) {
		this.temPermissaoConsultar = temPermissaoConsultar;
	}
}

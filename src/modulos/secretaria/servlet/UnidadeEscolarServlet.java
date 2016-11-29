package modulos.secretaria.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import modulos.secretaria.dao.AmbienteDAO;
import modulos.secretaria.dao.ContatoDAO;
import modulos.secretaria.dao.EnderecoDAO;
import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.dao.TerrenoDAO;
import modulos.secretaria.dao.UnidadeEscolarDAO;
import modulos.secretaria.om.Ambiente;
import modulos.secretaria.om.Bloco;
import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Contato;
import modulos.secretaria.om.Endereco;
import modulos.secretaria.om.Estado;
import modulos.secretaria.om.Pais;
import modulos.secretaria.om.Permissao;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.RedeEnsino;
import modulos.secretaria.om.Regiao;
import modulos.secretaria.om.SituacaoFuncionamento;
import modulos.secretaria.om.Terreno;
import modulos.secretaria.om.TipoAmbiente;
import modulos.secretaria.om.TipoOcupacao;
import modulos.secretaria.om.UnidadeEscolar;
import modulos.secretaria.om.Usuario;
import modulos.secretaria.utils.ConstantesSecretaria;
import modulos.sisEducar.utils.ConstantesSisEducar;
import modulos.sisEducar.utils.Logs;

@ManagedBean(name="unidadeEscolarServlet")
@ViewScoped
public class UnidadeEscolarServlet implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{paramServlet}")
	private ParametrosServlet paramDados;
	
	private UnidadeEscolar unidadeEscolar;
	private Terreno terreno;
	private Pessoa diretor;
	private String nomeDiretor;
	private String cpfDiretor;
	private Pais paisDado;
	private Estado estadoDado;
	private Cidade cidadeDado;
	private Endereco enderecoDado;
	private Contato contatoDado;
	private RedeEnsino redeEnsinoDado;
	private SituacaoFuncionamento situacaoFuncionamentoDado;
	private TipoOcupacao tipoOcupacaoDado;
	private Regiao regiaoDado;
	private Pessoa pessoaDado;
	private TipoAmbiente tipoAmbienteDado;
	private Bloco tipoBlocoDado;
	private Ambiente ambienteDado;
	
	/* VARIAVEIS DO CONSULTAR */
	private List<UnidadeEscolar> unidadesEscolaresCadastrados;
	
	private List<Ambiente> ambientes;
    
    private String cpfDiretorPesquisar;
	private String codigoPesquisar;
    private String nomePesquisar;
    
    private Boolean btRemoverEnabled;
	private Boolean btCadastrarEnabled;
	private Boolean btConsultarEnabled;
	Boolean temPermissaoCadastrar = false;
	Boolean temPermissaoExcluir = false;
	Boolean temPermissaoConsultar = false;
	
	/* Combo com valores de ESTADO */
	private List<SelectItem> comboEstado;
	
	/* Combo com valores de CIDADE */
	private List<SelectItem> comboCidade;
	
	private Usuario usuarioLogado = null;
	
	/**
	 * Construtor
	 */
	public UnidadeEscolarServlet()
	{	
		if(this.unidadeEscolar == null ){			
			this.unidadeEscolar = new UnidadeEscolar();
		}
		if(this.terreno == null){			
			this.terreno = new Terreno();
		}
		if(this.diretor == null){			
			this.diretor = new Pessoa();
		}
		if(this.enderecoDado == null) { 
			this.enderecoDado = new Endereco(); 
		}
		if(this.contatoDado == null) { 
			this.contatoDado = new Contato(); 
		}
		if(this.redeEnsinoDado == null) {			
			this.redeEnsinoDado = new RedeEnsino();
		}
		if(this.situacaoFuncionamentoDado == null) {		
			this.situacaoFuncionamentoDado = new SituacaoFuncionamento();
		}
		if(this.tipoOcupacaoDado == null) {		
			this.tipoOcupacaoDado = new TipoOcupacao();
		}	
		if(this.regiaoDado == null) {			
			this.regiaoDado = new Regiao();
		}
		if(this.pessoaDado == null) {			
			this.pessoaDado = new Pessoa();
		}
		if(this.paisDado == null) {			
			this.paisDado = new Pais();
		}
		if(this.estadoDado == null) {			
			this.estadoDado = new Estado();
		}
		if(this.cidadeDado == null) {			
			this.cidadeDado = new Cidade();
		}
		if(this.tipoAmbienteDado == null) {			
			this.tipoAmbienteDado = new TipoAmbiente();
		}
		if(this.tipoBlocoDado == null) {			
			this.tipoBlocoDado = new Bloco();
		}
		if(this.ambienteDado == null) {			
			this.ambienteDado = new Ambiente();
		}
		if(this.ambientes==null){
			this.ambientes = new ArrayList<Ambiente>();
		}
		
		nomeDiretor = new String();
		cpfDiretor = new String();
		
		btCadastrarEnabled = false;
		btConsultarEnabled = false;
		btRemoverEnabled = false;
		
		comboCidade = new ArrayList<SelectItem>();
		comboEstado = new ArrayList<SelectItem>();
		
		//Busco o usuário logado
		usuarioLogado = (Usuario)  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		validarPermissoes();
		if(temPermissaoCadastrar) { setBtCadastrarEnabled(true); }
		if(temPermissaoConsultar) { setBtConsultarEnabled(true); }
	}
	
	public void validarPermissoes()
	{
		//validar permissão
		for (Permissao permissao : usuarioLogado.getPermissoes()) 
		{
			if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_EXCLUIR) 
					&& permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_UNIDADE_ESCOLAR))
			{
				temPermissaoExcluir = true;
			}
			else if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_CADASTRAR) 
					&& permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_UNIDADE_ESCOLAR))
			{
				temPermissaoCadastrar = true;
			}
			else if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_CONSULTAR) 
					&& permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_UNIDADE_ESCOLAR))
			{
				temPermissaoConsultar = true;
			}
		}
	}
	
	/**
	 * Método usado para cadastrar um novo usuário no banco de dados, este usuário será cadastrado da tela de cadastro de usuário
	 * @author João Paulo 
	 * @return NULL - Apenas para retornar a função
	 */
	public String cadastrarUnidadeEscolar()
	{
		try 
		{
			Terreno terrenoAux = null;
			UnidadeEscolarDAO unidadeEscolarDAO = new UnidadeEscolarDAO();
			ContatoDAO contatoDAO = new ContatoDAO();
			EnderecoDAO enderecoDAO = new EnderecoDAO();
			TerrenoDAO terrenoDAO = new TerrenoDAO();
			Pessoa pessoa = null;
			AmbienteDAO ambienteDAO = new AmbienteDAO();
			
			if(usuarioLogado!=null && usuarioLogado.getFkMunicipioCliente()!=null)
			{
				unidadeEscolar.setFkMunicipioCliente(usuarioLogado.getFkMunicipioCliente());
			}
			
			/* Diretor */
			if(cpfDiretor!=null)
			{
				pessoa = new PessoaDAO().obtemUnicoPessoaSimples(cpfDiretor); 
				if(pessoa!=null && pessoa.getPkPessoa()!=null) 	{ unidadeEscolar.setDiretor(pessoa); }
			}
			
			/* Rede Ensino */
			if(redeEnsinoDado!=null)
			{
				unidadeEscolar.setRedeEnsino(new RedeEnsino());
				unidadeEscolar.getRedeEnsino().setPkRedeEnsino(new Integer(redeEnsinoDado.getPkRedeEnsino()));
			}
			
			/*----Endereço----*/
			if(cidadeDado!=null && cidadeDado.getPkCidade()!=null)
			{
				if(enderecoDado!=null)
				{
					enderecoDado.setStatus(ConstantesSisEducar.STATUS_ATIVO);
					enderecoDado.setCidade(cidadeDado);
				}
			}
			
			/* Região Dados */
			if(regiaoDado!=null && regiaoDado.getPkRegiao()!=null)
			{
				unidadeEscolar.setRegiao(regiaoDado);
			}
			
			/* Situaçao Funcionamento */
			if(situacaoFuncionamentoDado!=null && situacaoFuncionamentoDado.getPkSituacaoFuncionamento()!=null)
			{
				unidadeEscolar.setSituacaoFuncionamento(situacaoFuncionamentoDado);
			}
			
			/* Tipo Ocupação */
			if(tipoOcupacaoDado!=null && tipoOcupacaoDado.getPkTipoOcupacao()!=null)
			{
				unidadeEscolar.setTipoOcupacao(tipoOcupacaoDado);
			}
			
			/* SALVA O ENDEÇO */
			if(enderecoDado!=null)
			{
				//CONTATO
				if(enderecoDado.getContato()!=null && enderecoDado.getContato().getPkContato()!=0)
				{
					contatoDAO.atualizarContato(enderecoDado.getContato());
				}
				else
				{
					if(contatoDado!=null && (contatoDado.getTelCelular()!=null || contatoDado.getTelComercial()!=null || contatoDado.getTelResidencial()!=null || contatoDado.getEmail()!=null)) 
					{ 
						enderecoDado.setContato(contatoDAO.inserirContato(contatoDado));
					}
				}
				
				//ENDEREÇO
				if(enderecoDado.getPkEndereco()!=null) { enderecoDado = enderecoDAO.atualizarEndereco(enderecoDado); }
				else                                   { enderecoDado = new EnderecoDAO().inserirEndereco(enderecoDado); }
				
				if(enderecoDado!=null && enderecoDado.getPkEndereco()!=null) { unidadeEscolar.setEndereco(enderecoDado); }
			}
			
			/* Salva o Terreno */
			if(terreno!=null)
			{
				if(terreno.getPkTerreno()!=null) { terrenoAux = terrenoDAO.atualizarTerreno(terreno); }
				else
				{
					terrenoAux = terrenoDAO.inserirTerreno(terreno);
					if(terrenoAux!=null && terrenoAux.getPkTerreno()!=null) 	{ unidadeEscolar.setTerreno(terreno); }
				}
				
			}
			
			if(unidadeEscolar!=null && unidadeEscolar.getPkUnidadeEscolar()!=null) { unidadeEscolarDAO.atualizarUnidadeEscolar(unidadeEscolar); }
			else
			{
				/* Salva a unidade escolar */
				unidadeEscolar = unidadeEscolarDAO.inserirUnidadeEscolar(unidadeEscolar);
			}
			
			/* Salva os Ambientes */
			ambienteDAO.remover(unidadeEscolar);
			if(ambientes!=null && ambientes.size()>0)
			{
				for (Ambiente ambiente : ambientes) 
				{
					ambiente.setUnidadeEscolar(unidadeEscolar);
					ambienteDAO.inserir(ambiente);
				}
			}
			
			if(unidadeEscolar!=null && unidadeEscolar.getPkUnidadeEscolar()>0)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Unidade Escolar cadastrada com sucesso", null));
				resetarUnidadeEscolar();
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Unidade escolar não registrada", null));
			}
			
			return null;
		}
		catch (Exception e) 
		{
			Logs.addFatal("Erro ao cadastrar!", "cadastrarUnidadeEscolar");
			return null;
		}
	}
	
	/**
	 * Busca o nome do diretor e seta no input o nome do mesmo
	 * @author João Paulo
	 * @return String
	 */
	public String buscarInformacoesDiretor()
	{
		try 
		{
			Pessoa pessoa = new PessoaDAO().obtemUnicoPessoaSimples(cpfDiretor); 
			if(pessoa!=null && pessoa.getPkPessoa()!=null) 	{ nomeDiretor = pessoa.getNome(); }
			return null;
		} 
		catch (Exception e) 
		{
			Logs.addError("buscarInformacoesDiretor", "");
			return null;
		}
	}
	
	/**
	 * Método usado para inicializar novamente o Usuario
	 * @author João Paulo
	 */
	public void resetarUnidadeEscolar()
	{
		try
		{
			cpfDiretor = "";
			nomeDiretor = "";
			diretor = new Pessoa();
			terreno = new Terreno();
			unidadeEscolar = new UnidadeEscolar();
			enderecoDado = new Endereco();
			contatoDado = new Contato();
			redeEnsinoDado = new RedeEnsino();
			situacaoFuncionamentoDado = new SituacaoFuncionamento();
			tipoOcupacaoDado = new TipoOcupacao();
			regiaoDado = new Regiao();
			pessoaDado = new Pessoa();
			cidadeDado = new Cidade();
			estadoDado = new Estado();
			paisDado = new Pais();
			
			ambienteDado = new Ambiente();
			tipoAmbienteDado = new TipoAmbiente();
			tipoBlocoDado = new Bloco();
			ambientes = new ArrayList<Ambiente>();
			
			unidadesEscolaresCadastrados = new ArrayList<UnidadeEscolar>();
			
			btRemoverEnabled = false;
		}
		catch (Exception e) 
		{
			Logs.addFatal("Resetar", "Falha ao resetar a unidade escolar");
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
			UnidadeEscolar unidadeEscolarSelecionada = (UnidadeEscolar) dataTable.getRowData();
			
			if(unidadeEscolarSelecionada != null && unidadeEscolarSelecionada.getPkUnidadeEscolar() != null)
			{
				unidadeEscolar = unidadeEscolarSelecionada;
				if(unidadeEscolarSelecionada.getRedeEnsino()!=null)
				{
					redeEnsinoDado = unidadeEscolarSelecionada.getRedeEnsino();
				}
				if(unidadeEscolarSelecionada.getRegiao()!=null)
				{
					regiaoDado = unidadeEscolarSelecionada.getRegiao();
				}
				if(unidadeEscolarSelecionada.getSituacaoFuncionamento()!=null)
				{
					situacaoFuncionamentoDado = unidadeEscolarSelecionada.getSituacaoFuncionamento();
				}
				if(unidadeEscolarSelecionada.getTipoOcupacao()!=null)
				{
					tipoOcupacaoDado = unidadeEscolarSelecionada.getTipoOcupacao();
				}
				if(unidadeEscolarSelecionada.getTerreno()!=null)
				{
					terreno = unidadeEscolarSelecionada.getTerreno();
				}
				if(unidadeEscolarSelecionada.getEndereco()!=null)
				{
					enderecoDado = unidadeEscolarSelecionada.getEndereco();
					if(enderecoDado.getCidade() != null) {
						if(enderecoDado.getCidade().getEstado().getPais().getPkPais() != null) {
							for(SelectItem selectItem : paramDados.getComboPais()) {
								if(selectItem.getValue().equals(enderecoDado.getCidade().getEstado().getPais().getPkPais())) {
									paisDado = new Pais();
									paisDado.setPkPais(enderecoDado.getCidade().getEstado().getPais().getPkPais());
									break;
								}
							}
						}
						if(paisDado != null && paisDado.getPkPais() != null) {
							if( comboEstado == null || comboEstado.isEmpty()) {
								comboEstado = paramDados.consultaEstado(paisDado);
							}
							for(SelectItem selectItem : comboEstado) {
								if(selectItem.getValue().equals(enderecoDado.getCidade().getEstado().getPkEstado())) {
									estadoDado = new Estado();
									estadoDado.setPkEstado(enderecoDado.getCidade().getEstado().getPkEstado());
									break;
								}
							}
						}
						if(estadoDado != null && estadoDado.getPkEstado() != null){
							if( comboCidade == null || comboCidade.isEmpty()) { 
								comboCidade = paramDados.consultaCidade(estadoDado); 
							}
							for(SelectItem selectItem : comboCidade) {
								if(selectItem.getValue().equals(enderecoDado.getCidade().getPkCidade())) {
									cidadeDado = new Cidade();
									cidadeDado.setPkCidade(enderecoDado.getCidade().getPkCidade());
									break;
								}
							}
						}
					}
					if(unidadeEscolarSelecionada.getEndereco().getContato()!=null)
					{
						contatoDado = unidadeEscolarSelecionada.getEndereco().getContato();
					}
				}
				if(unidadeEscolarSelecionada.getDiretor()!=null && unidadeEscolarSelecionada.getDiretor().getNome()!=null)
				{
					cpfDiretor = unidadeEscolarSelecionada.getDiretor().getCpf().toString();
					nomeDiretor = unidadeEscolarSelecionada.getDiretor().getNome();
				}
				
				ambientes = unidadeEscolarSelecionada.getAmbientes();
				if(ambientes!=null && ambientes.size()>0)
				{
					for (Ambiente ambiente : ambientes) 
					{
						ambiente.setTipoNome(ambiente.getTipo().getDescricao());
						ambiente.setBlocoNome(ambiente.getBloco().getDescricao());
					}
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
	 * Usado para buscar todos os usuários cadastrados pelos filtros digitados na tela pelo usuário
	 * @author João Paulo
	 */
	public void pesquisar()
	{
		try 
		{
			unidadesEscolaresCadastrados = new UnidadeEscolarDAO().buscar(codigoPesquisar, nomePesquisar, cpfDiretorPesquisar);
		} 
		catch (Exception e) 
		{
			Logs.addError("pesquisar", "");
		}
	}
	
	public void adicionarAmbiente()
	{
		try 
		{
			if(ambienteDado!=null)
			{
				if(ambienteDado.getCodigo()!=null && ambienteDado.getNome()!=null && ambienteDado.getCapacidade()!=null && tipoAmbienteDado!=null && tipoBlocoDado!=null)
				{
					ambienteDado.setTipo(tipoAmbienteDado);
					ambienteDado.setBloco(tipoBlocoDado);
					
					for (SelectItem item : paramDados.getComboTiposAmbiente())
					{
						if(item.getValue().equals(tipoAmbienteDado.getPkTipoAmbiente())) 
						{ 
							ambienteDado.setTipoNome(item.getLabel());
							break;
						}
					}
					
					for (SelectItem item : paramDados.getComboBlocosAmbiente()) 
					{
						if(item.getValue().equals(tipoBlocoDado.getPkBloco())) 
						{ 
							ambienteDado.setBlocoNome(item.getLabel());
							break;
						}
					}
					
					ambientes.add(ambienteDado);
					
					ambienteDado = new Ambiente();
					tipoAmbienteDado = new TipoAmbiente();
					tipoBlocoDado = new Bloco();
				}
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("adicionarAmbiente", "adicionarAmbiente");
		}
	}
	
	public void removerAmbiente()
	{
		try 
		{
			List<Ambiente> listAmbientes = new ArrayList<Ambiente>();
			Ambiente ambienteSelecionado = null;
			
			if(dataTableAmbiente!=null && dataTableAmbiente.getRowData()!=null) { ambienteSelecionado = (Ambiente) dataTableAmbiente.getRowData(); }
			
			if(ambienteSelecionado != null && ambienteSelecionado.getPkAmbiente() != null)
			{
				for (Ambiente ambiente : ambientes) 
				{
					if(!ambiente.getPkAmbiente().equals(ambienteSelecionado.getPkAmbiente()))
					{
						listAmbientes.add(ambiente);
					}
				}
				
				ambientes = listAmbientes;
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("removerAmbiente", "removerAmbiente");
		}
	}
	
	public void removerTodosAmbientes()
	{
		try 
		{
			ambientes = new ArrayList<Ambiente>();
		} 
		catch (Exception e) 
		{
			Logs.addError("removerTodosAmbientes", "removerTodosAmbientes");
		}
	}
	
	public void removerGeral() throws SQLException
	{
		Boolean resultado = false;
		if(unidadeEscolar!=null && unidadeEscolar.getPkUnidadeEscolar()!=null)
		{
			/*AMBIENTES*/
			new AmbienteDAO().remover(unidadeEscolar);
			
			/*UNIDADE ESCOLAR*/
			resultado = new UnidadeEscolarDAO().remover(unidadeEscolar);
			if(resultado)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A Unidade Escolar foi removida com sucesso", null));
				resetarUnidadeEscolar();
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A Unidade Escolar não foi removida", null));
			}
		}
	}

	private HtmlDataTable dataTable;
	private HtmlDataTable dataTableAmbiente;
	   
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
    
	/* Getters and setters */
	public UnidadeEscolar getUnidadeEscolar() {
		return unidadeEscolar;
	}

	public void setUnidadeEscolar(UnidadeEscolar unidadeEscolar) {
		this.unidadeEscolar = unidadeEscolar;
	}
	
	public Terreno getTerreno() {
		return terreno;
	}

	public void setTerreno(Terreno terreno) {
		this.terreno = terreno;
	}

	public Pessoa getDiretor() {
		return diretor;
	}

	public void setDiretor(Pessoa diretor) {
		this.diretor = diretor;
	}

	public String getNomeDiretor() {
		return nomeDiretor;
	}

	public void setNomeDiretor(String nomeDiretor) {
		this.nomeDiretor = nomeDiretor;
	}

	public String getCpfDiretor() {
		return cpfDiretor;
	}

	public void setCpfDiretor(String cpfDiretor) {
		this.cpfDiretor = cpfDiretor;
	}

	public List<SelectItem> getComboEstado() {
		comboEstado.clear();
		if(paisDado != null && paisDado.getPkPais() != null) { 
			comboEstado = paramDados.consultaEstado(paisDado); 
		}
		return comboEstado;
	}

	public void setComboEstado(List<SelectItem> comboEstado) {
		this.comboEstado = comboEstado;
	}

	public List<SelectItem> getComboCidade() {
		comboCidade.clear();
		if(estadoDado != null && estadoDado.getPkEstado()!= null) { 
			comboCidade = paramDados.consultaCidade(estadoDado); 
		}
		return comboCidade;
	}

	public void setComboCidade(List<SelectItem> comboCidade) {
		this.comboCidade = comboCidade;
	}

	public Pais getPaisDado() {
		return paisDado;
	}

	public void setPaisDado(Pais paisDado) {
		this.paisDado = paisDado;
	}

	public Estado getEstadoDado() {
		return estadoDado;
	}

	public void setEstadoDado(Estado estadoDado) {
		this.estadoDado = estadoDado;
	}

	public Cidade getCidadeDado() {
		return cidadeDado;
	}

	public void setCidadeDado(Cidade cidadeDado) {
		this.cidadeDado = cidadeDado;
	}

	public Endereco getEnderecoDado() {
		return enderecoDado;
	}

	public void setEnderecoDado(Endereco enderecoDado) {
		this.enderecoDado = enderecoDado;
	}

	public RedeEnsino getRedeEnsinoDado() {
		return redeEnsinoDado;
	}

	public void setRedeEnsinoDado(RedeEnsino redeEnsinoDado) {
		this.redeEnsinoDado = redeEnsinoDado;
	}

	public SituacaoFuncionamento getSituacaoFuncionamentoDado() {
		return situacaoFuncionamentoDado;
	}

	public void setSituacaoFuncionamentoDado(SituacaoFuncionamento situacaoFuncionamentoDado) {
		this.situacaoFuncionamentoDado = situacaoFuncionamentoDado;
	}

	public TipoOcupacao getTipoOcupacaoDado() {
		return tipoOcupacaoDado;
	}

	public void setTipoOcupacaoDado(TipoOcupacao tipoOcupacaoDado) {
		this.tipoOcupacaoDado = tipoOcupacaoDado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Regiao getRegiaoDado() {
		return regiaoDado;
	}

	public void setRegiaoDado(Regiao regiaoDado) {
		this.regiaoDado = regiaoDado;
	}

	public Pessoa getPessoaDado() {
		return pessoaDado;
	}

	public void setPessoaDado(Pessoa pessoaDado) {
		this.pessoaDado = pessoaDado;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<UnidadeEscolar> getUnidadesEscolaresCadastrados() {
		return unidadesEscolaresCadastrados;
	}

	public void setUnidadesEscolaresCadastrados(List<UnidadeEscolar> unidadesEscolaresCadastrados) {
		this.unidadesEscolaresCadastrados = unidadesEscolaresCadastrados;
	}

	public String getCpfDiretorPesquisar() {
		return cpfDiretorPesquisar;
	}

	public void setCpfDiretorPesquisar(String cpfDiretorPesquisar) {
		this.cpfDiretorPesquisar = cpfDiretorPesquisar;
	}

	public String getCodigoPesquisar() {
		return codigoPesquisar;
	}

	public void setCodigoPesquisar(String codigoPesquisar) {
		this.codigoPesquisar = codigoPesquisar;
	}

	public String getNomePesquisar() {
		return nomePesquisar;
	}

	public void setNomePesquisar(String nomePesquisar) {
		this.nomePesquisar = nomePesquisar;
	}

	public Contato getContatoDado() {
		return contatoDado;
	}

	public void setContatoDado(Contato contatoDado) {
		this.contatoDado = contatoDado;
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

	public ParametrosServlet getParamDados() {
		return paramDados;
	}

	public void setParamDados(ParametrosServlet paramDados) {
		this.paramDados = paramDados;
	}

	public TipoAmbiente getTipoAmbienteDado() {
		return tipoAmbienteDado;
	}

	public void setTipoAmbienteDado(TipoAmbiente tipoAmbienteDado) {
		this.tipoAmbienteDado = tipoAmbienteDado;
	}

	public Bloco getTipoBlocoDado() {
		return tipoBlocoDado;
	}

	public void setTipoBlocoDado(Bloco tipoBlocoDado) {
		this.tipoBlocoDado = tipoBlocoDado;
	}

	public Ambiente getAmbienteDado() {
		return ambienteDado;
	}

	public void setAmbienteDado(Ambiente ambienteDado) {
		this.ambienteDado = ambienteDado;
	}

	public HtmlDataTable getDataTableAmbiente() {
		return dataTableAmbiente;
	}

	public void setDataTableAmbiente(HtmlDataTable dataTableAmbiente) {
		this.dataTableAmbiente = dataTableAmbiente;
	}

	public List<Ambiente> getAmbientes() {
		return ambientes;
	}

	public void setAmbientes(List<Ambiente> ambientes) {
		this.ambientes = ambientes;
	}
}

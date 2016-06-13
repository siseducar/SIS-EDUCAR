package modulos.secretaria.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import modulos.secretaria.dao.EnderecoDAO;
import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.dao.TerrenoDAO;
import modulos.secretaria.dao.UnidadeEscolarDAO;
import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Endereco;
import modulos.secretaria.om.Estado;
import modulos.secretaria.om.Pais;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.RedeEnsino;
import modulos.secretaria.om.Regiao;
import modulos.secretaria.om.SituacaoFuncionamento;
import modulos.secretaria.om.Terreno;
import modulos.secretaria.om.TipoOcupacao;
import modulos.secretaria.om.UnidadeEscolar;
import modulos.secretaria.om.Usuario;
import modulos.sisEducar.servlet.SisEducarServlet;
import modulos.sisEducar.utils.ConstantesSisEducar;
import modulos.sisEducar.utils.Logs;

@ManagedBean(name="unidadeEscolarServlet")
@ViewScoped
public class UnidadeEscolarServlet implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private UnidadeEscolar unidadeEscolar;
	private Terreno terreno;
	private Pessoa diretor;
	private String nomeDiretor;
	private String cpfDiretor;
	private Pais paisDado;
	private Estado estadoDado;
	private Cidade cidadeDado;
	private Endereco enderecoDado;
	private RedeEnsino redeEnsinoDado;
	private SituacaoFuncionamento situacaoFuncionamentoDado;
	private TipoOcupacao tipoOcupacaoDado;
	private Regiao regiaoDado;
	private Pessoa pessoaDado;
	private ParametrosServlet parametrosServlet;
	
	/* VARIAVEIS DO CONSULTAR */
	private List<UnidadeEscolar> unidadesEscolaresCadastrados;
    private Usuario usuarioCadastradoSelecionado;
    
    private String cpfDiretorPesquisar;
	private String codigoPesquisar;
    private String nomePesquisar;
	
	
	/* Combo com valores de ZONA RESIDENCIAL */
	private List<SelectItem> comboZonaResidencial;
	
	/* Combo com valores de PAÍS */
	private List<SelectItem> comboPais;
	
	/* Combo com valores de ESTADO */
	private List<SelectItem> comboEstado;
	
	/* Combo com valores de CIDADE */
	private List<SelectItem> comboCidade;
	
	/* Combo com valores de REDE DE ENSINO */
	private List<SelectItem> comboRedeEnsino;
	
	/* Combo com valores de TIPO OCUPAÇÃO */
	private List<SelectItem> comboTipoOcupacao;
	
	/* Combo com valores de SITUAÇÃO FUNCIONAMENTO */
	private List<SelectItem> comboSituacaoFuncionamento;
	
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
		if(this.enderecoDado == null){
			this.enderecoDado = new Endereco();
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
		if(this.parametrosServlet == null) {			
			this.parametrosServlet = new ParametrosServlet();
		}
		
		nomeDiretor = new String();
		cpfDiretor = new String();
		
		comboZonaResidencial = new ArrayList<SelectItem>();
		comboPais = new ArrayList<SelectItem>();
		comboEstado = new ArrayList<SelectItem>();
		comboCidade = new ArrayList<SelectItem>();
		comboRedeEnsino = new ArrayList<SelectItem>();
		comboTipoOcupacao = new ArrayList<SelectItem>();
		comboSituacaoFuncionamento = new ArrayList<SelectItem>();
		
		//Busco o usuário logado
		usuarioLogado = (Usuario) new SisEducarServlet().getSessionObject(ConstantesSisEducar.USUARIO_LOGADO);
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
			Endereco enderecoAux = null;
			UnidadeEscolarDAO unidadeEscolarDAO = new UnidadeEscolarDAO();
			Pessoa pessoa = null;
			
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
			
			/* Salva o endeço */
			if(enderecoDado!=null)
			{
				enderecoAux = new EnderecoDAO().inserirEndereco(enderecoDado);
				if(enderecoAux!=null && enderecoAux.getPkEndereco()!=null) { unidadeEscolar.setEndereco(enderecoAux); }
			}
			
			/* Salva o Terreno */
			if(terreno!=null)
			{
				terrenoAux = new TerrenoDAO().inserirTerreno(terreno);
				if(terrenoAux!=null && terrenoAux.getPkTerreno()!=null) 	{ unidadeEscolar.setTerreno(terreno); }
			}
			
			/* Salva a unidade escolar */
			unidadeEscolar = unidadeEscolarDAO.inserirUnidadeEscolar(unidadeEscolar);
			
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
			redeEnsinoDado = new RedeEnsino();
			situacaoFuncionamentoDado = new SituacaoFuncionamento();
			tipoOcupacaoDado = new TipoOcupacao();
			regiaoDado = new Regiao();
			pessoaDado = new Pessoa();
			paisDado = new Pais();
			
			comboZonaResidencial = new ArrayList<SelectItem>();
			comboPais = new ArrayList<SelectItem>();
			comboEstado = new ArrayList<SelectItem>();
			comboCidade = new ArrayList<SelectItem>();
			comboRedeEnsino = new ArrayList<SelectItem>();
			comboTipoOcupacao = new ArrayList<SelectItem>();
			comboSituacaoFuncionamento = new ArrayList<SelectItem>();
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
				if(unidadeEscolarSelecionada.getDiretor()!=null && unidadeEscolarSelecionada.getDiretor().getNome()!=null)
				{
					nomeDiretor = unidadeEscolarSelecionada.getDiretor().getNome();
				}
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
	
	public List<SelectItem> getComboZonaResidencial() {
		comboZonaResidencial.addAll(parametrosServlet.consultaRegiao());
		return comboZonaResidencial;
	}

	public void setComboZonaResidencial(List<SelectItem> comboZonaResidencial) {
		this.comboZonaResidencial = comboZonaResidencial;
	}

	public List<SelectItem> getComboPais() {
		comboPais.addAll(parametrosServlet.consultaPais());
		return comboPais;
	}

	public void setComboPais(List<SelectItem> comboPais) {
		this.comboPais = comboPais;
	}

	public List<SelectItem> getComboEstado() {
		comboEstado.clear();
		if(paisDado != null && paisDado.getPkPais() != null) { 
			comboEstado = parametrosServlet.consultaEstado(paisDado); 
		}
		return comboEstado;
	}

	public void setComboEstado(List<SelectItem> comboEstado) {
		this.comboEstado = comboEstado;
	}

	public List<SelectItem> getComboCidade() {
		comboCidade.clear();
		if(estadoDado != null && estadoDado.getPkEstado()!= null) { 
			comboCidade = parametrosServlet.consultaCidade(estadoDado); 
		}
		return comboCidade;
	}

	public void setComboCidade(List<SelectItem> comboCidade) {
		this.comboCidade = comboCidade;
	}

	public List<SelectItem> getComboRedeEnsino() throws SQLException {
		comboRedeEnsino.addAll(parametrosServlet.consultaRedeEnsino());
		return comboRedeEnsino;
	}

	public void setComboRedeEnsino(List<SelectItem> comboRedeEnsino) {
		this.comboRedeEnsino = comboRedeEnsino;
	}

	public List<SelectItem> getComboTipoOcupacao() {
		comboTipoOcupacao.addAll(parametrosServlet.consultaTipoOcupacao());
		return comboTipoOcupacao;
	}

	public void setComboTipoOcupacao(List<SelectItem> comboTipoOcupacao) {
		this.comboTipoOcupacao = comboTipoOcupacao;
	}

	public List<SelectItem> getComboSituacaoFuncionamento() {
		comboSituacaoFuncionamento.addAll(parametrosServlet.consultaSituacaoFuncionamento());
		return comboSituacaoFuncionamento;
	}

	public void setComboSituacaoFuncionamento(List<SelectItem> comboSituacaoFuncionamento) {
		this.comboSituacaoFuncionamento = comboSituacaoFuncionamento;
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
}

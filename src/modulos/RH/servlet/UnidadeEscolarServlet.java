package modulos.RH.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import modulos.RH.dao.EnderecoDAO;
import modulos.RH.dao.PessoaDAO;
import modulos.RH.dao.TerrenoDAO;
import modulos.RH.dao.UnidadeEscolarDAO;
import modulos.RH.om.Cidade;
import modulos.RH.om.Endereco;
import modulos.RH.om.Estado;
import modulos.RH.om.Pais;
import modulos.RH.om.Pessoa;
import modulos.RH.om.RedeEnsino;
import modulos.RH.om.Regiao;
import modulos.RH.om.SituacaoFuncionamento;
import modulos.RH.om.Terreno;
import modulos.RH.om.TipoOcupacao;
import modulos.RH.om.UnidadeEscolar;
import modulos.sisEducar.sisEducarServlet.SisEducarServlet;
import modulos.sisEducar.utils.ConstantesSisEducar;
import modulos.sisEducar.utils.Logs;

@ManagedBean(name="unidadeEscolarServlet")
@SessionScoped
public class UnidadeEscolarServlet extends SisEducarServlet
{
	private static final long serialVersionUID = 1L;
	UnidadeEscolar unidadeEscolar = null;
	Terreno terreno = null;
	Pessoa diretor = null;
	private String nomeDiretor = "";
	private String cpfDiretor = "";
	
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
	
	private Pais paisDado;
	private Estado estadoDado;
	private Cidade cidadeDado;
	private Endereco enderecoDado;
	private RedeEnsino redeEnsinoDado;
	private SituacaoFuncionamento situacaoFuncionamentoDado;
	private TipoOcupacao tipoOcupacaoDado;
	private Regiao regiaoDado;
	private Pessoa pessoaDado;
	
	/**
	 * Construtor
	 */
	public UnidadeEscolarServlet()
	{
		unidadeEscolar = new UnidadeEscolar();
		terreno = new Terreno();
		diretor = new Pessoa();
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
		}
		catch (Exception e) 
		{
			Logs.addFatal("Resetar", "Falha ao resetar a unidade escolar");
		}
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
		ParametrosServlet parametrosServlet = new ParametrosServlet();
		return parametrosServlet.consultaRegiao();
	}

	public void setComboZonaResidencial(List<SelectItem> comboZonaResidencial) {
		this.comboZonaResidencial = comboZonaResidencial;
	}

	public List<SelectItem> getComboPais() {
		ParametrosServlet parametrosServlet = new ParametrosServlet();
		return parametrosServlet.consultaPais();
	}

	public void setComboPais(List<SelectItem> comboPais) {
		this.comboPais = comboPais;
	}

	public List<SelectItem> getComboEstado() {
		ParametrosServlet parametrosServlet = new ParametrosServlet();
		comboEstado = parametrosServlet.consultaEstado(paisDado);
		return comboEstado;
	}

	public void setComboEstado(List<SelectItem> comboEstado) {
		this.comboEstado = comboEstado;
	}

	public List<SelectItem> getComboCidade() {
		ParametrosServlet parametrosServlet = new ParametrosServlet();
		return parametrosServlet.consultaCidade(estadoDado);
	}

	public void setComboCidade(List<SelectItem> comboCidade) {
		this.comboCidade = comboCidade;
	}

	public List<SelectItem> getComboRedeEnsino() throws SQLException {
		ParametrosServlet parametrosServlet = new ParametrosServlet();
		return parametrosServlet.consultaRedeEnsino();
	}

	public void setComboRedeEnsino(List<SelectItem> comboRedeEnsino) {
		this.comboRedeEnsino = comboRedeEnsino;
	}

	public List<SelectItem> getComboTipoOcupacao() {
		ParametrosServlet parametrosServlet = new ParametrosServlet();
		return parametrosServlet.consultaTipoOcupacao();
	}

	public void setComboTipoOcupacao(List<SelectItem> comboTipoOcupacao) {
		this.comboTipoOcupacao = comboTipoOcupacao;
	}

	public List<SelectItem> getComboSituacaoFuncionamento() {
		ParametrosServlet parametrosServlet = new ParametrosServlet();
		return parametrosServlet.consultaSituacaoFuncionamento();
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
}

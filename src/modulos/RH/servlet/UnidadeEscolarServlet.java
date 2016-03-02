package modulos.RH.servlet;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import modulos.RH.dao.UnidadeEscolarDAO;
import modulos.RH.om.Endereco;
import modulos.RH.om.Pessoa;
import modulos.RH.om.RedeEnsino;
import modulos.RH.om.Regiao;
import modulos.RH.om.SituacaoFuncionamento;
import modulos.RH.om.Terreno;
import modulos.RH.om.TipoOcupacao;
import modulos.RH.om.UnidadeEscolar;
import modulos.sisEducar.sisEducarServlet.SisEducarServlet;
import modulos.sisEducar.utils.Logs;

@ManagedBean(name="unidadeEscolarServlet")
@SessionScoped
public class UnidadeEscolarServlet extends SisEducarServlet
{
	private static final long serialVersionUID = 1L;
	UnidadeEscolar unidadeEscolar = null;
	Terreno terreno = null;
	Pessoa diretor = null;
	Regiao regiao = null;
	TipoOcupacao tipoOcupacao = null;
	SituacaoFuncionamento situacaoFuncionamento = null;
	RedeEnsino redeEnsino = null;
	Endereco endereco = null;
	
	/**
	 * Construtor
	 */
	public UnidadeEscolarServlet()
	{
		unidadeEscolar = new UnidadeEscolar();
		terreno = new Terreno();
		diretor = new Pessoa();
		regiao = new Regiao();
		tipoOcupacao = new TipoOcupacao();
		situacaoFuncionamento = new SituacaoFuncionamento();
		redeEnsino = new RedeEnsino();
		endereco = new Endereco();
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
			UnidadeEscolarDAO unidadeEscolarDAO = new UnidadeEscolarDAO();
			//unidadeEscolar = unidadeEscolarDAO.inserirUnidadeEscolar(unidadeEscolar);
			
			System.out.println(ParametrosServlet.enderecoDados);
			System.out.println(ParametrosServlet.cidadeDados);
			
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
	 * Método usado para inicializar novamente o Usuario
	 * @author João Paulo
	 */
	public void resetarUnidadeEscolar()
	{
		try
		{
			unidadeEscolar = new UnidadeEscolar();
		}
		catch (Exception e) 
		{
			Logs.addFatal("Resetar", "Falha ao resetar a unidade escolar");
		}
	}

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

	public Regiao getRegiao() {
		return regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

	public TipoOcupacao getTipoOcupacao() {
		return tipoOcupacao;
	}

	public void setTipoOcupacao(TipoOcupacao tipoOcupacao) {
		this.tipoOcupacao = tipoOcupacao;
	}

	public SituacaoFuncionamento getSituacaoFuncionamento() {
		return situacaoFuncionamento;
	}

	public void setSituacaoFuncionamento(SituacaoFuncionamento situacaoFuncionamento) {
		this.situacaoFuncionamento = situacaoFuncionamento;
	}

	public RedeEnsino getRedeEnsino() {
		return redeEnsino;
	}

	public void setRedeEnsino(RedeEnsino redeEnsino) {
		this.redeEnsino = redeEnsino;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}

package modulos.RH.servlet;

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
import modulos.RH.om.Endereco;
import modulos.RH.om.Pessoa;
import modulos.RH.om.RedeEnsino;
import modulos.RH.om.Regiao;
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
	
	/* Combo com valores de GRAU DE PARENTESCO */
	private List<SelectItem> comboGrauParentesco;
	
	/* Combo com valores de NACIONALIDADE */
	private List<SelectItem> comboNacionalidade;
	
	/* Combo com valores de RAÇA */
	private List<SelectItem> comboRaca;
	
	/* Combo com valores de ESTADO CIVIL */
	private List<SelectItem> comboEstadoCivil;
	
	/* Combo com valores de GRAU DE PARENTESCO */
	private List<SelectItem> comboGrauInstrucao;
	
	/* Combo com valores de SITUAÇÂO ECÔNIMCA */
	private List<SelectItem> comboSituacaoEconomica;
	
	/* Combo com valores de RELIGIÃO */
	private List<SelectItem> comboReligiao;
	
	/* Combo com valores de ZONA RESIDENCIAL */
	private List<SelectItem> comboZonaResidencial;
	
	/* Combo com valores de TIPO DE DEFICENCIA*/
	private List<SelectItem> comboTipoDeficiencia;
	
	/* Combo com valores de PAÍS */
	private List<SelectItem> comboPais;
	
	/* Combo com valores de ESTADO */
	private List<SelectItem> comboEstado;
	
	/* Combo com valores de CIDADE */
	private List<SelectItem> comboCidade;
	
	/* Combo com valores de CARGO */
	private List<SelectItem> comboCargo;
	
	/* Combo com valores de REDE DE ENSINO */
	private List<SelectItem> comboRedeEnsino;
	
	/* Combo com valores de UNIDADE ESCOLAR */
	private List<SelectItem> comboUnidadeEscolar;
	
	
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
			if(ParametrosServlet.alunoDados!=null && ParametrosServlet.alunoDados.getRedeEnsino()!=null && ParametrosServlet.alunoDados.getRedeEnsino().length()>0)
			{
				unidadeEscolar.setRedeEnsino(new RedeEnsino());
				unidadeEscolar.getRedeEnsino().setPkRedeEnsino(new Integer(ParametrosServlet.alunoDados.getRedeEnsino()));
			}
			
			/*----Endereço----*/
			if(ParametrosServlet.cidadeDados!=null && ParametrosServlet.cidadeDados.getPkCidade()!=null)
			{
				if(ParametrosServlet.enderecoDados!=null)
				{
					ParametrosServlet.enderecoDados.setStatus(ConstantesSisEducar.STATUS_ATIVO);
					ParametrosServlet.enderecoDados.setCidade(ParametrosServlet.cidadeDados);
				}
			}
			
			/* Região Dados */
			if(ParametrosServlet.regiaoDados!=null && ParametrosServlet.regiaoDados.getPkRegiao()!=null)
			{
				unidadeEscolar.setRegiao(ParametrosServlet.regiaoDados);
			}
			
			/* Situaçao Funcionamento */
			if(ParametrosServlet.situacaoFuncionamentoDados!=null && ParametrosServlet.situacaoFuncionamentoDados.getPkSituacaoFuncionamento()!=null)
			{
				unidadeEscolar.setSituacaoFuncionamento(ParametrosServlet.situacaoFuncionamentoDados);
			}
			
			/* Tipo Ocupação */
			if(ParametrosServlet.tipoOcupacaoDados!=null && ParametrosServlet.tipoOcupacaoDados.getPkTipoOcupacao()!=null)
			{
				unidadeEscolar.setTipoOcupacao(ParametrosServlet.tipoOcupacaoDados);
			}
			
			/* Salva o endeço */
			if(ParametrosServlet.enderecoDados!=null)
			{
				enderecoAux = new EnderecoDAO().inserirEndereco(ParametrosServlet.enderecoDados);
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
}

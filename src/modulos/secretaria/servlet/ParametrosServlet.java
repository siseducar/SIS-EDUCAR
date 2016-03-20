package modulos.secretaria.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import modulos.secretaria.dao.CargoDAO;
import modulos.secretaria.dao.CidadeDAO;
import modulos.secretaria.dao.EstadoCivilDAO;
import modulos.secretaria.dao.EstadoDAO;
import modulos.secretaria.dao.GrauInstrucaoDAO;
import modulos.secretaria.dao.NacionalidadeDAO;
import modulos.secretaria.dao.PaisDAO;
import modulos.secretaria.dao.RacaDAO;
import modulos.secretaria.dao.RedeEnsinoDAO;
import modulos.secretaria.dao.RegiaoDAO;
import modulos.secretaria.dao.ReligiaoDAO;
import modulos.secretaria.dao.SituacaoEconomicaDAO;
import modulos.secretaria.dao.SituacaoFuncionamentoDAO;
import modulos.secretaria.dao.TipoDeficienciaDAO;
import modulos.secretaria.dao.TipoLogradouroDAO;
import modulos.secretaria.dao.TipoOcupacaoDAO;
import modulos.secretaria.dao.UnidadeEscolarDAO;
import modulos.secretaria.om.Cargo;
import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Estado;
import modulos.secretaria.om.EstadoCivil;
import modulos.secretaria.om.GrauInstrucao;
import modulos.secretaria.om.Nacionalidade;
import modulos.secretaria.om.Pais;
import modulos.secretaria.om.Raca;
import modulos.secretaria.om.RedeEnsino;
import modulos.secretaria.om.Regiao;
import modulos.secretaria.om.Religiao;
import modulos.secretaria.om.SituacaoEconomica;
import modulos.secretaria.om.SituacaoFuncionamento;
import modulos.secretaria.om.TipoDeficiencia;
import modulos.secretaria.om.TipoLogradouro;
import modulos.secretaria.om.TipoOcupacao;
import modulos.secretaria.om.UnidadeEscolar;

public class ParametrosServlet implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Metodo para carregar as Naturalidades
	 * */
	public List<SelectItem> consultaNacionalidade() {
		try {
			NacionalidadeDAO nacionalidadeDAO = new NacionalidadeDAO();
			List<SelectItem> comboNacionalidade = new ArrayList<SelectItem>();
			List<Nacionalidade> paramNacionalidade = nacionalidadeDAO.consultaNacionalidade();
			
			for (Nacionalidade param : paramNacionalidade){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkNacionalidade());
			   s.setLabel(param.getDescricao());
			   comboNacionalidade.add(s);
			}
			return comboNacionalidade;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de NACIONALIDADE, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar as Racas
	 * */
	public List<SelectItem> consultaRaca() {
		try {
			RacaDAO racaDAO = new RacaDAO();
			List<SelectItem> comboRaca = new ArrayList<SelectItem>();
			List<Raca> paramRaca = racaDAO.consultaRaca();
			
			for (Raca param : paramRaca){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkRaca());
			   s.setLabel(param.getDescricao());
			   comboRaca.add(s);
			}
			return comboRaca;
		} catch (SQLException e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de RAÇA, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar as situações de funcionamento
	 * */
	public List<SelectItem> consultaSituacaoFuncionamento() {
		try {
			SituacaoFuncionamentoDAO situFuncionamentoDAO = new SituacaoFuncionamentoDAO();
			List<SelectItem> comboSituFuncionamento = new ArrayList<SelectItem>();
			List<SituacaoFuncionamento> paramSituFunc = situFuncionamentoDAO.consultaSituacaoFuncionamento();
			
			for (SituacaoFuncionamento param : paramSituFunc){
				SelectItem  s = new SelectItem();
				s.setValue(param.getPkSituacaoFuncionamento());
				s.setLabel(param.getDescricao());
				comboSituFuncionamento.add(s);
			}
			return comboSituFuncionamento;
		} catch (SQLException e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de SITUAÇÃO FUNCIONAMENTO, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar os tipos ocupação
	 * */
	public List<SelectItem> consultaTipoOcupacao() {
		try {
			TipoOcupacaoDAO tipoOcupacaoDAO = new TipoOcupacaoDAO();
			List<SelectItem> comboTipoOcupacao = new ArrayList<SelectItem>();
			List<TipoOcupacao> paramTipoOcupacao = tipoOcupacaoDAO.consultaTipoOcupacao();
			
			for (TipoOcupacao param : paramTipoOcupacao){
				SelectItem  s = new SelectItem();
				s.setValue(param.getPkTipoOcupacao());
				s.setLabel(param.getDescricao());
				comboTipoOcupacao.add(s);
			}
			return comboTipoOcupacao;
		} catch (SQLException e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de TIPO OCUPAÇÃO, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar os Estados Civis
	 * */
	public List<SelectItem> consultaEstaCivil() {
		try {
			EstadoCivilDAO estaCivilDAO = new EstadoCivilDAO();
			List<SelectItem> comboEstaCivil = new ArrayList<SelectItem>();
			List<EstadoCivil> paramEstaCivil = estaCivilDAO.consultaEstaCivil();
			
			for (EstadoCivil param : paramEstaCivil){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkEstadoCivil());
			   s.setLabel(param.getDescricao());
			   comboEstaCivil.add(s);
			}
			return comboEstaCivil;
		}catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de ESTADOS CIVIS, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar os Graus de Instrucoes
	 * */
	public List<SelectItem> consultaGrauInstru() {
		try {
			GrauInstrucaoDAO grauInstruDAO = new GrauInstrucaoDAO();
			List<SelectItem> comboGrauInstru = new ArrayList<SelectItem>();
			List<GrauInstrucao> paramGrauInstru = grauInstruDAO.consultaGrauInstru();
			
			for (GrauInstrucao param : paramGrauInstru){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkGrauInstrucao());
			   s.setLabel(param.getDescricao());
			   comboGrauInstru.add(s);
			}
			return comboGrauInstru;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de GRAU DE INSTRUÇÃO, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar as situacoes economicas
	 * */
	public List<SelectItem> consultaSituEconomica() {
		try {
			SituacaoEconomicaDAO situEconomicaDAO = new SituacaoEconomicaDAO();
			List<SelectItem> comboSituEconomica = new ArrayList<SelectItem>();
			List<SituacaoEconomica> paramSituEconomica = situEconomicaDAO.consultaSituEconomica();
			
			for (SituacaoEconomica param : paramSituEconomica){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkSituacaoEconomica());
			   s.setLabel(param.getDescricao());
			   comboSituEconomica.add(s);
			}
			return comboSituEconomica;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de SITUAÇÕES ECONÔMICAS, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar as Religioes
	 * */
	public List<SelectItem> consultaReligiao() {
		try {
			ReligiaoDAO religiaoDAO = new ReligiaoDAO();
			List<SelectItem> comboReligiao = new ArrayList<SelectItem>();
			List<Religiao> paramReligiao = religiaoDAO.consultaReligiao();
			
			for (Religiao param : paramReligiao){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkReligiao());
			   s.setLabel(param.getDescricao());
			   comboReligiao.add(s);
			}
			return comboReligiao;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de RELIGIÕES, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar os Paises
	 * */
	public List<SelectItem> consultaPais() {
		try {
			PaisDAO paisDAO = new PaisDAO();
			List<SelectItem> comboPais = new ArrayList<>();
			List<Pais> paramPais = paisDAO.consultaPais();
			
			for (Pais param : paramPais){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkPais());
			   s.setLabel(param.getNome());
			   comboPais.add(s);
			}
			return comboPais;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de PAÍS, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar os Estados
	 * */
	public List<SelectItem> consultaEstado(Pais paisDados) {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			List<SelectItem> comboEstado = new ArrayList<>();
			List<Estado> paramEstado = estadoDAO.consultaEstado(paisDados.getPkPais());
			
			for (Estado param : paramEstado){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkEstado());
			   s.setLabel(param.getNome());
			   comboEstado.add(s);
			}
			return comboEstado;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de ESTADO, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar as Cidades
	 * */
	public List<SelectItem> consultaCidade(Estado estadoDados) {
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			List<SelectItem> comboCidade = new ArrayList<>();
			List<Cidade> paramCidade = cidadeDAO.consultaCidade(estadoDados.getPkEstado());
			
			for (Cidade param : paramCidade){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkCidade());
			   s.setLabel(param.getNome());
			   comboCidade.add(s);
			}
			return comboCidade;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de CIDADE, contate o administrador do sistema!", null));
			return null;
		}
	}

	/*
	 * Metodo para carregar as Zonas Residencias
	 * */
	public List<SelectItem> consultaRegiao() {
		try {
			RegiaoDAO regiaoDAO = new RegiaoDAO();
			List<SelectItem> comboRegiao = new ArrayList<SelectItem>();
			List<Regiao> paramRegiao = regiaoDAO.consultaRegiao();
			
			for (Regiao param : paramRegiao){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkRegiao());
			   s.setLabel(param.getDescricao());
			   comboRegiao.add(s);
			}
			return comboRegiao;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de REGIÃO, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar os tipos de Deficiencia
	 * */
	public List<SelectItem> consultaTipoDeficiencia() {
		try {
			TipoDeficienciaDAO tipoDeficienciaDAO = new TipoDeficienciaDAO();
			List<SelectItem> comboRegiao = new ArrayList<SelectItem>();
			List<TipoDeficiencia> paramTipoDeficiencia = tipoDeficienciaDAO.consultaTipoDeficiencia();
			
			for (TipoDeficiencia param : paramTipoDeficiencia){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkTipoDeficiencia());
			   s.setLabel(param.getDescricao());
			   comboRegiao.add(s);
			}
			return comboRegiao;
		}catch(SQLException e ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de TIPO DE DEFICIENCIA, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar as rede de ensino
	 * */
	public List<SelectItem> consultaRedeEnsino() {
		try {
			RedeEnsinoDAO redeEnsinoDAO = new RedeEnsinoDAO();
			List<SelectItem> comboRedeEnsino = new ArrayList<>();
			List<RedeEnsino> paramRedeEnsino = redeEnsinoDAO.consultaRedeEnsino();
			
			for (RedeEnsino param : paramRedeEnsino){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkRedeEnsino());
			   s.setLabel(param.getNome());
			   comboRedeEnsino.add(s);
			}
			return comboRedeEnsino;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de REDE DE ENSINO, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar as Unidades Escolares
	 * */
	public List<SelectItem> consultaUnidadeEscolar(RedeEnsino redeEnsinoDados) {
		try {
			UnidadeEscolarDAO unidadeEscolarDAO = new UnidadeEscolarDAO();
			List<SelectItem> comboUnidadeEscolar = new ArrayList<>();
			List<UnidadeEscolar> paramUnidadeEscolar = 
						unidadeEscolarDAO.consultaUnidadeEscolar(redeEnsinoDados.getPkRedeEnsino());
			
			for (UnidadeEscolar param : paramUnidadeEscolar){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkUnidadeEscolar());
			   s.setLabel(param.getNome());
			   comboUnidadeEscolar.add(s);
			}
			return comboUnidadeEscolar;
		}catch(SQLException e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de UNIDADE ESCOLAR, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar os Graus de Parentesco
	 * */
	public List<SelectItem> consultaGrauParentesco() {
		try {
			
			List<SelectItem> comboParentesco = new ArrayList<>();
			
			return comboParentesco;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de UNIDADE ESCOLAR, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar as Naturalidades
	 * */
	public List<SelectItem> consultaTipoLogradouro() {
		try {
			TipoLogradouroDAO tipoLogradouroDAO = new TipoLogradouroDAO();
			List<SelectItem> comboTipoLogradouro = new ArrayList<SelectItem>();
			List<TipoLogradouro> paramTipoLogradouro = tipoLogradouroDAO.consultaTipoLogradouro();
			
			for (TipoLogradouro param : paramTipoLogradouro){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkTipoLogradouro());
			   s.setLabel(param.getDescricao());
			   comboTipoLogradouro.add(s);
			}
			return comboTipoLogradouro;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de TIPOS DE LOGRADOURO, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	
	/*
	 * Metodo para carregar os cargos
	 * */
	public List<SelectItem> consultaCargo() {
		try {
			List<SelectItem> comboCargo = new ArrayList<>();
			CargoDAO cargoDAO = new CargoDAO();
			List<Cargo> paramCargo = cargoDAO.consultaCargo();
			
			for (Cargo param : paramCargo){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkCargo());
			   s.setLabel(param.getDescricao());
			   comboCargo.add(s);
			}
			return comboCargo;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de CARGO, contate o administrador do sistema!", null));
			return null;
		}
	}
}

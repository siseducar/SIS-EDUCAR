package modulos.RH.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import modulos.RH.dao.CargoDAO;
import modulos.RH.dao.CidadeDAO;
import modulos.RH.dao.EstadoCivilDAO;
import modulos.RH.dao.EstadoDAO;
import modulos.RH.dao.GrauInstrucaoDAO;
import modulos.RH.dao.NacionalidadeDAO;
import modulos.RH.dao.PaisDAO;
import modulos.RH.dao.RacaDAO;
import modulos.RH.dao.RedeEnsinoDAO;
import modulos.RH.dao.RegiaoDAO;
import modulos.RH.dao.ReligiaoDAO;
import modulos.RH.dao.SituacaoEconomicaDAO;
import modulos.RH.dao.SituacaoFuncionamentoDAO;
import modulos.RH.dao.TipoDeficienciaDAO;
import modulos.RH.dao.TipoOcupacaoDAO;
import modulos.RH.dao.UnidadeEscolarDAO;
import modulos.RH.om.Aluno;
import modulos.RH.om.Cargo;
import modulos.RH.om.Cidade;
import modulos.RH.om.Estado;
import modulos.RH.om.EstadoCivil;
import modulos.RH.om.GrauInstrucao;
import modulos.RH.om.GrauParentesco;
import modulos.RH.om.Nacionalidade;
import modulos.RH.om.Pais;
import modulos.RH.om.Raca;
import modulos.RH.om.RedeEnsino;
import modulos.RH.om.Regiao;
import modulos.RH.om.Religiao;
import modulos.RH.om.SituacaoEconomica;
import modulos.RH.om.SituacaoFuncionamento;
import modulos.RH.om.TipoDeficiencia;
import modulos.RH.om.TipoOcupacao;
import modulos.RH.om.UnidadeEscolar;

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

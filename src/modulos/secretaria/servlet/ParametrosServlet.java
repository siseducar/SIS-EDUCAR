package modulos.secretaria.servlet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import modulos.secretaria.dao.BlocoDAO;
import modulos.secretaria.dao.CidadeDAO;
import modulos.secretaria.dao.CursoDAO;
import modulos.secretaria.dao.EstadoCivilDAO;
import modulos.secretaria.dao.EstadoDAO;
import modulos.secretaria.dao.EtapaDAO;
import modulos.secretaria.dao.GrauInstrucaoDAO;
import modulos.secretaria.dao.GrauParentescoDAO;
import modulos.secretaria.dao.NacionalidadeDAO;
import modulos.secretaria.dao.PaisDAO;
import modulos.secretaria.dao.RacaDAO;
import modulos.secretaria.dao.RedeEnsinoDAO;
import modulos.secretaria.dao.RegiaoDAO;
import modulos.secretaria.dao.ReligiaoDAO;
import modulos.secretaria.dao.SituacaoEconomicaDAO;
import modulos.secretaria.dao.SituacaoFuncionamentoDAO;
import modulos.secretaria.dao.TipoAmbienteDAO;
import modulos.secretaria.dao.TipoDeficienciaDAO;
import modulos.secretaria.dao.TipoOcupacaoDAO;
import modulos.secretaria.dao.TurnoDAO;
import modulos.secretaria.dao.UnidadeEscolarDAO;
import modulos.secretaria.om.Bloco;
import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Curso;
import modulos.secretaria.om.Estado;
import modulos.secretaria.om.EstadoCivil;
import modulos.secretaria.om.Etapa;
import modulos.secretaria.om.GrauInstrucao;
import modulos.secretaria.om.GrauParentesco;
import modulos.secretaria.om.Nacionalidade;
import modulos.secretaria.om.Pais;
import modulos.secretaria.om.Raca;
import modulos.secretaria.om.RedeEnsino;
import modulos.secretaria.om.Regiao;
import modulos.secretaria.om.Religiao;
import modulos.secretaria.om.SituacaoEconomica;
import modulos.secretaria.om.SituacaoFuncionamento;
import modulos.secretaria.om.TipoAmbiente;
import modulos.secretaria.om.TipoDeficiencia;
import modulos.secretaria.om.TipoOcupacao;
import modulos.secretaria.om.Turno;
import modulos.secretaria.om.UnidadeEscolar;

@ManagedBean(name="paramServlet", eager=true)
@ApplicationScoped
public class ParametrosServlet {
	
	/* Combo com valores de NACIONALIDADE */
	private List<SelectItem> comboNacionalidade;
	
	/* Combo com valores de RAÇA */
	private List<SelectItem> comboRaca;
	
	/* Combo com valores de ESTADO CIVIL */
	private List<SelectItem> comboEstadoCivil;
	
	/* Combo com valores de GRAU DE INSTRUCAO */
	private List<SelectItem> comboGrauInstrucao;
	
	/* Combo com valores de SITUAÇÃO ECÔNIMCA */
	private List<SelectItem> comboSituacaoEconomica;
	
	/* Combo com valores de RELIGIÃO */
	private List<SelectItem> comboReligiao;
	
	/* Combo com valores de PAÍS */
	private List<SelectItem> comboPais;
	
	/* Combo com valores de TIPO DE REGIÃO */
	private List<SelectItem> comboRegiao;
	
	/* Combo com valores de GRAU DE PARENTESCO */
	private List<SelectItem> comboGrauParentesco;
	
	/* Combo com valores de ESTADO*/
	private List<SelectItem> comboEstado;
	
	/* Combo com valores de TIPO DE DEFICIENCIA */
	private List<SelectItem> comboTipoDeficiencia;
	
	/* Combo com valores de TIPO DE REDE DE ENSINO */
	private List<SelectItem> comboRedeEnsino;

	/* Combo com valores de SITUAÇÃO DE FUNCIONAMENTO */
	private List<SelectItem> comboSituFuncionamento;
	
	/* Combo com valores de TIPO DE OCUPAÇÃO */
	private List<SelectItem> comboTipoOcupacao;
	
	/* Combo com valores de TIPOS DE TURNO */
	private List<SelectItem> comboTurno;
	
	/* Combo com valores de TIPOS DE LOGRADOUROS */
	private List<SelectItem> comboTipoLogradouro;
	
	/* Combo com valores de TIPOS AMBIENTE */
	private List<SelectItem> comboTiposAmbiente;
	
	/* Combo com valores de BLOCOS AMBIENTE */
	private List<SelectItem> comboBlocosAmbiente;
	
	public ParametrosServlet() {
		if(this.comboNacionalidade == null) {
			this.comboNacionalidade = new ArrayList<SelectItem>();
		}
		
		if(this.comboRaca == null) {
			this.comboRaca = new ArrayList<SelectItem>();
		}
		
		if(this.comboSituFuncionamento == null) {
			this.comboSituFuncionamento = new ArrayList<SelectItem>();
		}
		
		if(this.comboTipoOcupacao == null) {
			this.comboTipoOcupacao = new ArrayList<SelectItem>();
		}
		
		if(this.comboTurno == null) {
			this.comboTurno = new ArrayList<SelectItem>();
		}
		
		if(this.comboEstadoCivil == null) {
			this.comboEstadoCivil = new ArrayList<SelectItem>();
		}
		
		if(this.comboGrauInstrucao == null) {
			this.comboGrauInstrucao = new ArrayList<SelectItem>();
		}
		
		if(this.comboSituacaoEconomica == null ) {
			this.comboSituacaoEconomica = new ArrayList<SelectItem>();
		}
		
		if(this.comboReligiao == null) {
			this.comboReligiao = new ArrayList<SelectItem>();
		}
		
		if(this.comboPais == null) {
			this.comboPais = new ArrayList<SelectItem>();
		}
		
		if(this.comboRegiao == null) {
			this.comboRegiao = new ArrayList<SelectItem>();
		}
		
		if(this.comboTipoDeficiencia == null) {
			this.comboTipoDeficiencia = new ArrayList<SelectItem>();
		}
		
		if(this.comboRedeEnsino == null) {
			this.comboRedeEnsino = new ArrayList<SelectItem>();
		}
		
		if(this.comboGrauParentesco == null) {
			this.comboGrauParentesco = new ArrayList<SelectItem>();
		}
		
		if(this.comboEstado == null) {
			this.comboEstado = new ArrayList<SelectItem>();
		}
		
		if(this.comboTiposAmbiente == null) {
			this.comboTiposAmbiente = new ArrayList<SelectItem>();
		}
		
		if(this.comboBlocosAmbiente == null) {
			this.comboBlocosAmbiente = new ArrayList<SelectItem>();
		}
		
		carregarCombos();
	}
	
	/*
	 * Metodo para carregar os parametros padroes
	 * 
	 * */
	public void carregarCombos() {
		if( comboNacionalidade.isEmpty() ) {			
			consultaNacionalidade();
		}
		
		if( comboRaca.isEmpty() ) {			
			consultaRaca();
		}
		
		if( comboTipoDeficiencia.isEmpty() ) {
			consultaTipoDeficiencia();
		}
		
		if( comboSituFuncionamento.isEmpty() ) {
			consultaSituacaoFuncionamento();
		}
		
		if( comboTipoOcupacao.isEmpty() ) {
			consultaTipoOcupacao();
		}
		
		if( comboTurno.isEmpty() ) {
			consultaTurno();
		}
		
		if( comboEstadoCivil.isEmpty() ) {			
			consultaEstaCivil();
		}
		
		if( comboGrauInstrucao.isEmpty() ) {
			consultaGrauInstru();
		}
		
		if( comboReligiao.isEmpty() ) {			
			consultaReligiao();
		}
		
		if( comboRegiao.isEmpty() ) {			
			consultaRegiao();
		}
		
		if( comboPais.isEmpty() ) {
			consultaPais();
		}
		
		if( comboRedeEnsino.isEmpty() ) {			
			consultaRedeEnsino();
		}
		
		if( comboGrauParentesco.isEmpty() ) {			
			consultaGrauParentesco();
		}
		
		if( comboEstado.isEmpty() ) {			
			consultaEstado();
		}
		
		if( comboSituacaoEconomica.isEmpty() ) {			
			consultaSituEconomica();
		}
		
		if( comboTiposAmbiente.isEmpty() ) {			
			consultaTiposAmbiente();
		}
		
		if( comboBlocosAmbiente.isEmpty() ) {			
			consultaBlocosAmbiente();
		}
	}
	
	/*
	 * Metodo para carregar as Naturalidades
	 * */
	public List<SelectItem> consultaNacionalidade() {
		try {
			NacionalidadeDAO nacionalidadeDAO = new NacionalidadeDAO();
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
	 * Metodo para carregar os tipos ocupação
	 * */
	public List<SelectItem> consultaTurno() {
		try {
			TurnoDAO turnoDAO = new TurnoDAO();
			List<Turno> paramTurno = turnoDAO.consultaTurno();
			
			for (Turno param : paramTurno){
				SelectItem  s = new SelectItem();
				s.setValue(param.getPkTurno());
				s.setLabel(param.getDescricao());
				comboTurno.add(s);
			}
			return comboTurno;
		} catch (SQLException e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de TURNO, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar os Estados Civis
	 * */
	public List<SelectItem> consultaEstaCivil() {
		try {
			EstadoCivilDAO estaCivilDAO = new EstadoCivilDAO();
			List<EstadoCivil> paramEstaCivil = estaCivilDAO.consultaEstaCivil();
			
			for (EstadoCivil param : paramEstaCivil){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkEstadoCivil());
			   s.setLabel(param.getDescricao());
			   comboEstadoCivil.add(s);
			}
			return comboEstadoCivil;
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
			List<GrauInstrucao> paramGrauInstru = grauInstruDAO.consultaGrauInstru();
			
			for (GrauInstrucao param : paramGrauInstru){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkGrauInstrucao());
			   s.setLabel(param.getDescricao());
			   comboGrauInstrucao.add(s);
			}
			return comboGrauInstrucao;
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
			List<SituacaoEconomica> paramSituEconomica = situEconomicaDAO.consultaSituEconomica();
			
			for (SituacaoEconomica param : paramSituEconomica){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkSituacaoEconomica());
			   s.setLabel(param.getDescricao());
			   comboSituacaoEconomica.add(s);
			}
			return comboSituacaoEconomica;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de SITUAÇÕES ECONÔMICAS, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar os tipos de ambiente
	 * */
	public List<SelectItem> consultaTiposAmbiente() 
	{
		try 
		{
			TipoAmbienteDAO tipoAmbienteDAO = new TipoAmbienteDAO();
			List<TipoAmbiente> paramTiposAmbiente = tipoAmbienteDAO.consultaTipoAmbiente();
			
			for (TipoAmbiente param : paramTiposAmbiente)
			{
				SelectItem  s = new SelectItem();
				s.setValue(param.getPkTipoAmbiente());
				s.setLabel(param.getDescricao());
				comboTiposAmbiente.add(s);
			}
			return comboTiposAmbiente;
		}
		catch(SQLException e) 
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de TIPO AMBIENTE, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar os blocos de ambiente
	 * */
	public List<SelectItem> consultaBlocosAmbiente() 
	{
		try 
		{
			BlocoDAO tipoAmbienteDAO = new BlocoDAO();
			List<Bloco> paramBloco = tipoAmbienteDAO.consultaBloco();
			
			for (Bloco param : paramBloco)
			{
				SelectItem  s = new SelectItem();
				s.setValue(param.getPkBloco());
				s.setLabel(param.getDescricao());
				comboBlocosAmbiente.add(s);
			}
			return comboBlocosAmbiente;
		}
		catch(SQLException e) 
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de TIPO AMBIENTE, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar as Religioes
	 * */
	public List<SelectItem> consultaReligiao() {
		try {
			ReligiaoDAO religiaoDAO = new ReligiaoDAO();
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
			List<SelectItem> comboEstado = new ArrayList<SelectItem>();
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
			List<TipoDeficiencia> paramTipoDeficiencia = tipoDeficienciaDAO.consultaTipoDeficiencia();
			
			for (TipoDeficiencia param : paramTipoDeficiencia){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkTipoDeficiencia());
			   s.setLabel(param.getDescricao());
			   comboTipoDeficiencia.add(s);
			}
			return comboTipoDeficiencia;
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
			List<RedeEnsino> paramRedeEnsino = redeEnsinoDAO.consultaRedeEnsino();
			
			for (RedeEnsino param : paramRedeEnsino){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkRedeEnsino());
			   s.setLabel(param.getDescricao());
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
	 * Metodo para carregar os Cursos Escolares
	 * */
	public List<SelectItem> consultaCursoEscolar(UnidadeEscolar unidadeEscolarDados) {
		try {
			CursoDAO cursoDAO = new CursoDAO();
			List<SelectItem> comboCursoEscolar = new ArrayList<>();
			List<Curso> paramCurso = cursoDAO.consultaCursoEscolar(unidadeEscolarDados.getPkUnidadeEscolar());
			
			for(Curso param : paramCurso) {
				SelectItem s = new SelectItem();
				s.setValue(param.getPkCurso());
				s.setLabel(param.getDescricao());
				comboCursoEscolar.add(s);
			}
			return comboCursoEscolar;
		}catch(SQLException e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de CURSO ESCOLAR, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar os Etapas Escolares 
	 * */
	public List<SelectItem> consultaEtapaEscolar(Curso cursoDados) {
		try {
			EtapaDAO etapaDAO = new EtapaDAO();
			List<SelectItem> comboEtapaEscolar = new ArrayList<>();
			List<Etapa> paramEtapaEscolar = etapaDAO.consultaEtapaEscolar(cursoDados.getPkCurso());
			
			for(Etapa param : paramEtapaEscolar) {
				SelectItem s = new SelectItem();
				s.setValue(param.getPkEtapa());
				s.setLabel(param.getDescricao());
				comboEtapaEscolar.add(s);
			}
			
			return comboEtapaEscolar;
		}catch(SQLException e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de ETAPA ESCOLAR, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/*
	 * Metodo para carregar os Turnos Escolares
	 * */
	public List<SelectItem> consultaTurnoEscolar(Etapa etapaDados){
		
		return null;
	}
	
	/*
	 * Metodo para carregar os Graus de Parentesco
	 * */
	public List<SelectItem> consultaGrauParentesco() {
		try {
			GrauParentescoDAO grauParentescoDAO = new GrauParentescoDAO();
			List<GrauParentesco> paramGrauParentesco = grauParentescoDAO.consultaGrau();
			
			for (GrauParentesco param : paramGrauParentesco){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkGrauParentesco());
			   s.setLabel(param.getDescricao());
			   comboGrauParentesco.add(s);
			}
			return comboGrauParentesco;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de GRAU DE PARENTESCO, contate o administrador do sistema!", null));
			return null;
		}
		
	}
	
	/*
	 * Metodo para carregar todos os estados
	 * */
	public List<SelectItem> consultaEstado() {       
        try {
        	
			EstadoDAO estadoDAO = new EstadoDAO();
			List<Estado> paramEstado = estadoDAO.listaEstado();
			
			for (Estado param : paramEstado){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkEstado());
			   s.setLabel(param.getNome());
			   comboEstado.add(s);
			}
			return comboEstado;
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de ESTADO DE NASCIMENTO, contate o administrador do sistema!", null));
			return null;
		}
    }

	
	/* GETTERS AND SETTERS */
	public List<SelectItem> getComboNacionalidade() {
		return comboNacionalidade;
	}

	public void setComboNacionalidade(List<SelectItem> comboNacionalidade) {
		this.comboNacionalidade = comboNacionalidade;
	}

	public List<SelectItem> getComboRaca() {
		return comboRaca;
	}

	public void setComboRaca(List<SelectItem> comboRaca) {
		this.comboRaca = comboRaca;
	}

	public List<SelectItem> getComboEstadoCivil() {
		return comboEstadoCivil;
	}

	public void setComboEstadoCivil(List<SelectItem> comboEstadoCivil) {
		this.comboEstadoCivil = comboEstadoCivil;
	}

	public List<SelectItem> getComboGrauInstrucao() {
		return comboGrauInstrucao;
	}

	public void setComboGrauInstrucao(List<SelectItem> comboGrauInstrucao) {
		this.comboGrauInstrucao = comboGrauInstrucao;
	}

	public List<SelectItem> getComboSituacaoEconomica() {
		return comboSituacaoEconomica;
	}

	public void setComboSituacaoEconomica(List<SelectItem> comboSituacaoEconomica) {
		this.comboSituacaoEconomica = comboSituacaoEconomica;
	}

	public List<SelectItem> getComboReligiao() {
		return comboReligiao;
	}

	public void setComboReligiao(List<SelectItem> comboReligiao) {
		this.comboReligiao = comboReligiao;
	}

	public List<SelectItem> getComboPais() {
		return comboPais;
	}

	public void setComboPais(List<SelectItem> comboPais) {
		this.comboPais = comboPais;
	}

	public List<SelectItem> getComboRegiao() {
		return comboRegiao;
	}

	public void setComboRegiao(List<SelectItem> comboRegiao) {
		this.comboRegiao = comboRegiao;
	}

	public List<SelectItem> getComboGrauParentesco() {
		return comboGrauParentesco;
	}

	public void setComboGrauParentesco(List<SelectItem> comboGrauParentesco) {
		this.comboGrauParentesco = comboGrauParentesco;
	}

	public List<SelectItem> getComboEstado() {
		return comboEstado;
	}

	public void setComboEstado(List<SelectItem> comboEstado) {
		this.comboEstado = comboEstado;
	}

	public List<SelectItem> getComboTipoDeficiencia() {
		return comboTipoDeficiencia;
	}

	public void setComboTipoDeficiencia(List<SelectItem> comboTipoDeficiencia) {
		this.comboTipoDeficiencia = comboTipoDeficiencia;
	}

	public List<SelectItem> getComboRedeEnsino() {
		return comboRedeEnsino;
	}

	public void setComboRedeEnsino(List<SelectItem> comboRedeEnsino) {
		this.comboRedeEnsino = comboRedeEnsino;
	}

	public List<SelectItem> getComboSituFuncionamento() {
		return comboSituFuncionamento;
	}

	public void setComboSituFuncionamento(List<SelectItem> comboSituFuncionamento) {
		this.comboSituFuncionamento = comboSituFuncionamento;
	}

	public List<SelectItem> getComboTipoOcupacao() {
		return comboTipoOcupacao;
	}

	public void setComboTipoOcupacao(List<SelectItem> comboTipoOcupacao) {
		this.comboTipoOcupacao = comboTipoOcupacao;
	}

	public List<SelectItem> getComboTurno() {
		return comboTurno;
	}

	public void setComboTurno(List<SelectItem> comboTurno) {
		this.comboTurno = comboTurno;
	}

	public List<SelectItem> getComboTipoLogradouro() {
		return comboTipoLogradouro;
	}

	public void setComboTipoLogradouro(List<SelectItem> comboTipoLogradouro) {
		this.comboTipoLogradouro = comboTipoLogradouro;
	}

	public List<SelectItem> getComboTiposAmbiente() {
		return comboTiposAmbiente;
	}

	public void setComboTiposAmbiente(List<SelectItem> comboTiposAmbiente) {
		this.comboTiposAmbiente = comboTiposAmbiente;
	}

	public List<SelectItem> getComboBlocosAmbiente() {
		return comboBlocosAmbiente;
	}

	public void setComboBlocosAmbiente(List<SelectItem> comboBlocosAmbiente) {
		this.comboBlocosAmbiente = comboBlocosAmbiente;
	}
}

package modulos.RH.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import modulos.RH.dao.CargoDAO;
import modulos.RH.dao.CidadeDAO;
import modulos.RH.dao.EnderecoDAO;
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
import modulos.RH.dao.TipoDeficienciaDAO;
import modulos.RH.dao.UnidadeEscolarDAO;
import modulos.RH.om.Aluno;
import modulos.RH.om.Cargo;
import modulos.RH.om.Cidade;
import modulos.RH.om.Endereco;
import modulos.RH.om.Estado;
import modulos.RH.om.EstadoCivil;
import modulos.RH.om.Fornecedor;
import modulos.RH.om.Funcionario;
import modulos.RH.om.GrauInstrucao;
import modulos.RH.om.Nacionalidade;
import modulos.RH.om.Pais;
import modulos.RH.om.Pessoa;
import modulos.RH.om.Raca;
import modulos.RH.om.RedeEnsino;
import modulos.RH.om.Regiao;
import modulos.RH.om.Religiao;
import modulos.RH.om.SituacaoEconomica;
import modulos.RH.om.TipoDeficiencia;
import modulos.RH.om.UnidadeEscolar;

@ManagedBean(name="parametrosServlet")
@ViewScoped
public class ParametrosServlet implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/* Atributos */
	static Pessoa pessoaDados;
	static Aluno alunoDados;
	static Fornecedor fornecedorDados;
	static Funcionario funcionarioDados;
	static Pais paisDados;
	static Estado estadoDados;
	static Cidade cidadeDados;
	static Endereco enderecoDados;
	static Nacionalidade nacionalidadeDados;
	static Raca racaDados;
	static EstadoCivil estaCivilDados;
	static GrauInstrucao grauInstruDados;
	static SituacaoEconomica situEconomicaDados;
	static Religiao religiaoDados;
	
	/* Metodo Construtor */
	public ParametrosServlet() throws SQLException{
		if(pessoaDados == null){
			pessoaDados = new Pessoa();
		}
		if(funcionarioDados == null){
			funcionarioDados = new Funcionario();
		}
		if(alunoDados == null){
			alunoDados = new Aluno();
		}
		if(fornecedorDados == null){
			fornecedorDados = new Fornecedor();
		}
		if(paisDados == null){
			paisDados = new Pais();
		}
		if(estadoDados == null){
			estadoDados = new Estado();
		}
		if(cidadeDados == null){
			cidadeDados = new Cidade();
		}
		if(enderecoDados == null) {
			enderecoDados = new Endereco();
		}
		if(nacionalidadeDados == null) {
			nacionalidadeDados = new Nacionalidade();
		}
		if(racaDados == null) {
			racaDados = new Raca();
		}
		if(estaCivilDados == null) {
			estaCivilDados = new EstadoCivil();
		}
		if(grauInstruDados == null){
			grauInstruDados = new GrauInstrucao();
		}
		if(situEconomicaDados == null) {
			situEconomicaDados = new SituacaoEconomica();
		}
		if(religiaoDados == null) {
			religiaoDados = new Religiao();
		}
	}
	
	/*
	 *Metodo para salvar os dados da pessoa 
	 * 
	 * */
	public String getCadastroPessoa(){
		cidadeDados.getNome();
		cidadeDados.getPkCidade();
		
		pessoaDados.getNome();
		return pessoaDados.getNome();
	}
	
	public String getTestaAtributos () {
		System.out.println(
				pessoaDados.getNome() + " " +
				pessoaDados.getCpf() + " " +
				pessoaDados.getRg() + " " +
				pessoaDados.getSexo());
		System.out.println(
				nacionalidadeDados.getDescricao() + " " +
				nacionalidadeDados.getPkNacionalidade());
		System.out.println(
				racaDados.getDescricao() + " " +
				racaDados.getPkRaca());
		System.out.println(
				estaCivilDados.getDescricao() + " " +
				estaCivilDados.getPkEstadoCivil());
		System.out.println(
				grauInstruDados.getDescricao() + " " +
				grauInstruDados.getPkGrauInstrucao());
		System.out.println(
				situEconomicaDados.getDescricao() + " " +
				situEconomicaDados.getPkSituacaoEconomica());
		System.out.print(
				religiaoDados.getDescricao() + " " +
				religiaoDados.getPkReligiao());
		return "Cadastro com sucesso";
	}
	
	public void consultaEndereco(){
		try {
			EnderecoDAO enderecoDAO = new EnderecoDAO();
			Endereco dadosEndereco = enderecoDAO.retornEnderecoDados(enderecoDados.getCep());
			
			enderecoDados.setLogradouro(dadosEndereco.getLogradouro());
			enderecoDados.setBairro(dadosEndereco.getBairro());
			enderecoDados.setNumero(dadosEndereco.getNumero());
			enderecoDados.setComplemento(dadosEndereco.getComplemento());
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"CEP não encontrado", null));
		}
	}
	
	public List<String> consultaCep(String cep) throws SQLException {
        List<String> results = new ArrayList<String>();
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        
        results = enderecoDAO.consultaCEP(cep);
                 
        return results;
    }
	
	/*
	 * Metodo para carregar as Naturalidades
	 * */
	public List<SelectItem> getConsultaNacionalidade() {
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
	public List<SelectItem> getConsultaRaca() {
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
	 * Metodo para carregar os Estados Civis
	 * */
	public List<SelectItem> getConsultaEstaCivil() {
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
	public List<SelectItem> getConsultaGrauInstru() {
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
	public List<SelectItem> getConsultaSituEconomica() {
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
	public List<SelectItem> getConsultaReligiao() {
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
	public List<SelectItem> getConsultaPais() {
		try {
			PaisDAO paisDAO = new PaisDAO();
			List<SelectItem> comboPais = new ArrayList<SelectItem>();
			List<Pais> paramPais = paisDAO.consultaPais();
			
			for (Pais param : paramPais){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkPais());
			   s.setLabel(param.getNome());
			   comboPais.add(s);
			}
			estadoDados.setPkEstado(null);
			cidadeDados.setPkCidade(null);
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
	public List<SelectItem> getConsultaEstado() {
		List<SelectItem> comboEstado = new ArrayList<SelectItem>();
		if(paisDados.getPkPais() != null){
			try {
				EstadoDAO estadoDAO = new EstadoDAO();
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
		estadoDados.setPkEstado(null);
		comboEstado.clear();
		return null;
	}
	
	/*
	 * Metodo para carregar as Cidades
	 * */
	public List<SelectItem> getConsultaCidade() {
		List<SelectItem> comboCidade = new ArrayList<SelectItem>();
		if(estadoDados.getPkEstado() != null){
			try {
				CidadeDAO cidadeDAO = new CidadeDAO();
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
		comboCidade.clear();
		return null;
	}

	/*
	 * Metodo para carregar as Zonas Residencias
	 * */
	public List<SelectItem> getConsultaRegiao() {
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
	public List<SelectItem> getConsultaTipoDeficiencia() throws SQLException {
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
	}
	
	/*
	 * Metodo para carregar os cargos
	 * */
	public List<SelectItem> getConsultaCargo() throws SQLException {
		CargoDAO cargoDAO = new CargoDAO();
		List<SelectItem> comboCargo = new ArrayList<SelectItem>();
		List<Cargo> paramCargo = cargoDAO.consultaCargo();
		
		for (Cargo param : paramCargo){
		   SelectItem  s = new SelectItem();
		   s.setValue(param.getPkCargo());
		   s.setLabel(param.getDescricao());
		   comboCargo.add(s);
		}
		return comboCargo;
	}
	
	/*
	 * Metodo para carregar as rede de ensino
	 * */
	public List<SelectItem> getConsultaRedeEnsino() throws SQLException {
		RedeEnsinoDAO redeEnsinoDAO = new RedeEnsinoDAO();
		List<SelectItem> comboRedeEnsino = new ArrayList<>();
		List<RedeEnsino> paramRedeEnsino = redeEnsinoDAO.consultaRedeEnsino();
		
		for (RedeEnsino param : paramRedeEnsino){
		   SelectItem  s = new SelectItem();
		   s.setValue(param.getPkRedeEnsino());
		   s.setLabel(param.getNome());
		   comboRedeEnsino.add(s);
		}
		alunoDados.setUnidadeEscolar(null);
		return comboRedeEnsino;
	}
	
	/*
	 * Metodo para carregar as Unidades Escolares
	 * */
	public List<SelectItem> getConsultaUnidadeEscolar() throws NumberFormatException, SQLException {
		if(alunoDados.getRedeEnsino() != null) {
			UnidadeEscolarDAO unidadeEscolarDAO = new UnidadeEscolarDAO();
			List<SelectItem> comboUnidadeEscolar = new ArrayList<>();
			List<UnidadeEscolar> paramUnidadeEscolar = 
						unidadeEscolarDAO.consultaUnidadeEscolar(Integer.parseInt(alunoDados.getRedeEnsino()));
			
			for (UnidadeEscolar param : paramUnidadeEscolar){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkUnidadeEscolar());
			   s.setLabel(param.getNome());
			   comboUnidadeEscolar.add(s);
			}
			return comboUnidadeEscolar;
		}
		return null;
	}

	/* GETTERS AND SETTERS */
	public Pessoa getPessoaDados() {
		return pessoaDados;
	}

	public void setPessoaDados(Pessoa pessoaDados) {
		this.pessoaDados = pessoaDados;
	}

	public Aluno getAlunoDados() {
		return alunoDados;
	}

	public void setAlunoDados(Aluno alunoDados) {
		this.alunoDados = alunoDados;
	}

	public Fornecedor getFornecedorDados() {
		return fornecedorDados;
	}

	public void setFornecedorDados(Fornecedor fornecedorDados) {
		this.fornecedorDados = fornecedorDados;
	}

	public Funcionario getFuncionarioDados() {
		return funcionarioDados;
	}

	public void setFuncionarioDados(Funcionario funcionarioDados) {
		this.funcionarioDados = funcionarioDados;
	}

	public Pais getPaisDados() {
		return paisDados;
	}

	public void setPaisDados(Pais paisDados) {
		this.paisDados = paisDados;
	}

	public Estado getEstadoDados() {
		return estadoDados;
	}

	public void setEstadoDados(Estado estadoDados) {
		this.estadoDados = estadoDados;
	}

	public Cidade getCidadeDados() {
		return cidadeDados;
	}

	public void setCidadeDados(Cidade cidadeDados) {
		this.cidadeDados = cidadeDados;
	}

	public Nacionalidade getNacionalidadeDados() {
		return nacionalidadeDados;
	}

	public void setNacionalidadeDados(Nacionalidade nacionalidadeDados) {
		this.nacionalidadeDados = nacionalidadeDados;
	}

	public Raca getRacaDados() {
		return racaDados;
	}

	public void setRacaDados(Raca racaDados) {
		this.racaDados = racaDados;
	}

	public EstadoCivil getEstaCivilDados() {
		return estaCivilDados;
	}

	public void setEstaCivilDados(EstadoCivil estaCivilDados) {
		this.estaCivilDados = estaCivilDados;
	}

	public GrauInstrucao getGrauInstruDados() {
		return grauInstruDados;
	}

	public void setGrauInstruDados(GrauInstrucao grauInstruDados) {
		this.grauInstruDados = grauInstruDados;
	}

	public SituacaoEconomica getSituEconomicaDados() {
		return situEconomicaDados;
	}

	public void setSituEconomicaDados(SituacaoEconomica situEconomicaDados) {
		this.situEconomicaDados = situEconomicaDados;
	}

	public Religiao getReligiaoDados() {
		return religiaoDados;
	}

	public void setReligiaoDados(Religiao religiaoDados) {
		this.religiaoDados = religiaoDados;
	}

	public Endereco getEnderecoDados() {
		return enderecoDados;
	}

	public void setEnderecoDados(Endereco enderecoDados) {
		this.enderecoDados = enderecoDados;
	}
}

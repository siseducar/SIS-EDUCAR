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
import javax.servlet.http.Part;

import modulos.RH.dao.CargoDAO;
import modulos.RH.dao.CidadeDAO;
import modulos.RH.dao.EnderecoDAO;
import modulos.RH.dao.EstadoCivilDAO;
import modulos.RH.dao.EstadoDAO;
import modulos.RH.dao.GrauInstrucaoDAO;
import modulos.RH.dao.NacionalidadeDAO;
import modulos.RH.dao.PaisDAO;
import modulos.RH.dao.PessoaDAO;
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
import modulos.RH.om.ImagemBase64;
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

@ManagedBean(name="pessoaServlet")
@ViewScoped
public class PessoaServlet implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/* Atributos */
	Pessoa pessoaDados;
	Aluno alunoDados;
	Fornecedor fornecedorDados;
	Funcionario funcionarioDados;
	Pais paisDados;
	Estado estadoDados;
	Cidade cidadeDados;
	Endereco enderecoDados;
	Nacionalidade nacionalidadeDados;
	Raca racaDados;
	EstadoCivil estaCivilDados;
	GrauInstrucao grauInstruDados;
	SituacaoEconomica situEconomicaDados;
	Religiao religiaoDados;
	Regiao regiaoDados;
	ImagemBase64 imagem64B;
		
	/* Componente de dados complementares do aluno */
	private Boolean complementoAluno;
	
	/* Componente de dados complementares do funcionario */
	private Boolean complementoFuncionario;
	
	/* Componente do tipo de deficiencia */
	private Boolean alunoDeficiente;
	
	/* Componente para validacao de concursado */
	private Boolean funcConcursado;
	
	/* Componente para validacao de aposentadoria */
	private Boolean funcAposentado;
	
	/* Componente para validar demissao */
	private Boolean funcDemitido;
	
	private Part imagem;
	
	List<SelectItem> comboPais;
	
	List<SelectItem> comboEstado;
	
	List<SelectItem> comboCidade;
	
	/* Metodo Construtor */
	public PessoaServlet() throws SQLException{
		if(this.pessoaDados == null){
			this.pessoaDados = new Pessoa();
		}
		if(this.funcionarioDados == null){
			this.funcionarioDados = new Funcionario();
		}
		if(this.alunoDados == null){
			this.alunoDados = new Aluno();
		}
		if(this.fornecedorDados == null){
			this.fornecedorDados = new Fornecedor();
		}
		if(this.paisDados == null){
			this.paisDados = new Pais();
		}
		if(this.estadoDados == null){
			this.estadoDados = new Estado();
		}
		if(this.cidadeDados == null){
			this.cidadeDados = new Cidade();
		}
		if(this.enderecoDados == null) {
			this.enderecoDados = new Endereco();
		}
		if(this.nacionalidadeDados == null) {
			this.nacionalidadeDados = new Nacionalidade();
		}
		if(this.racaDados == null) {
			this.racaDados = new Raca();
		}
		if(this.estaCivilDados == null) {
			this.estaCivilDados = new EstadoCivil();
		}
		if(this.grauInstruDados == null){
			this.grauInstruDados = new GrauInstrucao();
		}
		if(this.situEconomicaDados == null) {
			this.situEconomicaDados = new SituacaoEconomica();
		}
		if(this.religiaoDados == null) {
			this.religiaoDados = new Religiao();
		}
		if(this.imagem64B == null) {
			this.imagem64B = new ImagemBase64();
		}
		if(this.regiaoDados == null) {
			this.regiaoDados = new Regiao();
		}
		pessoaDados.setTipoPessoa(0);
		complementoAluno = false;
		complementoFuncionario = false;
		alunoDeficiente = false;
		funcConcursado = false;
		funcAposentado = false;
		funcDemitido = false;
		comboPais = new ArrayList<SelectItem>();
		comboEstado = new ArrayList<SelectItem>();
		comboCidade = new ArrayList<SelectItem>();
	}
	
	/*
	 * Metodo para redenrizar os dados complementares
	 * 
	 * */
	public void complementoDados() {
		if(pessoaDados.getTipoPessoa() == 0) {
			complementoAluno = false;
			complementoFuncionario = false;
		}
		if(pessoaDados.getTipoPessoa() == 1) {
			complementoAluno = true;
			complementoFuncionario = false;
		}
		if(pessoaDados.getTipoPessoa() == 2) {
			complementoFuncionario = true;
			complementoAluno = false;
		}
	}
	
	/*
	 * Metodo para validar funcionario concursado
	 * 
	 * */
	public void validaConcursado(){
		if(funcionarioDados.getConcursado()){
			funcConcursado = true;
		}else{
			funcConcursado = false;
		}		
	}
	
	/*
	 * Metodo para validar aluno deficiente
	 * 
	 * */
	public void validaDeficiente() {
		if(alunoDados.getAlunoDeficiente()) {
			alunoDeficiente = true;
		} else {
			alunoDeficiente = false;
		}
	}
	
	/*
	 * Metodo para validar aposentadoria
	 * 
	 * */
	public void validaAposentadoria(){
		if(funcionarioDados.getAposentado()){
			funcAposentado = true;
		} else {
			funcAposentado = false;
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
	
	public void salvarCadastro(){
		if(pessoaDados.getTipoPessoa() == 0 && pessoaDados != null) {
			salvarCadastroPessoa();
		} else {
			if(pessoaDados.getTipoPessoa() == 1 && pessoaDados != null) {
				System.out.println("aluno");
			} else {
				System.out.println("funcionario");
			}
		}
		
	}
	
	
	public String salvarCadastroPessoa(){
		try {
			Pessoa pessoaDadosFinal = new Pessoa();
			replaceCampos();
			
			if( pessoaDados != null ) {
				pessoaDadosFinal.setTipoPessoa(pessoaDados.getTipoPessoa());
				pessoaDadosFinal.setNome(pessoaDados.getNome());
				pessoaDadosFinal.setCpf(pessoaDados.getCpf());
				pessoaDadosFinal.setRg(pessoaDados.getRg());
				pessoaDadosFinal.setDataNascimento(pessoaDados.getDataNascimento());
				pessoaDadosFinal.setSexo(pessoaDados.getSexo());
				pessoaDadosFinal.setEmail(pessoaDados.getEmail());
				pessoaDadosFinal.setTelefoneResidencial(pessoaDados.getTelefoneResidencial());
				pessoaDadosFinal.setTelefoneCelular(pessoaDados.getTelefoneCelular());
				pessoaDadosFinal.setStatus(Integer.valueOf(0));
				
			}
			if( paisDados != null ) {
				pessoaDadosFinal.setPais(paisDados);
			}
			if( estadoDados != null ) {
				pessoaDadosFinal.setEstado(estadoDados);
			}
			if( enderecoDados != null ) {
				enderecoDados.setCidade(cidadeDados);
				pessoaDadosFinal.setEndereco(enderecoDados);
			}
			if( nacionalidadeDados != null ) {
				pessoaDadosFinal.setNacionalidade(nacionalidadeDados);
			}
			if( racaDados != null ) {
				pessoaDadosFinal.setRaca(racaDados);
			}
			if( estaCivilDados != null ) {
				pessoaDadosFinal.setEstadoCivil(estaCivilDados);
			}
			if( grauInstruDados != null ) {
				pessoaDadosFinal.setGrauInstrucao(grauInstruDados);
			}		
			if( situEconomicaDados != null ) {
				pessoaDadosFinal.setSituacaoEconomica(situEconomicaDados);
			}
			if( religiaoDados != null ) {
				pessoaDadosFinal.setReligiao(religiaoDados);
			}
			if(regiaoDados != null) {
				pessoaDadosFinal.setRegiao(regiaoDados);
			}
			if(paisDados != null){
				System.out.println(paisDados);
			}
			if(estadoDados != null){
				System.out.println(estadoDados);
			}
			if(cidadeDados != null){
				System.out.println(cidadeDados);
			}
			new PessoaDAO().salvarCadastroPessoa(pessoaDadosFinal);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
					"Cadastro Realizado com sucesso",null));
			limparFormulario();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao cadastrar",null));
			return null;
		}
		
		return null;
	}
	
	public void replaceCampos(){
		/* RG */
		pessoaDados.setRg(pessoaDados.getRg().replace(".", ""));
		pessoaDados.setRg(pessoaDados.getRg().replace("-", ""));
		pessoaDados.setRg(pessoaDados.getRg().replace(" ", ""));
		
		/* TELFONE RESIDENCIAL */
		pessoaDados.setTelefoneResidencial(pessoaDados.getTelefoneResidencial().replace("(", ""));
		pessoaDados.setTelefoneResidencial(pessoaDados.getTelefoneResidencial().replace(")", ""));
		pessoaDados.setTelefoneResidencial(pessoaDados.getTelefoneResidencial().replace(" ", ""));
		pessoaDados.setTelefoneResidencial(pessoaDados.getTelefoneResidencial().replace("-", ""));
		
		/* TELEFONE CELULAR */
		pessoaDados.setTelefoneCelular(pessoaDados.getTelefoneCelular().replace("(", ""));
		pessoaDados.setTelefoneCelular(pessoaDados.getTelefoneCelular().replace(")", ""));
		pessoaDados.setTelefoneCelular(pessoaDados.getTelefoneCelular().replace("-", ""));
		pessoaDados.setTelefoneCelular(pessoaDados.getTelefoneCelular().replace(" ", ""));
		
	}
	
	public void limparFormulario(){
		pessoaDados = new Pessoa();
		alunoDados = new Aluno();
		fornecedorDados = new Fornecedor();
		funcionarioDados = new Funcionario();
		paisDados = new Pais();
		estadoDados = new Estado();
		cidadeDados = new Cidade();
		enderecoDados = new Endereco();
		nacionalidadeDados = new Nacionalidade();
		racaDados = new Raca();
		estaCivilDados = new EstadoCivil();
		grauInstruDados = new GrauInstrucao();
		situEconomicaDados = new SituacaoEconomica();
		religiaoDados = new Religiao();
		regiaoDados = new Regiao();
		pessoaDados.setTipoPessoa(0);
		complementoAluno = false;
		complementoFuncionario = false;
		alunoDeficiente = false;
		funcConcursado = false;
		funcAposentado = false;
		funcDemitido = false;
		comboPais = new ArrayList<SelectItem>();
		comboEstado = new ArrayList<SelectItem>();
		comboCidade = new ArrayList<SelectItem>();
	}
	
	public Boolean getvalidaNomeMae(){
		if(alunoDados.getCpfMae() != null){
			return true;
		}
		
		return false;
	}
	
	public Boolean validaNomePai(){
		
		
		return false;
	}
	
	public Boolean validaNomeResponsavel(){
		
		return false;
	}
/* ------------------------------------------------------------------------------------------------------------------------ */
/* ---------------------------------Metodos para carregar os compos da tela------------------------------------------------ */
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
			List<Pais> paramPais = paisDAO.consultaPais();
			
			for (Pais param : paramPais){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkPais());
			   s.setLabel(param.getNome());
			   comboPais.add(s);
			}
			comboEstado.clear();
			comboCidade.clear();
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
		if(paisDados.getPkPais() != null && !comboPais.isEmpty()){
			try {
				EstadoDAO estadoDAO = new EstadoDAO();
				List<Estado> paramEstado = estadoDAO.consultaEstado(paisDados.getPkPais());
				
				for (Estado param : paramEstado){
				   SelectItem  s = new SelectItem();
				   s.setValue(param.getPkEstado());
				   s.setLabel(param.getNome());
				   comboEstado.add(s);
				}
				comboCidade.clear();
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
		if(estadoDados.getPkEstado() != null && !comboEstado.isEmpty()){
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
		cidadeDados.setPkCidade(null);
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

	public Boolean getComplementoAluno() {
		return complementoAluno;
	}

	public void setComplementoAluno(Boolean complementoAluno) {
		this.complementoAluno = complementoAluno;
	}

	public Boolean getComplementoFuncionario() {
		return complementoFuncionario;
	}

	public void setComplementoFuncionario(Boolean complementoFuncionario) {
		this.complementoFuncionario = complementoFuncionario;
	}

	public Boolean getAlunoDeficiente() {
		return alunoDeficiente;
	}

	public void setAlunoDeficiente(Boolean alunoDeficiente) {
		this.alunoDeficiente = alunoDeficiente;
	}

	public Boolean getFuncConcursado() {
		return funcConcursado;
	}

	public void setFuncConcursado(Boolean funcConcursado) {
		this.funcConcursado = funcConcursado;
	}

	public Boolean getFuncAposentado() {
		return funcAposentado;
	}

	public void setFuncAposentado(Boolean funcAposentado) {
		this.funcAposentado = funcAposentado;
	}

	public Boolean getFuncDemitido() {
		return funcDemitido;
	}

	public void setFuncDemitido(Boolean funcDemitido) {
		this.funcDemitido = funcDemitido;
	}
	
	public Endereco getEnderecoDados() {
		return enderecoDados;
	}

	public void setEnderecoDados(Endereco enderecoDados) {
		this.enderecoDados = enderecoDados;
	}
	
	public Part getImagem() { 
		return imagem; 
	} 
	
	public void setImagem(Part imagem) { 
		this.imagem = imagem; 
	}

	public ImagemBase64 getImagem64B() {
		return imagem64B;
	}

	public void setImagem64B(ImagemBase64 imagem64b) {
		imagem64B = imagem64b;
	}

	public Regiao getRegiaoDados() {
		return regiaoDados;
	}

	public void setRegiaoDados(Regiao regiaoDados) {
		this.regiaoDados = regiaoDados;
	}
}

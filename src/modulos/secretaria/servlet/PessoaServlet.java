package modulos.secretaria.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import modulos.secretaria.dao.AlunoDAO;
import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.om.Aluno;
import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Endereco;
import modulos.secretaria.om.Estado;
import modulos.secretaria.om.EstadoCivil;
import modulos.secretaria.om.Fornecedor;
import modulos.secretaria.om.Funcionario;
import modulos.secretaria.om.GrauInstrucao;
import modulos.secretaria.om.Nacionalidade;
import modulos.secretaria.om.Pais;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.Raca;
import modulos.secretaria.om.Regiao;
import modulos.secretaria.om.Religiao;
import modulos.secretaria.om.SituacaoEconomica;

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
	
	/* Componente para salvar COMPROVANTE DE RESIDENCIA*/
	private UploadedFile imagemResidencia;
	
	/* Componente para salvar FOTO DO ALUNO */
	private UploadedFile imagemAluno;
	
	/* Componente para salvar CERTIDÃO DE NASCIMENTO */
	private UploadedFile imagemCertNascimento;
		
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
	
	/* Combo com valores de Tipos de Logradouros*/
	private List<SelectItem> comboTipoLogradouro;
	
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
	
	/* Componente */
	private Boolean nomeMae;
	
	/* Componente */
	private Boolean nomePai;
	
	/* Componente */
	private Boolean nomeResponsavel;
	
	/* Componente para validar idade da pessoa */
	private Boolean menorIdade;

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
		if(this.regiaoDados == null) {
			this.regiaoDados = new Regiao();
		}

		pessoaDados.setTipoPessoa(0);
		comboCargo = new ArrayList<SelectItem>();
		comboEstadoCivil = new ArrayList<SelectItem>();
		comboGrauInstrucao = new ArrayList<SelectItem>();
		comboNacionalidade = new ArrayList<SelectItem>();
		comboRaca = new ArrayList<SelectItem>();
		comboRedeEnsino = new ArrayList<SelectItem>();
		comboReligiao = new ArrayList<SelectItem>();
		comboSituacaoEconomica = new ArrayList<SelectItem>();
		comboUnidadeEscolar = new ArrayList<SelectItem>();
		comboZonaResidencial = new ArrayList<SelectItem>();
		comboGrauParentesco = new ArrayList<SelectItem>();
		comboTipoDeficiencia = new ArrayList<SelectItem>();
		comboPais = new ArrayList<SelectItem>();
		comboEstado = new ArrayList<SelectItem>();
		comboCidade = new ArrayList<SelectItem>();
		comboTipoLogradouro = new ArrayList<SelectItem>();
		
		carregaCombos();
		complementoAluno = false;
		funcDemitido = false;
		complementoFuncionario = false;
		alunoDeficiente = false;
		funcConcursado = false;
		funcAposentado = false;
		nomeMae = false;
		nomePai = false;
		nomeResponsavel = false;
		menorIdade = false;
	}
	
	public void calculaIdade(){
		
		if(pessoaDados.getDataNascimento() != null && !pessoaDados.getDataNascimento().equals("__/__/____")){
			GregorianCalendar dataHoje = new GregorianCalendar();
			GregorianCalendar nascimento = new GregorianCalendar();
			
			int idade;
			nascimento.setTime(pessoaDados.getDataNascimento());
					
			int anoAtual = dataHoje.get(Calendar.YEAR);
			int anoNascimento = nascimento.get(Calendar.YEAR);
			idade = anoAtual - anoNascimento;
			
			if( idade < 18 ){
				menorIdade = true;
			}else{
				menorIdade = false;
			}
		}
	}
	
	public void save(FileUploadEvent event) {
		if(event.getFile() != null ){
            System.out.println("OK");
		}
    }
	
	/*
	 * Metodo para salvar o cadastro de Pessoa
	 * 
	 * */
	public String salvarCadastroPessoa(){
		try {
			
			Pessoa pessoaDadosFinal = new Pessoa();
			
			/* Validação dos dados referentes a PESSOA */
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
			}
			
			/* Validação dos dados referentes ao ENDEREÇO */
			if( enderecoDados != null ) {
				enderecoDados.setCidade(cidadeDados);
				pessoaDadosFinal.setEndereco(enderecoDados);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Informe os dados referentes ao ENDEREÇO.",null));
			}
			
			/* Validação dos dados referentes a NACIONALIDADE */
			if( nacionalidadeDados != null ) {
				pessoaDadosFinal.setNacionalidade(nacionalidadeDados);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Selecione uma NACIONALIDADE.",null));
			}
			
			/* Validação dos dados referentes a RAÇA */
			if( racaDados != null ) {
				pessoaDadosFinal.setRaca(racaDados);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Selecione uma RAÇA.",null));
			}
			
			/* Validação dos dados referentes ao ESTADO CIVIL */
			if( estaCivilDados != null ) {
				pessoaDadosFinal.setEstadoCivil(estaCivilDados);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Selecione um ESTADO CIVIL.",null));
			}
			
			/* Validação dos dados referentes ao GRAU DE INSTRUÇÃO */
			if( grauInstruDados != null ) {
				pessoaDadosFinal.setGrauInstrucao(grauInstruDados);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Selecione um GRAU DE INSTRUÇÃO.",null));
			}
			
			/* Validação dos dados referentes a SITUAÇÃO ECONÔMICA */
			if( situEconomicaDados != null ) {
				pessoaDadosFinal.setSituacaoEconomica(situEconomicaDados);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Selecione uma SITUAÇÃO ECONÔMICA.",null));
			}
			
			/* Validação dos dados referentes a RELIGIÃO */
			if( religiaoDados != null ) {
				pessoaDadosFinal.setReligiao(religiaoDados);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Selecione uma RELIGIÃO.",null));
			}
			
			/* Validação dos dados referentes a REGIÃO */
			if(regiaoDados != null) {
				pessoaDadosFinal.setRegiao(regiaoDados);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Selecione uma REGIÃO.",null));
			}
			
			/* Validação dos dados referentes ao PAÍS */
			if(paisDados != null){
				System.out.println(paisDados);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Selecione um PAÍS.",null));
			}
			
			/* Validação dos dados referentes ao ESTADO */
			if(estadoDados != null){
				System.out.println(estadoDados);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Selecione um ESTADO.",null));
			}
			
			/* Validação dos dados referentes a CIDADE */
			if(cidadeDados != null){
				System.out.println(cidadeDados);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Selecione uma CIDADE.",null));
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
	
	/*
	 * Metodo responsavel por salvar os dados do aluno
	 * 
	 * */
	public String salvarCadastroAluno(){
		return null;
	}
	
	/*
	 * Metodo para limpar o formulario apos cadastro realizado
	 * 
	 * */
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
		nomeMae = false;
		nomePai = false;
		nomeResponsavel = false;
		comboPais = new ArrayList<SelectItem>();
		comboEstado = new ArrayList<SelectItem>();
		comboCidade = new ArrayList<SelectItem>();
	}
	
/* ------------------------------------------------------------------------------------------------------------------------ */
/* ---------------------------------Metodos utlizados na tela------------------------------------------------ */

	
	
	/*
	 * Metodo responsavel por validar o nome da MAE do aluno
	 * 
	 * */
	public void validaNomeMae(){
		if(alunoDados.getCpfMae() != null && alunoDados.getCpfMae() != 0){
			try {
				Long cpfMae = alunoDados.getCpfMae();
				AlunoDAO alunoDAO = new AlunoDAO();
				
				String nome = alunoDAO.consultaNomeResponsavel(cpfMae); 
				
				if(nome != null && !nome.equals("")){
					alunoDados.setNomeMae(nome);
					nomeMae = true;
				}else{
					alunoDados.setNomeMae(null);
					nomeMae = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"CPF não encontrado, favor informar o nome.", null));
				}
			} catch (SQLException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Erro ao consultar o CPF informado", null));
			}
		}
	}
	
	/*
	 * Metodo responsavel por validar o nome do PAI do aluno
	 * 
	 * */
	public void validaNomePai(){
		if(alunoDados.getCpfPai() != null && alunoDados.getCpfPai() != 0 ){
			try {
				Long cpfPai = alunoDados.getCpfPai();
				AlunoDAO alunoDAO = new AlunoDAO();
				
				String nome = alunoDAO.consultaNomeResponsavel(cpfPai); 
				if(nome != null && !nome.equals("")){
					alunoDados.setNomePai(nome);
					nomePai = true;
				}else{
					alunoDados.setNomePai(null);
					nomePai = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"CPF não encontrado, favor informar o nome.", null));
				}
			} catch (SQLException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Erro ao consultar o CPF informado", null));
			}
		}
	}
	
	/*
	 * Metodo responsavel por validar o nome do RESPONSAVEL pelo aluno
	 * 
	 * */
	public void validaNomeResponsavel(){
		if(alunoDados.getCpfResponsavel() != null && alunoDados.getCpfResponsavel() != null){
			try {
				Long cpfResponsavel = alunoDados.getCpfResponsavel();
				AlunoDAO alunoDAO = new AlunoDAO();
				
				String nome = alunoDAO.consultaNomeResponsavel(cpfResponsavel); 
				if(nome != null && !nome.equals("")){
					alunoDados.setNomeResponsavel(nome);
					nomeResponsavel = true;
				}else{
					alunoDados.setNomePai(null);
					nomeResponsavel = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"CPF não encontrado, favor informar o nome.", null));
				}
			} catch (SQLException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Erro ao consultar o CPF informado", null));
			}
		}
	}
	
	/*
	 * Metodo para redenrizar os dados complementares
	 * 
	 * */
	public void complementoDados() {
		if(pessoaDados.getTipoPessoa() == 0) {
			complementoAluno = false;
			complementoFuncionario = false;
			menorIdade = false;
		}
		if(pessoaDados.getTipoPessoa() == 1) {
			complementoAluno = true;
			menorIdade = true;
			complementoFuncionario = false;
		}
		if(pessoaDados.getTipoPessoa() == 2) {
			complementoFuncionario = true;
			complementoAluno = false;
			menorIdade = false;
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
	 * Metodo para validar o tipo de cadastro
	 * 
	 * */
	public void validaCadastro(){
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

	/*
	 * Metodo responsavel por carregar os combos da tela
	 * 
	 * */
	public void carregaCombos() throws SQLException{
		
		ParametrosServlet paramDados = new ParametrosServlet();
		
		comboEstadoCivil.addAll(paramDados.consultaEstaCivil());
		comboGrauInstrucao.addAll(paramDados.consultaGrauInstru());
		comboNacionalidade.addAll(paramDados.consultaNacionalidade());
		comboRaca.addAll(paramDados.consultaRaca());
		comboReligiao.addAll(paramDados.consultaReligiao());
		comboSituacaoEconomica.addAll(paramDados.consultaSituEconomica());
		comboZonaResidencial.addAll(paramDados.consultaRegiao());
		comboTipoDeficiencia.addAll(paramDados.consultaTipoDeficiencia());
		comboPais.addAll(paramDados.consultaPais());
		comboCargo.addAll(paramDados.consultaCargo());
		comboTipoLogradouro.addAll(paramDados.consultaTipoLogradouro());
	}
	
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
	/* GETTERS AND SETTER DE ATRIBUTOS OBJETOS */
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
	/* GETTERS AND SETTER DE ATRIBUTOS OBJETOS */
	/* ------------------------------------------------------------------------------------------------------------------------ */

		
	public Endereco getEnderecoDados() {
		return enderecoDados;
	}

	public void setEnderecoDados(Endereco enderecoDados) {
		this.enderecoDados = enderecoDados;
	}

	public Regiao getRegiaoDados() {
		return regiaoDados;
	}

	public void setRegiaoDados(Regiao regiaoDados) {
		this.regiaoDados = regiaoDados;
	}

	public Boolean getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(Boolean nomeMae) {
		this.nomeMae = nomeMae;
	}

	public Boolean getNomePai() {
		return nomePai;
	}

	public void setNomePai(Boolean nomePai) {
		this.nomePai = nomePai;
	}

	public Boolean getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(Boolean nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
	/* GETTERS AND SETTER DE PAIS ESTADO E CIDADE */
	
	/* PAIS */
	public List<SelectItem> getComboPais() {
		return comboPais;
	}

	public void setComboPais(List<SelectItem> comboPais) {
		this.comboPais = comboPais;
	}
	
	/* ESTADO */
	public List<SelectItem> getComboEstado() {
		comboEstado.clear();
		if(paisDados.getPkPais() !=null && !comboPais.isEmpty()) {
			
			ParametrosServlet paramDados = new ParametrosServlet();
			comboEstado.addAll(paramDados.consultaEstado(paisDados));
			
			return comboEstado;
		}
		return comboEstado;
	}

	public void setComboEstado(List<SelectItem> comboEstado) {
		this.comboEstado = comboEstado;
	}
	
	/* CIDADE */
	public List<SelectItem> getComboCidade() {
		comboCidade.clear();
		if(estadoDados.getPkEstado() != null && !comboEstado.isEmpty()){
			ParametrosServlet paramDados = new ParametrosServlet();
			comboCidade.addAll(paramDados.consultaCidade(estadoDados));
		}
		return comboCidade;
	}

	public void setComboCidade(List<SelectItem> comboCidade) {
		this.comboCidade = comboCidade;
	}
	/* GETTERS AND SETTER DE PAIS ESTADO E CIDADE */
	/* ------------------------------------------------------------------------------------------------------------------------ */
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
	/* GETTERS AND SETTER DE PARAMETROS DA TELA */
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

	public List<SelectItem> getComboZonaResidencial() {
		return comboZonaResidencial;
	}

	public void setComboZonaResidencial(List<SelectItem> comboZonaResidencial) {
		this.comboZonaResidencial = comboZonaResidencial;
	}

	public List<SelectItem> getComboCargo() {
		return comboCargo;
	}

	public void setComboCargo(List<SelectItem> comboCargo) {
		this.comboCargo = comboCargo;
	}

	public List<SelectItem> getComboRedeEnsino() {
		return comboRedeEnsino;
	}

	public void setComboRedeEnsino(List<SelectItem> comboRedeEnsino) {
		this.comboRedeEnsino = comboRedeEnsino;
	}

	public List<SelectItem> getComboUnidadeEscolar() {
		return comboUnidadeEscolar;
	}

	public void setComboUnidadeEscolar(List<SelectItem> comboUnidadeEscolar) {
		this.comboUnidadeEscolar = comboUnidadeEscolar;
	}

	public List<SelectItem> getComboTipoDeficiencia() {
		return comboTipoDeficiencia;
	}

	public void setComboTipoDeficiencia(List<SelectItem> comboTipoDeficiencia) {
		this.comboTipoDeficiencia = comboTipoDeficiencia;
	}
	
	public List<SelectItem> getComboGrauParentesco() {
		return comboGrauParentesco;
	}

	public void setComboGrauParentesco(List<SelectItem> comboGrauParentesco) {
		this.comboGrauParentesco = comboGrauParentesco;
	}
	/* GETTERS AND SETTER DE PARAMETROS DA TELA */
	/* ------------------------------------------------------------------------------------------------------------------------ */
	
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

	public Boolean getMenorIdade() {
		return menorIdade;
	}

	public void setMenorIdade(Boolean menorIdade) {
		this.menorIdade = menorIdade;
	}

	public UploadedFile getImagemResidencia() {
		return imagemResidencia;
	}

	public void setImagemResidencia(UploadedFile imagemResidencia) {
		this.imagemResidencia = imagemResidencia;
	}

	public UploadedFile getImagemAluno() {
		return imagemAluno;
	}

	public void setImagemAluno(UploadedFile imagemAluno) {
		this.imagemAluno = imagemAluno;
	}

	public UploadedFile getImagemCertNascimento() {
		return imagemCertNascimento;
	}

	public void setImagemCertNascimento(UploadedFile imagemCertNascimento) {
		this.imagemCertNascimento = imagemCertNascimento;
	}

	public List<SelectItem> getComboTipoLogradouro() {
		return comboTipoLogradouro;
	}

	public void setComboTipoLogradouro(List<SelectItem> comboTipoLogradouro) {
		this.comboTipoLogradouro = comboTipoLogradouro;
	}
}
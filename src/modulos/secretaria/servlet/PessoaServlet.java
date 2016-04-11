package modulos.secretaria.servlet;

import java.io.IOException;
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

import org.postgresql.util.Base64;
import org.primefaces.model.UploadedFile;

import modulos.secretaria.dao.AlunoDAO;
import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.om.Aluno;
import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Curso;
import modulos.secretaria.om.Endereco;
import modulos.secretaria.om.Estado;
import modulos.secretaria.om.EstadoCivil;
import modulos.secretaria.om.Etapa;
import modulos.secretaria.om.Fornecedor;
import modulos.secretaria.om.Funcionario;
import modulos.secretaria.om.GrauInstrucao;
import modulos.secretaria.om.Nacionalidade;
import modulos.secretaria.om.Pais;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.Raca;
import modulos.secretaria.om.RedeEnsino;
import modulos.secretaria.om.Regiao;
import modulos.secretaria.om.Religiao;
import modulos.secretaria.om.SituacaoEconomica;
import modulos.secretaria.om.TipoDeficiencia;
import modulos.secretaria.om.Turno;
import modulos.secretaria.om.UnidadeEscolar;
import modulos.secretaria.om.Usuario;
import modulos.sisEducar.om.ImagemBase64;
import modulos.sisEducar.sisEducarServlet.SisEducarServlet;
import modulos.sisEducar.utils.ConstantesSisEducar;

@ManagedBean(name="pessoaServlet")
@ViewScoped
public class PessoaServlet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final int CADASTRO_PESSOA = 0;
	public static final int CADASTRO_ALUNO = 1;
	public static final int CADASTRO_FUNCIONARIO = 2;
	
	/* Atributos */
	private Pessoa pessoaDados;
	private Aluno alunoDados;
	private Fornecedor fornecedorDados;
	private Funcionario funcionarioDados;
	private Pais paisDados;
	private Estado estadoDados;
	private Cidade cidadeDados;
	private Endereco enderecoDados;
	private Nacionalidade nacionalidadeDados;
	private Raca racaDados;
	private EstadoCivil estaCivilDados;
	private GrauInstrucao grauInstruDados;
	private SituacaoEconomica situEconomicaDados;
	private Religiao religiaoDados;
	private Regiao regiaoDados;
	private ParametrosServlet paramDados;
	private Usuario usuarioLogado;
	private RedeEnsino redeEnsinoDados;
	private UnidadeEscolar unidadeEscolarDados;
	private Etapa etapaDados;
	private Curso cursoDados;
	private Turno turnoDados;
	private TipoDeficiencia tipoDeficienciaDados;
	
	

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
	
	/* Combo com valores de ETAPA ESCOLAR */
	private List<SelectItem> comboEtapaEscolar;
	
	/* Combo com valores de CURSO ESCOLAR */
	private List<SelectItem> comboCursoEscolar;
	
	/* Combo com valores de TURNO ESCOLAR */
	private List<SelectItem> comboTurnoEscolar;
	
	/* Componente */
	private Boolean nomeMae;
	
	/* Componente */
	private Boolean nomePai;
	
	/* Componente */
	private Boolean nomeResponsavel;
	
	/* Componente para validar idade da pessoa */
	private Boolean menorIdade;

	/* Metodo Construtor */
	public PessoaServlet() throws SQLException {
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
		if(this.paramDados == null) {
			this.paramDados = new ParametrosServlet();
		}
		if(this.redeEnsinoDados == null) {
			this.redeEnsinoDados = new RedeEnsino();
		}
		if(this.unidadeEscolarDados == null) {
			this.unidadeEscolarDados = new UnidadeEscolar();
		}
		if(this.etapaDados == null) {
			this.etapaDados = new Etapa();
		}
		if(this.cursoDados == null) {
			this.cursoDados = new Curso();
		}
		if(this.turnoDados == null) {
			this.turnoDados = new Turno();
		}
		 /* testando cmite parcial */
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
		
		usuarioLogado = (Usuario) new SisEducarServlet().getSessionObject(ConstantesSisEducar.USUARIO_LOGADO);
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
			if(idade < 0 || idade > 100) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Informe uma data valida.",null));
			} else {
				if( idade < 18 ){
					menorIdade = true;
				}else{
					menorIdade = false;
				}
			}
		}
	}
	
	/*
	 * Metodo para salvar o cadastro de Pessoa
	 * 
	 * */
	public String salvarCadastroPessoa(){
		try {
			Pessoa pessoaDadosFinal = new Pessoa();
			uploadImagens();
			if( validaDadosPessoa() == true ) {
				if(usuarioLogado!=null && usuarioLogado.getFkMunicipioCliente()!=null)
				{
					pessoaDadosFinal.setFkMunicipioCliente(usuarioLogado.getFkMunicipioCliente());
				}
				
				pessoaDadosFinal.setTipoPessoa(pessoaDados.getTipoPessoa());
				pessoaDadosFinal.setNome(pessoaDados.getNome());
				pessoaDadosFinal.setCpf(pessoaDados.getCpf());
				pessoaDadosFinal.setRg(pessoaDados.getRg());
				pessoaDadosFinal.setDataNascimento(pessoaDados.getDataNascimento());
				pessoaDadosFinal.setSexo(pessoaDados.getSexo());
				pessoaDadosFinal.setEmail(pessoaDados.getEmail());
				pessoaDadosFinal.setTelefoneResidencial(pessoaDados.getTelefoneResidencial());
				pessoaDadosFinal.setTelefoneCelular(pessoaDados.getTelefoneCelular());
				
				enderecoDados.setCidade(cidadeDados);
				pessoaDadosFinal.setEndereco(enderecoDados);
				
				pessoaDadosFinal.setNacionalidade(nacionalidadeDados);
				pessoaDadosFinal.setRaca(racaDados);
				pessoaDadosFinal.setEstadoCivil(estaCivilDados);
				pessoaDadosFinal.setGrauInstrucao(grauInstruDados);
				pessoaDadosFinal.setSituacaoEconomica(situEconomicaDados);
				pessoaDadosFinal.setReligiao(religiaoDados);
				pessoaDadosFinal.setRegiao(regiaoDados);
				pessoaDadosFinal.setPais(paisDados);				
				pessoaDadosFinal.setEstado(estadoDados);				
				
				new PessoaDAO().salvarCadastroPessoa(pessoaDadosFinal);
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"Cadastro Realizado com sucesso",null));
				limparFormulario();
			}
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao cadastrar",null));
			return null;
		}
		
		return null;
	}
	
	/*
	 * Metodo para salvar o cadastro de Aluno
	 * 
	 * */
	public String salvarCadastroAluno(){
		Aluno alunoDadosFinal = new Aluno();
		
		if(alunoDados != null) {
			alunoDadosFinal.setRm(alunoDados.getRm());
			alunoDadosFinal.setRa(alunoDados.getRa());
		}
		
		if(redeEnsinoDados.getPkRedeEnsino() != null ) {
			alunoDadosFinal.setRedeEnsino(redeEnsinoDados);
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"Seleciona uma REDE DE ENSINO.",null));
			return null;
		}
		
		if(unidadeEscolarDados.getPkUnidadeEscolar() != null) {
			alunoDadosFinal.setUnidadeEscolar(unidadeEscolarDados);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Selecione uma UNIDADE ESCOLAR.",null));
			return null;
		}
		
		if(cursoDados.getPkCurso() != null) {
			alunoDadosFinal.setCurso(cursoDados);
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Selecione um CURSO.",null));
			return null;
		}
		
		if(etapaDados.getPkEtapa() != null) {
			alunoDadosFinal.setEtapa(etapaDados);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Selecione uma ETAPA.",null));
			return null;
		}
		
		if(turnoDados.getPkTurno() != null) {
			alunoDadosFinal.setTurno(turnoDados);
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Selecione um TURNO.",null));
			return null;
		}
		
		
		return null;
	}
	
	/*
	 * Metodo para salvar o cadastro de Funcionario
	 * 
	 * */
	public String salvarCadastroFuncionario(){
		return null;
	}
	
	public void uploadImagens(){
		ImagemBase64 imagemAluno64 = new ImagemBase64();
		ImagemBase64 imagemCertNasci64 = new ImagemBase64();
		ImagemBase64 imagemResidencia64 = new ImagemBase64();
		
		converteBase64(imagemAluno, imagemAluno64);
		converteBase64(imagemCertNascimento, imagemCertNasci64);
		converteBase64(imagemResidencia, imagemResidencia64);
	}
	
	public void converteBase64(UploadedFile imagemConverte, ImagemBase64 imagem) {
    	String formato = imagemConverte.getContentType();
    	String nome = imagemConverte.getFileName();
    	byte[] imageAsByte = new byte[(int) imagemConverte.getSize()];
    	try {
    		imagemConverte.getInputstream().read(imageAsByte);
    		String base64AsString = new String(Base64.encodeBytes(imageAsByte));
    		imagem.setB64(base64AsString);
    		imagem.setTipo(formato);
    		imagem.setNome(nome);
    		
    		System.out.println(imagem.getB64());
    		System.out.println(imagem.getNome());
    		System.out.println(imagem.getTipo());
    	}catch (IOException e) {
    		e.printStackTrace(); 
    	}
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
		if(pessoaDados.getCpfMae() != null && pessoaDados.getCpfMae() != 0){
			try {
				Long cpfMae = pessoaDados.getCpfMae();
				AlunoDAO alunoDAO = new AlunoDAO();
				
				String nome = alunoDAO.consultaNomeResponsavel(cpfMae); 
				
				if(nome != null && !nome.equals("")){
					pessoaDados.setNomeMae(nome);
					nomeMae = true;
				}else{
					pessoaDados.setNomeMae(null);
					nomeMae = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"CPF não encontrado, favor informar o nome.", null));
				}
			} catch (SQLException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Erro ao consultar o CPF informado", null));
			}
		}else{
			pessoaDados.setNomeMae(null);
			nomeMae = false;
		}
	}
	
	/*
	 * Metodo responsavel por validar o nome do PAI do aluno
	 * 
	 * */
	public void validaNomePai(){
		if(pessoaDados.getCpfPai() != null && pessoaDados.getCpfPai() != 0 ){
			try {
				Long cpfPai = pessoaDados.getCpfPai();
				AlunoDAO alunoDAO = new AlunoDAO();
				
				String nome = alunoDAO.consultaNomeResponsavel(cpfPai); 
				if(nome != null && !nome.equals("")){
					pessoaDados.setNomePai(nome);
					nomePai = true;
				}else{
					pessoaDados.setNomePai(null);
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
		if(pessoaDados.getCpfResponsavel() != null && pessoaDados.getCpfResponsavel() != 0){
			try {
				Long cpfResponsavel = pessoaDados.getCpfResponsavel();
				AlunoDAO alunoDAO = new AlunoDAO();
				
				String nome = alunoDAO.consultaNomeResponsavel(cpfResponsavel); 
				if(nome != null && !nome.equals("")){
					pessoaDados.setNomeResponsavel(nome);
					nomeResponsavel = true;
				}else{
					pessoaDados.setNomePai(null);
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
		if(pessoaDados.getTipoPessoa() == CADASTRO_PESSOA && pessoaDados != null) {
			salvarCadastroPessoa();
		}
		if(pessoaDados.getTipoPessoa() == CADASTRO_ALUNO && pessoaDados != null) {
			salvarCadastroPessoa();
			salvarCadastroAluno();
		}
		if(pessoaDados.getTipoPessoa() == CADASTRO_FUNCIONARIO && pessoaDados != null) {
			salvarCadastroPessoa();
			salvarCadastroFuncionario();
		}
	}

	/*
	 * Metodo responsavel por carregar os combos da tela
	 * 
	 * */
	public void carregaCombos() throws SQLException{
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
	
	public Boolean validaDadosPessoa(){
		if( pessoaDados.getNome() == null || pessoaDados.getNome().equals("") ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"O NOME deve ser preenchido.",null));
			pessoaDados.setNome(null);
			return false;
		}

		if(pessoaDados.getDataNascimento() == null ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"A DATA DE NASCIMENTO deve ser preenchida.",null));
			pessoaDados.setDataNascimento(null);
			return false;
		}	
		
		if( menorIdade == false  ) {
			if(pessoaDados.getCpf() == null  || pessoaDados.getCpf() == 0 ) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"O CPF deve ser preenchido.",null));
				pessoaDados.setCpf(null);
				return false;
			}
		}
				
		if( pessoaDados.getSexo() == null ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"O SEXO deve ser informado.",null));
			return false;
		}
		
		if( nacionalidadeDados.getPkNacionalidade() == null ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"A NACIONALIDADE deve ser informada.",null));
			return false;
		}
		
		if( racaDados.getPkRaca() == null ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"A RAÇA deve ser informada.",null));
			return false;
		}
		
		if( estaCivilDados.getPkEstadoCivil() == null ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"O ESTADO CIVIL deve ser informado.",null));
			return false;
		}
		
		if( grauInstruDados.getPkGrauInstrucao() == null ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"O GRAU DE INSTRUÇÃO deve ser informado.",null));
			return false;
		}
		
		if( situEconomicaDados.getPkSituacaoEconomica() == null ){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"A SITUAÇÃO ECONÔMICA deve ser informada.",null));
			return false;
		}
		
		if( religiaoDados.getPkReligiao() == null ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"A RELIGIÃO deve ser informada.",null));
			return false;
		}
		
		if( paisDados.getPkPais() == null ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"O PAÍS deve ser informado.",null));
			return false;
		}
		
		if( estadoDados.getPkEstado() == null ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"O ESTADO deve ser informado.",null));
			return false;
		}
		
		if( cidadeDados.getPkCidade() == null ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"O MUNICÍPIO deve ser informado.",null));
			return false;
		}
		
		if( enderecoDados.getCep() == null || enderecoDados.getCep().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"O CEP deve ser preenchido.",null));
			return false;
		}
		
		if( enderecoDados.getLogradouro() == null || enderecoDados.getCep().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"O LOGRADOURO deve ser preenchido.",null));
			return false;
		}
		
		if( enderecoDados.getNumero() == null || enderecoDados.getNumero().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"O NÚMERO deve ser preenchido.",null));
			return false;
		}
		
		if( enderecoDados.getBairro() == null || enderecoDados.getBairro().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"O BAIRRO deve ser preenchido.",null));
			return false;
		}
		
		if( regiaoDados.getPkRegiao() == null ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"A ZONA RESIDENCIAL deve ser informada.",null));
			return false;
		}
		
		if( pessoaDados.getTelefoneResidencial() == null || pessoaDados.getTelefoneResidencial().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"O TELEFONE DE CONTATO deve ser preenchido.",null));
			return false;
		}
		
		if( pessoaDados.getTelefoneCelular() == null || pessoaDados.getTelefoneCelular().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"O TELEFONE CELULAR deve ser preenchido.",null));
			return false;
		}
		if( menorIdade == true ) {
			if( pessoaDados.getCpfMae() == null || pessoaDados.getCpfMae() == 0 
					|| pessoaDados.getCpfResponsavel() == null || pessoaDados.getCpfResponsavel() == 0	) {
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"O NOME DA MÃE ou de algum RESPONSAVEL deve ser informado.",null));
				return false;
			}
		}
		
		return true;
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
	
	public ParametrosServlet getParamDados() {
		return paramDados;
	}

	public void setParamDados(ParametrosServlet paramDados) {
		this.paramDados = paramDados;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public RedeEnsino getRedeEnsinoDados() {
		return redeEnsinoDados;
	}

	public void setRedeEnsinoDados(RedeEnsino redeEnsinoDados) {
		this.redeEnsinoDados = redeEnsinoDados;
	}

	public UnidadeEscolar getUnidadeEscolarDados() {
		return unidadeEscolarDados;
	}

	public void setUnidadeEscolarDados(UnidadeEscolar unidadeEscolarDados) {
		this.unidadeEscolarDados = unidadeEscolarDados;
	}

	public Etapa getEtapaDados() {
		return etapaDados;
	}

	public void setEtapaDados(Etapa etapaDados) {
		this.etapaDados = etapaDados;
	}

	public Curso getCursoDados() {
		return cursoDados;
	}

	public void setCursoDados(Curso cursoDados) {
		this.cursoDados = cursoDados;
	}

	public Turno getTurnoDados() {
		return turnoDados;
	}

	public void setTurnoDados(Turno turnoDados) {
		this.turnoDados = turnoDados;
	}
	
	public TipoDeficiencia getTipoDeficienciaDados() {
		return tipoDeficienciaDados;
	}

	public void setTipoDeficienciaDados(TipoDeficiencia tipoDeficienciaDados) {
		this.tipoDeficienciaDados = tipoDeficienciaDados;
	}
	/* GETTERS AND SETTER DE ATRIBUTOS OBJETOS */
	/* ------------------------------------------------------------------------------------------------------------------------ */

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
		if(paisDados.getPkPais() != null && !comboPais.isEmpty()) {
			
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
	
	public List<SelectItem> getComboTipoLogradouro() {
		return comboTipoLogradouro;
	}
	
	public void setComboTipoLogradouro(List<SelectItem> comboTipoLogradouro) {
		this.comboTipoLogradouro = comboTipoLogradouro;
	}
/* ------------------------------------------------------------------------------------------------------------------------ */
/* Informações referentes ao dados do aluno */	
	public List<SelectItem> getComboRedeEnsino() {
		comboRedeEnsino.clear();
		if(comboRedeEnsino == null || comboRedeEnsino.isEmpty()) {			
			comboRedeEnsino.addAll(paramDados.consultaRedeEnsino());
		}
		return comboRedeEnsino;
	}

	public void setComboRedeEnsino(List<SelectItem> comboRedeEnsino) {
		this.comboRedeEnsino = comboRedeEnsino;
	}

	public List<SelectItem> getComboUnidadeEscolar() {
		comboUnidadeEscolar.clear();
		if( redeEnsinoDados.getPkRedeEnsino() != null && !comboRedeEnsino.isEmpty() ) {
			comboUnidadeEscolar.addAll(paramDados.consultaUnidadeEscolar(redeEnsinoDados));
		}		
		return comboUnidadeEscolar;
	}

	public void setComboUnidadeEscolar(List<SelectItem> comboUnidadeEscolar) {
		this.comboUnidadeEscolar = comboUnidadeEscolar;
	}
	
	public List<SelectItem> getComboCursoEscolar() {
		comboCursoEscolar.clear();
		if(unidadeEscolarDados.getPkUnidadeEscolar() != null && !comboUnidadeEscolar.isEmpty()) {
			comboCursoEscolar.addAll(paramDados.consultaCursoEscolar(unidadeEscolarDados));
		}
		return comboCursoEscolar;
	}

	public void setComboCursoEscolar(List<SelectItem> comboCursoEscolar) {
		this.comboCursoEscolar = comboCursoEscolar;
	}
	
	public List<SelectItem> getComboEtapaEscolar() {
		comboEtapaEscolar.clear();
		if(cursoDados.getPkCurso() != null && !comboCursoEscolar.isEmpty()) {
			comboEtapaEscolar.addAll(paramDados.consultaEtapaEscolar(cursoDados));
		}
		return comboEtapaEscolar;
	}

	public void setComboEtapaEscolar(List<SelectItem> comboEtapaEscolar) {
		this.comboEtapaEscolar = comboEtapaEscolar;
	}

	public List<SelectItem> getComboTurnoEscolar() {
		comboTurnoEscolar.clear();
		if(etapaDados.getPkEtapa() != null && !comboEtapaEscolar.isEmpty()) {
			comboTurnoEscolar.addAll(paramDados.consultaTurnoEscolar(etapaDados));
		}
		return comboTurnoEscolar;
	}

	public void setComboTurnoEscolar(List<SelectItem> comboTurnoEscolar) {
		this.comboTurnoEscolar = comboTurnoEscolar;
	}
	
	public List<SelectItem> getComboTipoDeficiencia() {
		comboTipoDeficiencia.clear();
		if(comboTipoDeficiencia == null || comboTipoDeficiencia.isEmpty()) {
			comboTipoDeficiencia.addAll(paramDados.consultaTipoDeficiencia());
		}
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
	
	public Boolean getComplementoAluno() {
		return complementoAluno;
	}

	public void setComplementoAluno(Boolean complementoAluno) {
		this.complementoAluno = complementoAluno;
	}
	
	public Boolean getAlunoDeficiente() {
		return alunoDeficiente;
	}

	public void setAlunoDeficiente(Boolean alunoDeficiente) {
		this.alunoDeficiente = alunoDeficiente;
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

	public UploadedFile getImagemCertNascimento() {
		return imagemCertNascimento;
	}

	public void setImagemCertNascimento(UploadedFile imagemCertNascimento) {
		this.imagemCertNascimento = imagemCertNascimento;
	}	
	public UploadedFile getImagemAluno() {
		return imagemAluno;
	}

	public void setImagemAluno(UploadedFile imagemAluno) {
		this.imagemAluno = imagemAluno;
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
	/* Informações referentes ao dados do aluno */	
	/* ------------------------------------------------------------------------------------------------------------------------ */
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
	/* Informações referentes ao dados do funcionario */
	public Boolean getComplementoFuncionario() {
		return complementoFuncionario;
	}

	public void setComplementoFuncionario(Boolean complementoFuncionario) {
		this.complementoFuncionario = complementoFuncionario;
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
	
	public List<SelectItem> getComboCargo() {
		return comboCargo;
	}

	public void setComboCargo(List<SelectItem> comboCargo) {
		this.comboCargo = comboCargo;
	}
	/* Informações referentes ao dados do funcionario */
	/* ------------------------------------------------------------------------------------------------------------------------ */
/* GETTERS AND SETTER DE PARAMETROS DA TELA */
/* ------------------------------------------------------------------------------------------------------------------------ */
	
	

	

	

	

	

	
	
}
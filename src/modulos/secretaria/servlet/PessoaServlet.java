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
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import modulos.secretaria.dao.AlunoDAO;
import modulos.secretaria.dao.ContatoDAO;
import modulos.secretaria.dao.EnderecoDAO;
import modulos.secretaria.dao.FuncionarioDAO;
import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.om.Aluno;
import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Contato;
import modulos.secretaria.om.Curso;
import modulos.secretaria.om.Endereco;
import modulos.secretaria.om.Estado;
import modulos.secretaria.om.EstadoCivil;
import modulos.secretaria.om.Etapa;
import modulos.secretaria.om.Fornecedor;
import modulos.secretaria.om.Funcionario;
import modulos.secretaria.om.GrauInstrucao;
import modulos.secretaria.om.GrauParentesco;
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
import modulos.sisEducar.servlet.SisEducarServlet;
import modulos.sisEducar.utils.ConstantesSisEducar;
import modulos.sisEducar.utils.Logs;

@ManagedBean(name="pessoaServlet")
@ViewScoped
public class PessoaServlet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final int CADASTRO_PESSOA = 0;
	public static final int CADASTRO_ALUNO = 1;
	public static final int CADASTRO_FUNCIONARIO = 2;
	public static Boolean cadastrar = true;
	public static Boolean atualizar = false;
	
	/* Atributos */
	private Pessoa pessoaDados;
	private Aluno alunoDados;
	private Fornecedor fornecedorDados;
	private Funcionario funcionarioDados;
	private Contato contatoDados;
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
	private GrauParentesco grauParentescoDados;
	private PessoaDAO pessoaDAO;
	private AlunoDAO alunoDAO;
	private FuncionarioDAO funcionarioDAO;
	private EnderecoDAO enderecoDAO;
	private ContatoDAO contatoDAO;
	private ImagemBase64 imagem64;
	private Estado estadoNascimentoDados;
	private Cidade cidadeNascimentoDados;
	private Pessoa pessoaSelecionada;
			
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
	
	/* Combo com valores de SITUAÇÃO ECÔNIMCA */
	private List<SelectItem> comboSituacaoEconomica;
	
	/* Combo com valores de RELIGIÃO */
	private List<SelectItem> comboReligiao;
	
	/* Combo com valores de ZONA RESIDENCIAL */
	private List<SelectItem> comboZonaResidencial;
	
	/* Combo com valores de TIPO DE DEFICENCIA*/
	private List<SelectItem> comboTipoDeficiencia;
	
	/* Combo com valores de PAÃ�S */
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
	
	private List<SelectItem> comboEstadoNascimento;
	
	private List<SelectItem> comboCidadeNascimento;
	
	/* Componente */
	private Boolean nomeMae;
	
	/* Componente */
	private Boolean nomePai;
	
	/* Componente */
	private Boolean nomeResponsavel;
	
	/* Componente para validar idade da pessoa */
	private Boolean menorIdade;
	
	/* Componenete Tabela de consutal */
	private HtmlDataTable dataTable;
	
	/* Lista de pessoas cadastradas */
	private List<Pessoa> listaConsultaPessoa;
	
	private Part fotoAluno;
	
	private Part copiaCertidao;
	
	private Part copiaEndereco;
	
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
		if(this.contatoDados == null) {
			this.contatoDados = new Contato();
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
		if(this.grauParentescoDados == null) {
			this.grauParentescoDados = new GrauParentesco();
		}
		if(this.pessoaDAO == null) {
			this.pessoaDAO = new PessoaDAO();
		}
		if(this.alunoDAO == null) {
			this.alunoDAO = new AlunoDAO();
		}
		if(this.funcionarioDAO == null) {
			this.funcionarioDAO = new FuncionarioDAO();
		}
		if(this.enderecoDAO == null) {
			this.enderecoDAO = new EnderecoDAO();
		}
		if(this.contatoDAO == null) {
			this.contatoDAO = new ContatoDAO();
		}
		if(this.dataTable == null) {
			this.dataTable = new HtmlDataTable();
		}
		if(this.estadoNascimentoDados == null) {
			this.estadoNascimentoDados = new Estado();
		}
		if(this.cidadeNascimentoDados == null) {
			this.cidadeNascimentoDados = new Cidade();
		}
		if(this.pessoaSelecionada == null) {
			this.pessoaSelecionada = new Pessoa();
		}
		
		 /* testando cmite parcial */
		pessoaDados.setTipoPessoa(0);
		comboCargo = new ArrayList<SelectItem>();
		comboEstadoCivil = new ArrayList<SelectItem>();
		comboGrauInstrucao = new ArrayList<SelectItem>();
		comboNacionalidade = new ArrayList<SelectItem>();
		comboRaca = new ArrayList<SelectItem>();
		comboReligiao = new ArrayList<SelectItem>();
		comboSituacaoEconomica = new ArrayList<SelectItem>();
		comboZonaResidencial = new ArrayList<SelectItem>();
		comboGrauParentesco = new ArrayList<SelectItem>();
		comboTipoDeficiencia = new ArrayList<SelectItem>();
		comboPais = new ArrayList<SelectItem>();
		comboEstado = new ArrayList<SelectItem>();
		comboCidade = new ArrayList<SelectItem>();
		comboRedeEnsino = new ArrayList<SelectItem>();
		comboUnidadeEscolar = new ArrayList<SelectItem>();
		comboCursoEscolar = new ArrayList<SelectItem>();
		comboEtapaEscolar = new ArrayList<SelectItem>();
		comboTurnoEscolar = new ArrayList<SelectItem>();
		comboEstadoNascimento = new ArrayList<SelectItem>();
		comboCidadeNascimento = new ArrayList<SelectItem>();
		
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

	/*
	 * Metodo para salvar o cadastro de Pessoa
	 * 
	 * */
	public String salvarCadastroPessoa() {
		try {	
			/* Objeto com os atributos de contato da pessoa */
			contatoDados = contatoDAO.salvarContatoPessoa(contatoDados);
			if(contatoDados.getPkContato() == null ){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"Erro",null));
				return null;
			}
			 
			/* Objeto com os atributos de endereco da pessoa */
			enderecoDados.setCidade(cidadeDados);
			enderecoDados.setTipo("Residencial");
			enderecoDados.setRegiao(regiaoDados);
			enderecoDados.setFkMunicipioCliente(usuarioLogado.getFkMunicipioCliente());
			enderecoDados = enderecoDAO.salvarEnderecoPessoa(enderecoDados);
			if(enderecoDados.getPkEndereco() == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"Erro",null));
				return null;
			}
			
			/* Objeto com os atributos de da pessoa */
			if(usuarioLogado!=null && usuarioLogado.getFkMunicipioCliente()!=null)
			{
				pessoaDados.setFkMunicipioCliente(usuarioLogado.getFkMunicipioCliente());
			}
			pessoaDados.setNacionalidade(nacionalidadeDados);
			pessoaDados.setRaca(racaDados);
			pessoaDados.setEstadoCivil(estaCivilDados);
			pessoaDados.setGrauInstrucao(grauInstruDados);
			pessoaDados.setSituacaoEconomica(situEconomicaDados);
			pessoaDados.setReligiao(religiaoDados);
			pessoaDados.setGrauParentesco(grauParentescoDados);
			pessoaDados.setEndereco(enderecoDados);
			pessoaDados.setContato(contatoDados);
			pessoaDados.setStatus(ConstantesSisEducar.STATUS_ATIVO);
			
			pessoaDados = pessoaDAO.salvarCadastroPessoa(pessoaDados);
			
			if( pessoaDados.getPkPessoa() != null ) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"Cadastro Realizado com sucesso",null));
			} else {
				if(contatoDados.getPkContato() != null || contatoDados != null) {
					contatoDAO.deletarContato(contatoDados.getPkContato());
				}
				if(enderecoDados.getPkEndereco() != null || enderecoDados != null){
					enderecoDAO.deletarEndereco(enderecoDados.getPkEndereco());
				}
				if(pessoaDados.getPkPessoa() != null || pessoaDados != null ) {
					pessoaDados.getPkPessoa();
				}
			}
		} catch (Exception e) {
			if(contatoDados.getPkContato() != null || contatoDados != null) {
				contatoDAO.deletarContato(contatoDados.getPkContato());
			}
			if(enderecoDados.getPkEndereco() != null || enderecoDados != null){
				enderecoDAO.deletarEndereco(enderecoDados.getPkEndereco());
			}
			if(pessoaDados.getPkPessoa() != null || pessoaDados != null ) {
				pessoaDados.getPkPessoa();
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao realizar cadastro contate o administrador!",null));
			return null;
		}
		return null;
	}
	
	/*
	 * Metodo para salvar o cadastro de Aluno
	 * 
	 * */
	public String salvarCadastroAluno(){
		if(pessoaDados.getPkPessoa() != null ){
			alunoDados.setRedeEnsino(redeEnsinoDados);
			alunoDados.setUnidadeEscolar(unidadeEscolarDados);
			alunoDados.setEtapa(etapaDados);
			alunoDados.setCurso(cursoDados);
			alunoDados.setTurno(turnoDados);
			alunoDados.setPessoa(pessoaDados);
			
			alunoDAO.salvarCadastroAluno(alunoDados);
			
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao realizar o cadastro.",null));
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
	
	public Boolean validaDadosPessoa(){
		if( pessoaDados.getNome() == null || pessoaDados.getNome().equals("") ) {
			Logs.addWarning("O NOME deve ser preenchido.", null);
			return false;
		}

		if(pessoaDados.getDataNascimento() == null ) {
			Logs.addWarning("A DATA DE NASCIMENTO deve ser preenchida.", null);
			pessoaDados.setDataNascimento(null);
			return false;
		}	
		
		if( menorIdade == false  ) {
			if(pessoaDados.getCpf() == null  || pessoaDados.getCpf() == 0 ) {
				Logs.addWarning("O CPF deve ser preenchido.", null);
				pessoaDados.setCpf(null);
				return false;
			}
			if(pessoaDados.getRg() == null  || pessoaDados.getRg().equals("") ) {
				Logs.addWarning("O RG deve ser preenchido.", null);
				pessoaDados.setCpf(null);
				return false;
			}
		}
				
		if( pessoaDados.getSexo() == null ) {
			Logs.addWarning("O SEXO deve ser informado.", null);
			return false;
		}
		
		if( nacionalidadeDados.getPkNacionalidade() == null ) {
			Logs.addWarning("A NACIONALIDADE deve ser informada.", null);
			return false;
		}
		
		if( racaDados.getPkRaca() == null ) {
			Logs.addWarning("A RAÇA deve ser informada.", null);
			return false;
		}
		
		if( estaCivilDados.getPkEstadoCivil() == null ) {
			Logs.addWarning("O ESTADO CIVIL deve ser informado.", null);
			return false;
		}
		
		if( grauInstruDados.getPkGrauInstrucao() == null ) {
			Logs.addWarning("O GRAU DE INSTRUÇÃO deve ser informado.", null);
			return false;
		}
		
		if( situEconomicaDados.getPkSituacaoEconomica() == null ){
			Logs.addWarning("A SITUAÇÃO ECONÔMICA deve ser informada.", null);
			return false;
		}
		
		if( religiaoDados.getPkReligiao() == null ) {
			Logs.addWarning("A RELIGIÃO deve ser informada.", null);
			return false;
		}
		
		if( paisDados.getPkPais() == null ) {
			Logs.addWarning("O PAÍS deve ser informado.", null);
			return false;
		}
		
		if( estadoDados.getPkEstado() == null ) {
			Logs.addWarning("O ESTADO deve ser informado.", null);
			return false;
		}
		
		if( cidadeDados.getPkCidade() == null ) {
			Logs.addWarning("O MUNICÍ�PIO deve ser informado.", null);
			return false;
		}
		
		if( enderecoDados.getCep() == null || enderecoDados.getCep().equals("")) {
			Logs.addWarning("O CEP deve ser preenchido.", null);
			return false;
		}
		
		if( enderecoDados.getLogradouro() == null || enderecoDados.getCep().equals("")) {
			Logs.addWarning("O LOGRADOURO deve ser preenchido.", null);
			return false;
		}
		
		if( enderecoDados.getNumero() == null || enderecoDados.getNumero().equals("")) {
			Logs.addWarning("O NÚMERO deve ser preenchido.", null);
			return false;
		}
		
		if( enderecoDados.getBairro() == null || enderecoDados.getBairro().equals("")) {
			Logs.addWarning("O BAIRRO deve ser preenchido.", null);
			return false;
		}
		
		if( regiaoDados.getPkRegiao() == null ) {
			Logs.addWarning("A ZONA RESIDENCIAL deve ser informada.", null);
			return false;
		}
		
		if( contatoDados.getTelResidencial() == null || contatoDados.getTelResidencial().equals("")) {
			Logs.addWarning("O TELEFONE DE CONTATO deve ser preenchido.", null);
			return false;
		}
		
		if( contatoDados.getTelCelular() == null || contatoDados.getTelCelular().equals("")) {
			Logs.addWarning("O TELEFONE CELULAR deve ser preenchido.", null);
			return false;
		}
		if( menorIdade == true ) {
			if( (pessoaDados.getCpfMae() == null || pessoaDados.getCpfMae() == 0) && 
					 (pessoaDados.getCpfResponsavel() == null || pessoaDados.getCpfResponsavel() == 0)) {
				Logs.addWarning("O CPF DA MÃE ou de algum RESPONSAVEL deve ser informado.", null);
				return false;
			}
			if( (pessoaDados.getNomeMae() == null || pessoaDados.getNomeMae().equals("")) && 
					 (pessoaDados.getNomeResponsavel() == null || pessoaDados.getNomeResponsavel().equals(""))) {
				Logs.addWarning("O NOME DA MÃE ou de algum RESPONSAVEL deve ser informado.", null);
				return false;
			}
		}
		
		return true;
	}
	
	public Boolean validaDadosAluno(){
		if( alunoDados.getRm() == null && alunoDados.getRm().equals("")) {
			Logs.addWarning("O RM do aluno deve ser informado.", null);
			return false;
		}
		if( alunoDados.getRa() == null && alunoDados.getRa().equals("")) {
			Logs.addWarning("O RA do aluno deve ser informado.",null);
			return false;
		}
		if( alunoDados.getCodigoInep() == null && alunoDados.getCodigoInep().equals("")) {
			Logs.addWarning("O CÓDIGO DO INEP do aluno deve ser informado.",null);
			return false;
		}
		if( redeEnsinoDados.getPkRedeEnsino() == null) {
			Logs.addWarning("A REDE DE ENSINO deve ser informada.",null);
			return false;
		}
		if( unidadeEscolarDados.getPkUnidadeEscolar() == null) {
			Logs.addWarning("A UNIDADE ESCOLAR deve ser informada.",null);
			return false;
		}
		if( cursoDados.getPkCurso() == null) {
			Logs.addWarning("O CURSO deve ser informado.",null);
			return false;
		}
		if( etapaDados.getPkEtapa() == null) {
			Logs.addWarning("A ETAPA deve ser informado.",null);
			return false;
		}
		if( turnoDados.getPkTurno() == null) {
			Logs.addWarning("O TURNO deve ser informado.",null);
			return false;
		}
		if(alunoDeficiente) {
			if(tipoDeficienciaDados.getPkTipoDeficiencia() == null ) {
				Logs.addWarning("O TIPO DE DEFICIENCIA deve ser informado.",null);
				return false;
			}
		}
		if(fotoAluno == null){
			Logs.addWarning("Selecione a FOTO do aluno.",null);
			return false;
		}
		if(copiaCertidao == null) {
			Logs.addWarning("Selecione uma copia da CERTIDÃO DE NASCIMENTO.",null);
			return false;
		}
		if(copiaEndereco == null) {
			Logs.addWarning("Selecione uma copia do COMPROVANTE DE RESIDÊNCIA.",null);
			return false;
		}
		return false;
	}
	
	/*
	 * Metodo para validar se ja existe o CPF cadastrado
	 * */
	public void verificaCadastro() {
		try {
			if(pessoaDados.getCpf() != null && pessoaDados.getCpf() != 0) {
				if ( pessoaDAO.obtemUnicoPessoaSimples(pessoaDados.getCpf().toString()).getCpf() != null ){
					pessoaDados.setCpf(null);
					Logs.addWarning("CPF já cadastrado.",null);
				}
			}
		} catch (SQLException e) {
			Logs.addError(e.toString(),null);
		}
	}
	
	/*
	 * Metodo para validar idade da pessoa cadastrada
	 * */
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
				Logs.addWarning("Informe uma data valida.",null);
			} else {
				if( idade < 18 ){
					menorIdade = true;
				}else{
					menorIdade = false;
				}
			}
		} else {
			menorIdade = false;
		}
	}
	
	/*
	 * Metodo para limpar o formulario apos cadastro realizado
	 * 
	 * */
	public void limparFormulario(){
		
	}
	
	public void consultaCadastro() {
		listaConsultaPessoa = new ArrayList<Pessoa>();
		
		listaConsultaPessoa = pessoaDAO.obtemTodos();
	}
	
	public void editarCadastro() {
		cadastrar = false;
		atualizar = true;
		
		pessoaDados = new Pessoa();
		enderecoDados = new Endereco();
		contatoDados = new Contato();
		pessoaSelecionada = (Pessoa) dataTable.getRowData();
		
		if(pessoaSelecionada != null && pessoaSelecionada.getPkPessoa() != null) {
			pessoaDados = pessoaSelecionada;
			
			pessoaDados = pessoaDAO.consultaCadastroPessoa(pessoaDados.getPkPessoa());
			enderecoDados = enderecoDAO.consultaEnderecoPessoa(pessoaDados.getEndereco().getPkEndereco());
			contatoDados = contatoDAO.buscarContato(pessoaDados.getContato().getPkContato());
			
			nacionalidadeDados = pessoaDados.getNacionalidade();
			racaDados = pessoaDados.getRaca();
			estaCivilDados = pessoaDados.getEstadoCivil();
			grauInstruDados = pessoaDados.getGrauInstrucao();
			situEconomicaDados = pessoaDados.getSituacaoEconomica();
			religiaoDados = pessoaDados.getReligiao();
			
			paisDados.setPkPais(enderecoDados.getCidade().getEstado().getPais().getPkPais());
			estadoDados.setPkEstado(enderecoDados.getCidade().getEstado().getPkEstado());
			cidadeDados.setPkCidade(enderecoDados.getCidade().getPkCidade());
			
		}
	}
/* ------------------------------------------------------------------------------------------------------------------------ */
/* ---------------------------------Metodos utlizados na tela------------------------------------------------ */
	
	/*
	 * Metodo para converter a foto do aluno para BASE64
	 * */
	public void converteFotoAluno() {
		try {
			imagem64 = new ImagemBase64();
			String formato = fotoAluno.getContentType();
			String nome = fotoAluno.getSubmittedFileName();
			byte[] imagemFotoAluno = new byte[(int) fotoAluno.getSize()]; 
			
			fotoAluno.getInputStream().read(imagemFotoAluno);
			String fotoBase64 = new String(Base64.encode(imagemFotoAluno));
			imagem64.setB64(fotoBase64);
			imagem64.setNome(nome);
			imagem64.setTipo(formato);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Metodo para converter a copia da certidao para BASE64
	 * */
	public void convertecopiaCertidao() {
		try {
			imagem64 = new ImagemBase64();
			String formato = copiaCertidao.getContentType();
			String nome = fotoAluno.getSubmittedFileName();
			byte[] imagemCopiaCertidao = new byte[(int) copiaCertidao.getSize()]; 
			
			fotoAluno.getInputStream().read(imagemCopiaCertidao);
			String fotoBase64 = new String(Base64.encode(imagemCopiaCertidao));
			imagem64.setB64(fotoBase64);
			imagem64.setNome(nome);
			imagem64.setTipo(formato);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Metodo para converter a copia de endereco para BASE64
	 * */
	public void convertecopiaEndereco() {
		try {
			imagem64 = new ImagemBase64();
			String formato = copiaEndereco.getContentType();
			String nome = fotoAluno.getSubmittedFileName();
			byte[] imagemCopiaEndereco = new byte[(int) copiaEndereco.getSize()]; 
			
			fotoAluno.getInputStream().read(imagemCopiaEndereco);
			String fotoBase64 = new String(Base64.encode(imagemCopiaEndereco));
			imagem64.setB64(fotoBase64);
			imagem64.setNome(nome);
			imagem64.setTipo(formato);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
					Logs.addWarning("CPF não encontrado, favor informar o nome.", null);
				}
			} catch (SQLException e) {
				Logs.addError("Erro ao consultar o CPF informado", null);
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
				Logs.addError("Erro ao consultar o CPF informado", null);
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
					Logs.addWarning("CPF não encontrado, favor informar o nome.", null);
				}
			} catch (SQLException e) {
				Logs.addError("Erro ao consultar o CPF informado", null);
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
			if( validaDadosPessoa() == true ) {	
				
				if(cadastrar) {					
					salvarCadastroPessoa();
				}
				if(atualizar){
					
				}
				limparFormulario();
			}
		}
		if(pessoaDados.getTipoPessoa() == CADASTRO_ALUNO && pessoaDados != null) {
			if( (validaDadosPessoa() == true) && (validaDadosAluno() == true) ) {	
				salvarCadastroPessoa();
				salvarCadastroAluno();
				limparFormulario();
			}
		}
		if(pessoaDados.getTipoPessoa() == CADASTRO_FUNCIONARIO && pessoaDados != null) {
			salvarCadastroPessoa();
			salvarCadastroFuncionario();
			limparFormulario();
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
		comboGrauParentesco.addAll(paramDados.consultaGrauParentesco());
		comboRedeEnsino.addAll(paramDados.consultaRedeEnsino());
		comboTipoDeficiencia.addAll(paramDados.consultaTipoDeficiencia());
		comboEstadoNascimento.addAll(paramDados.consultaEstadoNascimento());
	}
	
	public void pageFirst() {
        dataTable.setFirst(0);
    }

    public void pagePrevious() {
        dataTable.setFirst(dataTable.getFirst() - dataTable.getRows());
    }

    public void pageNext() {
        dataTable.setFirst(dataTable.getFirst() + dataTable.getRows());
    }

    public void pageLast() {
        int count = dataTable.getRowCount();
        int rows = dataTable.getRows();
        dataTable.setFirst(count - ((count % rows != 0) ? count % rows : rows));
    }
	
    public int getCurrentPage() {
        int rows = dataTable.getRows();
        int first = dataTable.getFirst();
        int count = dataTable.getRowCount();
        return (count / rows) - ((count - first) / rows) + 1;
    }

    public int getTotalPages() {
        int rows = dataTable.getRows();
        int count = dataTable.getRowCount();
        return (count / rows) + ((count % rows != 0) ? 1 : 0);
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

	public Contato getContatoDados() {
		return contatoDados;
	}
	
	public void setContatoDados(Contato contatoDados) {
		this.contatoDados = contatoDados;
	}

	public PessoaDAO getPessoaDAO() {
		return pessoaDAO;
	}

	public void setPessoaDAO(PessoaDAO pessoaDAO) {
		this.pessoaDAO = pessoaDAO;
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
			
			cidadeDados.setPkCidade(null);
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
/* ------------------------------------------------------------------------------------------------------------------------ */
/* InformaÃ§Ãµes referentes ao dados do aluno */	
	public List<SelectItem> getComboRedeEnsino() {
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
	/* InformaÃ§Ãµes referentes ao dados do aluno */	
	/* ------------------------------------------------------------------------------------------------------------------------ */
	
	/* ------------------------------------------------------------------------------------------------------------------------ */
	/* InformaÃ§Ãµes referentes ao dados do funcionario */
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
	/* InformaÃ§Ãµes referentes ao dados do funcionario */
	/* ------------------------------------------------------------------------------------------------------------------------ */
/* GETTERS AND SETTER DE PARAMETROS DA TELA */
/* ------------------------------------------------------------------------------------------------------------------------ */
	
	public GrauParentesco getGrauParentescoDados() {
		return grauParentescoDados;
	}
	
	public void setGrauParentescoDados(GrauParentesco grauParentescoDados) {
		this.grauParentescoDados = grauParentescoDados;
	}
	
	public HtmlDataTable getDataTable() {
        return dataTable;
	}

	public void setDataTable(HtmlDataTable dataTable) {
		this.dataTable = dataTable;
	}

	public List<Pessoa> getListaConsultaPessoa() {
		return listaConsultaPessoa;
	}
	
	public void setListaConsultaPessoa(List<Pessoa> listaConsultaPessoa) {
		this.listaConsultaPessoa = listaConsultaPessoa;
	}

	public Part getFotoAluno() {
		return fotoAluno;
	}

	public void setFotoAluno(Part fotoAluno) {
		this.fotoAluno = fotoAluno;
	}

	public Part getCopiaCertidao() {
		return copiaCertidao;
	}

	public void setCopiaCertidao(Part copiaCertidao) {
		this.copiaCertidao = copiaCertidao;
	}

	public Part getCopiaEndereco() {
		return copiaEndereco;
	}

	public void setCopiaEndereco(Part copiaEndereco) {
		this.copiaEndereco = copiaEndereco;
	}

	public List<SelectItem> getComboEstadoNascimento() {
		return comboEstadoNascimento;
	}

	public void setComboEstadoNascimento(List<SelectItem> comboEstadoNascimento) {
		this.comboEstadoNascimento = comboEstadoNascimento;
	}

	public List<SelectItem> getComboCidadeNascimento() {
		comboCidadeNascimento.clear();
		if(estadoNascimentoDados.getPkEstado() != null && !comboEstadoNascimento.isEmpty()){
			ParametrosServlet paramDados = new ParametrosServlet();
			comboCidadeNascimento.addAll(paramDados.consultaCidade(estadoNascimentoDados));
		}
		return comboCidadeNascimento;
	}

	public void setComboCidadeNascimento(List<SelectItem> comboCidadeNascimento) {
		this.comboCidadeNascimento = comboCidadeNascimento;
	}

	public Estado getEstadoNascimentoDados() {
		return estadoNascimentoDados;
	}

	public void setEstadoNascimentoDados(Estado estadoNascimentoDados) {
		this.estadoNascimentoDados = estadoNascimentoDados;
	}

	public Cidade getCidadeNascimentoDados() {
		return cidadeNascimentoDados;
	}

	public void setCidadeNascimentoDados(Cidade cidadeNascimentoDados) {
		this.cidadeNascimentoDados = cidadeNascimentoDados;
	}     
}
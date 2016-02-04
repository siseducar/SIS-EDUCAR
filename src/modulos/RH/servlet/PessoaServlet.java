package modulos.RH.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import modulos.RH.dao.CargoDAO;
import modulos.RH.dao.CidadeDAO;
import modulos.RH.dao.EstadoCivilDAO;
import modulos.RH.dao.EstadoDAO;
import modulos.RH.dao.GrauInstrucaoDAO;
import modulos.RH.dao.NacionalidadeDAO;
import modulos.RH.dao.PaisDAO;
import modulos.RH.dao.RacaDAO;
import modulos.RH.dao.RegiaoDAO;
import modulos.RH.dao.ReligiaoDAO;
import modulos.RH.dao.SituacaoEconomicaDAO;
import modulos.RH.dao.TipoDeficienciaDAO;
import modulos.RH.om.Aluno;
import modulos.RH.om.Cargo;
import modulos.RH.om.Cidade;
import modulos.RH.om.Estado;
import modulos.RH.om.EstadoCivil;
import modulos.RH.om.Fornecedor;
import modulos.RH.om.Funcionario;
import modulos.RH.om.GrauInstrucao;
import modulos.RH.om.Nacionalidade;
import modulos.RH.om.Pais;
import modulos.RH.om.Pessoa;
import modulos.RH.om.Raca;
import modulos.RH.om.Regiao;
import modulos.RH.om.Religiao;
import modulos.RH.om.SituacaoEconomica;
import modulos.RH.om.TipoDeficiencia;

@ManagedBean(name="pessoaServlet")
@ViewScoped
public class PessoaServlet implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/* Atributos */
	Pessoa pessoaDados;
	Aluno alunoDados;
	Fornecedor fornecedorDados;
	Funcionario funcionarioDados;	

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
	
	/* Construtor */
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
		complementoAluno = false;
		complementoFuncionario = false;
		alunoDeficiente = false;
		funcConcursado = false;
		funcAposentado = false;
		funcDemitido = false;
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
	 * Metodo responsavel por validar o tipo de cadastro
	 * 
	 * */
	public String validarTipoCadastro(){
		if( pessoaDados.getTipoPessoa() == 0 ) {
			cadastroPessoa();
			System.out.println(pessoaDados.getNome());
		}else {
			if ( pessoaDados.getTipoPessoa() == 1 ) {
				cadastroAluno();
			}else{
				if( pessoaDados.getTipoPessoa() == 2 ) {
					cadastroFuncionario();
				}
			}
		}
		return null;
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
	public Boolean cadastroPessoa(){
		System.out.println(pessoaDados);
		return false;
	}
	
	/*
	 * Metodo para salvar os dados do aluno
	 * 
	 * */
	public Boolean cadastroAluno() {
		System.out.println(pessoaDados);
		System.out.println(alunoDados);
		return false;
	}
	
	/*
	 * Metodo para salvar os dados do funcionario
	 * 
	 * */
	public Boolean cadastroFuncionario() {
		System.out.println(pessoaDados);
		System.out.println(funcionarioDados);
		return false;
	}
/* ------------------------------------------------------------------------------------------------------------------------ */
/* ---------------------------------Metodos para carregar os compos da tela------------------------------------------------ */
	/*
	 * Metodo para carregar as Naturalidades
	 * */
	public List<SelectItem> getConsultaNacionalidade() throws SQLException {
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
	}
	
	/*
	 * Metodo para carregar as Racas
	 * */
	public List<SelectItem> getConsultaRaca() throws SQLException {
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
	}
	
	/*
	 * Metodo para carregar os Estados Civis
	 * */
	public List<SelectItem> getConsultaEstaCivil() throws SQLException {
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
	}
	
	/*
	 * Metodo para carregar os Graus de Instrucoes
	 * */
	public List<SelectItem> getConsultaGrauInstru() throws SQLException {
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
	}
	
	/*
	 * Metodo para carregar as situacoes economicas
	 * */
	public List<SelectItem> getConsultaSituEconomica() throws SQLException {
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
	}
	
	/*
	 * Metodo para carregar as Religioes
	 * */
	public List<SelectItem> getConsultaReligiao() throws SQLException {
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
	}
	
	/*
	 * Metodo para carregar os Paises
	 * */
	public List<SelectItem> getConsultaPais() throws SQLException {
		PaisDAO paisDAO = new PaisDAO();
		List<SelectItem> comboPais = new ArrayList<SelectItem>();
		List<Pais> paramPais = paisDAO.consultaPais();
		
		for (Pais param : paramPais){
		   SelectItem  s = new SelectItem();
		   s.setValue(param.getPkPais());
		   s.setLabel(param.getNome());
		   comboPais.add(s);
		}
		return comboPais;
	}
	
	/*
	 * Metodo para carregar os Estados
	 * */
	public List<SelectItem> getConsultaEstado() throws SQLException {
		EstadoDAO estadoDAO = new EstadoDAO();
		List<SelectItem> comboEstado = new ArrayList<SelectItem>();
		List<Estado> paramEstado = estadoDAO.consultaEstado();
		
		for (Estado param : paramEstado){
		   SelectItem  s = new SelectItem();
		   s.setValue(param.getPkEstado());
		   s.setLabel(param.getNome());
		   comboEstado.add(s);
		}
		return comboEstado;
	}
	
	/*
	 * Metodo para carregar as Cidades
	 * */
	public List<SelectItem> getConsultaCidade() throws SQLException {
		CidadeDAO cidadeDAO = new CidadeDAO();
		List<SelectItem> comboCidade = new ArrayList<SelectItem>();
		List<Cidade> paramCidade = cidadeDAO.consultaCidade();
		
		for (Cidade param : paramCidade){
		   SelectItem  s = new SelectItem();
		   s.setValue(param.getPkCidade());
		   s.setLabel(param.getNome());
		   comboCidade.add(s);
		}
		return comboCidade;
	}
	
	/*
	 * Metodo para carregar as Zonas Residencias
	 * */
	public List<SelectItem> getConsultaRegiao() throws SQLException {
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
	
	/* GETTERS AND SETTERS */
	public Pessoa getPessoaDados() {
		return pessoaDados;
	}

	public Aluno getAlunoDados() {
		return alunoDados;
	}

	public Fornecedor getFornecedorDados() {
		return fornecedorDados;
	}

	public Funcionario getFuncionarioDados() {
		return funcionarioDados;
	}

	public void setPessoaDados(Pessoa pessoaDados) {
		this.pessoaDados = pessoaDados;
	}

	public void setAlunoDados(Aluno alunoDados) {
		this.alunoDados = alunoDados;
	}

	public void setFornecedorDados(Fornecedor fornecedorDados) {
		this.fornecedorDados = fornecedorDados;
	}

	public void setFuncionarioDados(Funcionario funcionarioDados) {
		this.funcionarioDados = funcionarioDados;
	}
	
	public Boolean getComplementoAluno() {
		return complementoAluno;
	}

	public Boolean getComplementoFuncionario() {
		return complementoFuncionario;
	}

	public void setComplementoAluno(Boolean complementoAluno) {
		this.complementoAluno = complementoAluno;
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

	public Boolean getAlunoDeficiente() {
		return alunoDeficiente;
	}

	public void setAlunoDeficiente(Boolean alunoDeficiente) {
		this.alunoDeficiente = alunoDeficiente;
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
}

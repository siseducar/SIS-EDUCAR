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
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import modulos.secretaria.dao.ContatoDAO;
import modulos.secretaria.dao.EnderecoDAO;
import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Contato;
import modulos.secretaria.om.Endereco;
import modulos.secretaria.om.Estado;
import modulos.secretaria.om.EstadoCivil;
import modulos.secretaria.om.GrauInstrucao;
import modulos.secretaria.om.GrauParentesco;
import modulos.secretaria.om.Nacionalidade;
import modulos.secretaria.om.Pais;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.Raca;
import modulos.secretaria.om.Regiao;
import modulos.secretaria.om.Religiao;
import modulos.secretaria.om.SituacaoEconomica;
import modulos.secretaria.om.Usuario;
import modulos.sisEducar.servlet.SisEducarServlet;
import modulos.sisEducar.utils.ConstantesSisEducar;
import modulos.sisEducar.utils.Logs;

@ManagedBean(name="pessoaServlet")
@ViewScoped
public class PessoaServlet implements Serializable{

	private static final long serialVersionUID = 1L;

	/* Atributos */
	private Pessoa pessoaDados;
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
	private GrauParentesco grauParentescoDados;
	private PessoaDAO pessoaDAO;
	private EnderecoDAO enderecoDAO;
	private ContatoDAO contatoDAO;
	private Estado estadoNascimentoDados;
	private Cidade cidadeNascimentoDados;
	private Pessoa pessoaSelecionada;
	private Pessoa pessoaDadosConsulta;
		
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
	
	/* Combo com valores de PAÃ�S */
	private List<SelectItem> comboPais;
	
	/* Combo com valores de ESTADO */
	private List<SelectItem> comboEstado;
	
	/* Combo com valores de CIDADE */
	private List<SelectItem> comboCidade;
	
	/* Combo com valores de CARGO */
	private List<SelectItem> comboCargo;
	
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
	
	private Boolean deletarCadastro;
	
	private Boolean cadastroSucesso;

	/* Metodo Construtor */
	public PessoaServlet() throws SQLException {
		if(this.pessoaDados == null){
			this.pessoaDados = new Pessoa();
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
		if(this.grauParentescoDados == null) {
			this.grauParentescoDados = new GrauParentesco();
		}
		if(this.pessoaDAO == null) {
			this.pessoaDAO = new PessoaDAO();
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
		if(this.pessoaDadosConsulta == null) {
			this.pessoaDadosConsulta = new Pessoa();
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
		comboPais = new ArrayList<SelectItem>();
		comboEstado = new ArrayList<SelectItem>();
		comboCidade = new ArrayList<SelectItem>();
		comboEstadoNascimento = new ArrayList<SelectItem>();
		comboCidadeNascimento = new ArrayList<SelectItem>();
		
		carregaCombos();
		nomeMae = false;
		nomePai = false;
		nomeResponsavel = false;
		menorIdade = false;
		deletarCadastro = false;
		
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
			enderecoDados.setContato(contatoDados);
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
			pessoaDados.setStatus(ConstantesSisEducar.STATUS_ATIVO);
			
			pessoaDados = pessoaDAO.salvarCadastroPessoa(pessoaDados);
			
			if( pessoaDados.getPkPessoa() == null ) {
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
	
	
	
	/*
	 * Metodo para validar se ja existe o CPF cadastrado
	 * */
	public void verificaCadastro() {
		try {
			if(pessoaDados.getCpf() != null && pessoaDados.getCpf() != 0) {
				if ( pessoaDAO.obtemUnicoPessoaSimples( pessoaDados.getCpf().toString()) != null ){
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
	public void limparFormulario() throws SQLException{
		
		pessoaDados = new Pessoa();
		contatoDados = new Contato();
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
		paramDados = new ParametrosServlet();
		grauParentescoDados = new GrauParentesco();
		pessoaDAO = new PessoaDAO();
		enderecoDAO = new EnderecoDAO();
		contatoDAO = new ContatoDAO();
		dataTable = new HtmlDataTable();
		estadoNascimentoDados = new Estado();
		cidadeNascimentoDados = new Cidade();
		pessoaSelecionada = new Pessoa();
		
		nomeMae = false;
		nomePai = false;
		nomeResponsavel = false;
		menorIdade = false;
		deletarCadastro = false;
	}

	public void consultaCadastro() {
		listaConsultaPessoa = new ArrayList<Pessoa>();
		
		listaConsultaPessoa = 
				pessoaDAO.consultaCadastroPessoa(pessoaDadosConsulta.getNome(), 
						pessoaDadosConsulta.getCpf(), 
						pessoaDadosConsulta.getRg(), 
						pessoaDadosConsulta.getDataNascimento());
	}
	
	public void editarCadastro() {
		
		pessoaDados = new Pessoa();
		enderecoDados = new Endereco();
		contatoDados = new Contato();
		pessoaSelecionada = (Pessoa) dataTable.getRowData();
		
		if(pessoaSelecionada != null && pessoaSelecionada.getPkPessoa() != null) {
			pessoaDados = pessoaSelecionada;
			
			pessoaDados = pessoaDAO.consultaCadastroPessoa(pessoaDados.getPkPessoa());
			enderecoDados = enderecoDAO.consultaEnderecoPessoa(pessoaDados.getEndereco().getPkEndereco());
			contatoDados = contatoDAO.buscarContato(enderecoDados.getContato().getPkContato());
			
			nacionalidadeDados = pessoaDados.getNacionalidade();
			racaDados = pessoaDados.getRaca();
			estaCivilDados = pessoaDados.getEstadoCivil();
			grauInstruDados = pessoaDados.getGrauInstrucao();
			situEconomicaDados = pessoaDados.getSituacaoEconomica();
			religiaoDados = pessoaDados.getReligiao();
			
			if(enderecoDados.getCidade() != null) {
				if(enderecoDados.getCidade().getEstado().getPais().getPkPais() != null) {
					for(SelectItem selectItem : comboPais) {
						if(selectItem.getValue().equals(enderecoDados.getCidade().getEstado().getPais().getPkPais())) {
							paisDados = new Pais();
							paisDados.setPkPais(enderecoDados.getCidade().getEstado().getPais().getPkPais());
							break;
						}
					}
				}
				if(paisDados != null && paisDados.getPkPais() != null) {
					if( comboEstado == null || comboEstado.isEmpty()) {
						comboEstado = paramDados.consultaEstado(paisDados);
					}
					for(SelectItem selectItem : comboEstado) {
						if(selectItem.getValue().equals(enderecoDados.getCidade().getEstado().getPkEstado())) {
							estadoDados = new Estado();
							estadoDados.setPkEstado(enderecoDados.getCidade().getEstado().getPkEstado());
							break;
						}
					}
				}
				if(estadoDados != null && estadoDados.getPkEstado() != null){
					if( comboCidade == null || comboCidade.isEmpty()) { 
						comboCidade = paramDados.consultaCidade(estadoDados); 
					}
					for(SelectItem selectItem : comboCidade) {
						if(selectItem.getValue().equals(enderecoDados.getCidade().getPkCidade())) {
							cidadeDados = new Cidade();
							cidadeDados.setPkCidade(enderecoDados.getCidade().getPkCidade());
							break;
						}
					}
				}
			}
			deletarCadastro = true;
		}
	}
/* ------------------------------------------------------------------------------------------------------------------------ */
/* ---------------------------------Metodos utlizados na tela------------------------------------------------ */
	/*
	 * Metodo responsavel por validar o nome da MAE caso de menor
	 * 
	 * */
	public void validaNomeMae(){
		if(pessoaDados.getCpfMae() != null && pessoaDados.getCpfMae() != 0){
			Long cpfMae = pessoaDados.getCpfMae();
			
			String nome = pessoaDAO.consultaNomeResponsavel(cpfMae); 
			
			if(nome != null && !nome.equals("")){
				pessoaDados.setNomeMae(nome);
				nomeMae = true;
			}else{
				pessoaDados.setNomeMae(null);
				nomeMae = false;
				Logs.addWarning("CPF não encontrado, favor informar o nome.", null);
			}
		}else{
			pessoaDados.setNomeMae(null);
			nomeMae = false;
		}
	}
	
	/*
	 * Metodo responsavel por validar o nome do PAI caso de menor
	 * 
	 * */
	public void validaNomePai(){
		if(pessoaDados.getCpfPai() != null && pessoaDados.getCpfPai() != 0 ){
			Long cpfPai = pessoaDados.getCpfPai();
			
			String nome = pessoaDAO.consultaNomeResponsavel(cpfPai); 
			if(nome != null && !nome.equals("")){
				pessoaDados.setNomePai(nome);
				nomePai = true;
			}else{
				pessoaDados.setNomePai(null);
				nomePai = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"CPF não encontrado, favor informar o nome.", null));
			}
		}
	}
	
	/*
	 * Metodo responsavel por validar o nome do RESPONSAVEL caso de menor
	 * 
	 * */
	public void validaNomeResponsavel(){
		if(pessoaDados.getCpfResponsavel() != null && pessoaDados.getCpfResponsavel() != 0){
			Long cpfResponsavel = pessoaDados.getCpfResponsavel();
			
			String nome = pessoaDAO.consultaNomeResponsavel(cpfResponsavel); 
			if(nome != null && !nome.equals("")){
				pessoaDados.setNomeResponsavel(nome);
				nomeResponsavel = true;
			}else{
				pessoaDados.setNomePai(null);
				nomeResponsavel = false;
				Logs.addWarning("CPF não encontrado, favor informar o nome.", null);
			}
		}
	}
		
	/*
	 * Metodo para validar o tipo de cadastro
	 * 
	 * */
	public void validaCadastro(){
		if( validaDadosPessoa() == true ) {	
			if(pessoaDados.getPkPessoa() == null ) {					
				salvarCadastroPessoa();
			}
		}
	}

	/*
	 * Metodo responsavel por carregar os combos da tela
	 * 
	 * */
	public void carregaCombos() throws SQLException{
		if( comboEstadoCivil.isEmpty()) {			
			comboEstadoCivil.addAll(paramDados.consultaEstaCivil());
		}
		if(comboGrauInstrucao.isEmpty()) {
			comboGrauInstrucao.addAll(paramDados.consultaGrauInstru());	
		}
		if(comboNacionalidade.isEmpty()) {
			comboNacionalidade.addAll(paramDados.consultaNacionalidade());	
		}
		if(comboRaca.isEmpty()) {
			comboRaca.addAll(paramDados.consultaRaca());	
		}
		if(comboReligiao.isEmpty()) {
			comboReligiao.addAll(paramDados.consultaReligiao());	
		}
		if(comboSituacaoEconomica.isEmpty()) {
			comboSituacaoEconomica.addAll(paramDados.consultaSituEconomica());	
		}
		if(comboZonaResidencial.isEmpty()) {
			comboZonaResidencial.addAll(paramDados.consultaRegiao());	
		}
		if(comboPais.isEmpty()) {
			comboPais.addAll(paramDados.consultaPais());	
		}
		if(comboCargo.isEmpty()) {
			comboCargo.addAll(paramDados.consultaCargo());	
		}
		if(comboGrauParentesco.isEmpty()) {
			comboGrauParentesco.addAll(paramDados.consultaGrauParentesco());	
		}
		if(comboEstadoNascimento.isEmpty()) {
			comboEstadoNascimento.addAll(paramDados.consultaEstadoNascimento());	
		}
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
		if(paisDados.getPkPais() != null && !comboPais.isEmpty()) {
			
			ParametrosServlet paramDados = new ParametrosServlet();
			comboEstado.addAll(paramDados.consultaEstado(paisDados));
			
			return comboEstado;
		}
		estadoDados.setPkEstado(null);
		comboEstado.clear();
		return comboEstado;
	}

	public void setComboEstado(List<SelectItem> comboEstado) {
		this.comboEstado = comboEstado;
	}
	
	/* CIDADE */
	public List<SelectItem> getComboCidade() {
		if(estadoDados.getPkEstado() != null && !comboEstado.isEmpty()){
			ParametrosServlet paramDados = new ParametrosServlet();
			comboCidade.addAll(paramDados.consultaCidade(estadoDados));
			
			return comboCidade;
		}
		cidadeDados.setPkCidade(null);
		comboCidade.clear();
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
	public List<SelectItem> getComboGrauParentesco() {
		return comboGrauParentesco;
	}

	public void setComboGrauParentesco(List<SelectItem> comboGrauParentesco) {
		this.comboGrauParentesco = comboGrauParentesco;
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

	public Pessoa getPessoaDadosConsulta() {
		return pessoaDadosConsulta;
	}

	public void setPessoaDadosConsulta(Pessoa pessoaDadosConsulta) {
		this.pessoaDadosConsulta = pessoaDadosConsulta;
	}

	public Boolean getDeletarCadastro() {
		return deletarCadastro;
	}

	public void setDeletarCadastro(Boolean deletarCadastro) {
		this.deletarCadastro = deletarCadastro;
	}
	
	public Boolean getCadastroSucesso() {
		return cadastroSucesso;
	}

	public void setCadastroSucesso(Boolean cadastroSucesso) {
		this.cadastroSucesso = cadastroSucesso;
	}
}
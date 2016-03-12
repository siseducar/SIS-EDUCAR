package modulos.RH.servlet;

import java.io.File;
import java.io.FileOutputStream;
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
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import org.apache.tomcat.util.codec.binary.Base64;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import modulos.RH.dao.AlunoDAO;
import modulos.RH.dao.CargoDAO;
import modulos.RH.dao.CidadeDAO;
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
import modulos.sisEducar.converter.ImagemBase64;

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
	
	ImagemBase64 base64MB;
	private Part imagem;
	
	private UploadedFile imagemResidencia;
	private UploadedFile fotoAluno;
	private UploadedFile imagemCertidaoNascimento;
		
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
	
	private List<SelectItem> comboGrauParentesco;
	
	private List<SelectItem> comboNacionalidade;
	
	private List<SelectItem> comboRaca;
	
	private List<SelectItem> comboEstadoCivil;
	
	private List<SelectItem> comboGrauInstrucao;
	
	private List<SelectItem> comboSituacaoEconomica;
	
	private List<SelectItem> comboReligiao;
	
	private List<SelectItem> comboZonaResidencial;
	
	private List<SelectItem> comboTipoDeficiencia;
	
	private List<SelectItem> comboPais;
	
	private List<SelectItem> comboEstado;
	
	private List<SelectItem> comboCidade;
	
	private List<SelectItem> comboCargo;
	
	private List<SelectItem> comboRedeEnsino;
	
	private List<SelectItem> comboUnidadeEscolar;
	
	private Boolean nomeMae;
	
	private Boolean nomePai;
	
	private Boolean nomeResponsavel;
	
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
		if(base64MB == null) {
			this.base64MB = new ImagemBase64();
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
	
	public void salvarImagem(){ 
		String formato = imagem.getContentType(); 
		String nome = imagem.getName(); 
		byte[] imageAsByte = new byte[(int) imagem.getSize()]; 
		
		try { 
			imagem.getInputStream().read(imageAsByte); 
			ImagemBase64 ib4 = new ImagemBase64(); 
			String base64AsString = new String(Base64.encodeBase64(imageAsByte)); 
			ib4.setB64(base64AsString); 
			ib4.setFormato(formato); 
			ib4.setNome(nome); 
			
			System.out.println(ib4.getB64()); 
		} catch (IOException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		
	}
	
	public void upload(){
		System.out.println("Upload enviado " + fotoAluno.getFileName());
	}
	
	public void upload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage(event.getFile().getFileName() + " foi enviado com sucesso.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file        
        try {
            byte[] foto = event.getFile().getContents();
            String nomeArquivo = event.getFile().getFileName();  
            FacesContext facesContext = FacesContext.getCurrentInstance();  
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();  
            String arquivo = scontext.getRealPath("/uploads/imagensTopo/" + nomeArquivo);
//            String arquivo = scontext.getContextPath()+"/uploadis/" + nomeArquivo;
            File f=new File(arquivo);
            if(!f.getParentFile().exists())f.getParentFile().mkdirs();
            if(!f.exists())f.createNewFile();
            System.out.println(f.getAbsolutePath());
            FileOutputStream fos=new FileOutputStream(arquivo);
            fos.write(foto);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	
	public void calculaIdade(){
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
		
	/*
	 * Metodo para salvar o cadastro de Pessoa
	 * 
	 * */
	public String salvarCadastroPessoa(){
		try {
			
			Pessoa pessoaDadosFinal = new Pessoa();
			replaceCampos();
			
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
				pessoaDadosFinal.setStatus(Integer.valueOf(0));
				
			}
			
			/* Validação dos dados referentes ao ENDEREÇO */
			if( enderecoDados != null ) {
				enderecoDados.setCidade(cidadeDados);
				pessoaDadosFinal.setEndereco(enderecoDados);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
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
	 * Metodo para carregar as Naturalidades
	 * */
	public void consultaNacionalidade() {
		try {
			NacionalidadeDAO nacionalidadeDAO = new NacionalidadeDAO();
			List<Nacionalidade> paramNacionalidade = nacionalidadeDAO.consultaNacionalidade();
			
			for (Nacionalidade param : paramNacionalidade){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkNacionalidade());
			   s.setLabel(param.getDescricao());
			   comboNacionalidade.add(s);
			}
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de NACIONALIDADE, contate o administrador do sistema!", null));
		}
	}
	
	/*
	 * Metodo para carregar as Racas
	 * */
	public void consultaRaca() {
		try {
			RacaDAO racaDAO = new RacaDAO();
			List<Raca> paramRaca = racaDAO.consultaRaca();
			
			for (Raca param : paramRaca){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkRaca());
			   s.setLabel(param.getDescricao());
			   comboRaca.add(s);
			}
		} catch (SQLException e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de RAÇA, contate o administrador do sistema!", null));
		}
	}
	
	/*
	 * Metodo para carregar os Estados Civis
	 * */
	public void consultaEstaCivil() {
		try {
			EstadoCivilDAO estaCivilDAO = new EstadoCivilDAO();
			List<EstadoCivil> paramEstaCivil = estaCivilDAO.consultaEstaCivil();
			
			for (EstadoCivil param : paramEstaCivil){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkEstadoCivil());
			   s.setLabel(param.getDescricao());
			   comboEstadoCivil.add(s);
			}
		}catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de ESTADOS CIVIS, contate o administrador do sistema!", null));
		}
	}
	
	/*
	 * Metodo para carregar os Graus de Instrucoes
	 * */
	public void consultaGrauInstru() {
		try {
			GrauInstrucaoDAO grauInstruDAO = new GrauInstrucaoDAO();
			List<GrauInstrucao> paramGrauInstru = grauInstruDAO.consultaGrauInstru();
			
			for (GrauInstrucao param : paramGrauInstru){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkGrauInstrucao());
			   s.setLabel(param.getDescricao());
			   comboGrauInstrucao.add(s);
			}
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de GRAU DE INSTRUÇÃO, contate o administrador do sistema!", null));
		}
	}
	
	/*
	 * Metodo para carregar as situacoes economicas
	 * */
	public void consultaSituEconomica() {
		try {
			SituacaoEconomicaDAO situEconomicaDAO = new SituacaoEconomicaDAO();
			List<SituacaoEconomica> paramSituEconomica = situEconomicaDAO.consultaSituEconomica();
			
			for (SituacaoEconomica param : paramSituEconomica){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkSituacaoEconomica());
			   s.setLabel(param.getDescricao());
			   comboSituacaoEconomica.add(s);
			}
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de SITUAÇÕES ECONÔMICAS, contate o administrador do sistema!", null));
		}
	}
	
	/*
	 * Metodo para carregar as Religioes
	 * */
	public void consultaReligiao() {
		try {
			ReligiaoDAO religiaoDAO = new ReligiaoDAO();
			List<Religiao> paramReligiao = religiaoDAO.consultaReligiao();
			
			for (Religiao param : paramReligiao){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkReligiao());
			   s.setLabel(param.getDescricao());
			   comboReligiao.add(s);
			}
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de RELIGIÕES, contate o administrador do sistema!", null));
		}
	}
	
	/*
	 * Metodo para carregar os Paises
	 * */
	public void consultaPais() {
		try {
			PaisDAO paisDAO = new PaisDAO();
			List<Pais> paramPais = paisDAO.consultaPais();
			
			for (Pais param : paramPais){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkPais());
			   s.setLabel(param.getNome());
			   comboPais.add(s);
			}
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de PAÍS, contate o administrador do sistema!", null));
		}
	}
	
	/*
	 * Metodo para carregar os Estados
	 * */
	public void consultaEstado() {
		comboEstado.clear();
		estadoDados.setPkEstado(null);
		if(paisDados.getPkPais() != null && !comboPais.isEmpty()) {
			try {
				EstadoDAO estadoDAO = new EstadoDAO();
				List<Estado> paramEstado = estadoDAO.consultaEstado(paisDados.getPkPais());
				
				for (Estado param : paramEstado){
				   SelectItem  s = new SelectItem();
				   s.setValue(param.getPkEstado());
				   s.setLabel(param.getNome());
				   comboEstado.add(s);
				}
			}catch(SQLException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Erro ao carregar os dados de ESTADO, contate o administrador do sistema!", null));
			}
		}
	}
	
	/*
	 * Metodo para carregar as Cidades
	 * */
	public void consultaCidade() {		
		comboCidade.clear();
		cidadeDados.setPkCidade(null);
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
			}catch(SQLException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Erro ao carregar os dados de CIDADE, contate o administrador do sistema!", null));
			}
		}
	}

	/*
	 * Metodo para carregar as Zonas Residencias
	 * */
	public void consultaZonaResidencial() {
		try {
			RegiaoDAO regiaoDAO = new RegiaoDAO();
			List<Regiao> paramRegiao = regiaoDAO.consultaRegiao();
			
			for (Regiao param : paramRegiao){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkRegiao());
			   s.setLabel(param.getDescricao());
			   comboZonaResidencial.add(s);
			}
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de REGIÃO, contate o administrador do sistema!", null));
		}
	}
	
	/*
	 * Metodo para carregar os tipos de Deficiencia
	 * */
	public void consultaTipoDeficiencia() {
		try {
			TipoDeficienciaDAO tipoDeficienciaDAO = new TipoDeficienciaDAO();
			List<TipoDeficiencia> paramTipoDeficiencia = tipoDeficienciaDAO.consultaTipoDeficiencia();
			
			for (TipoDeficiencia param : paramTipoDeficiencia){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkTipoDeficiencia());
			   s.setLabel(param.getDescricao());
			   comboTipoDeficiencia.add(s);
			}
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de TIPOS DE DEFICIENCIA, contate o administrador do sistema!", null));
		}
	}
	
	/*
	 * Metodo para carregar os cargos
	 * */
	public void consultaCargo() {
		try {
			CargoDAO cargoDAO = new CargoDAO();
			List<Cargo> paramCargo = cargoDAO.consultaCargo();
			
			for (Cargo param : paramCargo){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkCargo());
			   s.setLabel(param.getDescricao());
			   comboCargo.add(s);
			}
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de CARGO, contate o administrador do sistema!", null));
		}
	}
	
	/*
	 * Metodo para carregar as rede de ensino
	 * */
	public void consultaRedeEnsino() {
		try {
			comboRedeEnsino.clear();
			RedeEnsinoDAO redeEnsinoDAO = new RedeEnsinoDAO();
			List<RedeEnsino> paramRedeEnsino = redeEnsinoDAO.consultaRedeEnsino();
			
			for (RedeEnsino param : paramRedeEnsino){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkRedeEnsino());
			   s.setLabel(param.getNome());
			   comboRedeEnsino.add(s);
			}
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de REDE DE ENSINO, contate o administrador do sistema!", null));
		}
	}
	
	/*
	 * Metodo para carregar as Unidades Escolares
	 * */
	public void consultaUnidadeEscolar() {
		try {
			UnidadeEscolarDAO unidadeEscolarDAO = new UnidadeEscolarDAO();			
			List<UnidadeEscolar> paramUnidadeEscolar = 
						unidadeEscolarDAO.consultaUnidadeEscolar(Integer.parseInt(alunoDados.getRedeEnsino()));
			
			for (UnidadeEscolar param : paramUnidadeEscolar){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkUnidadeEscolar());
			   s.setLabel(param.getNome());
			   comboUnidadeEscolar.add(s);
			}
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de UNIDADE ESCOLAR, contate o administrador do sistema!", null));
		}
		
	}

	/*
	 * Metodo para carregar os Graus de Parentesco
	 * */
	public void consultaGrauParentesco() {
		try {
			UnidadeEscolarDAO unidadeEscolarDAO = new UnidadeEscolarDAO();			
			List<UnidadeEscolar> paramUnidadeEscolar = 
						unidadeEscolarDAO.consultaUnidadeEscolar(Integer.parseInt(alunoDados.getRedeEnsino()));
			
			for (UnidadeEscolar param : paramUnidadeEscolar){
			   SelectItem  s = new SelectItem();
			   s.setValue(param.getPkUnidadeEscolar());
			   s.setLabel(param.getNome());
			   comboUnidadeEscolar.add(s);
			}
		}catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao carregar os dados de UNIDADE ESCOLAR, contate o administrador do sistema!", null));
		}
		
	}
	
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
	 * Metodo para dar replace em valores de alguns campos
	 * 
	 * */
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
	public void carregaCombos(){
		consultaCargo();
		consultaEstaCivil();
		consultaGrauInstru();
		consultaNacionalidade();
		consultaRaca();
		consultaReligiao();
		consultaSituEconomica();
		consultaPais();
		consultaZonaResidencial();
		consultaTipoDeficiencia();
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
	public ImagemBase64 getBase64MB() {
		return base64MB;
	}

	public void setBase64MB(ImagemBase64 base64mb) {
		base64MB = base64mb;
	}

	public List<SelectItem> getComboPais() {
		return comboPais;
	}

	public void setComboPais(List<SelectItem> comboPais) {
		this.comboPais = comboPais;
	}

	public List<SelectItem> getComboEstado() {
		return comboEstado;
	}

	public void setComboEstado(List<SelectItem> comboEstado) {
		this.comboEstado = comboEstado;
	}

	public List<SelectItem> getComboCidade() {
		return comboCidade;
	}

	public void setComboCidade(List<SelectItem> comboCidade) {
		this.comboCidade = comboCidade;
	}

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

	public UploadedFile getFotoAluno() {
		return fotoAluno;
	}

	public void setFotoAluno(UploadedFile fotoAluno) {
		this.fotoAluno = fotoAluno;
	}

	public UploadedFile getImagemCertidaoNascimento() {
		return imagemCertidaoNascimento;
	}

	public void setImagemCertidaoNascimento(UploadedFile imagemCertidaoNascimento) {
		this.imagemCertidaoNascimento = imagemCertidaoNascimento;
	}

	public List<SelectItem> getComboGrauParentesco() {
		return comboGrauParentesco;
	}

	public void setComboGrauParentesco(List<SelectItem> comboGrauParentesco) {
		this.comboGrauParentesco = comboGrauParentesco;
	}
}

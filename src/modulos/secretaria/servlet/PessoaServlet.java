package modulos.secretaria.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

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
import modulos.secretaria.services.ContatoService;
import modulos.secretaria.services.EnderecoService;
import modulos.secretaria.services.PessoaService;
import modulos.sisEducar.utils.ConstantesSisEducar;
import modulos.sisEducar.utils.Logs;

@ManagedBean(name="pessoaServlet")
@ViewScoped
public class PessoaServlet implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{paramServlet}")
	private ParametrosServlet paramDados;
	
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
	private Usuario usuarioLogado;
	private GrauParentesco grauParentescoDados;
	private Estado estadoNascimentoDados;
	private Cidade cidadeNascimentoDados;
	private Pessoa pessoaSelecionada;
	private Pessoa pessoaDadosConsulta;
	
	/* Combo com valores de ESTADO */
	private List<SelectItem> comboEstado;
	
	/* Combo com valores de CIDADE */
	private List<SelectItem> comboCidade;
	
	/* Combo com valores de CIDADE DE NASCIMENTO */
	private List<SelectItem> comboCidadeNascimento;
	
	/* Componenete Tabela de consulta de Cadastros*/
	private HtmlDataTable dataTable;
	
	/* Lista de pessoas cadastradas */
	private List<Pessoa> listaConsultaPessoa;
	
	/* Habilita ou desabilita botão para deletar o cadastro */
	private Boolean deletarCadastro;
	
	/*  Habilita ou desabilita a modal de cadastro com sucesso */
	private Boolean cadastroSucesso;
	
	/* Habilita ou desabilita campos padroes da tela */
	private Boolean desabilitaCampos;
	
	/* Habilita ou desabilita campo CPF da tela*/
	private Boolean campoCPF;
	
	/* Habilita ou desabilita campo RG da tela*/
	private Boolean campoRG;
	
	/* Componente para validar idade da pessoa */
	private Boolean panelMenorIdade;
	
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
		if(this.grauParentescoDados == null) {
			this.grauParentescoDados = new GrauParentesco();
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
		panelMenorIdade = false;
		cadastroSucesso = false;
		desabilitaCampos = false;
		campoCPF = false;
		campoRG = false;
		
		deletarCadastro = false;
		comboCidade = new ArrayList<SelectItem>();
		comboCidadeNascimento = new ArrayList<SelectItem>();
		comboEstado = new ArrayList<SelectItem>();
		
		usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		
		pessoaDados.setFkMunicipioCliente(usuarioLogado.getFkMunicipioCliente());
	}

	/*
	 * Metodo para salvar o cadastro de Pessoa
	 * 
	 * */
	public void salvarCadastroPessoa() {
		try {
			/* Objeto com os atributos de endereco da pessoa */
			enderecoDados.setTipo("Residencial");
			enderecoDados.setFkMunicipioCliente(usuarioLogado.getFkMunicipioCliente());
			
			if( !new ContatoService().salvarContato(contatoDados)) {
				Logs.addError("Erro ao salvar contato.", null);
			} else {
				enderecoDados.setContato(contatoDados);
				if ( !new EnderecoService().salvarDadosEndereco(enderecoDados) ) {
					Logs.addError("Erro ao salvar endereco.", null);
				} else {
					pessoaDados.setEndereco(enderecoDados);
					pessoaDados.setCodigo(123);
					if( !new PessoaService().salvarCadastroPessoa( pessoaDados )) {
						Logs.addError("Erro ao salvar cadastro.", null);
					} else {
						limparFormulario();
						Logs.addInfo("Cadastro Salvo.", null);
						
					}
				}
			}
		} catch (Exception e) {
			Logs.addError("Erro ao salvar cadastro.", null);
		}
	}
	
	public void deletarCadastroPessoa() throws SQLException {
		try {
			if(pessoaDados.getPkPessoa() != null) {
				
				new PessoaService().deletarCadastroPessoa(pessoaDados.getPkPessoa());
				
				limparFormulario();
				Logs.addInfo("Cadastro deletado com sucesso", null);
			}
		} catch (Exception e) {
			Logs.addError("Erro ao deletar cadastro.", e.toString());
		}
	}	
	
	/*
	 * Metodo para salvar o cadastro de Pessoa
	 * 
	 * */
	public String atualizarCadastroPessoa() throws SQLException {
		if( !new ContatoService().atualizarDadosContato(contatoDados)) {
			Logs.addError("Erro ao atualizar dados.", null);
		} else {
			enderecoDados.setContato(contatoDados);
			if ( !new EnderecoService().atualizarDadosEndereco(enderecoDados) ) {
				Logs.addError("Erro ao atualizar endereco.", null);
			} else {
				pessoaDados.setEndereco(enderecoDados);
				if(pessoaDados.getSexo().equals("2")) {
					pessoaDados.setSexo(ConstantesSisEducar.GENERO_FEMININO);
				} else{
					if(pessoaDados.getSexo().equals("1")) {
						pessoaDados.setSexo(ConstantesSisEducar.GENERO_MASCULINO);
					}
				}
				if ( !new PessoaService().atualizarDadosPessoa(pessoaDados) ) {
					Logs.addError("Erro ao atualizar os dados da Pessoa.", null);
				} else {
					Logs.addInfo("Dados atualizados com sucesso!", null);
				}
			}
			
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
		
		if( panelMenorIdade == false  ) {
			if(pessoaDados.getCpf() == null  || pessoaDados.getCpf() == 0 ) {
				Logs.addWarning("O CPF deve ser preenchido.", null);
				pessoaDados.setCpf(null);
				return false;
			}
		}
		
		
		if( pessoaDados.getSexo() == null || pessoaDados.getSexo().equals("")) {
			Logs.addWarning("O SEXO deve ser informado.", null);
			return false;
		}

		if(estadoNascimentoDados.getPkEstado() == null) {
			Logs.addWarning("O ESTADO DE NASCIMENTO deve ser informado.", null);
			return false;
		}
		
		if(cidadeNascimentoDados.getPkCidade() == null) {
			Logs.addWarning("A CIDADE DE NASCIMENTO deve ser informada.", null);
			return false;
		} else {
			cidadeNascimentoDados.setEstado(estadoNascimentoDados);
			pessoaDados.setFkCidadeNascimento(cidadeNascimentoDados);
		}
		
		if( nacionalidadeDados.getPkNacionalidade() == null ) {
			Logs.addWarning("A NACIONALIDADE deve ser informada.", null);
			return false;
		} else {
			pessoaDados.setNacionalidade(nacionalidadeDados);
		}
		
		if( racaDados.getPkRaca() == null ) {
			Logs.addWarning("A RAÇA deve ser informada.", null);
			return false;
		} else {
			pessoaDados.setRaca(racaDados);
		}
		
		if( estaCivilDados.getPkEstadoCivil() == null ) {
			Logs.addWarning("O ESTADO CIVIL deve ser informado.", null);
			return false;
		} else {
			pessoaDados.setEstadoCivil(estaCivilDados);
		}
		
		if( grauInstruDados.getPkGrauInstrucao() == null ) {
			Logs.addWarning("O GRAU DE INSTRUÇÃO deve ser informado.", null);
			return false;
		} else {
			pessoaDados.setGrauInstrucao(grauInstruDados);
		}
		
		if( situEconomicaDados.getPkSituacaoEconomica() == null ){
			Logs.addWarning("A SITUAÇÃO ECONÔMICA deve ser informada.", null);
			return false;
		} else {
			pessoaDados.setSituacaoEconomica(situEconomicaDados);
		}
		
		if( religiaoDados.getPkReligiao() == null ) {
			Logs.addWarning("A RELIGIÃO deve ser informada.", null);
			return false;
		} else {
			pessoaDados.setReligiao(religiaoDados);
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
			Logs.addWarning("O MUNICÍPIO deve ser informado.", null);
			return false;
		} else {
			enderecoDados.setCidade(cidadeDados);
			enderecoDados.getCidade().setEstado(estadoDados);
			enderecoDados.getCidade().getEstado().setPais(paisDados);
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
		} else {
			enderecoDados.setRegiao(regiaoDados);
		}
		
		if( contatoDados.getTelResidencial() == null || contatoDados.getTelResidencial().equals("")) {
			Logs.addWarning("O TELEFONE DE CONTATO deve ser preenchido.", null);
			return false;
		}
		
		if( contatoDados.getTelCelular() == null || contatoDados.getTelCelular().equals("")) {
			Logs.addWarning("O TELEFONE CELULAR deve ser preenchido.", null);
			return false;
		}
		
		if( contatoDados.getEmail() == null || contatoDados.getEmail().equals("")) {
			Logs.addWarning("O EMAIL deve ser preenchido.", null);
			return false;
		}
		
		if( panelMenorIdade == true ) {
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
				if ( !new PessoaService().validaCadastroPessoa(pessoaDados) ){
					pessoaDados.setCpf(null);
					Logs.addWarning("CPF já cadastrado.",null);
				}
			}
		} catch (Exception e) {
			Logs.addError("Erro verificaCadastro()", e.toString());
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
				pessoaDados.setDataNascimento(null);
				Logs.addWarning("Informe uma data valida.",null);
			} else {
				if( idade < 18 ){
					panelMenorIdade = true;
				}else{
					panelMenorIdade = false;
				}
			}
		} else {
			panelMenorIdade = false;
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
		grauParentescoDados = new GrauParentesco();
		dataTable = new HtmlDataTable();
		estadoNascimentoDados = new Estado();
		cidadeNascimentoDados = new Cidade();
		pessoaSelecionada = new Pessoa();
		
		panelMenorIdade = false;
		campoCPF = false;
		campoRG = false;
		deletarCadastro = false;
		desabilitaCampos = false;
		
		listaConsultaPessoa = new ArrayList<Pessoa>();
		
	}

	public void consultaCadastro() {
		
		listaConsultaPessoa = new ArrayList<Pessoa>();
		pessoaDadosConsulta.setFkMunicipioCliente(usuarioLogado.getFkMunicipioCliente());
		listaConsultaPessoa = new PessoaService().consultaListaPessoa(pessoaDadosConsulta);
	}
	
	public void editarCadastro() {
		
		pessoaDados = new Pessoa();
		enderecoDados = new Endereco();
		contatoDados = new Contato();
		pessoaSelecionada = (Pessoa) dataTable.getRowData();
		
		if(pessoaSelecionada != null && pessoaSelecionada.getPkPessoa() != null) {
			pessoaDados = pessoaSelecionada;
			
			pessoaDados = new PessoaService().consultaCadastroPessoa(pessoaDados);
			enderecoDados = new EnderecoService().consultaDadosEndereco(pessoaDados.getEndereco().getPkEndereco());
			contatoDados = new ContatoService().consultaDadosContato(enderecoDados.getContato());
			
			nacionalidadeDados = pessoaDados.getNacionalidade();
			racaDados = pessoaDados.getRaca();
			estaCivilDados = pessoaDados.getEstadoCivil();
			grauInstruDados = pessoaDados.getGrauInstrucao();
			situEconomicaDados = pessoaDados.getSituacaoEconomica();
			religiaoDados = pessoaDados.getReligiao();
			regiaoDados = enderecoDados.getRegiao();
			cidadeNascimentoDados = pessoaDados.getFkCidadeNascimento();
			
			if( (pessoaDados.getCpfMae() != null && pessoaDados.getCpfMae() != 0) 
					|| (pessoaDados.getCpfResponsavel() != null && pessoaDados.getCpfResponsavel() != 0)
					|| (pessoaDados.getCpfPai() != null && pessoaDados.getCpfPai() != 0)) {
				
				panelMenorIdade = true;
				
				if( pessoaDados.getCpf() != null && pessoaDados.getCpf() != 0) {
					campoCPF = true;
				} else {
					campoCPF = false;
				}
				
				if( pessoaDados.getRg() != null && !pessoaDados.getRg().equals("")) {
					campoRG = true;
				} else {
					campoRG = false;
				}
			} else {
				panelMenorIdade = false;
				campoCPF = true;
				campoRG = true;
			}
			
			if (pessoaDados.getFkCidadeNascimento() != null) {
				if(pessoaDados.getFkCidadeNascimento().getEstado().getPkEstado() != null) {
					for(SelectItem selectItem : paramDados.getComboEstado()) {
						if(selectItem.getValue().equals(pessoaDados.getFkCidadeNascimento().getEstado().getPkEstado())) {
							estadoNascimentoDados = new Estado();
							estadoNascimentoDados.setPkEstado(pessoaDados.getFkCidadeNascimento().getEstado().getPkEstado());
							break;
						}
					}
				}
				
				if(estadoNascimentoDados != null && estadoNascimentoDados.getPkEstado() != null){
					if( comboCidadeNascimento == null || comboCidadeNascimento.isEmpty()) { 
						comboCidadeNascimento = paramDados.consultaCidade(estadoNascimentoDados); 
					}
					for(SelectItem selectItem : comboCidadeNascimento) {
						if(selectItem.getValue().equals(pessoaDados.getFkCidadeNascimento().getPkCidade())) {
							cidadeNascimentoDados = new Cidade();
							cidadeNascimentoDados.setPkCidade(pessoaDados.getFkCidadeNascimento().getPkCidade());
							break;
						}
					}
				}
			}
			
			
			if(enderecoDados.getCidade() != null) {
				if(enderecoDados.getCidade().getEstado().getPais().getPkPais() != null) {
					for(SelectItem selectItem : paramDados.getComboPais()) {
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
			desabilitaCampos = true;
			listaConsultaPessoa = new ArrayList<Pessoa>();
		}
	}
		
/* ------------------------------------------------------------------------------------------------------------------------ */
/* ---------------------------------Metodos utlizados na tela------------------------------------------------ */
	/*
	 * Metodo responsavel por validar o nome da MAE caso de menorr
	 * 
	 * */
	public void validaNomeMae(){
		
		if(pessoaDados.getCpfMae() != null && pessoaDados.getCpfMae() != 0){
			pessoaDados.setNomeMae( 
					new PessoaService().consultaNomeResponsavel(
						pessoaDados.getCpfMae(),
						ConstantesSisEducar.GENERO_FEMININO) ); 
			
			if(pessoaDados.getNomeMae() == null || pessoaDados.getNomeMae().equals("")){
				pessoaDados.setCpfMae(null);
				Logs.addWarning("CPF não encontrado.", null);			
			}
		} else {
			pessoaDados.setCpfMae(null);
			pessoaDados.setNomeMae(null);
		}
	}
	
	/*
	 * Metodo responsavel por validar o nome do PAI caso de menor
	 * 
	 * */
	public void validaNomePai(){
		if(pessoaDados.getCpfPai() != null && pessoaDados.getCpfPai() != 0 ){
			pessoaDados.setNomePai( 
					new PessoaService().consultaNomeResponsavel(
						pessoaDados.getCpfPai(),
						ConstantesSisEducar.GENERO_MASCULINO) ); 
			
			if(pessoaDados.getNomePai() == null && pessoaDados.getNomePai().equals("")){
				pessoaDados.setCpfPai(null);
				Logs.addWarning("CPF não encontrado.", null);				
			}
		} else {
			pessoaDados.setCpfPai(null);
			pessoaDados.setNomePai(null);
		}
	}
	
	/*
	 * Metodo responsavel por validar o nome do RESPONSAVEL caso de menor
	 * 
	 * */
	public void validaNomeResponsavel(){
		if(pessoaDados.getCpfResponsavel() != null && pessoaDados.getCpfResponsavel() != 0){
			pessoaDados.setNomeResponsavel( 
					new PessoaService().consultaNomeResponsavel(
						pessoaDados.getCpfResponsavel(),
						null) ); 
			
			if(pessoaDados.getNomeResponsavel() == null && pessoaDados.getNomeResponsavel().equals("")){
				pessoaDados.setCpfResponsavel(null);
				Logs.addWarning("CPF não encontrado.", null);				
			}
		} else {
			pessoaDados.setCpfResponsavel(null);
			pessoaDados.setNomeResponsavel(null);
		}
	}	
	
	/*
	 * Metodo para validar o tipo de cadastro
	 * 
	 * */
	public void validaCadastro() throws SQLException{
		if( validaDadosPessoa() ) {	
			if(pessoaDados.getPkPessoa() == null ) {					
				salvarCadastroPessoa();
			} else {
				if( pessoaDados.getPkPessoa() != null ) {
					atualizarCadastroPessoa();
				}
			}
		}
	}

			
    /* 
	 * Tabela de consulta de cadastro de Pessoa 
	 * * * * * * */
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
	
	/* ESTADO */
	public List<SelectItem> getComboEstado() {
		if(paisDados.getPkPais() != null) {			
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
	public List<SelectItem> getComboCidadeNascimento() {
		if(estadoNascimentoDados.getPkEstado() != null){
			comboCidadeNascimento.addAll(paramDados.consultaCidade(estadoNascimentoDados));
			return comboCidadeNascimento;
		}
		cidadeNascimentoDados.setPkCidade(null);
		comboCidadeNascimento.clear();		
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

	public Boolean getDesabilitaCampos() {
		return desabilitaCampos;
	}

	public void setDesabilitaCampos(Boolean desabilitaCampos) {
		this.desabilitaCampos = desabilitaCampos;
	}

	public Boolean getCampoCPF() {
		return campoCPF;
	}

	public void setCampoCPF(Boolean campoCPF) {
		this.campoCPF = campoCPF;
	}

	public Boolean getCampoRG() {
		return campoRG;
	}

	public void setCampoRG(Boolean campoRG) {
		this.campoRG = campoRG;
	}

	public Boolean getPanelMenorIdade() {
		return panelMenorIdade;
	}

	public void setPanelMenorIdade(Boolean panelMenorIdade) {
		this.panelMenorIdade = panelMenorIdade;
	}

	public ParametrosServlet getParamDados() {
		return paramDados;
	}

	public void setParamDados(ParametrosServlet paramDados) {
		this.paramDados = paramDados;
	}
}
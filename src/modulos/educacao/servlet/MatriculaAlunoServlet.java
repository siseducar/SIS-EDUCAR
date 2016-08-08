package modulos.educacao.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.model.SelectItem;

import modulos.educacao.dao.AlunoDAO;
import modulos.educacao.om.Aluno;
import modulos.secretaria.dao.EnderecoDAO;
import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.dao.UnidadeEscolarDAO;
import modulos.secretaria.om.Curso;
import modulos.secretaria.om.Endereco;
import modulos.secretaria.om.Etapa;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.RedeEnsino;
import modulos.secretaria.om.TipoDeficiencia;
import modulos.secretaria.om.Turno;
import modulos.secretaria.om.UnidadeEscolar;
import modulos.secretaria.servlet.ParametrosServlet;
import modulos.sisEducar.utils.Logs;

@ManagedBean(name="matriAlunoServlet")
@ViewScoped
public class MatriculaAlunoServlet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/* Atributos */
	private Pessoa pessoaDados;
	private Aluno alunoDados;
	private Endereco enderecoDados;
	private RedeEnsino redeEnsinoDados;
	private UnidadeEscolar unidadeEscolarDados;
	private Etapa etapaDados;
	private Curso cursoDados;
	private Turno turnoDados;
	private TipoDeficiencia tipoDeficienciaDados;
	private ParametrosServlet paramDados;
	private Pessoa pessoaSelecionada;
	
	private AlunoDAO alunoDAO;
	private PessoaDAO pessoaDAO;
	private EnderecoDAO enderecoDAO;
	
	/* Componente do tipo de deficiencia */
	private Boolean alunoDeficiente;
	
	/* Componente do tipo de deficiencia */
	private Boolean panelDadosAluno;
	
	/* Combo com valores de TIPO DE DEFICENCIA*/
	private List<SelectItem> comboTipoDeficiencia;
	
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
	
	/* Lista de pessoas cadastradas */
	private List<Pessoa> listaConsultaPessoa;
	
	/* Componenete Tabela de consutal */
	private HtmlDataTable dataTable;
	
	/* Componente para validar de Menor */
	private Boolean menorIdade;
	
	private String nomeResponsavel;
	
	private Integer quantidadeAlunosUnidade;
	
	private Integer quantidadeAlunosTurma;
	
	public MatriculaAlunoServlet() throws SQLException {
		if(this.pessoaDados == null){
			this.pessoaDados = new Pessoa();
		}
		if(this.alunoDados == null){
			this.alunoDados = new Aluno();
		}
		if(this.enderecoDados == null) {
			this.enderecoDados = new Endereco();
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
		if(this.alunoDAO == null) {
			this.alunoDAO = new AlunoDAO();
		}
		if(this.pessoaDAO == null) {
			this.pessoaDAO = new PessoaDAO();
		}
		if(this.enderecoDAO == null) {
			this.enderecoDAO = new EnderecoDAO();
		}
		if(this.paramDados == null) {
			this.paramDados = new ParametrosServlet();
		}
		if(this.dataTable == null) {
			this.dataTable = new HtmlDataTable();
		}
		if(this.pessoaSelecionada == null) {
			this.pessoaSelecionada = new Pessoa();
		}
		if(this.tipoDeficienciaDados == null) {
			this.tipoDeficienciaDados = new TipoDeficiencia();
		}
		
		comboTipoDeficiencia = new ArrayList<SelectItem>();
		comboRedeEnsino = new ArrayList<SelectItem>();
		comboUnidadeEscolar = new ArrayList<SelectItem>();
		comboCursoEscolar = new ArrayList<SelectItem>();
		comboEtapaEscolar = new ArrayList<SelectItem>();
		comboTurnoEscolar = new ArrayList<SelectItem>();
		
		carregaCombos();
		alunoDeficiente = false;
		panelDadosAluno = false;
		menorIdade = false;
		quantidadeAlunosUnidade = 0;
		quantidadeAlunosTurma = 0;
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
			Logs.addError("Erro ao realizar o cadastro.",null);
		}
		
		return null;
	}
	
	
	/*
	 * Metodo responsavel por carregar os combos da tela
	 * 
	 * */
	public void carregaCombos() throws SQLException{
		if(comboTipoDeficiencia.isEmpty()) {
			comboTipoDeficiencia.addAll(paramDados.consultaTipoDeficiencia());	
		}
		if(comboRedeEnsino.isEmpty()) {
			comboRedeEnsino.addAll(paramDados.consultaRedeEnsino());	
		}
		if(comboTipoDeficiencia.isEmpty()) {
			comboTipoDeficiencia.addAll(paramDados.consultaTipoDeficiencia());	
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
	 * Metodo para validar se ja existe o RM cadastrado
	 * */
	public void verificarAluno() throws SQLException {
		if(alunoDados.getRm() != null && !alunoDados.getRm().equals("")) {
			if(alunoDAO.consultaAluno(alunoDados.getRm())) {
				alunoDados.setRm(null);
				Logs.addWarning("RM já cadastrado.",null);
			}
		}
	}
	
	/*
	 * Metodo para validar os dados do Aluno
	 * */
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
		return true;
	}
	
	/*
	 * Consulta o cadastro da pessoa
	 * **/
	public void consultaCadastro() {
		listaConsultaPessoa = new ArrayList<Pessoa>();
		
		listaConsultaPessoa = pessoaDAO.obtemTodos();
	}
	
	public void editarCadastro() {
		pessoaSelecionada = (Pessoa) dataTable.getRowData();
		
		if(pessoaSelecionada != null && pessoaSelecionada.getPkPessoa() != null) {
			pessoaDados = pessoaSelecionada;
			
			pessoaDados = pessoaDAO.consultaCadastroPessoa(pessoaDados.getPkPessoa());
			enderecoDados = enderecoDAO.consultaEnderecoPessoa(pessoaDados.getEndereco().getPkEndereco());
			
			if( (pessoaDados.getCpfMae() != null && pessoaDados.getCpfMae() != 0) ||
				(pessoaDados.getCpfResponsavel() != null && pessoaDados.getCpfResponsavel() != 0) ) {
				
				if(pessoaDados.getCpfMae() != null && pessoaDados.getCpfMae() != 0) {
					nomeResponsavel = pessoaDados.getNomeMae();
				} else {
					nomeResponsavel = pessoaDados.getNomeResponsavel();
				}
				menorIdade = true;				
			} else {
				menorIdade = false;
				nomeResponsavel = null;
			}
		}
	}

	public void calculaAlunos() throws SQLException {
		if(unidadeEscolarDados.getPkUnidadeEscolar() != null && !comboUnidadeEscolar.isEmpty()) {			
			UnidadeEscolarDAO unidadeDAO = new UnidadeEscolarDAO();
			
			quantidadeAlunosUnidade = unidadeDAO.calculaQuantidadeAlunos(unidadeEscolarDados.getPkUnidadeEscolar());
		}
	}
	
	
	public void limparCadastro() throws SQLException {
		pessoaDados = new Pessoa();
		alunoDados = new Aluno();
		enderecoDados = new Endereco();
		redeEnsinoDados = new RedeEnsino();
		unidadeEscolarDados = new UnidadeEscolar();
		etapaDados = new Etapa();
		cursoDados = new Curso();
		turnoDados = new Turno();
		alunoDAO = new AlunoDAO();
		pessoaDAO = new PessoaDAO();
		enderecoDAO = new EnderecoDAO();
		paramDados = new ParametrosServlet();
		dataTable = new HtmlDataTable();
		pessoaSelecionada = new Pessoa();
		tipoDeficienciaDados = new TipoDeficiencia();
		
		alunoDeficiente = false;
		panelDadosAluno = false;
		menorIdade = false;
		quantidadeAlunosUnidade = 0;
		quantidadeAlunosTurma = 0;
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
	
/*-----------------------------------------------------------------------------------------------------*/	
	
	public Boolean getAlunoDeficiente() {
		return alunoDeficiente;
	}

	public void setAlunoDeficiente(Boolean alunoDeficiente) {
		this.alunoDeficiente = alunoDeficiente;
	}
	
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
		if(etapaDados != null && !comboEtapaEscolar.isEmpty()) {
			comboCursoEscolar.addAll(paramDados.consultaCursoEscolar(unidadeEscolarDados));
		}
		comboCursoEscolar.clear();
		return comboCursoEscolar;
	}

	public void setComboCursoEscolar(List<SelectItem> comboCursoEscolar) {
		this.comboCursoEscolar = comboCursoEscolar;
	}
	
	public List<SelectItem> getComboEtapaEscolar() {
		if(unidadeEscolarDados.getPkUnidadeEscolar() != null && !comboUnidadeEscolar.isEmpty()) {
			comboEtapaEscolar.addAll(paramDados.consultaEtapaEscolar(cursoDados));
		}
		comboEtapaEscolar.clear();
		return comboEtapaEscolar;
	}

	public void setComboEtapaEscolar(List<SelectItem> comboEtapaEscolar) {
		this.comboEtapaEscolar = comboEtapaEscolar;
	}

	public List<SelectItem> getComboTurnoEscolar() {
		if(cursoDados.getPkCurso() != null && !comboCursoEscolar.isEmpty()) {
			comboTurnoEscolar.addAll(paramDados.consultaTurnoEscolar(etapaDados));
		}
		comboTurnoEscolar.clear();
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
	
	public Aluno getAlunoDados() {
		return alunoDados;
	}

	public void setAlunoDados(Aluno alunoDados) {
		this.alunoDados = alunoDados;
	}

	public Boolean getPanelDadosAluno() {
		return panelDadosAluno;
	}
	
	public void setPanelDadosAluno(Boolean panelDadosAluno) {
		this.panelDadosAluno = panelDadosAluno;
	}

	public Pessoa getPessoaDados() {
		return pessoaDados;
	}

	public void setPessoaDados(Pessoa pessoaDados) {
		this.pessoaDados = pessoaDados;
	}
	
	public List<Pessoa> getListaConsultaPessoa() {
		return listaConsultaPessoa;
	}
	
	public void setListaConsultaPessoa(List<Pessoa> listaConsultaPessoa) {
		this.listaConsultaPessoa = listaConsultaPessoa;
	}
	
	public HtmlDataTable getDataTable() {
        return dataTable;
	}

	public void setDataTable(HtmlDataTable dataTable) {
		this.dataTable = dataTable;
	}


	public Endereco getEnderecoDados() {
		return enderecoDados;
	}


	public void setEnderecoDados(Endereco enderecoDados) {
		this.enderecoDados = enderecoDados;
	}


	public Boolean getMenorIdade() {
		return menorIdade;
	}


	public void setMenorIdade(Boolean menorIdade) {
		this.menorIdade = menorIdade;
	}


	public String getNomeResponsavel() {
		return nomeResponsavel;
	}


	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}


	public Integer getQuantidadeAlunosUnidade() {
		return quantidadeAlunosUnidade;
	}


	public void setQuantidadeAlunosUnidade(Integer quantidadeAlunosUnidade) {
		this.quantidadeAlunosUnidade = quantidadeAlunosUnidade;
	}


	public Integer getQuantidadeAlunosTurma() {
		return quantidadeAlunosTurma;
	}


	public void setQuantidadeAlunosTurma(Integer quantidadeAlunosTurma) {
		this.quantidadeAlunosTurma = quantidadeAlunosTurma;
	}
}

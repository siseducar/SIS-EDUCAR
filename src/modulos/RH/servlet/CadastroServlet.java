package modulos.RH.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

import modulos.RH.dao.ParametrosDAO;
import modulos.RH.om.Aluno;
import modulos.RH.om.Funcionario;
import modulos.RH.om.ParametrosSecretaria;
import modulos.RH.om.Pessoa;

@ManagedBean(name="cadastroServlet")
@ViewScoped
public class CadastroServlet implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/* Atributos */
	Pessoa pessoaDados;
	Funcionario funcionarioDados;
	Aluno alunoDados;
	ParametrosSecretaria paramDados;
	private int tipoPessoa = 0;
		
	
	/* Metodo Construtor */
	public CadastroServlet() throws SQLException{
		if(this.pessoaDados == null){
			this.pessoaDados = new Pessoa();
		}
		if(this.funcionarioDados == null){
			this.funcionarioDados = new Funcionario();
		}
		if(this.alunoDados == null){
			this.alunoDados = new Aluno();
		}
		if(this.paramDados == null){
			this.paramDados = new ParametrosSecretaria();
		}
	}
	
	/*
	 * Metodo responsavel por listar as RACAS
	 * */
	public List<SelectItem> getRaca() throws SQLException {
		
		ParametrosDAO paramDAO = new ParametrosDAO();
		
		List<ParametrosSecretaria> listaCor = paramDAO.consultaRaca();

		List<SelectItem> itens = new ArrayList<SelectItem>();

		for(ParametrosSecretaria p : listaCor){
			itens.add(new SelectItem(p.getDescriCor()));
		}
		return itens;
	}
	
	/* 
	 * Metodo responsavel por listar os Estados Civis
	 * */
	public List<SelectItem> getEstCivil() throws SQLException{
		
		ParametrosDAO paramDAO = new ParametrosDAO();
		
		List<ParametrosSecretaria> listaEstCivil = paramDAO.consultaEstaCivil();
		
		List<SelectItem> itens = new ArrayList<SelectItem>();
		
		for(ParametrosSecretaria p : listaEstCivil) {
			itens.add(new SelectItem(p.getDescriEstadoCivil()));
		}
		
		return itens;
	}
	
	/* 
	 * Metodo responsavel por listar o GRAU DE INSTRUCAO
	 * */
	public List<SelectItem> getGrauInstru() throws SQLException{
		
		ParametrosDAO paramDAO = new ParametrosDAO();
		
		List<ParametrosSecretaria> listaGrauInstru = paramDAO.consultaGrauInstru();
		
		List<SelectItem> itens = new ArrayList<SelectItem>();
		
		for(ParametrosSecretaria p : listaGrauInstru) {
			itens.add(new SelectItem(p.getDescriGrauIntrucao()));
		}
		
		return itens;
	}
	
	/* 
	 * Metodo responsavel por listar a NACIONALIDADE
	 * */
	public List<SelectItem> getNacionalidade() throws SQLException{
		
		ParametrosDAO paramDAO = new ParametrosDAO();
		
		List<ParametrosSecretaria> listaNacio = paramDAO.consultaNacionalidade();
		
		List<SelectItem> itens = new ArrayList<SelectItem>();
		
		for(ParametrosSecretaria p : listaNacio) {
			itens.add(new SelectItem(p.getDescriNacionalidade()));
		}
		
		return itens;
	}
	
	/*
	 * Metodo responsavel por listar as RACAS
	 * */
	public List<SelectItem> getReligiao() throws SQLException {
		
		ParametrosDAO paramDAO = new ParametrosDAO();
		
		List<ParametrosSecretaria> listaReligiao = paramDAO.consultaReligiao();

		List<SelectItem> itens = new ArrayList<SelectItem>();

		for(ParametrosSecretaria p : listaReligiao){
			itens.add(new SelectItem(p.getDescriReligiao()));
		}
		return itens;
	}
	
	/* GETTERS AND SETTERS */
	public Pessoa getPessoaDados() {
		return pessoaDados;
	}

	public Funcionario getFuncionarioDados() {
		return funcionarioDados;
	}

	public Aluno getAlunoDados() {
		return alunoDados;
	}

	public void setPessoaDados(Pessoa pessoaDados) {
		this.pessoaDados = pessoaDados;
	}

	public void setFuncionarioDados(Funcionario funcionarioDados) {
		this.funcionarioDados = funcionarioDados;
	}

	public void setAlunoDados(Aluno alunoDados) {
		this.alunoDados = alunoDados;
	}

	public ParametrosSecretaria getParamDados() {
		return paramDados;
	}

	public void setParamDados(ParametrosSecretaria paramDados) {
		this.paramDados = paramDados;
	}

	public int getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(int tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
}

package modulos.RH.servlet;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modulos.RH.om.Aluno;
import modulos.RH.om.Fornecedor;
import modulos.RH.om.Funcionario;
import modulos.RH.om.Pessoa;

@ManagedBean(name="cadastroPessoaServlet")
@ViewScoped
public class CadastroPessoaServlet implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/* Atributos */
	Pessoa pessoaDados;
	Aluno alunoDados;
	Fornecedor fornecedorDados;
	Funcionario funcionarioDados;

	/* Construtor */
	public CadastroPessoaServlet() {
		pessoaDados = new Pessoa();
		alunoDados = new Aluno();
		fornecedorDados = new Fornecedor();
		funcionarioDados = new Funcionario();
	}
	
	public void tipoPessoa(){
		System.out.println(pessoaDados.getNome());
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
}

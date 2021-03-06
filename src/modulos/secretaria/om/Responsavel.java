package modulos.secretaria.om;

import java.io.Serializable;

import modulos.educacao.om.Aluno;

public class Responsavel implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	private int pkResponsavel;
	private Aluno aluno;
	private Pessoa pessoa;
	private int status = 0;
	
	public int getPkResponsavel() {
		return pkResponsavel;
	}
	public void setPkResponsavel(int pkResponsavel) {
		this.pkResponsavel = pkResponsavel;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}

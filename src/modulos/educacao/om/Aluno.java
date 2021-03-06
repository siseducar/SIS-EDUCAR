package modulos.educacao.om;

import java.io.Serializable;

import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Curso;
import modulos.secretaria.om.Etapa;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.RedeEnsino;
import modulos.secretaria.om.TipoDeficiencia;
import modulos.secretaria.om.Turno;
import modulos.secretaria.om.UnidadeEscolar;

public class Aluno implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	private int pkAluno;
	private String codigoInep;
	private String ra;
	private String ra2;
	private String folha;
	private String livro;
	private Integer registro;
	private Pessoa pessoa;
	private String livroUF;
	private Byte certiNascimento;
	private Cidade cidadeNascimento;
	private Byte comproResidencia;
	private String rm;
	private RedeEnsino redeEnsino;
	private UnidadeEscolar unidadeEscolar;
	private Etapa etapa;
	private Curso curso;
	private Turno turno;
	private TipoDeficiencia deficiencia;
	private Boolean alunoDeficiente;

	public String getRa2() {
		return ra2;
	}
	public void setRa2(String ra2) {
		this.ra2 = ra2;
	}
	public String getFolha() {
		return folha;
	}
	public void setFolha(String folha) {
		this.folha = folha;
	}
	public String getLivro() {
		return livro;
	}
	public void setLivro(String livro) {
		this.livro = livro;
	}
	public Integer getRegistro() {
		return registro;
	}
	public void setRegistro(Integer registro) {
		this.registro = registro;
	}
	public String getLivroUF() {
		return livroUF;
	}
	public void setLivroUF(String livroUF) {
		this.livroUF = livroUF;
	}
	public String getRa() {
		return ra;
	}
	public void setRa(String ra) {
		this.ra = ra;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public int getPkAluno() {
		return pkAluno;
	}
	public void setPkAluno(int pkAluno) {
		this.pkAluno = pkAluno;
	}
	public Byte getCertiNascimento() {
		return certiNascimento;
	}
	public void setCertiNascimento(Byte certiNascimento) {
		this.certiNascimento = certiNascimento;
	}
	public Byte getComproResidencia() {
		return comproResidencia;
	}
	public void setComproResidencia(Byte comproResidencia) {
		this.comproResidencia = comproResidencia;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
	}
	public Boolean getAlunoDeficiente() {
		return alunoDeficiente;
	}
	public void setAlunoDeficiente(Boolean alunoDeficiente) {
		this.alunoDeficiente = alunoDeficiente;
	}
	public RedeEnsino getRedeEnsino() {
		return redeEnsino;
	}
	public void setRedeEnsino(RedeEnsino redeEnsino) {
		this.redeEnsino = redeEnsino;
	}
	public UnidadeEscolar getUnidadeEscolar() {
		return unidadeEscolar;
	}
	public void setUnidadeEscolar(UnidadeEscolar unidadeEscolar) {
		this.unidadeEscolar = unidadeEscolar;
	}
	public Etapa getEtapa() {
		return etapa;
	}
	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	public Turno getTurno() {
		return turno;
	}
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	public TipoDeficiencia getDeficiencia() {
		return deficiencia;
	}
	public void setDeficiencia(TipoDeficiencia deficiencia) {
		this.deficiencia = deficiencia;
	}
	public String getCodigoInep() {
		return codigoInep;
	}
	public void setCodigoInep(String codigoInep) {
		this.codigoInep = codigoInep;
	}
	public Cidade getCidadeNascimento() {
		return cidadeNascimento;
	}
	public void setCidadeNascimento(Cidade cidadeNascimento) {
		this.cidadeNascimento = cidadeNascimento;
	}
}

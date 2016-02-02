package modulos.RH.om;

public class Aluno 
{
	private int pkAluno;
	private String ra;
	private String ra2;
	private String folha;
	private String livro;
	private Integer registro;
	private Pessoa pessoa;
	private Cidade cidadeNascimento;
	private String livroUF;
	private String nomePai;
	private String nomeMae;

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
	public Cidade getCidadeNascimento() {
		return cidadeNascimento;
	}
	public void setCidadeNascimento(Cidade cidadeNascimento) {
		this.cidadeNascimento = cidadeNascimento;
	}
	public String getLivroUF() {
		return livroUF;
	}
	public void setLivroUF(String livroUF) {
		this.livroUF = livroUF;
	}
	public String getNomePai() {
		return nomePai;
	}
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
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
}

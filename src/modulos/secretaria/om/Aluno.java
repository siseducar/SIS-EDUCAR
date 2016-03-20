package modulos.secretaria.om;

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
	private Long cpfPai;
	private String nomeMae;
	private Long cpfMae;
	private String nomeResponsavel;
	private Long cpfResponsavel;
	private Byte certiNascimento;
	private Byte comproResidencia;
	private String rm;
	private byte[] fotoAluno;
	private String redeEnsino;
	private String unidadeEscolar;
	private String etapa;
	private String curso;
	private String turno;
	private Boolean alunoDeficiente;
	private String deficiencia;
	
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
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
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
	public String getRedeEnsino() {
		return redeEnsino;
	}
	public void setRedeEnsino(String redeEnsino) {
		this.redeEnsino = redeEnsino;
	}
	public String getUnidadeEscolar() {
		return unidadeEscolar;
	}
	public void setUnidadeEscolar(String unidadeEscolar) {
		this.unidadeEscolar = unidadeEscolar;
	}
	public String getEtapa() {
		return etapa;
	}
	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public Boolean getAlunoDeficiente() {
		return alunoDeficiente;
	}
	public void setAlunoDeficiente(Boolean alunoDeficiente) {
		this.alunoDeficiente = alunoDeficiente;
	}
	public String getDeficiencia() {
		return deficiencia;
	}
	public void setDeficiencia(String deficiencia) {
		this.deficiencia = deficiencia;
	}
	public Long getCpfPai() {
		return cpfPai;
	}
	public void setCpfPai(Long cpfPai) {
		this.cpfPai = cpfPai;
	}
	public Long getCpfMae() {
		return cpfMae;
	}
	public void setCpfMae(Long cpfMae) {
		this.cpfMae = cpfMae;
	}
	public Long getCpfResponsavel() {
		return cpfResponsavel;
	}
	public void setCpfResponsavel(Long cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}
	public byte[] getFotoAluno() {
		return fotoAluno;
	}
	public void setFotoAluno(byte[] fotoAluno) {
		this.fotoAluno = fotoAluno;
	}
}

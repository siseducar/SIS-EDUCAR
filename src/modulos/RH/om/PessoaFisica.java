package modulos.RH.om;

import sun.util.calendar.BaseCalendar.Date;

public class PessoaFisica 
{
	private Integer pkPessoaFisica;
	private String nome;
	private String cpf;
	private String rg;
	private String rgOrgaoEmissor;
	private Date rgDataEmissao;
	private Date dataNascimento;
	private String nacionalidade;
	private int sexo;
	private String profissao;
	private String formacaoAcademica;
	private String estadoCivil;
	private Date dataRegime;
	private String regime;
	private Boolean falecido;
	private Date dataFalecimento;
	private Boolean semCPF;
	
	public PessoaFisica() {}
	
	/* Getters and Setters*/
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	public String getCpf() { return cpf; }
	public void setCpf(String cpf) { this.cpf = cpf; }
	public String getRg() { return rg; }
	public void setRg(String rg) { this.rg = rg; }
	public String getRgOrgaoEmissor() { return rgOrgaoEmissor; }
	public void setRgOrgaoEmissor(String rgOrgaoEmissor) { this.rgOrgaoEmissor = rgOrgaoEmissor; }
	public Date getRgDataEmissao() { return rgDataEmissao; }
	public void setRgDataEmissao(Date rgDataEmissao) { this.rgDataEmissao = rgDataEmissao; }
	public Date getDataNascimento() { return dataNascimento; }
	public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
	public String getNacionalidade() { return nacionalidade; }
	public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }
	public int getSexo() { return sexo; }
	public void setSexo(int sexo) { this.sexo = sexo; }
	public String getProfissao() { return profissao; }
	public void setProfissao(String profissao) { this.profissao = profissao; }
	public String getFormacaoAcademica() { return formacaoAcademica; }
	public void setFormacaoAcademica(String formacaoAcademica) { this.formacaoAcademica = formacaoAcademica; }
	public String getEstadoCivil() { return estadoCivil; }
	public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }
	public Date getDataRegime() { return dataRegime; }
	public void setDataRegime(Date dataRegime) { this.dataRegime = dataRegime; }
	public String getRegime() { return regime; }
	public void setRegime(String regime) { this.regime = regime; }
	public Boolean getFalecido() { return falecido; }
	public void setFalecido(Boolean falecido) { this.falecido = falecido; }
	public Date getDataFalecimento() { return dataFalecimento; }
	public void setDataFalecimento(Date dataFalecimento) { this.dataFalecimento = dataFalecimento; }
	public Boolean getSemCPF() { return semCPF; }
	public void setSemCPF(Boolean semCPF) { this.semCPF = semCPF; }
	public Integer getPkPessoaFisica() { return pkPessoaFisica; }
	public void setPkPessoaFisica(Integer pkPessoaFisica) {	this.pkPessoaFisica = pkPessoaFisica; }
}

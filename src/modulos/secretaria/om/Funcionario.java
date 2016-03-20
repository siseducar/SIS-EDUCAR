package modulos.secretaria.om;

import java.sql.Date;

public class Funcionario 
{
	private Integer pkFuncionario;
	private String matricula;
	private String numeroContrato;
	private String vinculoEmpregaticio;
	private String codigoAdmissao;
	private Date dataAdmissao;
	private String sitOcuAtual;
	private String SedeAtual;
	private String codigoRecisao;
	private Date dataRecisao;
	private Double cargaSemanal;
	private Double cargaMensal;
	private Boolean aposentado;
	private Date dataAposentadoria;
	private String numeroInscricao;
	private Boolean concursado;
	private String nomeConcurso;
	private Integer numContraConcurso;
	private Date dataConcurso;
	private Integer posicao;
	private Pessoa pessoa;
	private Cargo cargo;
	
	public Integer getPkFuncionario() {
		return pkFuncionario;
	}
	public void setPkFuncionario(Integer pkFuncionario) {
		this.pkFuncionario = pkFuncionario;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNumeroContrato() {
		return numeroContrato;
	}
	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}
	public String getVinculoEmpregaticio() {
		return vinculoEmpregaticio;
	}
	public void setVinculoEmpregaticio(String vinculoEmpregaticio) {
		this.vinculoEmpregaticio = vinculoEmpregaticio;
	}
	public String getCodigoAdmissao() {
		return codigoAdmissao;
	}
	public void setCodigoAdmissao(String codigoAdmissao) {
		this.codigoAdmissao = codigoAdmissao;
	}
	public Date getDataAdmissao() {
		return dataAdmissao;
	}
	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	public String getSitOcuAtual() {
		return sitOcuAtual;
	}
	public void setSitOcuAtual(String sitOcuAtual) {
		this.sitOcuAtual = sitOcuAtual;
	}
	public String getSedeAtual() {
		return SedeAtual;
	}
	public void setSedeAtual(String sedeAtual) {
		SedeAtual = sedeAtual;
	}
	public String getCodigoRecisao() {
		return codigoRecisao;
	}
	public void setCodigoRecisao(String codigoRecisao) {
		this.codigoRecisao = codigoRecisao;
	}
	public Date getDataRecisao() {
		return dataRecisao;
	}
	public void setDataRecisao(Date dataRecisao) {
		this.dataRecisao = dataRecisao;
	}
	public Double getCargaSemanal() {
		return cargaSemanal;
	}
	public void setCargaSemanal(Double cargaSemanal) {
		this.cargaSemanal = cargaSemanal;
	}
	public Double getCargaMensal() {
		return cargaMensal;
	}
	public void setCargaMensal(Double cargaMensal) {
		this.cargaMensal = cargaMensal;
	}
	public Boolean getAposentado() {
		return aposentado;
	}
	public void setAposentado(Boolean aposentado) {
		this.aposentado = aposentado;
	}
	public Date getDataAposentadoria() {
		return dataAposentadoria;
	}
	public void setDataAposentadoria(Date dataAposentadoria) {
		this.dataAposentadoria = dataAposentadoria;
	}
	public String getNumeroInscricao() {
		return numeroInscricao;
	}
	public void setNumeroInscricao(String numeroInscricao) {
		this.numeroInscricao = numeroInscricao;
	}
	public Boolean getConcursado() {
		return concursado;
	}
	public void setConcursado(Boolean concursado) {
		this.concursado = concursado;
	}
	public Date getDataConcurso() {
		return dataConcurso;
	}
	public void setDataConcurso(Date dataConcurso) {
		this.dataConcurso = dataConcurso;
	}
	public Integer getPosicao() {
		return posicao;
	}
	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	public Integer getNumContraConcurso() {
		return numContraConcurso;
	}
	public void setNumContraConcurso(Integer numContraConcurso) {
		this.numContraConcurso = numContraConcurso;
	}
	public String getNomeConcurso() {
		return nomeConcurso;
	}
	public void setNomeConcurso(String nomeConcurso) {
		this.nomeConcurso = nomeConcurso;
	}
}

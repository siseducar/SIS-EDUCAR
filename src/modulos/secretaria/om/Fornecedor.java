package modulos.secretaria.om;

import java.io.Serializable;

public class Fornecedor implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	private Integer pkFornecedor;
	private String codFornecedor;
	private String razaoSocial;
	private String nomeFantasia;
	private String cnpj;
	private String siteEmpresa;
	private String observacao;
	private Estado estadoInscricao;
	private String numInscriEstadual;
	private Cidade cidadeInscricao;
	private String numInscriMunicipal;
	private Integer status;
	private Pessoa pessoa;
	private Endereco endereco;
	private Cidade fkMunicipioCliente;
	
	/* GETTERS AND SETTERS */
	public Integer getPkFornecedor() {
		return pkFornecedor;
	}
	public void setPkFornecedor(Integer pkFornecedor) {
		this.pkFornecedor = pkFornecedor;
	}
	public String getCodFornecedor() {
		return codFornecedor;
	}
	public void setCodFornecedor(String codFornecedor) {
		this.codFornecedor = codFornecedor;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getSiteEmpresa() {
		return siteEmpresa;
	}
	public void setSiteEmpresa(String siteEmpresa) {
		this.siteEmpresa = siteEmpresa;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Estado getEstadoInscricao() {
		return estadoInscricao;
	}
	public void setEstadoInscricao(Estado estadoInscricao) {
		this.estadoInscricao = estadoInscricao;
	}
	public String getNumInscriEstadual() {
		return numInscriEstadual;
	}
	public void setNumInscriEstadual(String numInscriEstadual) {
		this.numInscriEstadual = numInscriEstadual;
	}
	public Cidade getCidadeInscricao() {
		return cidadeInscricao;
	}
	public void setCidadeInscricao(Cidade cidadeInscricao) {
		this.cidadeInscricao = cidadeInscricao;
	}
	public String getNumInscriMunicipal() {
		return numInscriMunicipal;
	}
	public void setNumInscriMunicipal(String numInscriMunicipal) {
		this.numInscriMunicipal = numInscriMunicipal;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Cidade getFkMunicipioCliente() {
		return fkMunicipioCliente;
	}
	public void setFkMunicipioCliente(Cidade fkMunicipioCliente) {
		this.fkMunicipioCliente = fkMunicipioCliente;
	}
}

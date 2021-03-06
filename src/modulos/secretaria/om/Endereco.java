package modulos.secretaria.om;

import java.io.Serializable;

public class Endereco implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	private Integer pkEndereco;
	private String logradouro;
	private String bairro;
	private String complemento;
	private String numero;
	private Integer cep;
	private String tipo;
	private Integer status;
	private Cidade cidade;
	private Double latitude;
	private Double longitude;
	private String enderecoCompleto;
	private Regiao regiao;
	private TipoLogradouro tipologradouro;
	private Cidade fkMunicipioCliente;
	private Contato contato;
	
	public Integer getPkEndereco() {
		return pkEndereco;
	}
	public void setPkEndereco(Integer pkEndereco) {
		this.pkEndereco = pkEndereco;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Integer getCep() {
		return cep;
	}
	public void setCep(Integer cep) {
		this.cep = cep;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public Cidade getFkMunicipioCliente() {
		return fkMunicipioCliente;
	}
	public void setFkMunicipioCliente(Cidade fkMunicipioCliente) {
		this.fkMunicipioCliente = fkMunicipioCliente;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getEnderecoCompleto() {
		return enderecoCompleto;
	}
	public void setEnderecoCompleto(String enderecoCompleto) {
		this.enderecoCompleto = enderecoCompleto;
	}
	public Contato getContato() {
		return contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
	}
	public Regiao getRegiao() {
		return regiao;
	}
	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}
	public TipoLogradouro getTipologradouro() {
		return tipologradouro;
	}
	public void setTipologradouro(TipoLogradouro tipologradouro) {
		this.tipologradouro = tipologradouro;
	}
}

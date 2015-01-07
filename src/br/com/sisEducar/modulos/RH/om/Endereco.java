package br.com.sisEducar.modulos.RH.om;

public class Endereco 
{
	private String apelido;
	private String logradouro;
	private String numero;
	private String cep;
	private String complemento;
	private String bairro;
	private boolean padrao;
	private int tipoLogradouro;
	private String pontoReferencia;
	private Cidade cidade;
	private TipoEndereco tipoEndereco;
	private Pessoa pessoa;
	
	public Endereco()
	{
		cidade = new Cidade();
		tipoEndereco = new TipoEndereco();
		pessoa = new Pessoa();
	}

	/* Getters and Setters*/
	public String getApelido() {return apelido;}
	public void setApelido(String apelido) {this.apelido = apelido;}
	public String getLogradouro() {return logradouro;}
	public void setLogradouro(String logradouro) {this.logradouro = logradouro;}
	public String getNumero() {return numero;}
	public void setNumero(String numero) {this.numero = numero;}
	public String getCep() {return cep;}
	public void setCep(String cep) {this.cep = cep;}
	public String getComplemento() {return complemento;}
	public String getBairro() {return bairro;}
	public void setBairro(String bairro) {this.bairro = bairro;}
	public boolean isPadrao() {return padrao;}
	public void setPadrao(boolean padrao) {this.padrao = padrao;}
	public int getTipoLogradouro() {return tipoLogradouro;}
	public void setTipoLogradouro(int tipoLogradouro) {this.tipoLogradouro = tipoLogradouro;}
	public String getPontoReferencia() {return pontoReferencia;}
	public void setPontoReferencia(String pontoReferencia) {this.pontoReferencia = pontoReferencia;}
	public Cidade getCidade() {return cidade;}
	public void setCidade(Cidade cidade) {this.cidade = cidade;}
	public TipoEndereco getTipoEndereco() {	return tipoEndereco;}
	public void setTipoEndereco(TipoEndereco tipoEndereco) {this.tipoEndereco = tipoEndereco;}
	public Pessoa getPessoa() {return pessoa;}
	public void setPessoa(Pessoa pessoa) {this.pessoa = pessoa;}
}

package modulos.RH.om;

import java.sql.Date;
import java.util.List;

public class Pessoa
{
	private int pkPessoa;
	private String nome;
	private String nomeFantasia;
	private Boolean semCpf;
	private String cpf;
	private String cnpj;
	private String rg;
	private Date dataNascimento;
	private Date dataCadastro;
	private int sexo;
	private String endereco;
	private String enderecoNumero;
	private String cep;
	private String estado;
	private String cidade;
	private int telefoneComercial;
	private int telefoneResidencial;
	private int telefoneCelular;
	private int tipoPessoa;
	private Boolean falecido;
	private Date dataFalecimento;
	private int status = 0;
	private List<Aluno> alunos;
	
	//Getters and setters
	public int getPkPessoa() {
		return pkPessoa;
	}
	public void setPkPessoa(int pkPessoa) {
		this.pkPessoa = pkPessoa;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getEnderecoNumero() {
		return enderecoNumero;
	}
	public void setEnderecoNumero(String enderecoNumero) {
		this.enderecoNumero = enderecoNumero;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public int getTelefoneComercial() {
		return telefoneComercial;
	}
	public void setTelefoneComercial(int telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}
	public int getTelefoneResidencial() {
		return telefoneResidencial;
	}
	public void setTelefoneResidencial(int telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}
	public int getTelefoneCelular() {
		return telefoneCelular;
	}
	public void setTelefoneCelular(int telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}
	public int getTipoPessoa() {
		return tipoPessoa;
	}
	public void setTipoPessoa(int tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	public Boolean getFalecido() {
		return falecido;
	}
	public void setFalecido(Boolean falecido) {
		this.falecido = falecido;
	}
	public Date getDataFalecimento() {
		return dataFalecimento;
	}
	public void setDataFalecimento(Date dataFalecimento) {
		this.dataFalecimento = dataFalecimento;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<Aluno> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
	public Boolean getSemCpf() {
		return semCpf;
	}
	public void setSemCpf(Boolean semCpf) {
		this.semCpf = semCpf;
	}
	public int getSexo() {
		return sexo;
	}
	public void setSexo(int sexo) {
		this.sexo = sexo;
	}
}

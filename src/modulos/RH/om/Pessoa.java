package modulos.RH.om;

import java.sql.Date;

public class Pessoa
{
	private int pkPessoa;
	private int codigo;
	private String nome;
	private String cpf;
	private String rg;
	private Date dataNascimento;
	private Date dataCadastro;
	private String sexo;
	private int telefoneComercial;
	private int telefoneResidencial;
	private int telefoneCelular;
	private int tipoPessoa;
	private Boolean falecido;
	private Date dataFalecimento;
	private int status;
	private String email;
	private Raca raca;
	private SituacaoEconomica situacaoEconomica;
	private Religiao religiao;
	private TipoDeficiencia tipoDeficiencia;
	private Regiao regiao;
	private Nacionalidade nacionalidade;
	private EstadoCivil estadoCivil;
	private Turno turno;
	private GrauInstrucao grauInstrucao;
	private UnidadeEscolar unidadeEscolar;
	private Endereco endereco;
	private Pais pais;
	private Estado estado;
	
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
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
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
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
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public UnidadeEscolar getUnidadeEscolar() {
		return unidadeEscolar;
	}
	public void setUnidadeEscolar(UnidadeEscolar unidadeEscolar) {
		this.unidadeEscolar = unidadeEscolar;
	}
	public Raca getRaca() {
		return raca;
	}
	public void setRaca(Raca raca) {
		this.raca = raca;
	}
	public SituacaoEconomica getSituacaoEconomica() {
		return situacaoEconomica;
	}
	public void setSituacaoEconomica(SituacaoEconomica situacaoEconomica) {
		this.situacaoEconomica = situacaoEconomica;
	}
	public Religiao getReligiao() {
		return religiao;
	}
	public void setReligiao(Religiao religiao) {
		this.religiao = religiao;
	}
	public TipoDeficiencia getTipoDeficiencia() {
		return tipoDeficiencia;
	}
	public void setTipoDeficiencia(TipoDeficiencia tipoDeficiencia) {
		this.tipoDeficiencia = tipoDeficiencia;
	}
	public Regiao getRegiao() {
		return regiao;
	}
	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}
	public Nacionalidade getNacionalidade() {
		return nacionalidade;
	}
	public void setNacionalidade(Nacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public Turno getTurno() {
		return turno;
	}
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	public GrauInstrucao getGrauInstrucao() {
		return grauInstrucao;
	}
	public void setGrauInstrucao(GrauInstrucao grauInstrucao) {
		this.grauInstrucao = grauInstrucao;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

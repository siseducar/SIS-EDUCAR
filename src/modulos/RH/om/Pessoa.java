package modulos.RH.om;

import java.sql.Date;

public class Pessoa
{	
	private Integer pkPessoa;
	private Integer codigo;
	private String nome;
	private String cpf;
	private String rg;
	private Date dataNascimento;
	private Date dataCadastro;
	private String sexo;
	private Integer telefoneComercial;
	private Integer telefoneResidencial;
	private Integer telefoneCelular;
	private Integer tipoPessoa;
	private Boolean falecido;
	private Date dataFalecimento;
	private Integer status;
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
	
	/* GETTERS AND SETTERS */
	public Integer getPkPessoa() {
		return pkPessoa;
	}
	public void setPkPessoa(Integer pkPessoa) {
		this.pkPessoa = pkPessoa;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Integer getTelefoneComercial() {
		return telefoneComercial;
	}
	public void setTelefoneComercial(Integer telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}
	public Integer getTelefoneResidencial() {
		return telefoneResidencial;
	}
	public void setTelefoneResidencial(Integer telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}
	public Integer getTelefoneCelular() {
		return telefoneCelular;
	}
	public void setTelefoneCelular(Integer telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}
	public Integer getTipoPessoa() {
		return tipoPessoa;
	}
	public void setTipoPessoa(Integer tipoPessoa) {
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public UnidadeEscolar getUnidadeEscolar() {
		return unidadeEscolar;
	}
	public void setUnidadeEscolar(UnidadeEscolar unidadeEscolar) {
		this.unidadeEscolar = unidadeEscolar;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	

}

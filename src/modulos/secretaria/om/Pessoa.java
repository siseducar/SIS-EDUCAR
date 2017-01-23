package modulos.secretaria.om;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entidade que representa PESSOA 
 * @author Michael
 */
@Entity
public class Pessoa implements Serializable {	

	private static final long serialVersionUID = 1L;
	
	private Integer pkPessoa;
	private Integer codigo;
	private String nome;
	private Long cpf;
	private String rg;
	private Date dataNascimento;
	private Date dataCadastro;
	private String sexo;
	private Integer tipoPessoa;
	private Boolean falecido;
	private Date dataFalecimento;
	private Integer status;
	private String nomePai;
	private Long cpfPai;
	private String nomeMae;
	private Long cpfMae;
	private String nomeResponsavel;
	private Long cpfResponsavel;
	private Raca raca;
	private SituacaoEconomica situacaoEconomica;
	private Religiao religiao;
	private TipoDeficiencia tipoDeficiencia;
	private Nacionalidade nacionalidade;
	private EstadoCivil estadoCivil;
	private Turno turno;
	private GrauInstrucao grauInstrucao;
	private GrauParentesco grauParentesco;
	private UnidadeEscolar unidadeEscolar;
	private Endereco endereco;
	private Cidade fkMunicipioCliente;
	private Cidade fkCidadeNascimento;

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
	public Long getCpf() {
		return cpf;
	}
	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}
	public Cidade getFkMunicipioCliente() {
		return fkMunicipioCliente;
	}
	public void setFkMunicipioCliente(Cidade fkMunicipioCliente) {
		this.fkMunicipioCliente = fkMunicipioCliente;
	}
	public String getNomePai() {
		return nomePai;
	}
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}
	public Long getCpfPai() {
		return cpfPai;
	}
	public void setCpfPai(Long cpfPai) {
		this.cpfPai = cpfPai;
	}
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	public Long getCpfMae() {
		return cpfMae;
	}
	public void setCpfMae(Long cpfMae) {
		this.cpfMae = cpfMae;
	}
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}
	public Long getCpfResponsavel() {
		return cpfResponsavel;
	}
	public void setCpfResponsavel(Long cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}
	public GrauParentesco getGrauParentesco() {
		return grauParentesco;
	}
	public void setGrauParentesco(GrauParentesco grauParentesco) {
		this.grauParentesco = grauParentesco;
	}
	public Cidade getFkCidadeNascimento() {
		return fkCidadeNascimento;
	}
	public void setFkCidadeNascimento(Cidade fkCidadeNascimento) {
		this.fkCidadeNascimento = fkCidadeNascimento;
	}
}

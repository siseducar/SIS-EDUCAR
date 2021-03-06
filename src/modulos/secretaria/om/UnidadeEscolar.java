package modulos.secretaria.om;

import java.io.Serializable;
import java.util.List;

import modulos.educacao.om.Horario;

public class UnidadeEscolar implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	private Integer pkUnidadeEscolar;
	private String codigo;
	private String codigoFederal;
	private String nome;
	private int status;
	private Boolean unidadeControlada = false;
	private Boolean unidadeInformatizada = false;
	private RedeEnsino redeEnsino;
	private Endereco endereco;
	private TipoOcupacao tipoOcupacao;
	private SituacaoFuncionamento situacaoFuncionamento;
	private Terreno terreno;
	private Regiao regiao;
	private Pessoa diretor;
	private Cidade fkMunicipioCliente;
	private List<Horario> horarios;
	private List<Ambiente> ambientes;
	
	public Integer getPkUnidadeEscolar() {
		return pkUnidadeEscolar;
	}
	public void setPkUnidadeEscolar(Integer pkUnidadeEscolar) {
		this.pkUnidadeEscolar = pkUnidadeEscolar;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Boolean getUnidadeControlada() {
		return unidadeControlada;
	}
	public void setUnidadeControlada(Boolean unidadeControlada) {
		this.unidadeControlada = unidadeControlada;
	}
	public Boolean getUnidadeInformatizada() {
		return unidadeInformatizada;
	}
	public void setUnidadeInformatizada(Boolean unidadeInformatizada) {
		this.unidadeInformatizada = unidadeInformatizada;
	}
	public RedeEnsino getRedeEnsino() {
		return redeEnsino;
	}
	public void setRedeEnsino(RedeEnsino redeEnsino) {
		this.redeEnsino = redeEnsino;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public TipoOcupacao getTipoOcupacao() {
		return tipoOcupacao;
	}
	public void setTipoOcupacao(TipoOcupacao tipoOcupacao) {
		this.tipoOcupacao = tipoOcupacao;
	}
	public SituacaoFuncionamento getSituacaoFuncionamento() {
		return situacaoFuncionamento;
	}
	public void setSituacaoFuncionamento(SituacaoFuncionamento situacaoFuncionamento) {
		this.situacaoFuncionamento = situacaoFuncionamento;
	}
	public Terreno getTerreno() {
		return terreno;
	}
	public void setTerreno(Terreno terreno) {
		this.terreno = terreno;
	}
	public Regiao getRegiao() {
		return regiao;
	}
	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}
	public Pessoa getDiretor() {
		return diretor;
	}
	public void setDiretor(Pessoa diretor) {
		this.diretor = diretor;
	}
	public Cidade getFkMunicipioCliente() {
		return fkMunicipioCliente;
	}
	public void setFkMunicipioCliente(Cidade fkMunicipioCliente) {
		this.fkMunicipioCliente = fkMunicipioCliente;
	}
	public List<Horario> getHorarios() {
		return horarios;
	}
	public void setHorarios(List<Horario> horarios) {
		this.horarios = horarios;
	}
	public List<Ambiente> getAmbientes() {
		return ambientes;
	}
	public void setAmbientes(List<Ambiente> ambientes) {
		this.ambientes = ambientes;
	}
	public String getCodigoFederal() {
		return codigoFederal;
	}
	public void setCodigoFederal(String codigoFederal) {
		this.codigoFederal = codigoFederal;
	}
	
}
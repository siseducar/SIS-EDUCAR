package modulos.RH.om;

public class UnidadeEscolar 
{
	private int pkUnidadeEscolar;
	private String codigo;
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
	public int getPkUnidadeEscolar() {
		return pkUnidadeEscolar;
	}
	public void setPkUnidadeEscolar(int pkUnidadeEscolar) {
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
	public Pessoa getDiretor() {
		return diretor;
	}
	public void setDiretor(Pessoa diretor) {
		this.diretor = diretor;
	}
}

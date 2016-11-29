package modulos.secretaria.om;

public class Ambiente 
{
	private Integer pkAmbiente;
	private String codigo;
	private String nome;
	private Double metragem;
	private Integer status;
	private Integer capacidade;
	private TipoAmbiente tipo;
	private String tipoNome;
	private Bloco bloco;
	private String blocoNome;
	private UnidadeEscolar unidadeEscolar;
	
	public Integer getPkAmbiente() {
		return pkAmbiente;
	}
	public void setPkAmbiente(Integer pkAmbiente) {
		this.pkAmbiente = pkAmbiente;
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
	public Double getMetragem() {
		return metragem;
	}
	public void setMetragem(Double metragem) {
		this.metragem = metragem;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCapacidade() {
		return capacidade;
	}
	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}
	public UnidadeEscolar getUnidadeEscolar() {
		return unidadeEscolar;
	}
	public void setUnidadeEscolar(UnidadeEscolar unidadeEscolar) {
		this.unidadeEscolar = unidadeEscolar;
	}
	public TipoAmbiente getTipo() {
		return tipo;
	}
	public void setTipo(TipoAmbiente tipo) {
		this.tipo = tipo;
	}
	public Bloco getBloco() {
		return bloco;
	}
	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}
	public String getTipoNome() {
		return tipoNome;
	}
	public void setTipoNome(String tipoNome) {
		this.tipoNome = tipoNome;
	}
	public String getBlocoNome() {
		return blocoNome;
	}
	public void setBlocoNome(String blocoNome) {
		this.blocoNome = blocoNome;
	}
}

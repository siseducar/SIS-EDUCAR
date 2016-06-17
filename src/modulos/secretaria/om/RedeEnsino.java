package modulos.secretaria.om;

public class RedeEnsino 
{
	private Integer pkRedeEnsino;
	private String codigo;
	private String nome;
	private int status;
	private int ordemExibicao;
	private Cidade fkMunicipioCliente;
	
	public Integer getPkRedeEnsino() {
		return pkRedeEnsino;
	}
	public void setPkRedeEnsino(Integer pkRedeEnsino) {
		this.pkRedeEnsino = pkRedeEnsino;
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
	public Cidade getFkMunicipioCliente() {
		return fkMunicipioCliente;
	}
	public void setFkMunicipioCliente(Cidade fkMunicipioCliente) {
		this.fkMunicipioCliente = fkMunicipioCliente;
	}
	public int getOrdemExibicao() {
		return ordemExibicao;
	}
	public void setOrdemExibicao(int ordemExibicao) {
		this.ordemExibicao = ordemExibicao;
	}
	

}

package modulos.RH.om;

public class UnidadeEscolar 
{
	private int pkUnidadeEscolar;
	private String codigo;
	private String nome;
	private int status;
	private RedeEnsino redeEnsino;
	
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
}

package modulos.RH.om;

public class Cidade 
{
	private String nome;
	private int codigoIBGE;
	private String sigla;
	private Estado estado;
	private int status;
	
	public Cidade()
	{
		estado = new Estado();
	}

	/* Getters and Stters*/
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
	public int getCodigoIBGE() {return codigoIBGE;}
	public void setCodigoIBGE(int codigoIBGE) {this.codigoIBGE = codigoIBGE;}
	public String getSigla() {return sigla;}
	public void setSigla(String sigla) {this.sigla = sigla;}
	public Estado getEstado() {return estado;}
	public void setEstado(Estado estado) {this.estado = estado;}
	public int getStatus() {return status;}
	public void setStatus(int status) {this.status = status;}
}

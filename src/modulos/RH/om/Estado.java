package modulos.RH.om;

public class Estado 
{
	private String nome;
	private int codigoIBGE;
	private String sigla;
	private Pais pais;
	
	public Estado()
	{
		pais = new Pais();
	}

	/* Getters and Setters*/
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
	public int getCodigoIBGE() {return codigoIBGE;}
	public void setCodigoIBGE(int codigoIBGE) {this.codigoIBGE = codigoIBGE;}
	public String getSigla() {return sigla;}
	public void setSigla(String sigla) {this.sigla = sigla;}
	public Pais getPais() {return pais;}
	public void setPais(Pais pais) {this.pais = pais;}
}

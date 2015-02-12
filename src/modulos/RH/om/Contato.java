package modulos.RH.om;

public class Contato 
{
	private String contato;
	private String responsavel;
	private boolean padrao;
	private TipoContato tipoContato;
	private Pessoa pessoa;
	
	public Contato()
	{
		tipoContato = new TipoContato();
		pessoa = new Pessoa();
	}
	
	/* Getters and Setters*/
	public String getContato() {return contato;}
	public void setContato(String contato) {this.contato = contato;}
	public String getResponsavel() {return responsavel;}	
	public void setResponsavel(String responsavel) {this.responsavel = responsavel;}
	public boolean isPadrao() {return padrao;}
	public void setPadrao(boolean padrao) {this.padrao = padrao;}
	public TipoContato getTipoContato() {return tipoContato;}
	public void setTipoContato(TipoContato tipoContato) {this.tipoContato = tipoContato;}
	public Pessoa getPessoa() {return pessoa;}
	public void setPessoa(Pessoa pessoa) {this.pessoa = pessoa;}
}

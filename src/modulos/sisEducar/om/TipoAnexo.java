package modulos.sisEducar.om;

public class TipoAnexo 
{
	private Integer pkTipoAnexo;
	private String nome;
	private Integer tipo;
	private String caminho;
	
	public Integer getPkTipoAnexo() {
		return pkTipoAnexo;
	}
	public void setPkTipoAnexo(Integer pkTipoAnexo) {
		this.pkTipoAnexo = pkTipoAnexo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
}

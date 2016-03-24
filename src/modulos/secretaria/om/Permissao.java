package modulos.secretaria.om;

public class Permissao
{
	private Integer pkPermissao;
	private String nome;
	private Integer tipo;
	private Integer status;
	
	public Integer getPkPermissao() {
		return pkPermissao;
	}
	public void setPkPermissao(Integer pkPermissao) {
		this.pkPermissao = pkPermissao;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}

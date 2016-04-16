package modulos.secretaria.om;

public class Permissao
{
	private Integer pkPermissao;
	private String nome;
	private String nomeModulo; //Essa variável só tem na OM
	private Integer tipo;
	private Integer tipoModuloResponsavel;
	private Integer tipoSubMenuResponsavel;
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
	public Integer getTipoModuloResponsavel() {
		return tipoModuloResponsavel;
	}
	public void setTipoModuloResponsavel(Integer tipoModuloResponsavel) {
		this.tipoModuloResponsavel = tipoModuloResponsavel;
	}
	public Integer getTipoSubMenuResponsavel() {
		return tipoSubMenuResponsavel;
	}
	public void setTipoSubMenuResponsavel(Integer tipoSubMenuResponsavel) {
		this.tipoSubMenuResponsavel = tipoSubMenuResponsavel;
	}
	public String getNomeModulo() {
		return nomeModulo;
	}
	public void setNomeModulo(String nomeModulo) {
		this.nomeModulo = nomeModulo;
	}
}

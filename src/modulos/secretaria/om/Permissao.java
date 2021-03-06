package modulos.secretaria.om;

import java.io.Serializable;

public class Permissao implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	private Integer pkPermissao;
	private String nome;
	private String nomeModulo; //Essa variável só tem na OM
	private String nomeSubMenu; //Essa variável só tem na OM
	private String nomeTela; //Essa variável só tem na OM
	private Integer tipo;
	private Integer tipoModuloResponsavel;
	private Integer tipoSubMenuResponsavel;
	private Integer telaResponsavel;
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
	public String getNomeSubMenu() {
		return nomeSubMenu;
	}
	public void setNomeSubMenu(String nomeSubMenu) {
		this.nomeSubMenu = nomeSubMenu;
	}
	public Integer getTelaResponsavel() {
		return telaResponsavel;
	}
	public void setTelaResponsavel(Integer telaResponsavel) {
		this.telaResponsavel = telaResponsavel;
	}
	public String getNomeTela() {
		return nomeTela;
	}
	public void setNomeTela(String nomeTela) {
		this.nomeTela = nomeTela;
	}
}

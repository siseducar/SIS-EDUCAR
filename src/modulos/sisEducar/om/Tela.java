package modulos.sisEducar.om;

public class Tela 
{
	private String nome;
	private Integer tipoTela;
	private Integer tipoSubMenu;
	private Integer modulo;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getTipoTela() {
		return tipoTela;
	}
	public void setTipoTela(Integer tipoTela) {
		this.tipoTela = tipoTela;
	}
	public Integer getModulo() {
		return modulo;
	}
	public void setModulo(Integer modulo) {
		this.modulo = modulo;
	}
	public Integer getTipoSubMenu() {
		return tipoSubMenu;
	}
	public void setTipoSubMenu(Integer tipoSubMenu) {
		this.tipoSubMenu = tipoSubMenu;
	}
}

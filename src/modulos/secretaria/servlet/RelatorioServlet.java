package modulos.secretaria.servlet;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="relatorioServlet")
@ViewScoped
public class RelatorioServlet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/* Renderiza o painel com filtros de raca */
	private Boolean painelRaca;
	
	/* Renderiza o painel com filtros de situacao economica */
	private Boolean painelSituEconomica;
	
	/* Tipo de relatorio desejado */
	private int tipoDados;
	
	public RelatorioServlet() {
		painelRaca = false;
		painelSituEconomica = false;
		tipoDados = 0;
	}
	
	public void validaTipoDados(){
		if(tipoDados == 1){
			painelRaca = true;
			painelSituEconomica = false;
		}
		if(tipoDados == 2){
			painelRaca = false;
			painelSituEconomica = true;
		}
		if(tipoDados == 0){
			painelRaca = false;
			painelSituEconomica = false;
		}
	}
	
	
	/* GETTERS AND SETTERS */
	public Boolean getPainelRaca() {
		return painelRaca;
	}

	public void setPainelRaca(Boolean painelRaca) {
		this.painelRaca = painelRaca;
	}

	public Boolean getPainelSituEconomica() {
		return painelSituEconomica;
	}

	public void setPainelSituEconomica(Boolean painelSituEconomica) {
		this.painelSituEconomica = painelSituEconomica;
	}

	public int getTipoDados() {
		return tipoDados;
	}

	public void setTipoDados(int tipoDados) {
		this.tipoDados = tipoDados;
	}	
}

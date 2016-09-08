package modulos.secretaria.servlet;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modulos.secretaria.om.Raca;

@ManagedBean(name="ambienteServlet")
@ViewScoped
public class AmbienteEscolarServlet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int quanSalas = 0;
	
	private Raca[] listaRaca;

	
	
	public void carregaSalas() {
		listaRaca = new Raca[quanSalas];
	}
	
	public Raca[] getListaRaca() {
		return listaRaca;
	}

	public void setListaRaca(Raca[] listaRaca) {
		this.listaRaca = listaRaca;
	}

	public int getQuanSalas() {
		return quanSalas;
	}

	public void setQuanSalas(int quanSalas) {
		this.quanSalas = quanSalas;
	}
	
	
}

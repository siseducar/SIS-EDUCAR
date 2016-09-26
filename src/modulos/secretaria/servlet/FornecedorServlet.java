package modulos.secretaria.servlet;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Estado;
import modulos.secretaria.om.Fornecedor;
import modulos.secretaria.om.Pais;

@ManagedBean(name="forneServlet")
@ViewScoped
public class FornecedorServlet implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{paramServlet}")
	private ParametrosServlet paramDados;

	Fornecedor fornecedorDados;
	
	Pais paisDados;
	Estado estadoDados;
	Cidade cidadeDados;
	
	
	public FornecedorServlet() {
		if(this.fornecedorDados == null) {
			this.fornecedorDados = new Fornecedor();
		}
		if(this.paisDados == null) {
			this.paisDados = new Pais();
		}
		if(this.estadoDados == null) {
			this.estadoDados = new Estado();
		}
		if(this.cidadeDados == null) {
			this.cidadeDados = new Cidade();
		}
	}
}

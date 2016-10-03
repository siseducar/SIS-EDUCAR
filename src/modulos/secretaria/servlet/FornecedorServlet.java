package modulos.secretaria.servlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Estado;
import modulos.secretaria.om.Fornecedor;
import modulos.secretaria.om.Pais;
import modulos.secretaria.om.Regiao;
import modulos.secretaria.om.TipoLogradouro;

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
	TipoLogradouro tipoLograDados;
	Regiao regiaoDados;
	
	/* Combo com informacoes de ESTADO para inscricao estadual */
	List<SelectItem> estadoInscricaoDados;
	
	/* Combo com os valores de ESTADO */
	List<SelectItem> comboEstado;
	
	/* Combo com os valores de MUNICIPIO */
	List<SelectItem> comboMunicipio;
	
	public FornecedorServlet() {
		if( this.fornecedorDados == null ) {
			this.fornecedorDados = new Fornecedor();
		}
		if( this.paisDados == null ) {
			this.paisDados = new Pais();
		}
		if( this.estadoDados == null ) {
			this.estadoDados = new Estado();
		}
		if( this.cidadeDados == null ) {
			this.cidadeDados = new Cidade();
		}
		if( this.tipoLograDados == null ) {
			this.tipoLograDados = new TipoLogradouro();
		}
		if( this.regiaoDados == null ) {
			this.regiaoDados = new Regiao();
		}
		
		comboEstado = new ArrayList<SelectItem>();
		comboMunicipio = new ArrayList<SelectItem>();
	}
	
	
	public void validaDados() {
		
	}
	
	
	
	public ParametrosServlet getParamDados() {
		return paramDados;
	}

	public void setParamDados(ParametrosServlet paramDados) {
		this.paramDados = paramDados;
	}

	public Fornecedor getFornecedorDados() {
		return fornecedorDados;
	}

	public void setFornecedorDados(Fornecedor fornecedorDados) {
		this.fornecedorDados = fornecedorDados;
	}

	public Pais getPaisDados() {
		return paisDados;
	}

	public void setPaisDados(Pais paisDados) {
		this.paisDados = paisDados;
	}

	public Estado getEstadoDados() {
		return estadoDados;
	}

	public void setEstadoDados(Estado estadoDados) {
		this.estadoDados = estadoDados;
	}

	public Cidade getCidadeDados() {
		return cidadeDados;
	}

	public void setCidadeDados(Cidade cidadeDados) {
		this.cidadeDados = cidadeDados;
	}

	public List<SelectItem> getEstadoInscricaoDados() {
		return estadoInscricaoDados;
	}

	public void setEstadoInscricaoDados(List<SelectItem> estadoInscricaoDados) {
		this.estadoInscricaoDados = estadoInscricaoDados;
	}
	
	public List<SelectItem> getComboEstado() {
		if(paisDados.getPkPais() != null) {			
			comboEstado.addAll(paramDados.consultaEstado(paisDados));
			
			return comboEstado;
		}
		estadoDados.setPkEstado(null);
		comboEstado.clear();
		return comboEstado;
	}

	public void setComboEstado(List<SelectItem> comboEstado) {
		this.comboEstado = comboEstado;
	}
	
	/* CIDADE */
	public List<SelectItem> getComboMunicipio() {
		if(estadoDados.getPkEstado() != null) {
			comboMunicipio.addAll(paramDados.consultaCidade(estadoDados));
			
			return comboMunicipio;
		}
		cidadeDados.setPkCidade(null);
		comboMunicipio.clear();
		return comboMunicipio;
	}

	public void setComboMunicipio(List<SelectItem> comboMunicipio) {
		this.comboMunicipio = comboMunicipio;
	}


	public TipoLogradouro getTipoLograDados() {
		return tipoLograDados;
	}


	public void setTipoLograDados(TipoLogradouro tipoLograDados) {
		this.tipoLograDados = tipoLograDados;
	}


	public Regiao getRegiaoDados() {
		return regiaoDados;
	}


	public void setRegiaoDados(Regiao regiaoDados) {
		this.regiaoDados = regiaoDados;
	}
	
	
	
}

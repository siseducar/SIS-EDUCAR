package modulos.secretaria.servlet;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import modulos.secretaria.om.Cargo;
import modulos.secretaria.om.Funcionario;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.VinculoEmpregaticio;

@ManagedBean(name="funcionarioServlet")
@ViewScoped
public class FuncionarioServlet implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{paramServlet}")
	private ParametrosServlet paramDados;
	
	private Pessoa pessoaDados;
	private Funcionario funcionarioDados;
	private Cargo cargoDados;
	private VinculoEmpregaticio vincEmpregaticioDados;
	
	private Boolean panelDadosConcurso;
	
	private Boolean panelDadosAposentado;
	
	private Integer validaConcursado;
	
	private Integer validaAposentado;
	
	public FuncionarioServlet() {
		if( this.pessoaDados == null ) {
			this.pessoaDados = new Pessoa();
		}
		if( this.funcionarioDados == null) {
			this.funcionarioDados = new Funcionario();
		}
		if( this.cargoDados == null ) {
			this.cargoDados = new Cargo();
		}
		if( this.vincEmpregaticioDados == null ) {
			this.vincEmpregaticioDados = new VinculoEmpregaticio();
		}
		if( this.validaConcursado == null ) {
			this.validaConcursado = 0;
		}
		if( this.validaAposentado == null ) {
			this.validaAposentado = 0;
		}
		
		panelDadosConcurso = false;
		panelDadosAposentado = false;
	}

	
	public void validaPanelConcurso() {
		if( validaConcursado != null && validaConcursado == 1 ) {
			panelDadosConcurso = true;
		} else {
			panelDadosConcurso = false;
		}
	}
	
	public void validaPanelAposentado() {
		if( validaAposentado != null && validaAposentado == 1 ) {
			panelDadosAposentado = true;
		} else {
			panelDadosAposentado = false;
		}
	}
	
	
	
	
	public Integer getValidaConcursado() {
		return validaConcursado;
	}


	public void setValidaConcursado(Integer validaConcursado) {
		this.validaConcursado = validaConcursado;
	}


	public Integer getValidaAposentado() {
		return validaAposentado;
	}


	public void setValidaAposentado(Integer validaAposentado) {
		this.validaAposentado = validaAposentado;
	}


	public ParametrosServlet getParamDados() {
		return paramDados;
	}

	public void setParamDados(ParametrosServlet paramDados) {
		this.paramDados = paramDados;
	}

	public Pessoa getPessoaDados() {
		return pessoaDados;
	}

	public void setPessoaDados(Pessoa pessoaDados) {
		this.pessoaDados = pessoaDados;
	}

	public Funcionario getFuncionarioDados() {
		return funcionarioDados;
	}

	public void setFuncionarioDados(Funcionario funcionarioDados) {
		this.funcionarioDados = funcionarioDados;
	}

	public Cargo getCargoDados() {
		return cargoDados;
	}

	public void setCargoDados(Cargo cargoDados) {
		this.cargoDados = cargoDados;
	}

	public VinculoEmpregaticio getVincEmpregaticioDados() {
		return vincEmpregaticioDados;
	}

	public void setVincEmpregaticioDados(VinculoEmpregaticio vincEmpregaticioDados) {
		this.vincEmpregaticioDados = vincEmpregaticioDados;
	}

	public Boolean getPanelDadosConcurso() {
		return panelDadosConcurso;
	}

	public void setPanelDadosConcurso(Boolean panelDadosConcurso) {
		this.panelDadosConcurso = panelDadosConcurso;
	}

	public Boolean getPanelDadosAposentado() {
		return panelDadosAposentado;
	}

	public void setPanelDadosAposentado(Boolean panelDadosAposentado) {
		this.panelDadosAposentado = panelDadosAposentado;
	}
}

package modulos.educacao.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import modulos.educacao.om.Horario;
import modulos.secretaria.om.Turno;
import modulos.secretaria.servlet.ParametrosServlet;

@ManagedBean(name="horarioServlet")
@ViewScoped
public class HorarioServlet implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	public ParametrosServlet parametrosServlet;
	private String codigoUnidadeEscolar;
	private String nomeUnidadeEscolar;
	private Turno turnoDado;
	private Horario horario;
	private List<SelectItem> comboTurno;
	private Boolean btRemoverEnabled = false;
	
	public HorarioServlet()
	{
		comboTurno = new ArrayList<SelectItem>();
		btRemoverEnabled = false;
		
		if(horario==null) { horario = new Horario(); }
		if(turnoDado==null) { turnoDado = new Turno(); }
		if(parametrosServlet==null) { parametrosServlet = new ParametrosServlet(); }
	}
	
	public void cadastrarHorario()
	{
		
	}
	
	public void removerHorario()
	{
		
	}
	
	/*
	 * Metodo para limpar o formulario apos cadastro realizado
	 * 
	 * */
	public void limparFormulario() throws SQLException{
		
		turnoDado = new Turno();
		codigoUnidadeEscolar = "";
		nomeUnidadeEscolar = "";
		comboTurno = new ArrayList<SelectItem>();
		horario = new Horario();
	}

	
	/* GETTERS AND SETTERS */
	public String getCodigoUnidadeEscolar() {
		return codigoUnidadeEscolar;
	}

	public void setCodigoUnidadeEscolar(String codigoUnidadeEscolar) {
		this.codigoUnidadeEscolar = codigoUnidadeEscolar;
	}

	public String getNomeUnidadeEscolar() {
		return nomeUnidadeEscolar;
	}

	public void setNomeUnidadeEscolar(String nomeUnidadeEscolar) {
		this.nomeUnidadeEscolar = nomeUnidadeEscolar;
	}

	public Turno getTurnoDado() {
		return turnoDado;
	}

	public void setTurnoDado(Turno turnoDado) {
		this.turnoDado = turnoDado;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public List<SelectItem> getComboTurno() {
		comboTurno.clear();
		comboTurno.addAll(parametrosServlet.consultaTurno());
		return comboTurno;
	}

	public void setComboTurno(List<SelectItem> comboTurno) {
		this.comboTurno = comboTurno;
	}

	public Boolean getBtRemoverEnabled() {
		return btRemoverEnabled;
	}

	public void setBtRemoverEnabled(Boolean btRemoverEnabled) {
		this.btRemoverEnabled = btRemoverEnabled;
	}
}

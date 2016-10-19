package modulos.educacao.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;

import modulos.educacao.dao.HorarioDAO;
import modulos.educacao.om.Horario;
import modulos.educacao.om.HorarioAula;
import modulos.secretaria.dao.UnidadeEscolarDAO;
import modulos.secretaria.om.Turno;
import modulos.secretaria.om.UnidadeEscolar;
import modulos.secretaria.servlet.ParametrosServlet;
import modulos.sisEducar.utils.Logs;

@ManagedBean(name="horarioServlet")
@ViewScoped
public class HorarioServlet implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private String codigoUnidadeEscolar;
	private String nomeUnidadeEscolar;
	private Turno turnoDado;
	private Horario horario;
	private Boolean btRemoverEnabled = false;
	private UnidadeEscolar unidadeEscolarSelecionada = null;
	
	private List<HorarioAula> aulas;
	
	public HorarioServlet()
	{
		aulas = new ArrayList<HorarioAula>();
		btRemoverEnabled = false;
		
		if(horario==null) { horario = new Horario(); }
		if(turnoDado==null) { turnoDado = new Turno(); }
	}
	
	public void cadastrarHorario()
	{
		try 
		{
			Boolean resultado = false;
			if(unidadeEscolarSelecionada!=null && turnoDado!=null)
			{
				horario.setUnidadeEscolar(unidadeEscolarSelecionada);
				horario.setTurno(turnoDado);
				horario.setHorariosAula(aulas);
				
				resultado = new HorarioDAO().inserirHorario(horario);
				if(resultado)
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Horário registrado", null));
				}
				else
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Horário não registrado", null));
				}
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("cadastrarHorario", "cadastrarHorario");
		}
		
	}
	
	public void removerHorario()
	{
		List<HorarioAula> aulasAux = new ArrayList<HorarioAula>();
		HorarioAula aulaSelecionada = null;
		
		if(dataTableAulas!=null && dataTableAulas.getRowData()!=null) { aulaSelecionada = (HorarioAula) dataTableAulas.getRowData(); }
		
		if(aulaSelecionada != null && aulaSelecionada.getInicioAux()!=null && aulaSelecionada.getTerminoAux()!=null)
		{
			for (HorarioAula aula : aulas) 
			{
				if(!aula.getInicioAux().equals(aulaSelecionada.getInicioAux()) && !aula.getTerminoAux().equals(aulaSelecionada.getTerminoAux()))
				{
					aulasAux.add(aula);
				}
			}
			
			aulas = aulasAux;
		}
	}
	
	/**
	 * Adiciona os horários de acordo com preenchimento de informações do usuário na tela
	 * @author João Paulo
	 * @return
	 */
	public void adicionarAula()
	{
		try
		{
			String inicioHora = null;
			String inicioMinuto = null;
			String terminoHora = null;
			String terminoMinuto = null;
			String horaAulaHora = null;
			String horaAulaMinuto = null;
			Double inicio = null;
			Double termino = null;
			Double horaAula = null;
			Integer minutosIniciais = 0;
			Integer minutosFinais = 0;
			HorarioAula aula = null;
			String horarioConvertido = "";
			
			if(aulas.size()>0)
			{
				inicioHora = aulas.get(aulas.size()-1).getTerminoAux().substring(0, 2);
				inicioMinuto = aulas.get(aulas.size()-1).getTerminoAux().substring(3, 5);
				
				terminoHora = horario.getTerminoAux().substring(0, 2);
				terminoMinuto = horario.getTerminoAux().substring(2, 4);
			}
			else
			{
				inicioHora = horario.getInicioAux().substring(0, 2);
				inicioMinuto = horario.getInicioAux().substring(2, horario.getInicioAux().length());
				
				terminoHora = horario.getTerminoAux().substring(0, 2);
				terminoMinuto = horario.getTerminoAux().substring(2, horario.getTerminoAux().length());
			}
			
			horaAulaHora = horario.getHoraAulaAux().substring(0, 2);
			horaAulaMinuto = horario.getHoraAulaAux().substring(2, horario.getHoraAulaAux().length());
			
			inicio = new Double(inicioHora + "." + inicioMinuto);
			termino = new Double(terminoHora + "." + terminoMinuto);
			horaAula = new Double(horaAulaHora + "." + horaAulaMinuto);
			
			minutosIniciais = (new Integer(inicioHora) * 60) + (new Integer(inicioMinuto));
			minutosFinais = (new Integer(terminoHora) * 60) + (new Integer(terminoMinuto));
			
			minutosIniciais += ((new Integer(horaAulaHora) * 60) + (new Integer(horaAulaMinuto)));
			if(minutosIniciais<=minutosFinais)
			{
				horarioConvertido = converterHoraMinuto(new Integer(inicioHora), new Integer(inicioMinuto), new Integer(horaAulaHora), new Integer(horaAulaMinuto), 0, 0);
				
				aula = preencherOMHorarioAula(inicioHora, inicioMinuto, horarioConvertido, false);
				
				aulas.add(aula);
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("adicionarAula", "adicionarAula");
		}
	}
	
	public void adicionarIntervalo()
	{
		try
		{
			String inicioHora = null;
			String inicioMinuto = null;
			String terminoHora = null;
			String terminoMinuto = null;
			String intervaloHora = null;
			String intervaloMinuto = null;
			String horaAulaHora = null;
			String horaAulaMinuto = null;
			Double inicio = null;
			Double termino = null;
			Integer minutosIniciais = 0;
			Integer minutosFinais = 0;
			HorarioAula aula = null;
			String horarioConvertido = "";
			
			if(aulas.size()>0)
			{
				inicioHora = aulas.get(aulas.size()-1).getTerminoAux().substring(0, 2);
				inicioMinuto = aulas.get(aulas.size()-1).getTerminoAux().substring(3, 5);
				
				terminoHora = horario.getTerminoAux().substring(0, 2);
				terminoMinuto = horario.getTerminoAux().substring(2, 4);
				
				intervaloHora = horario.getIntervaloAux().substring(0, 2);
				intervaloMinuto = horario.getIntervaloAux().substring(2, 4);
				
				inicio = new Double(inicioHora + "." + inicioMinuto);
				termino = new Double(terminoHora + "." + terminoMinuto);
				
				minutosIniciais = (new Integer(inicioHora) * 60) + (new Integer(inicioMinuto));
				minutosFinais = (new Integer(terminoHora) * 60) + (new Integer(terminoMinuto));
				
				if(minutosIniciais<=minutosFinais)
				{
					horarioConvertido = converterHoraMinuto(new Integer(inicioHora), new Integer(inicioMinuto), null, null, new Integer(intervaloHora), new Integer(intervaloMinuto));
					
					aula = preencherOMHorarioAula(inicioHora, inicioMinuto, horarioConvertido, true);
					
					aulas.add(aula);
				}
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("adicionarIntervalo", "adicionarIntervalo");
		}
	}
	
	/**
	 * Faz a conversão de hora e retorna no formato string HH:MM
	 * @author João Paulo
	 * @param hora
	 * @param minuto
	 * @param addHora
	 * @param addMinuto
	 * @return
	 */
	public String converterHoraMinuto(Integer hora, Integer minuto, Integer addHora, Integer addMinuto, Integer addIntervaloHora, Integer addIntervaloMinuto)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(new Date());
			gc.set( Calendar.HOUR_OF_DAY, hora);
			gc.set( Calendar.MINUTE, minuto);
			
			if(addHora != null)   		  { gc.add(Calendar.HOUR, addHora); }
			if(addMinuto != null) 		  { gc.add(Calendar.MINUTE, addMinuto); }
			if(addIntervaloHora!= null)   { gc.add(Calendar.HOUR, addIntervaloHora); }
			if(addIntervaloMinuto!= null) { gc.add(Calendar.MINUTE, addIntervaloMinuto); }
			
			return sdf.format(gc.getTime());
		} 
		catch (Exception e) 
		{
			Logs.addError("converterHoraMinuto", "converterHoraMinuto");
			return null;
		}
	}
	
	public Double converteHoraToInteger(String hora)
	{
		try
		{
			return new Double(hora.substring(0, 2));
		} 
		catch (Exception e) 
		{
			Logs.addError("converteHoraToInteger", "converteHoraToInteger");
			return null;
		}
	}
	
	public Double converteMinutoToInteger(String minuto)
	{
		try
		{
			return new Double(minuto.substring(3, 5));
		} 
		catch (Exception e) 
		{
			Logs.addError("converteHoraToInteger", "converteHoraToInteger");
			return null;
		}
	}
	
	public void buscarUnidadeEscolar()
	{
		try
		{
			if(codigoUnidadeEscolar!=null && codigoUnidadeEscolar.length()>0)
			{
				UnidadeEscolar unidadeEscolar = new UnidadeEscolarDAO().buscarUnidadeEscolarSimples(codigoUnidadeEscolar, null);
				if(unidadeEscolar!=null)
				{
					unidadeEscolarSelecionada = unidadeEscolar;
					nomeUnidadeEscolar = unidadeEscolar.getNome();
				}
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("buscarUnidadeEscolar", "buscarUnidadeEscolar");
		}
	}
	
	public HorarioAula preencherOMHorarioAula(String inicioHora, String inicioMinuto, String horarioConvertido, Boolean indicarIntervalo)
	{
		try
		{
			HorarioAula aula = new HorarioAula();
			aula.setHoraInicio(new Double(inicioHora));
			aula.setMinutoInicio(new Double(inicioMinuto));
			
			if(indicarIntervalo) { aula.setInicioAux(inicioHora + ":" + inicioMinuto + "(Intervalo)"); }
			else 				 { aula.setInicioAux(inicioHora + ":" + inicioMinuto); }
			
			aula.setHoraTermino(converteHoraToInteger(horarioConvertido));
			aula.setMinutoTermino(converteMinutoToInteger(horarioConvertido));
			
			if(indicarIntervalo) { aula.setTerminoAux(horarioConvertido + "(Intervalo)"); }
			else 				 { aula.setTerminoAux(horarioConvertido); }
			
			return aula;
		} 
		catch (Exception e) 
		{
			Logs.addError("preencherOMHorarioAula", "preencherOMHorarioAula");
			return null;
		}
	}
	
	/**
	 * Remove todas as aulas da tabela
	 */
	public void removerTodasAulas()
	{
		try 
		{
			aulas = new ArrayList<HorarioAula>();
		} 
		catch (Exception e) 
		{
			Logs.addError("removerTodasAulas", "removerTodasAulas");
		}
	}
	
	/**
	 * Remove apenas a aulas selecionada na tabela
	 */
	public void removerPermissao()
	{
		List<HorarioAula> listAulasAux = new ArrayList<HorarioAula>();
		HorarioAula aulaSelecionada = null;
		
		if(dataTable!=null && dataTable.getRowData()!=null) { aulaSelecionada = (HorarioAula) dataTable.getRowData(); }
		
		if(aulaSelecionada != null && aulaSelecionada.getPkHorarioAula() != null)
		{
			for (HorarioAula aula : aulas) 
			{
				if(!aula.getPkHorarioAula().equals(aulaSelecionada.getPkHorarioAula()))
				{
					listAulasAux.add(aula);
				}
			}
			
			aulas = listAulasAux;
		}
	}
	
	public void pesquisarHorarios() throws SQLException
	{
		if(unidadeEscolarSelecionada!=null && unidadeEscolarSelecionada.getPkUnidadeEscolar()>0 && turnoDado!=null && turnoDado.getPkTurno()>0)
		{
			horario = new HorarioDAO().obtemHorariosPorTurno(unidadeEscolarSelecionada, turnoDado);
			aulas = horario.getHorariosAula();
		}
	}
	
	/*
	 * Metodo para limpar o formulario apos cadastro realizado
	 * 
	 * */
	public void limparFormulario() throws SQLException{
		
		turnoDado = new Turno();
		codigoUnidadeEscolar = "";
		nomeUnidadeEscolar = "";
		horario = new Horario();
	}

	private HtmlDataTable dataTable;
	private HtmlDataTable dataTableAulas;
	   
    public HtmlDataTable getDataTable() {
          return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
          this.dataTable = dataTable;
    }            

    public HtmlDataTable getDataTableAulas() {
		return dataTableAulas;
	}

	public void setDataTableAulas(HtmlDataTable dataTableAulas) {
		this.dataTableAulas = dataTableAulas;
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

	public Boolean getBtRemoverEnabled() {
		return btRemoverEnabled;
	}

	public void setBtRemoverEnabled(Boolean btRemoverEnabled) {
		this.btRemoverEnabled = btRemoverEnabled;
	}

	public List<HorarioAula> getAulas() {
		return aulas;
	}

	public void setAulas(List<HorarioAula> aulas) {
		this.aulas = aulas;
	}

	public UnidadeEscolar getUnidadeEscolarSelecionada() {
		return unidadeEscolarSelecionada;
	}

	public void setUnidadeEscolarSelecionada(UnidadeEscolar unidadeEscolarSelecionada) {
		this.unidadeEscolarSelecionada = unidadeEscolarSelecionada;
	}
}

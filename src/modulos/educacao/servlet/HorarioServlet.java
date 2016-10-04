package modulos.educacao.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.model.SelectItem;

import modulos.educacao.om.Horario;
import modulos.educacao.om.HorarioAula;
import modulos.secretaria.om.Turno;
import modulos.secretaria.servlet.ParametrosServlet;
import modulos.sisEducar.utils.Logs;

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
	
	private List<HorarioAula> aulas;
	
	public HorarioServlet()
	{
		comboTurno = new ArrayList<SelectItem>();
		aulas = new ArrayList<HorarioAula>();
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
			String intervaloHora = null;
			String intervaloMinuto = null;
			String horaAulaHora = null;
			String horaAulaMinuto = null;
			Double inicio = null;
			Double termino = null;
			Double intervalo = null;
			Double horaAula = null;
			Integer minutosIniciais = 0;
			Integer minutosFinais = 0;
			HorarioAula aula = null;
			Integer count = 0;
			String horarioConvertido = "";
			aulas = new ArrayList<HorarioAula>();
			
			inicioHora = horario.getInicioAux().substring(0, 2);
			inicioMinuto = horario.getInicioAux().substring(2, horario.getInicioAux().length());
			
			terminoHora = horario.getTerminoAux().substring(0, 2);
			terminoMinuto = horario.getTerminoAux().substring(2, horario.getTerminoAux().length());
			
			intervaloHora = horario.getIntervaloAux().substring(0, 2);
			intervaloMinuto = horario.getIntervaloAux().substring(2, horario.getIntervaloAux().length());
			
			horaAulaHora = horario.getHoraAulaAux().substring(0, 2);
			horaAulaMinuto = horario.getHoraAulaAux().substring(2, horario.getHoraAulaAux().length());
			
			inicio = new Double(inicioHora + "." + inicioMinuto);
			termino = new Double(terminoHora + "." + terminoMinuto);
			intervalo = new Double(intervaloHora + "." + intervaloMinuto);
			horaAula = new Double(horaAulaHora + "." + horaAulaMinuto);
			
			minutosIniciais = (new Integer(inicioHora) * 60) + (new Integer(inicioMinuto));
			minutosFinais = (new Integer(terminoHora) * 60) + (new Integer(terminoMinuto));
			
			do
			{
				if(count==2)
				{
					horarioConvertido = converterHoraMinuto(new Integer(inicioHora), new Integer(inicioMinuto), null, null,
							new Integer(intervaloHora), new Integer(intervaloMinuto));
					
					aula = preencherOMHorarioAula(inicioHora, inicioMinuto, horarioConvertido, true);
				}
				else
				{
					horarioConvertido = converterHoraMinuto(new Integer(inicioHora), new Integer(inicioMinuto), new Integer(horaAulaHora), new Integer(horaAulaMinuto), 0, 0);
					
					aula = preencherOMHorarioAula(inicioHora, inicioMinuto, horarioConvertido, false);
				}
				
				
				inicioHora = horarioConvertido.substring(0, 2);
				inicioMinuto = horarioConvertido.substring(3, 5);
				
				minutosIniciais += ((new Integer(horaAulaHora) * 60) + (new Integer(horaAulaMinuto)));
				count++;
						
				aulas.add(aula);
			}
			while(minutosIniciais <= minutosFinais);
		} 
		catch (Exception e) 
		{
			Logs.addError("adicionarAula", "adicionarAula");
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
	
	public HorarioAula preencherOMHorarioAula(String inicioHora, String inicioMinuto, String horarioConvertido, Boolean indicarIntervalo)
	{
		try
		{
			HorarioAula aula = new HorarioAula();
			aula.setHoraInicio(new Double(inicioHora));
			aula.setMinutoInicio(new Double(inicioMinuto));
			
			if(indicarIntervalo) { aula.setInicioAux("Intervalo - " + inicioHora + ":" + inicioMinuto); }
			else 				 { aula.setInicioAux(inicioHora + ":" + inicioMinuto); }
			
			aula.setHoraTermino(converteHoraToInteger(horarioConvertido));
			aula.setMinutoTermino(converteMinutoToInteger(horarioConvertido));
			
			if(indicarIntervalo) { aula.setTerminoAux("Intervalo - " + horarioConvertido); }
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
//		List<Permissao> listPermissoesAux = new ArrayList<Permissao>();
//		Permissao permissaoSelecionada = null;
//		
//		if(dataTablePermissao!=null && dataTablePermissao.getRowData()!=null) { permissaoSelecionada = (Permissao) dataTablePermissao.getRowData(); }
//		
//		if(permissaoSelecionada != null && permissaoSelecionada.getPkPermissao() != null)
//		{
//			for (Permissao permissao : permissoes) 
//			{
//				if(!permissao.getPkPermissao().equals(permissaoSelecionada.getPkPermissao()))
//				{
//					listPermissoesAux.add(permissao);
//				}
//			}
//			
//			permissoes = listPermissoesAux;
//		}
	}
	
	public void pesquisarHorarios()
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

	public List<HorarioAula> getAulas() {
		return aulas;
	}

	public void setAulas(List<HorarioAula> aulas) {
		this.aulas = aulas;
	}
}

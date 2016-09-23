package modulos.educacao.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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
	 * Adiciona a permissão selecionada na tabela de permissões do usuário
	 * @author João Paulo
	 * @return
	 */
	public void adicionarPermissao()
	{
		try
		{
//			Boolean permissaoExiste = false;
//			Permissao permissaoAux = null;
//			if(permissaoSelecionada!=null && permissaoSelecionada.getPkPermissao()!=null)
//			{
//				//OBTEM O OBJETO PERMISSÃO COM TODAS AS SUAS INFORMAÇÕES
//				for (Permissao permissao : todasPermissoes) 
//				{
//					if(permissao.getPkPermissao().equals(permissaoSelecionada.getPkPermissao()))
//					{
//						permissaoAux = permissao;
//						break;
//					}
//				}
//				
//				//SETA A VARIÁVEL DE PERMISSÃO DUPLICADA
//				for (Permissao permissao : permissoes) 
//				{
//					if(permissaoSelecionada.getPkPermissao().equals(permissao.getPkPermissao())) { permissaoExiste = true; }
//				}
//
//				//VERIFICA SE A PERMISSÃO JÁ FOI ADICIONADA NA TABELA, CASO ESTEJA ENTÃO NÃO ADICIONA NOVAMENTE
//				if(!permissaoExiste)
//				{
//					permissoes.add(preencherInformacoesFaltantesPermissao(permissaoAux));
//				}
//				
//				permissaoSelecionada = new Permissao();
//			}
		} 
		catch (Exception e) 
		{
			Logs.addError("adicionarAula", "adicionarAula");
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

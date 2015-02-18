package modulos.RH.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modulos.RH.om.Cidade;
import modulos.RH.om.Pessoa;
import modulos.sisEducar.sisEducarServlet.SisEducarServlet;
import modulos.sisEducar.utils.ConstantesSisEducar;
import modulos.sisEducar.utils.Logs;

import org.primefaces.event.FlowEvent;

@ManagedBean(name="pessoaServlet")
@ViewScoped
public class PessoaServlet extends SisEducarServlet
{
	//Variaveis Globais
	Pessoa pessoa;

	private List<String> orgaosEmissores = null;
	private List<Cidade> listaCidades = null;
	
	/**
	 * Construtor
	 */
	public PessoaServlet ()
	{
		pessoa = new Pessoa();
		orgaosEmissores = new ArrayList<String>();
		setListaCidades(new ArrayList<Cidade>());
		
		popularListaOrgaoEmissor();
	}
	
	/**
	 * Método generico para redirecionamento de tela
	 * @param idTela (1 = TelaCadastroPessoa)
	 * @return String
	 */
	public void redirecionarTela(int idTela)
	{
		try 
		{
			if(idTela == 1)
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesSisEducar.PATH_PROJETO_NOME + "modulos/RH/cadastros/telaCadastroPessoa.jsf");  
			}
		}
		catch (Exception e) 
		{
			Logs.addError("Redirecionar tela", "Erro ao redirecionar para a tela, contate o administrador.");
		}
	}
	
	/**
	 * Método usado para avançar etapas no cadastro de pessoa
	 * @param event
	 * @return
	 */
	public String avancarEtapa(FlowEvent event) 
	{
		try 
		{
			return event.getNewStep();
		} 
		catch (Exception e) 
		{
			Logs.addError("Avançar etapa", "Erro ao avançar para a próxima etapa, contate o administrador.");
			return null;
		}
    }
	
	/**
	 * Método usado para preencher a lista de orgãos emissores
	 */
	public void popularListaOrgaoEmissor()
	{
		orgaosEmissores.add("SSP");
		orgaosEmissores.add("COREN");
		orgaosEmissores.add("CRA");
		orgaosEmissores.add("CRAS");
		orgaosEmissores.add("CRB");
		orgaosEmissores.add("CRC");
		orgaosEmissores.add("CRE");
		orgaosEmissores.add("CREA");
		orgaosEmissores.add("CRECI");
		orgaosEmissores.add("CREFIT");
		orgaosEmissores.add("CRF");
		orgaosEmissores.add("CRM");
		orgaosEmissores.add("CRN");
		orgaosEmissores.add("CRO");
		orgaosEmissores.add("CRP");
		orgaosEmissores.add("CRPRE");
		orgaosEmissores.add("CRQ");
		orgaosEmissores.add("CRRC");
		orgaosEmissores.add("CRMV");
		orgaosEmissores.add("DPF");
		orgaosEmissores.add("EST");
		orgaosEmissores.add("I CLA");
		orgaosEmissores.add("MAE");
		orgaosEmissores.add("MEX");
		orgaosEmissores.add("MMA");
		orgaosEmissores.add("OAB");
		orgaosEmissores.add("OMB");
		orgaosEmissores.add("IFP");
		orgaosEmissores.add("OUT");
	}
	
	/* Getters and Setters */
	public Pessoa getPessoa() {	return pessoa; }
	public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }

	public List<String> getOrgaosEmissores() { return orgaosEmissores; }
	public void setOrgaosEmissores(List<String> orgaosEmissores) { this.orgaosEmissores = orgaosEmissores; }

	public List<Cidade> getListaCidades() {return listaCidades;}
	public void setListaCidades(List<Cidade> listaCidades) {this.listaCidades = listaCidades;}
}

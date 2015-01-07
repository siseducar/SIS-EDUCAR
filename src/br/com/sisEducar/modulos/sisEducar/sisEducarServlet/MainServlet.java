package br.com.sisEducar.modulos.sisEducar.sisEducarServlet;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.com.sisEducar.modulos.sisEducar.utils.ConstantesSisEducar;
import br.com.sisEducar.modulos.sisEducar.utils.Logs;

@ManagedBean(name= "mainServlet")
public class MainServlet extends SisEducarServlet
{
	/**
	 * Método de redirecionar generico, ele recebe o id do modulo e redireciona para o modulo solicitado
	 * @param idModulo (1 = Modulo RH)
	 */
	public void redirecionarModulo(int idModulo)
	{
		try
		{
			if(idModulo == 1)
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesSisEducar.PATH_PROJETO + "modulos/RH/moduloRH.jsf");  
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("Redirecionar módulo", "Erro ao redirecionar para o módulo, contate o administrador.");
		}
	}
}

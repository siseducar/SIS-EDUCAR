package modulos.sisEducar.sisEducarServlet;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import modulos.sisEducar.utils.ConstantesSisEducar;

public class SisEducarServlet 
{
	public Object getSessionObject(String chave)
	{
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(chave);
	}
	
	public Object getSessionObject(HttpServletRequest req, String chave)
	{
		return req.getSession().getAttribute(ConstantesSisEducar.USUARIO_LOGADO);
	}
	
	public void putSessionObject(String chave, Object object)
	{
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(chave, object);
	}
}

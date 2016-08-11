package modulos.sisEducar.servlet;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import modulos.secretaria.servlet.ParametrosServlet;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class MainSisEducar implements Servlet, Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig arg0) throws ServletException
	{
		try
		{
			//Aqui ele defini qual será a chave de acesso do usuário logado
			ConstantesSisEducar.USUARIO_LOGADO = "";
			
			/*Inicializa a classe de parâmetros para ela já construir e popular as informações que preecheram os combos de todas as telas, assim garantimos melhor perfoamance no sistema
			pois o mesmo está sendo chamado apenas uma vez*/
			new ParametrosServlet();
			
			System.out.println("<-------Sistema SisEducar iniciado-------->");
		}
		catch(Exception e)
		{ e.printStackTrace(); }
	}
	
	@Override
	public ServletConfig getServletConfig() { 
		return null; 
	}

	@Override
	public String getServletInfo() { 
		return null; 
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		
	}

	@Override
	public void destroy() {
		
	}
}

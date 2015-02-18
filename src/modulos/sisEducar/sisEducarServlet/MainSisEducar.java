package modulos.sisEducar.sisEducarServlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import modulos.sisEducar.conexaoBanco.ConectaBanco;

public class MainSisEducar implements Servlet
{
	@Override
	public void init(ServletConfig arg0) throws ServletException
	{
		try
		{
			new ConectaBanco().getConection();
			System.out.println("Sistema SisEducar iniciado");
		}
		catch(Exception e)
		{ e.printStackTrace(); }
	}
	
	@Override
	public ServletConfig getServletConfig() { return null; }

	@Override
	public String getServletInfo() { return null; }

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException { }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}

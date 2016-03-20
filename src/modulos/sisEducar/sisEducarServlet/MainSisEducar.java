package modulos.sisEducar.sisEducarServlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import modulos.secretaria.servlet.ParametrosServlet;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class MainSisEducar implements Servlet
{
	@Override
	public void init(ServletConfig arg0) throws ServletException
	{
		try
		{
			//Busca a chave de acesso cadastrada no banco de dados
			String chaveAcessoSimples = new SisEducarDAO().obtemChaveAcesso();

			//Aqui ele defini qual será a chave de acesso do usuário logado
			ConstantesSisEducar.USUARIO_LOGADO = new SisEducarServlet().gerarChaveAcessoCriptografada(chaveAcessoSimples);
			
			/*Inicializa a classe de parâmetros para ela já construir e popular as informações que preecheram os combos de todas as telas, assim garantimos melhor perfoamance no sistema
			pois o mesmo está sendo chamado apenas uma vez*/
			new ParametrosServlet();
			
			System.out.println("<-------Sistema SisEducar iniciado-------->");
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

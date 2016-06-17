package modulos.sisEducar.utils;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.SessionScoped;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modulos.sisEducar.servlet.*;

/**
 * Classe que verifica se existe um usuario logado antes de permitir o acesso a uma pagina
 */
@SessionScoped
public class AuthenticationFilter implements Serializable, Filter {
	
	private static final long serialVersionUID = 1L;	
	/*-*-*-* Variaveis e Objetos Privados *-*-*-*/
	@SuppressWarnings("unused")
	private FilterConfig config;

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException 
	{	
		if(new SisEducarServlet().getSessionObject((HttpServletRequest)req, ConstantesSisEducar.USUARIO_LOGADO) == null) 
		{
			((HttpServletResponse)resp).sendRedirect(ConstantesSisEducar.PATH_PROJETO_NOME + "/login/login.xhtml");
		} 
		else 
		{
			chain.doFilter(req, resp);
		}
	}

	public void init(FilterConfig config) throws ServletException 
	{ this.config = config; }

	public void destroy() 
	{ config = null; }
}
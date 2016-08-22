package modulos.sisEducar.utils;

import java.io.IOException;

import javax.faces.bean.SessionScoped;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modulos.secretaria.om.Usuario;

/**
 * Classe que verifica se existe um usuario logado antes de permitir o acesso a uma pagina
 */
@SessionScoped
public class AuthenticationFilter implements Filter {
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = (HttpSession)req.getSession();
		
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
		
		if(usuarioLogado == null) {
			res.sendRedirect(req.getContextPath() + "/login/login.xhtml");
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException { 
	}

	@Override
	public void destroy() { 
	}
}
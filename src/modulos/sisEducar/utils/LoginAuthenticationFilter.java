package modulos.sisEducar.utils;

import java.io.IOException;

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

public class LoginAuthenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = (HttpSession)req.getSession();
		
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuario");
		
		if( usuarioLogado == null ) {
			chain.doFilter(request, response);
		} else {
			res.sendRedirect( req.getContextPath() + "/resources/templates/sisEducar/menuPrincipal.xhtml" );			
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {	
	}

	@Override
	public void destroy() {		
	}
}

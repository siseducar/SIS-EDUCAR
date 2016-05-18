package modulos.sisEducar.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
@WebFilter(urlPatterns={"/*"}, description="Request encoding utf-8")
public class ContextFilter implements Filter {
 
    @Override
    public void destroy() {
    }
 
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
 
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
 
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/";
 
        ContextPathController pathForm = 
            (ContextPathController) session.getAttribute("contextPathController");
 
        if (pathForm == null) {
            pathForm = new ContextPathController();
            pathForm.setPath(basePath);
            session.setAttribute("contextPathController", pathForm);
        }
 
        chain.doFilter(request, response);
    }
 
    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
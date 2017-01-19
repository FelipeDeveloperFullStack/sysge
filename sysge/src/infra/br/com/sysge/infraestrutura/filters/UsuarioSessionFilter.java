package br.com.sysge.infraestrutura.filters;

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

import br.com.sysge.model.conf.Usuario;

@WebFilter(filterName = "usuarioSessionFilter", urlPatterns = "/page_seguranca/p_login.xhtml")
public class UsuarioSessionFilter implements Filter {

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, 
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		HttpSession session = (HttpSession) request.getSession();
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		if(usuario == null){
			chain.doFilter(servletRequest, servletResponse); // Termina a requisição
		}else{
			response.sendRedirect(request.getContextPath() + "/pages/sys/p_dashboard.xhtml");
		}
		
	}
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {}

}

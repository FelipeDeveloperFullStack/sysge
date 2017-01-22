package br.com.sysge.infraestrutura.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.inject.spi.CDI;
import javax.faces.context.FacesContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import br.com.sysge.service.conf.UsuarioService;

@WebListener
public class SessionListener implements HttpSessionListener {

	private UsuarioService usuarioService;
	
	public SessionListener() {
		super();
		usuarioService = CDI.current().select(UsuarioService.class).get();
	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		 String ultimoAcesso = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date(event.getSession().getLastAccessedTime()));
         System.out.println("Data da sessão criada "+event.getSession().getId()+". Ultimo Acesso = "+ultimoAcesso);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		 String ultimoAcesso = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date(event.getSession().getLastAccessedTime()));
         System.out.println("Sessão expirada "+event.getSession().getId()+". Ultimo Acesso = "+ultimoAcesso);
         event.getSession().invalidate();
         FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
	
	
	
   
	
}

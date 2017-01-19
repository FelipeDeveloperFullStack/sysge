package br.com.sysge.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtil {

	public static void mensagemInfo(String mensagem){
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, facesMessage);
	}
	
	public static void mensagemErro(String mensagem){
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,mensagem);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, facesMessage);
		
	}
	
	public static void mensagemFatal(String mensagem){
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL,mensagem,mensagem);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, facesMessage);
		
	}
	public static void mensagemWarn(String mensagem){
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN,mensagem,mensagem);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, facesMessage);
		
	}
}

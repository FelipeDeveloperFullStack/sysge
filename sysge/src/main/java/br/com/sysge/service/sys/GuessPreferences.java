package br.com.sysge.service.sys;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class GuessPreferences implements Serializable{

	private static final long serialVersionUID = 2330138785773694068L;

	private String  theme = "hot-sneaks";
	
	public String getTheme(){
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(params.containsKey("theme")){
			theme = params.get("theme");
		}
		return theme;
	}
	
	public void setTheme(String theme){
		this.theme = theme;
	}
}

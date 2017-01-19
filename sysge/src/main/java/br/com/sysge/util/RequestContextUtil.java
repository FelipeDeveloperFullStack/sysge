package br.com.sysge.util;

import java.util.List;
import java.util.Map;


import org.primefaces.context.RequestContext;

public class RequestContextUtil{
	
	public static void execute(String path){
		RequestContext.getCurrentInstance().execute(path);
	}
	
	public static void openDialog(String view, Map<String, Object> options, Map<String, List<String>> params){
		RequestContext.getCurrentInstance().openDialog(view, options, params);
	}
	
	public static void update(String id){
		RequestContext.getCurrentInstance().update(id);
		
	}
	
	public static void closeDialog(Object view){
		RequestContext.getCurrentInstance().closeDialog(view);
	}

}

package br.com.sysge.controller.sys;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.sysge.util.RequestContextUtil;

@ManagedBean
@RequestScoped
public class TemplateViewPage implements Serializable {

	private static final long serialVersionUID = -4532122633294716766L;

	public void openDialog(String view, String headerElement, Long width, Long height, boolean modal) {
		Map<String, Object> options = new HashMap<String, Object>();
		
		options.put("minimizable", false);
		options.put("resizable", false);
		options.put("draggable", true);
		options.put("contentWidth", width);//900
		options.put("contentHeight", height);//550
		options.put("headerElement", headerElement);
		options.put("modal", modal);

		RequestContextUtil.openDialog(view, options, null);
		
	}
	
	public void closeDialog(Object view){
		RequestContextUtil.closeDialog(view);
	}
}
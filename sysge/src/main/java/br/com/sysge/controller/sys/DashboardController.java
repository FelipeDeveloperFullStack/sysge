package br.com.sysge.controller.sys;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

@Named
@ViewScoped
public class DashboardController implements Serializable{

	private static final long serialVersionUID = 248574364508549444L;

	private DashboardModel model;
	
	@PostConstruct
	public void init(){
		model = new DefaultDashboardModel();
		DashboardColumn column1 = new DefaultDashboardColumn();
	    
	    column1.addWidget("osAbertas");
	    column1.addWidget("produtoEstoqMinimo");
	    
	    model.addColumn(column1);
	}

	public DashboardModel getModel() {
		return model;
	}

	public void setModel(DashboardModel model) {
		this.model = model;
	}
	
	
}

package br.com.sysge.model.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.sysge.infraestrutura.dao.GenericDomain;
import br.com.sysge.model.conf.PerfilAcesso;

@Entity
@Table(name = "tbl_panel_menu")
public class PanelMenu extends GenericDomain{
	
	private static final long serialVersionUID = -2447779873179766104L;
	
	@Column(name = "menu_menu")
	private String menu;
	
	@Column(name = "menu_source")
	private boolean menuSource;
	
	@Column(name = "menu_target")
	private boolean menuTarget;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "perfil_acesso")
	private PerfilAcesso perfilAcesso;
	
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public PerfilAcesso getPerfilAcesso() {
		return perfilAcesso;
	}
	public void setPerfilAcesso(PerfilAcesso perfilAcesso) {
		this.perfilAcesso = perfilAcesso;
	}
	@Override
	public String toString() {
		return menu + " , "+ perfilAcesso + " , "+ menuSource + " , "+ menuTarget;
	}
	public boolean isMenuSource() {
		return menuSource;
	}
	public void setMenuSource(boolean menuSource) {
		this.menuSource = menuSource;
	}
	public boolean isMenuTarget() {
		return menuTarget;
	}
	public void setMenuTarget(boolean menuTarget) {
		this.menuTarget = menuTarget;
	}
	
}

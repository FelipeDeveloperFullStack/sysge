package br.com.sysge.converter;


import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import br.com.sysge.model.sys.PanelMenu;
import br.com.sysge.model.type.MenuPerfilAcesso;
import br.com.sysge.service.sys.PanelMenuService;


@FacesConverter(value = "primeFacesPickListConverter", managed = true, forClass = PanelMenu.class)
public class PrimeFacesPickListConverter implements Converter {
	
	private PanelMenuService panelMenuService;
	
	public PrimeFacesPickListConverter(){
		this.panelMenuService = CDI.current().select(PanelMenuService.class).get();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		try {
			if(arg1 instanceof PickList){
				Object dualList = ((PickList) arg1).getValue();
				DualListModel<PanelMenu> dl = (DualListModel<PanelMenu>) dualList;
				if(dl.getSource() == null){
					dl.setSource(setarMenuPerfilAcesso(new ArrayList<PanelMenu>()));
				}
				for(Object o : dl.getSource()){
					if(value.contains(((PanelMenu)o).getMenu())){
						return ((PanelMenu)o);
					}else{
						if(((PanelMenu)o).getPerfilAcesso() != null){
							List<PanelMenu> listMenus = panelMenuService.findAllByIdWithNativeQuery(
									((PanelMenu)o).getPerfilAcesso().getId(),
									Boolean.TRUE, "tbl_panel_menu", "perfil_acesso", "menu_target");
							for(PanelMenu m : listMenus){
								if(value.contains(m.getMenu())){
									return m;
								}
							}
						}
					}
				}
				for(Object o : dl.getTarget()){
					if(value.contains(((PanelMenu)o).getMenu())){
						return ((PanelMenu)o);
					}else{
						if(((PanelMenu)o).getPerfilAcesso() != null){
							List<PanelMenu> listMenus = panelMenuService.findAllByIdWithNativeQuery(
									((PanelMenu)o).getPerfilAcesso().getId(),
									Boolean.TRUE, "tbl_panel_menu", "perfil_acesso", "menu_source");
							for(PanelMenu m : listMenus){
								if(value.contains(m.getMenu())){
									return m;
								}
							}
						}
					}
				}
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<PanelMenu> setarMenuPerfilAcesso(List<PanelMenu> menus){
		for(MenuPerfilAcesso m : MenuPerfilAcesso.values()){
			PanelMenu menu = new PanelMenu();
			menu.setMenu(m.getMenu());
			menus.add(menu);
		}
		return menus;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		try {
			return arg2.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
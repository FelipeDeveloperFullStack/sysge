package br.com.sysge.service.sys;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.primefaces.model.DualListModel;

import br.com.sysge.infraestrutura.dao.GenericDaoImpl;
import br.com.sysge.model.conf.PerfilAcesso;
import br.com.sysge.model.sys.PanelMenu;
import br.com.sysge.model.type.MenuPerfilAcesso;
import br.com.sysge.model.type.MenuSistema;
import br.com.sysge.model.type.TipoAcesso;
import br.com.sysge.service.conf.PerfilAcessoService;

public class PanelMenuService extends GenericDaoImpl<PanelMenu, Long>{

	private static final long serialVersionUID = 8750947842342786618L;
	
	@Inject
	private PerfilAcessoService perfilAcessoService;
	
	public List<PanelMenu> setarMenuSource(PerfilAcesso perfilAcesso,DualListModel<PanelMenu> menus){
		try {
			if(perfilAcesso.getId() != null){
				return getListMenuSource(perfilAcesso);
			}else{
				if(menus == null){
					return setarMenuPerfilAcesso(new ArrayList<PanelMenu>());
				}else{
					if(!menus.getTarget().isEmpty()){
						if(menus.getTarget().size() == MenuSistema.values().length){
							return new ArrayList<PanelMenu>();
						}
					}else{
						return setarMenuPerfilAcesso(new ArrayList<PanelMenu>());
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e); 
		}
		return setarMenuPerfilAcesso(new ArrayList<PanelMenu>());
	}
	public List<PanelMenu> setarMenuTarget(PerfilAcesso perfilAcesso){
		if(perfilAcesso.getId() != null){
			List<PanelMenu> menus = getListMenuTarget(perfilAcesso);
			if(menus.isEmpty()){
				return new ArrayList<PanelMenu>();
			}else{
				return menus;
			}
		}else{
			return new ArrayList<PanelMenu>();
		}
	}
	
	private List<PanelMenu> getListMenuSource(PerfilAcesso perfilAcesso){
		return super.findAllByIdWithNativeQuery(perfilAcesso.getId(), Boolean.TRUE, "tbl_panel_menu", "perfil_acesso", "menu_source");
	}
	
	public List<PanelMenu> getListMenuTarget(PerfilAcesso perfilAcesso){
		return super.findAllByIdWithNativeQuery(perfilAcesso.getId(), Boolean.TRUE, "tbl_panel_menu", "perfil_acesso", "menu_target");
	}
	
	public void setarMenu(DualListModel<PanelMenu> menus, PerfilAcesso perfilAcesso){
		if(perfilAcesso.getId() == null){
			PerfilAcesso perfil = perfilAcessoService.salvar(perfilAcesso);
			salvarNovoMenuSource(menus, perfil);
			salvarNovoMenuTarget(menus, perfil);
			verificarTamanhoListaMenu(perfil);
		}else{
			setarMenuTarget(menus, perfilAcesso);
			verificarTamanhoListaMenu(perfilAcesso);
		}
	}
	
	private void verificarTamanhoListaMenu(PerfilAcesso perfilAcesso){
		if(getListMenuTarget(perfilAcesso).size() == 0){
			perfilAcesso.setTipoAcesso(TipoAcesso.NENHUM_MENU_SELECIONADO);
			perfilAcessoService.salvar(perfilAcesso);
		}else{
			if(getListMenuTarget(perfilAcesso).size() != MenuPerfilAcesso.values().length){
				perfilAcesso.setTipoAcesso(TipoAcesso.ACESSO_PERSONALIZADO);
				perfilAcessoService.salvar(perfilAcesso);
			}else{
				perfilAcesso.setTipoAcesso(TipoAcesso.ACESSO_TOTAL);
				perfilAcessoService.salvar(perfilAcesso);
			}
		}
	}
	
	private void salvarNovoMenuSource(DualListModel<PanelMenu> menus, PerfilAcesso perfilAcesso){
		for(PanelMenu menu : menus.getSource()){
			PanelMenu pm = new PanelMenu();
			pm.setMenu(menu.getMenu());
			pm.setMenuSource(true);
			pm.setPerfilAcesso(perfilAcesso);
			super.save(pm);
		}
	}
	
	public void setarMenuTarget(DualListModel<PanelMenu> menus, PerfilAcesso perfilAcesso){
			List<PanelMenu> listMenus = getListMenuTarget(perfilAcesso);
			if(listMenus.isEmpty()){
				for(PanelMenu menu : menus.getTarget()){
					editarMenuSource(menu, perfilAcesso);
				}
			}else{
				consistirMenuTarget(menus, listMenus, perfilAcesso);
				if(menus.getTarget().isEmpty()){
					if((listMenus.size() + menus.getSource().size()) == MenuSistema.values().length){
						return;
					}
				}else{
					if((listMenus.size() + menus.getTarget().size()) == MenuSistema.values().length){
						return;
					}
				}
				consistirMenuDataBase(menus, listMenus, perfilAcesso);
			}
	}
	
	private void consistirMenuTarget(DualListModel<PanelMenu> menus, List<PanelMenu> listMenus, PerfilAcesso perfilAcesso){
		for(PanelMenu pm : menus.getTarget()){
			if(listMenus.contains(pm)){
				System.out.println("OK");
			}else{
				editarMenuSource(pm, perfilAcesso);
			}
		}
	}
	
	private void consistirMenuDataBase(DualListModel<PanelMenu> menus, List<PanelMenu> listMenus, PerfilAcesso perfilAcesso){
		for(PanelMenu pm : listMenus){
			if(menus.getTarget().contains(pm)){
				System.out.println("OK");
			}else{
				editarMenuTarget(pm, perfilAcesso);
			}
		}
	}
	
	public void salvarNovoMenuTarget(DualListModel<PanelMenu> menus, PerfilAcesso perfilAcesso){
		for(PanelMenu menu : menus.getTarget()){
			PanelMenu pm = new PanelMenu();
			pm.setMenu(menu.getMenu());
			pm.setMenuTarget(true);
			pm.setPerfilAcesso(perfilAcesso);
			super.save(pm);
		}
	}
	private void editarMenuTarget(PanelMenu pm,PerfilAcesso perfilAcesso){
		pm.setMenuTarget(Boolean.FALSE);
		pm.setMenuSource(Boolean.TRUE);
		pm.setPerfilAcesso(perfilAcesso);
		super.save(pm);
	}
	
	private void editarMenuSource(PanelMenu pm,PerfilAcesso perfilAcesso){
		pm.setMenuTarget(Boolean.TRUE);
		pm.setMenuSource(Boolean.FALSE);
		pm.setPerfilAcesso(perfilAcesso);
		super.save(pm);
	}
	
	/*private List<PanelMenu> setarPanelMenu(List<PanelMenu> menus){
		 for(MenuSistema m : MenuSistema.values()){
			 PanelMenu menu = new PanelMenu();
			 menu.setMenu(m.getMenu());
			 menus.add(menu);
		 }
		 return menus;
	}*/
	
	public List<PanelMenu> setarMenuPerfilAcesso(List<PanelMenu> menus){
		for(MenuPerfilAcesso m : MenuPerfilAcesso.values()){
			PanelMenu menu = new PanelMenu();
			menu.setMenu(m.getMenu());
			menus.add(menu);
		}
		return menus;
	}
	
	
}

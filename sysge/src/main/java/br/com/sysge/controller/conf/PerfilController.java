package br.com.sysge.controller.conf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.com.sysge.model.conf.PerfilAcesso;
import br.com.sysge.model.sys.PanelMenu;
import br.com.sysge.model.type.Situacao;
import br.com.sysge.service.conf.PerfilAcessoService;
import br.com.sysge.service.sys.PanelMenuService;
import br.com.sysge.util.FacesUtil;
import br.com.sysge.util.RequestContextUtil;

@Named
@ViewScoped
public class PerfilController implements Serializable{

	private static final long serialVersionUID = -8278690574259597505L;
	
	private PerfilAcesso perfilAcesso;
	private int currentTab = 0;
	
	private DualListModel<PanelMenu> menus;
	
	protected List<PerfilAcesso> perfis;
	
	@Inject
	private PanelMenuService panelMenuService;
	
	@Inject
	private PerfilAcessoService perfilAcessoService;
	
	@PostConstruct
    public void init() {
		novoPerfil();
        perfis = new ArrayList<>();
	}
	
	private void createDualListModel(){
		menus = new DualListModel<PanelMenu>(setarMenuSource(menus), setarMenuTarget());
	}
	
	private List<PanelMenu> setarMenuSource(DualListModel<PanelMenu> menus){
		return panelMenuService.setarMenuSource(perfilAcesso, menus);
	}
	
	private List<PanelMenu> setarMenuTarget(){
		return panelMenuService.setarMenuTarget(perfilAcesso);
	}
	
	/*public void pesquisarPerfilAcesso(){
		setPerfis(perfilAcessoService.pesquisarPerfilAcesso(perfilAcesso));
		novoPerfil();
	}*/
	
	public void novoPerfil(){
		this.perfilAcesso = new PerfilAcesso();
		createDualListModel();
		setarTabIndex(0);
	}
	
	public void setarPerfilAcesso(PerfilAcesso perfilAcesso){
		this.perfilAcesso = perfilAcesso;
		createDualListModel();
		setarTabIndex(0);
	}
	
	public void salvar(){
		try {
			setarMenu(perfilAcesso);
		} catch (RuntimeException e) {
			FacesUtil.mensagemErro(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void editar(){
		try {
			perfilAcesso = perfilAcessoService.salvar(perfilAcesso);
			setarMenu(perfilAcesso);
		} catch (RuntimeException e) {
			FacesUtil.mensagemErro(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void setarMenu(PerfilAcesso perfilAcesso){
		panelMenuService.setarMenu(menus, perfilAcesso);
		FacesUtil.mensagemInfo("Perfil salvo com sucesso!");
		fecharDialogs();
		listarPerfil();
	}
	
	private void fecharDialogs(){
		RequestContextUtil.execute("PF('dialogNovoPerfil').hide();");
		RequestContextUtil.execute("PF('dialogEditarPerfil').hide();");
	}
	
	@SuppressWarnings("unchecked")
	public void onTransfer(TransferEvent event){
		if(event.isAdd()){
			menus.setTarget((List<PanelMenu>) event.getItems());
		}
		System.out.println(menus);
	}
	
	public void setarTabIndex(int tabIndex) {
	     this.setCurrentTab(tabIndex);
	}
	
	public void listarPerfil(){
		createDualListModel();
		setPerfis(perfilAcessoService.pesquisarPerfilAcesso(perfilAcesso));
	}
 
	public PerfilAcesso getPerfilAcesso() {
		return perfilAcesso;
	}

	public void setPerfilAcesso(PerfilAcesso perfilAcesso) {
		this.perfilAcesso = perfilAcesso;
	}
	
	public Situacao[] getSituacoes(){
		return Situacao.values();
	}
	
	public List<PerfilAcesso> getPerfis() {
		return perfilAcessoService.findAll();
	}

	public void setPerfis(List<PerfilAcesso> perfis) {
		this.perfis = perfis;
	}


	public DualListModel<PanelMenu> getMenus() {
		return menus;
	}


	public void setMenus(DualListModel<PanelMenu> menus) {
		this.menus = menus;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
	}
	
	
}

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
import br.com.sysge.model.conf.Usuario;
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
	private boolean existeUsuario;
	private Usuario usuario;
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
	
	public void pesquisarPerfilAcesso(){
		perfis = new ArrayList<PerfilAcesso>();
		perfis = perfilAcessoService.pesquisarPerfilAcesso(perfilAcesso);
	}
	
	public void novoPerfil(){
		this.perfilAcesso = new PerfilAcesso();
		createDualListModel();
		setarTabIndex(0);
	}
	
	public void setarPerfilAcesso(PerfilAcesso perfilAcesso){
		try {
			this.perfilAcesso = perfilAcesso;
			existeUsuario = perfilAcessoService.verificarSeExistePerfilAcesso(perfilAcesso.getId());
			createDualListModel();
			setarTabIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	public void novaListaPerfilAcesso(){
		perfis = new ArrayList<PerfilAcesso>();
	}
	
	public void salvar(){
		try {
			verificarSeExistePerfilCadastradoComMesmaDescricao(perfilAcesso);
			setarMenu(perfilAcesso);
			novaListaPerfilAcesso();
		} catch (RuntimeException e) {
			FacesUtil.mensagemErro(e.getMessage());
		}
		
	}
	
	public void editar(){
		try {
			verificarSeExistePerfilCadastradoComMesmaDescricao(perfilAcesso);
			if(perfilAcesso.getSituacao().equals(Situacao.INATIVO)){
				usuario = new Usuario();
				if(perfilAcessoService.verificarSeExistePerfilAcesso(perfilAcesso.getId())){
					usuario = perfilAcessoService.procurarUsuarioPorPerfil(perfilAcesso.getId());
					RequestContextUtil.execute("PF('dialog_confirmacao_perfil_inativar').show();");
				}else{
					editarPerfil();
				}
			}else{
				editarPerfil();
			}
		} catch (RuntimeException e) {
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	public void editarPerfil(){
		perfilAcesso = perfilAcessoService.salvar(perfilAcesso);
		setarMenu(perfilAcesso);
		novaListaPerfilAcesso();
		RequestContextUtil.execute("PF('dialog_confirmacao_perfil_inativar').hide();");
	}
	
	private void verificarSeExistePerfilCadastradoComMesmaDescricao(PerfilAcesso perfilAcesso){
		perfilAcessoService.verificarSeExistePerfilCadastradoComMesmaDescricao(perfilAcesso);
	}
	
	private void setarMenu(PerfilAcesso perfilAcesso){
		panelMenuService.setarMenu(menus, perfilAcesso);
		FacesUtil.mensagemInfo("Perfil salvo com sucesso!");
		fecharDialogs();
		//listarPerfil();
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
		return  perfis;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isExisteUsuario() {
		return existeUsuario;
	}

	public void setExisteUsuario(boolean existeUsuario) {
		this.existeUsuario = existeUsuario;
	}
	
	
}

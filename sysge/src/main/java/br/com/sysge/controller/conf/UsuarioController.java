package br.com.sysge.controller.conf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.sysge.controller.sys.TemplateViewPage;
import br.com.sysge.model.conf.PerfilAcesso;
import br.com.sysge.model.conf.Usuario;
import br.com.sysge.model.rh.Funcionario;
import br.com.sysge.model.type.Situacao;
import br.com.sysge.model.type.TipoAcesso;
import br.com.sysge.service.conf.PerfilAcessoService;
import br.com.sysge.service.conf.UsuarioService;
import br.com.sysge.service.rh.FuncionarioService;
import br.com.sysge.util.FacesUtil;
import br.com.sysge.util.RequestContextUtil;

@Named
@ViewScoped
public class UsuarioController implements Serializable{

	private static final long serialVersionUID = 5133625956304965649L;
	
	private Usuario usuario;
	private boolean botaoDisable = false;
	
	protected List<Usuario> usuarios;
	protected List<Funcionario> funcionarios;
	protected List<PerfilAcesso> perfisAcesso;
	
	private int currentTab = 0;
	
	@Inject
	private TemplateViewPage templateViewPage;
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private FuncionarioService funcionarioService;
	
	@Inject
	private PerfilAcessoService perfilAcessoService;
	
	private static final String PAGE_FUNCIONARIO = "/pages_framework/p_funcionario.xhtml";
	
	@PostConstruct
	public void init(){
		novaListaUsuario();
		funcionarios = new ArrayList<Funcionario>();
		perfisAcesso = new ArrayList<PerfilAcesso>();
		usuario = new Usuario();
	}
	
	public void novoUsuario(){
		this.usuario = new Usuario();
		setCurrentTab(0);
		setBotaoDisable(false);
	}
	
	public void obterPageFuncionario(){
		templateViewPage.openDialog(PAGE_FUNCIONARIO, 
				"idTitleFuncionário", 800L, 450L, true);
	}
	
	public void fecharDialogFuncionario(Funcionario funcionario){
		templateViewPage.closeDialog(funcionario);
	}
	
	public void funcionarioSelecionado(SelectEvent event){
		Funcionario funcionario = (Funcionario) event.getObject();
		if(funcionarioService.verificarSeFuncionarioEDiferenteDeNull(funcionario)){
			usuario.setFuncionario(funcionario);
		}
	}
	
	public void salvarUsuario(){
		try {
			
			if(!usuarioService.isExisteUsuario(usuario)){
				setBotaoDisable(true);
				RequestContextUtil.update(":formNovoUsuario");
				usuarioService.salvar(usuario);
				fecharDialog();
				FacesUtil.mensagemInfo("Usuário salvo com sucesso!");
				novaListaUsuario();
			}else{
				FacesUtil.mensagemWarn("Já existe um usuário cadastrado com o mesmo 'nome de usuário', "
						+ "por favor escolha um outro 'nome de usuário'.");
			}
		} catch (RuntimeException e) {
			setBotaoDisable(false);
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	public void pesquisar(){
		novaListaUsuario();
		usuarios = usuarioService.findByParametersForSituation(usuario.getPerfilAcesso().getPerfilAcesso(), usuario.getSituacao(), "perfilAcesso.perfilAcesso", "=", "", "");
		System.out.println(usuarios);
	}
	
	public void novaListaUsuario(){
		usuarios = new ArrayList<Usuario>();
	}
	
	public void fecharDialog(){
		RequestContextUtil.execute("PF('dialogNovoUsuario').hide();");
		RequestContextUtil.execute("PF('dialogEditarUsuario').hide();");
	}
	
	public void setarUsuario(Usuario usuario){
		this.usuario = usuario;
		setCurrentTab(0);
		setBotaoDisable(false);
	}
	
	public List<Usuario> getUsuarios(){
		return usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarioService.findAll();
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
	}

	public List<PerfilAcesso> getPerfisAcesso() {
		return perfilAcessoService.findByParametersForSituation
				(TipoAcesso.NENHUM_MENU_SELECIONADO, Situacao.ATIVO, "tipoAcesso", "<>", "", "");
	}

	public void setPerfisAcesso(List<PerfilAcesso> perfisAcesso) {
		this.perfisAcesso = perfisAcesso;
	}
	
	public Situacao[] getSituacoes(){
		return Situacao.values();
	}

	public boolean isBotaoDisable() {
		return botaoDisable;
	}

	public void setBotaoDisable(boolean botaoDisable) {
		this.botaoDisable = botaoDisable;
	}

}

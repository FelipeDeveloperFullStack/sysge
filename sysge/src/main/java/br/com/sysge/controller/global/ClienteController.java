package br.com.sysge.controller.global;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sysge.model.gestserv.GarantiaServico;
import br.com.sysge.model.gestserv.Servico;
import br.com.sysge.model.global.Cliente;
import br.com.sysge.model.type.Atividade;
import br.com.sysge.model.type.Categoria;
import br.com.sysge.model.type.Situacao;
import br.com.sysge.model.type.TipoContribuinte;
import br.com.sysge.model.type.TipoPessoa;
import br.com.sysge.model.type.UnidadeFederativa;
import br.com.sysge.service.gestserv.ServicoService;
import br.com.sysge.service.global.ClienteService;
import br.com.sysge.service.sys.WebServiceCEPService;
import br.com.sysge.util.FacesUtil;
import br.com.sysge.util.RequestContextUtil;

@Named
@ViewScoped
public class ClienteController implements Serializable {

	private static final long serialVersionUID = -2506223673479436354L;

	private Cliente cliente;
	
	private int currentTab = 0;
	
	private GarantiaServico garantiaServico;
	
	private Servico servico;
	
	private List<Cliente> clientes;
	
	@SuppressWarnings("unused")
	private List<Servico> servicos;
	
	private List<GarantiaServico> garantiaServicos;
	
	@Inject
	private ServicoService servicoService;

	@Inject
	private ClienteService clienteService;

	@PostConstruct
	public void init() {
		novoCliente();
		garantiaServico = new GarantiaServico();
		servicos = new ArrayList<Servico>();
		garantiaServicos = new ArrayList<GarantiaServico>();
		servico = new Servico();
	}

	public void novoCliente() {
		this.cliente = new Cliente();
		setarTabIndex(0);
	}
	
	public void adicionarGarantia(){
		garantiaServico.setServico(servico);
		garantiaServicos.add(garantiaServico);
		garantiaServico = new GarantiaServico();
		servico = new Servico();
	}
	
	public void removerGarantia(GarantiaServico garantiaServico){
		garantiaServicos.remove(garantiaServico);
	}
	
	public void salvar() {
		try {
			cliente.setNomeDaPessoaFisica(cliente.getNomeTemporario());
			cliente.setNomeFantasia(cliente.getNomeTemporario());
			cliente = clienteService.salvar(cliente);
			this.clientes = new ArrayList<Cliente>();
			FacesUtil.mensagemInfo("Cliente salvo com sucesso!");
			fecharDialogs();
		} catch (Exception e) {
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	public void cancelar(){
		this.clientes = new ArrayList<Cliente>();
	}
	
	/*private void listarClientes(Cliente cliente){
		try {
			clientes = clienteService.findBySituationAndCategoriaAndTipoPessoa
					(cliente.getSituacao(), cliente.getCategoria(), cliente.getTipoPessoa());
		} catch (Exception e) {
			FacesUtil.mensagemErro(e.getMessage());
		}
	}*/
	
	public void consultarCnpj(){
		try {
			cliente = clienteService.consultarCnpj(cliente);
			FacesUtil.mensagemInfo("Dados encontrados com sucesso!");
			fecharDialodDeProcurarCnpj();
		} catch (Exception e) {
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	public void consultarCep(){
		try {
			Map<Object, Object> mapCep = new HashMap<Object, Object>();
			mapCep.putAll(WebServiceCEPService.procurarCEP(cliente.getCep()));
			this.cliente.setLogradouro(mapCep.get(5).toString() + " " + mapCep.get(1).toString());
			this.cliente.setCidade(mapCep.get(2).toString());
			this.cliente.setUnidadeFederativa(UnidadeFederativa.valueOf(mapCep.get(3).toString()));
			this.cliente.setBairro(mapCep.get(4).toString());
		} catch (Exception e) {
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	public void pesquisar(){
		cliente.setNomeDaPessoaFisica(cliente.getNomeTemporario());
		cliente.setNomeFantasia(cliente.getNomeTemporario());
		clientes = clienteService.procurarCliente(cliente);
	}
	
	public void setarCliente(Cliente cliente){
		this.cliente = clienteService.verificarTipoPessoa(cliente);
		setarTabIndex(0);
	}
	
	private void fecharDialogs(){
		RequestContextUtil.execute("PF('dialogNovoCliente').hide();");
		RequestContextUtil.execute("PF('dialogEditarCliente').hide();");
	}
	
	private void fecharDialodDeProcurarCnpj(){
		RequestContextUtil.execute("PF('dialog_procurar_cnpj').hide();");
	}
	
	public void setarTabIndex(int tabIndex) {
	     this.setCurrentTab(tabIndex);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Situacao[] getSituacoes() {
		return Situacao.values();
	}

	public TipoPessoa[] getTipoPessoa() {
		return TipoPessoa.values();
	}

	public Categoria[] getCategorias() {
		return Categoria.values();
	}

	public Atividade[] getAtividades() {
		return Atividade.values();
	}

	public TipoContribuinte[] getTipoContribuintes() {
		return TipoContribuinte.values();
	}

	public UnidadeFederativa[] getUnidadesFederativas() {
		return UnidadeFederativa.values();
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public GarantiaServico getGarantiaServico() {
		if(garantiaServico == null){
			garantiaServico = new GarantiaServico();
		}
		return garantiaServico;
	}

	public void setGarantiaServico(GarantiaServico garantiaServico) {
		this.garantiaServico = garantiaServico;
	}

	public List<Servico> getServicos() {
		return servicoService.findBySituation(Situacao.ATIVO);
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public List<GarantiaServico> getGarantiaServicos() {
		return garantiaServicos;
	}

	public void setGarantiaServicos(List<GarantiaServico> garantiaServicos) {
		this.garantiaServicos = garantiaServicos;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
	}

	
}

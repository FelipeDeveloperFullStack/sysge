package br.com.sysge.controller.gestserv;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.sysge.controller.sys.TemplateViewPage;
import br.com.sysge.model.estoque.Produto;
import br.com.sysge.model.gestserv.OrdemServico;
import br.com.sysge.model.gestserv.Servico;
import br.com.sysge.model.gestserv.ServicoOrdemServico;
import br.com.sysge.model.global.Cliente;
import br.com.sysge.model.rh.Funcionario;
import br.com.sysge.model.type.FormaPagamento;
import br.com.sysge.model.type.Garantia;
import br.com.sysge.model.type.Pago;
import br.com.sysge.model.type.Situacao;
import br.com.sysge.model.type.StatusOS;
import br.com.sysge.model.type.TipoDesconto;
import br.com.sysge.service.gestserv.OrdemServicoService;
import br.com.sysge.service.gestserv.ServicoOrdemServicoService;
import br.com.sysge.service.global.ClienteService;
import br.com.sysge.service.rh.FuncionarioService;
import br.com.sysge.util.FacesUtil;
import br.com.sysge.util.RequestContextUtil;

@Named
@ViewScoped
public class OrdemServicoController implements Serializable {

	private static final long serialVersionUID = 1267523434219231347L;

	private OrdemServico ordemServico;
	
	private Servico servico;
	
	private List<OrdemServico> ordensServicos;
	
	@SuppressWarnings("unused")
	private List<OrdemServico> ordensServicosAbertas;

	private List<Servico> servicos;

	private List<Produto> produtos;

	@SuppressWarnings("unused")
	private List<Funcionario> funcionarios;

	private List<Cliente> clientes;
	
	private List<ServicoOrdemServico> listaServicos;
	
	@Inject
	private FuncionarioService funcionarioService;
	
	@Inject
	private ClienteService clienteService;
	
	@Inject
	private TemplateViewPage templateViewPage;
	
	@Inject
	private OrdemServicoService ordemServicoService;
	
	@Inject
	private ServicoOrdemServicoService servicoOrdemServicoService;
	
	private static final String PAGE_CLIENTE = "/pages_framework/p_cliente.xhtml";
	private static final String PAGE_SERVICO = "/pages_framework/p_servicos.xhtml";
	
	@PostConstruct
	public void init() {
		novaOrdemServico();
	}
	
	public void obterPageCliente(){
		templateViewPage.openDialog(PAGE_CLIENTE, 
				"idTitleCliente", 800L, 485L, true);
	}
	
	public void fecharDialogCliente(Cliente cliente){
		templateViewPage.closeDialog(cliente);
	}
	
	public void clienteSelecionado(SelectEvent event){
		this.ordemServico.setCliente(clienteService.verificarTipoPessoa((Cliente) event.getObject()));
	}
	
	public void obterPageServico(){
		templateViewPage.openDialog(PAGE_SERVICO, 
				"idTitleServico", 800L, 450L, true);
	}
	
	public void fecharDialogServico(Servico servico){
		templateViewPage.closeDialog(servico);
	}
	
	public void servicoSelecionado(SelectEvent event){
		this.servico = (Servico) event.getObject();
		if(servicoOrdemServicoService.verificarSeExisteServicoNaTabela(listaServicos, servico)){
			FacesUtil.mensagemWarn("Já existe um serviço '"+this.servico.getNome() +
					"' na lista, por favor escolha outro serviço!");
		}else{
			somarTotalServico(this.servico.getValor());
			adicionarServico();
		}
	}
	
	private void somarTotalServico(BigDecimal valorServico){
		//totalServico = ordemServico.getTotalServico();
		//totalServico = totalServico.add(valorServico);
		ordemServico.setTotalServico(ordemServico.getTotalServico().add(valorServico));
	}
	
	public void pesquisarOS(){
		try {
			ordensServicos = new ArrayList<OrdemServico>();
			ordensServicos = ordemServicoService.pesquisarPorNumeroEStatusOS(ordemServico);
		} catch (RuntimeException e) {
			FacesUtil.mensagemWarn(e.getMessage());
		}
	}
	
	public void adicionarServico(){
		ServicoOrdemServico servicoOrdemServico = new ServicoOrdemServico();
		servicoOrdemServico.setServico(servico);
		servicoOrdemServico.setSubTotal(servico.getValor());
		servicoOrdemServico.setValor(servico.getValor());
		this.listaServicos.add(servicoOrdemServico);
		servico = new Servico();
	}
	
	public void calcularValorServico(ServicoOrdemServico servicoOrdemServico){
		ordemServico.setTotalServico(BigDecimal.ZERO);
		for(ServicoOrdemServico so : listaServicos){
			if(so.getServico().getId() == servicoOrdemServico.getServico().getId()){
				BigDecimal valorServico = servicoOrdemServico.getServico().getValor().
									      multiply(BigDecimal.valueOf(servicoOrdemServico.getQuantidade()));
				so.setValor(servicoOrdemServico.getValor());
				so.setSubTotal(valorServico);
			}
			ordemServico.setTotalServico(ordemServico.getTotalServico().add(so.getSubTotal()));
		}
	}
	
	public void setarOrdemServico(OrdemServico ordemServico){
		this.ordemServico.setTotalServico(BigDecimal.ZERO);
		this.ordemServico.setCliente(clienteService.verificarTipoPessoa(ordemServico.getCliente()));
		this.ordemServico = ordemServico;
		this.listaServicos = ordemServicoService.procurarServicosOS(ordemServico.getId());
		RequestContextUtil.execute("PF('dialogEditarOrdemDeServico').show();");
		ordensServicos = new ArrayList<OrdemServico>();
	}
	
	public void novaOrdemServico(){
		this.ordemServico = new OrdemServico();
		this.servico = new Servico();
		this.ordensServicos = new ArrayList<OrdemServico>();
		this.servicos = new ArrayList<Servico>();
		this.produtos = new ArrayList<Produto>();
		this.funcionarios = new ArrayList<Funcionario>();
		this.clientes = new ArrayList<Cliente>();
		this.listaServicos = new ArrayList<ServicoOrdemServico>();
		this.ordensServicosAbertas = new ArrayList<OrdemServico>();
	}
	
	public void salvar(){
		try {
			if(ordemServico.getStatusOS() == StatusOS.CANCELADO){
				RequestContextUtil.execute("PF('dialog_motivo_cancelamento').show();");
			}else{
				salvarOS();
				RequestContextUtil.execute("PF('dialog_opcoes').show();");
				fecharDialogs();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	private void salvarOS(){
		if(ordemServico.getId() == null){
			ordemServico.setTotal(ordemServico.getTotalServico().add(ordemServico.getTotalProduto()));
			ordemServico = ordemServicoService.salvar(ordemServico);
			ordemServicoService.consistirServico(listaServicos, ordemServico);
		}else{
			ordemServico.setTotal(ordemServico.getTotalServico().add(ordemServico.getTotalProduto()));
			ordemServico = ordemServicoService.salvar(ordemServico);
			ordemServicoService.consistirServico(listaServicos, ordemServico);
		}
		
		FacesUtil.mensagemInfo("Ordem de servico de nº "+ordemServico.getId() + " salvo com sucesso!");
	}
	
	public void salvarMotivoCancelamento(){
		if(ordemServico.getMotivoCancelamento().trim().isEmpty()){
			FacesUtil.mensagemWarn("O motivo de cancelamento é obrigatório!");
		}else{
			salvarOS();
			RequestContextUtil.execute("PF('dialog_motivo_cancelamento').hide();");
			fecharDialogs();
		}
	}
	
	public void fecharDialogs(){
		RequestContextUtil.execute("PF('dialogNovaOrdemDeServico').hide();");
		RequestContextUtil.execute("PF('dialogEditarOrdemDeServico').hide();");
		ordensServicos = new ArrayList<OrdemServico>();
	}
	
	public void fecharDialogMotivoCancelamento(){
		fecharDialogs();
		this.ordemServico = ordemServicoService.findById(ordemServico.getId());
		pesquisarOS();
	}

	public StatusOS[] getStatusOs() {
		return StatusOS.values();
	}

	public Pago[] getPagos() {
		return Pago.values();
	}

	public Garantia[] getGarantia() {
		return Garantia.values();
	}
	
	public TipoDesconto[] getTiposDesconto() {
		return TipoDesconto.values();
	}

	public FormaPagamento[] getFormaPagamento() {
		return FormaPagamento.values();
	}

	public OrdemServico getOrdemServico() {
			if(ordemServico == null){
				ordemServico = new OrdemServico();
			}
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public List<OrdemServico> getOrdensServicos() {
		return ordensServicos;
	}

	public void setOrdensServicos(List<OrdemServico> ordensServicos) {
		this.ordensServicos = ordensServicos;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarioService.findBySituation(Situacao.ATIVO);
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Servico getServico() {
		if(servico == null){
			servico = new Servico();
		}
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public List<ServicoOrdemServico> getListaServicos() {
		return listaServicos;
	}

	public void setListaServicos(List<ServicoOrdemServico> listaServicos) {
		this.listaServicos = listaServicos;
	}

	public List<OrdemServico> getOrdensServicosAbertas() {
		return ordemServicoService.findByStatusOs(StatusOS.ABERTO);
	}

	public void setOrdensServicosAbertas(List<OrdemServico> ordensServicosAbertas) {
		this.ordensServicosAbertas = ordensServicosAbertas;
	}

}

package br.com.sysge.controller.gestserv;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.sysge.controller.sys.TemplateViewPage;
import br.com.sysge.model.estoque.Produto;
import br.com.sysge.model.financ.CondicaoPagamento;
import br.com.sysge.model.financ.ParcelasPagamentoOs;
import br.com.sysge.model.gestserv.OrdemServico;
import br.com.sysge.model.gestserv.ProdutoOrdemServico;
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
import br.com.sysge.service.financ.CondicaoPagamentoService;
import br.com.sysge.service.financ.ParcelasPagamentoOsService;
import br.com.sysge.service.gestserv.OrdemServicoService;
import br.com.sysge.service.gestserv.ProdutoOrdemServicoService;
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
	
	private ParcelasPagamentoOs parcelasPagamentoOs;
	
	private Servico servico;
	
	private Produto produto;;
	
	private List<OrdemServico> ordensServicos;
	
	private List<ParcelasPagamentoOs> parcelas;
	
	@SuppressWarnings("unused")
	private List<CondicaoPagamento> condicoesPagamento;
	
	@SuppressWarnings("unused")
	private List<OrdemServico> ordensServicosAbertas;

	private List<Servico> servicos;

	private List<Produto> produtos;

	@SuppressWarnings("unused")
	private List<Funcionario> funcionarios;

	private List<Cliente> clientes;
	
	private List<ServicoOrdemServico> listaServicos;
	
	private List<ProdutoOrdemServico> listaProdutos;
	
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
	
	@Inject
	private ProdutoOrdemServicoService produtoOrdemServicoService;
	
	@Inject
	private ParcelasPagamentoOsService parcelasPagamentoOsService;
	
	@Inject
	private CondicaoPagamentoService condicaoPagamentoService;
	
	private static final String PAGE_CLIENTE = "/pages_framework/p_cliente.xhtml";
	private static final String PAGE_SERVICO = "/pages_framework/p_servicos.xhtml";
	private static final String PAGE_PRODUTO = "/pages_framework/p_produto.xhtml";
	
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
	public void obterPageProduto(){
		templateViewPage.openDialog(PAGE_PRODUTO, 
				"idTitleProduto", 800L, 450L, true);
	}
	
	public void fecharDialogProduto(Produto produto){
		templateViewPage.closeDialog(produto);
	}
	
	public void produtoSelecionado(SelectEvent event){
		this.produto = (Produto) event.getObject();
		if(produtoOrdemServicoService.verificarSeExisteProdutoNaTabela(listaProdutos, produto)){
			FacesUtil.mensagemWarn("Já existe um produto '"+this.produto.getDescricaoProduto() +
					"' na lista, por favor escolha outro produto!");
		}else{
			somarTotalProduto(this.produto.getValorVenda());
			adicionarProduto();
		}
	}
	
	public void calcularDescontoParcela(ParcelasPagamentoOs parcelasPagamentoOs){
		for(ParcelasPagamentoOs p : parcelas){
			if(parcelasPagamentoOs.getId() == p.getId()){
				p.setValorCobrado(parcelasPagamentoOs.getValorParcela().subtract(parcelasPagamentoOs.getValorDesconto()));
			}
		}
	}
	
	public void gerarParcelas(){
		try {
			parcelas = parcelasPagamentoOsService.gerarParcelas(ordemServico, parcelas, parcelasPagamentoOs);
		} catch (RuntimeException e) {
			FacesUtil.mensagemWarn(e.getMessage());
		}
	}
	
	public void calcularDescontoReais(){
		ordemServico.setDescontoPorcento(BigDecimal.ZERO);
		ordemServico.setTotal((ordemServico.getTotalServico().add(ordemServico.getTotalProduto())).subtract(ordemServico.getDescontoReais()));
	}
	
	public void calcularDescontoPorcentagem(){
		ordemServico.setDescontoReais(BigDecimal.ZERO);
		BigDecimal porcentagem = (ordemServico.getTotalServico().add(ordemServico.getTotalProduto()))
								 .divide(new BigDecimal("100"))
								 .multiply(ordemServico.getDescontoPorcento());
		ordemServico.setTotal((ordemServico.getTotalServico().add(ordemServico.getTotalProduto())).subtract(porcentagem));
		
	}
	
	private void somarTotalServico(BigDecimal valorServico){
		ordemServico.setTotalServico(ordemServico.getTotalServico().add(valorServico));
	}
	private void somarTotalProduto(BigDecimal valorProduto){
		ordemServico.setTotalProduto(ordemServico.getTotalProduto().add(valorProduto));
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
		ordemServico.setTotal(ordemServico.getTotalServico().add(ordemServico.getTotalProduto()));
		this.listaServicos.add(servicoOrdemServico);
		servico = new Servico();
	}
	
	public void adicionarProduto(){
		ProdutoOrdemServico produtoOrdemServico = new ProdutoOrdemServico();
		produtoOrdemServico.setProduto(produto);
		produtoOrdemServico.setSubTotal(produto.getValorVenda());
		produtoOrdemServico.setValor(produto.getValorVenda());
		ordemServico.setTotal(ordemServico.getTotalProduto().add(ordemServico.getTotalServico()));
		this.listaProdutos.add(produtoOrdemServico);
		produto = new Produto();
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
			ordemServico.setTotal((ordemServico.getTotalServico().add(ordemServico.getTotalProduto())).add(so.getSubTotal()));
			
			ordemServico.setTotalServico(ordemServico.getTotalServico().add(so.getSubTotal()));
		}
	}
	
	public void calcularValorProduto(ProdutoOrdemServico produtoOrdemServico){
		ordemServico.setTotalProduto(BigDecimal.ZERO);
		for(ProdutoOrdemServico po : listaProdutos){
			if(po.getProduto().getId() == produtoOrdemServico.getProduto().getId()){
				BigDecimal valorProduto = produtoOrdemServico.getProduto().getValorVenda().
									      multiply(BigDecimal.valueOf(produtoOrdemServico.getQuantidade()));
				po.setValor(produtoOrdemServico.getValor());
				po.setSubTotal(valorProduto);
			}
			ordemServico.setTotal((ordemServico.getTotalServico().add(ordemServico.getTotalProduto())).add(po.getSubTotal()));
			
			ordemServico.setTotalProduto(ordemServico.getTotalProduto().add(po.getSubTotal()));
		}
	}
	
	public void setarOrdemServico(OrdemServico ordemServico){
		this.ordemServico.setTotalServico(BigDecimal.ZERO);
		this.ordemServico.setCliente(clienteService.verificarTipoPessoa(ordemServico.getCliente()));
		this.ordemServico = ordemServico;
		this.listaServicos = ordemServicoService.procurarServicosOS(ordemServico.getId());
		this.listaProdutos = ordemServicoService.procurarProdutosOS(ordemServico.getId());
		this.parcelas = parcelasPagamentoOsService.procurarParcelasPorOS(ordemServico.getId());
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
		this.listaProdutos = new ArrayList<ProdutoOrdemServico>();
		this.ordensServicosAbertas = new ArrayList<OrdemServico>();
		this.parcelas = new ArrayList<ParcelasPagamentoOs>();
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
			ordemServico = ordemServicoService.salvar(ordemServico);
			parcelasPagamentoOsService.salvar(ordemServico, parcelas);
			ordemServicoService.consistirServico(listaServicos, ordemServico);
			ordemServicoService.consistirProduto(listaProdutos, ordemServico);
		}else{
			ordemServico = ordemServicoService.salvar(ordemServico);
			parcelasPagamentoOsService.salvar(ordemServico, parcelas);
			ordemServicoService.consistirServico(listaServicos, ordemServico);
			ordemServicoService.consistirProduto(listaProdutos, ordemServico);
		}
		
		FacesUtil.mensagemInfo("Ordem de servico de nº "+ordemServico.getId() + " salvo com sucesso!");
	}
	
	public void salvarMotivoCancelamento(){
		if(ordemServico.getMotivoCancelamento().trim().isEmpty()){
			FacesUtil.mensagemWarn("O motivo de cancelamento é obrigatório!");
		}else{
			ordemServico.setDataSaida(Calendar.getInstance().getTime());
			ordemServico.setHoraSaida(Calendar.getInstance().getTime());
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

	public List<CondicaoPagamento> getCondicoesPagamento() {
		return condicaoPagamentoService.findBySituation(Situacao.ATIVO);
	}

	public void setCondicoesPagamento(List<CondicaoPagamento> condicoesPagamento) {
		this.condicoesPagamento = condicoesPagamento;
	}

	public List<ParcelasPagamentoOs> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<ParcelasPagamentoOs> parcelas) {
		this.parcelas = parcelas;
	}

	public Produto getProduto() {
		if(produto == null){
			produto = new Produto();
		}
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<ProdutoOrdemServico> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<ProdutoOrdemServico> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

}

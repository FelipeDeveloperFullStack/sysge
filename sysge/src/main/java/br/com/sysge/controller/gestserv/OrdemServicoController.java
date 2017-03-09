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
import br.com.sysge.service.estoque.ProdutoService;
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
	
	private int currentTab = 0;
	
	private ProdutoOrdemServico produtoOrdemServico;

	private OrdemServico ordemServico;
	
	private Long quantidadeAdicionada = 0L;
	
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
	
	private List<ProdutoOrdemServico> listaProdutosTemporário;
	
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
	
	@Inject
	private ProdutoService produtoService;
	
	private static final String PAGE_CLIENTE = "/pages_framework/p_cliente.xhtml";
	private static final String PAGE_SERVICO = "/pages_framework/p_servicos.xhtml";
	private static final String PAGE_PRODUTO = "/pages_framework/p_produto.xhtml";
	
	@PostConstruct
	public void init() {
		novaOrdemServico();
	}
	
	public void obterPageCliente(){
		templateViewPage.openDialog(PAGE_CLIENTE, 
				"idTitleCliente", 900L, 495L, true);
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
		//servico.setId(null);
		servicoOrdemServico.setServico(servico);
		servicoOrdemServico.setSubTotal(servico.getValor());
		servicoOrdemServico.setValor(servico.getValor());
		servicoOrdemServico.setOrdemServico(ordemServico);
		
		//servicoOrdemServicoService.save(servicoOrdemServico);
		
		listaServicos.add(servicoOrdemServico);
		
		//this.listaServicos = ordemServicoService.procurarServicosOS(ordemServico.getId());
		ordemServico.setTotal(ordemServico.getTotalServico().add(ordemServico.getTotalProduto()));
		
		//ordemServicoService.salvar(ordemServico);
		servico = new Servico();
	}
	
	public void adicionarProduto(){
		ProdutoOrdemServico produtoOrdemServico = new ProdutoOrdemServico();
		//produto.setId(null);
		produtoOrdemServico.setProduto(produto);
		produtoOrdemServico.setSubTotal(produto.getValorVenda());
		produtoOrdemServico.setValor(produto.getValorVenda());
		produtoOrdemServico.setOrdemServico(ordemServico);
		
		listaProdutos.add(produtoOrdemServico);
		
		/*produtoOrdemServicoService.save(produtoOrdemServico);*/
		
		/*this.listaProdutos = ordemServicoService.procurarProdutosOS(ordemServico.getId());*/
		ordemServico.setTotal(ordemServico.getTotalProduto().add(ordemServico.getTotalServico()));
		
		/*ordemServicoService.salvar(ordemServico);*/
		
		/*for(ProdutoOrdemServico pos : listaProdutos){
			if(produto.getId() == pos.getProduto().getId()){
				pos.getProduto().setQuantidadeEstoque(pos.getProduto().getQuantidadeEstoque()- pos.getQuantidade());
				produtoService.salvar(pos.getProduto());
				produtoOrdemServicoService.save(pos);
			}
		}*/
		
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
	
	public void setarProduto(ProdutoOrdemServico produtoOrdemServico){
		this.produtoOrdemServico = produtoOrdemServico;
	}
	
	public void calcularValorProduto(){

		if(quantidadeAdicionada == 0L){
			FacesUtil.mensagemWarn("A quantidade é obrigatório!");
		}else{
			ordemServico.setTotalProduto(BigDecimal.ZERO);
			produtoOrdemServico.setQuantidade(produtoOrdemServico.getQuantidade() + quantidadeAdicionada);
			
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
			RequestContextUtil.execute("PF('dialog_quantidade_estoque_produto').hide();");
			//quantidadeAdicionada = 0L;
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
		
		setarTabIndex(0);
	}
	
	public void novaOrdemServico(){
		this.ordemServico = new OrdemServico();
		this.servico = new Servico();
		this.produtoOrdemServico = new ProdutoOrdemServico();
		this.ordensServicos = new ArrayList<OrdemServico>();
		this.servicos = new ArrayList<Servico>();
		this.produtos = new ArrayList<Produto>();
		this.funcionarios = new ArrayList<Funcionario>();
		this.clientes = new ArrayList<Cliente>();
		this.listaServicos = new ArrayList<ServicoOrdemServico>();
		this.listaProdutos = new ArrayList<ProdutoOrdemServico>();
		this.ordensServicosAbertas = new ArrayList<OrdemServico>();
		this.parcelas = new ArrayList<ParcelasPagamentoOs>();
		this.listaProdutosTemporário = new ArrayList<ProdutoOrdemServico>();
		
		setarTabIndex(0);
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
			
			List<ProdutoOrdemServico> listaProdutoOS = produtoOrdemServicoService.findByListProperty(ordemServico.getId(), "ordemServico.id");
			
			for(ProdutoOrdemServico pos : listaProdutos){
				if(listaProdutoOS.isEmpty()){
					pos.getProduto().setQuantidadeEstoque(pos.getProduto().getQuantidadeEstoque() - pos.getQuantidade());
					produtoService.salvar(pos.getProduto());
				}else{
					for(ProdutoOrdemServico ps : listaProdutoOS){
						List<ProdutoOrdemServico> prodBd = produtoOrdemServicoService.findByListProperty(pos.getProduto().getId(), "produto.id");
						if(prodBd.isEmpty()){
							subtrairQuantidadeEstoqueProduto(pos, ps);
						}else{
							if(ps.getQuantidade() != pos.getQuantidade()){
								subtrairQuantidadeEstoqueProduto(pos, ps);
							}
						}
						
					}
				}
				produtoOrdemServicoService.save(pos);
			}
			for(ServicoOrdemServico sos : listaServicos){
				servicoOrdemServicoService.save(sos);
			}
			/*ordemServicoService.consistirServico(listaServicos, ordemServico);
			ordemServicoService.consistirProduto(listaProdutos, ordemServico);*/
		}
		
		FacesUtil.mensagemInfo("Ordem de servico de nº "+ordemServico.getId() + " salvo com sucesso!");
	}
	
	private void subtrairQuantidadeEstoqueProduto(ProdutoOrdemServico pos, ProdutoOrdemServico ps){
		if(pos.getQuantidade() > ps.getQuantidade()){
			pos.getProduto().setQuantidadeEstoque(pos.getProduto().getQuantidadeEstoque() - this.quantidadeAdicionada);
			produtoService.salvar(pos.getProduto());
		}else{
			pos.setQuantidade(pos.getQuantidade() - 1);
			pos.getProduto().setQuantidadeEstoque(pos.getProduto().getQuantidadeEstoque() - this.quantidadeAdicionada);
			produtoService.salvar(pos.getProduto());
		}
		this.quantidadeAdicionada = 0L;
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
		
		/*for(ProdutoOrdemServico pos : listaProdutos){
			pos.getProduto().setQuantidadeEstoque(pos.getProduto().getQuantidadeEstoque()- pos.getQuantidade());
			produtoService.salvar(pos.getProduto());
			produtoOrdemServicoService.save(pos);
		}*/
	}
	
	public void fecharDialogMotivoCancelamento(){
		fecharDialogs();
		this.ordemServico = ordemServicoService.findById(ordemServico.getId());
		pesquisarOS();
	}
	
	/**
	 * @param servicoOrdemServico
	 */
	public void removerServico(ServicoOrdemServico servicoOrdemServico){
		if(servicoOrdemServico.getId() == null){
			for(int i = 0; i < this.listaServicos.size(); i++){
				if(this.listaServicos.get(i).getServico().getNome().equals(servicoOrdemServico.getServico().getNome())){
					this.listaServicos.remove(i);
					ordemServico.setTotalServico(ordemServico.getTotalServico().subtract(servicoOrdemServico.getSubTotal()));
					ordemServico.setTotal(BigDecimal.ZERO);
					ordemServico.setTotal(ordemServico.getTotalServico().add(ordemServico.getTotalProduto()));
				}
			}
		}else{
			
			this.listaServicos = servicoOrdemServicoService.removerServicoOSPeloID(listaServicos, servicoOrdemServico);	
			
			if(!servicoOrdemServicoService.verificarSeExisteIdNull(listaServicos)){
				this.listaServicos = ordemServicoService.procurarServicosOS(ordemServico.getId());
			}
			
			ordemServico.setTotalServico(ordemServico.getTotalServico().subtract(servicoOrdemServico.getSubTotal()));
			ordemServico.setTotal(BigDecimal.ZERO);
			ordemServico.setTotal(ordemServico.getTotalServico().add(ordemServico.getTotalProduto()));
			ordemServicoService.salvar(ordemServico);
		}
	}
	
	public void removerProduto(ProdutoOrdemServico produtoOrdemServico){
		
		if(produtoOrdemServico.getId() == null){
			for(int i = 0; i < listaProdutos.size(); i++){
				if(this.listaProdutos.get(i).getProduto().getDescricaoProduto().trim().equals(produtoOrdemServico.getProduto().getDescricaoProduto().trim())){
					this.listaProdutos.remove(i);
					ordemServico.setTotalProduto(ordemServico.getTotalProduto().subtract(produtoOrdemServico.getSubTotal()));
					ordemServico.setTotal(BigDecimal.ZERO);
					ordemServico.setTotal(ordemServico.getTotalProduto().add(ordemServico.getTotalServico()));
				}
			}
		}else{
			
			for(int i = 0; i < listaProdutos.size(); i++){
				if(this.listaProdutos.get(i).getProduto().getDescricaoProduto().trim().equals(produtoOrdemServico.getProduto().getDescricaoProduto().trim())){
					produtoOrdemServicoService.remove(produtoOrdemServico.getId());
					this.listaProdutos.remove(i);
				}
			}	
			
			produtoOrdemServico.getProduto().setQuantidadeEstoque(produtoOrdemServico.getProduto().getQuantidadeEstoque() + produtoOrdemServico.getQuantidade());
			produtoService.salvar(produtoOrdemServico.getProduto());
			
			ordemServico.setTotalProduto(ordemServico.getTotalProduto().subtract(produtoOrdemServico.getSubTotal()));
			ordemServico.setTotal(BigDecimal.ZERO);
			ordemServico.setTotal(ordemServico.getTotalProduto().add(ordemServico.getTotalServico()));
			ordemServicoService.salvar(ordemServico);
		}
	}
	
	public void setarTabIndex(int tabIndex) {
	     this.setCurrentTab(tabIndex);
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
		return ordemServicoService.findByStatusOs(StatusOS.ABERTO, StatusOS.EM_ANDAMENTO);
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

	public int getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
	}

	public List<ProdutoOrdemServico> getListaProdutosTemporário() {
		return listaProdutosTemporário;
	}

	public void setListaProdutosTemporário(List<ProdutoOrdemServico> listaProdutosTemporário) {
		this.listaProdutosTemporário = listaProdutosTemporário;
	}

	public ProdutoOrdemServico getProdutoOrdemServico() {
		return produtoOrdemServico == null ? new ProdutoOrdemServico() : this.produtoOrdemServico;
	}

	public void setProdutoOrdemServico(ProdutoOrdemServico produtoOrdemServico) {
		this.produtoOrdemServico = produtoOrdemServico;
	}

	public Long getQuantidadeAdicionada() {
		return quantidadeAdicionada;
	}

	public void setQuantidadeAdicionada(Long quantidadeAdicionada) {
		this.quantidadeAdicionada = quantidadeAdicionada;
	}

}

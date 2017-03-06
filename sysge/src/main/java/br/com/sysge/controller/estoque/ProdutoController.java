package br.com.sysge.controller.estoque;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sysge.model.estoque.Produto;
import br.com.sysge.model.global.Cliente;
import br.com.sysge.model.global.Fornecedor;
import br.com.sysge.model.type.Situacao;
import br.com.sysge.service.estoque.ProdutoService;
import br.com.sysge.service.global.FornecedorService;
import br.com.sysge.util.FacesUtil;
import br.com.sysge.util.RequestContextUtil;

@Named
@ViewScoped
public class ProdutoController implements Serializable{

	private static final long serialVersionUID = 7321092633765106412L;
	
	private Produto produto;
	
	private List<Produto> produtos;
	
	@SuppressWarnings("unused")
	private List<Produto> produtosEstoqueMinimo;
	
	@SuppressWarnings("unused")
	private List<Cliente> fornecedores;
	
	@Inject
	private ProdutoService produtoService;
	
	@Inject
	private FornecedorService fornecedorService;
	
	public Situacao[] getSituacoes(){
		return Situacao.values();
	}
	
	public void pesquisar(){
		this.produtos = new ArrayList<Produto>();
		this.produtos = produtoService.pesquisarProduto(produto);
	}
	
	public void novo(){
		this.produto = new Produto();
		this.produtos = new ArrayList<Produto>();
		this.fornecedores = new ArrayList<Cliente>();
		
		verificarSeExisteFornecedorAtivoCadastrado();
	}
	
	private void verificarSeExisteFornecedorAtivoCadastrado(){
		if(produtoService.verificarSeExisteFornecedorAtivo()){
			FacesUtil.mensagemWarn("Não existe nenhum fornecedor 'Ativo' cadastrado, "
									+ " para cadastrar um produto é preciso selecionar um fornecedor"
									+ " 'ativo', verifique e tente novamente!");
		}else{
			RequestContextUtil.execute("PF('dialogNovoServico').show();");
		}
	}
	
	public void salvar(){
		try {
			produtoService.salvar(produto);
			FacesUtil.mensagemInfo("Produto salvo com sucesso!");
			novaListaProduto();
			fecharDialogs();
		} catch (RuntimeException e) {
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	
	
	public void fecharDialogs(){
		RequestContextUtil.execute("PF('dialogNovoServico').hide();");
		RequestContextUtil.execute("PF('dialogEditarServico').hide();");
	}
	
	public void novaListaProduto(){
		this.produto = new Produto();
		this.produtos = new ArrayList<Produto>();
		this.fornecedores = new ArrayList<Cliente>();
	}
	
	public void setarProduto(Produto produto){
		this.produto = produto;
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

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedorService.findBySituation(Situacao.ATIVO);
	}

	public void setFornecedores(List<Cliente> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<Produto> getProdutosEstoqueMinimo() {
		return produtoService.obterProdutoQuantidadeMinimoEstoque();
	}

	public void setProdutosEstoqueMinimo(List<Produto> produtosEstoqueMinimo) {
		this.produtosEstoqueMinimo = produtosEstoqueMinimo;
	}
	
	

}

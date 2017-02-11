package br.com.sysge.model.estoque;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.sysge.infraestrutura.dao.GenericDomain;
import br.com.sysge.model.global.Fornecedor;
import br.com.sysge.model.type.Situacao;

@Entity
@Table(name = "tbl_produto")
public class Produto extends GenericDomain{

	private static final long serialVersionUID = -7328127398997221454L;

	private String codigoProduto;
	
	private String descricaoProduto;
	
	private BigDecimal valorCusto = BigDecimal.ZERO;
	
	private BigDecimal valorVenda = BigDecimal.ZERO;
	
	private long quantidadeEstoque = 0L;
	
	private long quantidadeEstoqueMinimo = 0L;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Fornecedor fornecedor;
	
	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
	private boolean mostrarEstoqueMinimoTelaInicial;

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public String getDescricaoProduto() {
		return descricaoProduto;
	}

	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public BigDecimal getValorCusto() {
		return valorCusto;
	}

	public void setValorCusto(BigDecimal valorCusto) {
		this.valorCusto = valorCusto;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public long getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(long quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public long getQuantidadeEstoqueMinimo() {
		return quantidadeEstoqueMinimo;
	}

	public void setQuantidadeEstoqueMinimo(long quantidadeEstoqueMinimo) {
		this.quantidadeEstoqueMinimo = quantidadeEstoqueMinimo;
	}

	public Fornecedor getFornecedor() {
		if(fornecedor == null){
			fornecedor = new Fornecedor();
		}
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public boolean isMostrarEstoqueMinimoTelaInicial() {
		return mostrarEstoqueMinimoTelaInicial;
	}

	public void setMostrarEstoqueMinimoTelaInicial(boolean mostrarEstoqueMinimoTelaInicial) {
		this.mostrarEstoqueMinimoTelaInicial = mostrarEstoqueMinimoTelaInicial;
	}
	
	
}

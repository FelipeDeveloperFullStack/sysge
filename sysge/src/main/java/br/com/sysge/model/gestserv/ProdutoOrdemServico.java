package br.com.sysge.model.gestserv;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.sysge.infraestrutura.dao.GenericDomain;
import br.com.sysge.model.estoque.Produto;


@Entity
@Table(name = "tbl_produto_os")
public class ProdutoOrdemServico extends GenericDomain{
	
	private static final long serialVersionUID = 1626357136962819168L;

	@OneToOne(fetch  =FetchType.EAGER)
	private Produto produto;
	
	@OneToOne(fetch  =FetchType.EAGER)
	private OrdemServico ordemServico;
	
	private Long quantidade = 1L;
	
	private BigDecimal valor = BigDecimal.ZERO;
	
	private BigDecimal subTotal = BigDecimal.ZERO;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}
	
	

}

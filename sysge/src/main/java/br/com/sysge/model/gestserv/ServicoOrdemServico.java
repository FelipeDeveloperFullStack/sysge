package br.com.sysge.model.gestserv;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.sysge.infraestrutura.dao.GenericDomain;


@Entity
//@IdClass(ServicoOrdemServicoId.class)
@Table(name = "tbl_servico_os")
public class ServicoOrdemServico extends GenericDomain{
	
	private static final long serialVersionUID = 1626357136962819168L;

	//@Id
	//@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "servico_id")
	@OneToOne(fetch  =FetchType.EAGER)
	private Servico servico;
	
	//@Id
	//@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "ordem_servico_id")
	@OneToOne(fetch  =FetchType.EAGER)
	private OrdemServico ordemServico;
	
	private Long quantidade = 1L;
	
	private BigDecimal valor = BigDecimal.ZERO;
	
	private BigDecimal subTotal = BigDecimal.ZERO;

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(long quantidade) {
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

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}
	
	

}

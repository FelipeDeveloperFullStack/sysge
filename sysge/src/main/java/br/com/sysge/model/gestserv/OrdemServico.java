package br.com.sysge.model.gestserv;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.sysge.infraestrutura.dao.GenericDomain;
import br.com.sysge.model.financ.CondicaoPagamento;
import br.com.sysge.model.global.Cliente;
import br.com.sysge.model.rh.Funcionario;
import br.com.sysge.model.type.FormaPagamento;
import br.com.sysge.model.type.Garantia;
import br.com.sysge.model.type.Pago;
import br.com.sysge.model.type.StatusOS;
import br.com.sysge.model.type.TipoDesconto;

@Entity
@Table(name = "tbl_ordem_servico")
public class OrdemServico extends GenericDomain {

	private static final long serialVersionUID = -520625131525167597L;
	
	private boolean gerouReceitaFinanceiro;

	@Temporal(TemporalType.DATE)
	private Date dataEntrada = Calendar.getInstance().getTime();

	@Temporal(TemporalType.TIME)
	private Date horaEntrada = Calendar.getInstance().getTime();

	@Temporal(TemporalType.DATE)
	private Date dataSaida;

	@Temporal(TemporalType.TIME)
	private Date horaSaida;

	@Enumerated(EnumType.STRING)
	private StatusOS statusOS;

	@OneToOne(fetch = FetchType.EAGER)
	private Cliente cliente;

	@OneToOne(fetch = FetchType.EAGER)
	private Funcionario funcionario;

	private String marca;

	private String numeroSerie;

	private String modelo;

	private String numeroPatrimonio;

	private String acessorios;

	private String defeito;

	private String laudoTecnico;

	@Enumerated(EnumType.STRING)
	private FormaPagamento formaPagamento;
	
	@OneToOne(fetch = FetchType.EAGER)
	private CondicaoPagamento condicaoPagamento;

	@Enumerated(EnumType.STRING)
	private Pago pago;

	private BigDecimal valorPago = BigDecimal.ZERO;

	@Enumerated(EnumType.STRING)
	private Garantia garantia;

	private BigDecimal totalServico = BigDecimal.ZERO;
	
	private BigDecimal totalProduto = BigDecimal.ZERO;
	
	@Enumerated(EnumType.STRING)
	private TipoDesconto tipoDesconto;
	
	private BigDecimal descontoReais = BigDecimal.ZERO;

	private BigDecimal descontoPorcento = BigDecimal.ZERO;

	private BigDecimal subTotal = BigDecimal.ZERO;

	private BigDecimal total = BigDecimal.ZERO;

	private String observacoes;

	private String motivoCancelamento;

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Date getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(Date horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Date getHoraSaida() {
		return horaSaida;
	}

	public void setHoraSaida(Date horaSaida) {
		this.horaSaida = horaSaida;
	}

	public StatusOS getStatusOS() {
		return statusOS;
	}

	public void setStatusOS(StatusOS statusOS) {
		this.statusOS = statusOS;
	}

	public Cliente getCliente() {
		if (cliente == null) {
			cliente = new Cliente();
		}
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionario() {
		if (funcionario == null) {
			funcionario = new Funcionario();
		}
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNumeroPatrimonio() {
		return numeroPatrimonio;
	}

	public void setNumeroPatrimonio(String numeroPatrimonio) {
		this.numeroPatrimonio = numeroPatrimonio;
	}

	public String getAcessorios() {
		return acessorios;
	}

	public void setAcessorios(String acessorios) {
		this.acessorios = acessorios;
	}

	public String getDefeito() {
		return defeito;
	}

	public void setDefeito(String defeito) {
		this.defeito = defeito;
	}

	public String getLaudoTecnico() {
		return laudoTecnico;
	}

	public void setLaudoTecnico(String laudoTecnico) {
		this.laudoTecnico = laudoTecnico;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public Garantia getGarantia() {
		return garantia;
	}

	public void setGarantia(Garantia garantia) {
		this.garantia = garantia;
	}

	public BigDecimal getDescontoReais() {
		return descontoReais;
	}

	public void setDescontoReais(BigDecimal descontoReais) {
		this.descontoReais = descontoReais;
	}

	public BigDecimal getDescontoPorcento() {
		return descontoPorcento;
	}

	public void setDescontoPorcento(BigDecimal descontoPorcento) {
		this.descontoPorcento = descontoPorcento;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public BigDecimal getTotalServico() {
		return totalServico;
	}

	public void setTotalServico(BigDecimal totalServico) {
		this.totalServico = totalServico;
	}

	public BigDecimal getTotalProduto() {
		return totalProduto;
	}

	public void setTotalProduto(BigDecimal totalProduto) {
		this.totalProduto = totalProduto;
	}

	public TipoDesconto getTipoDesconto() {
		return tipoDesconto;
	}

	public void setTipoDesconto(TipoDesconto tipoDesconto) {
		this.tipoDesconto = tipoDesconto;
	}

	public boolean isGerouReceitaFinanceiro() {
		return gerouReceitaFinanceiro;
	}

	public void setGerouReceitaFinanceiro(boolean gerouReceitaFinanceiro) {
		this.gerouReceitaFinanceiro = gerouReceitaFinanceiro;
	}

	public CondicaoPagamento getCondicaoPagamento() {
		if(condicaoPagamento == null){
			condicaoPagamento = new CondicaoPagamento();
		}
		return condicaoPagamento;
	}

	public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
		this.condicaoPagamento = condicaoPagamento;
	}
	
	

}

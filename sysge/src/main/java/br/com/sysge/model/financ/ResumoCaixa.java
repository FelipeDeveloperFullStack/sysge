package br.com.sysge.model.financ;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.sysge.infraestrutura.dao.GenericDomain;

@Entity
@Table(name = "tbl_resumo_caixa")
public class ResumoCaixa extends GenericDomain {

	private static final long serialVersionUID = 3149510261682394858L;

	private BigDecimal saldoInicial = new BigDecimal("150.00");
	private BigDecimal totalEntrada = new BigDecimal("640.87");
	private BigDecimal totalSaida = new BigDecimal("321.54");
	private BigDecimal saldoOperacional = new BigDecimal("319.33");
	private BigDecimal saldoFinal = new BigDecimal("469.33");

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public BigDecimal getTotalEntrada() {
		return totalEntrada;
	}

	public void setTotalEntrada(BigDecimal totalEntrada) {
		this.totalEntrada = totalEntrada;
	}

	public BigDecimal getTotalSaida() {
		return totalSaida;
	}

	public void setTotalSaida(BigDecimal totalSaida) {
		this.totalSaida = totalSaida;
	}

	public BigDecimal getSaldoOperacional() {
		return saldoOperacional;
	}

	public void setSaldoOperacional(BigDecimal saldoOperacional) {
		this.saldoOperacional = saldoOperacional;
	}

	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}

	public void setSaldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
	}

}

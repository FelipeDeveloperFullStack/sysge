package br.com.sysge.model.gestserv;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.sysge.infraestrutura.dao.GenericDomain;

@Entity
@Table(name = "tbl_forma_pagamento")
public class FormaPagamento extends GenericDomain{

	private static final long serialVersionUID = -2932635791736380587L;

	private String descricao;
	
	private long numeroParcelas;
	
	private long intervaloDias;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public long getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(long numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	public long getIntervaloDias() {
		return intervaloDias;
	}

	public void setIntervaloDias(long intervaloDias) {
		this.intervaloDias = intervaloDias;
	}
	
	
	
}

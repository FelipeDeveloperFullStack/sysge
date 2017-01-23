package br.com.sysge.model.financ;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import br.com.sysge.infraestrutura.dao.GenericDomain;
import br.com.sysge.model.type.Situacao;

@Entity
@Table(name = "tbl_condicao_pagamento")
public class CondicaoPagamento extends GenericDomain{

	private static final long serialVersionUID = -2932635791736380587L;

	private String descricao;
	
	private long numeroParcelas = 1L;
	
	private long intervaloDias;
	
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

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

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	
	
	
}

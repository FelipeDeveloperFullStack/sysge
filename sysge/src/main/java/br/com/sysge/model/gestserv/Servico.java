package br.com.sysge.model.gestserv;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;

import br.com.sysge.infraestrutura.dao.GenericDomain;
import br.com.sysge.model.type.Situacao;

@Entity
@Table(name = "tbl_servico")
public class Servico extends GenericDomain {

	private static final long serialVersionUID = 2576493816494710460L;

	@Column(name = "serv_nome")
	private String nome;
	
	@Column(name = "serv_valor")
	@DecimalMin(value = "0", message = "O valor mínimo é zero!")
	private BigDecimal valor;
	
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(name = "serv_situacao")
	private Situacao situacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}

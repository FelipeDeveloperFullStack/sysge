package br.com.sysge.model.gestserv;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.sysge.infraestrutura.dao.GenericDomain;
import br.com.sysge.model.global.Cliente;

@Entity
@Table(name = "tbl_garantia_servico")
public class GarantiaServico extends GenericDomain{

	private static final long serialVersionUID = 7225316930871704572L;
	
	@OneToOne(fetch =FetchType.EAGER)
	private Servico servico;
	
	@Temporal(TemporalType.DATE)
	private Date dataInicial = Calendar.getInstance().getTime();
	
	@Temporal(TemporalType.DATE)
	private Date dataFinal = Calendar.getInstance().getTime();;
	
	@OneToOne(fetch =FetchType.EAGER)
	private Cliente cliente;
	
	private String quantidadeDiasRestante;

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getQuantidadeDiasRestante() {
		return quantidadeDiasRestante;
	}

	public void setQuantidadeDiasRestante(String quantidadeDiasRestante) {
		this.quantidadeDiasRestante = quantidadeDiasRestante;
	}

}

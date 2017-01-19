package br.com.sysge.model.gestserv;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ServicoOrdemServicoId implements Serializable{

	private static final long serialVersionUID = -6924501620995522295L;
	
	private long servico;
	
	private long ordemServico;

	public long getServico() {
		return servico;
	}

	public void setServico(long servico) {
		this.servico = servico;
	}

	public long getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(long ordemServico) {
		this.ordemServico = ordemServico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ordemServico ^ (ordemServico >>> 32));
		result = prime * result + (int) (servico ^ (servico >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServicoOrdemServicoId other = (ServicoOrdemServicoId) obj;
		if (ordemServico != other.ordemServico)
			return false;
		if (servico != other.servico)
			return false;
		return true;
	}

	

}

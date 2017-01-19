package br.com.sysge.model.conf;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.sysge.infraestrutura.dao.GenericDomain;

@Entity
@Table(name = "tbl_backup_historico")
public class BackupHistorico extends GenericDomain{
	
	private static final long serialVersionUID = -4413837792851172035L;

	private String nomeArquivo;
	
	private String tamanhoArquivo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataBackup;
	
	private String diretorio;

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getTamanhoArquivo() {
		return tamanhoArquivo;
	}

	public void setTamanhoArquivo(String tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}

	public Date getDataBackup() {
		return dataBackup;
	}

	public void setDataBackup(Date dataBackup) {
		this.dataBackup = dataBackup;
	}

	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}
	
	
	
}

package br.com.sysge.model.conf;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.sysge.infraestrutura.dao.GenericDomain;

@Entity
@Table(name = "tbl_config_backup")
public class ConfiguracaoBackup extends GenericDomain{

	private static final long serialVersionUID = 8610505648257982399L;

	@Column(name = "configbacku_caminho_arquivo")
	private String caminhoArquivoMysqlDump;
	
	@Column(name = "configbacku_diretorio")
	private String diretorio;
	
	@Column(name = "configbacku_automatico")
	private boolean automatico;
	
	@Column(name = "")
	@Temporal(TemporalType.TIME)
	private Date tempoBackupAutomatico;
	
	@Column(name = "configbackup_responsavel")
	private String responsavel;
	
	@Column(name = "configbacku_email")
	private String email;
	
	public String getCaminhoArquivoMysqlDump() {
		return caminhoArquivoMysqlDump;
	}

	public void setCaminhoArquivoMysqlDump(String caminhoArquivoMysqlDump) {
		this.caminhoArquivoMysqlDump = caminhoArquivoMysqlDump;
	}

	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

	public boolean isAutomatico() {
		return automatico;
	}

	public void setAutomatico(boolean automatico) {
		this.automatico = automatico;
	}

	public Date getTempoBackupAutomatico() {
		return tempoBackupAutomatico;
	}

	public void setTempoBackupAutomatico(Date tempoBackupAutomatico) {
		this.tempoBackupAutomatico = tempoBackupAutomatico;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

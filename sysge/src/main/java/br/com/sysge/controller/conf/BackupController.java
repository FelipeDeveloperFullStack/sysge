package br.com.sysge.controller.conf;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sysge.model.conf.BackupHistorico;
import br.com.sysge.model.conf.ConfiguracaoBackup;
import br.com.sysge.service.conf.BackupService;
import br.com.sysge.service.conf.ConfiguracaoBackupService;
import br.com.sysge.util.DateUtil;
import br.com.sysge.util.FacesUtil;

@Named
@ViewScoped
public class BackupController implements Serializable{

	private static final long serialVersionUID = -6691709383659177389L;
	
	@Inject
	private ConfiguracaoBackupService configuracaoBackupService;
	
	private ConfiguracaoBackup configuracaoBackup;
	
	@SuppressWarnings("unused")
	private List<BackupHistorico> backupHistoricos;
	
	private LocalDate dataInicial;
	
	private LocalDate dataFinal;
	
	@Inject
	private BackupService backupService;
	
	@PostConstruct
	public void init(){
		novo();
		setarDadosConfiguracao();
		mostrarData();
	}
	
	private void novo(){
		configuracaoBackup = new ConfiguracaoBackup();
		backupHistoricos = new ArrayList<BackupHistorico>();
	}
	
	private void mostrarData(){
		dataInicial = LocalDate.now();;
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(DateUtil.asDate(dataInicial));
		int dia = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		int mes = calendar.get((Calendar.MONTH)) + 1;
		int ano = calendar.get(Calendar.YEAR);
		try {
			dataInicial = DateUtil.asLocalDate((new SimpleDateFormat("dd/MM/yyyy"))
					.parse(dia+"/"+mes+"/"+ano));
			dataFinal = LocalDate.now();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void setarDadosConfiguracao(){
		for(ConfiguracaoBackup config : configuracaoBackupService.findAll()){
			configuracaoBackup.setCaminhoArquivoMysqlDump(config.getCaminhoArquivoMysqlDump());
			configuracaoBackup.setDiretorio(config.getDiretorio());
			configuracaoBackup.setAutomatico(config.isAutomatico());
			configuracaoBackup.setTempoBackupAutomatico(config.getTempoBackupAutomatico());
			configuracaoBackup.setResponsavel(config.getResponsavel());
			configuracaoBackup.setEmail(config.getEmail());
			configuracaoBackup.setId(config.getId());
		}
	}

	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<BackupHistorico> getBackupHistoricos() {
		return backupService.findAll();
	}

	public void setBackupHistoricos(List<BackupHistorico> backupHistoricos) {
		this.backupHistoricos = backupHistoricos;
	}

	public ConfiguracaoBackup getConfiguracaoBackup() {
		if(this.configuracaoBackup == null){
			this.configuracaoBackup = new ConfiguracaoBackup();
		}
		return configuracaoBackup;
	}

	public void setConfiguracaoBackup(ConfiguracaoBackup configuracaoBackup) {
		this.configuracaoBackup = configuracaoBackup;
	}
	
	public void salvar(){
		try {
			configuracaoBackupService.salvar(configuracaoBackup);
			FacesUtil.mensagemInfo("Configuração salva com sucesso!");
		} catch (RuntimeException e) {
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	public void fazerBackup(){
		try {
			backupService.fazerBackup();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			FacesUtil.mensagemInfo("Backup realizado com sucesso as "+ sdf.format(DateUtil.asDate(LocalDateTime.now())));
		} catch (RuntimeException e) {
			FacesUtil.mensagemErro(e.getMessage());
			e.printStackTrace();
		}catch (IOException e) {
			FacesUtil.mensagemErro(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void pesquisarPorData(){
		try {
			this.backupHistoricos = backupService.listarPorData(DateUtil.asDate(dataInicial), DateUtil.asDate(dataFinal));
		} catch (RuntimeException e) {
			FacesUtil.mensagemErro(e.getMessage());
			e.printStackTrace();
		}
	}

}

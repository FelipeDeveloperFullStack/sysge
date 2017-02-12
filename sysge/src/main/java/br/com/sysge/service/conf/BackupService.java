package br.com.sysge.service.conf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.inject.Inject;

import br.com.sysge.infraestrutura.dao.GenericDaoImpl;
import br.com.sysge.model.conf.BackupHistorico;
import br.com.sysge.model.conf.ConfiguracaoBackup;
import br.com.sysge.util.DateUtil;
import br.com.sysge.util.FacesUtil;

public class BackupService extends GenericDaoImpl<BackupHistorico, Long>{

	private static final long serialVersionUID = 7547494882791200676L;
	
	@Inject
	private ConfiguracaoBackupService configuracaoBackupService;

	
	private BackupHistorico backupHistorico;
	
	public BackupService(){
		this.backupHistorico = new BackupHistorico();
	}
	
	public BackupHistorico fazerBackup() throws IOException {
		List<ConfiguracaoBackup> configuracaoBackups = configuracaoBackupService.findAll();
		if(configuracaoBackups.isEmpty()){
			throw new RuntimeException("Não é possível fazer o backup, não existe nenhum configuração cadastrada!"); 
		}else{
			for(ConfiguracaoBackup configuracaoBackup : configuracaoBackups){
				if(configuracaoBackup.getCaminhoArquivoMysqlDump().trim().isEmpty()){
					throw new RuntimeException("O caminho do arquivo 'mysqldump' não foi definida nas configurações! Não é possivel fazer o backup!");
				}
				if(configuracaoBackup.getDiretorio().trim().isEmpty()){
					throw new RuntimeException("O diretório não foi definido nas configurações, não é possível fazer o backup!");
				}
				if(configuracaoBackup.isAutomatico()){
					//agendarBackup(configuracaoBackup.getTempoBackupAutomatico());
					//FacesUtil.mensagemInfo("Backup agendado para todos os dias as "+configuracaoBackup.getTempoBackupAutomatico());
					FacesUtil.mensagemInfo("Backup agendado para todos os dias as "+configuracaoBackup.getTempoBackupAutomatico());
				}else{
					return processarBackup();
				}
			}
			return new BackupHistorico();
		}
		
	}
	
private void setarBackupHistorico(ConfiguracaoBackup config , BackupHistorico backupHistorico, String nomeArquivo, String tamanhoArquivo){
		
		backupHistorico.setNomeArquivo(nomeArquivo);
		backupHistorico.setDataBackup(DateUtil.asDate(LocalDateTime.now()));
		backupHistorico.setTamanhoArquivo(tamanhoArquivo);
		backupHistorico.setDiretorio(config.getDiretorio());
		
		this.backupHistorico = backupHistorico;
	}
	
	@SuppressWarnings("resource")
	public BackupHistorico processarBackup() throws IOException{
		
		for(ConfiguracaoBackup config : listarConfiguracaoBackups()){
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
			File dir = new File(config.getDiretorio());
			File backup = new File(config.getDiretorio() + getNomeArquivo(simpleDateFormat));
			Runtime runTime = Runtime.getRuntime();
				
				if(!dir.isDirectory()){
					new File(config.getDiretorio()).mkdir();
				}
				FileWriter fw = new FileWriter(backup);
				InputStreamReader irs = null;
				BufferedReader br = null;
				try {
					Process p = runTime.exec(config.getCaminhoArquivoMysqlDump()
						+ " --opt "
						+ "--password=fmds1701 "
						+ "--user=root "
						+ "--databases sysge "
						+ "-v "
						+ "--port=3306 "
						+ "--protocol=tcp "
						+ "--force "
						+ "--allow-keywords "
						+ "--compress "
						+ "-R ");
					  //+ "--result-file="+backup+" "
					
					irs = new InputStreamReader(p.getInputStream());
		            br = new BufferedReader(irs);

		            String line;
		            while( (line = br.readLine()) != null ) {
		                fw.write(line + "\n");
		            }
		            
				} catch (IOException  e) {
					e.printStackTrace();
					throw new RuntimeException(e.getMessage());
				}
				
				try {
					setarBackupHistorico(config, new BackupHistorico(),	getNomeArquivo(simpleDateFormat).replace("\\", "")
							, obterTamanhoArquivo(backup.length()));
					
					this.backupHistorico = salvarBackup(this.backupHistorico);
					
					/*compactarArquivo(config.getDiretorio() + getNomeArquivo(simpleDateFormat).replace(".sql", ".zip")
							, backup.toString()
							, backup.length());*/
				//FacesUtil.mensagemInfo("Backup realizado com sucesso as "+ LocalDateTime.now());	
					return super.findById(this.backupHistorico.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					fw.close();
		            irs.close();
		            br.close();
				}
				
				
		}
		return null;
	}
	
	private String obterTamanhoArquivo(Long bytes){
		DecimalFormat format = new DecimalFormat("#0.00");
		Long kb = new Long(1024);
		Long mb = (kb * 1024);
		Long gb = (mb * kb) * kb;
		if(bytes > gb){
			return format.format(bytes / gb) + " Gb";
		}else if(bytes > mb){
			return format.format(bytes / mb) + " Mb";
		}else if(bytes > kb){
			return format.format(bytes / kb) + " Kb";
		}else{
			return format.format(bytes) + " B";
		}
	}
	
	public void compactarArquivo(String arqSaida, String arqEntrada, long bytes) throws IOException{
		int cont;
		byte[] dados = new byte[(int) bytes];
		BufferedInputStream origem = null;
		FileInputStream streamDeEntrada = null;
		FileOutputStream destino = null;
		ZipOutputStream saida = null;
		ZipEntry entry = null;
		
		 try { 
			 destino = new FileOutputStream(new File(arqSaida)); 
			 saida = new ZipOutputStream(new BufferedOutputStream(destino));  
			 File file = new File(arqEntrada);       
			 streamDeEntrada = new FileInputStream(file);  
			 origem = new BufferedInputStream(streamDeEntrada, (int) bytes);    
			 entry = new ZipEntry(file.getName());   
			 saida.putNextEntry(entry);                                     
			 
			 while((cont = origem.read(dados, 0, (int) bytes)) != -1) {               
				 saida.write(dados, 0, cont);             
			 }             
			 
			 origem.close();             
			 saida.close();         
			 
		 } catch(IOException e) {             
			throw new IOException(e.getMessage());
		 }
		
	}
	
	
	private String getNomeArquivo(SimpleDateFormat simpleDateFormat){
		return "\\backup_"+simpleDateFormat.format(new Date()) + ".sql";
	}
	
	public BackupHistorico salvarBackup(BackupHistorico backupHistorico) {
		return super.save(backupHistorico);
	}

	public List<BackupHistorico> listarPorData(Date dataInicial, Date dataFinal) {
		return super.findByDate(dataInicial, dataFinal, "dataBackup");
	}

	public List<ConfiguracaoBackup> listarConfiguracaoBackups() {
		return configuracaoBackupService.findAll();
	}


	/*runTime.exec(config.getCaminhoArquivoMysqlDump()
	+ " -v -v -v "
	+ "--host=localhost "
	+ "--user=root "
	+ "--password=fmds1701 "
	+ "--port=3306 "
	+ "--protocol=tcp "
	+ "--force "
	+ "--allow-keywords "
	+ "--compress  "
	+ "--add-drop-table "
	+ "--default-character-set=utf-8 "
	+ "--hex-blob "
	+ "--result-file="+backup+" "
	+ "--databases protocolo_web -R");*/ 
}

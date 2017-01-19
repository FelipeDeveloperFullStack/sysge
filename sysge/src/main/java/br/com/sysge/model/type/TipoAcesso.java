package br.com.sysge.model.type;

public enum TipoAcesso {
	
	ACESSO_TOTAL("Acesso total"),
	NENHUM_MENU_SELECIONADO("Nenhum menu selecionado"),
	ACESSO_PERSONALIZADO("Acesso personalizado");
	
	private String tipoAcesso;
	
	TipoAcesso(String tipoAcesso){
		this.tipoAcesso = tipoAcesso;
	}
	
	public String getTipoAcesso(){
		return this.tipoAcesso;
	}

}

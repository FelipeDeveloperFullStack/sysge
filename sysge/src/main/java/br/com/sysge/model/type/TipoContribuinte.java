package br.com.sysge.model.type;

public enum TipoContribuinte {
	
	CONTRIBUINTE_ICMS("CONTRIBUINTE ICMS"),
	CONTRIBUINTE_ISENTO_DE_INSCRICAO_NO_CADASTRO_DE_CONTRIBUINTES_DO_ICMS("CONTRIBUINTE ISENTO DE INSCRICAO NO CADASTRO DE CONTRIBUINTES DO ICMS"),
	NAO_CONTRIBUINTE_QUE_PODE_OU_NAO_POSSUIR_INSCRICAO_ESTADUAL_NO_CADASTRO_DE_CONTRIBUINTES_DO_ICMS("NAO CONTRIBUINTE QUE PODE OU NAO POSSUIR INSCRICAO ESTADUAL NO CADASTRO DE CONTRIBUINTES DO ICMS");
	
	private String tipoContribuinte;
	
	TipoContribuinte(String tipoContribuinte){
		this.tipoContribuinte = tipoContribuinte;
	}
	
	public String getTipoContribuinte(){
		return this.tipoContribuinte;
	}

}

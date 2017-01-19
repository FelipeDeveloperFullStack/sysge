package br.com.sysge.model.type;

public enum TipoDesconto {
	
	DESCONTO_REAIS("Desconto em reais"),
	DESCONTO_PORCENTAGEM("Desconto em porcentagem");
	
	private String tipoDesconto;
	
	TipoDesconto(String tipoDesconto){
		this.tipoDesconto = tipoDesconto;
	}
	
	public String getTipoDesconto(){
		return this.tipoDesconto;
	}

}

package br.com.sysge.model.type;

public enum StatusOS {
	
	ABERTO("Aberto"),
	FINALIZADO("Finalizado"),
	CANCELADO("Cancelado"),
	EM_ANDAMENTO("Em andamento");

	private String statusOS;
	
	StatusOS(String statusOS){
		this.statusOS = statusOS;
	}
	
	public String getStatusOS(){
		return this.statusOS;
	}
}

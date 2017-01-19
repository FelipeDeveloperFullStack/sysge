package br.com.sysge.model.type;

public enum Pago {
	
	NAO("Não"),
	SIM("Sim");
	
	private String pago;
	
	Pago(String pago){
		this.pago = pago;
	}
	
	public String getPago(){
		return this.pago;
	}

}

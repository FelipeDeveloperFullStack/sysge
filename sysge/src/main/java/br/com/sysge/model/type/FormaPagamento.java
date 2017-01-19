package br.com.sysge.model.type;

public enum FormaPagamento {
	
	DINHEIRO("Dinheiro"),
	BOLETO("Boleto"),
	CARTAO_CREDITO("Cartão de crédito"),
	CARTAO_DEBITO("Cartão de debito"),
	DEPOSITO("Depósito"),
	CHEQUE("Cheque");
	
	private String formaPagamento;
	
	FormaPagamento(String formaPagamento){
		this.formaPagamento = formaPagamento;
	}
	
	public String getFormaPagamento(){
		return this.formaPagamento;
	}
	

}

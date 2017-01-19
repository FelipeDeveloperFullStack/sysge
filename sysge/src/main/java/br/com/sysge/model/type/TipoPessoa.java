package br.com.sysge.model.type;

public enum TipoPessoa {

	PESSOA_FISICA("Física"),
	PESSOA_JURIDICA("Jurídica");

	private String tipoPessoa;
	
	TipoPessoa(String tipoPessoa){
		this.tipoPessoa = tipoPessoa;
	}
	
	public String getTipoPessoa(){
		return this.tipoPessoa;
	}
}

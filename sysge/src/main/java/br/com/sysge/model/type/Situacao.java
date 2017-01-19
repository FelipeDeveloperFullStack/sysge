package br.com.sysge.model.type;

public enum Situacao {

	ATIVO("Ativo"), INATIVO("Inativo");
	
	private String situacao;
	
	Situacao(String situacao){
		this.situacao = situacao;
	}
	
	public String getSituacao(){
		return this.situacao;
	}
	
	public Situacao[] getSituacoes() {
		return Situacao.values();
	}
}

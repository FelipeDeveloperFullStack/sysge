package br.com.sysge.model.type;

public enum Sexo {
	
	MASC("Masculino"),
	FEMI("Feminino");
	
	private String sexo;
	
	Sexo(String sexo){
		this.sexo = sexo;
	}
	
	public String getSexo(){
		return this.sexo;
	}

}

package br.com.sysge.model.type;

public enum Categoria {
	
	CLIENTE("Cliente"),
	FORNECEDOR("Fornecedor");
	
	private String categoria;
	
	Categoria(String categoria){
		this.categoria = categoria;
	}
	
	public String getCategoria(){
		return this.categoria;
	}

}

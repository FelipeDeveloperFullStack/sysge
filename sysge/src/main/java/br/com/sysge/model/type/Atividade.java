package br.com.sysge.model.type;

public enum Atividade {
	
	OUTROS("Outros"),
	ADVOCACIA("Advocacia"),
	AGENCIA_DE_PUBLICIDADE("Agência de publicidade"),
	AGENCIA_DE_VIAGENS("Agência de viagens"),
	AGENCIA_DE_WEB("Agência de web"),
	AGRONEGOCIO("Agronegócio"),
	ARQUITETURA("Arquitetura"),
	ASSOCIACAO("Associação"),
	CONSTRUCAO_CIVIL("Construção Civil"),
	CONTABILIDADE("Contabilidade"),
	DESIGN("Design"),
	ENGENHARIA_CIVIL("Engenharia Civil"),
	ENGENHARIA_ELETRICA("Engenharia Elétrica"),
	FARMACIA("Farmácia"),
	GRAFICA("Gráfica"),
	IMOBILIARIA("Imobiliária"),
	INDUSTRIA("Indústria"),
	MATERIAIS_PARA_CONSTRUCAO("Materiais para construção"),
	MEDICINA("Medicina"),
	ODONTOLOGIA("Odontologia"),
	ONG("ONG"),
	SINDICATO("Sindicato"),
	TELECOMUNICACOES("Telecomunicações");
	
	public String atividade;
	
	Atividade(String atividade){
		this.atividade = atividade;
	}
	
	public String getAtividade(){
		return this.atividade;
	}
}

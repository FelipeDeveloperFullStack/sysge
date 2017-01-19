package br.com.sysge.infraestrutura.cep;


public class Test {
	
 
	public static void main(String[] args) {
		WebServiceCep web = WebServiceCep.searchCep("94395-999");
		if(web.isCepNotFound()){
			System.out.println("CEP n�o encontrado: "+web.getException());
		}else{
			if(web.wasSuccessful()){
				System.out.println("Bairro: "+web.getBairro());
				System.out.println("Cidade: "+web.getCidade());
				System.out.println("Logradouro: "+web.getLogradouro());
				System.out.println("Tipo Logradouro: "+web.getLogradouroType());
				System.out.println("CEP: "+web.getCep());
				System.out.println("C�digo de resultado: "+web.getResulCode());
				System.out.println("Texto de resultado: "+web.getResultText());
				System.out.println("UF: "+web.getUf());
			}
		}
	}

}

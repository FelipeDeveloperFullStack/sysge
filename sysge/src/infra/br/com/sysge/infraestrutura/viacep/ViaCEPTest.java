package br.com.sysge.infraestrutura.viacep;

public class ViaCEPTest implements ViaCEPEvents {

	private static final long serialVersionUID = 241791903857067365L;

	public static void main(String[] args) {
		new ViaCEPTest().run("01001-000");
	}

	public void run(String cep) {
		ViaCEP viaCEP = new ViaCEP(this);
		try {
			viaCEP.buscar(cep.replaceAll("\\D*", ""));
		} catch (ViaCEPException ex) {
			System.err.println("Ocorreu um erro na classe " + ex.getClasse() + ": " + ex.getMessage());
		}
	}

	@Override
	public void onCEPSuccess(ViaCEP cep) {
		System.out.println();
		System.out.println("CEP " + cep.getCep() + " encontrado!");
		System.out.println("Logradouro: " + cep.getLogradouro());
		System.out.println("Complemento: " + cep.getComplemento());
		System.out.println("Bairro: " + cep.getBairro());
		System.out.println("Localidade: " + cep.getLocalidade());
		System.out.println("UF: " + cep.getUf());
		System.out.println("Gia: " + cep.getGia());
		System.out.println("Ibge: " + cep.getIbge());
		System.out.println();
	}

	@Override
	public void onCEPError(String cep) {
		System.out.println();
		System.out.println("Não foi possível encontrar o CEP " + cep + "!");
		System.out.println();
	}
}

package br.com.sysge.service.sys;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.com.sysge.infraestrutura.cep.WebServiceCep;

public class WebServiceCEPService implements Serializable{
	
	private static final long serialVersionUID = 2004256194889696366L;

	public static Map<Object, Object> procurarCEP(String cep){
		Map<Object, Object> mapCep = new HashMap<Object, Object>();
		try {
			WebServiceCep serviceCep = WebServiceCep.searchCep(cep);
			if(serviceCep.isCepNotFound()){
				throw new RuntimeException("CEP inválido ou não foi possível "
						+ "obter conexão com a base de dados dos Correios! Tente novamente.");
			}else if (serviceCep.wasSuccessful()){
				mapCep.put(1, serviceCep.getLogradouro());
				mapCep.put(2, serviceCep.getCidade());
				mapCep.put(3, serviceCep.getUf());
				mapCep.put(4, serviceCep.getBairro());
				mapCep.put(5, serviceCep.getLogradouroType());
			}else{
				throw new RuntimeException("Servidor dos correios não está respondendo! Tente novamente mais tarde");
			}
			return mapCep;
		} catch (RuntimeException e) {
			throw e;
		}
	}

}

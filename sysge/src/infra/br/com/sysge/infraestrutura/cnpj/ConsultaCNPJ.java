package br.com.sysge.infraestrutura.cnpj;

import java.io.Serializable;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;


public class ConsultaCNPJ implements Serializable{

	private static final long serialVersionUID = 4128169528695210318L;
	
	private static int HTTP_COD_SUCESSO = 200;
	
	public static CnpjResource consultarCnpj(String cnpj){
		try {
			Client client = ClientBuilder.newClient(new ClientConfig().register( LoggingFilter.class ) );
			WebTarget target = client.target("https://www.receitaws.com.br/v1/cnpj/{cnpj}").resolveTemplate("cnpj", cnpj.replaceAll("\\D*", ""));
			String json = target.request().get(String.class);
			verificarSeExisteCnpjInvalido(json);
			verificarSeExisteRejeicaoNaReceitaFederal(json);
			Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.get();
			if(response.getStatus() != HTTP_COD_SUCESSO){
				System.out.println(response.getStatus());
				throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
			}
			return response.readEntity(CnpjResource.class);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private static void verificarSeExisteCnpjInvalido(String json){
		if(json.contains("CNPJ inválido")){
			throw new RuntimeException("CNPJ inválido");
		}
	}
	private static void verificarSeExisteRejeicaoNaReceitaFederal(String json){
		if(json.contains("CNPJ rejeitado pela Receita Federal")){
			throw new RuntimeException("CNPJ rejeitado pela Receita Federal");
		}
	}

}

package br.com.sysge.infraestrutura.cep;

import java.io.Serializable;

public class DadosWebService implements Serializable {
	private static final long serialVersionUID = -9033898272854492315L;

	private int resulCode = -1;
	private String resultText = "busca não realizada.";
	private String cep = null;
	private String bairro = null;
	private String cidade = null;
	private String logradouro = null;
	private String logradouroType = null;
	private String uf = null;
	private Exception exception;

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public boolean hasException() {
		return (exception != null);
	}

	public int getResulCode() {
		return resulCode;
	}

	public void setResulCode(int resulCode) {
		this.resulCode = resulCode;
	}

	public String getResultText() {
		return resultText;
	}

	public void setResultText(String resultText) {
		this.resultText = resultText;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getLogradouroType() {
		return logradouroType;
	}

	public void setLogradouroType(String logradouroType) {
		this.logradouroType = logradouroType;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public boolean wasSuccessful() {
		return (resulCode == 1 && exception == null);
	}

	public boolean isCepNotFound() {
		return (resulCode == 0);
	}

}

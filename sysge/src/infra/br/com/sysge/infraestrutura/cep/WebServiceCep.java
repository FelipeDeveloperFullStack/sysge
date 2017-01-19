package br.com.sysge.infraestrutura.cep;

import java.net.MalformedURLException;
import java.net.URL;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public final class WebServiceCep extends DadosWebService{
	
	private static final long serialVersionUID = 3884184648571276341L;
	private static final String URL_STRING = "http://cep.republicavirtual.com.br/web_cep.php?cep=%s&formato=xml";
	
	private WebServiceCep(String cep) {
		super.setCep(cep); 
	}
	
	public enum Xml {
		CIDADE {
			@Override
			public void setCep(String text, WebServiceCep webServiceCep) {
				webServiceCep.setCidade(text);
			}
		},
		BAIRRO {
			@Override
			public void setCep(String text, WebServiceCep webServiceCep) {
				webServiceCep.setBairro(text);
			}
		},
		TIPO_LOGRADOURO {
			@Override
			public void setCep(String text, WebServiceCep webServiceCep) {
				webServiceCep.setLogradouroType(text);
			}
		},
		LOGRADOURO {
			@Override
			public void setCep(String text, WebServiceCep webServiceCep) {
				webServiceCep.setLogradouro(text);
			}
		},
		RESULTADO {
			@Override
			public void setCep(String text, WebServiceCep webServiceCep) {
				webServiceCep.setResulCode(Integer.parseInt(text));
			}
		},
		RESULTADO_TXT {
			@Override
			public void setCep(String text, WebServiceCep webServiceCep) {
				webServiceCep.setResultText(text);
			}
		},
		UF {
			@Override
			public void setCep(String text, WebServiceCep webServiceCep) {
				webServiceCep.setUf(text);
			}
		};
		
		public abstract void setCep(String text, WebServiceCep webServiceCep);
	}

	private static Document getDocument(String cep) throws DocumentException,
			MalformedURLException {
		URL url = new URL(String.format(URL_STRING, cep));
		SAXReader reader = new SAXReader();
		Document document = reader.read(url);
		return document;
	}

	private static Element getRootElement(String cep) throws DocumentException,
			MalformedURLException {
		return getDocument(cep).getRootElement();
	}

	private static IterableElement getElements(String cep)
			throws DocumentException, MalformedURLException {
		return new IterableElement(getRootElement(cep).elementIterator());
	}

	public static WebServiceCep searchCep(String cep) {
		cep = cep.replaceAll("\\D*", ""); 
		if (cep.length() > 8)
			cep = cep.substring(0, 8);
		WebServiceCep loadCep = new WebServiceCep(cep);
		try {
			XmlEnums enums = new XmlEnums();
			for (Element e : getElements(cep))
				enums.getXml(e.getQualifiedName()).setCep(e.getText(), loadCep);
		} catch (DocumentException ex) {
			if (ex.getNestedException() != null
					&& ex.getNestedException() instanceof java.net.UnknownHostException) {
				loadCep.setResultText("Site não encontrado.");
				loadCep.setResulCode(-14);
			} else {
				loadCep.setResultText("Não foi possível ler o documento XML.");
				loadCep.setResulCode(-15);
			}
			loadCep.setException(ex);
		} catch (MalformedURLException ex) {
			loadCep.setException(ex);
			loadCep.setResultText("Erro na formação da url.");
			loadCep.setResulCode(-16);
		} catch (Exception ex) {
			loadCep.setException(ex);
			loadCep.setResultText("Erro inesperado.");
			loadCep.setResulCode(-17);
		}
		return loadCep;
	}

}

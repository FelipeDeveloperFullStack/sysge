package br.com.sysge.infraestrutura.cep;

import java.util.Iterator;

import org.dom4j.Element;

public final class IterableElement implements Iterable<Element>{
	
	private Iterator<Element> itr;

	@SuppressWarnings("unchecked")
	public IterableElement(Iterator<?> itr) {
		this.itr = (Iterator<Element>) itr;
	}

	public Iterator<Element> iterator() {
		return itr;
	}
}

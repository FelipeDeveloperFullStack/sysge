package br.com.sysge.controller.financ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.sysge.model.financ.MovimentoCaixa;
import br.com.sysge.model.financ.ResumoCaixa;

@Named
@ViewScoped
public class MovimentoCaixaController implements Serializable {

	private static final long serialVersionUID = 514967643852507096L;

	private MovimentoCaixa movimentoCaixa;

	private List<ResumoCaixa> resumoCaixas;
	
	public MovimentoCaixaController(){
		mostrarResumoCaixa();
	}

	private void mostrarResumoCaixa() {
		ResumoCaixa r = new ResumoCaixa();
		resumoCaixas = new ArrayList<ResumoCaixa>();
		resumoCaixas.add(r);
	}

	public List<ResumoCaixa> getResumoCaixas() {
		return resumoCaixas;
	}

	public void setResumoCaixas(List<ResumoCaixa> resumoCaixas) {
		this.resumoCaixas = resumoCaixas;
	}

	public MovimentoCaixa getMovimentoCaixa() {
		return movimentoCaixa;
	}

	public void setMovimentoCaixa(MovimentoCaixa movimentoCaixa) {
		this.movimentoCaixa = movimentoCaixa;
	}

}

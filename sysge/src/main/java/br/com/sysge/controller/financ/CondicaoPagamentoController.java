package br.com.sysge.controller.financ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sysge.model.financ.CondicaoPagamento;
import br.com.sysge.model.type.Situacao;
import br.com.sysge.service.financ.CondicaoPagamentoService;
import br.com.sysge.util.FacesUtil;
import br.com.sysge.util.RequestContextUtil;

@Named
@ViewScoped
public class CondicaoPagamentoController implements Serializable{

	private static final long serialVersionUID = 8114651297348114528L;
	
	private CondicaoPagamento condicaoPagamento;
	
	private static final String A_VISTA = "À Vista";
	
	private String descricao = A_VISTA;
	
	private List<CondicaoPagamento> condicoesPagamento;
	
	@Inject
	private CondicaoPagamentoService condicaoPagamentoService;
	
	@PostConstruct
	public void init() {
		novaListaFormaPagamento();
	}
	
	public void novaListaFormaPagamento(){
		condicoesPagamento = new ArrayList<CondicaoPagamento>();
	}

	public void novo() {
		descricao = A_VISTA;
		this.condicaoPagamento = new CondicaoPagamento();
	}
	
	public void pesquisar(){
		this.condicoesPagamento = new ArrayList<CondicaoPagamento>();
		this.condicoesPagamento = condicaoPagamentoService.pesquisarCondicaoPagamento(condicaoPagamento);
	}

	public void salvar() {
		try {
			condicaoPagamento.setDescricao(descricao);
			verificarSeExisteCondicaoPagamentoCadastradoComMesmaDescricao(condicaoPagamento);
	        condicaoPagamentoService.salvar(condicaoPagamento);
			FacesUtil.mensagemInfo("Condição de pagamento salvo com sucesso!");
			novaListaFormaPagamento();
			fecharDialogs();
		} catch (Exception e) {
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	private void verificarSeExisteCondicaoPagamentoCadastradoComMesmaDescricao(CondicaoPagamento condicaoPagamento){
		condicaoPagamentoService.verificarSeExisteCondicaoPagamentoCadastradoComMesmaDescricao(condicaoPagamento);
	}
	
	public void calcularCondicaoPagamento(){
		descricao = condicaoPagamentoService.calcularCondicaoPagamento(condicaoPagamento, descricao);
	}
	
	public void editarCondicaoPagamento(CondicaoPagamento condicaoPagamento){
		if(condicaoPagamento.getSituacao() == Situacao.ATIVO){
			condicaoPagamento.setSituacao(Situacao.INATIVO);
		}else{
			condicaoPagamento.setSituacao(Situacao.ATIVO);
		}
		condicaoPagamentoService.salvar(condicaoPagamento);
		novaListaFormaPagamento();
	}
	
	public void fecharDialogs(){
		RequestContextUtil.execute("PF('dialogNovoServico').hide();");
	}


	public Situacao[] getSituacoes() {
		return Situacao.values();
	}

	public CondicaoPagamento getCondicaoPagamento() {
		if(this.condicaoPagamento == null){
			this.condicaoPagamento = new CondicaoPagamento();
		}
		return condicaoPagamento;
	}

	public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
		this.condicaoPagamento = condicaoPagamento;
	}

	public List<CondicaoPagamento> getCondicoesPagamento() {
		return condicoesPagamento;
	}

	public void setCondicoesPagamento(List<CondicaoPagamento> condicoesPagamento) {
		this.condicoesPagamento = condicoesPagamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	
}

package br.com.sysge.controller.gestserv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sysge.model.gestserv.Servico;
import br.com.sysge.model.type.Situacao;
import br.com.sysge.service.gestserv.ServicoService;
import br.com.sysge.util.FacesUtil;
import br.com.sysge.util.RequestContextUtil;

@Named
@ViewScoped
public class ServicoController implements Serializable {

	private static final long serialVersionUID = 4027984504929564579L;

	private Servico servico;
	
	@Inject
	private ServicoService servicoService;

	private List<Servico> servicos;

	@PostConstruct
	public void init() {
		novaListaServicos();
	}
	
	public void novaListaServicos(){
		this.servicos = new ArrayList<Servico>();
	}

	public void novo() {
		this.servico = new Servico();
	}

	public void salvar() {
		try {
			verificarSeExisteServicoCadastradoComMesmaDescricao(servico);
			servicoService.salvar(servico);
			FacesUtil.mensagemInfo("Servico salvo com sucesso!");
			fecharDialogs();
			novaListaServicos();
		} catch (RuntimeException e) {
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	public void pesquisar(){
		novaListaServicos();
		servicos = servicoService.pesquisarServico(servico);
	}
	
	public void verificarSeExisteServicoCadastradoComMesmaDescricao(Servico servico){
		servicoService.verificarSeExisteServicoCadastradoComMesmaDescricao(servico);
	}
	
	public void fecharDialogs(){
		RequestContextUtil.execute("PF('dialogNovoServico').hide()");
		RequestContextUtil.execute("PF('dialogEditarServico').hide()");
	}

	public void setarServico(Servico servico){
		this.servico = servico;
	}
	
	public Servico getServico() {
		if (this.servico == null) {
			novo();
		}
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public ServicoService getServicoService() {
		return servicoService;
	}

	public void setServicoService(ServicoService servicoService) {
		this.servicoService = servicoService;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public Situacao[] getSituacoes() {
		return Situacao.values();
	}

}

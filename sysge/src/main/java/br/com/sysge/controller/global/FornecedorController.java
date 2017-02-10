package br.com.sysge.controller.global;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sysge.model.global.Fornecedor;
import br.com.sysge.model.type.Atividade;
import br.com.sysge.model.type.Categoria;
import br.com.sysge.model.type.Situacao;
import br.com.sysge.model.type.TipoContribuinte;
import br.com.sysge.model.type.TipoPessoa;
import br.com.sysge.model.type.UnidadeFederativa;
import br.com.sysge.service.global.FornecedorService;
import br.com.sysge.service.sys.WebServiceCEPService;
import br.com.sysge.util.FacesUtil;
import br.com.sysge.util.RequestContextUtil;

@Named
@ViewScoped
public class FornecedorController implements Serializable {

	private static final long serialVersionUID = -2506223673479436354L;

	private Fornecedor fornecedor;
	
	private List<Fornecedor> fornecedores;
	
	@Inject
	private  FornecedorService fornecedorService;

	@PostConstruct
	public void init() {
		novoFornecedor();
	}

	public void novoFornecedor() {
		this.fornecedor = new Fornecedor();
	}
	
	public void salvar() {
		try {
			fornecedor = fornecedorService.salvar(fornecedor);
			//listarFornecedores(fornecedor);
			FacesUtil.mensagemInfo("Fornecedor salvo com sucesso!");
			fecharDialogs();
		} catch (Exception e) {
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	public void cancelar(){
		listarFornecedores(fornecedor);
	}
	
	private void listarFornecedores(Fornecedor fornecedor){
		try {
			fornecedores = fornecedorService.findBySituationAndCategoriaAndTipoPessoa
					(fornecedor.getSituacao(), null, null);
		} catch (Exception e) {
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	public void consultarCnpj(){
		try {
			fornecedor = fornecedorService.consultarCnpj(fornecedor);
			FacesUtil.mensagemInfo("Dados do fornecedor encontrados com sucesso!");
			fecharDialodDeProcurarCnpj();
		} catch (Exception e) {
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	public void consultarCep(){
		try {
			Map<Object, Object> mapCep = new HashMap<Object, Object>();
			mapCep.putAll(WebServiceCEPService.procurarCEP(fornecedor.getCep()));
			this.fornecedor.setLogradouro(mapCep.get(5).toString() + " " + mapCep.get(1).toString());
			this.fornecedor.setCidade(mapCep.get(2).toString());
			this.fornecedor.setUnidadeFederativa(UnidadeFederativa.valueOf(mapCep.get(3).toString()));
			this.fornecedor.setBairro(mapCep.get(4).toString());
		} catch (Exception e) {
			FacesUtil.mensagemErro(e.getMessage());
		}
	}
	
	public void pesquisar(){
		fornecedores = fornecedorService.procurarFornecedor(fornecedor);
	}
	
	public void setarFornecedor(Fornecedor fornecedor){
		this.fornecedor = fornecedor;
	}
	
	private void fecharDialogs(){
		RequestContextUtil.execute("PF('dialogNovoCliente').hide();");
		RequestContextUtil.execute("PF('dialogEditarCliente').hide();");
	}
	
	private void fecharDialodDeProcurarCnpj(){
		RequestContextUtil.execute("PF('dialog_procurar_cnpj').hide();");
	}

	public Situacao[] getSituacoes() {
		return Situacao.values();
	}

	public TipoPessoa[] getTipoPessoa() {
		return TipoPessoa.values();
	}

	public Categoria[] getCategorias() {
		return Categoria.values();
	}

	public Atividade[] getAtividades() {
		return Atividade.values();
	}

	public TipoContribuinte[] getTipoContribuintes() {
		return TipoContribuinte.values();
	}

	public UnidadeFederativa[] getUnidadesFederativas() {
		return UnidadeFederativa.values();
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}


	
}

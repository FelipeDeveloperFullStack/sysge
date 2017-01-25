package br.com.sysge.service.gestserv;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.sysge.infraestrutura.dao.GenericDaoImpl;
import br.com.sysge.model.gestserv.OrdemServico;
import br.com.sysge.model.gestserv.ProdutoOrdemServico;
import br.com.sysge.model.gestserv.ServicoOrdemServico;
import br.com.sysge.service.estoque.ProdutoService;

public class OrdemServicoService extends GenericDaoImpl<OrdemServico, Long> {

	private static final long serialVersionUID = 6697038638256448464L;

	@Inject
	private ServicoOrdemServicoService servicoOrdemServicoService;
	
	@Inject
	private ProdutoOrdemServicoService produtoOrdemServicoService;
	
	@Inject
	private ServicoService servicoService;

	@Inject
	private ProdutoService produtoService;
	
	public OrdemServico salvar(OrdemServico ordemServico) {
		try {
			if (ordemServico.getFuncionario().getNome().isEmpty()) {
				throw new RuntimeException("O nome do funcionário é obrigatório!");
			}
			if (ordemServico.getCliente().getNomeTemporario().isEmpty()) {
				throw new RuntimeException("O nome do cliente é obrigatório!");
			}
			return super.save(ordemServico);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void consistirServico(List<ServicoOrdemServico> listaServicos, OrdemServico ordemServico) {
		if (!listaServicos.isEmpty()) {
			for (ServicoOrdemServico sos : listaServicos) {
				sos.setOrdemServico(ordemServico);
				sos.setServico(servicoService.salvar(sos.getServico()));
				servicoOrdemServicoService.save(sos);
			}
		}
	
	}
	
	public void consistirProduto(List<ProdutoOrdemServico> listaProdutos, OrdemServico ordemServico){
		if (!listaProdutos.isEmpty()) {
			for (ProdutoOrdemServico pos : listaProdutos) {
				pos.setOrdemServico(ordemServico);
				pos.setProduto(produtoService.salvar(pos.getProduto()));
				produtoOrdemServicoService.save(pos);
			}
		}
	}
	
	public List<ServicoOrdemServico> procurarServicosOS(long idOS){
		return servicoOrdemServicoService.findByListProperty(idOS, "ordemServico.id");
	}
	
	public List<ProdutoOrdemServico> procurarProdutosOS(long idOS){
		return produtoOrdemServicoService.findByListProperty(idOS, "ordemServico.id");
	}
	
	public List<OrdemServico> pesquisarPorNumeroEStatusOS(OrdemServico ordemServico){
		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();
		if(ordemServico.getId() == null){
			listaOS = super.findByStatusOs(ordemServico.getStatusOS());
		}else{
			listaOS = super.findByNumeroStatusOS(ordemServico.getId(), ordemServico.getStatusOS());
		}
		if(listaOS.isEmpty()){
			if(ordemServico.getId() == null){
			throw new RuntimeException("Nenhuma ordem de servico de status '"+ordemServico.getStatusOS().getStatusOS()+"' encontrada, "
						+ "verifique e tente novamente!");
			}else{
				throw new RuntimeException("Nenhuma ordem de servico de nº "
						+ ""+ordemServico.getId()+ " "
						+ "e status '"+ordemServico.getStatusOS().getStatusOS()+"' encontrada, "
						+ "verifique e tente novamente!");
			}
		}
		return listaOS;
	}
	
}

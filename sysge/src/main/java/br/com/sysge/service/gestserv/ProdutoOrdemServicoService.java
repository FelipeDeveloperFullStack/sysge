package br.com.sysge.service.gestserv;

import java.util.List;

import br.com.sysge.infraestrutura.dao.GenericDaoImpl;
import br.com.sysge.model.estoque.Produto;
import br.com.sysge.model.gestserv.ProdutoOrdemServico;

public class ProdutoOrdemServicoService extends GenericDaoImpl<ProdutoOrdemServico, Long>{

	private static final long serialVersionUID = 590295555955384164L;
	
	public boolean verificarSeExisteProdutoNaTabela(List<ProdutoOrdemServico> listaProdutos, Produto produto) {
		for (ProdutoOrdemServico s : listaProdutos) {
			if (s.getProduto().getDescricaoProduto().trim().equals(produto.getDescricaoProduto().trim())) {
				return true;
			}
		}
		return false;
	}

}

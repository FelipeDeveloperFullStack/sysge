package br.com.sysge.service.gestserv;

import java.util.List;

import br.com.sysge.infraestrutura.dao.GenericDaoImpl;
import br.com.sysge.model.gestserv.Servico;
import br.com.sysge.model.gestserv.ServicoOrdemServico;

public class ServicoOrdemServicoService extends GenericDaoImpl<ServicoOrdemServico, Long> {

	private static final long serialVersionUID = -9133422263268014887L;

	public boolean verificarSeExisteServicoNaTabela(List<ServicoOrdemServico> listaServicos, Servico servico) {
		for (ServicoOrdemServico s : listaServicos) {
			if (s.getServico().getNome().trim().equals(servico.getNome().trim())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean verificarSeExisteIdNull(List<ServicoOrdemServico> listaServicos){
		for(int i = 0; i < listaServicos.size(); i++){
			if(listaServicos.get(i).getId() == null){
				return true;
			}
		}
		return false;
	}
	
	public List<ServicoOrdemServico> removerServicoOSPeloID(List<ServicoOrdemServico> listaServicos, ServicoOrdemServico servicoOrdemServico){
		for(int i = 0; i < listaServicos.size(); i++){
			if(listaServicos.get(i).getServico().getNome().equals(servicoOrdemServico.getServico().getNome())){
				super.remove(servicoOrdemServico.getId());
			}
		}
		return listaServicos;
	}

}

package br.com.sysge.service.gestserv;

import java.util.ArrayList;
import java.util.List;

import br.com.sysge.infraestrutura.dao.GenericDaoImpl;
import br.com.sysge.model.gestserv.Servico;
import br.com.sysge.model.type.Situacao;

public class ServicoService extends GenericDaoImpl<Servico, Long>{

	private static final long serialVersionUID = 642432438861350933L;
	
	public Servico salvar(Servico servico){
		try {
			if(servico.getNome().trim().equals("")){
				throw new RuntimeException("O nome é obrigatório!");
			}
			if(servico.getValor() == null){
				throw new RuntimeException("O valor é obrigatório!");
			}
			return super.save(consistirServico(servico));
		} catch (RuntimeException e) {
			e.getStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private Servico consistirServico(Servico servico){
		if(servico.getId() == null){
			servico.setSituacao(Situacao.ATIVO);
		}
		return servico;
	}
	
	public List<Servico> pesquisarServico(Servico servico){
		if(servico.getNome() != null){
			if(servico.getNome().trim().equals("*")){
				return super.findBySituation(servico.getSituacao());
			}else{
				return super.findByParametersForSituation(servico.getNome(), 
						servico.getSituacao(), "nome", "LIKE", "%", "%");
			}
		}
		return new ArrayList<Servico>();
	}

}

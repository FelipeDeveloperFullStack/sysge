package br.com.sysge.service.conf;


import java.util.ArrayList;
import java.util.List;

import br.com.sysge.infraestrutura.dao.GenericDaoImpl;
import br.com.sysge.model.conf.PerfilAcesso;
import br.com.sysge.model.type.Situacao;

public class PerfilAcessoService extends GenericDaoImpl<PerfilAcesso, Long>{

	private static final long serialVersionUID = -2246221808094794560L;
	
	public List<PerfilAcesso> pesquisarPerfilAcesso(PerfilAcesso perfilAcesso){
		if(perfilAcesso.getPerfilAcesso() != null){
			if(perfilAcesso.getPerfilAcesso().equals("*")){
				return super.findBySituation(perfilAcesso.getSituacao());
			}else{
				return super.findByParametersForSituation(perfilAcesso.getPerfilAcesso(), 
						perfilAcesso.getSituacao(), "perfilAcesso", "LIKE", "%", "%");
			}
		}
		return new ArrayList<PerfilAcesso>();
	}
	
	public PerfilAcesso salvar(PerfilAcesso perfil){
		try {
			if(perfil.getPerfilAcesso().trim().equals("")){
				throw new RuntimeException("A descrição do perfil de acesso é obrigatório!");
			}
			return super.save(consistirPerfilAcesso(perfil));
		} catch (RuntimeException e) {
			e.getStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private PerfilAcesso consistirPerfilAcesso(PerfilAcesso perfil){
		if(perfil.getId() == null){
			perfil.setSituacao(Situacao.ATIVO);
		}
		return perfil;
	}

}

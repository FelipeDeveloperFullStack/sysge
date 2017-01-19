package br.com.sysge.service.rh;

import java.util.ArrayList;
import java.util.List;

import br.com.sysge.infraestrutura.dao.GenericDaoImpl;
import br.com.sysge.model.rh.Funcionario;
import br.com.sysge.model.type.Situacao;

public class FuncionarioService extends GenericDaoImpl<Funcionario, Long>{

	private static final long serialVersionUID = -7529766205098384896L;

	public Funcionario salvar(Funcionario funcionario){
		try {
			if(funcionario.getNome().trim().isEmpty()){
				throw new RuntimeException("O nome do funcionário é obrigatório!");
			}
			return super.save(consistirFuncionario(funcionario));
		} catch (RuntimeException e) {
			e.getStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private Funcionario consistirFuncionario(Funcionario funcionario){
		if(funcionario.getId() == null){
			funcionario.setSituacao(Situacao.ATIVO);
		}
		return funcionario;
	}
	
	public List<Funcionario> pesquisarFuncionario(Funcionario funcionario){
		if(funcionario.getNome() != null){
			if(funcionario.getNome().equals("*")){
				return super.findBySituation(funcionario.getSituacao());
			}else{
				return super.findByParametersForSituation(funcionario.getNome(), 
						funcionario.getSituacao(), "nome", "LIKE", "%", "%");
			}
		}
		return new ArrayList<Funcionario>();
	}
	
	public boolean verificarSeFuncionarioEDiferenteDeNull(Funcionario funcionario){
		if(funcionario != null){
			return true;
		}else{
			return false;
		}
			
	}
	
	public boolean verificarIdNull(Funcionario funcionario){
		if(funcionario.getId() == null){
			return true;
		}else{
			return false;
		}
	}
}

package br.com.sysge.service.rh;

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
			funcionario.setNome(funcionario.getNome().toUpperCase());
			funcionario.setSituacao(Situacao.ATIVO);
		}else{
			funcionario.setNome(funcionario.getNome().toUpperCase());
		}
		return funcionario;
	}
	
	public List<Funcionario> pesquisarFuncionario(Funcionario funcionario){
			if(funcionario.getNome().trim().isEmpty()){
				return super.findBySituation(funcionario.getSituacao());
			}else{
				return super.findByParametersForSituation(funcionario.getNome().toUpperCase(), 
						funcionario.getSituacao(), "nome", "LIKE", "%", "%");
			}
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
	
	public void verificarSeExisteFuncionarioCadastradoComMesmaDescricao(Funcionario funcionario){
		List<Funcionario> funcionarios = super.findAll();
		for(Funcionario f : funcionarios){
			verificarNomeIgual(f, funcionario);
		}
	}
	
	private void verificarNomeIgual(Funcionario f, Funcionario funcionario){
		if(funcionario.getFuncao().trim().equalsIgnoreCase(f.getFuncao())){
			if(funcionario.getId() == null){
				mostrarMensagemParaUsuario(f);
			}else{
				if(f.getId() != funcionario.getId()){
					mostrarMensagemParaUsuario(f);
				}
			}
		}
	}
	
	private void mostrarMensagemParaUsuario(Funcionario f){
		throw new RuntimeException("Existe o funcionário "+f.getNome()+" "
				+ "de código "+f.getId()+" já está cadastrado, "
				+ "por favor escolha outro funcionário!");
	}
}

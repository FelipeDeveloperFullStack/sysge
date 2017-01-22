package br.com.sysge.service.gestserv;

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
		if(servico.getNome().trim().isEmpty()){
			return super.findBySituation(servico.getSituacao());
		}else{
			return super.findByParametersForSituation(servico.getNome(), 
					servico.getSituacao(), "nome", "LIKE", "%", "%");
		}
	}
	
	public void verificarSeExisteServicoCadastradoComMesmaDescricao(Servico servico){
		List<Servico> servicos = super.findAll();
		for(Servico s : servicos){
			verificarDescricaoIgual(s, servico);
		}
	}
	
	private void verificarDescricaoIgual(Servico s, Servico servico){
		if(servico.getNome().trim().equalsIgnoreCase(s.getNome())){
			if(servico.getId() == null){
				mostrarMensagemParaUsuario(s);
			}else{
				if(s.getId() != servico.getId()){
					mostrarMensagemParaUsuario(s);
				}
			}
		}
	}
	
	private void mostrarMensagemParaUsuario(Servico s){
		throw new RuntimeException("Existe o serviço "+s.getNome()+" "
				+ "de código "+s.getId()+" já está cadastrado, "
				+ "por favor escolha outro nome de serviço!");
	}

}

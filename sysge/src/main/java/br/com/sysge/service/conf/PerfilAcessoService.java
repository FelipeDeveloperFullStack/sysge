package br.com.sysge.service.conf;


import java.util.List;

import javax.inject.Inject;

import br.com.sysge.infraestrutura.dao.GenericDaoImpl;
import br.com.sysge.model.conf.PerfilAcesso;
import br.com.sysge.model.conf.Usuario;
import br.com.sysge.model.type.Situacao;

public class PerfilAcessoService extends GenericDaoImpl<PerfilAcesso, Long>{

	private static final long serialVersionUID = -2246221808094794560L;
	
	@Inject
	private UsuarioService usuarioService;
	
	public List<PerfilAcesso> pesquisarPerfilAcesso(PerfilAcesso perfilAcesso){
		try {
			if(perfilAcesso.getPerfilAcesso().trim().isEmpty()){
				return super.findBySituation(perfilAcesso.getSituacao());
			}else{
				return super.findByParametersForSituation(perfilAcesso.getPerfilAcesso().toUpperCase(), 
						perfilAcesso.getSituacao(), "perfilAcesso", "LIKE", "%", "%"); 
			}
		} catch (RuntimeException e) {
			e.getStackTrace();
			throw new RuntimeException(e.getMessage());
		}
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
			perfil.getPerfilAcesso().toUpperCase();
			perfil.setSituacao(Situacao.ATIVO);
		}
		return perfil;
	}
	
	public Usuario procurarUsuarioPorPerfil(long idPerfilAcesso){
		return usuarioService.procurarUsuarioPorPerfil(idPerfilAcesso);
	}
	
	public boolean verificarSeExistePerfilAcesso(long idPerfilAcesso){
		return usuarioService.verificarSeExistePerfilAcesso(idPerfilAcesso);
	}
	
	public void verificarSeExistePerfilCadastradoComMesmaDescricao(PerfilAcesso perfilAcesso){
		List<PerfilAcesso> perfis = super.findAll();
		for(PerfilAcesso p : perfis){
			verificarDescricaoIgual(p, perfilAcesso);
		}
	}
	
	private void verificarDescricaoIgual(PerfilAcesso p, PerfilAcesso perfilAcesso){
		if(perfilAcesso.getPerfilAcesso().trim().equalsIgnoreCase(p.getPerfilAcesso())){
			if(perfilAcesso.getId() == null){
				mostrarMensagemParaUsuario(p);
			}else{
				if(p.getId() != perfilAcesso.getId()){
					mostrarMensagemParaUsuario(p);
				}
			}
		}
	}
	
	private void mostrarMensagemParaUsuario(PerfilAcesso p){
		throw new RuntimeException("Existe o perfil "+p.getPerfilAcesso()+" "
				+ "de código "+p.getId()+" já está cadastrado, "
				+ "por favor escolha outra descrição!");
	}

}

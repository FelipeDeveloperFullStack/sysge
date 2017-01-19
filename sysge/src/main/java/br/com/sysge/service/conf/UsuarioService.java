package br.com.sysge.service.conf;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.sysge.infraestrutura.dao.GenericDaoImpl;
import br.com.sysge.model.conf.Usuario;
import br.com.sysge.model.type.Situacao;
import br.com.sysge.service.rh.FuncionarioService;

public class UsuarioService extends GenericDaoImpl<Usuario, Long>{

	private static final long serialVersionUID = -2651419634334617651L;
	
	@Inject
	private FuncionarioService funcionarioService;
	
	public List<Usuario> pesquisarUsuario(Usuario usuario){
		if(usuario.getPerfilAcesso() != null){
			if(usuario.getPerfilAcesso().equals("*")){
				return super.findBySituation(usuario.getSituacao());
			}else{
				return super.findByParametersForSituation(usuario.getPerfilAcesso(), 
						usuario.getSituacao(), "usuario", "LIKE", "%", "%");
			}
		}
		return new ArrayList<Usuario>();
	}
	
	public Usuario salvar(Usuario usuario){
		try {
			return super.save(consistirUsuario(usuario));
		} catch (RuntimeException e) {
			e.getStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private Usuario consistirUsuario(Usuario usuario){
		if(usuario.getId() == null){
			usuario.setSituacao(Situacao.ATIVO);
		}
		return usuario;
	}
	
	public boolean isExisteUsuario(Usuario usuario){
		
		if(funcionarioService.verificarIdNull(usuario.getFuncionario())){
			throw new RuntimeException("O none do funcionário é obrigatório!");
		}else{
			if(usuario.getId() == null){
				verificarUsuarioESenha(usuario);
				if(isExisteNovoUsuario(usuario)){
					return true;
				}
			}else{
				verificarUsuarioESenha(usuario);
				if(isExiste(usuario)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void verificarUsuarioESenha(Usuario usuario){
		if(usuario.getNomeUsuario().trim().isEmpty()){
			throw new RuntimeException("O nome de usuário é obrigatório!");
		}
		if(usuario.getSenhaUsuario().trim().isEmpty()){
			throw new RuntimeException("A senha de usuário é obrigatório!");
		}
	}
	
	private boolean isExiste(Usuario usuario){
		for(Usuario u : super.findAll()){
			if(usuario.getId() != u.getId()){
				if(usuario.getNomeUsuario().equals(u.getNomeUsuario())){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isExisteNovoUsuario(Usuario usuario){
		for(Usuario u : super.findAll()){
			if(usuario.getNomeUsuario().equals(u.getNomeUsuario())){
				return true;
			}
		}
		return false;
	}
	
}

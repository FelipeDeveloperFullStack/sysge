package br.com.sysge.model.conf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


import br.com.sysge.infraestrutura.dao.GenericDomain;
import br.com.sysge.model.type.Situacao;
import br.com.sysge.model.type.TipoAcesso;

@Entity
@Table(name = "tbl_perfil_acesso")
public class PerfilAcesso extends GenericDomain{

	private static final long serialVersionUID = -6079597816829140471L;
	
	@Column(name = "perfil_acesso")
	private String perfilAcesso;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "perfil_tipo_acesso")
	private TipoAcesso tipoAcesso = TipoAcesso.ACESSO_TOTAL;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "perfil_situacao")
	private Situacao situacao;
	
	@Column(name = "perfil_admin")
	private String admin;

	public String getPerfilAcesso() {
		return perfilAcesso;
	}

	public void setPerfilAcesso(String perfilAcesso) {
		this.perfilAcesso = perfilAcesso;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public TipoAcesso getTipoAcesso() {
		return tipoAcesso;
	}

	public void setTipoAcesso(TipoAcesso tipoAcesso) {
		this.tipoAcesso = tipoAcesso;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	
	
}

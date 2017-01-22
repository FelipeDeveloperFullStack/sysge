package br.com.sysge.model.conf;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import br.com.sysge.infraestrutura.dao.GenericDomain;
import br.com.sysge.model.rh.Funcionario;
import br.com.sysge.model.type.Situacao;

@Entity
@Table(name = "tbl_usuario")
public class Usuario extends GenericDomain{

	private static final long serialVersionUID = 1344655132014776204L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usu_funcionario")
	private Funcionario funcionario;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usu_perfil_acesso")
	private PerfilAcesso perfilAcesso;
	
	@Column(name = "usu_email")
	private String email;
	
	@Column(name = "usu_nome_usuario")
	private String nomeUsuario;
	
	@Column(name = "usu_senha_usuario")
	private String senhaUsuario;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "usu_situacao")
	private Situacao situacao;
	
	@Column(name = "usu_ultimo_acesso")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultimoAcesso;
	
	@Column(name = "usu_data_inicial")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicial;
	
	@Column(name = "usu_data_final")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFinal;

	public Funcionario getFuncionario() {
		if(funcionario == null){
			funcionario = new Funcionario();
		}
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public PerfilAcesso getPerfilAcesso() {
		if(perfilAcesso == null){
			perfilAcesso = new PerfilAcesso();
		}
		return perfilAcesso;
	}

	public void setPerfilAcesso(PerfilAcesso perfilAcesso) {
		this.perfilAcesso = perfilAcesso;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	
	
}

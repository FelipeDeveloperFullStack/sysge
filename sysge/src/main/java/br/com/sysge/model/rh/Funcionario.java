package br.com.sysge.model.rh;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import br.com.sysge.infraestrutura.dao.GenericDomain;
import br.com.sysge.model.type.Sexo;
import br.com.sysge.model.type.Situacao;
import br.com.sysge.model.type.UnidadeFederativa;

@Entity
@Table(name = "tbl_funcionario")
public class Funcionario extends GenericDomain{

	private static final long serialVersionUID = -3257275101789304500L;

	@Column(name = "fun_nome")
	private String nome;
	
	@Column(name = "fun_data_nascimento")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@Column(name = "fun_endereco")
	private String endereco;
	
	@Column(name = "fun_cidade")
	private String cidade;
	
	private String codigoIBGE;
	
	@Column(name = "fun_bairro")
	private String bairro;
	
	@Column(name = "fun_uf")
	@Enumerated(EnumType.STRING)
	private UnidadeFederativa uf;
	
	@Column(name = "fun_cep")
	private String cep;
	
	@Column(name = "fun_telefone_fixo")
	private String telefoneFixo;
	
	@Column(name = "fun_telefone_celular")
	private String telefoneCelular;
	
	@Column(name = "fun_email")
	private String email;
	
	@Column(name = "fun_funcao")
	private String funcao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "fun_sexo")
	private Sexo sexo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "fun_situacao")
	private Situacao situacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public UnidadeFederativa getUf() {
		return uf;
	}

	public void setUf(UnidadeFederativa uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(String telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public String getCodigoIBGE() {
		return codigoIBGE;
	}

	public void setCodigoIBGE(String codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}
	
	
}

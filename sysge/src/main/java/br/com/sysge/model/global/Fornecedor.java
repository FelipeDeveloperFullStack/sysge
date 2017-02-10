package br.com.sysge.model.global;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import br.com.sysge.infraestrutura.dao.GenericDomain;
import br.com.sysge.model.type.Atividade;
import br.com.sysge.model.type.Situacao;
import br.com.sysge.model.type.TipoContribuinte;
import br.com.sysge.model.type.UnidadeFederativa;

@Entity
@Table(name = "tbl_fornecedor")
public class Fornecedor extends GenericDomain{

	private static final long serialVersionUID = -4365055249547093538L;

	private String nomeFantasia;
	
	private String tipoEmpresa;
	
	private String telefone;
	
	private String celular;
	
	private String email;
	
	private String site;
	
	@Enumerated(EnumType.STRING)
	private Atividade atividade;
	
	private String cnpj;
	
	private String razaoSocial;
	
	@Enumerated(EnumType.STRING)
	private TipoContribuinte tipoContribuinte;
	
	private String inscEstadual;
	
	private String inscMunicipal;
	
	private String telefoneAlternativo;
	
	private String observacao;
	
	private String horarioFuncionamento;
	
	private String cep;
	
	private String logradouro;
	
	private String numero = "S/N";
	
	private String complemento;
	
	private String bairro;
	
	private String cidade;
	
	private String codigoIBGE;
	
	@Enumerated(EnumType.STRING)
	private UnidadeFederativa unidadeFederativa;
	
	private String facebook;
	
	private String twitter;
	
	private String linkdin;
	
	private String youtube;
	
	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public TipoContribuinte getTipoContribuinte() {
		return tipoContribuinte;
	}

	public void setTipoContribuinte(TipoContribuinte tipoContribuinte) {
		this.tipoContribuinte = tipoContribuinte;
	}

	public String getInscEstadual() {
		return inscEstadual;
	}

	public void setInscEstadual(String inscEstadual) {
		this.inscEstadual = inscEstadual;
	}

	public String getInscMunicipal() {
		return inscMunicipal;
	}

	public void setInscMunicipal(String inscMunicipal) {
		this.inscMunicipal = inscMunicipal;
	}

	public String getTelefoneAlternativo() {
		return telefoneAlternativo;
	}

	public void setTelefoneAlternativo(String telefoneAlternativo) {
		this.telefoneAlternativo = telefoneAlternativo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getHorarioFuncionamento() {
		return horarioFuncionamento;
	}

	public void setHorarioFuncionamento(String horarioFuncionamento) {
		this.horarioFuncionamento = horarioFuncionamento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public UnidadeFederativa getUnidadeFederativa() {
		return unidadeFederativa;
	}

	public void setUnidadeFederativa(UnidadeFederativa unidadeFederativa) {
		this.unidadeFederativa = unidadeFederativa;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getLinkdin() {
		return linkdin;
	}

	public void setLinkdin(String linkdin) {
		this.linkdin = linkdin;
	}

	public String getYoutube() {
		return youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public String getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(String tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public String getCodigoIBGE() {
		return codigoIBGE;
	}

	public void setCodigoIBGE(String codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}
}

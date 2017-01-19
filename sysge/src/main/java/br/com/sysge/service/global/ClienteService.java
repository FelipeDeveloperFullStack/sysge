package br.com.sysge.service.global;

import java.util.List;

import br.com.sysge.infraestrutura.cnpj.CnpjResource;
import br.com.sysge.infraestrutura.cnpj.ConsultaCNPJ;
import br.com.sysge.infraestrutura.dao.GenericDaoImpl;
import br.com.sysge.model.global.Cliente;
import br.com.sysge.model.type.Situacao;
import br.com.sysge.model.type.TipoPessoa;
import br.com.sysge.model.type.UnidadeFederativa;

public class ClienteService extends GenericDaoImpl<Cliente, Long> {

	private static final long serialVersionUID = -3438513129762783683L;

	public Cliente salvar(Cliente cliente) {
		try {
			if (cliente.getTipoPessoa() == TipoPessoa.PESSOA_FISICA) {
				if (cliente.getNomeDaPessoaFisica().trim().equals("")) {
					throw new RuntimeException("O nome da pessoa física é obrigatório!");
				}
			}
			if (cliente.getTipoPessoa() == TipoPessoa.PESSOA_JURIDICA) {
				if (cliente.getNomeFantasia().trim().equals("")) {
					throw new RuntimeException("O nome da pessoa jurídica é obrigatório!");
				}
			}
			return super.save(consistirCliente(setarNomes(cliente)));
		} catch (RuntimeException e) {
			e.getStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private Cliente setarNomes(Cliente cliente){
		if(cliente.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
			cliente.setNomeFantasia("");
			cliente.setNomeTemporario("");
			return cliente;
		}else{
			cliente.setNomeDaPessoaFisica("");
			cliente.setNomeTemporario("");
			return cliente;
		}
	}
	
	public Cliente verificarTipoPessoa(Cliente cliente){
		if(cliente.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
			cliente.setNomeTemporario(cliente.getNomeDaPessoaFisica());
			return cliente;
		}else{
			cliente.setNomeTemporario(cliente.getNomeFantasia());
			return cliente;
		}
	}

	public List<Cliente> procurarCliente(Cliente cliente) {
		if (cliente.getNomeTemporario().trim().isEmpty()) {
			return super.findBySituationAndCategoriaAndTipoPessoa(cliente.getSituacao(), cliente.getCategoria(), cliente.getTipoPessoa());
		} else {
			if(cliente.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
				return super.findByParametersForSituation(cliente.getNomeDaPessoaFisica(), cliente.getTipoPessoa(),
						cliente.getCategoria(), cliente.getSituacao(), "nomeDaPessoaFisica", "LIKE", "%", "%");
			}else{
				return super.findByParametersForSituation(cliente.getNomeFantasia(), cliente.getTipoPessoa(),
						cliente.getCategoria(), cliente.getSituacao(), "nomeFantasia", "LIKE", "%", "%");
			}
		}
	}

	public Cliente consultarCnpj(Cliente cliente) {
		try {
			if (cliente.getCnpj().replaceAll("\\D*", "").trim().isEmpty()) {
				throw new RuntimeException("O Cnpj é obrigatório!");
			}
			CnpjResource cnpjResource = ConsultaCNPJ.consultarCnpj(cliente.getCnpj());
			cliente.setBairro(cnpjResource.getBairro());
			cliente.setCep(cnpjResource.getCep());
			cliente.setCnpj(cnpjResource.getCnpj());
			cliente.setComplemento(cnpjResource.getComplemento());
			cliente.setEmail(cnpjResource.getEmail());
			cliente.setNomeFantasia(cnpjResource.getFantasia());
			cliente.setLogradouro(cnpjResource.getLogradouro());
			cliente.setCidade(cnpjResource.getMunicipio());
			cliente.setRazaoSocial(cnpjResource.getNome());
			cliente.setNumero(cnpjResource.getNumero());
			cliente.setTelefone(cnpjResource.getTelefone());
			cliente.setUnidadeFederativa(UnidadeFederativa.valueOf(cnpjResource.getUf()));
			cliente.setTipoEmpresa(cnpjResource.getTipo());
			// cnpjResource.getNatureza_juridica();
			// cnpjResource.getAbertura();
			// cnpjResource.getData_situacao();
			return cliente;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private Cliente consistirCliente(Cliente cliente) {
		if (cliente.getId() == null) {
			cliente.setSituacao(Situacao.ATIVO);
		}
		return cliente;
	}

}

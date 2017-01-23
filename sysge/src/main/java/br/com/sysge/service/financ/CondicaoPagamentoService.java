package br.com.sysge.service.financ;

import java.util.ArrayList;
import java.util.List;

import br.com.sysge.infraestrutura.dao.GenericDaoImpl;
import br.com.sysge.model.financ.CondicaoPagamento;
import br.com.sysge.model.type.Situacao;

public class CondicaoPagamentoService extends GenericDaoImpl<CondicaoPagamento, Long> {

	private static final long serialVersionUID = -8777394922163626078L;
	
	private static final String A_VISTA = "À Vista";

	public CondicaoPagamento salvar(CondicaoPagamento condicaoPagamento) {
		try {
			if (condicaoPagamento.getDescricao().trim().equals("")) {
				throw new RuntimeException("A descrição é obrigatório!");
			}
			return super.save(consistirCondicaoPagamento(condicaoPagamento));
		} catch (RuntimeException e) {
			e.getStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private CondicaoPagamento consistirCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
		if (condicaoPagamento.getId() == null) {
			condicaoPagamento.setSituacao(Situacao.ATIVO);
		}
		return condicaoPagamento;
	}

	public List<CondicaoPagamento> pesquisarCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
		if (condicaoPagamento.getDescricao().trim().isEmpty()) {
			return super.findBySituation(condicaoPagamento.getSituacao());
		} else {
			return super.findByParametersForSituation(condicaoPagamento.getDescricao(), condicaoPagamento.getSituacao(),
					"descricao", "LIKE", "%", "%");
		}
	}

	public String calcularCondicaoPagamento(CondicaoPagamento condicaoPagamento, String descricao) {
		long intervalo = 0;
		if (condicaoPagamento.getNumeroParcelas() == 1L && condicaoPagamento.getIntervaloDias() == 0L) {
			descricao = A_VISTA;
			return descricao;
		}
		if(condicaoPagamento.getNumeroParcelas() > 1L && condicaoPagamento.getIntervaloDias() == 0L){
			descricao = A_VISTA;
			return descricao;
		}
		List<String> intervaloDias = new ArrayList<String>();
		for (long p = 1; p <= condicaoPagamento.getNumeroParcelas(); p++) {
			intervalo += condicaoPagamento.getIntervaloDias();
			intervaloDias.add(String.valueOf(intervalo));
		}
		descricao = intervaloDias.toString() + " dias";
		descricao = descricao.replace("[", "");
		descricao = descricao.replace("]", "");
		return descricao;

	}
	
	public void verificarSeExisteCondicaoPagamentoCadastradoComMesmaDescricao(CondicaoPagamento condicaoPagamento){
		List<CondicaoPagamento> condicoesPagamento = super.findAll();
		for(CondicaoPagamento c : condicoesPagamento){
			verificarDescricaoIgual(c, condicaoPagamento);
		}
	}
	
	private void verificarDescricaoIgual(CondicaoPagamento c, CondicaoPagamento condicaoPagamento){
		if(condicaoPagamento.getDescricao().trim().equalsIgnoreCase(c.getDescricao())){
			if(condicaoPagamento.getId() == null){
				mostrarMensagemParaUsuario(c);
			}else{
				if(c.getId() != condicaoPagamento.getId()){
					mostrarMensagemParaUsuario(c);
				}
			}
		}
	}
	
	private void mostrarMensagemParaUsuario(CondicaoPagamento c){
		throw new RuntimeException("Existe a condição de pagamento "+c.getDescricao()+" "
				+ "de código "+c.getId()+" já está cadastrado, "
				+ "por favor escolha outra descrição!");
	}

}

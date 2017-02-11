package br.com.sysge.service.financ;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.sysge.infraestrutura.dao.GenericDaoImpl;
import br.com.sysge.model.financ.ParcelasPagamentoOs;
import br.com.sysge.model.gestserv.OrdemServico;
import br.com.sysge.util.DateUtil;

public class ParcelasPagamentoOsService extends GenericDaoImpl<ParcelasPagamentoOs, Long>{

	private static final long serialVersionUID = 8977519177977425704L;
	
	private static final String A_VISTA = "À Vista";
	
	public void salvar(OrdemServico ordemServico, List<ParcelasPagamentoOs> parcelas){
		List<ParcelasPagamentoOs> listaParcelas = procurarParcelasPorOS(ordemServico.getId());
		if(!listaParcelas.isEmpty()){
			for(ParcelasPagamentoOs p : listaParcelas){
				super.removeByObject(p);
			}
		}
		for(ParcelasPagamentoOs p : parcelas){
			p.setOrdemServico(ordemServico);
			super.save(p);
		}
	}
	
	public List<ParcelasPagamentoOs> procurarParcelasPorOS(long idOS){
		return super.findByListProperty(idOS, "ordemServico.id");
	}
	
	public List<ParcelasPagamentoOs> gerarParcelas(OrdemServico ordemServico, List<ParcelasPagamentoOs> parcelas, ParcelasPagamentoOs parcelasPagamentoOs){
		if(ordemServico.getCondicaoPagamento() == null){
			throw new RuntimeException("A condição de pagamento é obrigatório!");
		}
		if(ordemServico.getTotal() == BigDecimal.ZERO){
			throw new RuntimeException("Não é possível gerar as parcelas, pois o valor total está igual a R$: 0,00");
		}
			parcelas = new ArrayList<ParcelasPagamentoOs>();
			String condicaoPagamento = ordemServico.getCondicaoPagamento().getDescricao();
			if(condicaoPagamento.equals(A_VISTA)){
				parcelasPagamentoOs = new ParcelasPagamentoOs();
				parcelasPagamentoOs.setNumero(1L);
				parcelasPagamentoOs.setOrdemServico(ordemServico);
				parcelasPagamentoOs.setQuantidadeParcelas(String.valueOf(1L));
				parcelasPagamentoOs.setValorParcela(ordemServico.getTotal());
				parcelasPagamentoOs.setValorCobrado(ordemServico.getTotal());
				parcelasPagamentoOs.setDataVencimento(DateUtil.asDate(LocalDate.now()));
				parcelas.add(parcelasPagamentoOs);
				return parcelas;
			}else{
				return percorrerListaCondicaoPagamento(condicaoPagamento, ordemServico, parcelas, parcelasPagamentoOs);
			}
		
	}
	
	private List<ParcelasPagamentoOs> percorrerListaCondicaoPagamento(String condicaoPagamento, OrdemServico ordemServico, List<ParcelasPagamentoOs> parcelas, ParcelasPagamentoOs parcelasPagamentoOs){
		condicaoPagamento = condicaoPagamento.trim().replace("dias", "");
		String[] listaCondicaoPagamentoDescricao = condicaoPagamento.trim().split(",");
		for(int i = 1; i <= listaCondicaoPagamentoDescricao.length; i++){
			parcelasPagamentoOs = new ParcelasPagamentoOs();
			parcelasPagamentoOs.setNumero(i);
			parcelasPagamentoOs.setOrdemServico(ordemServico);
			parcelasPagamentoOs.setQuantidadeParcelas(String.valueOf(listaCondicaoPagamentoDescricao.length));
			parcelasPagamentoOs.setValorParcela(ordemServico.getTotal().divide(new BigDecimal(listaCondicaoPagamentoDescricao.length), BigDecimal.ROUND_UP));
			parcelasPagamentoOs.setValorCobrado(ordemServico.getTotal().divide(new BigDecimal(listaCondicaoPagamentoDescricao.length), BigDecimal.ROUND_UP));
			String diaVencimento = listaCondicaoPagamentoDescricao[i-1];
			diaVencimento = diaVencimento.trim();
			parcelasPagamentoOs.setDataVencimento(DateUtil.asDate(LocalDate.now().plusDays(Integer.parseInt(diaVencimento))));
			parcelas.add(parcelasPagamentoOs);
		}
		return parcelas;
	}

}

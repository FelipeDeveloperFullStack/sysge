package br.com.sysge.infraestrutura.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.sysge.model.type.Categoria;
import br.com.sysge.model.type.Situacao;
import br.com.sysge.model.type.StatusOS;
import br.com.sysge.model.type.TipoPessoa;

public interface GenericDao<E, I> extends Serializable {

	E save(E entity);

	void remove(I id);
	
	void removeByObject(E object);

	List<E> findAll();

	E findById(I id);

	E findUserAndPassword(String usuario, String senha, Situacao situacao);

	List<E> findByParametersForSituation(Object value, Situacao situacao, String paramValue, String condition,
			String paramLikeLeft, String paramLikeRight);

	List<E> findByParametersForSituation(Object value, Categoria categoria, Situacao situation, String attributeClass,
			String condition, String paramLikeLeft, String paramLikeRight);

	List<E> findByParametersForSituation(Object value, TipoPessoa tipoPessoa, Situacao situation,
			String attributeClass, String condition, String paramLikeLeft, String paramLikeRight);

	List<E> findBySituation(Situacao situacao);
	
	List<E> findByStatusOs(StatusOS statusOs);
	
	List<E> findByStatusOs(StatusOS statusOS1, StatusOS statusOS2);
	
	List<E> findByListProperty(Object object, String attributeClass);
	
	E findByProperty(Object object, String attributeClass);
	
	List<E> findByNumeroStatusOS(long numero, StatusOS statusOS);
	
	List<E> findByDataEntradaOs(Date dataInicial, Date dataFinal);

	List<E> findAllByIdWithNativeQuery(I id, String table, String column);

	List<E> findAllByIdWithNativeQuery(I id, boolean valueBoolean, String table, String columnID, String columnBoolean);

	List<E> findByDate(Date dataInicial, Date dataFinal, String atributoData);
	
	List<E> findBySituationAndTipoPessoa(Situacao situacao, TipoPessoa tipoPessoa);
	
	List<E> findBySituationAndCategoriaAndTipoPessoa(Situacao situacao, Categoria categoria, TipoPessoa tipoPessoa);

}

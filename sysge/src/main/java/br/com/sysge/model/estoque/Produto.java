package br.com.sysge.model.estoque;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.sysge.infraestrutura.dao.GenericDomain;

@Entity
@Table(name = "tbl_produto")
public class Produto extends GenericDomain{

	private static final long serialVersionUID = -7328127398997221454L;

}

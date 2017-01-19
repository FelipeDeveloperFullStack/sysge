package br.com.sysge.model.type;

public enum MenuSistema {
	
	COMPRAS("Compras"), 
	ESTOQUE("Estoque"),
		CADASTRO_ESTQ("Cadastros"),
			PRODUTO("Produto"),
	GESTAO_SERVICO("Gestão de Serviço"),
		CADASTROS_GS("Cadastros"),
			SERVICO("Serviços"),
		ORDEM_DE_SERVICO("Ordem de Serviço"),
	RH("RH"),
		FUNCIONARIO("Funcionário"),
	FINANCEIRO("Financeiro"),
		CADASTROS("Cadastros"),
		CAIXA("Caixa"),
			LANCAMENTO_CAIXA("Lançamento de caixa"),
		BANCO("Banco"),
			LANCAMENTO_BANCO("Lançamento de banco"),
		CONTAS_A_PAGAR("Contas a pagar"),	
		CONTAS_A_RECEBER("Contas a receber"),	
		RELATORIOS("Relatórios"),
	GLOBAL("Global"),
		CADASTROS_GL("Cadastros"),
			CLIENTE("Cliente/Fornecedor"),
	CONFIGURACAO("Configuracao"),
		USUARIO("Usuário"),
		PERFIL_ACESSO("Perfil de acesso"),
		BACKUP("Backup");
	
	private String menu;
	
	MenuSistema(String menu){
		this.menu = menu;
	}
	
	public String getMenu(){
		return this.menu;
	}

}

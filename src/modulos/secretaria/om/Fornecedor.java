package modulos.secretaria.om;

import java.io.Serializable;

public class Fornecedor implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	private Integer pkFornecedor;
	private Integer codFornecedor;
	private String razaoSocial;
	private String nomeFantasia;
	private String cnpj;
	private String siteEmpresa;
	private String observacao;
	private Estado estadoInscricao;
	private String numInscriEstadual;
	private Cidade cidadeInscricao;
	private String numInscriMunicipal;
		
	private Pessoa pessoa;
	private Endereco endereco;
}

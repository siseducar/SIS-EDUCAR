package br.com.sisEducar.modulos.RH.om;

public class PessoaJuridica 
{
	private Integer pkPessoaJuridica;
	private String nomeFantasia;
	private String cnpj;
	private String razaoSocial;
	private String incricaoEstadual;
	private String incricaoMunicipal;
	private int tipoEmpresa;
	
	public PessoaJuridica() {}
	
	/*Getters and Setters*/
	public String getNomeFantasia() 							{ return nomeFantasia; }
	public void setNomeFantasia(String nomeFantasia) 			{ this.nomeFantasia = nomeFantasia; }
	public String getCnpj()										{ return cnpj; }
	public void setCnpj(String cnpj) 							{ this.cnpj = cnpj; }
	public String getRazaoSocial()								{ return razaoSocial; }
	public void setRazaoSocial(String razaoSocial) 				{ this.razaoSocial = razaoSocial; }
	public String getIncricaoEstadual() 						{ return incricaoEstadual; }
	public void setIncricaoEstadual(String incricaoEstadual) 	{ this.incricaoEstadual = incricaoEstadual; }
	public String getIncricaoMunicipal() 						{ return incricaoMunicipal; }
	public void setIncricaoMunicipal(String incricaoMunicipal)  { this.incricaoMunicipal = incricaoMunicipal; } 
	public int getTipoEmpresa() 								{ return tipoEmpresa; }
	public void setTipoEmpresa(int tipoEmpresa) 				{ this.tipoEmpresa = tipoEmpresa; }
	public Integer getPkPessoaFisica() 							{ return pkPessoaJuridica; }
	public void setPkPessoaFisica(Integer pkPessoaFisica) 	    { this.pkPessoaJuridica = pkPessoaFisica; }
}

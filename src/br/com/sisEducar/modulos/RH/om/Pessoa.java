package br.com.sisEducar.modulos.RH.om;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Pessoa
{ 
	private Integer pkPessoa = 0;
	private Date dataLancamento = new Date(0);
	private PessoaFisica pessoaFisica;
	private PessoaJuridica pessoaJuridica;
	private List<Endereco> listaEnderecos;
	
	public Pessoa() 
	{
		pessoaFisica = new PessoaFisica();
		pessoaJuridica = new PessoaJuridica();
		listaEnderecos = new ArrayList<Endereco>();
	}

	/* Getters and Setters*/
	public Date getDataLancamento() 							 { return dataLancamento; }
	public void setDataLancamento(Date dataLancamento) 			 { this.dataLancamento = dataLancamento; }
	public PessoaFisica getPessoaFisica() 						 { return pessoaFisica; }
	public void setPessoaFisica(PessoaFisica pessoaFisica) 		 { this.pessoaFisica = pessoaFisica; }
	public PessoaJuridica getPessoaJuridica() 					 { return pessoaJuridica; }
	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) { this.pessoaJuridica = pessoaJuridica; }
	public List<Endereco> getListaEnderecos() {return listaEnderecos;}
	public void setListaEnderecos(List<Endereco> listaEnderecos) {this.listaEnderecos = listaEnderecos;	}

	public Integer getPkPessoa() {
		return pkPessoa;
	}

	public void setPkPessoa(Integer pkPessoa) {
		this.pkPessoa = pkPessoa;
	}
}

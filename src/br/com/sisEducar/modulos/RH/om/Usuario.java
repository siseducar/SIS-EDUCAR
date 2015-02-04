package br.com.sisEducar.modulos.RH.om;

import java.util.Date;

public class Usuario 
{
	/* Variaveis*/
	private String nome;
	private String senha;
	private Date dataLancamento;
	private String confirmarSenha;
	private String email;
	private String confirmarEmail;
	private int tipo;
	private Pessoa pessoa;
	private int status;

	public Usuario() {}
	
	/* Getters and Setters */
	public String getNome() 				{ return nome; }
	public void setNome(String nome) 		{ this.nome = nome; }
	public String getSenha()				{ return senha; }
	public void setSenha(String senha) 		{ this.senha = senha; }
	public Date getDataLancamento() 		{ return dataLancamento; }
	public void setDataLancamento(Date dataLancamento) { this.dataLancamento = dataLancamento; }
	public String getEmail() 				{ return email; }
	public void setEmail(String email) 		{ this.email = email; }
	public int getTipo() 					{ return tipo; }
	public void setTipo(int tipo) 			{ this.tipo = tipo; }
	public String getConfirmarSenha() 		{ return confirmarSenha; }
	public void setConfirmarSenha(String confirmarSenha) { this.confirmarSenha = confirmarSenha;}
	public String getConfirmarEmail() 		{ return confirmarEmail; }
	public void setConfirmarEmail(String confirmarEmail) { this.confirmarEmail = confirmarEmail; }
	public Pessoa getPessoa() 				{ return pessoa; }
	public void setPessoa(Pessoa pessoa) 	{ this.pessoa = pessoa; }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

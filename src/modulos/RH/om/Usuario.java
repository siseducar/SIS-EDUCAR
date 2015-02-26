package modulos.RH.om;

import java.util.Date;

public class Usuario 
{
	/* Variaveis*/
	private String pkUsuario;
	private String nome;
	private String senha;
	private Date dataLancamento;
	private String email;
	private int tipo;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPkUsuario() {
		return pkUsuario;
	}

	public void setPkUsuario(String pkUsuario) {
		this.pkUsuario = pkUsuario;
	}
}

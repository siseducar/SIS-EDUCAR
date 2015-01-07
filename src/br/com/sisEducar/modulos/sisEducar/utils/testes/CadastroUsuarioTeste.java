package br.com.sisEducar.modulos.sisEducar.utils.testes;

import java.sql.SQLException;

import br.com.sisEducar.modulos.RH.dao.UsuarioDAO;
import br.com.sisEducar.modulos.RH.om.Usuario;
import br.com.sisEducar.modulos.sisEducar.sisEducarServlet.LoginServlet;

public class CadastroUsuarioTeste {

	public static void main(String[] args) throws SQLException 
	{
		Boolean resultado = false;
		System.out.println("Cadastrando...");

		Usuario usuario = new Usuario();
		usuario.setNome("admin");
		usuario.setSenha("123");
		usuario.setSenha(LoginServlet.criptografarSenha(usuario.getSenha()));
		
		resultado = new UsuarioDAO().inserirUsuario(usuario);

		if(resultado) { System.out.println("Cadastrado com sucesso!"); }
		else { System.out.println("Erro ao cadastrar!"); }
	}
}

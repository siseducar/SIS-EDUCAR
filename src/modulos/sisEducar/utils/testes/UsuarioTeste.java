package modulos.sisEducar.utils.testes;

import java.sql.SQLException;

import modulos.RH.dao.UsuarioDAO;
import modulos.RH.om.Usuario;
import modulos.sisEducar.sisEducarServlet.LoginServlet;

public class UsuarioTeste {

	public static void main(String[] args) throws SQLException 
	{
		inserirUsuario();
	}
	
	public static void inserirUsuario() throws SQLException
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

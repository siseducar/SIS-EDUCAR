package modulos.sisEducar.utils.testes;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import modulos.secretaria.dao.UsuarioDAO;
import modulos.secretaria.om.Usuario;
import modulos.sisEducar.servlet.SisEducarServlet;

public class UsuarioTeste {

	public static void main(String[] args) throws SQLException, NoSuchAlgorithmException 
	{
		inserirUsuario();
	}
	
	public static void inserirUsuario() throws SQLException, NoSuchAlgorithmException
	{
		Boolean resultado = false;
		System.out.println("Cadastrando...");
		
		Usuario usuario = new Usuario();
		usuario.setNome("michael");
		usuario.setSenha("123");
		usuario.setCpfcnpj("41225663806");
		usuario.setEmail("michaelseraphim@live.com");
		usuario.setGenero("masculino");
		usuario.setSenha(SisEducarServlet.criptografarSenha(usuario.getSenha()));
		
		resultado = new UsuarioDAO().inserirUsuario(usuario);
		
		if(resultado) { System.out.println("Cadastrado com sucesso!"); }
		else { System.out.println("Erro ao cadastrar!"); }
	}
}

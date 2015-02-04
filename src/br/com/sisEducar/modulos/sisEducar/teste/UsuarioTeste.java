package br.com.sisEducar.modulos.sisEducar.teste;

import br.com.sisEducar.modulos.RH.dao.UsuarioDAO;
import br.com.sisEducar.modulos.RH.om.Usuario;
import br.com.sisEducar.modulos.sisEducar.sisEducarServlet.LoginServlet;
import br.com.sisEducar.modulos.sisEducar.utils.ConstantesSisEducar;

public class UsuarioTeste 
{
	public static void main(String[] args) 
	{
		inserirUsuario();
	}
	
	public static void inserirUsuario()
	{
		Usuario usuario = new Usuario();
		Boolean resultado = false;
		try 
		{
			usuario.setNome("adm");
			usuario.setSenha(new LoginServlet().criptografarSenha("123"));
			usuario.setStatus(ConstantesSisEducar.STATUS_ATIVO);
			
			resultado = new UsuarioDAO().inserirUsuario(usuario);
			if(resultado)
			{
				System.out.println("Cadastro essa bosta!");
			}
			else
			{
				System.out.println("Nem cadastro em!");
			}
		} 
		catch (Exception e) 
		{
			System.out.println("fodeu, deu pau!");
		}
	}
}

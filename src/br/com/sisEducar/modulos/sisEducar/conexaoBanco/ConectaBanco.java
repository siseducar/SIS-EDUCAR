package br.com.sisEducar.modulos.sisEducar.conexaoBanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaBanco 
{
	/**
	 * M�todo de conex�o com o banco de dados PostgreSQL
	 * @return
	 */
	public Connection getConection() 
	{
		// pegando os parametros do banco
		ParametrosBD conf = new ParametrosBD();
		Connection con = null;

		try 
		{
			Class.forName(conf.getDRIVER());
			System.out.println("Driver encontrado com sucesso");
			try 
			{
				con = DriverManager.getConnection(conf.getURL(), conf.getUsuario(), conf.getSenha());
				System.out.println("Banco conectado com sucesso");
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}

		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		return con;
	}
}

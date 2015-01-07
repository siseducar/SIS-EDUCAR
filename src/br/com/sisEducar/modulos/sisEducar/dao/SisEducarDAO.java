package br.com.sisEducar.modulos.sisEducar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SisEducarDAO 
{
	/**
	 * Este metodo recebe como parametro uma conexao com o banco de dados para encerrar a mesma
	 * @param con
	 * @throws SQLException
	 */
	public void desabilitarAutoCommit(Connection con) throws SQLException
	{
		con.setAutoCommit(false);
	}
	
	/**
	 * O método é usado para fechar a conexão com o banco recebendo como parametro a conexão, o preparedStatment e assim a resposta se fechara a conexão e também
	 * se executara um commit
	 * @param Connection con
	 * @param PreparedStatement ps
	 * @param TRUE || FALSE fecharConexao
	 * @param TRUE || FALSE commit
	 * @throws SQLException
	 */
	public void fecharConexaoBanco(Connection con, PreparedStatement ps, Boolean fecharConexao, Boolean commit) throws SQLException
	{
		if(fecharConexao)
		{
			if(con !=null && ps !=null)
			{
				if(commit)
				{
					ps.execute();
					con.commit();
				}
				
				ps.close();
				con.close();
			}
		}
	}
}

package modulos.sisEducar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class SisEducarDAO 
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
		
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
	
	public String obtemChaveAcesso() throws SQLException
	{
		String chaveAcesso = "";
		String querySQL = "SELECT * FROM ChaveAcesso WHERE status = ?";
		
		ps = con.prepareStatement(querySQL);
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) 
		{
			chaveAcesso = rs.getString("chave");
		}
		
		return chaveAcesso;
	}
}

package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;

public class PopulaJustificativasFaltaDAO extends SisEducarDAO
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public PopulaJustificativasFaltaDAO() throws SQLException
	{
		desabilitarAutoCommit(con);
	}
	
	/**
	 * Adiciona as justificativas no banco de dados
	 * @author João Paulo
	 * @param ordemExibicao
	 * @param codigo
	 * @param descricao
	 * @param abonavel
	 * @throws SQLException
	 */
	public void inserirJustificativas(String codigo, String descricao, Boolean abonavel, Integer ordemExibicao) throws SQLException 
	{
		String querySQL = "INSERT INTO JustificativaFalta" 
				+ " ("
				+ "      ordemExibicao, codigo, descricao, abonavel"
				+ " )"
				+ " VALUES (?, ?, ?, ?)";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ordemExibicao);
		ps.setString(2, codigo);
		ps.setString(3, descricao);
		ps.setBoolean(4, abonavel);
		
		fecharConexaoBanco(con, ps, false, true);
	}
}

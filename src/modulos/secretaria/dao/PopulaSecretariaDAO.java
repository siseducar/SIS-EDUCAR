package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;

public class PopulaSecretariaDAO extends SisEducarDAO
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public PopulaSecretariaDAO() throws SQLException
	{
		desabilitarAutoCommit(con);
	}
	
	/**
	 * Adiciona as permissões no banco de dados de acordo com a informações passadas como parâmetro
	 * @author João Paulo
	 * @param nomePermissao
	 * @param status
	 * @param tipoPermissao
	 * @throws SQLException
	 */
	public void inserirPermissoes(String nomePermissao, Integer status, Integer tipoPermissao, Integer tipoModuloResponsavel, Integer tipoSubMenuResponsavel, Integer tela) throws SQLException 
	{
		String querySQL = "INSERT INTO Permissao (nome, status, tipo, tipoModuloResponsavel,tiposubmenuresponsavel, telaResponsavel) VALUES (?, ?, ?, ?, ?, ?)";
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, nomePermissao);
		ps.setInt(2, status);
		ps.setInt(3, tipoPermissao);
		ps.setObject(4, tipoModuloResponsavel!=null ? tipoModuloResponsavel : null);
		ps.setObject(5, tipoSubMenuResponsavel!=null ? tipoSubMenuResponsavel : null);
		ps.setObject(6, tela!=null ? tela : null);
		
		fecharConexaoBanco(con, ps, false, true);
	}
	
	/**
	 * Adiciona as raças no banco de dados de acordo com a informações passadas como parâmetro
	 * @author João Paulo
	 * @param codigo
	 * @param descricao
	 * @param status
	 * @throws SQLException
	 */
	public void inserirParametros(String nomeTabela, String codigo, String descricao, Integer status, Integer ordemExibicao) throws SQLException 
	{
		String querySQL = "INSERT INTO " + nomeTabela 
				+ " ("
				+ "      codigo, descricao, status";
		
		if(ordemExibicao!=null && ordemExibicao >0)
		{
			querySQL += ", ordemExibicao";
			querySQL += " )";
			querySQL += " VALUES (?, ?, ?, ?)";
		}
		else
		{ 
			querySQL += " )"; 
			querySQL += " VALUES (?, ?, ?)";
		}
		
		
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, codigo);
		ps.setString(2, descricao);
		ps.setInt(3, status);
		
		if(ordemExibicao!=null && ordemExibicao >0)
		{
			ps.setInt(4, ordemExibicao);
		}
		
		fecharConexaoBanco(con, ps, false, true);
	}
}
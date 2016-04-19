package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;

public class PopulaRHDAO extends SisEducarDAO
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public PopulaRHDAO() throws SQLException
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
	public void inserirPermissoes(String nomePermissao, Integer status, Integer tipoPermissao, Integer tipoModuloResponsavel, Integer tipoSubMenuResponsavel) throws SQLException 
	{
		String querySQL = "INSERT INTO Permissao (nome, status, tipo, tipoModuloResponsavel,tiposubmenuresponsavel) VALUES (?, ?, ?, ?, ?)";
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, nomePermissao);
		ps.setInt(2, status);
		ps.setInt(3, tipoPermissao);
		ps.setObject(4, tipoModuloResponsavel!=null ? tipoModuloResponsavel : null);
		ps.setObject(5, tipoSubMenuResponsavel!=null ? tipoSubMenuResponsavel : null);
		
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
	public void inserirParametros(String nomeTabela, String codigo, String descricao, Integer status) throws SQLException 
	{
		String querySQL = "INSERT INTO " + nomeTabela + " (codigo, descricao, status) VALUES (?, ?, ?)";
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, codigo);
		ps.setString(2, descricao);
		ps.setInt(3, status);
		
		fecharConexaoBanco(con, ps, false, true);
	}
}

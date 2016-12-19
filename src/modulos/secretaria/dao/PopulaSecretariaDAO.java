package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

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
		Boolean existePermissao = verificaPermissao(nomePermissao, tipoPermissao, tipoModuloResponsavel, tipoSubMenuResponsavel, tela);
		if(!existePermissao)
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
		Boolean existeParametro = verificaParametro(nomeTabela, codigo, descricao);
		if(!existeParametro)
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
			
			if(codigo.equals("NI"))
			{
				System.out.println(ps);
			}
			
			fecharConexaoBanco(con, ps, false, true);
		}
	}
	
	/**
	 * Verifica se existe o parametro, caso existir retorna TRUE, caso contrário retorna FALSE
	 * @author João Paulo
	 * @param nomeTabela
	 * @param codigo
	 * @param descricao
	 * @return TRUE || FALSE
	 * @throws SQLException
	 */
	public Boolean verificaParametro(String nomeTabela, String codigo, String descricao) throws SQLException
	{
		String querySQL = "SELECT STATUS FROM "
				+ nomeTabela
				+ " WHERE CODIGO = ? AND DESCRICAO = ? AND STATUS = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, codigo);
		ps.setString(2, descricao);
		ps.setInt(3, ConstantesSisEducar.STATUS_ATIVO);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next()) 
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Verifica se existe uma permissão com os mesmos parâmetros, caso sim etorna true, caso contrário volta false
	 * @author João Paulo
	 * @param nomePermissao
	 * @param tipoPermissao
	 * @param tipoModuloResponsavel
	 * @param tipoSubMenuResponsavel
	 * @param tela
	 * @return TRUE || FALSE
	 * @throws SQLException
	 */
	public Boolean verificaPermissao(String nomePermissao, Integer tipoPermissao, Integer tipoModuloResponsavel, Integer tipoSubMenuResponsavel, Integer tela) throws SQLException
	{
		String querySQL = "SELECT STATUS FROM PERMISSAO "
				+ " WHERE NOME = ? "
				+ " AND STATUS = ?"
				+ " AND TIPO = ? "
				+ " AND TIPOMODULORESPONSAVEL = ? "
				+ " AND TIPOSUBMENURESPONSAVEL = ? "
				+ " AND TELARESPONSAVEL = ? ";
				
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, nomePermissao);
		ps.setInt(2, ConstantesSisEducar.STATUS_ATIVO);
		ps.setInt(3, tipoPermissao);
		ps.setInt(4, tipoModuloResponsavel);
		ps.setInt(5, tipoSubMenuResponsavel);
		ps.setInt(6, tela);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next()) 
		{
			return true;
		}
		
		return false;
	}
}

package modulos.RH.dao;

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
	public void inserirPermissoes(String nomePermissao, Integer status, Integer tipoPermissao) throws SQLException 
	{
		String querySQL = "INSERT INTO Permissao (nome, status, tipo) VALUES (?, ?, ?)";
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, nomePermissao);
		ps.setInt(2, status);
		ps.setInt(3, tipoPermissao);
		
		//Depois que terminar o cadastro de pessoa, remover esta linha e adicionar corretamente a pessoa
		
		fecharConexaoBanco(con, ps, false, true);
	}
}

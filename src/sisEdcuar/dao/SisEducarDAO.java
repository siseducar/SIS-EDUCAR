package sisEdcuar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Usuario;
import sisEdcuar.conexaoBanco.ConectaBanco;
import sisEdcuar.om.ChaveAcesso;
import sisEdcuar.utils.ConstantesSisEducar;

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
		if(con !=null && ps !=null)
		{
			if(fecharConexao)
			{
				ps.close();
				con.close();
			}
			if(commit)
			{
				ps.execute();
				con.commit();
			}
		}
	}
	
	/**
	 * Busca a chave de acesso do usuário
	 * @return
	 * @throws SQLException
	 */
	public ChaveAcesso obtemChaveAcesso(Usuario usuario) throws SQLException
	{
		ChaveAcesso chaveAcesso = null;
		Cidade municipioCliente = null;
		String querySQL = "SELECT * FROM ChaveAcesso "
						+ "WHERE status = ?"
						+ "AND fkMunicipioCliente = ?";
		
		ps = con.prepareStatement(querySQL);
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setInt(2, usuario.getFkMunicipioCliente().getPkCidade());
		
		ResultSet rs = ps.executeQuery();
		if(rs.next()) 
		{
			municipioCliente = new Cidade();
			municipioCliente.setPkCidade(rs.getInt("fkMunicipioCliente"));
			
			chaveAcesso = new ChaveAcesso();
			chaveAcesso.setPkChaveAcesso(rs.getInt("pkChaveAcesso"));
			chaveAcesso.setChave(rs.getString("chave"));
			chaveAcesso.setMunicipioCliente(municipioCliente);
			
			return chaveAcesso;
		}
		
		return null;
	}
}

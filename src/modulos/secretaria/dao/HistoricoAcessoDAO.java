package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.secretaria.om.HistoricoAcesso;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;

public class HistoricoAcessoDAO extends SisEducarDAO
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;	
	ResultSet rs = null;

	Date dataAtual = new Date(System.currentTimeMillis());
	
	public HistoricoAcessoDAO() throws SQLException
	{
		//Quando construir a classe, deverá desabilitar o autoCommit
		desabilitarAutoCommit(con);
	}
	
	
	/**
	 * O método é usado para inserir um novo histórico de acesso no banco de dados
	 * @return
	 * @throws SQLException 
	 */
	public Boolean inserirHistoricoAcesso(HistoricoAcesso historicoAcesso)
	{
		try 
		{
			String querySQL = "INSERT INTO HistoricoAcesso "
					+ " (dataLogin, fkUsuario) values(now(),?)";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setObject(1, new Integer(historicoAcesso.getUsuario().getPkUsuario()));
			
			fecharConexaoBanco(con, ps, false, true);
			return true;
		} 
		catch (SQLException e) 
		{
			System.out.println(e);
			return false;
		}
	}
}
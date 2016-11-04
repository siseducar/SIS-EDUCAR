package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.HistoricoAcesso;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.Usuario;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

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
	
	/**
	 * Busca todos os históricos de acesso no banco de dados
	 * @author João Paulo
	 * @param usuario
	 * @return
	 * @throws SQLException
	 */
	public List<HistoricoAcesso> consultar(Usuario usuario, Pessoa pessoa, Date inicio, Date fim) throws SQLException
	{
		List<HistoricoAcesso> acessos = new ArrayList<HistoricoAcesso>();
		HistoricoAcesso historicoAcesso = null;
		Integer numeroArgumentos = 1;
		String querySQL = "SELECT HA.* FROM HISTORICOACESSO HA" +
				" INNER JOIN USUARIO U ON(HA.FKUSUARIO = U.PKUSUARIO)" +
				" INNER JOIN PESSOA P ON(U.FKPESSOA = P.PKPESSOA)" +
				" WHERE HA.STATUS = ?" +
				" AND U.STATUS = ?" +
				" AND P.STATUS = ?";
		
		if(inicio!=null)
		{
			querySQL += " AND DATALOGIN >= ?";
		}
		if(fim!=null)
		{
			querySQL += " AND DATALOGIN <= ?";
		}
		
		if(usuario!=null)
		{
			querySQL += " AND U.PKUSUARIO = ?";
		}
		
		if(pessoa!=null)
		{
			querySQL += " AND P.PKPESSOA = ?";
		}
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		numeroArgumentos++;
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		numeroArgumentos++;
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		
		if(inicio!=null)
		{
			numeroArgumentos++;
			ps.setDate(numeroArgumentos, inicio);
		}
		
		if(fim!=null)
		{
			numeroArgumentos++;
			ps.setDate(numeroArgumentos, fim);
		}
		
		if(usuario!=null)
		{
			numeroArgumentos++;
			ps.setObject(numeroArgumentos , new Integer(usuario.getPkUsuario()));
		}
		
		if(pessoa!=null)
		{
			numeroArgumentos++;
			ps.setObject(numeroArgumentos, pessoa.getPkPessoa());
		}
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) 
		{
			historicoAcesso = new HistoricoAcesso();
			historicoAcesso.setDataLogin(rs.getDate("DATALOGIN"));
			historicoAcesso.setUsuario(new UsuarioDAO().obtemUsuario(null, ConstantesSisEducar.STATUS_ATIVO, new Integer(rs.getString("FKUSUARIO"))));
			
			acessos.add(historicoAcesso);
		}
		
		return acessos;
	}
}
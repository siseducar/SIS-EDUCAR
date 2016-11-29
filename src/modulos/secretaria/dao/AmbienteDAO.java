package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Ambiente;
import modulos.secretaria.om.UnidadeEscolar;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class AmbienteDAO extends SisEducarDAO 
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public AmbienteDAO() throws SQLException
	{
		desabilitarAutoCommit(con);
	}
	
	/* Metodo para retornar os blocos do ambiente */
	public Boolean inserir(Ambiente ambiente) throws SQLException 
	{
		String querySQL = "INSERT INTO ambiente "
				+ " (codigo, nome, metragem,  capacidade, fkTipoAmbiente, fkBloco, fkUnidadeEscolar) values(?,?,?,?,?,?,?)";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, ambiente.getCodigo());
		ps.setString(2, ambiente.getNome());
		ps.setDouble(3, ambiente.getMetragem());
		ps.setDouble(4, ambiente.getCapacidade());
		ps.setObject(5, ambiente.getTipo()!=null ? ambiente.getTipo().getPkTipoAmbiente() : null);
		ps.setObject(6, ambiente.getBloco()!=null ? ambiente.getBloco().getPkBloco() : null);
		ps.setObject(7, ambiente.getUnidadeEscolar().getPkUnidadeEscolar());
		
		fecharConexaoBanco(con, ps, false, true);
		return true;	
	}
	
	/**
	 * Usado para remover todos os ambientes ligados a uma unidade escolar
	 * @author João Paulo
	 * @param unidadeEscolar
	 * @return
	 * @throws SQLException
	 */
	public Boolean remover(UnidadeEscolar unidadeEscolar) throws SQLException 
	{
		String querySQL = "UPDATE ambiente"
				+ " SET status = ?"
				+ " WHERE fkUnidadeEscolar = ? RETURNING STATUS";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_REMOVIDO);
		ps.setInt(2, unidadeEscolar.getPkUnidadeEscolar());
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			if(rs.getInt("STATUS") == ConstantesSisEducar.STATUS_REMOVIDO) {
				fecharConexaoBanco(con, ps, false, true);
				return true;
			} else {
				return false;					
			}
		}
		
		return false;	
	}
	
	/**
	 * Busca todos os ambientes de uma determinada unidade escolar
	 * @author João Paulo
	 * @param unidadeEscolar
	 * @return
	 * @throws SQLException
	 */
	public List<Ambiente> buscar(UnidadeEscolar unidadeEscolar) throws SQLException
	{
		List<Ambiente> ambientes = new ArrayList<Ambiente>();
		Ambiente ambiente = null;
		TipoAmbienteDAO tipoAmbienteDAO = new TipoAmbienteDAO();
		BlocoDAO blocoDAO = new BlocoDAO();
		
		String querySQL = "SELECT * FROM Ambiente"
				+ " WHERE status = ?"
				+ " AND fkUnidadeEscolar = ?";
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setObject(2, unidadeEscolar.getPkUnidadeEscolar());
		
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			ambiente = new Ambiente();
			ambiente.setPkAmbiente(rs.getInt("PKAMBIENTE"));
			ambiente.setCodigo(rs.getString("CODIGO"));
			ambiente.setNome(rs.getString("NOME"));
			ambiente.setMetragem(rs.getDouble("METRAGEM"));
			ambiente.setCapacidade(rs.getInt("CAPACIDADE"));
			
			ambiente.setTipo(tipoAmbienteDAO.buscarSimplesTipoAmbiente(rs.getInt("FKTIPOAMBIENTE")));
			ambiente.setBloco(blocoDAO.buscarSimplesBlocoAmbiente(rs.getInt("FKBLOCO")));
			
			ambientes.add(ambiente);
		}
		
		return ambientes;
	}
}

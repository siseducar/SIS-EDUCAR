package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Terreno;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class TerrenoDAO extends SisEducarDAO
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public TerrenoDAO() throws SQLException
	{
		desabilitarAutoCommit(con);
	}
	
	/**
	 * Inseri um terreno
	 * @author João Paulo
	 * @param terreno
	 * @return Terreno
	 * @throws SQLException
	 */
	public Terreno inserirTerreno(Terreno terreno) throws SQLException 
	{
		if(terreno.getDistanciaAteSede()!=null || terreno.getAreaTerrenoM2()!=null || terreno.getAreaConstrucaoM2()!=null || terreno.getLatitude()!=null || terreno.getLongitude()!=null)
		{
			String querySQL = "INSERT INTO terreno "
					+ " ("
					+ " distanciaAteSede, areaTerrenoM2, areaConstrucaoM2, latitude, longitude, status"
					+ ") "
					+ " values(?,?,?,?,?,?)";
			ps = con.prepareStatement(querySQL);
			ps.setDouble(1, terreno.getDistanciaAteSede()!=null ? terreno.getDistanciaAteSede() : 0);
			ps.setDouble(2, terreno.getAreaTerrenoM2() !=null ? terreno.getAreaTerrenoM2() : 0);
			ps.setDouble(3, terreno.getAreaConstrucaoM2()!=null ? terreno.getAreaConstrucaoM2() : 0);
			ps.setDouble(4, terreno.getLatitude()!=null ? terreno.getLatitude() : 0);
			ps.setDouble(5, terreno.getLongitude()!=null ? terreno.getLongitude() : 0);
			ps.setInt(6, ConstantesSisEducar.STATUS_ATIVO);
			
			fecharConexaoBanco(con, ps, false, true);
			
			terreno.setPkTerreno(obtemPKTerreno(terreno.getDistanciaAteSede(), terreno.getAreaTerrenoM2(), terreno.getAreaConstrucaoM2(), terreno.getLatitude(), terreno.getLongitude()));
		}
		
		return terreno;
	}
	
	/**
	 * Método usado para atualizar as informações do terreno a partir de sua PK
	 * @author João Paulo
	 * @param terreno
	 * @return Terreno
	 * @throws SQLException
	 */
	public Terreno atualizarTerreno(Terreno terreno) throws SQLException 
	{
		String querySQL = "UPDATE terreno "
				+ " SET (distanciaAteSede, areaTerrenoM2, areaConstrucaoM2, latitude, longitude) ="
				+ " (?,?,?,?,?)"
				+ " WHERE pkTerreno = ?";
		ps = con.prepareStatement(querySQL);
		ps.setDouble(1, terreno.getDistanciaAteSede()!=null ? terreno.getDistanciaAteSede() : 0);
		ps.setDouble(2, terreno.getAreaTerrenoM2() !=null ? terreno.getAreaTerrenoM2() : 0);
		ps.setDouble(3, terreno.getAreaConstrucaoM2()!=null ? terreno.getAreaConstrucaoM2() : 0);
		ps.setDouble(4, terreno.getLatitude()!=null ? terreno.getLatitude() : 0);
		ps.setDouble(5, terreno.getLongitude()!=null ? terreno.getLongitude() : 0);
		ps.setInt(6, terreno.getPkTerreno());
		
		fecharConexaoBanco(con, ps, false, true);
		
		return terreno;
	}
	
	/**
	 * Obtem a pk do terreno salvo pelos parâmeros passados
	 * @author João Paulo
	 * @param distanciaAteSede
	 * @param areaTerrenoM2
	 * @param areaConstrucaoM2
	 * @param latitude
	 * @param longitude
	 * @return pkTerreno
	 * @throws SQLException
	 */
	public Integer obtemPKTerreno(Double distanciaAteSede, Double areaTerrenoM2, Double areaConstrucaoM2, Double latitude, Double longitude) throws SQLException
	{
		Integer numeroArgumentos = 1;
		
		String querySQL = "SELECT * FROM terreno"
				+ " WHERE status = ?";
		
		if(distanciaAteSede!=null && distanciaAteSede >0)		 	{ querySQL += " AND distanciaAteSede = ?"; }
		if(areaTerrenoM2!=null && areaTerrenoM2 >0)					{ querySQL += " AND areaTerrenoM2 = ?"; }
		if(areaConstrucaoM2!=null && areaConstrucaoM2 >0)			{ querySQL += " AND areaConstrucaoM2 = ?"; }
		if(latitude!=null && latitude >0)							{ querySQL += " AND latitude = ?"; }
		if(longitude!=null && longitude >0)							{ querySQL += " AND longitude = ?"; }
		
		ps = con.prepareStatement(querySQL);
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		
		if(distanciaAteSede!=null && distanciaAteSede>0)	
		{ 
			numeroArgumentos ++; 
			ps.setDouble(numeroArgumentos, distanciaAteSede);
		}
		if(areaTerrenoM2!=null && areaTerrenoM2>0)	
		{ 
			numeroArgumentos ++; 
			ps.setDouble(numeroArgumentos, areaTerrenoM2);
		}
		if(areaConstrucaoM2!=null && areaConstrucaoM2>0)	
		{ 
			numeroArgumentos ++; 
			ps.setDouble(numeroArgumentos, areaConstrucaoM2);
		}
		if(latitude!=null && latitude>0)	
		{ 
			numeroArgumentos ++; 
			ps.setDouble(numeroArgumentos, latitude);
		}
		if(longitude!=null && longitude>0)	
		{ 
			numeroArgumentos ++; 
			ps.setDouble(numeroArgumentos, longitude);
		}
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			return rs.getInt("pkTerreno");
		}
		
		return null;
	}
	
	/**
	 * Busca um terreno pelos parâmetros passados
	 * @author João Paulo
	 * @param pkTerreno
	 * @return Terreno
	 * @throws SQLException
	 */
	public Terreno buscarTerreno(Integer pkTerreno) throws SQLException
	{
		Cidade cidadeTerrenoMunicipioCliente = null;
		Terreno terreno = null;
		String querySQL = "SELECT * FROM Terreno "
				+ " WHERE status = ?"
				+ " AND pkTerreno = ?";
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setInt(2, pkTerreno);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			cidadeTerrenoMunicipioCliente = new Cidade();
			terreno = new Terreno();
			terreno.setPkTerreno(rs.getInt("pkTerreno"));
			terreno.setDistanciaAteSede(rs.getDouble("distanciaAteSede"));
			terreno.setAreaTerrenoM2(rs.getDouble("areaTerrenoM2"));
			terreno.setAreaConstrucaoM2(rs.getDouble("areaConstrucaoM2"));
			terreno.setLatitude(rs.getDouble("latitude"));
			terreno.setLongitude(rs.getDouble("longitude"));
			
			return terreno;
		}
		
		return null;
	}
}

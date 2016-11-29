package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.TipoAmbiente;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class TipoAmbienteDAO extends SisEducarDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os tipos de ambiente */
	public List<TipoAmbiente> consultaTipoAmbiente() throws SQLException 
	{
		List<TipoAmbiente> listaTipos = new ArrayList<>();
		
		String querySQL = "SELECT * FROM TipoAmbiente WHERE STATUS = ? ORDER BY ORDEMEXIBICAO";

		ps = con.prepareStatement(querySQL.toString());
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			TipoAmbiente paramTipo = new TipoAmbiente();
			paramTipo.setPkTipoAmbiente(rs.getInt("PKTIPOAMBIENTE"));
			paramTipo.setCodigo(rs.getString("CODIGO"));
			paramTipo.setDescricao(rs.getString("DESCRICAO"));
						
			listaTipos.add(paramTipo);
		}
		
		fecharConexaoBanco(con, ps, true, false);
		
		return listaTipos;
	}
	
	/**
	 * Busca um tipo ambiente pela Pk do mesmo
	 * @author João Paulo
	 * @param pkTipoAmbiente
	 * @return
	 * @throws SQLException
	 */
	public TipoAmbiente buscarSimplesTipoAmbiente(Integer pkTipoAmbiente) throws SQLException 
	{
		String querySQL = "SELECT * FROM TipoAmbiente WHERE STATUS = ? AND pkTipoAmbiente = ?";
		ps = con.prepareStatement(querySQL.toString());
		
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setInt(2, pkTipoAmbiente);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) 
		{
			TipoAmbiente paramTipo = new TipoAmbiente();
			paramTipo.setPkTipoAmbiente(rs.getInt("PKTIPOAMBIENTE"));
			paramTipo.setCodigo(rs.getString("CODIGO"));
			paramTipo.setDescricao(rs.getString("DESCRICAO"));
			
			return paramTipo;
		}
		
		return null;
	}
}

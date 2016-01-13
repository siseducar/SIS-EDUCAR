package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.RH.om.ParametrosSecretaria;
import modulos.sisEducar.conexaoBanco.ConectaBanco;

public class ParametrosDAO {

	// Realizando conexão com o banco
		ConectaBanco conexao = new ConectaBanco();
		Connection con = conexao.getConection();
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		/* Metodo para retornar os tipos de raca padrao */		
		public List<ParametrosSecretaria> consultaRaca() throws SQLException{
			
			ParametrosSecretaria paramRaca = new ParametrosSecretaria();
			List<ParametrosSecretaria> listaRaca = new ArrayList<ParametrosSecretaria>();
			
			String querySQL = "SELECT * FROM PARAM_RACA";
			ps = con.prepareStatement(querySQL);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) 
			{
				paramRaca.setCodRaca(rs.getInt("CODIGO_RACA"));
				paramRaca.setDescriRaca(rs.getString("DESCRI_RACA"));
				
				listaRaca.add(paramRaca);
			}
			
			return listaRaca;
		}
		
		/* Metodo para retornar os tipos de cores padrao */
		public List<ParametrosSecretaria> consultaCor() throws SQLException{
			
			ParametrosSecretaria paramCor = new ParametrosSecretaria();
			List<ParametrosSecretaria> listaCor = new ArrayList<ParametrosSecretaria>();
			
			String querySQL = "SELECT * FROM PARAM_COR";
			ps.getConnection().prepareStatement(querySQL);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()){
				paramCor.setCodCor(rs.getInt("CODIGO_COR"));
				paramCor.setDescriCor(rs.getString("DESCRI_COR"));
				
				listaCor.add(paramCor);
			}
			
			return listaCor;
		}
		
		/* Metodo para retornar os tipos de Situacao Economica */
		public List<ParametrosSecretaria> consultaSituacaoEcon() throws SQLException{
			
			ParametrosSecretaria paramSituacaoEcon = new ParametrosSecretaria();
			List<ParametrosSecretaria> listaSituacaoEcon = new ArrayList<ParametrosSecretaria>();
			
			String querySQL = "SELECT * FROM PARAM_SITU_ECONOMICA";
			ps.getConnection().prepareStatement(querySQL);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()){
				paramSituacaoEcon.setCodSituaEconomica(rs.getInt("CODIGO_SITU_ECONOMICA"));
				paramSituacaoEcon.setDescriSituEconomica(rs.getString("DESCRI_SITU_ECONOMICA"));
				
				listaSituacaoEcon.add(paramSituacaoEcon);
			}
			
			return null;
		}
		
		/* Metodo para retornar os tipos de Religiao */
		public List<ParametrosSecretaria> consultaReligiao() throws SQLException{
			
			ParametrosSecretaria paramReligiao = new ParametrosSecretaria();
			List<ParametrosSecretaria> listareligiao = new ArrayList<ParametrosSecretaria>();
			
			String querySQL = "SELECT * FROM PARAM_RELIGIAO";
			ps.getConnection().prepareStatement(querySQL);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()){
				paramReligiao.setCodReligiao(rs.getInt("CODIGO_RELIGIAO"));
				paramReligiao.setDescriReligiao(rs.getString("DESCRI_RELIGIAO"));
				
				listareligiao.add(paramReligiao);
			}
			
			return null;
		}
		
}

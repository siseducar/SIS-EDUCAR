package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Regiao;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class RegiaoDAO {

	// Realizando conexão com o banco
		ConectaBanco conexao = new ConectaBanco();
		Connection con = conexao.getConection();
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		/* Metodo para retornar os tipos de REGIOES padrões */
		public List<Regiao> consultaRegiao() throws SQLException{
			
			List<Regiao> listaRegiao = new ArrayList<Regiao>();
			
			String querySQL = "SELECT * FROM REGIAO WHERE STATUS = ? ORDER BY ORDEMEXIBICAO";

			ps = con.prepareStatement(querySQL);
			ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
			rs = ps.executeQuery();
			
			while (rs.next()){
				Regiao paramRegiao = new Regiao();
				paramRegiao.setPkRegiao(rs.getInt("PKREGIAO"));
				paramRegiao.setCodigo(rs.getString("CODIGO"));
				paramRegiao.setDescricao(rs.getString("DESCRICAO"));
				paramRegiao.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
				
				listaRegiao.add(paramRegiao);
			}
			
			return listaRegiao;
		}
		
		/**
		 * Busca uma regiao pelos parâmetros passados
		 * @author João Paulo
		 * @param pkRegiao
		 * @return Regiao
		 * @throws SQLException
		 */
		public Regiao buscarRegiao(Integer pkRegiao) throws SQLException
		{
			Regiao regiao = null;
			String querySQL = "SELECT * FROM Regiao "
					+ " WHERE status = ?"
					+ " AND pkRegiao = ?";
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
			ps.setInt(2, pkRegiao);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				regiao = new Regiao();
				regiao.setPkRegiao(rs.getInt("pkRegiao"));
				regiao.setCodigo(rs.getString("codigo"));
				regiao.setDescricao(rs.getString("descricao"));
				regiao.setStatus(rs.getInt("status"));
				regiao.setOrdemExibicao(rs.getInt("ordemExibicao"));
				
				return regiao;
			}
			
			return null;
		}
}

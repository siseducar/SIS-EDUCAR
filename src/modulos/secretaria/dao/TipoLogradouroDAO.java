package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.TipoLogradouro;
import sisEdcuar.conexaoBanco.ConectaBanco;

public class TipoLogradouroDAO {

	// Realizando conexão com o banco
		ConectaBanco conexao = new ConectaBanco();
		Connection con = conexao.getConection();
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		/* Metodo para retornar os tipos de LOGRADOUROS padrões */
		public List<TipoLogradouro> consultaTipoLogradouro() throws SQLException {
			
			List<TipoLogradouro> listaTipoLogradouro = new ArrayList<>();
			
			String querySQL = "SELECT * FROM TIPOLOGRADOURO WHERE STATUS = 0 ORDER BY ORDEMEXIBICAO";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(querySQL);
			
			while(rs.next()) {
				TipoLogradouro paramTipoLogradouro = new TipoLogradouro();
				paramTipoLogradouro.setPkTipoLogradouro(rs.getInt("PKTIPOLOGRADOURO"));
				paramTipoLogradouro.setCodigo(rs.getString("CODIGO"));
				paramTipoLogradouro.setDescricao(rs.getString("DESCRICAO"));
				paramTipoLogradouro.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
				
				listaTipoLogradouro.add(paramTipoLogradouro);
			}
			
			return listaTipoLogradouro;
		}
}

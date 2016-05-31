package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.TipoDeficiencia;
import modulos.sisEducar.conexaoBanco.ConectaBanco;

public class TipoDeficienciaDAO {

	// Realizando conexão com o banco
		ConectaBanco conexao = new ConectaBanco();
		Connection con = conexao.getConection();
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		/* Metodo para retornar os tipos de DEFICIENCIAS padrões */
		public List<TipoDeficiencia> consultaTipoDeficiencia() throws SQLException {
			
			List<TipoDeficiencia> listaTipoDeficiencia = new ArrayList<>();
			
			String querySQL = "SELECT * FROM TIPODEFICIENCIA WHERE STATUS = 0 ORDER BY ORDEMEXIBICAO";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(querySQL);
			
			while(rs.next()) {
				TipoDeficiencia paramTipoDeficiencia = new TipoDeficiencia();
				paramTipoDeficiencia.setPkTipoDeficiencia(rs.getInt("PKTIPODEFICIENCIA"));
				paramTipoDeficiencia.setCodigo(rs.getString("CODIGO"));
				paramTipoDeficiencia.setDescricao(rs.getString("DESCRICAO"));
				paramTipoDeficiencia.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
							
				listaTipoDeficiencia.add(paramTipoDeficiencia);
			}
			
			return listaTipoDeficiencia;
		}
}

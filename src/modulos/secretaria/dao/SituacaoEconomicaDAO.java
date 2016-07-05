package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.SituacaoEconomica;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import sisEdcuar.utils.ConstantesSisEducar;

public class SituacaoEconomicaDAO {

	// Realizando conexão com o banco
		ConectaBanco conexao = new ConectaBanco();
		Connection con = conexao.getConection();
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		/* Metodo para retornar os tipos de SITUACOES ECONOMICAS padrões */
		public List<SituacaoEconomica> consultaSituEconomica() throws SQLException{
			
			List<SituacaoEconomica> listaSituEconomica = new ArrayList<SituacaoEconomica>();
			
			String querySQL = "SELECT * FROM SITUACAOECONOMICA WHERE STATUS = ? ORDER BY ORDEMEXIBICAO";

			ps = con.prepareStatement(querySQL);
			ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
			rs = ps.executeQuery();
			
			while (rs.next()){
				SituacaoEconomica paramSituEconomcia = new SituacaoEconomica();
				paramSituEconomcia.setPkSituacaoEconomica(rs.getInt("PKSITUACAOECONOMICA"));
				paramSituEconomcia.setCodigo(rs.getString("CODIGO"));
				paramSituEconomcia.setDescricao(rs.getString("DESCRICAO"));
				paramSituEconomcia.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
				
				listaSituEconomica.add(paramSituEconomcia);
			}
			
			return listaSituEconomica;
		}
}

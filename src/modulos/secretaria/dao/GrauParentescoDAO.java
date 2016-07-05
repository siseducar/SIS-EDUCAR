package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.GrauParentesco;
import sisEdcuar.conexaoBanco.ConectaBanco;
import sisEdcuar.utils.ConstantesSisEducar;

public class GrauParentescoDAO {

	// Realizando conexão com o banco
		ConectaBanco conexao = new ConectaBanco();
		Connection con = conexao.getConection();
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		/* Metodo para retornar os tipos de RACAS padrões */
		public List<GrauParentesco> consultaGrau () throws SQLException {
			List<GrauParentesco> listaGrauParentesco = new ArrayList<GrauParentesco>();
			
			String querySQL = "SELECT * FROM GRAUPARENTESCO WHERE STATUS = ? ORDER BY ORDEMEXIBICAO";
			
			ps = con.prepareStatement(querySQL.toString());
			ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()){
				GrauParentesco paramGrauParentesco = new GrauParentesco();
				paramGrauParentesco.setPkGrauParentesco(rs.getInt("PKGRAUPARENTESCO"));
				paramGrauParentesco.setCodigo(rs.getString("CODIGO"));
				paramGrauParentesco.setDescricao(rs.getString("DESCRICAO"));
				paramGrauParentesco.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
				
				listaGrauParentesco.add(paramGrauParentesco);
			}
			return listaGrauParentesco;
		}
}

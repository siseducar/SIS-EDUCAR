package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Religiao;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class ReligiaoDAO extends SisEducarDAO {

	// Realizando conexão com o banco
			ConectaBanco conexao = new ConectaBanco();
			Connection con = conexao.getConection();
			Statement st = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			/* Metodo para retornar os tipos de RELIGIOES padrões */
			public List<Religiao> consultaReligiao() throws SQLException {
				
				List<Religiao> listaReligiao = new ArrayList<>();
				
				String querySQL = "SELECT * FROM RELIGIAO WHERE STATUS = ? ORDER BY ORDEMEXIBICAO ";

				ps = con.prepareStatement(querySQL);
				ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					Religiao paramReligiao = new Religiao();
					paramReligiao.setPkReligiao(rs.getInt("PKRELIGIAO"));
					paramReligiao.setCodigo(rs.getString("CODIGO"));
					paramReligiao.setDescricao(rs.getString("DESCRICAO"));
					paramReligiao.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
					
					listaReligiao.add(paramReligiao);
				}
				
				fecharConexaoBanco(con, ps, true, false);
				
				return listaReligiao;
			}
}

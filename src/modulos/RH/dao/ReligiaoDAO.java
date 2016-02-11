package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.RH.om.Religiao;
import modulos.sisEducar.conexaoBanco.ConectaBanco;

public class ReligiaoDAO {

	// Realizando conexão com o banco
			ConectaBanco conexao = new ConectaBanco();
			Connection con = conexao.getConection();
			Statement st = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			/* Metodo para retornar os tipos de RELIGIOES padrões */
			public List<Religiao> consultaReligiao() throws SQLException {
				
				List<Religiao> listaReligiao = new ArrayList<>();
				
				String querySQL = "SELECT * FROM RELIGIAO ORDER BY ORDEMEXIBICAO";
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(querySQL);
				
				while(rs.next()) {
					Religiao paramReligiao = new Religiao();
					paramReligiao.setPkReligiao(rs.getInt("PKRELIGIAO"));
					paramReligiao.setCodigo(rs.getString("CODIGO"));
					paramReligiao.setDescricao(rs.getString("DESCRICAO"));
					paramReligiao.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
					
					listaReligiao.add(paramReligiao);
				}
				
				return listaReligiao;
			}
}

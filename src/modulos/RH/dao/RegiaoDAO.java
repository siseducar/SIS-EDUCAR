package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.RH.om.Raca;
import modulos.RH.om.Regiao;
import modulos.sisEducar.conexaoBanco.ConectaBanco;

public class RegiaoDAO {

	// Realizando conex�o com o banco
		ConectaBanco conexao = new ConectaBanco();
		Connection con = conexao.getConection();
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		/* Metodo para retornar os tipos de cores padrao */
		public List<Regiao> consultaRegiao() throws SQLException{
			
			List<Regiao> listaRegiao = new ArrayList<Regiao>();
			
			String querySQL = "SELECT * FROM REGIAO ORDER BY ORDEMEXIBICAO";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(querySQL);
			
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
}
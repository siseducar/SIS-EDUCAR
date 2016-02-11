package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.RH.om.Raca;
import modulos.sisEducar.conexaoBanco.ConectaBanco;

public class RacaDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os tipos de RACAS padrões */
	public List<Raca> consultaRaca() throws SQLException{
		
		List<Raca> listaRaca = new ArrayList<Raca>();
		
		String querySQL = "SELECT * FROM RACA ORDER BY ORDEMEXIBICAO";
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(querySQL);
		
		while (rs.next()){
			Raca paramRaca = new Raca();
			paramRaca.setPkRaca(rs.getInt("PKRACA"));
			paramRaca.setCodigo(rs.getString("CODIGO"));
			paramRaca.setDescricao(rs.getString("DESCRICAO"));
			paramRaca.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
			
			listaRaca.add(paramRaca);
		}
		
		return listaRaca;
	}

}

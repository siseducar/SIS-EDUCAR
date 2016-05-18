package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Raca;
import sisEdcuar.conexaoBanco.ConectaBanco;

public class RacaDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os tipos de RACAS padrões */
	public List<Raca> consultaRaca() throws SQLException {
		List<Raca> listaRaca = new ArrayList<Raca>();
		
		String querySQL = "SELECT * FROM RACA WHERE STATUS = 0 ORDER BY ORDEMEXIBICAO";
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
	
	public Raca consultaRacaEspecifica(String valor) throws SQLException{
		Raca paramRaca = new Raca();
		String querySQL = "SELECT * FROM RACA WHERE PKRACA = ? ORDER BY ORDEMEXIBICAO";
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, valor);

		ResultSet rs = ps.executeQuery(querySQL);
		
		while (rs.next()){
			paramRaca.setPkRaca(rs.getInt("PKRACA"));
			paramRaca.setCodigo(rs.getString("CODIGO"));
			paramRaca.setDescricao(rs.getString("DESCRICAO"));
			paramRaca.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
			
		}		
		return paramRaca;
	}
}

package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.RH.om.Cargo;
import modulos.RH.om.Religiao;
import modulos.sisEducar.conexaoBanco.ConectaBanco;

public class CargoDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os tipos de RELIGIAO padrao */
	public List<Cargo> consultaCargo() throws SQLException {
		
		List<Cargo> listaCargo = new ArrayList<>();
		
		String querySQL = "SELECT * FROM CARGO ORDER BY ORDEMEXIBICAO";
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(querySQL);
		
		while(rs.next()) {
			Cargo paramCargo = new Cargo();
			paramCargo.setPkCargo(rs.getInt("PKCARGO"));
			paramCargo.setCodigo(rs.getString("CODIGO"));
			paramCargo.setDescricao(rs.getString("NOME"));
						
			listaCargo.add(paramCargo);
		}
		
		return listaCargo;
	}
}

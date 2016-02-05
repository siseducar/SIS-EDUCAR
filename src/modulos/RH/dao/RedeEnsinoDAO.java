package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.RH.om.Raca;
import modulos.RH.om.RedeEnsino;
import modulos.sisEducar.conexaoBanco.ConectaBanco;

public class RedeEnsinoDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os tipos de cores padrao */
	public List<RedeEnsino> consultaRedeEnsino() throws SQLException{
		
		List<RedeEnsino> listaRedeEnsino = new ArrayList<RedeEnsino>();
		
		String querySQL = "SELECT * FROM REDEENSINO ORDER BY NOME";
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(querySQL);
		
		while (rs.next()){
			RedeEnsino paramRedeEnsino = new RedeEnsino();
			paramRedeEnsino.setPkRedeEnsino(rs.getInt("PKREDEENSINO"));
			paramRedeEnsino.setNome(rs.getString("NOME"));
			
			listaRedeEnsino.add(paramRedeEnsino);
		}
		
		return listaRedeEnsino;
	}
}

package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.RedeEnsino;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class RedeEnsinoDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os tipos de REDES DE ENSINO padrões */
	public List<RedeEnsino> consultaRedeEnsino() throws SQLException{
		
		List<RedeEnsino> listaRedeEnsino = new ArrayList<RedeEnsino>();
		
		String querySQL = "SELECT * FROM REDEENSINO WHERE STATUS = ? ORDER BY ORDEMEXIBICAO";
		
		ps = con.prepareStatement(querySQL);
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		rs = ps.executeQuery();
		
		while (rs.next()){
			RedeEnsino paramRedeEnsino = new RedeEnsino();
			paramRedeEnsino.setPkRedeEnsino(rs.getInt("PKREDEENSINO"));
			paramRedeEnsino.setNome(rs.getString("NOME"));
			
			listaRedeEnsino.add(paramRedeEnsino);
		}
		
		return listaRedeEnsino;
	}
}

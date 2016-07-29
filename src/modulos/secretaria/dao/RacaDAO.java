package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Raca;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class RacaDAO  extends SisEducarDAO{

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os tipos de RACAS padrões */
	public List<Raca> consultaRaca() throws SQLException {
		List<Raca> listaRaca = new ArrayList<Raca>();
		
		String querySQL = "SELECT * FROM RACA WHERE STATUS = ? ORDER BY ORDEMEXIBICAO";

		ps = con.prepareStatement(querySQL);
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		rs = ps.executeQuery();
		
		while (rs.next()){
			Raca paramRaca = new Raca();
			paramRaca.setPkRaca(rs.getInt("PKRACA"));
			paramRaca.setCodigo(rs.getString("CODIGO"));
			paramRaca.setDescricao(rs.getString("DESCRICAO"));
			paramRaca.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
			
			listaRaca.add(paramRaca);
		}
		
		fecharConexaoBanco(con, ps, true, false);
		
		return listaRaca;
	}
}

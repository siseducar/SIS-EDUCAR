package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.GrauInstrucao;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import sisEdcuar.utils.ConstantesSisEducar;

public class GrauInstrucaoDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os tipos de GRAUS DE INSTRUCOES padrões */
	public List<GrauInstrucao> consultaGrauInstru() throws SQLException {
		
		List<GrauInstrucao> listaGrauInstru = new ArrayList<>();
		
		String querySQL = "SELECT * FROM GRAUINSTRUCAO WHERE STATUS = ? ORDER BY ORDEMEXIBICAO";
		
		ps = con.prepareStatement(querySQL.toString());
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			GrauInstrucao paramGrauInstru = new GrauInstrucao();
			paramGrauInstru.setPkGrauInstrucao(rs.getInt("PKGRAUINSTRUCAO"));
			paramGrauInstru.setCodigo(rs.getString("CODIGO"));
			paramGrauInstru.setDescricao(rs.getString("DESCRICAO"));
			paramGrauInstru.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
			
			listaGrauInstru.add(paramGrauInstru);
		}
		
		return listaGrauInstru;
	}
}

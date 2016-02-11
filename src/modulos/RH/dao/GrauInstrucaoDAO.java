package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.RH.om.GrauInstrucao;
import modulos.sisEducar.conexaoBanco.ConectaBanco;

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
		
		String querySQL = "SELECT * FROM GRAUINSTRUCAO ORDER BY ORDEMEXIBICAO";
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(querySQL);
		
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

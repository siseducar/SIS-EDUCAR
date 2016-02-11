package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.RH.om.Nacionalidade;
import modulos.sisEducar.conexaoBanco.ConectaBanco;

public class NacionalidadeDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
			
	/* Método para retornar os tipos de NACIONALIDADES padrões */
	public List<Nacionalidade> consultaNacionalidade() throws SQLException {
		
		List<Nacionalidade> listaNacionalidade = new ArrayList<>();
		
		String querySQL = "SELECT * FROM NACIONALIDADE ORDER BY ORDEMEXIBICAO";
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(querySQL);
		
		while(rs.next()) {
			Nacionalidade paramNacionalidade = new Nacionalidade();
			paramNacionalidade.setPkNacionalidade(rs.getInt("PKNACIONALIDADE"));
			paramNacionalidade.setCodigo(rs.getString("CODIGO"));
			paramNacionalidade.setDescricao(rs.getString("DESCRICAO"));
			paramNacionalidade.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
			
			listaNacionalidade.add(paramNacionalidade);
		}
		
		return listaNacionalidade;
	}
}

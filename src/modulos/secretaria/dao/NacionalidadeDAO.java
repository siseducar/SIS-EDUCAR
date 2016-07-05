package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Nacionalidade;
import sisEdcuar.conexaoBanco.ConectaBanco;
import sisEdcuar.utils.ConstantesSisEducar;

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
		
		String querySQL = "SELECT * FROM NACIONALIDADE WHERE STATUS = ? ORDER BY ORDEMEXIBICAO";

		ps = con.prepareStatement(querySQL);
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		rs = ps.executeQuery();
		
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

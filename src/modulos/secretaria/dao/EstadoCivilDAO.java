package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.EstadoCivil;
import sisEdcuar.conexaoBanco.ConectaBanco;
import sisEdcuar.utils.ConstantesSisEducar;

public class EstadoCivilDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Método para retornar os tipos de ESTADO CIVIL padrões */
	public List<EstadoCivil> consultaEstaCivil() throws SQLException{
		
		List<EstadoCivil> listaEstaCivil = new ArrayList<>();
		
		String querySQL = "SELECT * FROM ESTADOCIVIL WHERE STATUS = ? ORDER BY ORDEMEXIBICAO";
		
		ps = con.prepareStatement(querySQL.toString());
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			EstadoCivil paramEstCivil = new EstadoCivil();
			paramEstCivil.setPkEstadoCivil(rs.getInt("PKESTADOCIVIL"));
			paramEstCivil.setCodigo(rs.getString("CODIGO"));
			paramEstCivil.setDescricao(rs.getString("DESCRICAO"));
			paramEstCivil.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
			
			listaEstaCivil.add(paramEstCivil);
		}
		
		return listaEstaCivil;
	}
}

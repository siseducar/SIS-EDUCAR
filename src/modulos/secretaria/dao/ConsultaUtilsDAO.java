package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;

public class ConsultaUtilsDAO extends SisEducarDAO {
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public Integer consultaPK(String tabelaConsulta) throws SQLException{
		try{
			String querySQL=
					"SELECT * FROM F_GERASEQUENCIA ('?') AS PKTABELA";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setString(1, tabelaConsulta);

			ResultSet rs = ps.executeQuery(querySQL);
			
			while(rs.next()){
				
			}
						
			return 0;
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

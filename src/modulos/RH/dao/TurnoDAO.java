package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.RH.om.Turno;
import modulos.sisEducar.conexaoBanco.ConectaBanco;

public class TurnoDAO {

	// Realizando conexão com o banco
		ConectaBanco conexao = new ConectaBanco();
		Connection con = conexao.getConection();
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		/* Metodo para retornar os tipos de TURNOS padrões */
		public List<Turno> consultaTurno() throws SQLException{
			
			List<Turno> listaTurno = new ArrayList<Turno>();
			
			String querySQL = "SELECT * FROM TURNO ORDER BY ORDEMEXIBICAO";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(querySQL);
			
			while (rs.next()){
				Turno paramTurno = new Turno();

				paramTurno.setDescricao(rs.getString("DESCRICAO"));
				paramTurno.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
				
				listaTurno.add(paramTurno);
			}
			
			return listaTurno;
		}
}

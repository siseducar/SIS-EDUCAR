package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Turno;
import sisEdcuar.conexaoBanco.ConectaBanco;
import sisEdcuar.utils.ConstantesSisEducar;

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
			
			String querySQL = "SELECT * FROM TURNO WHERE STATUS = ? ORDER BY ORDEMEXIBICAO";
			
			ps = con.prepareStatement(querySQL);
			ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
			
			rs = ps.executeQuery();
			
			while (rs.next()){
				Turno paramTurno = new Turno();

				paramTurno.setDescricao(rs.getString("DESCRICAO"));
				paramTurno.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
				
				listaTurno.add(paramTurno);
			}
			
			return listaTurno;
		}
}

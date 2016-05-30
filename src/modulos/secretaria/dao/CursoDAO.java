package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Curso;
import sisEdcuar.conexaoBanco.ConectaBanco;
import sisEdcuar.utils.ConstantesSisEducar;

public class CursoDAO {
	// Realizando conexão com o banco
			ConectaBanco conexao = new ConectaBanco();
			Connection con = conexao.getConection();
			Statement st = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			/* Metodo para retornar os tipos de RACAS padrões */
			public List<Curso> consultaCursoEscolar(Integer pkUnidadeEscolar) throws SQLException {
				
				List<Curso> listaCursoEscolar = new ArrayList<Curso>();
				String querySQL = "SELECT * FROM CURSO WHERE FKUNIDADEESCOLAR = ? AND STATUS = ? ORDER BY NOME";
				
				ps = con.prepareStatement(querySQL);
				
				ps.setInt(1, pkUnidadeEscolar);
				ps.setInt(2, ConstantesSisEducar.STATUS_ATIVO);
				
				rs = ps.executeQuery();
				
				while (rs.next()){
					Curso paramCurso = new Curso();
					paramCurso.setPkCurso(rs.getInt("PKCURSO"));
					paramCurso.setDescricao(rs.getString("NOME"));
					
					listaCursoEscolar.add(paramCurso);
				}
				
				return listaCursoEscolar;
			}
}

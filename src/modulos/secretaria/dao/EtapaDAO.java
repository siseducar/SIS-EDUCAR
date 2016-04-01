package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Etapa;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class EtapaDAO {
	// Realizando conexão com o banco
		ConectaBanco conexao = new ConectaBanco();
		Connection con = conexao.getConection();
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		/* Metodo para retornar os tipos de RACAS padrões */
		public List<Etapa> consultaEtapaEscolar(Integer pkCurso) throws SQLException {
			
			List<Etapa> listaEtapaEscolar = new ArrayList<Etapa>();
			String querySQL = "SELECT * FROM ETAPA WHERE FKCURSO = ? AND STATUS = ? ORDER BY NOME";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(1, pkCurso);
			ps.setInt(2, ConstantesSisEducar.STATUS_ATIVO);
			
			rs = ps.executeQuery();
			
			while (rs.next()){
				Etapa paramEtapa = new Etapa();
				paramEtapa.setPkEtapa(rs.getInt("PKETAPA"));
				paramEtapa.setDescricao(rs.getString("NOME"));
				
				listaEtapaEscolar.add(paramEtapa);
			}
			
			return listaEtapaEscolar;
		}
}

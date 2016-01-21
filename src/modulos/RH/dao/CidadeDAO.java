package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.RH.om.Cidade;
import modulos.RH.om.Estado;
import modulos.RH.om.Pais;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class CidadeDAO extends SisEducarDAO
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	/**
	 * O método busca o objeto cidade completo com estado e pais
	 * @author João Paulo
	 * @param sigla
	 * @param nome
	 * @return Cidade
	 * @throws SQLException
	 */
	public Cidade obtemCidade(String sigla, String nome) throws SQLException
	{
		Cidade cidade = null;
		
		String querySQL = "SELECT * FROM cidade"
				+ " WHERE status = ?";
		
		if(sigla!=null && sigla.length() >0) { querySQL += " AND sigla = ?"; }
		if(nome!=null && nome.length() >0)	 { querySQL += " AND nome = ?"; }
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setString(2, sigla);
		ps.setString(3, nome);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			cidade = new Cidade();
			cidade.setPkEstado(rs.getInt("pkcidade"));
			cidade.setNome(rs.getString("nome"));
			cidade.setSigla(rs.getString("sigla"));
			cidade.setCodigoibge(rs.getInt("codigoibge"));
			cidade.setStatus(rs.getInt("status"));
			cidade.setEstado(new EstadoDAO().obtemEstado(rs.getInt("fkstado"), null, null));
			
			return cidade;
		}
		
		return null;
	}
}

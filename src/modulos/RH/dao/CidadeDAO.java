package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.RH.om.Cidade;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class CidadeDAO extends SisEducarDAO
{
    //Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	Cidade cidade;
	List<Cidade> listaCidades = null;
	 
	public CidadeDAO() throws SQLException
	{
		//Quando construir a classe, deverá desabilitar o autoCommit
		desabilitarAutoCommit(con);
	}
	
	/**
	 * Retorna todos os registros da tabela cidade
	 * @return lista de pessoa
	 * @throws SQLException
	 */
	public List<Cidade> obtemTodos() throws SQLException
	{
		listaCidades = new ArrayList<Cidade>();
		
		String querySQL = "SELECT * FROM Cidade" + " WHERE status = ?";
		
		PreparedStatement ps = con.prepareStatement(querySQL);
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			cidade = new Cidade();
			cidade.setNome(rs.getString("nome"));
			cidade.setCodigoIBGE(rs.getInt("codigoIBGE"));
			cidade.setSigla(rs.getString("sigla"));
			cidade.setStatus(rs.getInt("status"));
			
			listaCidades.add(cidade);
		}
		
		fecharConexaoBanco(con, null, true, false);
		return listaCidades;
	}
}

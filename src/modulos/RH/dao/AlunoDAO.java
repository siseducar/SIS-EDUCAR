package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.RH.om.Aluno;
import modulos.RH.om.Pessoa;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class AlunoDAO extends SisEducarDAO
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	/**
	 * Este método é usado para verificar a existência de um aluno no banco de dados, sendo buscado pelo próprio RA
	 * @param raAluno
	 * @return TRUE || FALSE
	 * @throws SQLException
	 */
	public Boolean verificaExistenciaAluno(String raAluno) throws SQLException
	{
		String querySQL = "SELECT p.pkPessoa, a.pkAluno, p.nome, a.ra FROM Pessoa p"
				+ " LEFT OUTER JOIN Aluno a ON(a.fkPessoa = p.pkPessoa)"
				+ " WHERE p.status = ?"
				+ " AND a.ra = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1 , ConstantesSisEducar.STATUS_ATIVO);
		ps.setString(2, raAluno);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) 
		{
			return true;
		}
		
		return false;
	}
	
	public Boolean inserirAluno(Aluno aluno) throws SQLException 
	{
		String querySQL = "INSERT INTO aluno "
				+ " (ra, fkPessoa) "
				+ " values(?,?)";
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, aluno.getRa());
		ps.setInt(2, aluno.getPessoa().getPkPessoa());
		
		fecharConexaoBanco(con, ps, false, true);
		
		return true;
	}
}

package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sisEdcuar.conexaoBanco.ConectaBanco;
import sisEdcuar.dao.SisEducarDAO;
import sisEdcuar.utils.ConstantesSisEducar;

public class AlunoDAO extends SisEducarDAO
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public AlunoDAO() throws SQLException {
		desabilitarAutoCommit(con);
	}
	
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
	
	/*
	 * Metodo responsavel por validar responsavel existente
	 * */
	public String consultaNomeResponsavel(Long cpf){
		try {
			
			String nomePessoa = null;
			String querySQL = "SELECT NOME FROM PESSOA WHERE CPF = ?";
			
			ps = con.prepareStatement(querySQL);
			ps.setString(1, cpf.toString());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				nomePessoa = rs.getString("NOME");
			}
			
			return nomePessoa;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

}

package modulos.RH.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.RH.om.Usuario;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class UsuarioDAO extends SisEducarDAO
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	Date dataAtual = new Date(System.currentTimeMillis());
	
	public UsuarioDAO() throws SQLException
	{
		//Quando construir a classe, deverá desabilitar o autoCommit
		desabilitarAutoCommit(con);
	}
	
	/**
	 * Faz uma busca no banco para verificar se existe um usuario com o mesmo nome que o usuario esta tentando cadastrar
	 * @param usuario
	 * @return
	 * @throws SQLException
	 */
	public Boolean existeUsuario(Usuario usuario) throws SQLException
	{
		String querySQL = "SELECT * FROM Usuario WHERE nome = ? AND status = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, usuario.getNome());
		ps.setInt(2 , ConstantesSisEducar.STATUS_ATIVO);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) 
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * O método é usado para inserir um novo usuário no banco de dados
	 * @param loginBean
	 * @return
	 * @throws SQLException 
	 */
	public Boolean inserirUsuario(Usuario usuario) throws SQLException 
	{
		String querySQL = "INSERT INTO usuario "
				+ " (nome, senha, dataLancamento,  tipo, email) values(?,?,?,?,?)";
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, usuario.getNome());
		ps.setString(2, usuario.getSenha());
		ps.setDate(3, dataAtual);
		ps.setInt(4, usuario.getTipo());
		ps.setString(5, usuario.getEmail());
		
		//Depois que terminar o cadastro de pessoa, remover esta linha e adicionar corretamente a pessoa
		
		fecharConexaoBanco(con, ps, true, true);
		
		return true;
	}
	
	/**
	 * O método é usado para consultar o banco e verificar se existe o usu
	 * 
	 * @param loginBean
	 * @return
	 * @throws SQLException 
	 */
	public Boolean validarUsuario(Usuario usuario) throws SQLException
	{
		String querySQL = "SELECT * FROM usuario "
				+ " WHERE nome = ?"
				+ " AND senha = ?"
				+ " AND status = ?";
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, usuario.getNome());
		ps.setString(2, usuario.getSenha());
		ps.setInt(3, ConstantesSisEducar.STATUS_ATIVO);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			usuario.setNome(rs.getString("nome"));
			usuario.setSenha(rs.getString("senha"));
			
			fecharConexaoBanco(con, ps, true, false);
			return true;
		}
		
		return false;
	}
	
	/**
	 * O método é usado para buscar a existencia do usuário pelo cpf do mesmo
	 * @param cpf
	 * @return TRUE || FALSE
	 * @throws SQLException
	 */
	public Boolean verificaExistenciaUsuario(String cpf) throws SQLException
	{
		String querySQL = "SELECT r.* FROM Responsavel r" +
					" INNER JOIN Pessoa p ON(r.fkPessoa = p.pkPessoa)" +
					" INNER JOIN Aluno a ON(r.fkAluno = a.pkAluno)";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1 , ConstantesSisEducar.STATUS_ATIVO);
		ps.setString(2, cpf);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) 
		{
			return true;
		}
		
		return false;
	}
}

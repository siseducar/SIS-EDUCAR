package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Usuario;
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
	public Boolean inserirUsuario(Usuario usuario)
	{
		try 
		{
			String querySQL = "INSERT INTO usuario "
					+ " (nome, senha, dataLancamento,  tipo, email, status, cpfcnpj, genero) values(?,?,?,?,?,?,?,?)";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getSenha());
			ps.setDate(3, dataAtual);
			ps.setInt(4, usuario.getTipo());
			ps.setString(5, usuario.getEmail());
			ps.setInt(6, ConstantesSisEducar.STATUS_INCOMPLETO);
			ps.setString(7, usuario.getCpfcnpj());
			ps.setString(8, usuario.getGenero());
			
			fecharConexaoBanco(con, ps, false, true);
			return true;
		} 
		catch (SQLException e) 
		{
			System.out.println(e);
			return false;
		}
	}
	
	/**
	 * Atualiza o usuário com as novas informações
	 * @author João Paulo
	 * @param usuario
	 * @return Boolean
	 */
	public Boolean atualizarUsuario(Usuario usuario)
	{
		try 
		{
			String querySQL = "UPDATE usuario "
					+ " SET (nome, senha, email) = (?,?,?) WHERE pkusuario = CAST(? as bigint) ";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getSenha());
			ps.setString(3, usuario.getEmail());
			ps.setString(4, usuario.getPkUsuario());
			
			fecharConexaoBanco(con, ps, false, true);
			return true;
		} 
		catch (SQLException e) 
		{
			System.out.println(e);
			return false;
		}
	}
	
	/**
	 * O método é usado para consultar o banco e verificar se existe o usuário
	 * 
	 * @param loginBean
	 * @return
	 * @throws SQLException 
	 */
	public Usuario validarUsuario(Usuario usuario) throws SQLException
	{
		Cidade municipioCliente = null;
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
			municipioCliente = new Cidade();
			municipioCliente.setPkCidade(rs.getInt("fkMunicipioCliente"));
			
			usuario.setPkUsuario(rs.getString("pkusuario"));
			usuario.setNome(rs.getString("nome"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setGenero(rs.getString("genero"));
			usuario.setEmail(rs.getString("email"));
			usuario.setCpfcnpj(rs.getString("cpfcnpj"));
			usuario.setFkMunicipioCliente(municipioCliente);
			
			return usuario;
		}
		
		return null;
	}
	
	/**
	 * Método responsável por buscar um usuário completo a partir de suas informações básicas (nome, senha e status)
	 * @author João Paulo
	 * @param usuario
	 * @return Usuario
	 * @throws SQLException
	 */
	public Usuario buscarUsuario(Usuario usuario) throws SQLException
	{
		String querySQL = "SELECT * FROM usuario "
				+ " WHERE nome = ?"
				+ " AND senha = ?"
				+ " AND status = ?";
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, usuario.getNome());
		ps.setString(2, usuario.getSenha());
		ps.setInt(3, usuario.getStatus());
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			usuario.setPkUsuario(rs.getString("pkusuario"));
			usuario.setNome(rs.getString("nome"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setGenero(rs.getString("genero"));
			usuario.setEmail(rs.getString("email"));
			usuario.setCpfcnpj(rs.getString("cpfcnpj"));			
			
			return usuario;
		}
		
		return null;
	}
	
	/**
	 * Este método será responsável por remover um usuário
	 * @author João Paulo
	 * @param usuario
	 * @return TRUE || FALSE
	 * @throws SQLException
	 */
	public Boolean removerUsuario(Usuario usuario) throws SQLException
	{
		String querySQL = "UPDATE usuario "
				+ " SET status = ?"
				+ " WHERE pkUsuario = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_REMOVIDO);
		ps.setString(2, usuario.getPkUsuario());
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
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
	public Boolean verificaExistenciaResponsavel(String cpfcnpj) throws SQLException
	{
		String querySQL = "SELECT pr.pkPessoaResponsavel, pe.pkPessoa, a.pkAluno, " +
								" CASE WHEN pe.nome IS NOT NULL " +
									" THEN pe.nome " +
									" ELSE pe.nomefantasia " +
								" END as pessoaNome " +
							" FROM PessoaResponsavel pr " +
							" JOIN Pessoa pe ON(pr.fkPessoa = pe.pkPessoa)" +
							" JOIN Aluno a ON(pr.fkAluno = a.pkAluno)" +
							" WHERE pe.status = ?" +
							" AND pe.cpf = ? OR pe.cnpj = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1 , ConstantesSisEducar.STATUS_ATIVO);
		ps.setString(2, cpfcnpj);
		ps.setString(3, cpfcnpj);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) 
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Métoso usado para verificar se o usuário existe
	 * @author João Paulo
	 * @param cpf
	 * @return TRUE || FALSE
	 * @throws SQLException
	 */
	public Boolean verificaExistenciaUsuario(String cpf) throws SQLException
	{
		String querySQL = "SELECT * FROM Usuario" +
				" WHERE status <> ?" + 
				" AND cpfcnpj = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1 , ConstantesSisEducar.STATUS_REMOVIDO);
		ps.setString(2, cpf);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) 
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Método usado para obter um usuario, pode ser adicionado mais parametros neste método, so deve ser verificado aonde esta usando este metodo
	 * para que nas chamadas do mesmo sejam passados os novos parametros
	 * @param email
	 * @param status
	 * @return {@link} Usuario
	 * @throws SQLException
	 */
	public Usuario obtemUsuario(String email, Integer status) throws SQLException
	{
		Usuario usuario = new Usuario();
		String querySQL = "SELECT * FROM Usuario "
				+ " WHERE email = ?"
				+ " AND status = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, email);
		ps.setInt(2 , status);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) 
		{
			usuario.setPkUsuario(rs.getString("pkUsuario"));
			usuario.setNome(rs.getString("nome"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setEmail(rs.getString("email"));
			usuario.setDataLancamento(rs.getDate("dataLancamento"));
			usuario.setCpfcnpj(rs.getString("cpfcnpj"));
			usuario.setStatus(rs.getInt("status"));
			usuario.setTipo(rs.getInt("tipo"));
			
			return usuario;
		}
		
		return null;
	}
	
	/**
	 * Método usado para atualizar o usuario que esta com o status incompleto
	 * @param pkUsuario
	 * @return TRUE || FALSE
	 * @throws SQLException
	 */
	public Boolean atualizarUsuarioIncompleto(String pkUsuario) throws SQLException
	{
		String querySQL = "UPDATE Usuario "
				+ " SET status = ?"
				+ " WHERE pkUsuario = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setInt(2 , Integer.parseInt(pkUsuario));
		ps.execute();
		
		fecharConexaoBanco(con, ps, false, true);

		return true;
	}
	
	/**
	 * O metodo é usado para atualizar o usuario quando for enviado um email de redefinicao de senha para o mesmo
	 * @param pkUsuario
	 * @param status (ConstantesSisEducar.STATUS_REDEFINICAO_SENHA_LIBERADO || ConstantesSisEducar.STATUS_REDEFINICAO_SENHA_LIBERADO)
	 * @param parametro (é a string criptografada que será gerada randomicamente)
	 * @return true || false
	 * @throws SQLException
	 */
	public Boolean atualizarUsuarioStatusRedefinicaoSenha(String pkUsuario, int status, String parametro) throws SQLException
	{
		String querySQL = "UPDATE Usuario "
				+ " SET (statusRedeficaoSenha, ultimaRedefinicao) = (?,?)"
				+ " WHERE pkUsuario = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, status);
		ps.setString(2 , parametro);
		ps.setInt(3 , Integer.parseInt(pkUsuario));
		ps.execute();
		
		fecharConexaoBanco(con, ps, false, true);
		
		return true;
	}
	
	/**
	 * O método é usado para atualizar a senha do usuario
	 * @param pkUsuario
	 * @return TRUE || FALSE
	 * @throws SQLException
	 */
	public Boolean redefinirSenha(String pkUsuario, String novaSenha) throws SQLException
	{
		String querySQL = "UPDATE Usuario "
				+ " SET senha = ?"
				+ " WHERE pkUsuario = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, novaSenha);
		ps.setInt(2 , Integer.parseInt(pkUsuario));
		ps.execute();
		
		fecharConexaoBanco(con, ps, false, true);
		
		return true;
	}
}
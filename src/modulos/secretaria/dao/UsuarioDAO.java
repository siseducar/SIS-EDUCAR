package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Permissao;
import modulos.secretaria.om.PermissaoUsuario;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.Usuario;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.om.Modulo;
import modulos.sisEducar.om.Tela;
import modulos.sisEducar.om.TipoTela;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class UsuarioDAO extends SisEducarDAO
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;	
	ResultSet rs = null;
	
	Usuario usuarioLogado = null;

	Date dataAtual = new Date(System.currentTimeMillis());
	
	public UsuarioDAO() throws SQLException
	{
		usuarioLogado = (Usuario)  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		
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
					+ " (nome, senha, dataLancamento,  tipo, email, status, cpfcnpj, genero, fkMunicipioCliente, fkPessoa, fkCidadeOrigem) values(?,?,?,?,?,?,?,?,?,?,?)";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getSenha());
			ps.setDate(3, dataAtual);
			ps.setInt(4, usuario.getTipo());
			ps.setString(5, usuario.getEmail());
			ps.setInt(6, ConstantesSisEducar.STATUS_ATIVO);
			ps.setString(7, usuario.getCpfcnpj());
			ps.setString(8, usuario.getGenero());
			ps.setObject(9, usuario.getFkMunicipioCliente()!=null ? usuario.getFkMunicipioCliente().getPkCidade() : null);
			ps.setObject(10, usuario.getPessoa()!=null ? usuario.getPessoa().getPkPessoa() : null);
			ps.setObject(11, usuario.getFkMunicipioCliente()!=null ? usuario.getFkMunicipioCliente().getPkCidade() : null);
			
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
	 * Método usuado para dar um update no usuário já existente, esse método será usado quando o usuário estiver editando um item
	 * @author João Paulo
	 * @param usuario
	 * @return Boolean
	 */
	public Boolean alterarUsuario(Usuario usuario)
	{
		try 
		{
			String querySQL = "UPDATE Usuario"
					+ " SET (nome, senha, dataLancamento,  tipo, email, status, cpfcnpj, genero, fkMunicipioCliente, fkPessoa) = "
					+ " (?,?,?,?,?,?,?,?,?,?)"
					+ " WHERE pkUsuario = CAST(? as int)";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getSenha());
			ps.setDate(3, dataAtual);
			ps.setInt(4, usuario.getTipo());
			ps.setString(5, usuario.getEmail());
			ps.setInt(6, ConstantesSisEducar.STATUS_ATIVO);
			ps.setString(7, usuario.getCpfcnpj());
			ps.setString(8, usuario.getGenero());
			ps.setObject(9, usuario.getFkMunicipioCliente()!=null ? usuario.getFkMunicipioCliente().getPkCidade() : null);
			ps.setObject(10, usuario.getPessoa()!=null ? usuario.getPessoa().getPkPessoa() : null);
			ps.setObject(11, usuario.getPkUsuario());
			
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
		Permissao permissao = null;
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
			municipioCliente = new CidadeDAO().obtemCidade(null, null, rs.getInt("FKMUNICIPIOCLIENTE"));
//			municipioCliente = new Cidade();
//			municipioCliente.setPkCidade(rs.getInt("FKMUNICIPIOCLIENTE"));
//			
			usuario.setPkUsuario(rs.getString("pkusuario"));
			usuario.setNome(rs.getString("nome"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setGenero(rs.getString("genero"));
			usuario.setEmail(rs.getString("email"));
			usuario.setCpfcnpj(rs.getString("cpfcnpj"));
			usuario.setFkMunicipioCliente(municipioCliente);
			
			usuario.setPermissoes(buscarPermissoesUsuario(usuario));
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
		try 
		{
			String querySQL = "UPDATE usuario "
					+ " SET status = ?"
					+ " WHERE pkUsuario = ? RETURNING STATUS";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(1, ConstantesSisEducar.STATUS_REMOVIDO);
			ps.setInt(2, new Integer(usuario.getPkUsuario()));
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				if(rs.getInt("STATUS") == ConstantesSisEducar.STATUS_REMOVIDO) {
					fecharConexaoBanco(con, ps, false, true);
					return true;
				} else {
					return false;					
				}
			}
			
			return false;
		} 
		catch (Exception e) 
		{
			System.out.println("removerUsuario - " + e);
			return false;
		}
	}
	
	/**
	 * USado para remover todas as permissões de um determinado usuário
	 * @author João Paulo
	 * @param usuario
	 * @return
	 * @throws SQLException
	 */
	public Boolean removerPermissoesUsuario(Usuario usuario) throws SQLException
	{
		try 
		{
			String querySQL = "UPDATE PermissaoUsuario "
					+ " SET status = ?"
					+ " WHERE fkUsuario = ? RETURNING STATUS";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(1, ConstantesSisEducar.STATUS_REMOVIDO);
			ps.setInt(2, new Integer(usuario.getPkUsuario()));
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				if(rs.getInt("STATUS") == ConstantesSisEducar.STATUS_REMOVIDO) {
					fecharConexaoBanco(con, ps, false, true);
					return true;
				} else {
					return false;					
				}
			}
			
			return false;
		} 
		catch (Exception e) 
		{
			System.out.println("removerPermissoesUsuario - " + e);
			return false;
		}
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
	public Boolean verificaExistenciaUsuario(Usuario usuario) throws SQLException
	{
		String querySQL = "SELECT * FROM Usuario" +
				" WHERE status <> ?" + 
				" AND cpfcnpj = ?" +
				" AND fkMunicipioCliente = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1 , ConstantesSisEducar.STATUS_REMOVIDO);
		ps.setString(2, usuario.getCpfcnpj());
		ps.setObject(3, usuario.getFkMunicipioCliente()!=null ? usuario.getFkMunicipioCliente().getPkCidade() : null);
		
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
	public Usuario obtemUsuario(String email, Integer status, Integer pkUsuario) throws SQLException
	{
		Integer numeroArgumentos = 1;
		Usuario usuario = new Usuario();
		String querySQL = "SELECT * FROM Usuario "
				+ " WHERE status = ?";
		
		if(email!=null)
		{
			querySQL += " AND email = ?";
		}
		
		if(pkUsuario!=null)
		{
			querySQL += " AND PKUSUARIO = ?";
		}
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(numeroArgumentos , status);
		if(email!=null)
		{
			numeroArgumentos++;
			ps.setString(numeroArgumentos, email);
		}
		
		if(pkUsuario!=null)
		{
			numeroArgumentos++;
			ps.setInt(numeroArgumentos, pkUsuario);
		}
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) 
		{
			Cidade cidade = new Cidade();
			cidade.setPkCidade(rs.getInt("fkMunicipioCliente"));
			
			usuario.setPkUsuario(rs.getString("pkUsuario"));
			usuario.setNome(rs.getString("nome"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setEmail(rs.getString("email"));
			usuario.setDataLancamento(rs.getDate("dataLancamento"));
			usuario.setCpfcnpj(rs.getString("cpfcnpj"));
			usuario.setStatus(rs.getInt("status"));
			usuario.setTipo(rs.getInt("tipo"));
			usuario.setGenero(rs.getString("genero"));
			usuario.setFkMunicipioCliente(cidade);
			usuario.setPessoa(new PessoaDAO().obtemPessoaSimples(rs.getInt("FKPESSOA")));
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
	
	/**
	 * Busca todas as permissões no sistema
	 * @author João Paulo
	 * @return List<Permissao>
	 * @throws SQLException
	 */
	public List<Permissao> buscarPermissoes(Modulo modulo, TipoTela tipoTela, Tela tela) throws SQLException
	{
		Integer numeroParametros = 1;
		Permissao permissao = null;
		List<Permissao> permissoes = new ArrayList<Permissao>();
		String querySQL = "SELECT * FROM Permissao "
				+ " WHERE status = ?";
		
		if(modulo!=null)   { querySQL += " AND tipoModuloResponsavel = ?"; }
		if(tipoTela!=null) { querySQL += " AND tipoSubMenuResponsavel = ?"; }
		if(tela!=null)     { querySQL += " AND telaResponsavel = ?"; }
		
		ps = con.prepareStatement(querySQL);
		ps.setInt(numeroParametros, ConstantesSisEducar.STATUS_ATIVO);
		
		if(modulo!=null)
		{
			numeroParametros++;
			ps.setInt(numeroParametros, modulo.getTipo());
		}
		if(tipoTela!=null)
		{
			numeroParametros++;
			ps.setInt(numeroParametros, tipoTela.getTipo());
		}
		if(tela!=null)
		{
			numeroParametros++;
			ps.setInt(numeroParametros, tela.getTipoTela());
		}
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) 
		{
			permissao = new Permissao();
			permissao.setPkPermissao(rs.getInt("pkPermissao"));
			permissao.setNome(rs.getString("nome"));
			permissao.setTipo(rs.getInt("tipo"));
			permissao.setTipoModuloResponsavel(rs.getInt("tipoModuloResponsavel"));
			permissao.setTipoSubMenuResponsavel(rs.getInt("tipoSubMenuResponsavel"));
			permissao.setTelaResponsavel(rs.getInt("telaResponsavel"));
			
			permissoes.add(permissao);
		}
		
		return permissoes;
	}
	
	/**
	 * Método usuado para buscar as permissões de um usuário
	 * @author João Paulo
	 * @param usuario
	 * @return List<Permissao>
	 * @throws SQLException
	 */
	public List<Permissao> buscarPermissoesUsuario(Usuario usuario) throws SQLException
	{
		Permissao permissao = null;
		List<Permissao> permissoes = new ArrayList<Permissao>();
		String querySQL = "SELECT p.pkPermissao AS pkPermissao, p.nome as nome, p.tipo, p.tipoModuloResponsavel AS tipoModuloResponsavel, "
				+ " p.tipoSubMenuResponsavel as tipoSubMenuResponsavel, p.telaResponsavel as telaResponsavel"
				+ " FROM PermissaoUsuario pu"
				+ " LEFT OUTER JOIN Permissao p ON(pu.fkPermissao = p.pkPermissao)"
				+ " LEFT OUTER JOIN Usuario u ON(pu.fkUsuario = u.pkUsuario)"
				+ " WHERE pu.status = ?"
				+ " AND p.status = ?";
		
		if(usuario!=null) { querySQL += " AND fkusuario = CAST(? as bigint)"; }
		
		ps = con.prepareStatement(querySQL);
		
		ps.setObject(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setObject(2, ConstantesSisEducar.STATUS_ATIVO);
		
		if(usuario!=null) { ps.setObject(3, usuario.getPkUsuario());}

		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) 
		{
			permissao = new Permissao();
			permissao.setPkPermissao(rs.getInt("pkPermissao"));
			permissao.setNome(rs.getString("nome"));
			permissao.setTipo(rs.getInt("tipo"));
			permissao.setTipoModuloResponsavel(rs.getInt("tipoModuloResponsavel"));
			permissao.setTipoSubMenuResponsavel(rs.getInt("tipoSubMenuResponsavel"));
			permissao.setTelaResponsavel(rs.getInt("telaResponsavel"));
			permissoes.add(permissao);
		}
		
		return permissoes;
	}
	
	/**
	 * Método usado para inserir uma permissão para um determinado usuário
	 * @author João Paulo
	 * @param permissaoUsuario
	 * @return
	 */
	public Boolean inserirPermissaoUsuario(PermissaoUsuario permissaoUsuario)
	{
		try 
		{
			String querySQL = "INSERT INTO permissaoUsuario "
					+ " (fkusuario, fkpermissao, fkmunicipiocliente, status) values(CAST(? as bigint),?,?,?)";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setObject(1, permissaoUsuario.getUsuario()!=null ? permissaoUsuario.getUsuario().getPkUsuario() : null);
			ps.setObject(2, permissaoUsuario.getPermissao()!=null? permissaoUsuario.getPermissao().getPkPermissao() : null);
			ps.setObject(3, permissaoUsuario.getFkMunicipioCliente()!=null ? permissaoUsuario.getFkMunicipioCliente().getPkCidade() : null);
			ps.setInt(4, ConstantesSisEducar.STATUS_ATIVO);
			
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
	 * Método usado para buscar todos os usuários no banco de dados pelos parâmetros passados
	 * @author João Paulo
	 * @param cpf
	 * @param usuario
	 * @param email
	 * @return List<Usuario>
	 */
	public List<Usuario> buscar(String cpf, String usuario, String email)
	{
		try 
		{
			Integer numeroArqumentos = 1;
			Usuario usuaAux = null;
			Pessoa pessoa = null;
			Cidade cidade = null;
			List<Usuario> usuarioAux = new ArrayList<Usuario>();
			String querySQL = "SELECT * FROM Usuario"
					+ " WHERE status = ?";
			
			if(cpf!=null && cpf.length()>0)
			{
				querySQL += " AND cpfcnpj like ?";
			}
			if(usuario!=null && usuario.length() >0)
			{
				querySQL+= " AND nome like ?";
			}
			if(email!=null && email.length() >0)
			{
				querySQL+= " AND email like ?";
			}
			querySQL+= " AND fkMunicipioCliente = ?";
			querySQL+= " ORDER BY nome";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(numeroArqumentos, ConstantesSisEducar.STATUS_ATIVO);
			numeroArqumentos ++;
			if(cpf!=null && cpf.length()>0)
			{
				ps.setObject(numeroArqumentos, "%" + cpf + "%");
				numeroArqumentos ++;
			}
			if(usuario!=null && usuario.length() >0)
			{
				ps.setObject(numeroArqumentos, "%" + usuario + "%");
				numeroArqumentos ++;
			}
			if(email!=null && email.length() >0)
			{
				ps.setObject(numeroArqumentos, "%" + email + "%");
				numeroArqumentos ++;
			}
			
			ps.setObject(numeroArqumentos, usuarioLogado.getFkMunicipioCliente().getPkCidade());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				cidade = new Cidade();
				pessoa = new Pessoa();
				usuaAux = new Usuario();
				usuaAux.setPkUsuario(rs.getString("pkUsuario"));
				usuaAux.setCpfcnpj(rs.getString("cpfcnpj"));
				usuaAux.setNome(rs.getString("nome"));
				usuaAux.setEmail(rs.getString("email"));
				usuaAux.setDataLancamento(rs.getDate("dataLancamento"));
				usuaAux.setTipo(rs.getInt("tipo"));
				usuaAux.setStatus(rs.getInt("status"));
				usuaAux.setRaAluno(rs.getString("raAluno"));
				usuaAux.setGenero(rs.getString("genero"));
				
				if(rs.getObject("fkPessoa")!=null)
				{ 
					pessoa = new PessoaDAO().obtemPessoaSimples(rs.getInt("fkPessoa"));
					usuaAux.setPessoa(pessoa);
				}
				if(rs.getObject("fkMunicipioCliente")!=null)
				{
					cidade.setPkCidade(rs.getInt("fkMunicipioCliente"));
					usuaAux.setFkMunicipioCliente(cidade);
				}
				usuaAux.setPermissoes(buscarPermissoesUsuario(usuaAux));
				usuarioAux.add(usuaAux);
			}
			
			return usuarioAux;
		} 
		catch (Exception e) 
		{
			System.out.println(e);
			return null;
		}
	}
}
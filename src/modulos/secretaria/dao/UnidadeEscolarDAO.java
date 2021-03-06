package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Contato;
import modulos.secretaria.om.Endereco;
import modulos.secretaria.om.RedeEnsino;
import modulos.secretaria.om.Regiao;
import modulos.secretaria.om.SituacaoFuncionamento;
import modulos.secretaria.om.Terreno;
import modulos.secretaria.om.TipoOcupacao;
import modulos.secretaria.om.UnidadeEscolar;
import modulos.secretaria.om.Usuario;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class UnidadeEscolarDAO extends SisEducarDAO 
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	Usuario usuarioLogado = null;
	
	public UnidadeEscolarDAO() throws SQLException
	{
		usuarioLogado = (Usuario)  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		
		desabilitarAutoCommit(con);
	}
	
	/**
	 * Insere a unidade e já obtem a pk salva e retorna o objeto completo
	 * @author João Paulo
	 * @param unidadeEscolar
	 * @return unidadeEscolar
	 */
	public UnidadeEscolar inserirUnidadeEscolar(UnidadeEscolar unidadeEscolar)
	{
		try 
		{
			Integer pkUnidadeEscolarExistente = obtemPKUnidadeEscolar(unidadeEscolar.getCodigo(), unidadeEscolar.getNome());
			if(pkUnidadeEscolarExistente==null)
			{
				Integer numeroArgumentos = 1;
				String querySQL = "INSERT INTO unidadeescolar "
						+ " (codigo, nome, status, fkredeensino, fkregiao, fksituacaofuncionamento, unidadecontrolada, unidadeinformatizada, fktipoocupacao, fkterreno, fkDiretor, fkMunicipioCliente, fkEndereco, codigoFederal) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				ps = con.prepareStatement(querySQL);
				
				ps.setString(numeroArgumentos, unidadeEscolar.getCodigo());
				numeroArgumentos++;
				
				ps.setString(numeroArgumentos, unidadeEscolar.getNome());
				numeroArgumentos++;
				
				ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
				numeroArgumentos++;
				
				if(unidadeEscolar.getRedeEnsino()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getRedeEnsino().getPkRedeEnsino());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				
				if(unidadeEscolar.getRegiao()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getRegiao().getPkRegiao());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				
				if(unidadeEscolar.getSituacaoFuncionamento()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getSituacaoFuncionamento().getPkSituacaoFuncionamento());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				
				ps.setBoolean(numeroArgumentos, unidadeEscolar.getUnidadeControlada());
				numeroArgumentos++;
				
				ps.setBoolean(numeroArgumentos, unidadeEscolar.getUnidadeInformatizada());
				numeroArgumentos++;
				
				if(unidadeEscolar.getTipoOcupacao()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getTipoOcupacao().getPkTipoOcupacao());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				
				if(unidadeEscolar.getTerreno()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getTerreno().getPkTerreno());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				if(unidadeEscolar.getDiretor()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getDiretor().getPkPessoa());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				if(unidadeEscolar.getFkMunicipioCliente()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getFkMunicipioCliente().getPkCidade());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				if(unidadeEscolar.getEndereco()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getEndereco().getPkEndereco());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				if(unidadeEscolar.getCodigoFederal()!=null)
				{
					ps.setString(numeroArgumentos, unidadeEscolar.getCodigoFederal());
				}
				
				fecharConexaoBanco(con, ps, false, true);
			}

			unidadeEscolar.setPkUnidadeEscolar(obtemPKUnidadeEscolar(unidadeEscolar.getCodigo(), unidadeEscolar.getNome()));
			
			return unidadeEscolar;
		} 
		catch (SQLException e) 
		{
			return null;
		}
	}
	
	/**
	 * Método usado para atualizar uma unidade escolar por PK
	 * @author João Paulo
	 * @param unidadeEscolar
	 * @return UnidadeEscolar
	 * @throws SQLException
	 */
	public UnidadeEscolar atualizarUnidadeEscolar(UnidadeEscolar unidadeEscolar) throws SQLException
	{
		String querySQL = "UPDATE unidadeescolar "
				+ " SET (codigo, nome, unidadeControlada, unidadeInformatizada, fkRedeEnsino, fkRegiao, fkSituacaoFuncionamento,"
				+ " fkTipoOcupacao, fkDiretor, codigoFederal, fkTerreno) = (?,?,?,?,?,?,?,?,?,?,?)"
				+ " WHERE pkUnidadeEscolar = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, unidadeEscolar.getCodigo());
		ps.setString(2, unidadeEscolar.getNome());
		ps.setBoolean(3, unidadeEscolar.getUnidadeControlada());
		ps.setBoolean(4, unidadeEscolar.getUnidadeInformatizada());
		ps.setObject(5, unidadeEscolar.getRedeEnsino()!=null ? unidadeEscolar.getRedeEnsino().getPkRedeEnsino() : null);
		ps.setObject(6, unidadeEscolar.getRegiao()!=null ? unidadeEscolar.getRegiao().getPkRegiao() : null);
		ps.setObject(7, unidadeEscolar.getSituacaoFuncionamento()!=null ? unidadeEscolar.getSituacaoFuncionamento().getPkSituacaoFuncionamento() : null);
		ps.setObject(8, unidadeEscolar.getTipoOcupacao()!=null ? unidadeEscolar.getTipoOcupacao().getPkTipoOcupacao() : null);
		ps.setObject(9, unidadeEscolar.getDiretor()!=null ? unidadeEscolar.getDiretor().getPkPessoa() : null);
		ps.setString(10, unidadeEscolar.getCodigoFederal());
		ps.setInt(11, unidadeEscolar.getTerreno()!=null ? unidadeEscolar.getTerreno().getPkTerreno() : null);
		ps.setInt(12, unidadeEscolar.getPkUnidadeEscolar());
		
		fecharConexaoBanco(con, ps, false, true);
		
		return unidadeEscolar;
	}
	
	/**
	 * Busca apenas a pk do endereço a partir dos parâmetros recebidos
	 * @author João Paulo
	 * @param cep
	 * @param logradouro
	 * @param bairro
	 * @param numero
	 * @param complemento
	 * @param tipo
	 * @param fkCidade
	 * @return Integer pkEndereco
	 * @throws SQLException
	 */
	public Integer obtemPKUnidadeEscolar(String codigo, String nome) throws SQLException
	{
		Integer numeroArgumentos = 1;
		String querySQL = "SELECT * FROM unidadeescolar"
				+ " WHERE status = ?";
		
		if(codigo!=null && codigo.length() >0) 	{  querySQL += " AND codigo = CAST(? as character varying)"; }
		if(nome!=null && nome.length() >0)		{ querySQL += " AND nome = ?"; }
		
		querySQL += " AND fkMunicipioCliente = ?";
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		
		if(codigo!=null && codigo.length() >0)
		{
			numeroArgumentos ++;
			ps.setString(numeroArgumentos, codigo);
		}
		
		if(nome!=null && nome.length() >0)
		{
			numeroArgumentos ++;
			ps.setString(numeroArgumentos, nome);
		}
		
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, usuarioLogado.getFkMunicipioCliente().getPkCidade());
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			return rs.getInt("pkunidadeescolar");
		}
		
		return null;
	}
	
	/* Metodo para retornar os tipos de UNIDADES ESCOLARES de acordo com a rede */
	public List<UnidadeEscolar> consultaUnidadeEscolar(Integer pkRedeEnsino) throws SQLException{
		
		List<UnidadeEscolar> listaUnidadeEscolar = new ArrayList<UnidadeEscolar>();
		
		String querySQL = "SELECT * FROM UNIDADEESCOLAR WHERE FKREDEENSINO = ? AND STATUS = ? ";

		querySQL += " FKMUNICIPIOCLIENTE = ?";
		querySQL += " ORDER BY NOME";
		
		ps = con.prepareStatement(querySQL);
		ps.setInt(1, pkRedeEnsino);
		ps.setInt(2, ConstantesSisEducar.STATUS_ATIVO);
		ps.setObject(3, usuarioLogado.getFkMunicipioCliente().getPkCidade());
		rs = ps.executeQuery();
		
		while (rs.next()){
			UnidadeEscolar paramUnidadeEscolar = new UnidadeEscolar();
			paramUnidadeEscolar.setPkUnidadeEscolar(rs.getInt("PKUNIDADEESCOLAR"));
			paramUnidadeEscolar.setNome(rs.getString("NOME"));
			
			listaUnidadeEscolar.add(paramUnidadeEscolar);
		}
		
		return listaUnidadeEscolar;
	}
	
	/**
	 * Busca todas as unidades escolares do banco de dados pelos filtros que o usuário selecionar na tela
	 * @author João Paulo
	 * @param codigo
	 * @param nome
	 * @param cpfDiretor
	 * @return
	 */
	public List<UnidadeEscolar> buscar(String codigo, String codigoFederal, String nome, String cpfDiretor)
	{
		try 
		{
			Integer numeroArqumentos = 1;
			UnidadeEscolar unidadeEscolar = null;
			Regiao regiao = null;
			Cidade cidade = null;
			Terreno terreno = null;
			SituacaoFuncionamento situacaoFuncionamento = null;
			RedeEnsino redeEnsino = null;
			Endereco endereco = null;
			Contato contato = null;
			TipoOcupacao tipoOcupacao = null;
			RedeEnsinoDAO redeEnsinoDAO = new RedeEnsinoDAO();
			SituacaoFuncionamentoDAO situacaoFuncionamentoDAO = new SituacaoFuncionamentoDAO();
			RegiaoDAO regiaoDAO = new RegiaoDAO();
			TerrenoDAO terrenoDAO = new TerrenoDAO();
			EnderecoDAO enderecoDAO = new EnderecoDAO();
			TipoOcupacaoDAO tipoOcupacaoDAO = new TipoOcupacaoDAO();
			List<UnidadeEscolar> unidadesAux = new ArrayList<UnidadeEscolar>();
			String querySQL = "SELECT u.* "
					+ " FROM UnidadeEscolar u"
					+ " LEFT OUTER JOIN Pessoa p ON(u.fkDiretor = p.pkPessoa)"
					+ " LEFT OUTER JOIN RedeEnsino re ON(u.fkRedeEnsino = re.pkRedeEnsino)"
					+ " LEFT OUTER JOIN Endereco e ON(u.fkEndereco = e.pkEndereco)"
					+ " WHERE u.status = ?"
					+ " AND re.status = ?"
					+ " AND e.status = ?";
			
			if(codigo!=null && codigo.length()>0)
			{
				querySQL += " AND u.codigo = ?";
			}
			if(codigoFederal!=null && codigoFederal.length()>0)
			{
				querySQL += " AND u.codigoFederal like ?";
			}
			if(nome!=null && nome.length() >0)
			{
				querySQL+= " AND u.nome like ?";
			}
			if(cpfDiretor!=null && cpfDiretor.length() >0)
			{
				querySQL+= " AND p.cpf like ?";
			}
			
			querySQL+= " and u.fkMunicipioCliente = ?";
			querySQL+= " ORDER BY codigo";
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(numeroArqumentos, ConstantesSisEducar.STATUS_ATIVO);
			numeroArqumentos++;
			ps.setInt(numeroArqumentos, ConstantesSisEducar.STATUS_ATIVO);
			numeroArqumentos++;
			ps.setInt(numeroArqumentos, ConstantesSisEducar.STATUS_ATIVO);
			numeroArqumentos ++;
			if(codigo!=null && codigo.length()>0)
			{
				ps.setString(numeroArqumentos, codigo);
				numeroArqumentos ++;
			}
			if(codigoFederal!=null && codigoFederal.length()>0)
			{
				ps.setObject(numeroArqumentos, "%" + codigoFederal + "%");
				numeroArqumentos ++;
			}
			if(nome!=null && nome.length() >0)
			{
				ps.setObject(numeroArqumentos, "%" + nome + "%");
				numeroArqumentos ++;
			}
			if(cpfDiretor!=null && cpfDiretor.length() >0)
			{
				ps.setObject(numeroArqumentos, "%" + cpfDiretor + "%");
				numeroArqumentos ++;
			}
			
			ps.setObject(numeroArqumentos, usuarioLogado.getFkMunicipioCliente().getPkCidade());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				endereco = new Endereco();
				contato = new Contato();
				cidade = new Cidade();
				terreno = new Terreno();
				regiao = new Regiao();
				tipoOcupacao = new TipoOcupacao();
				redeEnsino = new RedeEnsino();
				situacaoFuncionamento = new SituacaoFuncionamento();
				unidadeEscolar = new UnidadeEscolar();
				unidadeEscolar.setPkUnidadeEscolar(rs.getInt("pkUnidadeEscolar"));
				unidadeEscolar.setCodigo(rs.getString("codigo"));
				unidadeEscolar.setCodigoFederal(rs.getString("codigoFederal"));
				unidadeEscolar.setNome(rs.getString("nome"));
				unidadeEscolar.setUnidadeControlada(rs.getBoolean("unidadeControlada"));
				unidadeEscolar.setUnidadeInformatizada(rs.getBoolean("unidadeInformatizada"));
				unidadeEscolar.setStatus(rs.getInt("status"));
				
				if(rs.getObject("fkRedeEnsino")!=null)
				{
					redeEnsino = redeEnsinoDAO.buscarRedeEnsino(rs.getInt("fkRedeEnsino"));
					unidadeEscolar.setRedeEnsino(redeEnsino);
				}
				if(rs.getObject("fkRegiao")!=null)
				{
					regiao = regiaoDAO.buscarRegiao(rs.getInt("fkRegiao"));
					unidadeEscolar.setRegiao(regiao);
				}
				if(rs.getObject("fkSituacaoFuncionamento")!=null)
				{
					situacaoFuncionamento = situacaoFuncionamentoDAO.buscarSituacaoFuncionamento(rs.getInt("fkSituacaoFuncionamento"));
					unidadeEscolar.setSituacaoFuncionamento(situacaoFuncionamento);
				}
				if(rs.getObject("fkTipoOcupacao")!=null)
				{
					tipoOcupacao = tipoOcupacaoDAO.buscarTipoOcupacao(rs.getInt("fkTipoOcupacao"));
					unidadeEscolar.setTipoOcupacao(tipoOcupacao);
				}
				if(rs.getObject("fkTerreno")!=null)
				{
					terreno = terrenoDAO.buscarTerreno(rs.getInt("fkTerreno"));
					unidadeEscolar.setTerreno(terreno);
				}
				if(rs.getObject("fkDiretor")!=null)
				{
					unidadeEscolar.setDiretor(new PessoaDAO().obtemPessoaSimples(rs.getInt("fkDiretor")));
				}
				if(rs.getObject("fkEndereco")!=null)
				{
					endereco = enderecoDAO.buscarEndereco(rs.getInt("fkEndereco"));
					unidadeEscolar.setEndereco(endereco);
					
					if(endereco.getContato()!=null)
					{
						endereco.setContato(new ContatoDAO().buscarContato(endereco.getContato()));
					}
				}
				if(rs.getObject("fkMunicipioCliente")!=null)
				{
					cidade.setPkCidade(rs.getInt("fkMunicipioCliente"));
					unidadeEscolar.setFkMunicipioCliente(cidade);
				}
				unidadeEscolar.setAmbientes(new AmbienteDAO().buscar(unidadeEscolar));
				
				unidadesAux.add(unidadeEscolar);
			}
			
			return unidadesAux;
		} 
		catch (Exception e) 
		{
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * Busca o registro com apenas as informações da unidade escolar, usado para buscas rápidas
	 * @author João Paulo
	 * @param codigo
	 * @param nome
	 * @return
	 */
	public UnidadeEscolar buscarUnidadeEscolarSimples(String codigo, String nome, Integer pkUnidadeEscolar)
	{
		try 
		{
			Integer numeroArqumentos = 1;
			UnidadeEscolar unidadeEscolar = null;
			Cidade cidade = null;
			String querySQL = "SELECT u.* "
					+ " FROM UnidadeEscolar u"
					+ " WHERE u.status = ?";
			
			if(codigo!=null && codigo.length()>0)
			{
				querySQL += " AND u.codigo like ?";
			}
			if(nome!=null && nome.length() >0)
			{
				querySQL+= " AND u.nome like ?";
			}
			if(pkUnidadeEscolar!=null)
			{
				querySQL+= " AND u.pkunidadeescolar = ?";
			}
			
			querySQL+= " and u.fkMunicipioCliente = ?";
			querySQL+= " ORDER BY codigo";
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(numeroArqumentos, ConstantesSisEducar.STATUS_ATIVO);
			numeroArqumentos++;
			if(codigo!=null && codigo.length()>0)
			{
				ps.setObject(numeroArqumentos, "%" + codigo + "%");
				numeroArqumentos ++;
			}
			if(nome!=null && nome.length() >0)
			{
				ps.setObject(numeroArqumentos, "%" + nome + "%");
				numeroArqumentos ++;
			}
			if(pkUnidadeEscolar!=null)
			{
				ps.setInt(numeroArqumentos, pkUnidadeEscolar);
				numeroArqumentos ++;
			}
			
			ps.setObject(numeroArqumentos, usuarioLogado.getFkMunicipioCliente().getPkCidade());
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				cidade = new Cidade();
				unidadeEscolar = new UnidadeEscolar();
				unidadeEscolar.setPkUnidadeEscolar(rs.getInt("pkUnidadeEscolar"));
				unidadeEscolar.setCodigo(rs.getString("codigo"));
				unidadeEscolar.setNome(rs.getString("nome"));
				unidadeEscolar.setUnidadeControlada(rs.getBoolean("unidadeControlada"));
				unidadeEscolar.setUnidadeInformatizada(rs.getBoolean("unidadeInformatizada"));
				unidadeEscolar.setStatus(rs.getInt("status"));
				
				if(rs.getObject("fkMunicipioCliente")!=null)
				{
					cidade.setPkCidade(rs.getInt("fkMunicipioCliente"));
					unidadeEscolar.setFkMunicipioCliente(cidade);
				}
			}
			
			return unidadeEscolar;
		} 
		catch (Exception e) 
		{
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * Conta a quantidade de alunos dentro da Unidade Escolar
	 * 
	 */
	public Integer calculaQuantidadeAlunos(Integer pkUnidadeEscolar) {
		Integer quantidadeAluno = 0;
		
		try {
			
			String querySQL = "SELECT COUNT(PKALUNO) AS QUANTALUNO FROM ALUNO WHERE FKREDEENSINO = ? ";
			
			ps = con.prepareStatement(querySQL);
			ps.setInt(1, pkUnidadeEscolar);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				quantidadeAluno = rs.getInt("QUANTALUNO");
			}
			
			return quantidadeAluno;
		} catch (Exception e){
			System.out.println(e);
			return quantidadeAluno;
		}
	}
	
	/**
	 * Usado para remover uma unidade escolar do banco de dados
	 * @author João Paulo
	 * @param unidadeEscolar
	 * @return
	 */
	public Boolean remover(UnidadeEscolar unidadeEscolar) 
	{
		try 
		{
			String querySQL = "UPDATE UNIDADEESCOLAR "
					+ " SET STATUS = ?"
					+ " WHERE PKUNIDADEESCOLAR = ? RETURNING STATUS";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(1, ConstantesSisEducar.STATUS_REMOVIDO);
			ps.setInt(2, unidadeEscolar.getPkUnidadeEscolar());
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				if(rs.getInt("STATUS") == ConstantesSisEducar.STATUS_REMOVIDO) 
				{
					fecharConexaoBanco(con, ps, false, true);
					return true;
				}
			}
			
			return false;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}
}

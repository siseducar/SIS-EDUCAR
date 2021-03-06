package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Fornecedor;
import modulos.secretaria.om.UnidadeEscolar;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class FornecedorDAO extends SisEducarDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public Fornecedor salvarDadosFornecedor(Fornecedor fornecedorDados) {
		try {
			int numeroArgumento = 1;
			
			String querySQL = " INSERT INTO FORNECEDOR ( ";
			if(fornecedorDados.getPkFornecedor()!=null)
				querySQL += "PKFORNECEDOR,";
				
			querySQL += " RAZAOSOCIAL, NOMEFANTASIA, CNPJ";
			if( fornecedorDados.getNumInscriEstadual() != null && !fornecedorDados.getNumInscriEstadual().equals("") ) {
				querySQL += " ,INSCRICAOESTADUAL, FKESTADOINSCRICAO";
			}
			if( fornecedorDados.getNumInscriMunicipal() != null && !fornecedorDados.getNumInscriMunicipal().equals("") ) {
				querySQL += " ,INSCRICAOMUNICIPAL, FKMUNICIPIOINSCRICAO ";
			}
			if( fornecedorDados.getObservacao() != null && !fornecedorDados.getObservacao().equals("") ) {
				querySQL += " ,OBSERVACOES";
			}
			querySQL += " ,FKPESSOA, FKENDERECO, FKMUNICIPIOCLIENTE, STATUS ";

			querySQL += " ) VALUES ( ";
			
			if(fornecedorDados.getPkFornecedor()!=null)
				querySQL += "?,";
			
			querySQL += " ?, ?, ?";
			
			if( fornecedorDados.getNumInscriEstadual() != null && !fornecedorDados.getNumInscriEstadual().equals("") ) {
				querySQL += " ,?, ?";
			}
			if( fornecedorDados.getNumInscriMunicipal() != null && !fornecedorDados.getNumInscriMunicipal().equals("") ) {
				querySQL += " ,?, ? ";
			}
			if( fornecedorDados.getObservacao() != null && !fornecedorDados.getObservacao().equals("") ) {
				querySQL += " ,?";
			}
			
			querySQL += " ,?, ?, ?, ? )";
			querySQL += " ON CONFLICT (PKFORNECEDOR) DO UPDATE SET ";
			
			querySQL += " RAZAOSOCIAL = ?, NOMEFANTASIA = ?, CNPJ = ?";
			if( fornecedorDados.getNumInscriEstadual() != null && !fornecedorDados.getNumInscriEstadual().equals("") ) {
				querySQL += " ,INSCRICAOESTADUAL = ?, FKESTADOINSCRICAO = ?";
			}
			if( fornecedorDados.getNumInscriMunicipal() != null && !fornecedorDados.getNumInscriMunicipal().equals("") ) {
				querySQL += " ,INSCRICAOMUNICIPAL = ?, FKMUNICIPIOINSCRICAO = ?";
			}
			if( fornecedorDados.getObservacao() != null && !fornecedorDados.getObservacao().equals("") ) {
				querySQL += " ,OBSERVACOES = ?";
			}
			querySQL += " ,FKPESSOA = ?, FKENDERECO = ?, FKMUNICIPIOCLIENTE = ?, STATUS = ? WHERE FORNECEDOR.PKFORNECEDOR = ?";
			
			querySQL += " RETURNING PKFORNECEDOR";
			
			ps = con.prepareStatement(querySQL);
			
			//INSERT
			if(fornecedorDados.getPkFornecedor()!=null)
			{
				ps.setObject(numeroArgumento, fornecedorDados.getPkFornecedor());
				numeroArgumento++;
			}
			
			ps.setString(numeroArgumento, fornecedorDados.getRazaoSocial());
			numeroArgumento++;
			
			ps.setString(numeroArgumento, fornecedorDados.getNomeFantasia());
			numeroArgumento++;
			
			ps.setString(numeroArgumento, fornecedorDados.getCnpj());
			numeroArgumento++;
			
			if( fornecedorDados.getNumInscriEstadual() != null && !fornecedorDados.getNumInscriEstadual().equals("") ) {
				ps.setString(numeroArgumento, fornecedorDados.getNumInscriEstadual());
				numeroArgumento++;
				
				ps.setInt(numeroArgumento, fornecedorDados.getEstadoInscricao().getPkEstado());
				numeroArgumento++;
			}
			if( fornecedorDados.getNumInscriMunicipal() != null && !fornecedorDados.getNumInscriMunicipal().equals("") ) {
				ps.setString(numeroArgumento, fornecedorDados.getNumInscriMunicipal());
				numeroArgumento++;
				
				ps.setInt(numeroArgumento, fornecedorDados.getCidadeInscricao().getPkCidade());
				numeroArgumento++;
			}
			if( fornecedorDados.getObservacao() != null && !fornecedorDados.getObservacao().equals("") ) {
				ps.setString(numeroArgumento, fornecedorDados.getObservacao());
				numeroArgumento++;
			}
			
			ps.setInt(numeroArgumento, fornecedorDados.getPessoa().getPkPessoa());
			numeroArgumento++;
			
			ps.setInt(numeroArgumento, fornecedorDados.getEndereco().getPkEndereco());
			numeroArgumento++;
			
			ps.setInt(numeroArgumento, fornecedorDados.getFkMunicipioCliente().getPkCidade());
			numeroArgumento++;
			
			ps.setInt(numeroArgumento, ConstantesSisEducar.STATUS_ATIVO);
			numeroArgumento++;
			
			//UPDATE
			ps.setString(numeroArgumento, fornecedorDados.getRazaoSocial());
			numeroArgumento++;
			
			ps.setString(numeroArgumento, fornecedorDados.getNomeFantasia());
			numeroArgumento++;
			
			ps.setString(numeroArgumento, fornecedorDados.getCnpj());
			numeroArgumento++;
			
			if( fornecedorDados.getNumInscriEstadual() != null && !fornecedorDados.getNumInscriEstadual().equals("") ) {
				ps.setString(numeroArgumento, fornecedorDados.getNumInscriEstadual());
				numeroArgumento++;
				
				ps.setInt(numeroArgumento, fornecedorDados.getEstadoInscricao().getPkEstado());
				numeroArgumento++;
			}
			if( fornecedorDados.getNumInscriMunicipal() != null && !fornecedorDados.getNumInscriMunicipal().equals("") ) {
				ps.setString(numeroArgumento, fornecedorDados.getNumInscriMunicipal());
				numeroArgumento++;
				
				ps.setInt(numeroArgumento, fornecedorDados.getCidadeInscricao().getPkCidade());
				numeroArgumento++;
			}
			if( fornecedorDados.getObservacao() != null && !fornecedorDados.getObservacao().equals("") ) {
				ps.setString(numeroArgumento, fornecedorDados.getObservacao());
				numeroArgumento++;
			}
			
			ps.setInt(numeroArgumento, fornecedorDados.getPessoa().getPkPessoa());
			numeroArgumento++;
			
			ps.setInt(numeroArgumento, fornecedorDados.getEndereco().getPkEndereco());
			numeroArgumento++;
			
			ps.setInt(numeroArgumento, fornecedorDados.getFkMunicipioCliente().getPkCidade());
			numeroArgumento++;
			
			ps.setInt(numeroArgumento, ConstantesSisEducar.STATUS_ATIVO);
			numeroArgumento++;
			
			ps.setInt(numeroArgumento, fornecedorDados.getPkFornecedor()!=null ? fornecedorDados.getPkFornecedor() : 0);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				fornecedorDados.setPkFornecedor(rs.getInt("PKFORNECEDOR"));
			}
			
			fecharConexaoBanco(con, ps, true, false);
			
			return fornecedorDados;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Retorna uma lista de Fornecedor
	 * @author Michael
	 * @return List Fornecedor
	 * @throws SQLException
	 */
	public List<Fornecedor> obtemTodos(Integer pkMunicipioCliente)
	{
		try {
			Fornecedor fornecedor = null;
			List<Fornecedor> listaFornecedor = new ArrayList<Fornecedor>();
			String querySQL = "SELECT * FROM Fornecedor"
					+ " WHERE status = ?"
					+ " AND FKMUNICIPIOCLIENTE = ?";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
			ps.setInt(2, pkMunicipioCliente);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				fornecedor = new Fornecedor();
				fornecedor.setPkFornecedor(rs.getInt("PKFORNECEDOR"));
				fornecedor.setRazaoSocial(rs.getString("RAZAOSOCIAL"));
				fornecedor.setNomeFantasia(rs.getString("NOMEFANTASIA"));
				fornecedor.setCnpj(rs.getString("CNPJ"));
				
				listaFornecedor.add(fornecedor);
			}
			return listaFornecedor;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Fornecedor obtemFornecedor(Integer pkPessoa) 
	{
		try 
		{
			Fornecedor fornecedor = null;
			String querySQL = "SELECT * FROM FORNECEDOR"
					+ " WHERE STATUS = ? AND FKPESSOA = ?";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
			ps.setInt(2, pkPessoa);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				fornecedor = new Fornecedor();
				fornecedor.setPkFornecedor(rs.getInt("PKFORNECEDOR"));
				fornecedor.setRazaoSocial(rs.getString("RAZAOSOCIAL"));
				fornecedor.setNomeFantasia(rs.getString("NOMEFANTASIA"));
				fornecedor.setCnpj(rs.getString("CNPJ"));
				fornecedor.setNumInscriEstadual(rs.getString("INSCRICAOESTADUAL"));
				fornecedor.setNumInscriMunicipal(rs.getString("INSCRICAOMUNICIPAL"));
				fornecedor.setEstadoInscricao(new EstadoDAO().obtemEstado(rs.getInt("FKESTADOINSCRICAO"), null, null));
				fornecedor.setCidadeInscricao(new CidadeDAO().obtemCidade(null, null, rs.getInt("FKMUNICIPIOINSCRICAO")));
				fornecedor.setPessoa(new PessoaDAO().obtemPessoaSimples(rs.getInt("FKPESSOA")));
				fornecedor.setEndereco(new EnderecoDAO().buscarEndereco(rs.getInt("FKENDERECO")));
				fornecedor.setFkMunicipioCliente(new CidadeDAO().obtemCidade(null, null, rs.getInt("FKMUNICIPIOCLIENTE")));
				fornecedor.setObservacao(rs.getString("observacoes"));
				return fornecedor;
			}
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean remover(Fornecedor fornecedor) throws SQLException 
	{
		String querySQL = "UPDATE fornecedor"
				+ " SET status = ?"
				+ " WHERE pkFornecedor = ? RETURNING STATUS";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_REMOVIDO);
		ps.setInt(2, fornecedor.getPkFornecedor());
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			if(rs.getInt("STATUS") == ConstantesSisEducar.STATUS_REMOVIDO) {
				return true;
			} else {
				return false;					
			}
		}
		
		return false;	
	}
}

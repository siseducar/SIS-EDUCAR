package br.com.sisEducar.modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.sisEducar.modulos.RH.om.Pessoa;
import br.com.sisEducar.modulos.RH.om.PessoaFisica;
import br.com.sisEducar.modulos.RH.om.PessoaJuridica;
import br.com.sisEducar.modulos.sisEducar.conexaoBanco.ConectaBanco;
import br.com.sisEducar.modulos.sisEducar.dao.SisEducarDAO;
import br.com.sisEducar.modulos.sisEducar.utils.ConstantesSisEducar;

public class PessoaDAO extends SisEducarDAO
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	/**
	 * Esta query e usada para buscar a pessoa com seu respectivo tipo de pessoa (PessoaFisica e ou PessoaJuridica)
	 * @return
	 * @throws SQLException
	 */
	public List<Pessoa> obtemTodos() throws SQLException
	{
		Pessoa pessoa = null;
		List<Pessoa> listaPessoas = new ArrayList<Pessoa>();
		String querySQL = "SELECT * FROM pessoa "
				+ " WHERE status = ?";

		ps = con.prepareStatement(querySQL);
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			pessoa = new Pessoa();
			pessoa.setPkPessoa(rs.getInt("pkPessoa"));
			
			//Busca PessoaFisica
			querySQL = new String();
			querySQL = "SELECT pf.* FROM PessoaFisica pf"
					+ " LEFT OUTER JOIN Pessoa p ON(p.fkPessoaFisica = pf.pkPessoaFisica)"
					+ " WHERE p.pkPessoa = ?";

			ps = con.prepareStatement(querySQL);
			ps.setInt(1, pessoa.getPkPessoa());
			
			ResultSet rsFisica = ps.executeQuery();
			
			if(rsFisica.next())
			{
				pessoa.setPessoaFisica(new PessoaFisica());
				pessoa.getPessoaFisica().setPkPessoaFisica(rsFisica.getInt("pkPessoaFisica"));
				pessoa.getPessoaFisica().setNome(rsFisica.getString("nome"));
			}
			else
			{
				pessoa.setPessoaFisica(null);
			}
			
			//Busca PessoaJuridica
			querySQL = new String();
			querySQL = "SELECT pj.* FROM PessoaJuridica pj"
					+ " LEFT OUTER JOIN Pessoa p ON(p.fkPessoaJuridica = pj.pkPessoaJuridica)"
					+ " WHERE p.pkPessoa = ?";

			ps = con.prepareStatement(querySQL);
			ps.setInt(1, pessoa.getPkPessoa());
			
			ResultSet rsJuridca = ps.executeQuery();
			
			if(rsJuridca.next())
			{
				pessoa.setPessoaJuridica(new PessoaJuridica());
				pessoa.getPessoaJuridica().setPkPessoaFisica(rsJuridca.getInt("pkPessoaJuridica"));
				pessoa.getPessoaJuridica().setNomeFantasia(rsJuridca.getString("nomefantasia"));
			}
			else
			{
				pessoa.setPessoaJuridica(null);
			}
			
			listaPessoas.add(pessoa);
		}
		
		fecharConexaoBanco(con, ps, true, false);
		return listaPessoas;
	}
}

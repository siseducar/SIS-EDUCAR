package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.RH.om.Pessoa;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class PessoaDAO extends SisEducarDAO
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public Boolean inserirPessoa(Pessoa pessoa) throws SQLException 
	{
		String querySQL = "INSERT INTO pessoa "
				+ " (nome, nomefantasia, cpf, cnpj, semcpf, rg, datanascimento, datacadastro, sexo, telefonecomercial, "
				+ " telefoneresidencial, telefonecelular,"
				+ " tipoPessoa, falecido, datafalecimento, status) "
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, pessoa.getNome());
		ps.setString(2, pessoa.getNomeFantasia());
		ps.setString(3, pessoa.getCpf());
		ps.setString(4, pessoa.getCnpj());
		ps.setBoolean(5, pessoa.getSemCpf());
		ps.setDate(6, pessoa.getDataNascimento());
		ps.setDate(7, pessoa.getDataCadastro());
		ps.setLong(8, pessoa.getSexo());
		ps.setInt(9, pessoa.getTelefoneComercial());
		ps.setInt(10, pessoa.getTelefoneResidencial());
		ps.setInt(11, pessoa.getTelefoneCelular());
		ps.setInt(12, pessoa.getTipoPessoa());
		ps.setBoolean(13, pessoa.getFalecido());
		ps.setDate(14, pessoa.getDataFalecimento());
		ps.setInt(15, ConstantesSisEducar.STATUS_ATIVO);
		
		//Depois que terminar o cadastro de pessoa, remover esta linha e adicionar corretamente a pessoa
		
		fecharConexaoBanco(con, ps, false, true);
		
		return true;
	}
}

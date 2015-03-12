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
				+ " (nome, nomefantasia, cpf, cnpj, semcpf, rg, datanascimento, datacadastro, sexo, endereco,"
				+ "endereconumero, cep, estado, cidade, telefonecomercial, telefoneresidencial, telefonecelular,"
				+ "tipoPessoa, falecido, datafalecimento, status) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, pessoa.getNome());
		ps.setString(2, pessoa.getNomeFantasia());
		ps.setString(3, pessoa.getCpf());
		ps.setString(4, pessoa.getCnpj());
		ps.setBoolean(5, pessoa.getSemCpf());
		ps.setDate(6, pessoa.getDataNascimento());
		ps.setDate(7, pessoa.getDataCadastro());
		ps.setInt(8, pessoa.getSexo());
		ps.setString(9, pessoa.getEndereco());
		ps.setString(10, pessoa.getEnderecoNumero());
		ps.setString(11, pessoa.getCep());
		ps.setString(12, pessoa.getEstado());
		ps.setString(13, pessoa.getCidade());
		ps.setInt(14, pessoa.getTelefoneComercial());
		ps.setInt(15, pessoa.getTelefoneResidencial());
		ps.setInt(16, pessoa.getTelefoneCelular());
		ps.setInt(17, pessoa.getTipoPessoa());
		ps.setBoolean(18, pessoa.getFalecido());
		ps.setDate(19, pessoa.getDataFalecimento());
		ps.setInt(20, ConstantesSisEducar.STATUS_ATIVO);
		
		//Depois que terminar o cadastro de pessoa, remover esta linha e adicionar corretamente a pessoa
		
		fecharConexaoBanco(con, ps, true, true);
		
		return true;
	}
}

package modulos.secretaria.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.om.Pessoa;

public class PessoaService {

	/**
	 * Salva o cadastro da pessoa
	 * @author Michael
	 * @uso cadastroPessoa.xhtml
	 * @param Pessoa dadosPessoa
	 * @return false/true 
	 */
	public Boolean salvarCadastroPessoa(Pessoa dadosPessoa) {
		try {
			dadosPessoa = new PessoaDAO().salvarCadastroPessoa(dadosPessoa);
			
			if( dadosPessoa != null && dadosPessoa.getPkPessoa() != null) {
				return true;
			}
			
		} catch (Exception e) {
			return false;
		}	
		
		return false;
	}
	
	/**
	 * Atualiza os dados da pessoa
	 * @author Michael
	 * @param Pessoa pessoaDados
	 * @return false/true
	 */
	public Boolean atualizarDadosPessoa(Pessoa pessoaDados) {
		try {
			if( new PessoaDAO().atualizarDadosPessoa(pessoaDados)) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Valida se ja existe o cadastro
	 * @author Michael
	 * @uso cadastroPessoa.xhtml
	 * @param Pessoa dadosPessoa
	 * @return false/true 
	 */
	public Pessoa consultaCadastroPessoa(Pessoa pessoaDados) {
		try {
			
			pessoaDados = new PessoaDAO().consultaCadastroPessoa( pessoaDados.getPkPessoa() );
			
			return pessoaDados;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Consulta os cadastros salvos 
	 * @author Michael
	 * @uso cadastroPessoa.xhtml
	 * @param Pessoa dadosPessoa
	 * @return List<Pessoa> listaPessoa
	 */
	public List<Pessoa> consultaListaPessoa(Pessoa pessoaDados) {
		List<Pessoa> listaPessoa = new ArrayList<Pessoa>();
		try {
			listaPessoa = new PessoaDAO().consultaCadastroPessoa(pessoaDados);
			
			return listaPessoa;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Valida se ja existe o cadastro
	 * @author Michael
	 * @uso cadastroPessoa.xhtml
	 * @param Pessoa dadosPessoa
	 * @return false/true 
	 */
	public Boolean validaCadastroPessoa(Pessoa pessoaDados) {
		try {
			if( new PessoaDAO().obtemUnicoPessoaSimples( pessoaDados.getCpf().toString()) == null ) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
			
		return false;
	}
	
	/**
	 * Consulta o cadastro da pessoa responsavel
	 * @author Michael
	 * @uso cadastroPessoa.xhtml
	 * @param cpf,sexo
	 * @return nomeResponsavel 
	 */
	public String consultaNomeResponsavel(Long cpf,String sexo) {
		String nomeResponsavel = null;
		
		try {
			
			nomeResponsavel = new PessoaDAO().consultaNomeResponsavel(cpf, sexo);
			return nomeResponsavel;
			
		} catch (SQLException e) {
			return nomeResponsavel;
		}
		
	}
	
	/**
	 * Desativa o cadastro da pessoa
	 * @author Michael
	 * @uso cadastroPessoa.xhtml
	 * @param Pessoa dadosPessoa
	 * @return false/true 
	 */
	public Boolean deletarCadastroPessoa(Integer pkPessoa) {
		try {
			if( new PessoaDAO().deletarPessoa(pkPessoa)){
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}

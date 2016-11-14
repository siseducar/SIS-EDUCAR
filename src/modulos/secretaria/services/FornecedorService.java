package modulos.secretaria.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import modulos.secretaria.dao.FornecedorDAO;
import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.om.Fornecedor;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.Usuario;

public class FornecedorService {

	/**
	 * Salva o cadastro do fornecedor
	 * @author Michael
	 * @uso cadastroFornecedor.xhtml
	 * @param Fornecedor dadosFornecedor
	 * @return false/true 
	 */
	public Boolean salvarCadastroFornecedor(Fornecedor dadosFornecedor) {
		try {
			dadosFornecedor = new FornecedorDAO().salvarDadosFornecedor(dadosFornecedor);
			
			if( dadosFornecedor != null && dadosFornecedor.getPkFornecedor() != null) {
				return true;
			}
			
		} catch (Exception e) {
			return false;
		}	
		
		return false;
	}
	
	/**
	 * Consulta o contato
	 * @author Michael
	 * @uso cadastroFornecedor.xhtml
	 * @param Long cpfContato 
	 */
	public Pessoa consultaNomeContato(Long cpfContato) {
		
		try {
			Pessoa dadosContato = null;
			dadosContato = new PessoaDAO().obtemUnicoPessoaSimples(cpfContato.toString());
			
			return dadosContato;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Consulta todos os fornecedores
	 * @author Michael
	 * @uso cadastroFornecedor.xhtml
	 */
	public List<Fornecedor> consultaFornecedores() {
		Usuario usuarioLogadao = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		try {
			List<Fornecedor> listaFornecedor = new ArrayList<Fornecedor>();
			
			listaFornecedor = new FornecedorDAO().obtemTodos(usuarioLogadao.getFkMunicipioCliente().getPkCidade());
			
			return listaFornecedor;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}

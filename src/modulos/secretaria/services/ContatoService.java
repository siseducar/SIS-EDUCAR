package modulos.secretaria.services;

import modulos.secretaria.dao.ContatoDAO;
import modulos.secretaria.om.Contato;

public class ContatoService {

	/**
	 * Salva ou Atualiza os dados do contato da pessoa
	 * @author Michael
	 * @param Contato contatoDados
	 * @return false/true
	 */
	public Boolean salvarContato(Contato contatoDados) {
		try {
			contatoDados = new ContatoDAO().salvarContato(contatoDados);
			
			if( contatoDados != null && contatoDados.getPkContato() != null) {
				return true;
			}
			
		} catch (Exception e) {
			return false;
		}	
		
		return false;
	}
	
	/**
	 * Busca os dados do contato da pessoa
	 * @author Michael
	 * @param Contato contatoDados
	 * @return false/true
	 */
	public Contato consultaDadosContato(Contato contatoDados) {
		try {
			
			contatoDados = new ContatoDAO().buscarContato(contatoDados);
			
			return contatoDados;
			
		} catch (Exception e) {
			return null;
		}
	}
}

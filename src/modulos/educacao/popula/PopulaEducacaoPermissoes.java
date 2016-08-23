package modulos.educacao.popula;

import java.sql.SQLException;

import modulos.secretaria.dao.PopulaSecretariaDAO;
import modulos.secretaria.utils.ConstantesSecretaria;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class PopulaEducacaoPermissoes 
{
	/**
	 * Quando executar este main ele adicionará no banco de dados todos os registros aqui adicionados
	 * ATENÇÃO: Quando for adicionar algum script novo deve adicionar nessa classe e no populaRHDAO
	 * @author João Paulo
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException 
	{
		PopulaSecretariaDAO populaRHDAO = new PopulaSecretariaDAO();
	
		System.out.println("Populando Permissões de Tela...");
		
		/* TELA DE CADASTRO MATRICULA ALUNO */
		populaRHDAO.inserirPermissoes("Cadastrar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CADASTRAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_EDUCACAO, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_ESCOLA_CADASTROS_MATRICULA_ALUNO);
		populaRHDAO.inserirPermissoes("Consultar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CONSULTAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_EDUCACAO, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_ESCOLA_CADASTROS_MATRICULA_ALUNO);
		populaRHDAO.inserirPermissoes("Excluir", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_EXCLUIR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_EDUCACAO, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_ESCOLA_CADASTROS_MATRICULA_ALUNO);
		
		System.out.println("Popula de permissões finalizado \n");
	}
}

package modulos.secretaria.popula;

import java.sql.SQLException;

import modulos.secretaria.dao.PopulaSecretariaDAO;
import modulos.secretaria.utils.ConstantesSecretaria;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class PopulaSecretariaPermissoes 
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
		
		/* TELA DE CADASTRO PESSOA */
		populaRHDAO.inserirPermissoes("Cadastrar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CADASTRAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_PESSOA);
		populaRHDAO.inserirPermissoes("Consultar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CONSULTAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_PESSOA);
		populaRHDAO.inserirPermissoes("Excluir", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_EXCLUIR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_PESSOA);
		
		/* TELA DE CADASTRO USUARIO */
		populaRHDAO.inserirPermissoes("Cadastrar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CADASTRAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_USUARIO);
		populaRHDAO.inserirPermissoes("Consultar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CONSULTAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_USUARIO);
		populaRHDAO.inserirPermissoes("Excluir", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_EXCLUIR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_USUARIO);

		/* TELA DE CADASTRO FORNECEDOR */
		populaRHDAO.inserirPermissoes("Cadastrar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CADASTRAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_FORNECEDOR);
		populaRHDAO.inserirPermissoes("Consultar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CONSULTAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_FORNECEDOR);
		populaRHDAO.inserirPermissoes("Excluir", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_EXCLUIR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_FORNECEDOR);

		/* TELA DE CADASTRO UNIDADE ESCOLAR */
		populaRHDAO.inserirPermissoes("Cadastrar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CADASTRAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_UNIDADE_ESCOLAR);
		populaRHDAO.inserirPermissoes("Consultar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CONSULTAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_UNIDADE_ESCOLAR);
		populaRHDAO.inserirPermissoes("Excluir", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_EXCLUIR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_UNIDADE_ESCOLAR);

		/* TELA DE CADASTRO AMBIENTE */
		populaRHDAO.inserirPermissoes("Cadastrar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CADASTRAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_AMBIENTE);
		populaRHDAO.inserirPermissoes("Consultar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CONSULTAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_AMBIENTE);
		populaRHDAO.inserirPermissoes("Excluir", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_EXCLUIR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CADASTRO, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_AMBIENTE);

		/* TELA DE CONSULTAR HISTÓRICO ACESSO */
		populaRHDAO.inserirPermissoes("Consultar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CONSULTAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CONSULTA, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_HISTORICO_ACESSO);

		/* TELA DE CONSULTAR GRÁFICOS */
		populaRHDAO.inserirPermissoes("Cadastrar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CADASTRAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CONSULTA, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_GRAFICOS);
		populaRHDAO.inserirPermissoes("Consultar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CONSULTAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CONSULTA, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_GRAFICOS);
		populaRHDAO.inserirPermissoes("Excluir", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_EXCLUIR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CONSULTA, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_GRAFICOS);

		/* TELA DE CONSULTAR RELATÓRIOS */
		populaRHDAO.inserirPermissoes("Cadastrar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CADASTRAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CONSULTA, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_GRAFICOS);
		populaRHDAO.inserirPermissoes("Consultar", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_CONSULTAR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CONSULTA, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_GRAFICOS);
		populaRHDAO.inserirPermissoes("Excluir", ConstantesSisEducar.STATUS_ATIVO, ConstantesSecretaria.PERMISSAO_EXCLUIR, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA, ConstantesSecretaria.TIPO_SUB_MENU_CONSULTA, ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_GRAFICOS);

		System.out.println("Popula de permissões finalizado \n");
	}
}

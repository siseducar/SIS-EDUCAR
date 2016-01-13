package modulos.RH.popula;

import java.sql.SQLException;

import modulos.RH.dao.PopulaRHDAO;
import modulos.RH.utils.ConstantesRH;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class PopulaRH 
{
	/**
	 * Quando executar este main ele adicionar� no banco de dados todos os registros aqui adicionados
	 * ATEN��O: Quando for adicionar algum script novo deve adicionar nessa classe e no populaRHDAO
	 * @author Jo�o Paulo
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException 
	{
		PopulaRHDAO populaRHDAO = new PopulaRHDAO();
	
		System.out.println("Populando Permiss�es...");
		populaRHDAO.inserirPermissoes("Administrador Sistema", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_ADMINISTRADOR_SISTEMA);
		populaRHDAO.inserirPermissoes("Secretaria da Unidade Escolar", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_SECRETARIA_UNIDADE_ESCOLAR);
		populaRHDAO.inserirPermissoes("Professor", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_PROFESSOR);
		populaRHDAO.inserirPermissoes("Coordenador Escolar", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_COORDENADOR_ESCOLAR);
		populaRHDAO.inserirPermissoes("Diretor Escolar", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_DIRETOR_ESCOLAR);
		populaRHDAO.inserirPermissoes("Secretaria Educa��o", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_SECRETARIA_EDUCACAO);
		populaRHDAO.inserirPermissoes("Assitente Social", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_ASSISTENCIA_SOCIAL);
		populaRHDAO.inserirPermissoes("Psic�logo(a)", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_PSICOLOGO);
		populaRHDAO.inserirPermissoes("Nutricionista", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_NUTRICIONISTA);
		populaRHDAO.inserirPermissoes("Digitador Almoxarifado", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_DIGITADOR_ALMOXARIFADO);
		populaRHDAO.inserirPermissoes("Digitador Patrim�nio", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_DIGITADOR_PATROMINIO);
		populaRHDAO.inserirPermissoes("Bibliotec�rio", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_BIBLIOTECARIO);
		populaRHDAO.inserirPermissoes("Transporte", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_TRANSPORTE);
		populaRHDAO.inserirPermissoes("Protocolo", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_PROTOCOLO);
		populaRHDAO.inserirPermissoes("Ouvidoria", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_OUVIDORIA);
		populaRHDAO.inserirPermissoes("Aluno", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_ALUNO);
		populaRHDAO.inserirPermissoes("Ex-Aluno", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_EX_ALUNO);
		populaRHDAO.inserirPermissoes("Respons�vel", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_RESPONSAVEL);
		System.out.println("Popula de Permiss�es Finalizado");
	}
}

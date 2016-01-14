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
		System.out.println("Popula de Permiss�es Finalizado \n");
		
		System.out.println("Populando Ra�as...");
		populaRHDAO.inserirParametros("Raca", "01", "", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Ra�as Finalizado \n");
		
		System.out.println("Populando Cor...");
		populaRHDAO.inserirParametros("Cor", "01", "", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Cor Finalizado \n");
		
		System.out.println("Populando Situa��o Econ�mica...");
		populaRHDAO.inserirParametros("SituacaoEconomica", "01", "", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Situa��o Econ�mica Finalizado \n");
		
		System.out.println("Populando Religi�o...");
		populaRHDAO.inserirParametros("Religiao", "01", "", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Religi�o Finalizado \n");
		
		System.out.println("Populando Tipo Defici�ncia...");
		populaRHDAO.inserirParametros("TipoDeficiencia", "01", "", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Tipo Defici�ncia Finalizado \n");
		
		System.out.println("Populando Regi�o...");
		populaRHDAO.inserirParametros("Regiao", "01", "", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Regi�o Finalizado \n");
		
		System.out.println("Populando Nacionalidade...");
		populaRHDAO.inserirParametros("Nacionalidade", "01", "", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Nacionalidade Finalizado \n");
		
		System.out.println("Populando Estado Civil...");
		populaRHDAO.inserirParametros("EstadoCivil", "01", "", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Estado Civil Finalizado \n");
		
		System.out.println("Populando Turno...");
		populaRHDAO.inserirParametros("Turno", "01", "", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Turno Finalizado \n");
		
		System.out.println("Populando Grau Instru��o...");
		populaRHDAO.inserirParametros("GrauInstrucao", "01", "", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Grau Instru��o Finalizado \n");
	}
}

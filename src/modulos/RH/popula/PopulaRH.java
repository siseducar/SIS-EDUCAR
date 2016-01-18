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
		populaRHDAO.inserirParametros("Raca", "BR", "Branca", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Raca", "PR", "Preta", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Raca", "PD", "Parda", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Raca", "AM", "Amarela", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Raca", "IN", "Ind�gena", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Raca", "ND", "N�o Declarada", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Ra�as Finalizado \n");
		
		System.out.println("Populando Situa��o Econ�mica...");
		populaRHDAO.inserirParametros("SituacaoEconomica", "A", "20 SM+", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("SituacaoEconomica", "B", "10/20 SM", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("SituacaoEconomica", "C", "4/10 SM", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("SituacaoEconomica", "D", "2/4 SM", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("SituacaoEconomica", "E", "2 SM-", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Situa��o Econ�mica Finalizado \n");
		
		System.out.println("Populando Religi�o...");
		populaRHDAO.inserirParametros("Religiao", "CC", "Crist�-Cat�lica", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "CL", "Crist�-Luterana", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "CE", "Crist�-Evang�lica", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "ES", "Espiritismo", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "IS", "Islamismo", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "BU", "Budismo", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "JU", "Judaismo", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "OU", "Outras", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "ND", "N�o Declarada", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Religi�o Finalizado \n");
		
		System.out.println("Populando Tipo Defici�ncia...");
		populaRHDAO.inserirParametros("TipoDeficiencia", "DA", "Def. Auditivo", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("TipoDeficiencia", "DF", "Def. F�sico", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("TipoDeficiencia", "DM", "Def. Mental", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("TipoDeficiencia", "DV", "Def. Visual", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("TipoDeficiencia", "DMU", "Def. M�ltiplo", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Tipo Defici�ncia Finalizado \n");
		
		System.out.println("Populando Regi�o...");
		populaRHDAO.inserirParametros("Regiao", "RU", "Rural", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Regiao", "UR", "Urbano", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Regi�o Finalizado \n");
		
		System.out.println("Populando Nacionalidade...");
		populaRHDAO.inserirParametros("Nacionalidade", "BR", "Brasileiro(a)", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Nacionalidade", "ES", "Estrangeiro(a)", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Nacionalidade", "BE", "Brasileiro(a)/Estrangeiro(a)", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Nacionalidade Finalizado \n");
		
		System.out.println("Populando Estado Civil...");
		populaRHDAO.inserirParametros("EstadoCivil", "SL", "Solteiro(a)", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("EstadoCivil", "CA", "Casado(a)", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("EstadoCivil", "VI", "Vi�vo(a)", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("EstadoCivil", "SE", "Separado(a)", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("EstadoCivil", "DI", "Divorciado(a)", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("EstadoCivil", "UE", "Uni�o Est�vel", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Estado Civil Finalizado \n");
		
		System.out.println("Populando Turno...");
		populaRHDAO.inserirParametros("Turno", "MA", "Manh�", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Turno", "TA", "Tarde", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Turno", "NO", "Noite", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Turno", "IN", "Integral", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Turno Finalizado \n");
		
		System.out.println("Populando Grau Instru��o...");
		populaRHDAO.inserirParametros("GrauInstrucao", "AN", "Analfabeto", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "FU", "Fundamental", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "FI", "Fundamental Imcompleto", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "MI", "M�dio Imcompleto", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "ME", "M�dio", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "SI", "Superior Imcompleto", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "SC", "Superior Completo", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "SA", "Superior Andamento", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "PG", "P�s Graduado", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "ME", "Mestrado", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "DO", "Doutorado", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Grau Instru��o Finalizado \n");
	}
}

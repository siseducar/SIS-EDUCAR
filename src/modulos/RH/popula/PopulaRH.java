package modulos.RH.popula;

import java.sql.SQLException;

import modulos.RH.dao.PopulaRHDAO;
import modulos.RH.utils.ConstantesRH;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class PopulaRH 
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
		PopulaRHDAO populaRHDAO = new PopulaRHDAO();
	
		System.out.println("Populando Permissões...");
		populaRHDAO.inserirPermissoes("Administrador Sistema", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_ADMINISTRADOR_SISTEMA);
		populaRHDAO.inserirPermissoes("Secretaria da Unidade Escolar", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_SECRETARIA_UNIDADE_ESCOLAR);
		populaRHDAO.inserirPermissoes("Professor", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_PROFESSOR);
		populaRHDAO.inserirPermissoes("Coordenador Escolar", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_COORDENADOR_ESCOLAR);
		populaRHDAO.inserirPermissoes("Diretor Escolar", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_DIRETOR_ESCOLAR);
		populaRHDAO.inserirPermissoes("Secretaria Educação", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_SECRETARIA_EDUCACAO);
		populaRHDAO.inserirPermissoes("Assitente Social", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_ASSISTENCIA_SOCIAL);
		populaRHDAO.inserirPermissoes("Psicólogo(a)", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_PSICOLOGO);
		populaRHDAO.inserirPermissoes("Nutricionista", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_NUTRICIONISTA);
		populaRHDAO.inserirPermissoes("Digitador Almoxarifado", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_DIGITADOR_ALMOXARIFADO);
		populaRHDAO.inserirPermissoes("Digitador Património", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_DIGITADOR_PATROMINIO);
		populaRHDAO.inserirPermissoes("Bibliotecário", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_BIBLIOTECARIO);
		populaRHDAO.inserirPermissoes("Transporte", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_TRANSPORTE);
		populaRHDAO.inserirPermissoes("Protocolo", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_PROTOCOLO);
		populaRHDAO.inserirPermissoes("Ouvidoria", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_OUVIDORIA);
		populaRHDAO.inserirPermissoes("Aluno", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_ALUNO);
		populaRHDAO.inserirPermissoes("Ex-Aluno", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_EX_ALUNO);
		populaRHDAO.inserirPermissoes("Responsável", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_RESPONSAVEL);
		System.out.println("Popula de Permissões Finalizado \n");
		
		System.out.println("Populando Raças...");
		populaRHDAO.inserirParametros("Raca", "BR", "Branca", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Raca", "PR", "Preta", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Raca", "PD", "Parda", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Raca", "AM", "Amarela", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Raca", "IN", "Indígena", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Raca", "ND", "Não Declarada", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Raças Finalizado \n");
		
		System.out.println("Populando Situação Econômica...");
		populaRHDAO.inserirParametros("SituacaoEconomica", "A", "Entre 0 e 2 Salários Mínimos", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("SituacaoEconomica", "B", "Entre 2 e 4 Salários Mínimos", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("SituacaoEconomica", "C", "Entre 4 e 10 Salários Mínimos", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("SituacaoEconomica", "D", "Entre 10 e 20 Salários Mínimos", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("SituacaoEconomica", "E", "Mais de 20 Salários Mínimos", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Situação Econômica Finalizado \n");
		
		System.out.println("Populando Religião...");
		populaRHDAO.inserirParametros("Religiao", "CC", "Cristã-Católica", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "CL", "Cristã-Luterana", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "CE", "Cristã-Evangélica", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "ES", "Espiritismo", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "IS", "Islamismo", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "BU", "Budismo", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "JU", "Judaismo", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "OU", "Outras", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Religiao", "ND", "Não Declarada", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Religião Finalizado \n");
		
		System.out.println("Populando Tipo Deficiência...");
		populaRHDAO.inserirParametros("TipoDeficiencia", "DA", "Def. Auditivo", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("TipoDeficiencia", "DF", "Def. Física", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("TipoDeficiencia", "DM", "Def. Mental", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("TipoDeficiencia", "DV", "Def. Visual", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("TipoDeficiencia", "DMU", "Def. Múltipla", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Tipo Deficiência Finalizado \n");
		
		System.out.println("Populando Região...");
		populaRHDAO.inserirParametros("Regiao", "RU", "Rural", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Regiao", "UR", "Urbano", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Região Finalizado \n");
		
		System.out.println("Populando Nacionalidade...");
		populaRHDAO.inserirParametros("Nacionalidade", "BR", "Brasileiro(a)", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Nacionalidade", "ES", "Estrangeiro(a)", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Nacionalidade", "BE", "Brasileiro(a)/Estrangeiro(a)", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Nacionalidade Finalizado \n");
		
		System.out.println("Populando Estado Civil...");
		populaRHDAO.inserirParametros("EstadoCivil", "SL", "Solteiro(a)", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("EstadoCivil", "CA", "Casado(a)", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("EstadoCivil", "VI", "Viúvo(a)", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("EstadoCivil", "SE", "Separado(a)", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("EstadoCivil", "DI", "Divorciado(a)", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("EstadoCivil", "UE", "União Estável", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Estado Civil Finalizado \n");
		
		System.out.println("Populando Turno...");
		populaRHDAO.inserirParametros("Turno", "MA", "Manhã", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Turno", "TA", "Tarde", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Turno", "NO", "Noite", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("Turno", "IN", "Integral", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Turno Finalizado \n");
		
		System.out.println("Populando Grau Instrução...");
		populaRHDAO.inserirParametros("GrauInstrucao", "AN", "Analfabeto", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "FU", "Fundamental", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "FI", "Fundamental Imcompleto", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "MI", "Médio Imcompleto", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "ME", "Médio", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "SI", "Superior Imcompleto", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "SC", "Superior Completo", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "SA", "Superior Andamento", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "PG", "Pós Graduado", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "ME", "Mestrado", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("GrauInstrucao", "DO", "Doutorado", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Grau Instrução Finalizado \n");
	}
}

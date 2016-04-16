package modulos.secretaria.popula;

import java.sql.SQLException;

import modulos.secretaria.dao.PopulaRHDAO;
import modulos.secretaria.utils.ConstantesRH;
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
	
		System.out.println("Populando Permissões...\n");
		System.out.println("Populando Permissões dos Módulos...");
		populaRHDAO.inserirPermissoes("Administrador Sistema", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_ADMINISTRADOR_SISTEMA, null, null);
		populaRHDAO.inserirPermissoes("Secretaria", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_SECRETARIA, null, null);
		populaRHDAO.inserirPermissoes("Escola", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_ESCOLA, null, null);
		populaRHDAO.inserirPermissoes("Merenda", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_MERENDA, null, null);
		populaRHDAO.inserirPermissoes("Docentes", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_DOCENTES, null, null);
		populaRHDAO.inserirPermissoes("Portal", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_PORTAL, null, null);
		populaRHDAO.inserirPermissoes("Patromônio", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_PATROMONIO, null, null);
		populaRHDAO.inserirPermissoes("Almoxarifado", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_ALMOXARIFADO, null, null);
		populaRHDAO.inserirPermissoes("Biblioteca", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_BIBLIOTECA, null, null);
		populaRHDAO.inserirPermissoes("Transporte", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_TRANSPORTE, null, null);
		populaRHDAO.inserirPermissoes("Social", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_SOCIAL, null, null);
		populaRHDAO.inserirPermissoes("Protocolo", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_PROTOCOLO, null, null);
		
		System.out.println("Populando Permissões das Telas...");
		populaRHDAO.inserirPermissoes("Pessoa", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_SECRETARIA_CADASTROS_PESSOA, ConstantesRH.PERMISSAO_TIPO_SECRETARIA, ConstantesRH.TIPO_SUB_MENU_CADASTRO);
		populaRHDAO.inserirPermissoes("Usuário", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_SECRETARIA_CADASTROS_USUARIO, ConstantesRH.PERMISSAO_TIPO_SECRETARIA, ConstantesRH.TIPO_SUB_MENU_CADASTRO);
		populaRHDAO.inserirPermissoes("Fornecedor", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_SECRETARIA_CADASTROS_FORNECEDOR, ConstantesRH.PERMISSAO_TIPO_SECRETARIA, ConstantesRH.TIPO_SUB_MENU_CADASTRO);
		populaRHDAO.inserirPermissoes("Unidade Escolar", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_SECRETARIA_CADASTROS_UNIDADE_ESCOLAR, ConstantesRH.PERMISSAO_TIPO_SECRETARIA, ConstantesRH.TIPO_SUB_MENU_CADASTRO);
		populaRHDAO.inserirPermissoes("Gráficos", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_GRAFICOS, ConstantesRH.PERMISSAO_TIPO_SECRETARIA, ConstantesRH.TIPO_SUB_MENU_CONSULTA);
		populaRHDAO.inserirPermissoes("Relatórios", ConstantesSisEducar.STATUS_ATIVO, ConstantesRH.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_RELATORIOS, ConstantesRH.PERMISSAO_TIPO_SECRETARIA, ConstantesRH.TIPO_SUB_MENU_CADASTRO);
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
		
		System.out.println("Populando Situacao Funcionamento...");
		populaRHDAO.inserirParametros("SituacaoFuncionamento", "EA", "Em Atividade", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Situacao Funcionamento Finalizado \n");
		
		System.out.println("Populando Tipo Ocupacao...");
		populaRHDAO.inserirParametros("TipoOcupacao", "P", "Próprio", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("TipoOcupacao", "A", "Alugado", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Tipo Ocupacao Finalizado \n");
		
		System.out.println("Populando Rede Ensino...");
		populaRHDAO.inserirParametros("RedeEnsino", "1", "Municipal", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("RedeEnsino", "2", "Estadual", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("RedeEnsino", "3", "Federal", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("RedeEnsino", "4", "Particular", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("RedeEnsino", "5", "Municipal de outro município", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("RedeEnsino", "6", "Estadual de outro município", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("RedeEnsino", "7", "Federal de outro município", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("RedeEnsino", "8", "Particular de outro município", ConstantesSisEducar.STATUS_ATIVO);
		populaRHDAO.inserirParametros("RedeEnsino", "9", "Outros", ConstantesSisEducar.STATUS_ATIVO);
		System.out.println("Popula de Tipo Ocupacao Finalizado \n");
	}
}

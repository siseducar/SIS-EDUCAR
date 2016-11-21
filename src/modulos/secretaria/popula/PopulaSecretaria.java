package modulos.secretaria.popula;

import java.sql.SQLException;

import modulos.secretaria.dao.PopulaSecretariaDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class PopulaSecretaria 
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
	
		System.out.println("Populando Raças...");
		populaRHDAO.inserirParametros("Raca", "BR", "Branca", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("Raca", "PR", "Preta", ConstantesSisEducar.STATUS_ATIVO, 2);
		populaRHDAO.inserirParametros("Raca", "PD", "Parda", ConstantesSisEducar.STATUS_ATIVO, 3);
		populaRHDAO.inserirParametros("Raca", "AM", "Amarela", ConstantesSisEducar.STATUS_ATIVO, 4);
		populaRHDAO.inserirParametros("Raca", "IN", "Indígena", ConstantesSisEducar.STATUS_ATIVO, 5);
		populaRHDAO.inserirParametros("Raca", "ND", "Não Declarada", ConstantesSisEducar.STATUS_ATIVO, 6);
		System.out.println("Popula de Raças Finalizado \n");
		
		System.out.println("Populando Situação Econômica...");
		populaRHDAO.inserirParametros("SituacaoEconomica", "A", "Entre 0 e 2 Salários Mínimos", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("SituacaoEconomica", "B", "Entre 2 e 4 Salários Mínimos", ConstantesSisEducar.STATUS_ATIVO, 2);
		populaRHDAO.inserirParametros("SituacaoEconomica", "C", "Entre 4 e 10 Salários Mínimos", ConstantesSisEducar.STATUS_ATIVO, 3);
		populaRHDAO.inserirParametros("SituacaoEconomica", "D", "Entre 10 e 20 Salários Mínimos", ConstantesSisEducar.STATUS_ATIVO, 4);
		populaRHDAO.inserirParametros("SituacaoEconomica", "E", "Mais de 20 Salários Mínimos", ConstantesSisEducar.STATUS_ATIVO, 5);
		System.out.println("Popula de Situação Econômica Finalizado \n");
		
		System.out.println("Populando Religião...");
		populaRHDAO.inserirParametros("Religiao", "CC", "Cristã-Católica", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("Religiao", "CL", "Cristã-Luterana", ConstantesSisEducar.STATUS_ATIVO, 2);
		populaRHDAO.inserirParametros("Religiao", "CE", "Cristã-Evangélica", ConstantesSisEducar.STATUS_ATIVO, 3);
		populaRHDAO.inserirParametros("Religiao", "ES", "Espiritismo", ConstantesSisEducar.STATUS_ATIVO, 4);
		populaRHDAO.inserirParametros("Religiao", "IS", "Islamismo", ConstantesSisEducar.STATUS_ATIVO, 5);
		populaRHDAO.inserirParametros("Religiao", "BU", "Budismo", ConstantesSisEducar.STATUS_ATIVO, 6);
		populaRHDAO.inserirParametros("Religiao", "JU", "Judaismo", ConstantesSisEducar.STATUS_ATIVO, 7);
		populaRHDAO.inserirParametros("Religiao", "OU", "Outras", ConstantesSisEducar.STATUS_ATIVO, 8);
		populaRHDAO.inserirParametros("Religiao", "ND", "Não Declarada", ConstantesSisEducar.STATUS_ATIVO, 9);
		System.out.println("Popula de Religião Finalizado \n");
		
		System.out.println("Populando Tipo Deficiência...");
		populaRHDAO.inserirParametros("TipoDeficiencia", "DA", "Def. Auditivo", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("TipoDeficiencia", "DF", "Def. Física", ConstantesSisEducar.STATUS_ATIVO, 2);
		populaRHDAO.inserirParametros("TipoDeficiencia", "DM", "Def. Mental", ConstantesSisEducar.STATUS_ATIVO, 3);
		populaRHDAO.inserirParametros("TipoDeficiencia", "DV", "Def. Visual", ConstantesSisEducar.STATUS_ATIVO, 4);
		populaRHDAO.inserirParametros("TipoDeficiencia", "DMU", "Def. Múltipla", ConstantesSisEducar.STATUS_ATIVO, 5);
		System.out.println("Popula de Tipo Deficiência Finalizado \n");
		
		System.out.println("Populando Região...");
		populaRHDAO.inserirParametros("Regiao", "RU", "Rural", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("Regiao", "UR", "Urbano", ConstantesSisEducar.STATUS_ATIVO, 2);
		System.out.println("Popula de Região Finalizado \n");
		
		System.out.println("Populando Nacionalidade...");
		populaRHDAO.inserirParametros("Nacionalidade", "BR", "Brasileiro(a)", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("Nacionalidade", "ES", "Estrangeiro(a)", ConstantesSisEducar.STATUS_ATIVO, 2);
		populaRHDAO.inserirParametros("Nacionalidade", "BE", "Brasileiro(a)/Estrangeiro(a)", ConstantesSisEducar.STATUS_ATIVO, 3);
		System.out.println("Popula de Nacionalidade Finalizado \n");
		
		System.out.println("Populando Estado Civil...");
		populaRHDAO.inserirParametros("EstadoCivil", "SL", "Solteiro(a)", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("EstadoCivil", "CA", "Casado(a)", ConstantesSisEducar.STATUS_ATIVO, 2);
		populaRHDAO.inserirParametros("EstadoCivil", "VI", "Viúvo(a)", ConstantesSisEducar.STATUS_ATIVO, 3);
		populaRHDAO.inserirParametros("EstadoCivil", "SE", "Separado(a)", ConstantesSisEducar.STATUS_ATIVO, 4);
		populaRHDAO.inserirParametros("EstadoCivil", "DI", "Divorciado(a)", ConstantesSisEducar.STATUS_ATIVO, 5);
		populaRHDAO.inserirParametros("EstadoCivil", "UE", "União Estável", ConstantesSisEducar.STATUS_ATIVO, 6);
		System.out.println("Popula de Estado Civil Finalizado \n");
		
		System.out.println("Populando Turno...");
		populaRHDAO.inserirParametros("Turno", "MA", "Manhã", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("Turno", "TA", "Tarde", ConstantesSisEducar.STATUS_ATIVO, 2);
		populaRHDAO.inserirParametros("Turno", "NO", "Noite", ConstantesSisEducar.STATUS_ATIVO, 3);
		populaRHDAO.inserirParametros("Turno", "IN", "Integral", ConstantesSisEducar.STATUS_ATIVO, 4);
		System.out.println("Popula de Turno Finalizado \n");
		
		System.out.println("Populando Grau Instrução...");
		populaRHDAO.inserirParametros("GrauInstrucao", "AN", "Analfabeto", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("GrauInstrucao", "FU", "Fundamental", ConstantesSisEducar.STATUS_ATIVO, 2);
		populaRHDAO.inserirParametros("GrauInstrucao", "FI", "Fundamental Imcompleto", ConstantesSisEducar.STATUS_ATIVO, 3);
		populaRHDAO.inserirParametros("GrauInstrucao", "MI", "Médio Imcompleto", ConstantesSisEducar.STATUS_ATIVO, 4);
		populaRHDAO.inserirParametros("GrauInstrucao", "ME", "Médio", ConstantesSisEducar.STATUS_ATIVO, 5);
		populaRHDAO.inserirParametros("GrauInstrucao", "SI", "Superior Imcompleto", ConstantesSisEducar.STATUS_ATIVO, 6);
		populaRHDAO.inserirParametros("GrauInstrucao", "SC", "Superior Completo", ConstantesSisEducar.STATUS_ATIVO, 7);
		populaRHDAO.inserirParametros("GrauInstrucao", "SA", "Superior Andamento", ConstantesSisEducar.STATUS_ATIVO, 8);
		populaRHDAO.inserirParametros("GrauInstrucao", "PG", "Pós Graduado", ConstantesSisEducar.STATUS_ATIVO, 9);
		populaRHDAO.inserirParametros("GrauInstrucao", "ME", "Mestrado", ConstantesSisEducar.STATUS_ATIVO, 10);
		populaRHDAO.inserirParametros("GrauInstrucao", "DO", "Doutorado", ConstantesSisEducar.STATUS_ATIVO, 11);
		System.out.println("Popula de Grau Instrução Finalizado \n");
		
		System.out.println("Populando Situacao Funcionamento...");
		populaRHDAO.inserirParametros("SituacaoFuncionamento", "EA", "Em Atividade", ConstantesSisEducar.STATUS_ATIVO, 1);
		System.out.println("Popula de Situacao Funcionamento Finalizado \n");
		
		System.out.println("Populando Tipo Ocupacao...");
		populaRHDAO.inserirParametros("TipoOcupacao", "P", "Próprio", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("TipoOcupacao", "A", "Alugado", ConstantesSisEducar.STATUS_ATIVO, 2);
		System.out.println("Popula de Tipo Ocupacao Finalizado \n");
		
		System.out.println("Populando Rede Ensino...");
		populaRHDAO.inserirParametros("RedeEnsino", "1", "Municipal", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("RedeEnsino", "2", "Estadual", ConstantesSisEducar.STATUS_ATIVO, 2);
		populaRHDAO.inserirParametros("RedeEnsino", "3", "Federal", ConstantesSisEducar.STATUS_ATIVO, 3);
		populaRHDAO.inserirParametros("RedeEnsino", "4", "Particular", ConstantesSisEducar.STATUS_ATIVO, 4);
		populaRHDAO.inserirParametros("RedeEnsino", "5", "Municipal de outro município", ConstantesSisEducar.STATUS_ATIVO, 5);
		populaRHDAO.inserirParametros("RedeEnsino", "6", "Estadual de outro município", ConstantesSisEducar.STATUS_ATIVO, 6);
		populaRHDAO.inserirParametros("RedeEnsino", "7", "Federal de outro município", ConstantesSisEducar.STATUS_ATIVO, 7);
		populaRHDAO.inserirParametros("RedeEnsino", "8", "Particular de outro município", ConstantesSisEducar.STATUS_ATIVO, 8);
		populaRHDAO.inserirParametros("RedeEnsino", "9", "Outros", ConstantesSisEducar.STATUS_ATIVO, 9);
		System.out.println("Popula de Tipo Ocupacao Finalizado \n");
		
		System.out.println("Populando Tipo Logradouro...");
		populaRHDAO.inserirParametros("TipoLogradouro", "ALM", "Alameda", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("TipoLogradouro", "AVN", "Avenida", ConstantesSisEducar.STATUS_ATIVO, 2);
		populaRHDAO.inserirParametros("TipoLogradouro", "BEC", "Beco", ConstantesSisEducar.STATUS_ATIVO, 3);
		populaRHDAO.inserirParametros("TipoLogradouro", "BLV", "Boulevard", ConstantesSisEducar.STATUS_ATIVO, 4);
		populaRHDAO.inserirParametros("TipoLogradouro", "CAM", "Caminho", ConstantesSisEducar.STATUS_ATIVO, 5);
		populaRHDAO.inserirParametros("TipoLogradouro", "CAS", "Cais", ConstantesSisEducar.STATUS_ATIVO, 6);
		populaRHDAO.inserirParametros("TipoLogradouro", "CMP", "Campo", ConstantesSisEducar.STATUS_ATIVO, 7);
		populaRHDAO.inserirParametros("TipoLogradouro", "ESC", "Escada", ConstantesSisEducar.STATUS_ATIVO, 8);
		populaRHDAO.inserirParametros("TipoLogradouro", "ETR", "Estrada", ConstantesSisEducar.STATUS_ATIVO, 9);
		populaRHDAO.inserirParametros("TipoLogradouro", "FAV", "Favela", ConstantesSisEducar.STATUS_ATIVO, 10);
		populaRHDAO.inserirParametros("TipoLogradouro", "FAZ", "Fazenda", ConstantesSisEducar.STATUS_ATIVO, 11);
		populaRHDAO.inserirParametros("TipoLogradouro", "FLT", "Floresta", ConstantesSisEducar.STATUS_ATIVO, 12);
		populaRHDAO.inserirParametros("TipoLogradouro", "ILH", "Ilha", ConstantesSisEducar.STATUS_ATIVO, 13);
		populaRHDAO.inserirParametros("TipoLogradouro", "JRD", "Jardim", ConstantesSisEducar.STATUS_ATIVO, 14);
		populaRHDAO.inserirParametros("TipoLogradouro", "LAD", "Ladeira", ConstantesSisEducar.STATUS_ATIVO, 15);
		populaRHDAO.inserirParametros("TipoLogradouro", "LRG", "Largo", ConstantesSisEducar.STATUS_ATIVO, 16);
		populaRHDAO.inserirParametros("TipoLogradouro", "LTM", "Loteamento", ConstantesSisEducar.STATUS_ATIVO, 17);
		populaRHDAO.inserirParametros("TipoLogradouro", "LUG", "Lugar", ConstantesSisEducar.STATUS_ATIVO, 18);
		populaRHDAO.inserirParametros("TipoLogradouro", "MRR", "Morro", ConstantesSisEducar.STATUS_ATIVO, 19);
		populaRHDAO.inserirParametros("TipoLogradouro", "PQE", "Parque", ConstantesSisEducar.STATUS_ATIVO, 20);
		populaRHDAO.inserirParametros("TipoLogradouro", "PAS", "Passeio", ConstantesSisEducar.STATUS_ATIVO, 21);
		populaRHDAO.inserirParametros("TipoLogradouro", "PRA", "Praia", ConstantesSisEducar.STATUS_ATIVO, 22);
		populaRHDAO.inserirParametros("TipoLogradouro", "PRC", "Praça", ConstantesSisEducar.STATUS_ATIVO, 23);
		populaRHDAO.inserirParametros("TipoLogradouro", "REC", "Recanto", ConstantesSisEducar.STATUS_ATIVO, 24);
		populaRHDAO.inserirParametros("TipoLogradouro", "ROD", "Rodovia", ConstantesSisEducar.STATUS_ATIVO, 25);
		populaRHDAO.inserirParametros("TipoLogradouro", "RUA", "Rua", ConstantesSisEducar.STATUS_ATIVO, 26);
		populaRHDAO.inserirParametros("TipoLogradouro", "SRV", "Servidão", ConstantesSisEducar.STATUS_ATIVO, 27);
		populaRHDAO.inserirParametros("TipoLogradouro", "TRV", "Travessa", ConstantesSisEducar.STATUS_ATIVO, 28);
		populaRHDAO.inserirParametros("TipoLogradouro", "VIA", "Via", ConstantesSisEducar.STATUS_ATIVO, 29);
		populaRHDAO.inserirParametros("TipoLogradouro", "VIL", "Vila", ConstantesSisEducar.STATUS_ATIVO, 30);
		System.out.println("Popula Tipo Logradouro Finalizado \n");
		
		System.out.println("Populando Grau Parentesco...");
		populaRHDAO.inserirParametros("GrauParentesco", "P", "Pai", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("GrauParentesco", "M", "Mãe", ConstantesSisEducar.STATUS_ATIVO, 2);
		populaRHDAO.inserirParametros("GrauParentesco", "TM", "Tio", ConstantesSisEducar.STATUS_ATIVO, 3);
		populaRHDAO.inserirParametros("GrauParentesco", "TF", "Tia", ConstantesSisEducar.STATUS_ATIVO, 4);
		populaRHDAO.inserirParametros("GrauParentesco", "AM", "Avô", ConstantesSisEducar.STATUS_ATIVO, 5);
		populaRHDAO.inserirParametros("GrauParentesco", "AF", "Avó", ConstantesSisEducar.STATUS_ATIVO, 6);
		System.out.println("Popula Grau Parentesco Finalizado \n");
		
		System.out.println("Populando Tipos de Ambiente...");
		populaRHDAO.inserirParametros("TipoAmbiente", "SL", "Sala", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("TipoAmbiente", "BN", "Banheiro", ConstantesSisEducar.STATUS_ATIVO, 2);
		populaRHDAO.inserirParametros("TipoAmbiente", "DS", "Dispensa", ConstantesSisEducar.STATUS_ATIVO, 3);
		populaRHDAO.inserirParametros("TipoAmbiente", "CZ", "Cozinha", ConstantesSisEducar.STATUS_ATIVO, 4);
		populaRHDAO.inserirParametros("TipoAmbiente", "QD", "Quadra", ConstantesSisEducar.STATUS_ATIVO, 5);
		System.out.println("Popula Tipos Ambiente Finalizado \n");
		
		System.out.println("Populando Bloco...");
		populaRHDAO.inserirParametros("Bloco", "P", "Principal", ConstantesSisEducar.STATUS_ATIVO, 1);
		populaRHDAO.inserirParametros("Bloco", "1", "1", ConstantesSisEducar.STATUS_ATIVO, 2);
		populaRHDAO.inserirParametros("Bloco", "2", "2", ConstantesSisEducar.STATUS_ATIVO, 3);
		populaRHDAO.inserirParametros("Bloco", "3", "3", ConstantesSisEducar.STATUS_ATIVO, 4);
		System.out.println("Popula Bloco Finalizado \n");
	}
}

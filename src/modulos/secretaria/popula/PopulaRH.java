package modulos.secretaria.popula;

import java.sql.SQLException;

import modulos.secretaria.dao.PopulaRHDAO;
import modulos.secretaria.utils.ConstantesRH;
import sisEdcuar.utils.ConstantesSisEducar;

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
	}
}

package sisEdcuar.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversorUtils
{
	/**
	 * Converte uma string no formato (dd//MM/yyyy) em date sql
	 * @param str_date
	 * @return
	 */
	 public static Date convertStringToTimestamp(String str_date) 
	 {
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	     Date parsed = null;
	     try 
	     {
	        parsed = sdf.parse(str_date);
	     } 
	     catch (ParseException e1) 
	     {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	     }
	     
	     java.sql.Date data = new java.sql.Date(parsed.getTime());
	     return data;
	 }
}

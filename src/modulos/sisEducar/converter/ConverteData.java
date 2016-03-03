package modulos.sisEducar.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class ConverteData implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		
		try{
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			java.sql.Date data = new java.sql.Date(format.parse(value).getTime());
			
			return data;
		}catch (ConverterException e){
			e.printStackTrace(); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		/*
         * Irá converter CPF não formatado para um com pontos e traço.
         * Ex.: 00000000000 torna-se 000.000.000-00.
         */
		String cpf = value.toString();
		if( cpf != null && cpf.length() == 11) {
			cpf = cpf.substring(0,3) + "." + cpf.substring(3,6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
		}
		
		return cpf;
	}

}

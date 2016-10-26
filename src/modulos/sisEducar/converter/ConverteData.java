package modulos.sisEducar.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="converte.data")
public class ConverteData implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		
		int digitos = value.replace("_", "").replace("/", "").length();
		
		if(digitos == 8) {
			try{
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				java.sql.Date data = new java.sql.Date(format.parse(value).getTime());
				
				return data;
			}catch (ConverterException e){
				e.printStackTrace(); 
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value == null || value.toString().equals("")){
			return null;			
		} else {
			DateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
	    	String dataFormatada = null;  
	    	dataFormatada = formata.format(((Date) value).getTime());  
	    	return dataFormatada;  
		}	
	}

}

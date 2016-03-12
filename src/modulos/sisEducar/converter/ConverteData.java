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
			SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
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
		
		String dataSt = value.toString();
		try{
			SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
			java.sql.Date data = new java.sql.Date(format.parse(dataSt).getTime());
			
			return data.toString();
		}catch (ConverterException e){
			e.printStackTrace(); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;

	}

}

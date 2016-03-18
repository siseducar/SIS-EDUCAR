package modulos.sisEducar.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class ConverteTelCelular implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		String telCelular = value;
		int digitos = value.replace("(", "").replace(")", "").replace(" ", "").replace("-", "").length();
		if(digitos == 11) {
			telCelular = value.replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
		}
		
		return telCelular;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		String telCelular = value.toString();
		if( telCelular != null && telCelular.length() == 11) {
			telCelular = 
					"(" + telCelular.substring(0,1) + ") " + 
					telCelular.substring(1,2) + " " + 
					telCelular.substring(2, 6) + "-" + 
					telCelular.substring(6, 11);
		}
		
		return telCelular;
	}
}

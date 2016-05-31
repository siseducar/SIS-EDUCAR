package modulos.sisEducar.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class ConverteTelResidencial implements Converter{
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		String telResidencial = value;
		int digitos = value.replace("(", "").replace(")", "").replace("-", "").replace(" ", "").length();
		if(digitos == 10) {
			telResidencial = value.replace(".", "").replace("-", "").replace(" ", "").replace("(", "").replace(")", "");
		}
		
		return telResidencial;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		String telResidencial = value.toString();
		if( telResidencial != null && telResidencial.length() == 10) {
			telResidencial = "(" + telResidencial.substring(0,2) + ")" +
					telResidencial.substring(2,6) + "-" + 
					telResidencial.substring(6, 10);
		} else {
			telResidencial = null;
		}
		
		return telResidencial;
	}
}

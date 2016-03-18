package modulos.sisEducar.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class ConverteRG implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		String rg = value;
		int digitos = value.replace(".", "").replace("-", "").replace(" ", "").length();
		if(digitos == 9) {
			rg = value.replace(".", "").replace("-", "").replace(" ", "");
		}
		
		return rg;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		String rg = value.toString();
		if( rg != null && rg.length() == 9) {
			rg = rg.substring(0,2) + "." + rg.substring(2,4) + "." + rg.substring(4, 7) + "-" + rg.substring(9);
		}
		return rg;
	}
}

package modulos.sisEducar.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="converte.cep")
public class ConverteCEP implements Converter{

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		String cep = null;
		int digitos = value.replace("-", "").length();
		if (digitos == 8) {
			cep = value.replace("-", "");
		}
		else if (digitos == 9) {
        	cep = value.replace("-", "").replace(".", "");
        }
        return cep;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
    	String cep = value.toString();
    	if (cep != null && cep.length() == 8) {    		
    		cep = cep.substring(0, 5) + "-" + cep.substring(5, 8);
    	} else {
    		cep = null;
    	}
    	return cep;
    }
}

package modulos.sisEducar.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class ConverteCEP implements Converter{

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		String cnpj = value;
		int digitos = value.replace(".", "").replace("-", "").length();
        if (digitos == 8) {
        	cnpj = value;
        	cnpj = value.replace(".", "").replace("-", "");
        }
        return cnpj;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
    	String cep = value.toString();
    	if (cep != null && cep.length() == 10)
    		cep = cep.substring(0, 2) + "." + cep.substring(2, 5) + "-" + cep.substring(5, cep.length());
    	return cep;
    }
}

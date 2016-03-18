package modulos.sisEducar.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class ConverteCNPJ implements Converter{

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		String cnpj = value;
		int digitos = value.replace(".", "").replace("-", "").replace("/", "").length();
        if (digitos == 14) {
        	cnpj = value.replaceAll(".", "").replaceAll("-", "").replaceAll("/", "");
        }
        return cnpj;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
    	String cnpj = value.toString();
    	if (cnpj != null && cnpj.length() == 14)
    		cnpj = cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8 ) + "/" + cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14);
    	return cnpj;
    }
}

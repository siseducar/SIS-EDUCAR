package modulos.sisEducar.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="converte.cnpj")
public class ConverteCNPJ implements Converter{

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		String cnpj = value;
		int digitos = value.replace(".", "").replace("-", "").replace("/", "").length();
        if (digitos == 14) {
        	cnpj = value.replace(".", "").replace("-", "").replace("/", "");
        }
        return cnpj;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
    	String cnpj = value.toString();
    	if (cnpj != null && cnpj.length() == 14) {    		
    		cnpj = cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8 ) + "/" + cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14);
    	} else {
    		cnpj = null;
    	}
    	return cnpj;
    }
}

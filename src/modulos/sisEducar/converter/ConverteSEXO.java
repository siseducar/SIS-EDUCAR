package modulos.sisEducar.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="converterSEXO")
public class ConverteSEXO implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		String cpf = value;
		int digitos = value.replace(".", "").replace("-", "").replace(" ", "").length();
		if(digitos == 11) {
			cpf = value.replace(".", "").replace("-", "").replace(" ", "").replace("_", "");
		}
		
		return cpf;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		String cpf = value.toString();
		if( cpf != null && cpf.length() == 11) {
			cpf = cpf.substring(0,3) + "." + cpf.substring(3,6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
		} else {
			cpf = null;
		}
		
		return cpf;
	}
}

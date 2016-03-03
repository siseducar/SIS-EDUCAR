package modulos.sisEducar.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="converterCPF")
public class ConverteCPF implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		
		/*
         * Irá converter CPF formatado para um sem pontos e traço.
         * Ex.: 000.000.000-00 torna-se 00000000000.
         * 
         */
		String cpf = value;
		
		if(value != null && !value.equals("")) {
			cpf = value.replace(".", "").replace("-", "").replace(" ", "");
		}
		
		return cpf;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		/*
         * Irá converter CPF não formatado para um com pontos e traço.
         * Ex.: 00000000000 torna-se 000.000.000-00.
         */
		String cpf = value.toString();
		if( cpf != null && cpf.length() == 11) {
			cpf = cpf.substring(0,3) + "." + cpf.substring(3,6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
		}
		
		return cpf;
	}

}

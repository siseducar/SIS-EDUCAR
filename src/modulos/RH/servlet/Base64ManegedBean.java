package modulos.RH.servlet;

import java.io.IOException;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import bwmorg.bouncycastle.util.encoders.Base64;
import modulos.sisEducar.converter.ImagemBase64; 

@ManagedBean(value = "base64MB")
@ViewScoped
public class Base64ManegedBean { 
	
	private Part imagem;
	
	public void salvar(){ 
		String formato = imagem.getContentType(); 
		String nome = imagem.getName(); 
		byte[] imageAsByte = new byte[(int) imagem.getSize()]; 
		
		try { 
			imagem.getInputStream().read(imageAsByte); 
			ImagemBase64 ib4 = new ImagemBase64(); 
			String base64AsString = new String(Base64.encode(imageAsByte)); 
			ib4.setB64(base64AsString); 
			ib4.setFormato(formato); 
			ib4.setNome(nome); 
					
			System.out.println(ib4.getB64()); 
		} catch (IOException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace();  
		}
		
	}
	public Part getImagem() { 
		return imagem; 
	} 
	
	public void setImagem(Part imagem) { 
		this.imagem = imagem; 
	} 
}


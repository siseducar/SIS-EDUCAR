package modulos.sisEducar.utils;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
 
@ManagedBean(name="contextPathController")
@SessionScoped
public class ContextPathController implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	private Date date;
    private String path;
 
    @PostConstruct
    public void inicializar(){
        date = new Date();
    }
 
    public String getPath() {
        return path;
    }
 
    public void setPath(String path) {
        this.path = path;
    }
 
    public Date getDate() {
        return date;
    }
 
    public void setDate(Date date) {
        this.date = date;
    }
}
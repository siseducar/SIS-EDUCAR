package modulos.sisEducar.utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.om.Pessoa;
 
@ManagedBean(name="themeService", eager = true)
@ApplicationScoped
public class ThemeService 
{
    private List<Theme> themes;
     
    @PostConstruct
    public void init()
    {
    	try 
    	{
    		themes = new ArrayList<Theme>();
    		buscarPessoas();
		} 
    	catch (Exception e) 
    	{
			Logs.addError("init", "");
		}
    }
    
    /**
     * Busca todas as pessoas e adiciona no combo auto complete de pessoa
     * @author João Paulo
     * @throws SQLException
     */
    public void buscarPessoas() throws SQLException
    {
    	themes = new ArrayList<Theme>();
    	List<Pessoa> pessoas = null;
    	pessoas = new PessoaDAO().obtemTodos();
    	
    	if(pessoas!=null && pessoas.size() >0)
    	{
    		for (int i = 0; i < pessoas.size(); i++) 
    		{
    			if(pessoas.get(i).getNome()!=null)
    			{
    				themes.add(new Theme(i, pessoas.get(i).getNome(), pessoas.get(i).getNome()));
    			}
			}
    	}
    }
     
    public List<Theme> getThemes() 
    {
        return themes;
    } 
}
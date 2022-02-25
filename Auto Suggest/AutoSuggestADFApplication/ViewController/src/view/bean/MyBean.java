package view.bean;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import javax.faces.model.SelectItem;

import javax.naming.InitialContext;

import javax.sql.DataSource;

import oracle.adf.view.rich.model.AutoSuggestUIHints;

public class MyBean
{
  public MyBean()
  {
  }

  private Connection getConnection()
  {
    try
    {
      InitialContext initialContext = new InitialContext();
      DataSource ds = (DataSource) initialContext.lookup("hrDS");
      Connection conn = ds.getConnection();
      return conn;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public List firstNameSuggestItems(FacesContext facesContext, AutoSuggestUIHints autoSuggestUIHints)
  {
    List<SelectItem> selectItems = new ArrayList<SelectItem>();

     String sql = "select first_name from employees where first_name like '" + autoSuggestUIHints.getSubmittedValue() + "%'";
     java.sql.PreparedStatement stat = null;
     java.sql.ResultSet rs = null;
     Connection conn = null;
     try
     {
       conn = getConnection();
       stat = conn.prepareStatement(sql);
       rs = stat.executeQuery();
       while (rs.next())
       {
         selectItems.add(new SelectItem(rs.getString(1), rs.getString(1)));
       }
     }
     catch (Exception e)
     {
     }
     finally
     {
       try
       {
         rs.close();
         stat.close();
         conn.close();
       }
       catch (Exception e)
       {
       }
     }
    
    return selectItems;
  }
}

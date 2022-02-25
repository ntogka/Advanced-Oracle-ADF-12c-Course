package view.bean;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.util.List;

import javax.naming.InitialContext;

import javax.sql.DataSource;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import org.apache.myfaces.trinidad.event.PollEvent;

public class MyBean
  implements Serializable
{
  private RichOutputText secondsOutputText;
  private RichOutputText minutesOutputText;
  private RichOutputText numberOfEmployees;
  
  private int seconds;
  private int minutes;

  public MyBean()
  {
    seconds = 0;
    minutes = 0;
  }
  
  public static Connection getConnection()
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

  public void setSecondsOutputText(RichOutputText secondsOutputText)
  {
    this.secondsOutputText = secondsOutputText;
  }

  public RichOutputText getSecondsOutputText()
  {
    return secondsOutputText;
  }

  public void setMinutesOutputText(RichOutputText minutesOutputText)
  {
    this.minutesOutputText = minutesOutputText;
  }

  public RichOutputText getMinutesOutputText()
  {
    return minutesOutputText;
  }

  public void setNumberOfEmployees(RichOutputText numberOfEmployees)
  {
    this.numberOfEmployees = numberOfEmployees;
  }

  public RichOutputText getNumberOfEmployees()
  {
    return numberOfEmployees;
  }
  
  private void adjustTime()
  {
    seconds++;
    if(seconds < 10)
    {
      getSecondsOutputText().setValue("0" + seconds);
      if(minutes < 10)
      {
        getMinutesOutputText().setValue("0" + minutes);
        return;
      }
      
      if(minutes > 9 && minutes < 60)
      {
        getMinutesOutputText().setValue(minutes);
        return;
      }      
      return;
    }
    
    if(seconds > 9 && seconds < 60)
    {
      getSecondsOutputText().setValue(seconds);
      if(minutes < 10)
      {
        getMinutesOutputText().setValue("0" + minutes);
        return;
      }
      
      if(minutes > 9 && minutes < 60)
      {
        getMinutesOutputText().setValue(minutes);
        return;
      }      
      return;
    }
    
    if(seconds == 60)
    {
      seconds = 0;
      minutes++;
      getSecondsOutputText().setValue(seconds);
      if(minutes < 10)
      {
        getMinutesOutputText().setValue("0" + minutes);
        return;
      }
      
      if(minutes > 9 && minutes < 60)
      {
        getMinutesOutputText().setValue(minutes);
        return;
      }      
    }
  }

  private int getEmployeesCount()
  {
    Connection conn = null;
    PreparedStatement stat = null;
    ResultSet rs = null;
    try
    {
      conn = getConnection();
      String sql = "select count(*) from employees";
      stat = conn.prepareStatement(sql);
      rs = stat.executeQuery();
      while (rs.next())
      {
        return rs.getInt(1);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
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
        e.printStackTrace();
      }
    }
    return 0;
  }

  public void pollListener(PollEvent pollEvent)
  {
    adjustTime();
    getNumberOfEmployees().setValue(getEmployeesCount());
  }
}

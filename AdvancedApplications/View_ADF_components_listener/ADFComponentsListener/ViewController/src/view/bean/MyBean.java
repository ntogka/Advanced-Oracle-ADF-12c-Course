package view.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.MethodExpression;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import javax.naming.InitialContext;

import javax.sql.DataSource;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.event.LaunchPopupEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.adf.view.rich.event.ReturnPopupEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import oracle.jbo.ViewObject;

import org.apache
.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;

public class MyBean
{
  public MyBean()
  {
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
  
  private int getEmployeesCount(Object deptId)
  {
    Connection conn = null;
    PreparedStatement stat = null;
    ResultSet rs = null;
    try
    {
      conn = getConnection();
      String sql = "select count(*) from employees where department_id= " + deptId;
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
  
  public void showSuccessfulMessage(String message) 
  {
    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, null, null);
    fm.setDetail(message);
    FacesContext.getCurrentInstance().addMessage(null, fm);
  }

  private Object invokeMethod(String expr, Class returnType, Class[] argTypes, Object[] args)
  {
    FacesContext fc = FacesContext.getCurrentInstance();
    ELContext elctx = fc.getELContext();
    ExpressionFactory elFactory = fc.getApplication().getExpressionFactory();
    MethodExpression methodExpr = elFactory.createMethodExpression(elctx, expr, returnType, argTypes);
    return methodExpr.invoke(elctx, args);
  }

  public void employeeTabListener(DisclosureEvent disclosureEvent)
  {
    System.out.println("I am in employee tab listener.... " + disclosureEvent.isExpanded());
    if(disclosureEvent.isExpanded())
    {
      // user enter to the tab
      
      BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
      DCIteratorBinding deptIterator = (DCIteratorBinding) bindings.get("DepartmentsView1Iterator");
      Row r = deptIterator.getCurrentRow();
      Object deptId = r.getAttribute("DepartmentId");
      
      DCIteratorBinding empIterator = (DCIteratorBinding) bindings.get("EmployeesView1Iterator");
      ViewObject empVO = empIterator.getViewObject();
      empVO.setWhereClause("DEPARTMENT_ID = " + deptId);
      empVO.executeQuery();

    }
    else
    {
      // user left the tab
    }
  }

  public void firstNameValueChangeListener(ValueChangeEvent valueChangeEvent)
  {
    System.out.println("New Entered Value is: " + valueChangeEvent.getNewValue());
    
    BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
    DCIteratorBinding empIterator = (DCIteratorBinding) bindings.get("EmployeesView1Iterator");
    Row r = empIterator.getCurrentRow();
    r.setAttribute("LastName", valueChangeEvent.getNewValue());
  }

  public void deptListValueChangeListener(ValueChangeEvent valueChangeEvent)
  {
    
    FacesContext fc = FacesContext.getCurrentInstance();
    valueChangeEvent.getComponent().processUpdates(fc);

    
    System.out.println("Selected dept: " + valueChangeEvent.getNewValue());
    BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();

    DCIteratorBinding empIterator = (DCIteratorBinding) bindings.get("EmployeesView1Iterator");
    ViewObject empVO = empIterator.getViewObject();
    empVO.setWhereClause("DEPARTMENT_ID = " + valueChangeEvent.getNewValue());
    empVO.executeQuery();
    
  }

  public void newDeptPopupFetchListener(PopupFetchEvent popupFetchEvent)
  {
    System.out.println("I am in Popup Fetch Listener");
    BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
    OperationBinding op = bindings.getOperationBinding("CreateInsert");
    op.execute();
  }

  public void deptPopupCancelListener(PopupCanceledEvent popupCanceledEvent)
  {
    System.out.println("I am in Popup Cancel Listener");

    BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
    OperationBinding op = bindings.getOperationBinding("Rollback");
    op.execute();
  }

  public void deptTableSelectionListener(SelectionEvent selectionEvent)
  {
    // Add event code here...     #{bindings.DepartmentsView11.collectionModel.makeCurrent}
    
    invokeMethod("#{bindings.DepartmentsView11.collectionModel.makeCurrent}", null, new Class[] { SelectionEvent.class }, new Object[] { selectionEvent }); 
    
    BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
    DCIteratorBinding deptIterator = (DCIteratorBinding) bindings.get("DepartmentsView1Iterator");
    Row r = deptIterator.getCurrentRow();
    Object deptId = r.getAttribute("DepartmentId");
    
    showSuccessfulMessage("Number of employees for the selected department (" + deptId + ") is: " + getEmployeesCount(deptId));
    
  }

  public void lovValueChangeListener(ValueChangeEvent valueChangeEvent)
  {
    System.out.println("LOV Value Change Listener: " + valueChangeEvent.getNewValue());
  }

  public void lovLaunchPopupListener(LaunchPopupEvent launchPopupEvent)
  {
    System.out.println("LOV Popup Launch Listener");
  }

  public void lovReturnPopupListener(ReturnPopupEvent returnPopupEvent)
  {
    System.out.println("LOV Popup Return Listener: " + returnPopupEvent.getReturnValue());
  }

  public void deptPanelBoxListener(DisclosureEvent disclosureEvent)
  {
    System.out.println("I am in Dept PanelBox Listener: " + disclosureEvent.isExpanded());
  }
}

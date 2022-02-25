package view.bean;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import model.vo.EmployeesViewImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.BindingContainer;

public class EmpBean
{
  private RichInputText userDeptInputText;
  private RichInputText salaryInputText;

  public EmpBean()
  {
  }

  public String loginAction()
  {
    Object deptId = getUserDeptInputText().getValue();
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userDept", deptId);
    return "emps";
  }

  public void setUserDeptInputText(RichInputText userDeptInputText)
  {
    this.userDeptInputText = userDeptInputText;
  }

  public RichInputText getUserDeptInputText()
  {
    return userDeptInputText;
  }
  
  private BindingContainer getBindings()
  {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
  }

  public void filterWithDept(ActionEvent actionEvent)
  {
    DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("EmployeesView1Iterator");
    EmployeesViewImpl empVO = (EmployeesViewImpl) iter.getViewObject();
    empVO.getEmpsForDept();
    
  }

  public void filterWithSalary(ActionEvent actionEvent)
  {
    Object salary = getSalaryInputText().getValue();
    
    DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("EmployeesView1Iterator");
    EmployeesViewImpl empVO = (EmployeesViewImpl) iter.getViewObject();
    empVO.getEmpsBySalary(Double.parseDouble(String.valueOf(salary)));
  }

  public void setSalaryInputText(RichInputText salaryInputText)
  {
    this.salaryInputText = salaryInputText;
  }

  public RichInputText getSalaryInputText()
  {
    return salaryInputText;
  }
}

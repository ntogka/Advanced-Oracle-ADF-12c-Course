package view.bean;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

public class DeptsBean
{
  private RichInputText deptIdComponent;
  private RichInputText deptNameComponent;

  public DeptsBean()
  {
  }

  public void setDeptIdComponent(RichInputText deptIdComponent)
  {
    this.deptIdComponent = deptIdComponent;
  }

  public RichInputText getDeptIdComponent()
  {
    return deptIdComponent;
  }

  public void setDeptNameComponent(RichInputText deptNameComponent)
  {
    this.deptNameComponent = deptNameComponent;
  }

  public RichInputText getDeptNameComponent()
  {
    return deptNameComponent;
  }

  private BindingContainer getBindings()
  {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
  }

  public void insertDept(ActionEvent actionEvent)
  {
    Object deptId = getDeptIdComponent().getValue();
    Object deptName = getDeptNameComponent().getValue();
    
    DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("ProgrammaticDepartmentsView1Iterator");
    
    ViewObject vo = iter.getViewObject();
    Row r = vo.createRow();
    r.setAttribute("DeprId", deptId);
    r.setAttribute("DeptName", deptName);
    vo.insertRow(r);
    
    getDeptIdComponent().setValue(null);
    getDeptNameComponent().setValue(null);
    
  }

  public void saveDeptsActionListener(ActionEvent actionEvent)
  {
    DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("ProgrammaticDepartmentsView1Iterator");
    for (int i = 0; i < iter.getViewObject().getEstimatedRowCount(); i++)
    {
      Row pRow = iter.getRowAtRangeIndex(i);
      callMethodBinding(pRow.getAttribute("DeprId"), pRow.getAttribute("DeptName"));
    }
    OperationBinding op = getBindings().getOperationBinding("Commit");
    op.execute();
    iter.getViewObject().executeQuery();
  }
  
  private void callMethodBinding(Object deptId, Object deptName)
  {
    OperationBinding op = getBindings().getOperationBinding("insertDeptToDatabase");
    op.getParamsMap().put("deptId", Integer.parseInt(deptId.toString()));
    op.getParamsMap().put("deptName", String.valueOf(deptName));
    op.execute();
  }
}

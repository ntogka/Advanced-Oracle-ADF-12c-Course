package view.bean;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.fragment.UIXDeclarativeComponent;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;


public class MyDecBean
{
  public MyDecBean()
  {
  }

  private Object getValueObject(String expr, Class returnType)
  {
    FacesContext fc = FacesContext.getCurrentInstance();
    ELContext elctx = fc.getELContext();
    ExpressionFactory elFactory = fc.getApplication().getExpressionFactory();
    ValueExpression valueExpr = elFactory.createValueExpression(elctx, expr, returnType);
    return valueExpr.getValue(elctx);
  }
  
  public void deleteDialogListener(DialogEvent dialogEvent)
  {
    BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
    
    UIXDeclarativeComponent myDeleteComp = (UIXDeclarativeComponent) getValueObject("#{comp}", UIXDeclarativeComponent.class);
    String s = (String) myDeleteComp.getAttributes().get("OperationId");
    
    OperationBinding op = bindings.getOperationBinding(s);
    op.execute();
    
    op = bindings.getOperationBinding("Commit");
    op.execute();
  }
}

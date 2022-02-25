package view.bean;

import javax.faces.event.ActionEvent;

import mysharedcode.MyADFUtils;

public class MyBean
{
  public MyBean()
  {
  }

  public void showEmpAction(ActionEvent actionEvent)
  {
    MyADFUtils.showPopup("pt1:pc1:p1");
    MyADFUtils.showSuccessfulMessage("Emps Popup Opened successfully");
    
  }
}

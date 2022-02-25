package view.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputFile;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.model.UploadedFile;

public class MyBean
{
  private RichInputFile inputFileComponent;

  public MyBean()
  {
  }

  public void setInputFileComponent(RichInputFile inputFileComponent)
  {
    this.inputFileComponent = inputFileComponent;
  }

  public RichInputFile getInputFileComponent()
  {
    return inputFileComponent;
  }

  public void uploadFile(ValueChangeEvent valueChangeEvent)
  {
    String uploadedFileName = uploadFileToLocation ((UploadedFile)valueChangeEvent.getNewValue());
    
    
    BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
    DCIteratorBinding iter = (DCIteratorBinding) bindings.get("EmployeesView1Iterator");
    iter.getCurrentRow().setAttribute("EmpDoc", uploadedFileName);
    OperationBinding op = bindings.getOperationBinding("Commit");
    op.execute();
    getInputFileComponent().resetValue();
  }

  private String uploadFileToLocation(UploadedFile file)
  {
    String fileName = System.currentTimeMillis() + "_" + file.getFilename();
    String path = "C:\\upload\\" + fileName;

    InputStream inputStream = null;
    try
    {
      FileOutputStream out = new FileOutputStream(path);
      inputStream = file.getInputStream();
      byte[] buffer = new byte[8192];
      int bytesRead = 0;
      while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1)
      {
        out.write(buffer, 0, bytesRead);
      }
      out.flush();
      out.close();
    }
    catch (Exception ex)
    {
      // handle exception
      ex.printStackTrace();
    }
    finally
    {
      try
      {
        inputStream.close();
      }
      catch (IOException e)
      {
      }
    }
    return fileName;
  }

  public void downloadDoc(FacesContext facesContext, java.io.OutputStream outputStream) throws IOException
  {
    BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
    DCIteratorBinding iter = (DCIteratorBinding) bindings.get("EmployeesView1Iterator");
    String path = "C:\\upload\\" + iter.getCurrentRow().getAttribute("EmpDoc");

    File file = new File(path);
    byte[] b = new byte[(int) file.length()];
    FileInputStream fileInputStream = new FileInputStream(file);
    fileInputStream.read(b);

    outputStream.write(b);
    outputStream.flush();

  }


}

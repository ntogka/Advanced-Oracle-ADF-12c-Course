package mysharedcode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.math.BigDecimal;

import oracle.jbo.ApplicationModule;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichQuery;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.DBTransactionImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SequenceImpl;
import oracle.jbo.server.ViewRowImpl;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class MyADFUtils
{
  
/************************************************** Private Methods Section **************************************************/
  
  private static Object resolveExpression(String expression)
  {
    FacesContext ctx = getFacesContext();
    ELContext elContext = ctx.getELContext();
    ValueExpression ve = ctx.getApplication().getExpressionFactory().createValueExpression(elContext, expression, Object.class);
    return ve.getValue(elContext);
  }

  private static BindingContainer getBindings()
  {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
  }

  private static UIComponent findComponent(UIComponent base, String id)
  {
    if (id.equals(base.getId()))
    {
      return base;
    }

    UIComponent children = null;
    UIComponent result = null;
    Iterator childrens = base.getFacetsAndChildren();
    while ((childrens.hasNext()) && (result == null))
    {
      children = (UIComponent) childrens.next();
      if (id.equals(children.getId()))
      {
        result = children;
      }
      else
      {
        result = findComponent(children, id);
        if (result == null)
        {
          continue;
        }
      }
    }
    return result;
  }

  private static void writeJavaScriptToClient(String script)
  {
    FacesContext fctx = FacesContext.getCurrentInstance();
    ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
    erks.addScript(fctx, script);
  }

  private static String getStringSafely(ResourceBundle bundle, String key, String defaultValue)
  {
    String resource = null;
    try
    {
      resource = bundle.getString(key);
    }
    catch (MissingResourceException mrex)
    {
      if (defaultValue != null)
      {
        resource = defaultValue;
      }
      else
      {
        resource = "Missing resource: " + key;
      }
    }
    return resource;
  }

  private static ResourceBundle getBundle(String baseName)
  {
    FacesContext ctx = getFacesContext();
    UIViewRoot uiRoot = ctx.getViewRoot();
    Locale locale = uiRoot.getLocale();
    ClassLoader ldr = Thread.currentThread().getContextClassLoader();
    return ResourceBundle.getBundle(baseName, locale, ldr);
  }

  private static Object invokeMethod(String expr, Class returnType, Class[] argTypes, Object[] args)
  {
    FacesContext fc = FacesContext.getCurrentInstance();
    ELContext elctx = fc.getELContext();
    ExpressionFactory elFactory = fc.getApplication().getExpressionFactory();
    MethodExpression methodExpr = elFactory.createMethodExpression(elctx, expr, returnType, argTypes);
    return methodExpr.invoke(elctx, args);
  }
  
  private static ServletContext getContext()
  {
    return (ServletContext) getFacesContext().getExternalContext().getContext();
  }
/************************************************** End Private Methods Section **************************************************/

/** function return application facesContext */
  public static FacesContext getFacesContext()
  {
    return FacesContext.getCurrentInstance();
  }

/** function return ApplicationModule Object */
  public static ApplicationModule getDefaultApplicationModule()
  {
    return (ApplicationModule) resolveExpression("#{data.AppModuleDataControl.dataProvider}");
  }

/** function take iterator name and return iterator object */
  public static DCIteratorBinding getIterator(String iteratorName)
  {
    DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(iteratorName);
    if (iter == null)
    {
      throw new RuntimeException("Iterator '" + iteratorName + "' not found");
    }
    return iter;
  }

/** function take operation name (from page definition) and execute this operation (e.g Commit, Rollback, Next, CreateInsert, Delete, .....) */
  public static void executeOperation(String operationName)
  {
    OperationBinding operationBinding = getBindings().getOperationBinding(operationName);
    operationBinding.execute();
  }

/** function used to commit changes to database*/
  public static void commit()
  {
    getDefaultDBTransaction().commit();
  }

/** function used to rollback any changes to the last commit point*/
  public static void rollback()
  {
    executeOperation("Rollback");
  }

/** function used to rollback any changes to the last commit point without changing current row of the passed iterator*/
  public static void rollbackAndBeInTheCurrentRow(String iteratorName)
  {
    try
    {
      DCIteratorBinding locationsIter = getIterator(iteratorName);
      Row lRow = locationsIter.getCurrentRow();
      Key key = null;
      if (lRow != null)
      {
        key = lRow.getKey();
      }
      rollback();
      if (key != null)
      {
        locationsIter.setCurrentRowWithKey(key.toStringFormat(true));
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

/** function return application database transaction object */
  public static DBTransaction getDefaultDBTransaction()
  {
    return (DBTransaction) getDefaultApplicationModule().getTransaction();
  }

/** function take the component id and will return the component object */
  public static UIComponent findComponentInRoot(String componentId)
  {
    UIComponent component = null;
    FacesContext context = FacesContext.getCurrentInstance();
    if (context != null)
    {
      UIComponent root = context.getViewRoot();
      component = findComponent(root, componentId);
    }
    return component;
  }

/** function take popup id and will show this popup */
  public static void showPopup(String popupName)
  {
    StringBuilder strb = new StringBuilder("AdfPage.PAGE.findComponentByAbsoluteId(\"" + popupName + "\").show();");
    writeJavaScriptToClient(strb.toString());
  }

/** function take popup id and component id and will show this popup behind with this component */
  public static void showPopup(String popupName, String alignId)
  {
    StringBuilder strb = new StringBuilder("var pop = AdfPage.PAGE.findComponentByAbsoluteId(" + popupName + ");");
    strb.append("var hints = {};\n");
    strb.append("hints[AdfRichPopup.HINT_ALIGN_ID] = '" + alignId + "';");
    strb.append("pop.show(hints);");
    writeJavaScriptToClient(strb.toString());
  }
  
/** function take iterator name and attribute name and value then set this value to the attribute in the current row of the iterator */
  public static void setAttributeInIterator(String iteratorName, String attributeName, Object value)
  {
    if (getIterator(iteratorName).getCurrentRow() != null)
    {
      getIterator(iteratorName).getCurrentRow().setAttribute(attributeName, value);
    }
  }

/** function take iterator name and attribute name then return the value of the attribute from the current row of the iterator */
  public static Object getAttributeFromIterator(String iteratorName, String attributeName)
  {
    if (getIterator(iteratorName).getCurrentRow() != null)
    {
      return getIterator(iteratorName).getCurrentRow().getAttribute(attributeName);
    }
    return null;
  }

/** function take URL and open it in new window */
  public static void openUrlInNewWindow(String url)
  {
    StringBuilder strb = new StringBuilder("window.open('" + url + "');");
    writeJavaScriptToClient(strb.toString());
  }

/** function return current application database connection */
  public static Connection getConnection()
  {
    DBTransactionImpl dbt = (DBTransactionImpl) getDefaultDBTransaction();
    Connection conn = dbt.getPersistManagerConnection();
    return conn;
  }

/** function take session variable name and object then save this object inside the variable name in the session scope */
  public static void putInSession(String key, Object object)
  {
    try
    {
      FacesContext ctx = getFacesContext();
      HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);
      session.setAttribute(key, object);
    }
    catch (Exception e)
    {
      System.err.println("storeOnSession -- " + e);
    }
  }

/** function take session variable name and return the value inside this variable name from the session scope */
  public static Object getFromSession(String key)
  {
    try
    {
      FacesContext ctx = getFacesContext();
      Map sessionState = ctx.getExternalContext().getSessionMap();
      return sessionState.get(key);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }

/** function take request variable name and object then save this object inside the variable name in the request scope */
  public static void putInRequest(String name, Object value)
  {
    getFacesContext().getExternalContext().getRequestMap().put(name, value);
  }
  
/** function take request variable name and return the value inside this variable name from the request scope */
  public static Object getFromRequest(String name)
  {
    return getFacesContext().getExternalContext().getRequestMap().get(name);
  }

/** function take the resource bundle base Name and the key and return the value from resource bundle */
  public static String getStringFromBundle(String baseName, String key)
  {
    ResourceBundle bundle = getBundle(baseName);
    return getStringSafely(bundle, key, null);
  }

/** function return current application locale */
  public static Locale getLocale()
  {
    return getFacesContext().getViewRoot().getLocale();
  }

/** function return the current jsp name */
  public static String getPageName()
  {
    FacesContext context = getFacesContext();
    String viewId = context.getViewRoot().getViewId();
    int dotIndex = viewId.indexOf(".");
    if (dotIndex < 0)
    {
      return viewId;
    }
    String fileName = viewId.substring(1, dotIndex);
    return fileName;
  }

/** function used to refresh the hole jsp page */
  public static void refreshPage()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    String currentView = context.getViewRoot().getViewId();
    ViewHandler vh = context.getApplication().getViewHandler();
    UIViewRoot UIV = vh.createView(context, currentView);
    UIV.setViewId(currentView);
    context.setViewRoot(UIV);
  }

/** function take outcome and will navigate to the page */
  public static void navigateToPage(String outcome)
  {
    FacesContext fc = FacesContext.getCurrentInstance();
    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, outcome);
  }

/** function For Cancelling any Changes Happened In The Row (Create New Row or Update Row) */
  public static void cancelChangesInCurrentRow(String iteratorName) 
  {
    DCIteratorBinding iterBinding = getIterator(iteratorName);
    ViewObject vo = iterBinding.getViewObject();
    Row currentRow = vo.getCurrentRow();
    currentRow.refresh(Row.REFRESH_REMOVE_NEW_ROWS | Row.REFRESH_WITH_DB_FORGET_CHANGES);
  }
  
/** function take a message and display it as error message (error icon will appear) */  
  public static void showErrorMessage(String message) 
  {
    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, null);
    fm.setDetail(message);
    FacesContext.getCurrentInstance().addMessage(null, fm);
  }

/** function take a message and display it as information message (information icon will appear) */  
  public static void showSuccessfulMessage(String message) 
  {
    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, null, null);
    fm.setDetail(message);
    FacesContext.getCurrentInstance().addMessage(null, fm);
  }

/** function take a message and display it as warning message (warn icon will appear) */  
  public static void showWarnMessage(String message) 
  {
    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, null, null);
    fm.setDetail(message);
    FacesContext.getCurrentInstance().addMessage(null, fm);
  }

/** function take viewobject name and refresh this view object */  
  public static void refreshViewObject(String viewObjectName) 
  {
    ViewObject vo = getDefaultApplicationModule().findViewObject(viewObjectName);
    vo.executeQuery();
  }

/** function take iterator name and refresh the view object of that iterator without losing the current row */
  public static void refreshVOByIteratorName(String iteratorName)
  {
    try
    {
      DCIteratorBinding locationsIter = getIterator(iteratorName);
      Row lRow = locationsIter.getCurrentRow();
      Key key = null;
      if (lRow != null)
      {
        key = lRow.getKey();
      }
      locationsIter.getViewObject().executeQuery();
      if (key != null)
      {
        locationsIter.setCurrentRowWithKey(key.toStringFormat(true));
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

/** function take iterator name and refresh this iterator */   
  public static void refreshIterator(String iteratorName) 
  {
    DCIteratorBinding dciter = getIterator(iteratorName);
    dciter.executeQuery();
    dciter.refresh(DCIteratorBinding.RANGESIZE_UNLIMITED);
  }

/** function take iterator name and the attribute name then sum all the attribute values for all rows in the iterator */
  public static double getSumOfAttribute(String iteratorName, String attributeName)
  {    
    DCIteratorBinding dciter = getIterator(iteratorName);
    double sum = 0;
    Row[] rows = dciter.getViewObject().getAllRowsInRange();
    for (Row r : rows)
    {
      if (r != null && r.getAttribute(attributeName) != null)
      {
        sum = sum + Double.parseDouble(r.getAttribute(attributeName).toString());
      }
    }
    return sum; 
  }

/** function take iterator name and the attribute name then sum all the attribute values for all rows in the iterator and return long value*/  
  public static long getSumOfAttributeAsLong(String iteratorName, String attributeName)
  {
    DCIteratorBinding dciter = getIterator(iteratorName);
    long sum = 0;
    Row[] rows = dciter.getViewObject().getAllRowsInRange();
    for (Row r : rows)
    {
      if (r != null && r.getAttribute(attributeName) != null)
      {
        sum = sum + Long.parseLong(r.getAttribute(attributeName).toString());
      }
    }
    return sum; 
  }

/** function take component object and refresh this component */
  public static void refreshComponent(UIComponent component)
  {
    RequestContext.getCurrentInstance().addPartialTarget(component);
  }
  
/** function used to set current local*/
  public static void setBrowserLocal(String locale)
  {
    FacesContext ctx = FacesContext.getCurrentInstance();
    UIViewRoot uiRoot = ctx.getViewRoot();
    Locale bLocale = new Locale(locale);
    uiRoot.setLocale(bLocale);
  }

/** function return the current browser local ( e.g. "ar" - "en" - ....) */
  public static String getBrowserLocal()
  {
    FacesContext ctx = FacesContext.getCurrentInstance();
    UIViewRoot uiRoot = ctx.getViewRoot();
    return uiRoot.getLocale().toString();
  }
  
/** function used for uploading file */
  public static void uploadFile(ValueChangeEvent valueChangeEvent, String fileLocation, String fileName)
  {
    UploadedFile file = (UploadedFile) valueChangeEvent.getNewValue();
    if (fileLocation == null)
    {
      fileLocation = "c:/";
    }
    InputStream in;
    FileOutputStream out;
    boolean exists = (new File(fileLocation)).exists();
    if (!exists)
    {
      (new File(fileLocation)).mkdirs();
    }
    if (file != null && file.getLength() > 0)
    {
      FacesContext context = FacesContext.getCurrentInstance();
      FacesMessage message = new FacesMessage("File Uploaded  " + file.getFilename() + " (" + file.getLength() + " bytes)");
      context.addMessage(valueChangeEvent.getComponent().getClientId(context), message);
      try
      {
        out = new FileOutputStream(fileLocation + "" + fileName);
        in = file.getInputStream();
        for (int bytes = 0; bytes < file.getLength(); bytes++)
        {
          out.write(in.read());
        }
        in.close();
        out.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      String filename = file != null? file.getFilename(): null;
      String byteLength = file != null? "" + file.getLength(): "0";
      FacesContext context = FacesContext.getCurrentInstance();
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, " " + " " + filename + " (" + byteLength + " bytes)", null);
      context.addMessage(valueChangeEvent.getComponent().getClientId(context), message);
    }
  }

/** function used for downloading file */
  public static void downloadFile(java.io.OutputStream outputStream, String fileName) throws IOException
  {
    try
    {
      File file = new File(fileName);
      byte[] b = new byte[(int) file.length()];
      FileInputStream fileInputStream = new FileInputStream(file);
      fileInputStream.read(b);
      outputStream.write(b);
      outputStream.flush();
    }
    catch (Exception e)
    {
      FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, null);
      fm.setDetail("No file found");
      FacesContext.getCurrentInstance().addMessage(null, fm);
    }
  }

/** function take database sequence name and return the next value for this sequence */  
  public static BigDecimal getSequenceNextValue(String sequenceName)
  {
    SequenceImpl seq = new SequenceImpl(sequenceName, getDefaultDBTransaction());
    return new BigDecimal(seq.getSequenceNumber().toString());
  }

/** function take view object name and delete all rows in this viewobject */  
  public static void clearViewObject(String viewObjectName)
  {
    ViewObject vo = getDefaultApplicationModule().findViewObject(viewObjectName);
    vo.executeQuery();
    while (vo.hasNext())
    {
      vo.next().remove();
      if (vo.getEstimatedRowCount() > 0)
      {
        vo.first();
      }
    }
    if (vo.getEstimatedRowCount() > 0)
    {
      vo.getCurrentRow().remove();
    }
  }

/** function used to close preparedStatement */
  public static void close(PreparedStatement stat)
  {
    try
    {
      if(stat != null)
      {
        stat.close();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

/** function used to close ResultSet */
  public static void close(ResultSet rs)
  {
    try
    {
      if(rs != null)
      {
        rs.close();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

/** function used to close CallableStatement */
  public static void close(java.sql.CallableStatement cstat)
  {
    try
    {
      if(cstat != null)
      {
        cstat.close();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
           
/** function take a double number and integer number then round the double number with the integer number (e.g  roundToDecimals(15.22545112, 3) then the output will be 15.225) */ 
  public static double roundToDecimals(double d, int c)
  {
    int temp = (int) ((d * Math.pow(10, c)));
    return (((double) temp) / Math.pow(10, c));
  }

/** function take sql statement and return the result value e.g (getSqlDescription("select username from users where user_id=10") --> function will return username value) */
  public static String getSqlDescription(String sql)
  {
    PreparedStatement stat = null;
    ResultSet rs = null;
    try
    {
      stat = getDefaultDBTransaction().createPreparedStatement(sql, 1);
      rs = stat.executeQuery();
      while (rs.next())
      {
        return rs.getString(1);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      close(rs);
      close(stat);
    }
    return "";
  }

  /** function take sql statement and return the result as double */
  public static double getSqlAsDuoble(String sql)
  {
    PreparedStatement stat = null;
    ResultSet rs = null;
    try
    {
      stat = getDefaultDBTransaction().createPreparedStatement(sql, 1);
      rs = stat.executeQuery();
      while (rs.next())
      {
        return rs.getDouble(1);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      close(rs);
      close(stat);
    }
    return 0;
  }
  
  
  /** function take sql statement and return the result as long */
  public static long getSqlAsLong(String sql)
  {
    PreparedStatement stat = null;
    ResultSet rs = null;
    try
    {
      stat = getDefaultDBTransaction().createPreparedStatement(sql, 1);
      rs = stat.executeQuery();
      while (rs.next())
      {
        return rs.getLong(1);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      close(rs);
      close(stat);
    }
    return 0;
  }

/** function take dml sql statement and execute this statement then return number of records affects during the execution */
  public static int executeDML(String sql)
  {
    PreparedStatement stat = null;
    try
    {
      stat = getDefaultDBTransaction().createPreparedStatement(sql, 1);
      int result = stat.executeUpdate();
      return result;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      close(stat);
    }
    return 0;
  }

/** function take the name of the view object and return this view object */
  public static ViewObject getViewObjectByName(String viewObjectName)
  {
    return getDefaultApplicationModule().findViewObject(viewObjectName);
  }

/** function take iterator name and return the view object associated to this iterator */
  public static ViewObject getViewObjectFromIterator(String iteratorName)
  {
    return getIterator(iteratorName).getViewObject();
  }

/** function can be called when the developer override table selectionListener and want to make the selected row is the current row (e.g makeTableSelectedRowCurrentRow("#{bindings.UsersView1.treeModel.makeCurrent}", selectionEvent)) */    
  public static void makeTableSelectedRowCurrentRow(String exp, SelectionEvent selectionEvent)
  {
    invokeMethod(exp, null, new Class[] { SelectionEvent.class }, new Object[] { selectionEvent });  
  }

/** function take iterator name and attribute name and value then set the attribute with the value for all rows in the iterator */    
  public static void setValueToAllRowsInTheIterator(String iteratorName, String attributeName, Object value)
  {
    DCIteratorBinding itemIter = getIterator(iteratorName);
    for (int i = 0; i < itemIter.getViewObject().getEstimatedRowCount(); i++)
    {
      Row r = itemIter.getRowAtRangeIndex(i);
      r.setAttribute(attributeName, value);
    }
  }

/** function take the folder name (which is located inside Web Content folder) and will return the real path of that folder */   
  public static String getContextRealPath(String folderName)
  {
    String path = "";
    try
    {
      path = getContext().getRealPath("/" + folderName);
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
    return path;
  }

/** function take af:table component (RichTable object) and clear all columns filters */
  public static void clearTableFilters(RichTable tableComponent)
  {
    FilterableQueryDescriptor q = (FilterableQueryDescriptor) tableComponent.getFilterModel();
    Map<String, Object> m = q.getFilterCriteria();
    if (m != null)
    {
      m.clear();
    }
  }

/** function take af:query component (RichQuery object) and reset query fields  */  
  public static void resetQueryFields(RichQuery queryComponent)
  {
    QueryModel queryModel = queryComponent.getModel(); 
    QueryDescriptor queryDescriptor = queryComponent.getValue(); 
    queryModel.reset(queryDescriptor);
  }

/** function take componentId and set focus of this component  */  
  public static void setFocus(String componentId) 
  {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExtendedRenderKitService service = Service.getRenderKitService(facesContext, ExtendedRenderKitService.class);
    service.addScript(facesContext, "comp = AdfPage.PAGE.findComponent('"+componentId+"'); comp.focus();");
  }

  public static void createNewFile(String filePath)
  {
    Writer writer = null;
    try
    {
      writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "utf-8"));
    }
    catch (IOException ex)
    {
    }
    finally
    {
      try
      {
        writer.close();
      }
      catch (Exception ex)
      {
      }
    }
  }
  
  public static void editPropertyFile(String filePath, String key, String value)
  {
    try
    {
      FileInputStream in = new FileInputStream(filePath);
      Properties props = new Properties();
      props.load(in);
      in.close();

      FileOutputStream out = new FileOutputStream(filePath);
      props.setProperty(key, value);
      props.store(out, null);
      out.close();
    }
    catch (Exception e)
    {
    }
  }
  
  public static String readValueFromPropertyFile(String filePath, String key)
  {
    Properties prop = new Properties();
    InputStream input = null;
    try
    {
      input = new FileInputStream(filePath);
      prop.load(input);
      String value = prop.getProperty(key);
      return value;
    }
    catch (Exception e)
    {
    }
    finally
    {
      if (input != null)
      {
        try
        {
          input.close();
        }
        catch (IOException e)
        {
        }
      }
    }
    return null;
  }
  
  /** function take iterator name  then return the current row of the iterator */
    public static Row getCurrentRow(String iteratorName)
    {
      if (getIterator(iteratorName).getCurrentRow() != null)
      {
        return getIterator(iteratorName).getCurrentRow();
      }
      return null;
    }
  
  /** function to cancel changes in ViewObject */
  public static void cancelChangesInViewObject(String voName)
  {
    cancelChangesForRows(voName, true);
  }
  
  /** function to cancel changes in Iterator */
  public static void cancelChangesInIter(String Iter)
  {
    cancelChangesForRows(Iter, false);
  }
  
  private static void cancelChangesForRows(String name, boolean isVo)
  {
    ViewObject vo = null;
    if(isVo)
    {
      vo = getViewObjectByName(name);
    }
    else
    {
      vo = getIterator(name).getViewObject();
    }
    
    if(vo == null)
    {
      return;
    }
    
    Row[] rows = vo.getAllRowsInRange();
    if(rows == null)
    {
      return;
    }
    
    for (int i = 0; i < rows.length; i++)
    {
      Row r = rows[i];
      if(r != null)
      {
        r.refresh(Row.REFRESH_REMOVE_NEW_ROWS | Row.REFRESH_UNDO_CHANGES);
      }
    }
  }
  
  public static boolean isRowStatusNew(Row rw)
  {
    return EntityImpl.STATUS_NEW == getRowStatus(rw);
  }
  
  public static boolean isRowStatusUpdate(Row rw)
  {
    return EntityImpl.STATUS_MODIFIED == getRowStatus(rw);
  }
  
  public static byte getRowStatus(Row rw)
  {
    if (rw != null)
    {
      ViewRowImpl myRow = (ViewRowImpl) rw;
      EntityImpl entityImpl = myRow.getEntity(0);
      return entityImpl.getEntityState();
    }
    return -1;
  }
  
  public static String getClientComputerName()
  {
    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    return request.getRemoteHost().split("\\.")[0];
  }
  
  public static void addCookie(String cookieName, String cookieValue)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();

    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setMaxAge(60*60*24*365*5);
    response.addCookie(cookie);
  }

  public static void removeCookie(String cookieName)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
    HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
    Cookie[] cookies = request.getCookies();

    if (cookies != null)
    {
      for (int i = 0; i < cookies.length; i++)
      {
        String name = cookies[i].getName();

        if ((name == null) || (!name.equals(cookieName)))
          continue;
        cookies[i].setMaxAge(0);
        response.addCookie(cookies[i]);
        break;
      }
    }
  }

  public static String getCookieValue(String cookieName)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
    Cookie[] cookies = request.getCookies();

    if (cookies != null)
    {
      for (int i = 0; i < cookies.length; i++)
      {
        String name = cookies[i].getName();
        String value = cookies[i].getValue();
        int age = cookies[i].getMaxAge();

        if ((name != null) && (name.equals(cookieName)) && (age != 0))
        {
          return value;
        }
      }
    }
    return null;
  }
}
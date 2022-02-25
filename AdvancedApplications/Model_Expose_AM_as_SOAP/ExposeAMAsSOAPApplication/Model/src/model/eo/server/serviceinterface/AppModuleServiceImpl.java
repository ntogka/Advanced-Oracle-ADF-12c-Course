package model.eo.server.serviceinterface;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.interceptor.Interceptors;

import model.eo.AppModuleImpl;
import model.eo.common.serviceinterface.AppModuleService;

import model.vo.common.DepartmentsViewSDO;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.common.Diagnostic;
import oracle.jbo.common.sdo.SDOHelper;
import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.FindCriteria;
import oracle.jbo.server.svc.ServiceContextInterceptor;
import oracle.jbo.server.svc.ServiceImpl;
import oracle.jbo.service.errors.ServiceException;

import oracle.webservices.annotations.PortableWebService;

import oracle.wsm.metadata.annotation.PolicyReference;
import oracle.wsm.metadata.annotation.PolicySet;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Nov 27 01:05:19 GMT 2018
// ---------------------------------------------------------------------
@Interceptors({ ServiceContextInterceptor.class })
@Stateless(name = "model.eo.common.AppModuleServiceBean", mappedName = "AppModuleServiceBean")
@Remote(AppModuleService.class)
@PortableWebService(targetNamespace = "/model/eo/common/", serviceName = "AppModuleService",
                    portName = "AppModuleServiceSoapHttpPort",
                    endpointInterface = "model.eo.common.serviceinterface.AppModuleService")
@PolicySet(references = { @PolicyReference(value = "oracle/wss_username_token_service_policy") })
public class AppModuleServiceImpl
  extends ServiceImpl
  implements AppModuleService
{
  private static boolean _isInited = false;

  private static final Map _map = new HashMap();

  /**
   * This is the default constructor (do not remove).
   */
  public AppModuleServiceImpl()
  {
    init();
    setApplicationModuleDefName("model.eo.AppModule");
    setConfigurationName("AppModuleService");
  }

  /**
   * Generated method. Do not modify. Do initialization in the constructor
   */
  protected void init()
  {
    if (_isInited)
    {
      return;
    }
    synchronized (AppModuleServiceImpl.class)
    {
      if (_isInited)
      {
        return;
      }
      try
      {
        SDOHelper.INSTANCE.defineSchema("model/eo/common/serviceinterface/", "AppModuleService.xsd");
        _map.put("getCustomDeptName", AppModuleImpl.class.getMethod("getCustomDeptName", new Class[] { int.class }));
        _isInited = true;
      }
      catch (Throwable t)
      {
        ADFLogger.createADFLogger(Diagnostic.SERVINT_RT_LOGGER).severe(t);
      }
    }
  }


  /**
   * getDepartmentsView1: generated method. Do not modify.
   */
  public DepartmentsViewSDO getDepartmentsView1(Integer departmentId)
    throws ServiceException
  {
    return (DepartmentsViewSDO) get(new Object[] { departmentId }, "DepartmentsView1", DepartmentsViewSDO.class);
  }

  /**
   * createDepartmentsView1: generated method. Do not modify.
   */
  public DepartmentsViewSDO createDepartmentsView1(DepartmentsViewSDO departmentsView1)
    throws ServiceException
  {
    return (DepartmentsViewSDO) create(departmentsView1, "DepartmentsView1");
  }

  /**
   * deleteDepartmentsView1: generated method. Do not modify.
   */
  public void deleteDepartmentsView1(DepartmentsViewSDO departmentsView1)
    throws ServiceException
  {
    delete(departmentsView1, "DepartmentsView1");
  }

  /**
   * findDepartmentsView1: generated method. Do not modify.
   */
  public List<DepartmentsViewSDO> findDepartmentsView1(FindCriteria findCriteria, FindControl findControl)
    throws ServiceException
  {
    return (List<DepartmentsViewSDO>) find(findCriteria, findControl, "DepartmentsView1", DepartmentsViewSDO.class);
  }

  /**
   * getCustomDeptName: generated method. Do not modify.
   */
  public String getCustomDeptName(int deptId)
    throws ServiceException
  {
    return (String) invokeCustom((Method) _map.get("getCustomDeptName"), new Object[] { deptId }, new String[] { null },
                                 false);
  }
}


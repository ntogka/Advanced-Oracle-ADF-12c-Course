
package model.eo.common;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import com.oracle.xmlns.adf.svc.types.FindControl;
import com.oracle.xmlns.adf.svc.types.FindCriteria;

import model.vo.common.DepartmentsViewSDO;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.0-b170214.1743
 * Generated source version: 2.2
 *
 */
@WebService(name = "AppModuleService", targetNamespace = "/model/eo/common/")
@XmlSeeAlso({ com.oracle.xmlns.adf.svc.errors.ObjectFactory.class, com.oracle.xmlns.adf.svc.types.ObjectFactory.class,
              model.eo.common.types.ObjectFactory.class, model.vo.common.ObjectFactory.class,
              sdo.commonj.ObjectFactory.class, sdo.commonj.java.ObjectFactory.class, sdo.commonj.xml.ObjectFactory.class
  })
public interface AppModuleService
{


  /**
   *
   * @param departmentId
   * @return
   *     returns model.vo.common.DepartmentsViewSDO
   * @throws ServiceException
   */
  @WebMethod(action = "/model/eo/common/getDepartmentsView1")
  @WebResult(name = "result", targetNamespace = "/model/eo/common/types/")
  @RequestWrapper(localName = "getDepartmentsView1", targetNamespace = "/model/eo/common/types/",
                  className = "model.eo.common.types.GetDepartmentsView1")
  @ResponseWrapper(localName = "getDepartmentsView1Response", targetNamespace = "/model/eo/common/types/",
                   className = "model.eo.common.types.GetDepartmentsView1Response")
  public DepartmentsViewSDO getDepartmentsView1(@WebParam(name = "departmentId",
                                                          targetNamespace = "/model/eo/common/types/") int departmentId)
    throws ServiceException;

  /**
   *
   * @param departmentsView1
   * @return
   *     returns model.vo.common.DepartmentsViewSDO
   * @throws ServiceException
   */
  @WebMethod(action = "/model/eo/common/createDepartmentsView1")
  @WebResult(name = "result", targetNamespace = "/model/eo/common/types/")
  @RequestWrapper(localName = "createDepartmentsView1", targetNamespace = "/model/eo/common/types/",
                  className = "model.eo.common.types.CreateDepartmentsView1")
  @ResponseWrapper(localName = "createDepartmentsView1Response", targetNamespace = "/model/eo/common/types/",
                   className = "model.eo.common.types.CreateDepartmentsView1Response")
  public DepartmentsViewSDO createDepartmentsView1(@WebParam(name = "departmentsView1",
                                                             targetNamespace = "/model/eo/common/types/")
                                                   DepartmentsViewSDO departmentsView1)
    throws ServiceException;

  /**
   *
   * @param departmentsView1
   * @throws ServiceException
   */
  @WebMethod(action = "/model/eo/common/deleteDepartmentsView1")
  @RequestWrapper(localName = "deleteDepartmentsView1", targetNamespace = "/model/eo/common/types/",
                  className = "model.eo.common.types.DeleteDepartmentsView1")
  @ResponseWrapper(localName = "deleteDepartmentsView1Response", targetNamespace = "/model/eo/common/types/",
                   className = "model.eo.common.types.DeleteDepartmentsView1Response")
  public void deleteDepartmentsView1(@WebParam(name = "departmentsView1", targetNamespace = "/model/eo/common/types/")
                                     DepartmentsViewSDO departmentsView1)
    throws ServiceException;

  /**
   *
   * @param findCriteria
   * @param findControl
   * @return
   *     returns java.util.List<model.vo.common.DepartmentsViewSDO>
   * @throws ServiceException
   */
  @WebMethod(action = "/model/eo/common/findDepartmentsView1")
  @WebResult(name = "result", targetNamespace = "/model/eo/common/types/")
  @RequestWrapper(localName = "findDepartmentsView1", targetNamespace = "/model/eo/common/types/",
                  className = "model.eo.common.types.FindDepartmentsView1")
  @ResponseWrapper(localName = "findDepartmentsView1Response", targetNamespace = "/model/eo/common/types/",
                   className = "model.eo.common.types.FindDepartmentsView1Response")
  public List<DepartmentsViewSDO> findDepartmentsView1(@WebParam(name = "findCriteria",
                                                                 targetNamespace = "/model/eo/common/types/")
                                                       FindCriteria findCriteria,
                                                       @WebParam(name = "findControl",
                                                                 targetNamespace = "/model/eo/common/types/")
                                                       FindControl findControl)
    throws ServiceException;

  /**
   *
   * @param deptId
   * @return
   *     returns java.lang.String
   * @throws ServiceException
   */
  @WebMethod(action = "/model/eo/common/getCustomDeptName")
  @WebResult(name = "result", targetNamespace = "/model/eo/common/types/")
  @RequestWrapper(localName = "getCustomDeptName", targetNamespace = "/model/eo/common/types/",
                  className = "model.eo.common.types.GetCustomDeptName")
  @ResponseWrapper(localName = "getCustomDeptNameResponse", targetNamespace = "/model/eo/common/types/",
                   className = "model.eo.common.types.GetCustomDeptNameResponse")
  public String getCustomDeptName(@WebParam(name = "deptId", targetNamespace = "/model/eo/common/types/") int deptId)
    throws ServiceException;

}
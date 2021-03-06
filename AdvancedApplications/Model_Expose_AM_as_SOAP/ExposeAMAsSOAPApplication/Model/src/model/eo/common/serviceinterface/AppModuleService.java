package model.eo.common.serviceinterface;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;

import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import model.vo.common.DepartmentsViewSDO;

import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.FindCriteria;
import oracle.jbo.service.errors.ServiceException;

import oracle.webservices.annotations.PortableWebService;
import oracle.webservices.annotations.SDODatabinding;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Nov 27 01:05:19 GMT 2018
// ---------------------------------------------------------------------
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED, style = SOAPBinding.Style.DOCUMENT)
@PortableWebService(targetNamespace = "/model/eo/common/", name = "AppModuleService",
                    wsdlLocation = "model/eo/common/serviceinterface/AppModuleService.wsdl")
@SDODatabinding(schemaLocation = "model/eo/common/serviceinterface/AppModuleService.xsd")
public interface AppModuleService
{

  public static final String NAME = "{/model/eo/common/}AppModuleService";

  @WebMethod(action = "/model/eo/common/getDepartmentsView1", operationName = "getDepartmentsView1")
  @RequestWrapper(targetNamespace = "/model/eo/common/types/", localName = "getDepartmentsView1")
  @ResponseWrapper(targetNamespace = "/model/eo/common/types/", localName = "getDepartmentsView1Response")
  @WebResult(name = "result")
  DepartmentsViewSDO getDepartmentsView1(@WebParam(mode = WebParam.Mode.IN, name = "departmentId") Integer departmentId)
    throws ServiceException;

  @WebMethod(action = "/model/eo/common/createDepartmentsView1", operationName = "createDepartmentsView1")
  @RequestWrapper(targetNamespace = "/model/eo/common/types/", localName = "createDepartmentsView1")
  @ResponseWrapper(targetNamespace = "/model/eo/common/types/", localName = "createDepartmentsView1Response")
  @WebResult(name = "result")
  DepartmentsViewSDO createDepartmentsView1(@WebParam(mode = WebParam.Mode.IN, name = "departmentsView1")
                                            DepartmentsViewSDO departmentsView1)
    throws ServiceException;

  @WebMethod(action = "/model/eo/common/deleteDepartmentsView1", operationName = "deleteDepartmentsView1")
  @RequestWrapper(targetNamespace = "/model/eo/common/types/", localName = "deleteDepartmentsView1")
  @ResponseWrapper(targetNamespace = "/model/eo/common/types/", localName = "deleteDepartmentsView1Response")
  void deleteDepartmentsView1(@WebParam(mode = WebParam.Mode.IN, name = "departmentsView1")
                              DepartmentsViewSDO departmentsView1)
    throws ServiceException;

  @WebMethod(action = "/model/eo/common/findDepartmentsView1", operationName = "findDepartmentsView1")
  @RequestWrapper(targetNamespace = "/model/eo/common/types/", localName = "findDepartmentsView1")
  @ResponseWrapper(targetNamespace = "/model/eo/common/types/", localName = "findDepartmentsView1Response")
  @WebResult(name = "result")
  List<DepartmentsViewSDO> findDepartmentsView1(@WebParam(mode = WebParam.Mode.IN, name = "findCriteria")
                                                FindCriteria findCriteria,
                                                @WebParam(mode = WebParam.Mode.IN, name = "findControl")
                                                FindControl findControl)
    throws ServiceException;

  /**
   * Exported method getCustomDeptName from AppModule.
   */
  @WebMethod(action = "/model/eo/common/getCustomDeptName", operationName = "getCustomDeptName")
  @RequestWrapper(targetNamespace = "/model/eo/common/types/", localName = "getCustomDeptName")
  @ResponseWrapper(targetNamespace = "/model/eo/common/types/", localName = "getCustomDeptNameResponse")
  @WebResult(name = "result")
  String getCustomDeptName(@WebParam(mode = WebParam.Mode.IN, name = "deptId") int deptId)
    throws ServiceException;
}



package model.vo.common;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the model.vo.common package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory
{

  private final static QName _DepartmentsViewSDO_QNAME = new QName("/model/vo/common/", "departmentsViewSDO");
  private final static QName _DepartmentsViewSDOManagerId_QNAME = new QName("/model/vo/common/", "ManagerId");
  private final static QName _DepartmentsViewSDOLocationId_QNAME = new QName("/model/vo/common/", "LocationId");

  /**
   * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: model.vo.common
   *
   */
  public ObjectFactory()
  {
  }

  /**
   * Create an instance of {@link DepartmentsViewSDO }
   *
   */
  public DepartmentsViewSDO createDepartmentsViewSDO()
  {
    return new DepartmentsViewSDO();
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link DepartmentsViewSDO }{@code >}
   *
   * @param value
   *     Java instance representing xml element's value.
   * @return
   *     the new instance of {@link JAXBElement }{@code <}{@link DepartmentsViewSDO }{@code >}
   */
  @XmlElementDecl(namespace = "/model/vo/common/", name = "departmentsViewSDO")
  public JAXBElement<DepartmentsViewSDO> createDepartmentsViewSDO(DepartmentsViewSDO value)
  {
    return new JAXBElement<DepartmentsViewSDO>(_DepartmentsViewSDO_QNAME, DepartmentsViewSDO.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
   *
   * @param value
   *     Java instance representing xml element's value.
   * @return
   *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
   */
  @XmlElementDecl(namespace = "/model/vo/common/", name = "ManagerId", scope = DepartmentsViewSDO.class)
  public JAXBElement<Integer> createDepartmentsViewSDOManagerId(Integer value)
  {
    return new JAXBElement<Integer>(_DepartmentsViewSDOManagerId_QNAME, Integer.class, DepartmentsViewSDO.class, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
   *
   * @param value
   *     Java instance representing xml element's value.
   * @return
   *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
   */
  @XmlElementDecl(namespace = "/model/vo/common/", name = "LocationId", scope = DepartmentsViewSDO.class)
  public JAXBElement<Integer> createDepartmentsViewSDOLocationId(Integer value)
  {
    return new JAXBElement<Integer>(_DepartmentsViewSDOLocationId_QNAME, Integer.class, DepartmentsViewSDO.class,
                                    value);
  }

}

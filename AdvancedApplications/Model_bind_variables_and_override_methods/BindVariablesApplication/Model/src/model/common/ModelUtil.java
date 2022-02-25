package model.common;

import oracle.adf.share.ADFContext;

public class ModelUtil
{
  public static String getUserDeptId()
  {
    return (String) ADFContext.getCurrent().getSessionScope().get("userDept");
  }
}



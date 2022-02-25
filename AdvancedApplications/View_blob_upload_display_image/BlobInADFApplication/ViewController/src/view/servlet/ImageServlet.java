package view.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

@WebServlet(name = "ImageServlet", urlPatterns = { "/imageservlet" })
public class ImageServlet
  extends HttpServlet
{
  private static final String CONTENT_TYPE = "image/jpg; charset=windows-1256";

  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
  }
  
  private Connection getConnection() throws Exception
  {
    Context ctx = new InitialContext();
    DataSource ds = (DataSource) ctx.lookup("hrDS");
    Connection conn = ds.getConnection();
    return conn;
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType(CONTENT_TYPE);
    String id = request.getParameter("id");
    OutputStream os = response.getOutputStream();
    Connection conn = null;
    try
    {
      conn = getConnection();
      PreparedStatement statement =
        conn.prepareStatement("SELECT EMP_IMAGE FROM EMPLOYEES where EMPLOYEE_ID= ? ");
      statement.setInt(1, new Integer(id));
      ResultSet rs = statement.executeQuery();
      if (rs.next())
      {
        Blob blob = rs.getBlob(1);
        if (blob != null)
        {
          BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
          int b;
          byte[] buffer = new byte[10240];
          while ((b = in.read(buffer, 0, 10240)) != -1)
          {
            os.write(buffer, 0, b);
          }
          os.close();
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        if (conn != null)
        {
          conn.close();
        }
      }
      catch (SQLException sqle)
      {
        sqle.printStackTrace();
      }
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doGet(request, response);
  }
}

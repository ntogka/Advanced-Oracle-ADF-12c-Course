package view.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CommonUtil
{
  
  
  public static void main(String[] args)
  {
    System.out.println("Get: " + invokeGetREST("http://127.0.0.1:7101/ExposeAMToRESTApplication-RESTWebService-context-root/rest/1/depts?limit=20","application/vnd.oracle.adf.action+json"));
    
    System.out.println("POST: " + invokePostREST("http://127.0.0.1:7101/ExposeAMToRESTApplication-RESTWebService-context-root/rest/1/depts", "application/vnd.oracle.adf.action+json", "{\"name\" : \"getDeptNameCustomMethod\",\"parameters\" : [{\"deptId\" : \"10\"}]}") );
  }
  
  public static String invokeGetREST(String restURL, String contentType)
  {
    try
    {

      URL url = new URL(restURL);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-Type", contentType);
      
      
      // Apply credentials to call secure REST service
      String username = "weblogic";
      String password = "weblogic1";
      
      byte[] message = (username + ":" + password).getBytes("UTF-8");
      String encoded = javax.xml.bind.DatatypeConverter.printBase64Binary(message);
      conn.setRequestProperty("Authorization", "Basic " + encoded);
      ////////////////////////////////////////////////////////
      
      
      
      if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
      {
        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

      String output; 
      String finalOut = "";
      System.out.println("Output from Server .... \n");
      while ((output = br.readLine()) != null)
      {
        finalOut = finalOut + output;
      }

      conn.disconnect();
      return finalOut;
    }
    catch (MalformedURLException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static String invokePostREST(String restURL, String contentType, String input)
  {
    try
    {

      URL url = new URL(restURL);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", contentType);

      OutputStream os = conn.getOutputStream();
      os.write(input.getBytes());
      os.flush();
      
      if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
      {
        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

      String output, out = "";
      System.out.println("Output from Server .... \n");
      while ((output = br.readLine()) != null)
      {
        out = out + output;
      }

      conn.disconnect();
      return out;
    }
    catch (MalformedURLException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}

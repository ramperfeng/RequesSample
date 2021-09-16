package com.duco.api.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PreRunUtility {
	public static FileInputStream FIS=null;
	public static FileInputStream ErrorProp=null;
	public static FileInputStream ENDPointProp=null;
	public static Properties config=null;
	public static Logger logman;
	
  public static void main(String [] args)
  {
	  System.out.println("Pre run utility method Launched properly ");
	  try {
		  System.setProperty("ScenarioName", "PreRunLog");
		  System.setProperty("ScenarioId", "PreRunID");
		  logman=LogManagerPreRun.getInstance();		  
		  FIS=new FileInputStream(System.getProperty("user.dir")+"/src/test/java/com/duco/api/globalconfig/GlobalConfig.properties");
		  ErrorProp=new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/test_props/error.properties");
		  ENDPointProp=new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/test_props/apiEndPoints.properties");
		  config= new Properties();
		  config.load(FIS);
		  config.load(ErrorProp);
		  config.load(ENDPointProp);
		  failSafePropertyGeneration();
		  
		  
		  System.out.println("Pre run utility method completed as expected :>>>>>>>>>>>>>>>>> ");
	  }
	  catch(Throwable e)
	  {
		  logman.error("unable to create pre run log, error="+e.getMessage());
	  }
	  
	  //handleRunConfigurationUsingExcel();
  }
	
  
  public static void failSafePropertyGeneration()
  {
	  
  try
  {
	  for (Object prop:config.keySet())
	  {
		if(System.getenv(prop.toString())!=null)
		{
			System.setProperty(prop.toString().trim().toUpperCase(),System.getenv(prop.toString()));
		}else 
		{
			System.setProperty(prop.toString().trim().toUpperCase(),config.getProperty(prop.toString()));
		}
	  }
	  }catch (Exception e)
  {
		  logman.error("Error Occured Inside failSafePropertyGenaeration block in preRun,Error Description ="+e.getMessage());
  }
		  
  }
  
  public static void invokeWireMockServer()
  {
	  try
	  {
		  if(System.getProperty("TYPE").equalsIgnoreCase("API"))
		  {
			  if(System.getProperty("ISMOCKINGENABLED")==null)
			  {
				  System.setProperty("ISMOCKINGENABLED", "FLASE");
			  }
			  if(System.getProperty("ISMOCKINGENABLED").equalsIgnoreCase("TRUE"))
			  {
				  System.out.println("Wire mock process");
				  String path=System.getProperty("user.dir")+"/src/test/resources/wiremock/wiremock-jre8-standalone-2.27.2.jar";
				  int port=Integer.valueOf(System.getProperty("URI").split(":")[2]);
				  try
				  {
					  Runtime.getRuntime().exec("java.exe-jar"+path+"--port="+port);
					  System.out.println("WireMock up");
				  }
				  catch (IOException e)
				  {
					  System.out.println("There are some errors during setting up wiremock sever, message="+e.getMessage());
				  }
			  }
		  }
	  }catch (Exception e)
	  {
		  System.out.println("There are some error in invocked wiremock server ,message= "+e.getMessage());
	  }
  }
}


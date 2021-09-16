package com.duco.api.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;



public class FeatureUtils {
	public Logger logman;
	public FeatureUtils()
	{
		logman=LogManagerPreRun.getInstance();
	}
	
	public void readAllFeatureFile(String initialPath,String finalPath,Map<String,List<Map<String,String>>> testData,Map<String,List<String>> colNamePerScenarioTestData) throws Exception
	{
		try
		{
			File file=new File(System.getProperty("user.dir")+"/"+initialPath);
			String [] featureArray=file.list();
			for(String featureName:featureArray)
			{
				FileReader fileReader=new FileReader(System.getProperty("user.dir")+"/"+initialPath+"/"+featureName);
				BufferedReader bufferReader=new BufferedReader(fileReader);
				StringBuilder stringBuilder=new StringBuilder();
				String line=bufferReader.readLine();
				while(line!=null)
				{
					stringBuilder.append(line+"!");
					line=bufferReader.readLine();
				}
				String featureINOneLine=stringBuilder.toString();
				logman.debug("old data"+stringBuilder.toString());
				String featureWithAppendedData=getScenarioNameAndParseForEachOfThem(featureINOneLine,testData,colNamePerScenarioTestData);
				logman.debug("new Data ="+featureWithAppendedData);
				PrintWriter printWriter=new PrintWriter(new FileWriter(System.getProperty("user.dir")+"/"+finalPath+"/"+featureName));
				String [] arr=featureWithAppendedData.split("!!");
				for (String featureLine:arr)
				{
					printWriter.println(featureLine);
				}
				printWriter.flush();
				printWriter.close();
				logman.info("New Feature file ="+featureName+"is created with data appnded from Excel file at location ="+finalPath);
				
			}
			
			
		}catch (IOException e)
		{
			logman.error("Error in readAllFeatureFile, Error="+e.getMessage());
		}
	}
	
	public String getScenarioNameAndParseForEachOfThem(String text,Map<String,List<Map<String,String>>> data, Map<String,List<String>> colNamePerScenarioTestData)
	{
	String newText=null;
	try
	{
		newText=text;
		List<Integer> listOfScenarioIndex=searchPatternForScenario("Scenario",newText);
		String splitter="!!";
		for(int i=0;i<listOfScenarioIndex.size();i++)
		{
			if(newText.length()>text.length())
			{
				listOfScenarioIndex=searchPatternForScenario("Scenario",newText);
			}
			int endpoint=newText.indexOf(splitter,listOfScenarioIndex.get(i));
			String textWithScenarioName=newText.substring(listOfScenarioIndex.get(i),endpoint);
			String scenarioName=textWithScenarioName.split(":")[1].trim();
			if(data.containsKey(scenarioName))
			{
				//newText=parseFeatureFileAndAppendDataFromExcelFile(newText,splitter,scenarioName,data,colNamePerScenarioTestData);
			}
		}
		}catch(Exception e)
		{
			logman.error("Error in getScenarioNameAndParseForEachOfThem Method,Error="+e.getMessage());
		}
	return newText;
	
	}
	
	public List<Integer> searchPatternForScenario(String regex,String test)
	{
		List<Integer> list=new ArrayList<>();
	
		return list;
	}
}

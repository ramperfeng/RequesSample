package com.duco.api.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;



import jxl.Sheet;
import jxl.Workbook;

public class ReadXML {
	
	public String readXlsWithSameTagName(String paload,String inputFileName,String outputFileName)
	{
		
		 
		
			String basedir=System.getProperty("user.dir") +"/src/main/resource";
		
		Workbook tcWorkBook;
		String sheet_path=basedir+inputFileName;//basedir+config().getString(inputFileName)
		String lines="";
		String key_name="";
		String finalXMLBlock="";
		
		try
		{
		File file1=new File(sheet_path);
		
			tcWorkBook=Workbook.getWorkbook(file1);
			Sheet tcsheet=tcWorkBook.getSheet("DATA");
			
			//Reading the excel to get data 
			
		for(int row=1;row<tcsheet.getRows();row++)
		{
		key_name=tcsheet.getCell(0, row).getContents();
		if(paload.equalsIgnoreCase(key_name))
		{
		String outfilepath=basedir+outputFileName;	
		String line;
	//	String outfilepath=basedir+outputFileName;
		try {
			File file=new File(outfilepath);
			BufferedReader reader = new BufferedReader(new FileReader(file.toString()));
			
			/*
			 * while((line = reader.readLine()!=null)) { lines=lines+line;
			 * 
			 * }
			 */
			reader.close();
		}
		catch(Exception e)
		{
			
		}
		String xmlblock=lines;
		// **** inserting data into xml **  
		for (int col=1;col<tcsheet.getColumns();col++)
		{
			String colHeader =tcsheet.getCell(col, 0).getContents();
			String colHeaderVal="$-{ " + colHeader +"}";
			String colValue=tcsheet.getCell(col, row).getContents();
			if(colValue.equals("NA"))
			{
				System.out.println("colHeader is NA  ***** "+ colHeader);
				xmlblock=ReadXML.removeTagFromPayload(xmlblock,colHeader);
				
			}
			else
			{
				xmlblock=xmlblock.replace(colHeader, colHeaderVal);
			}
			
		}
		finalXMLBlock=finalXMLBlock+xmlblock;
		}
		}
		}
		
		catch (Exception e) {
			
		}
		return finalXMLBlock;
		
	}
	
	
	
	private static String removeTagFromPayload(String payload ,String tagName)
	{
		
		String [] tags =tagName.split("/");
		String temp= "";
		String tagStart ="<"+tags[0]+">";
		String tagEnd=   "<"+tags[0]+">";
		int start=payload.indexOf(tagStart);
		int end=payload.indexOf(tagEnd,start);
		if(tags.length==1)
		{
			if(start==-1 || end ==-1)
			{
				temp=payload.replaceAll("<" +tagName+"/>" ,"");
				
				
			}
			else 
			{
				String xmlBlockForTag=payload.substring(start,end)+tagEnd;
				temp=payload.replace(xmlBlockForTag, "");
				
			}
			return temp;
		}
			else 
			{
				String newPayload = payload.substring(start,end+tags[0].length()+3);
				temp = removeTagFromPayload(newPayload ,tagName.replace((tags[0]+"/"), ""));
				payload=payload.replace(newPayload,temp);
			}
		
		
		return payload;
	}
	
	public File [] finder(String dirName,String fileName)
	{
		File dir = new File (dirName);
		return dir.listFiles(new FilenameFilter()
				{public boolean accept (File dir,String filename)
			{
					return filename.endsWith(fileName + ".xml");}
			});
			
				}
	
		
	
				/*
				 * public String readXML(String fileName,String folderName) { for(File file :
				 * finder ("basedir"+"/input-payload/"+folderName,fileName)) { try { File
				 * fxmlFile = new File(file.getAbsolutePath()); DocumentBuilderFactory
				 * dbFactory=DocumentBuilderFactory.newInstance(); DocumentBuilder
				 * dBuilder=dbFactory.newDocumentBuilder(); Document
				 * doc=dBuilder.parse(fxmlFile); DOMSource domSource= new DOMSource(doc); }
				 * 
				 * } }
				 */
	
	
	
}
	

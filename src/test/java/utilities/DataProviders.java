package utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {
	
	//DataProvider 1
	@DataProvider(name="LoginData")
	public String [][] getLoginData() throws IOException
	{
		String path=".\\testData\\LoginData.xlsx";  	//taking excel file from testData folder
		
		ExcelUtility xlutil = new ExcelUtility(path);	//Creating an object for ExcelUtility
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);
		
		String logindata[][]=new String[totalrows][totalcols];  //created for two dimension array which can store
		
		for(int i=1; i<=totalrows; i++)			//read data from excel storing in two dimensional array
		{
			for(int j=0; j<totalcols; j++)				//0	i is row and j is column
			{
				logindata[i-1][j]=xlutil.getCellData("Sheet1", i, j);	//1,0
			}
		}
		return logindata;	//returning two dimensional array
	}
	
	//DataProvider 2
	@DataProvider(name="MenuListData")
	public String [][] getMenuData() throws IOException
	{
		String path=".\\testData\\MenuListData.xlsx";  	//taking excel file from testData folder
		
		ExcelUtility xlutil = new ExcelUtility(path);	//Creating an object for ExcelUtility
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);
		
		String logindata[][]=new String[totalrows][totalcols];  //created for two dimension array which can store
		
		for(int i=1; i<=totalrows; i++)			//read data from excel storing in two dimensional array
		{
			for(int j=0; j<totalcols; j++)				//0	i is row and j is column
			{
				logindata[i-1][j]=xlutil.getCellData("Sheet1", i, j);	//1,0
			}
		}
		return logindata;	//returning two dimensional array
	}
	
	//DataProvider 3
}

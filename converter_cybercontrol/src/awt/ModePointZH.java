package awt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModePointZH {

	//读取CSV
	public static void main(String[] args) {
		readModePointCSV();
	}

	private static void readModePointCSV() {
		// TODO Auto-generated method stub
		 File csv = new File("D:\\new_car.csv");  // CSV文件路径
		 File outfile = new File("D://new_car2.csv");//存储到新文件的路径
		    BufferedReader br = null;
		    try
		    {
		        br = new BufferedReader(new FileReader(csv));
		    
		    BufferedWriter bw = new BufferedWriter(new FileWriter(outfile));
		    
		    String line = "";
		    String everyLine = "";
		    List<String> allString = new ArrayList();
		            while ((line = br.readLine()) != null)  //读取到的内容给line变量
		            {
		            	//bw.write(pointList2.get(i));
					    
		                everyLine = line;
		                if (everyLine.length() == 15) {
		                	everyLine = everyLine.replaceAll("_MODE", "");
		                	if (everyLine.startsWith("_A")) {
		                		everyLine="D"+everyLine.substring(2, everyLine.length());
							}
		                	if (everyLine.indexOf("MA")!=-1) {
		                		everyLine = everyLine.replace("MA", "M");
							}if (everyLine.indexOf("_")!=-1) {
		                		everyLine = everyLine.replace("_", "");
							}
							everyLine=everyLine.substring(0, everyLine.length()-1)+"MA"+everyLine.substring(everyLine.length()-1, everyLine.length());
		                	System.out.println(everyLine);
						}
		                //allString.add(everyLine);
		                bw.write(line+","+everyLine);
		                bw.newLine();
		            }
		            bw.close();
		            System.out.println("csv表格中所有行数："+allString.size());
		    } catch (IOException e)
		    {
		        e.printStackTrace();
		    }
	}
}

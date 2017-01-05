package helppac;

import java.io.ByteArrayOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.FileWriter;  
import java.io.IOException;  
import java.nio.channels.FileChannel;  
import java.sql.Date;
import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.List;    
  
public class CsvWriter  
{  
  
    public static void main(String args[]){  
        CsvWriter cw = new CsvWriter();  
        String csvFile = "C:/Temp/createCSV.csv";  
        String cur="USD";
        int num=100;
        cw.createCSV(csvFile,cur,num);  
        System.out.println("生成完毕");
          
//        String localPath = "C:/Temp/";  
//        String fName = "createCsvByList.csv";  
//        String sTitle = "ID,NAME,SEX,EMAIL,TEL";  
//        List listSource = new ArrayList();  
//        listSource.add("1,tomcat,male,tomcat@tomcat.com,1383838438");  
//        listSource.add("2,Jboss,male,jboss@jboss.com,1484848748");  
          
//        cw.createCSVByList(listSource, sTitle, localPath, fName);  
    }  
      
    /** 
     * 复制csv文件 
     * @param source 源文件 
     * @param dest   目标文件 
     * */  
    public void copyCSV(String source, String dest){  
        try  
        {  
            FileChannel in = new FileInputStream(source).getChannel();  
            FileChannel out = new FileOutputStream(dest).getChannel();  
              
//          in.transferTo(0, in.size(), out);  
            out.transferFrom(in, 0, in.size());  
              
            in.close();  
            out.close();  
        } catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 创建csv文件 
     * @param csvFile csv完整文件名 
     * @param currency 目标币别
     * @param num 明细行数
     * */  
    public void createCSV(String csvFile,String currency,int num){  
        FileWriter fw = null;  
        String terminal=null;
        
        try  
        {  
            fw = new FileWriter(csvFile);  
            
            if(currency.endsWith("USD")){
            	terminal="test001";
            }
            else{
            	terminal="test002";
            }
            for(int i=0;i<num;i++){
            	long orderid=System.currentTimeMillis()+i; 
//            	System.out.println("orderid="+orderid);            	
            fw.write("10012016111,"+terminal+","+orderid+","+"CNY,"+currency+",500,50,1,320722199010231665,姓名,目的,6225881257401777,ext1,ext2\r\n");  
            }
              
            fw.flush();  
            fw.close();  
        } catch (IOException e)  
        {  
            e.printStackTrace();  
        }finally{  
            if(null != fw){  
                try  
                {  
                    fw.close();  
                } catch (IOException e)  
                {  
                    e.printStackTrace();  
                }  
            }  
        }  
          
    }  
      
    /** 
     * 创建csv文件 
     * @param listSource 行数据 
     * @param sTitle     字段名 
     * @param localPath  目录路径 
     * @param fName      文件名 
     * */  
    public void createCSVByList(List listSource, String sTitle, String localPath, String fName){  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        FileOutputStream fos = null;  
        try  
        {  
            out.write(sTitle.getBytes());  
            out.write(",".getBytes());  
            out.write("\n".getBytes());  
              
            Iterator it = listSource.iterator();  
            while(it.hasNext()){  
                String value = (String)it.next();  
                out.write(value.getBytes());  
                out.write(",".getBytes());    //以逗号为分隔符  
                out.write("\n".getBytes());   //换行  
            }  
            //没有目录，先生成目录  
//            FileUtils fileTool = new FileUtils();  
//            fileTool.newFolder(localPath);  
              
            File newfile = new File(localPath,fName);  
              
            fos = new FileOutputStream(newfile);  
            fos.write(out.toByteArray());  
              
            fos.flush();  
            out.close();  
            fos.close();  
        } catch (IOException e)  
        {  
            e.printStackTrace();  
        }finally{  
            try  
            {  
                if(null != out) out.close();  
                if(null != fos) fos.close();  
            } catch (IOException e)  
            {  
                e.printStackTrace();  
            }  
        }  
        System.out.println("生成"+fName+"完成");  
    }  
      
} 

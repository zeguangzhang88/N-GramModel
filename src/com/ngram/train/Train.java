package com.ngram.train;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.dal.Resultdal;
import com.model.Result;

public class Train {
    static int count=0;
	
    public static void ngramsbychar(int n, String str) {
     
    	
        int srcBegin = 0;
		int srcEnd =str.length();
		char[] dst =new char[srcEnd];
		int dstBegin = 0;
		str.getChars(srcBegin, srcEnd, dst, dstBegin);
        
        for (int i = 0; i < dst.length - n + 1; i++)
        {
        	 String k = singleNgram(dst, i, i+n);
        	 System.out.println(k);
        	 count++;
        }
        System.out.println("你的ngram总数为："+count);
        
    }

    //得到一个Ngram
    public static String singleNgram(char[] dst, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + dst[i]);
        
        if (Resultdal.contain(sb.toString())){
        	Resultdal.updateResult(sb.toString());
        }else{
        	Result result = new Result(sb.toString(),1);
        	Resultdal.save(result);
        }
        	
        return sb.toString();
    }

    public static String changeCharset(String str,String newCharset){
    	
    	if(str!=null){
    		byte[] bs = str.getBytes();
    		
    		try {
				return new String(bs,newCharset);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    		
    	}
    	return null;
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
     
    	long startTime= System.currentTimeMillis(); //获取开始时间
    	
    	//NGRAM的两个输入：nOfNgramInput（几元模型） corpusOfNgramInput（语料库文本）
    	int nOfNgramInput = Integer.parseInt(args[0]);
    	String corpusOfNgramInput = readFile(args[1]);
    	ngramsbychar(nOfNgramInput, corpusOfNgramInput);
    	
    	long endTime= System.currentTimeMillis();  //获取结束时间
    	
    	
    	String runtime = formatLongToTimeStr(endTime - startTime);
    	System.out.println("程序运行时间： "+ runtime);
    	
    }
    
    
    
    /**
     * 读取某个文件夹下的所有文件
     */
    public static String readFile(String filepath) throws FileNotFoundException, IOException {
    	String totalTxtOfFiles ="";  
    	File file = new File(filepath);
			if (file.isDirectory()) {
			        System.out.println("文件夹");
			        String[] filelist = file.list();
			        for (int i = 0; i < filelist.length; i++) {
			                File readfile = new File(filepath + "\\" + filelist[i]);
			                if (!readfile.isDirectory()) {
			                	String s = readTxtFile(readfile.getPath());
			                	totalTxtOfFiles+=s;
			                } 
			        }

			}
            return totalTxtOfFiles;
    }

    /**
     * 负责读取某个文件中的内容,如果出现异常返回空格字符串，这样不会影响蒙古文的语料质量
     */
	public static String readTxtFile(String filePath) {
		String s ="";
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;

				while ((lineTxt = bufferedReader.readLine()) != null) {
					s+=lineTxt;				
				}
				read.close();
				return s;
			} else {
				System.out.println("找不到指定的文件");
				return " ";
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			return " ";
			
		}
	}
	
	public static String formatLongToTimeStr(Long l) {        
		   String str = "";       
		   int hour = 0;        
		   int minute = 0;       
		   int second = 0;        
		   second = l.intValue() / 1000;        
		   if (second > 60) {            
		    minute = second / 60;            
		    second = second % 60;        
		   }       

		   if (minute > 60) {            
		    hour = minute / 60;            
		    minute = minute % 60;        
		   }        
		   String strtime = hour+"小时"+minute+"分钟"+second+"秒";
		   return strtime;   

		}


    
}
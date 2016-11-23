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
        System.out.println("���ngram����Ϊ��"+count);
        
    }

    //�õ�һ��Ngram
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
     
    	long startTime= System.currentTimeMillis(); //��ȡ��ʼʱ��
    	
    	//NGRAM���������룺nOfNgramInput����Ԫģ�ͣ� corpusOfNgramInput�����Ͽ��ı���
    	int nOfNgramInput = Integer.parseInt(args[0]);
    	String corpusOfNgramInput = readFile(args[1]);
    	ngramsbychar(nOfNgramInput, corpusOfNgramInput);
    	
    	long endTime= System.currentTimeMillis();  //��ȡ����ʱ��
    	
    	
    	String runtime = formatLongToTimeStr(endTime - startTime);
    	System.out.println("��������ʱ�䣺 "+ runtime);
    	
    }
    
    
    
    /**
     * ��ȡĳ���ļ����µ������ļ�
     */
    public static String readFile(String filepath) throws FileNotFoundException, IOException {
    	String totalTxtOfFiles ="";  
    	File file = new File(filepath);
			if (file.isDirectory()) {
			        System.out.println("�ļ���");
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
     * �����ȡĳ���ļ��е�����,��������쳣���ؿո��ַ�������������Ӱ���ɹ��ĵ���������
     */
	public static String readTxtFile(String filePath) {
		String s ="";
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
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
				System.out.println("�Ҳ���ָ�����ļ�");
				return " ";
			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݳ���");
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
		   String strtime = hour+"Сʱ"+minute+"����"+second+"��";
		   return strtime;   

		}


    
}
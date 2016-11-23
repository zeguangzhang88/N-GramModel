package com.ngram.identify;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import com.dal.Resultdal;
import com.model.Result;

public class GetDocument {

	static int count=0;
	//获取某一个小的窗口的字符串，并放入内存的list中。
	static List<Result> listofidentify = new  ArrayList<Result>();
	
	
	public static List<Result> getDocument(int n,String text) {
		// TODO Auto-generated method stub
System.out.println(text);
		ngramsbychar(n,text); //根据输入把ngram放入内存listofidentify中
		sort();               //对listofidentify进行排序            
		return listofidentify;
	}
	
	//按照thecount给list降序排序
	public static void sort(){
		
		Comparator<Result> comparator = new Comparator<Result>(){
			   public int compare(Result s1, Result s2) {
			    //先排年龄
			    if(s1.getTheCount()!=s2.getTheCount()){
			     return s2.getTheCount()-s1.getTheCount();
			    }
				return 1;
			   }
		};
		Collections.sort(listofidentify,comparator);
		
	}
	
	
	
	//该方法字符串str上设置了一个大小为n的滑动窗口
	public static void ngramsbychar(int n, String str) {
	     
        int srcBegin = 0;
		int srcEnd =str.length();
		char[] dst =new char[srcEnd];
		int dstBegin = 0;
		str.getChars(srcBegin, srcEnd, dst, dstBegin);
        
        for (int i = 0; i < dst.length - n + 1; i++)
        {
        	 String k = singleNgram(dst, i, i+n);       
        	 count++;
        } 
    }
	
	
	// 得到窗口下的Ngram
	public static String singleNgram(char[] dst, int start, int end) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < end; i++)
			sb.append((i > start ? " " : "") + dst[i]);

		if (sb.toString() != "   ") {
			int pos = contain(sb.toString());
			if (pos != -1) {
				Result temp = listofidentify.get(pos);
				int countnow = temp.getTheCount();
				temp.setTheCount(countnow + 1);
				listofidentify.set(pos, temp);

			} else {
				Result temp = new Result(sb.toString(), 1);
				listofidentify.add(temp);
			}
		} else {
			// do nothings

		}
		return sb.toString();
	}
    
    //判断是否包含word,如果包含，则返回位置，否则返回默认值-1位置
    public static int contain(String word){
    	 for(int i = 0;i<listofidentify.size();i++){   		
             if (listofidentify.get(i).getTheWord().equals(word)){     	
             	return i;
             }
    	 }
    	 return -1;
    }
	
}

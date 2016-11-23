package com.ngram.identify;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.List;

import com.model.Result;

public class BeginCompare {

	public static int MAX =396;
	public static int tempDistance;
	public static int Threshold = 375;
	public static int countOfCorrectrecognition =0;
	public static int countOfAll = 0;
	

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		int nOfInput = Integer.parseInt(args[0]);
		String fileDirectoryOfInput = args[1];			
		String rateOfCorrect= readFile(nOfInput,fileDirectoryOfInput);
		System.out.println("正确识别率为：  " + rateOfCorrect);
		
	}
	
	/**
     * 读取某个文件夹下的所有文件
     */
	public static String readFile(int nOfInput, String fileDirectoryOfInput)
			throws FileNotFoundException, IOException {

		File file = new File(fileDirectoryOfInput);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				int distance = 0;
				File readfile = new File(fileDirectoryOfInput + "\\" + filelist[i]);
				if (!readfile.isDirectory()) {
					countOfAll++;
					String inputStr = readTxtFile(readfile.getPath());
					distance =measureDistance(nOfInput, inputStr);
					System.out.println("距离 " + distance);
					if (distance < Threshold) {
						System.out.println("YES,who I WANT TO FIND is you ");
						countOfCorrectrecognition++;

					} else {
						System.out.println("NO!you must get out");
						System.out.println("这个不能识别，序号为" + filelist[i] + "文件内容为  " + inputStr);// 如果不是识别的语种，那么我们应该输出这个不能识别的序号及文本
					}
				}
			
			}

		} else {

			System.out.println("你输入了一个无效的目录");
		}

		float rateOfCorrectrecognition = (float) countOfCorrectrecognition/countOfAll;
		DecimalFormat df = new DecimalFormat("0.00");				// 格式化小数，不足的补0
		String rateOfCorrect = df.format(rateOfCorrectrecognition);// 返回的是String类型的
		return rateOfCorrect;
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
	
	
	public static int measureDistance(int n,String text){
		int sum = 0;
		int banlance =0;
		List<Result> listofmodel = GetTrainModel.getTrainModel();		
		List<Result> listofidentify =GetDocument.getDocument(n,text);
		int leni = listofidentify.size();
		int lenj = listofmodel.size();
		for(int i = 0;i<leni;i++){
			tempDistance =MAX;
			for(int j= 0;j<lenj;j++){
				if(listofidentify.get(i).getTheWord().equals(listofmodel.get(j).getTheWord())){
					tempDistance =java.lang.Math.abs(i-j);
					
					break;
				}				
			}	
			//System.out.println("单个距离为 :"+tempDistance);
			sum += tempDistance;
		}
		
		banlance = sum/listofidentify.size();
		return banlance;
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
}

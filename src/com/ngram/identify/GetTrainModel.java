package com.ngram.identify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.dal.Resultdal;
import com.model.Result;

public class GetTrainModel {

	static List<Result> listofmodel = new ArrayList();

	public static List<Result> getTrainModel() {

		listofmodel = Resultdal.showAllResult();
		return listofmodel;
	}

	
	//前多少个ngram占据总的ngram次数的比值
	public static void main(String[] args){
		int sum =0;
		int sum300 =0;
		List<Result> listofmodel = GetTrainModel.getTrainModel();
		int len = listofmodel.size();
		for(int i=0;i<len;i++){
			
			sum += listofmodel.get(i).getTheCount();
			
		}
		
		for(int i=0;i<=700;i++){
			
			sum300 += listofmodel.get(i).getTheCount();
			
		}
		
		float rate = (float)sum300/sum;
		System.out.println(rate);
	}
}

package com.hztianque.exceltool.test;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.hztianque.framework.core.utils.DateUtil;

public class Test3 {
	 public static  String generateRandomFilename(){    
	        String RandomFilename = "";    
	        Random rand = new Random();//生成随机数    
	        int random = rand.nextInt();    
	  
	        Calendar calCurrent = Calendar.getInstance();   
	        int intMillisecond = calCurrent.get(Calendar.MILLISECOND);
	        int intMinute = calCurrent.get(Calendar.MINUTE);
	        int intHour = calCurrent.get(Calendar.HOUR_OF_DAY);
	        int intDay = calCurrent.get(Calendar.DATE);    
	        int intMonth = calCurrent.get(Calendar.MONTH) + 1;    
	        int intYear = calCurrent.get(Calendar.YEAR);    
	        String now = String.valueOf(intYear) + "_" + String.valueOf(intMonth) + "_" +    
	            String.valueOf(intDay) + "_"+intHour+"_"+intMinute+"_"+intMillisecond;    
	            
	            
	        return now;    
	    }    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(generateRandomFilename());
	}

}

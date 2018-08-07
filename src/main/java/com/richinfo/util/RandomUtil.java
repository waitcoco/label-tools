package com.richinfo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomUtil {
	
	/**
	 * 生成二维码串"数字码+，+字符码 "
	 */
	public static String createRandom(int codelength){
		StringBuffer numcode = new StringBuffer();//数字二维码
		StringBuffer charcode = new StringBuffer();//字母二维码
		String seeds[]={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		Random random=new Random();
		if(codelength<=0) return null;
		for(int i=0;i<codelength;i++){
			numcode.append(random.nextInt(10));//nextInt(10) 0-10之间的整数、不包含10
			if(i%2==0){
				int index=random.nextInt(26);
				if(index>=26) index=25;
				charcode.append(seeds[index]);
			}else{
				charcode.append(random.nextInt(10));
			}
		}
		return numcode.toString()+","+charcode.toString();
		
	}
	public static String createRandomNum(int codelength){
		StringBuffer numcode = new StringBuffer();
		Random random=new Random();
		if(codelength<=0) return null;
		for(int i=0;i<codelength;i++){
			numcode.append(random.nextInt(10));
		}
		return numcode.toString();
	}
	
	
	
	public static String zeroStr(int length,int c){
		String s = String.valueOf(c);
		if(s.length()<length){
			String zero="";
			for(int i=0;i<length-s.length();i++){
				zero+="0";
			}
			return zero+c;
		}else{
			return ""+c;
		}
	}
	
	
	public static String randomStr(int length){
		
		StringBuffer result= new StringBuffer("");
		Random random=new Random();
		if(length<=0) return "";
		for(int i=0;i<length;i++){
			result.append(random.nextInt(10));
		}
		return result.toString();
	}
	public static String randomNSStr(int length){
		
		StringBuffer result= new StringBuffer("");
		String seeds[]={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9"};
		Random random=new Random();
		if(length<=0) return "";
		for(int i=0;i<length;i++){
			int index=random.nextInt(35);
			if(index>=35) index=34;
			result.append(seeds[index]);
		}
		return result.toString();
	}
	/**
	 * 生成二维码串"数字码+，+字符码 "(最新码库)
	 * 刘国庆
	 */
	public static String createRandom(int codelength,int charlength){
		StringBuffer numcode = new StringBuffer();//数字二维码
		StringBuffer charcode = new StringBuffer();//字母二维码
		String seeds[]={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		Random random=new Random();
		if(codelength<=0) return null;
		for(int i=0;i<codelength;i++){
			numcode.append(random.nextInt(10));//nextInt(10) 0-10之间的整数、不包含10
		}
		for(int i=0;i<charlength;i++){
			if(i%2==0){
				int index=random.nextInt(26);
				if(index>=26) index=25;
				charcode.append(seeds[index]);
			}else{
				charcode.append(random.nextInt(10));
			}
		}
		
		return numcode.toString()+","+charcode.toString();
	}
	public static String getRandom(){
		Random rand = new Random(); 
		int tmp = Math.abs(rand.nextInt());
		return String.valueOf(tmp % (999999 - 100000 + 1) + 100000);
	}
	
	public static String getString(int length){
		  String base = "0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";   //生成字符串从此序列中取
		  Random random = new Random();   
		  StringBuffer sb = new StringBuffer();   
		  for (int i = 0; i < length; i++) {   
		      int number = random.nextInt(base.length());   
		      sb.append(base.charAt(number));   
		  }  
		  return sb.toString();
	  }
	
	public static String createMembersn(Long entid,Long memberid){
		String s = ""+entid+memberid;
		String date = DateTimeUtils.getDate("ddHHmmss");
		if(s.length()<8){
			s = s + date.substring(0, 8-s.length());
		}
		return s;
	}
	
	public static void main(String args	[]){
//		Random rand = new Random(); 
//			int tmp = Math.abs(rand.nextInt(500000));
//			System.out.println(""+tmp);
//		for(int i=0;i<20;i++){
//		List<String> list = getString("04,05,06,08,10,14,19,24,27,29",6);
//		List<String> list2 = getString("04,05,06,08,10,14",1);
//		Collections.sort(list);
//		System.out.println(list.toString()+list2.toString());
//		}
		for(int i=1;i<2025;i++) {
			System.out.println(zeroStr(6, i));
		}
	}
	public static List<String> getString(String base,int length){
		  Random random = new Random();   
		  String[] bs = base.split(",");
		  List<String> list = new ArrayList<String>();
		  for (int i = 0; i < length*10; i++) {   
		      int number = random.nextInt(bs.length); 
		      String s = bs[number];
		      if(!list.contains(s))
		    	  list.add(s);   
		      if(list.size()>=length)
		    	  break;
		  }  
		  return list;
	}
}
